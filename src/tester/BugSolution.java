package tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class BugSolution extends SelectorComposer<Window>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rows postrow;
	
	@Wire
	private Window bugSolutionWindow;
	
	@Wire
	private Row post;
	
	@Wire
	private Textbox txtOpen;
	@Wire
	private Row btnRow;
	@Wire
	private Row txtRow;
	
	
	@Listen("onClick = #btnOpen")
	public void reopenBug(){
		Connection conn;
		Statement st;
		int b_id;
		HashMap<String,Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("bugDetail");
		Bug bug = (Bug) map.get("bugModel");
		b_id = bug.getB_id();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			st = conn.createStatement();
			st.execute("update bug set status='Open' where b_id="+b_id);
			Textbox txtOpen = new Textbox();
			txtOpen.setId("txtOpen");
			txtOpen.setRows(3);
			Button btnPost = new Button();
			btnPost.setId("btnPost");
			btnPost.setLabel("Post");
			Row temprow = new Row();
			temprow.setId("txtRow");
			temprow.setValign("bottom");
			temprow.appendChild(txtOpen);
			temprow.appendChild(btnPost);
			postrow.appendChild(temprow);
			btnRow.setVisible(false);
			doCallCompose(bugSolutionWindow);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Listen("onClick = #btnPost")
	public void reopenPost(){
		System.out.println("hi");
	}

	@Override
	public void doAfterCompose(Window comp) throws Exception {
		// TODO Auto-generated method stub
		postrow = (Rows) comp.getFellow("postrow");
		try{
			Connection conn;
			Statement st,st2;
			ResultSet rs,rs2;
			int b_id;
			HashMap<String,Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("bugDetail");
			Bug bug = (Bug) map.get("bugModel");
			b_id = bug.getB_id();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			st = conn.createStatement();
			rs = st.executeQuery("select u_id,solution from resolve_bug where b_id="+b_id);
			while(rs.next()){
				int u_id = rs.getInt(1);
				String solution = rs.getString(2);
				st2 = conn.createStatement();
				rs2 = st2.executeQuery("select name from user where u_id="+u_id);
				while(rs2.next()){
					String nm = rs2.getString(1);
					Label name = new Label();
					name.setValue(nm);
					Label sol = new Label();
					sol.setValue(solution);
					Row temprow = new Row();
					temprow.appendChild(name);
					temprow.appendChild(sol);
					postrow.appendChild(temprow);
				}
			}
			
			
			rs = st.executeQuery("select status from bug where b_id="+b_id);
			while(rs.next()){
				String status = rs.getString(1);
				if(status.equals("Resolved")){
					Button btnOpen = new Button();
					btnOpen.setId("btnOpen");
					btnOpen.setLabel("Open");
					Button btnClose = new Button();
					btnClose.setId("btnClose");
					btnClose.setLabel("Close");
					Row temprow = new Row();
					temprow.setId("btnRow");
					temprow.appendChild(btnOpen);
					temprow.appendChild(btnClose);
					postrow.appendChild(temprow);
				}
			}
			
			rs.close();
			st.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		doCallCompose(comp);
	}
	
	private void doCallCompose(Window comp) throws Exception{
		super.doAfterCompose(comp);
	}
}
