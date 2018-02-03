package tester;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.zkoss.image.AImage;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class BugDetails implements Composer{

	private Label summary,desc,pname,b_id,devName,testerName,cdate,udate,cldate,version;
	private Image img;
	private Row row,udateRow,cldateRow;
	private String summ,description,ver;
	private int id,d_id,t_id;
	private byte[] image;
	private String projname;
	private Date createDate,updateDate,closeDate;
	
	public Row getUdateRow() {
		return udateRow;
	}

	public void setUdateRow(Row udateRow) {
		this.udateRow = udateRow;
	}

	public Row getCldateRow() {
		return cldateRow;
	}

	public void setCldateRow(Row cldateRow) {
		this.cldateRow = cldateRow;
	}

	public Label getUdate() {
		return udate;
	}

	public void setUdate(Label udate) {
		this.udate = udate;
	}

	public Label getCldate() {
		return cldate;
	}

	public void setCldate(Label cldate) {
		this.cldate = cldate;
	}

	public Label getVersion() {
		return version;
	}

	public void setVersion(Label version) {
		this.version = version;
	}

	public Label getCdate() {
		return cdate;
	}

	public void setCdate(Label cdate) {
		this.cdate = cdate;
	}

	public Label getTesterName() {
		return testerName;
	}

	public void setTesterName(Label testerName) {
		this.testerName = testerName;
	}

	public Label getDevName() {
		return devName;
	}

	public void setDevName(Label devName) {
		this.devName = devName;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	public Label getB_id() {
		return b_id;
	}

	public Label getSummary() {
		return summary;
	}

	public void setSummary(Label summary) {
		this.summary = summary;
	}

	public Label getDesc() {
		return desc;
	}

	public void setDesc(Label desc) {
		this.desc = desc;
	}

	public Label getPname() {
		return pname;
	}

	public void setPname(Label pname) {
		this.pname = pname;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		Connection conn;
		Statement stmt,stmt2;
		ResultSet rs,rs2;
		String dname,tname;
		HashMap<String,Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("bugDetail");
		Bug bug = (Bug) map.get("bugModel");
		
		summ = bug.getSummary();
		description = bug.getDesc();
		projname = bug.getPname();
		id = bug.getB_id();
		image = bug.getImg();
		d_id = bug.getD_id();
		t_id = bug.getT_id();
		ver = bug.getVersion();
		
		summary = (Label) comp.getFellow("summary");
		desc = (Label) comp.getFellow("desc");
		pname = (Label) comp.getFellow("pname");
		b_id = (Label) comp.getFellow("b_id");
		img = (Image) comp.getFellow("img");
		row = (Row) comp.getFellow("imgrow");
		devName = (Label) comp.getFellow("devName");
		testerName = (Label) comp.getFellow("testerName");
		cdate = (Label) comp.getFellow("cdate");
		udate = (Label) comp.getFellow("udate");
		cldate = (Label) comp.getFellow("cldate");
		version = (Label) comp.getFellow("version");
		udateRow = (Row) comp.getFellow("udateRow");
		cldateRow = (Row) comp.getFellow("cldateRow");
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/BTS?user=root&password=password");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select name from user where u_id="+d_id);
			while(rs.next()){
				dname = rs.getString(1);
				summary.setValue(summ);
				desc.setValue(description);
				pname.setValue(projname);
				b_id.setValue(String.valueOf(id));
				devName.setValue(dname);
				version.setValue(ver);
			}
			stmt.close();
			rs.close();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select name from user where u_id="+t_id);
			while(rs.next()){
				tname = rs.getString(1);
				testerName.setValue(tname);
			}
			stmt.close();
			rs.close();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select create_date,update_date,close_date from bug_log where b_id="+id);
			while(rs.next()){
				createDate = rs.getDate(1);
				cdate.setValue(String.valueOf(createDate));
				stmt2 = conn.createStatement();
				System.out.println("1");
				rs2 = stmt2.executeQuery("select status from bug where b_id="+id);
				while(rs2.next()){
					String status = rs2.getString(1);
					System.out.println(status);
					if(status.equals("Resolved")){
						
						updateDate = rs.getDate(2);
						System.out.println(updateDate);
						udate.setValue(String.valueOf(updateDate));
						cldateRow.setVisible(false);
					}
					if(status.equals("Closed")){
						
						closeDate = rs.getDate(3);
						cldate.setValue(String.valueOf(closeDate));
						udateRow.setVisible(false);
					}
				}
			}
			conn.close();
		}catch(Exception e){e.printStackTrace();}
		
		try{
			AImage ai = new AImage("Image",image);
			img.setContent(ai);
		}
		catch (Exception e) {
			row.setVisible(false);
		}
	}

}