package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class ForgetPassword extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Wire
	private Textbox uname;
	@Wire
	private Textbox ans;
	@Wire
	private Textbox npwd;
	@Wire
	private Textbox cpwd;
	@Wire
	private Row lblunm;
	@Wire
	private Row sec_que;
	@Wire
	private Row sec_ans;
	@Wire
	private Row btnunm;
	@Wire
	private Row btnsubmit;
	@Wire
	private Row newpwd;
	@Wire
	private Row newcpwd;
	@Wire
	private Row btnpwd;
	@Wire
	private Label que;
	
	
	
	@Listen("onClick=#submit")
	public void forgetPassword(){
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String secQue = null;
		boolean flag = false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select sec_que from user where unm='"+uname.getValue()+"'");
			while(rs.next()){
				secQue = rs.getString(1);
				if(!secQue.equals(null)){
					flag = true;
					break;
				}
			}
			if(flag){
				que.setValue(secQue);
				lblunm.setVisible(false);
				btnunm.setVisible(false);
				sec_que.setVisible(true);
				sec_ans.setVisible(true);
				btnsubmit.setVisible(true);
			}
			else{
				Messagebox.show("User doesn't exsist");
				uname.setValue("");
			}
			conn.close();
		}catch(Exception e){}
	}
	
	@Listen("onClick=#submitAns")
	public void submitAnswer(){
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String secAns = null;
		boolean flag = false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select sec_ans from user where unm='"+uname.getValue()+"'");
			while(rs.next()){
				secAns = rs.getString(1);
				if(ans.getValue().equals(secAns)){
					flag = true;
					break;
				}
			}
			if(flag == true){
				sec_que.setVisible(false);
				sec_ans.setVisible(false);
				btnsubmit.setVisible(false);
				newpwd.setVisible(true);
				newcpwd.setVisible(true);
				btnpwd.setVisible(true);
			}
			else{
				Messagebox.show("Wrong Answer.. Please write correct answer");
				ans.setValue("");
			}
		}catch(Exception e){}
	}
	
	@Listen("onClick=#changePwd")
	public void changePassword(){
		if(!(npwd.getValue().equals(cpwd.getValue()))|| npwd.getValue().equals(""))
    	{
    		Messagebox.show("Passwords do not match");
    		npwd.setValue("");
    		cpwd.setValue("");
    	}
		else{
			Connection conn;
			Statement stmt;
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
				stmt = conn.createStatement();
				stmt.execute("update user set pwd='"+npwd.getValue()+"' where unm='"+uname.getValue()+"'");
				conn.close();
				Messagebox.show("Password Successfully Changed!!!", "Success", Messagebox.OK, Messagebox.EXCLAMATION,
						new org.zkoss.zk.ui.event.EventListener(){

							@Override
							public void onEvent(Event event) throws Exception {
								// TODO Auto-generated method stub
								if(Messagebox.ON_OK.equals(event.getName()))
									Executions.sendRedirect("login.zul");
								else
									Executions.sendRedirect("login.zul");
							}
					
				});
			}catch(Exception e){}
		}
	}
}
