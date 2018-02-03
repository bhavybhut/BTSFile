package projectManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Label;

import admin.User;

public class ProjectDetail implements Composer{
	
	@Wire
	private Label d_id;
	@Wire
	private Label dname;
	
	public Label getD_id() {
		return d_id;
	}

	public void setD_id(Label d_id) {
		this.d_id = d_id;
	}

	public Label getDname() {
		return dname;
	}

	public void setDname(Label dname) {
		this.dname = dname;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("projectDetail");
		Project project = (Project) map.get("projectModel");
		d_id = (Label) comp.getFellow("d_id");
		dname = (Label) comp.getFellow("dname");
		int id;
		
		Connection conn;
		Statement stmt,stmt1;
		ResultSet rs,rs1;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select d_id from assign_project where p_id="+project.getId());
			while(rs.next()){
				id = rs.getInt(1);
				stmt1 = conn.createStatement();
				rs1 = stmt1.executeQuery("select name from user where u_id="+id);
				while(rs1.next()){
					String name = rs1.getString(1);
					d_id.setValue(String.valueOf(id));
					dname.setValue(name);
				}
			}
		}catch(Exception e){}
	}
	

}
