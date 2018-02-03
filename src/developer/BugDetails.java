package developer;

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
import tester.Bug;

public class BugDetails implements Composer{

	private Label summary,desc,pname,b_id,devName,testerName,cdate,version;
	private Image img;
	private Row imgrow;
	private String summ,description,ver;
	private int id,d_id,t_id;
	private byte[] image;
	private String projname;
	private Date createDate;
	
	public Label getVersion() {
		return version;
	}

	public void setVersion(Label version) {
		this.version = version;
	}

	public Label getTesterName() {
		return testerName;
	}

	public void setTesterName(Label testerName) {
		this.testerName = testerName;
	}

	public Label getCdate() {
		return cdate;
	}

	public void setCdate(Label cdate) {
		this.cdate = cdate;
	}

	public Label getDevName() {
		return devName;
	}

	public void setDevName(Label devName) {
		this.devName = devName;
	}

	public Row getImgrow() {
		return imgrow;
	}

	public void setImgrow(Row imgrow) {
		this.imgrow = imgrow;
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
		Statement stmt;
		ResultSet rs;
		String dname,tname;
		HashMap<String,Object> map = (HashMap<String, Object>) Sessions.getCurrent().getAttribute("bugDetail");
		Bug bug = (Bug) map.get("bugModel");
		
		summ = bug.getSummary();
		description = bug.getDesc();
		projname = bug.getPname();
		id = bug.getB_id();
		image = bug.getImg();
		createDate = bug.getCreateDate();
		d_id = bug.getD_id();
		t_id = bug.getT_id();
		ver = bug.getVersion();
		
		summary = (Label) comp.getFellow("summary");
		desc = (Label) comp.getFellow("desc");
		pname = (Label) comp.getFellow("pname");
		b_id = (Label) comp.getFellow("b_id");
		img = (Image) comp.getFellow("img");
		imgrow = (Row) comp.getFellow("imgrow");
		devName = (Label) comp.getFellow("devName");
		testerName = (Label)comp.getFellow("testerName");
		cdate = (Label) comp.getFellow("cdate");
		version = (Label)comp.getFellow("version");
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
			rs = stmt.executeQuery("select create_date from bug_log where b_id="+id);
			while(rs.next()){
				createDate = rs.getDate(1);
				cdate.setValue(String.valueOf(createDate));
			}
			conn.close();
		}catch(Exception e){}
		
		try{
			AImage ai = new AImage("Image",image);
			img.setContent(ai);
		}catch(Exception e){
			imgrow.setVisible(false);
		}
		
	}
}

