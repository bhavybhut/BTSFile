package admin;

import java.sql.DriverManager;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
public class JdbcComposer extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	StoreComponent combo = new StoreComponent();
	@Wire
	private Textbox name;
	@Wire
	private Textbox unm;
	@Wire
	private Textbox pwd;
	@Wire
	private Combobox desig;
	@Wire
	private Textbox email;
	@Wire
	private Textbox pm;
	@Wire
	private Textbox dept;
	@Listen("onClick = button")
	public void submit()
	{
		PreparedStatement stmt = null;
		Connection conn = null;
		try
		{
			combo.doAfterCompose(desig);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("insert into user(name,unm,pwd,desig,email,pm,dept) values(?,?,?,?,?,?,?)");
			stmt.setString(1, name.getValue());
			stmt.setString(2, unm.getValue());
			stmt.setString(3, pwd.getValue());
			stmt.setString(4, String.valueOf(desig.getSelectedItem().getValue()));
			stmt.setString(5, email.getValue());
			stmt.setString(6, pm.getValue());
			stmt.setString(7, dept.getValue());
			stmt.executeUpdate();
			Executions.sendRedirect("view_user.zul");
			conn.close();
		}
		catch(Exception e){}
	}
	public void updateUser(User user){
		Statement stmt;
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			stmt.execute("update user set name='"+user.getName()+"', unm='"+user.getUsername()
					+"', desig='"+user.getDesignation()+"', email='"+user.getEmail()+"', pm='"+ user.getPm()+"', dept='"+user.getDept()
					+"' where u_id='"+user.getId()+"'");
			stmt.close();
			
		}catch(Exception e){
			System.err.println("ERROR: " + e.getMessage());
		}
		finally {
			if (conn != null) {
				try {
					conn.close();
					} catch (Exception e) { /* ignore close errors */
				}
			}
		}
	}
	public void deleteUser(User user){
		Connection conn;
		Statement stmt;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			stmt.executeUpdate("delete from user where u_id="+user.getId());
			stmt.close();
			conn.close();
			Executions.sendRedirect("view_user.zul");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
