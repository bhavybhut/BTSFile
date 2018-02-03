package developer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.ChartsModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zk.ui.Sessions;

import projectManager.Project;

public class BugData {

	private static CategoryModel model;
    static {
    	Connection conn;
    	Statement stmt,stmt2;
    	ResultSet rs,rs2;
    	try{
    		Class.forName("com.mysql.jdbc.Driver");
    		conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
    		HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("projectDetail");
    		Project project = (Project) map.get("projectModel");
    		String p_id = project.getId();
    		stmt = conn.createStatement();
    		rs =stmt.executeQuery("select b_id from bug where p_id="+p_id+" and status='Open'");
    		while(rs.next()){
    			int b_id = rs.getInt(1);
    			stmt2 = conn.createStatement();
    			rs2 = stmt2.executeQuery("select DATE_FORMAT(%d-%m) from bug_log where create_date BETWEEN (CURDATE() - 30 DAY AND CURDATE()) and b_id="+b_id);
    			while(rs2.next()){
    				model = new DefaultCategoryModel();
    				model.setValue("Open", rs2.getDate(1), 1);
    				rs2.getDate(1);
    			}
    		}
    	}catch(Exception e){}
       
        model.setValue("Open", "16-apr", 1);
        model.setValue("Open", "18-apr", 1);
        model.setValue("Open", "23-apr", 3);
        model.setValue("Open", "25-apr", 4);
        model.setValue("Open", "25-apr", 5);
        model.setValue("Open", "27-apr", 7);
        model.setValue("Open", "28-apr", 7);
        model.setValue("Open", "28-apr", 8);
        model.setValue("Open", "29-apr", 10);
        model.setValue("Open", "29-apr", 11);
        model.setValue("Open", "30-apr", 11);
        model.setValue("Open", "30-apr", 12);
        model.setValue("Resolved", "16-apr", 0);
        model.setValue("Resolved", "18-apr", 1);
        model.setValue("Resolved", "23-apr", 2);
        model.setValue("Resolved", "25-apr", 4);
        model.setValue("Resolved", "25-apr", 4);
        model.setValue("Resolved", "27-apr", 5);
        model.setValue("Resolved", "28-apr", 6);
        model.setValue("Resolved", "28-apr", 6);
        model.setValue("Resolved", "29-apr", 7);
        model.setValue("Resolved", "29-apr", 7);
        model.setValue("Resolved", "30-apr", 8);
        model.setValue("Resolved", "30-apr", 10);
    }
	public static ChartsModel getCategoryModel() {
		// TODO Auto-generated method stub
		return model;
	}

}
