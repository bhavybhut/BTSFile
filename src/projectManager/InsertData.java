package projectManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class InsertData extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Wire 
	private Datebox stdate;
	@Wire
	private Datebox edate;
	@Wire
	private Textbox name;
	@Listen("onClick = button")
	public void submit()
	{
		PreparedStatement stmt;
		Connection conn = null;
		
		try
		{
			Long time1 = stdate.getValue().getTime();
			Date date1 = new Date(time1);
			Long time2 = edate.getValue().getTime();
			Date date2 = new Date(time2);
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("insert into project(name,stdate,edate) values(?,?,?)");
			stmt.setString(1, name.getValue());
			stmt.setDate(2, date1);
			stmt.setDate(3, date2);
			stmt.executeUpdate();
			Executions.sendRedirect("assign_proj.zul");
			conn.close();
		}
		catch(Exception e){}
		
	}
	
	public void updateProject(Project project){
		
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			stmt.execute("update project set name='"+project.getName()+"', stdate='"+project.getStdate()+"', edate='"+project.getEdate()+"' where p_id='"+project.getId()+"'");
			stmt.close();
			conn.close();
		}catch(Exception e){}
	}
	
	public void deleteProject(Project project){
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			stmt.execute("delete from project where p_id="+project.getId());
			stmt.close();
			conn.close();
			Executions.sendRedirect("view_proj.zul");
		}
		catch(Exception e){}
	}
	
	
}
