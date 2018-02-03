package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class LoginValidation extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Wire
	private Textbox unm;
	
	@Wire
	private Textbox pwd;
	
	@Wire
	private Label msg;
	
	@Listen("onClick = button")
	public void validate(){
		Connection conn;
		PreparedStatement stmt,stmt1;
		ResultSet rs,rs1;
		String uname = null;
		String password = null;
		String designation = null;
		String name = null;
		String secQ = null;
		String adminUnm = null;
		String adminPwd = null;
		boolean flag = false,flag1 = false;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt1 = conn.prepareStatement("select unm,pwd from admin");
			rs1 = stmt1.executeQuery();
			while(rs1.next()){
				adminUnm = rs1.getString(1);
				adminPwd = rs1.getString(2);
				if(unm.getValue().equals(adminUnm) && pwd.getValue().equals(adminPwd)){
					flag1 = true;
					break;
				}
			}
			stmt = conn.prepareStatement("select unm,pwd,desig,name,sec_que from user where unm='"+unm.getValue()+"'");
			rs = stmt.executeQuery();
			while(rs.next()){
				uname = rs.getString(1);
				password = rs.getString(2);
				designation = rs.getString(3);
				name = rs.getString(4);
				secQ = rs.getString(5);
				if(unm.getValue().equals(uname) && pwd.getValue().equals(password)){
					Sessions.getCurrent().setAttribute("user", name);
					flag = true;
					break;
				}	
			}
			if(flag== true){
				if(designation.equals("Developer")){
					if(secQ == null)
						Executions.sendRedirect("/developer/edit_prof.zul");
					else
						Executions.sendRedirect("/developer/dev_home.zul");
				}
				else if(designation.equals("Project Manager")){
					if(secQ == null)
						Executions.sendRedirect("/projectmanager/edit_prof.zul");
					else
						Executions.sendRedirect("/projectmanager/pm_home.zul");
				}
				else if(designation.equals("Tester")){
					if(secQ == null)
						Executions.sendRedirect("/tester/edit_prof.zul");
					else
						Executions.sendRedirect("/tester/tester_home.zul");
				}
			}
			else if(flag1 == true){
				Sessions.getCurrent().setAttribute("user", "Admin");
				Executions.sendRedirect("/admin/admin_home.zul");
			}
			else{
				unm.setValue("");
				pwd.setValue("");
				msg.setVisible(true);
			}
			conn.close();
		}catch(Exception e){ e.printStackTrace();}
	}
}
