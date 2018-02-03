package projectManager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewProject {

	private static List<Project> projectList = new ArrayList<Project>();
	public static void initProject()
	{
		if(!projectList.isEmpty())
			projectList.clear();
		PreparedStatement stmt;
		Connection conn;
		ResultSet rs;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("select p_id,name,stdate,edate from project");
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
	public static List<Project> getAllProjects() {
    	initProject();
        return new ArrayList<Project>(projectList);
    }
	
	public static List<Project> getFilterProject(ViewFilter filter)
	{
		List<Project> someProjects = new ArrayList<Project>();
		String name = filter.getName().toLowerCase();
		
		for(Iterator<Project> i = projectList.iterator(); i.hasNext();)
		{
			Project tmp = i.next();
			if(tmp.getName().toLowerCase().contains(name))
				someProjects.add(tmp);
		}
		return someProjects;
	}
}
