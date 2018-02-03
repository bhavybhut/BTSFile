package developer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Sessions;

import projectManager.Project;

public class ViewProjects {
	
	private static List<Project> projectList = new ArrayList<Project>();

	public static void viewDeveloperProject(){
		if(!projectList.isEmpty())
			projectList.clear();
		PreparedStatement stmt;
		Connection conn;
		ResultSet rs;
		int d_id = 0,p_id = 0;
		try
		{
			String user = String.valueOf(Sessions.getCurrent().getAttribute("user"));
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("select u_id from user where name='"+user+"'");
			rs = stmt.executeQuery();
			while(rs.next()){
				d_id = rs.getInt(1);
			}
			stmt.close();
			rs.close();
			stmt = conn.prepareStatement("select p_id from assign_project where d_id="+d_id);
			rs = stmt.executeQuery();
			while(rs.next()){
				p_id = rs.getInt(1);
			}
			stmt.close();
			rs.close();
			stmt = conn.prepareStatement("select p_id,name,stdate,edate from project where p_id="+p_id);
			rs = stmt.executeQuery();
			while(rs.next())
			{
				String id = rs.getString(1);
				String name = rs.getString(2);
				Date stdate = rs.getDate(3);
				Date edate = rs.getDate(4);
				projectList.add(new Project(id,name,stdate,edate));
			}
			conn.close();
		}
		catch(Exception e){}
	}
	
	public static List<Project> getAllProjects(){
		viewDeveloperProject();
		return new ArrayList<Project>(projectList);
	}
	
	public static List<Project> getFilterProject(ViewFilter filter)
	{
		List<Project> someProjects = new ArrayList<Project>();
		String name = filter.getPname().toLowerCase();
		
		for(Iterator<Project> i = projectList.iterator(); i.hasNext();)
		{
			Project tmp = i.next();
			if(tmp.getName().toLowerCase().contains(name))
				someProjects.add(tmp);
		}
		return someProjects;
	}
}
