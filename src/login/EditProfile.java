package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import admin.User;

public class EditProfile implements Composer{

	private Connection conn;
	Statement stmt;
	ResultSet rs;
	String nm;
	String unm;
	String email;
	String sec_que;
	String sec_ans;
	
	@Wire
	private Textbox name;
	@Wire
	private Textbox uname;
	@Wire
	private Textbox mail;
	@Wire
	private Textbox secQ;
	@Wire
	private Textbox secA;
	

	public void setPassword(String pwd,String cpwd){
		try{
				String user = String.valueOf(Sessions.getCurrent().getAttribute("user"));
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
				stmt = conn.createStatement();
				if(user.equals("Admin")){
					stmt.execute("update admin set pwd='"+pwd+"' where name='"+user+"'");
					Messagebox.show("Successfully changed!!!");
				}
				else{
					stmt.execute("update user set pwd='"+pwd+"' where name='"+user+"'");
					Messagebox.show("Successfully changed!!!");
				}
		}catch(Exception e){}
	}
	
	public void updateUser(User u){
		String desig = null;
		final String designation;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			String user = String.valueOf(Sessions.getCurrent().getAttribute("user"));
			stmt = conn.createStatement();
			stmt.execute("update user set name='"+u.getName()+"', unm='"+u.getUsername()+"', email='"+u.getEmail()+"', sec_que='"+u.getSec_que()+"', sec_ans='"+u.getSec_ans()+"' where name='"+user+"'");
			stmt.close();

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select desig from user where name='"+user+"'");
			while(rs.next()){
				desig = rs.getString(1);
			}
			designation = desig;
			Messagebox.show("Successfully Updated!!!","Success",Messagebox.OK,Messagebox.INFORMATION,
					new org.zkoss.zk.ui.event.EventListener(){

						@Override
						public void onEvent(Event arg0) throws Exception {
							// TODO Auto-generated method stub
							if(Messagebox.ON_OK.equals(arg0.getName())){
								if(designation.equals("Developer"))
									Executions.sendRedirect("dev_home.zul");
								else if(designation.equals("Project Manager"))
									Executions.sendRedirect("pm_home.zul");
								else if(designation.equals("Tester"))
									Executions.sendRedirect("tester_home.zul");
							}
						}
				
			});
			conn.close();
		}catch(Exception e){}
	}
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			String user = String.valueOf(Sessions.getCurrent().getAttribute("user"));
			name = (Textbox) comp.getFellow("name");
			uname = (Textbox) comp.getFellow("uname");
			mail = (Textbox) comp.getFellow("mail");
			secQ = (Textbox) comp.getFellow("secQ");
			secA = (Textbox) comp.getFellow("secA");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select name,unm,email,sec_que,sec_ans from user where name='"+user+"'");
			while(rs.next()){
				nm = rs.getString(1);
				unm = rs.getString(2);
				email = rs.getString(3);
				sec_que = rs.getString(4);
				sec_ans = rs.getString(5);
			}
			name.setValue(nm);
			uname.setValue(unm);
			mail.setValue(email);
			secQ.setValue(sec_que);
			secA.setValue(sec_ans);
		}catch(Exception e){}
	}
}
