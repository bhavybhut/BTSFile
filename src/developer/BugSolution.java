package developer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import tester.Bug;

public class BugSolution extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire
	private Textbox txtresolve;
	@Wire
	private Row post;

	private Rows postrow;
	
	@Wire
	private AbstractComponent btnRow;

	@Listen("onClick = #btnResolve")
	public void postSolution(){
		
		Connection conn;
		Statement st,st2;
		PreparedStatement stmt;
		ResultSet rs,rs2;
		int b_id;
		HashMap<String,Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("bugDetail");
		Bug bug = (Bug) map.get("bugModel");
		b_id = bug.getB_id();
		Label name = new Label();
		String user = String.valueOf(Sessions.getCurrent().getAttribute("user"));
		name.setValue(user);
		Label solution = new Label();
		solution.setValue(txtresolve.getValue().toString());
		post.setVisible(true);
		post.appendChild(name);
		post.appendChild(solution);
		btnRow.setVisible(false);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			st = conn.createStatement();
			rs = st.executeQuery("select u_id from user where name='"+user+"'");
			while(rs.next()){
				int u_id = rs.getInt(1);
				stmt = conn.prepareStatement("insert into resolve_bug(b_id,u_id,solution) values(?,?,?)");
				stmt.setInt(1, b_id);
				stmt.setInt(2, u_id);
				stmt.setString(3, txtresolve.getValue());
				stmt.executeUpdate();
				st2 = conn.createStatement();
				st2.execute("update bug set status='Resolved' where b_id="+b_id);
				st2.close();
				stmt.close();
				Date currentDatetime = new Date(System.currentTimeMillis());  
		        java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime()); 
				st2 = conn.createStatement();
				st2.execute("insert into bug_log(update_date) values('"+sqlDate+"') where b_id="+b_id);
			}
			rs.close();
			st.close();
			
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void doAfterCompose(Window comp) throws Exception {
		// TODO Auto-generated method stub
		postrow = (Rows) comp.getFellow("postrow");
		try{
			Connection conn;
			Statement st,st2;
			ResultSet rs,rs2;
			int b_id;
			HashMap<String,Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("bugDetail");
			Bug bug = (Bug) map.get("bugModel");
			b_id = bug.getB_id();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			st = conn.createStatement();
			rs = st.executeQuery("select u_id,solution from resolve_bug where b_id="+b_id);
			while(rs.next()){
				int u_id = rs.getInt(1);
				String solution = rs.getString(2);
				st2 = conn.createStatement();
				rs2 = st2.executeQuery("select name from user where u_id="+u_id);
				while(rs2.next()){
					String nm = rs2.getString(1);
					Label name = new Label();
					name.setValue(nm);
					Label sol = new Label();
					sol.setValue(solution);
					Row temprow = new Row();
					temprow.appendChild(name);
					temprow.appendChild(sol);
					postrow.appendChild(temprow);
				}
			}
			
			
			rs = st.executeQuery("select status from bug where b_id="+b_id);
			while(rs.next()){
				String status = rs.getString(1);
				if(status.equals("Open")){
					Textbox txtbx = new Textbox();
					txtbx.setId("txtresolve");
					txtbx.setRows(3);
					Button btn = new Button();
					btn.setId("btnResolve");
					btn.setLabel("Resolve");
					Row temprow = new Row();
					temprow.setId("btnRow");
					temprow.setValign("bottom");
					temprow.appendChild(txtbx);
					temprow.appendChild(btn);
					postrow.appendChild(temprow);
				}
			}
			
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		super.doAfterCompose(comp);
	}
}
