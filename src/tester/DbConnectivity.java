package tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class DbConnectivity extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	private Combobox proj;
	@Wire
	private Combobox priority;
	@Wire
	private Textbox summary;
	@Wire
	private Textbox description;
	@Wire
	private Combobox developer;
	@Wire
	private Textbox version;
	private static byte[] img;
	
	public void storeBytes(byte[] img){
		DbConnectivity.img = img;
	}

	
	@Listen("onClick = #btnBug")
	public void insert(){
		int p_id = 0,d_id = 0,b_id,t_id = 0;
		String email = null;
		String user = String.valueOf(Sessions.getCurrent().getAttribute("user"));
		Connection conn;
		Statement stmt1,stmt2,stmt4;
		PreparedStatement stmt3,stmt5;
		ResultSet rs1,rs2,rs3;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt1 = conn.createStatement();
			rs1 = stmt1.executeQuery("select p_id from project where name='"+proj.getSelectedItem().getValue().toString()+"'");
			while(rs1.next()){
				p_id = rs1.getInt("p_id");
			}
			rs1.close();
			stmt2 = conn.createStatement();
			rs2 = stmt2.executeQuery("select u_id,email from user where name='"+developer.getSelectedItem().getValue().toString()+"'");
			while(rs2.next()){
				d_id = rs2.getInt(1);
				email = rs2.getString(2);
			}
			rs2.close();
			stmt2.close();
			stmt2 = conn.createStatement();
			rs2 = stmt2.executeQuery("select u_id from user where name='"+user+"'");
			while(rs2.next()){
				t_id = rs2.getInt(1);
			}
			stmt3 = conn.prepareStatement("insert into bug(p_id,priority,summary,description,d_id,img,t_id,version) values (?,?,?,?,?,?,?,?)");
			stmt3.setInt(1, p_id);
			stmt3.setString(2, String.valueOf(priority.getSelectedItem().getValue()));
			stmt3.setString(3, summary.getValue());
			stmt3.setString(4, description.getValue());
			stmt3.setInt(5, d_id);
			stmt3.setBytes(6, img);
			stmt3.setInt(7, t_id);
			stmt3.setString(8, version.getValue());
			stmt3.executeUpdate();
			stmt4 = conn.createStatement();
			rs3 = stmt4.executeQuery("select max(b_id) from bug");
			while(rs3.next()){
				b_id = rs3.getInt(1);
				System.out.println(b_id);
				stmt5 = conn.prepareStatement("insert into bug_log(b_id,create_date) values(?,?)");
				stmt5.setInt(1, b_id);
				Date currentDatetime = new Date(System.currentTimeMillis());  
		        java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());  
				stmt5.setDate(2, sqlDate);
				stmt5.executeUpdate();
			}
			mailDetails(email, String.valueOf(proj.getSelectedItem().getValue()), summary.getValue(), description.getValue());
			rs3.close();
			conn.close();
			Executions.sendRedirect("bug_report.zul");
		}catch(Exception e){ e.printStackTrace(); }
	}
	
	public void mailDetails(String email,String pname,String summary,String desc){
		String from = "poojadave209@gmail.com";
		String to = email;
		String subject = "You've got a bug in your system";
		String text = "\nYou've got a bug in your project "+pname
				+". You can find details of the bug here.\nSummary :"+summary
				+"\nDescripiton :"+desc
				+"\nFor more details Click here : localhost:8080/BTS/developer/bug_report.zul";
		SendMail mail = new SendMail(from, to, subject, text);
		mail.send();
	}
	
	public void closeBug(Bug bug){
		Connection conn;
		Statement stmt;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			stmt.execute("update bug set status='Closed' where b_id="+bug.getB_id());
			conn.close();
			Executions.sendRedirect("bug_report.zul");
		}catch(Exception e){}
	}
	
}
