package projectManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.zkoss.zk.ui.Executions;


public class AssignProject{

	public void assignProject(String name,String pname){
		int p_id = 0,d_id = 0;
		Connection conn = null;
		Statement stmt1,stmt2;
		ResultSet rs1,rs2;
		PreparedStatement stmt3;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt1 = conn.createStatement();
			rs1 = stmt1.executeQuery("select p_id from project where name='"+pname+"'");
			while(rs1.next()){
				p_id = rs1.getInt(1);
				stmt2 = conn.createStatement();
				rs2 = stmt2.executeQuery("select u_id from user where name='"+name+"' and desig='Developer'");
				while(rs2.next()){
					d_id = rs2.getInt(1);
					stmt3 = conn.prepareStatement("insert into assign_project(p_id,d_id) values(?,?)");
					stmt3.setInt(1, p_id);
					stmt3.setInt(2, d_id);
					stmt3.executeUpdate();
					Executions.sendRedirect("view_proj.zul");
				}
			}
			conn.close();
			
		}
		catch(ClassNotFoundException e){}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
