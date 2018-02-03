package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
 
import admin.User;
import admin.ViewFilter;
 
public class ViewUser {
 
    private static List<User> userList = new ArrayList<User>();
    private static void initData() {
        if (!userList.isEmpty())
        	userList.clear();
        PreparedStatement stmt;
		Connection conn;
		ResultSet rs;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("select u_id,name,unm,desig,email,pm,dept from user");
			rs = stmt.executeQuery();
			while(rs.next())
			{
				String id = rs.getString(1);
				String name = rs.getString(2);
				String unm = rs.getString(3);
				String desig = rs.getString(4);
				String email = rs.getString(5);
				String pm = rs.getString(6);
				String dept = rs.getString(7);
				userList.add(new User(id, name, unm, desig, email, pm, dept));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    public static void getDeveloper(){
    	PreparedStatement stmt;
		Connection conn;
		ResultSet rs;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("select u_id,name from user where desig='Developer'");
			rs = stmt.executeQuery();
			userList.clear();
			while(rs.next()){
				String u_id = rs.getString(1);
				String name = rs.getString(2);
				userList.add(new User(u_id,name));
			}
			conn.close();
		}catch(Exception e){}
		
    }
    
    public static List<User> getAllDevelopers(){
    	getDeveloper();
    	return new ArrayList<User>(userList);
    }
    
    public static void getTester(){
    	PreparedStatement stmt;
		Connection conn;
		ResultSet rs;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.prepareStatement("select u_id,name from user where desig='Tester'");
			rs = stmt.executeQuery();
			userList.clear();
			while(rs.next()){
				String u_id = rs.getString(1);
				String name = rs.getString(2);
				userList.add(new User(u_id,name));
			}
			conn.close();
		}catch(Exception e){}
		
    }
    
    public static List<User> getAllTesters(){
    	getTester();
    	return new ArrayList<User>(userList);
    }
    
    public static List<User> getAllUsers() {
    	initData();
        return new ArrayList<User>(userList);
    }
    public static User[] getAllFoodsArray() {
        return userList.toArray(new User[userList.size()]);
    }
 
    // This Method only used in "Data Filter" Demo
    public static List<User> getFilterUsers(ViewFilter userFilter) {
        List<User> someusers = new ArrayList<User>();
        String name = userFilter.getName().toLowerCase();
        String unm = userFilter.getUsername().toLowerCase();
        String dept = userFilter.getDept().toLowerCase();
         
        for (Iterator<User> i = userList.iterator(); i.hasNext();) {
            User tmp = i.next();
            if (tmp.getName().toLowerCase().contains(name) &&
                tmp.getUsername().toLowerCase().contains(unm)  &&
                tmp.getDept().toLowerCase().contains(dept)) {
                someusers.add(tmp);
            }
        }
        return someusers;
    }
}