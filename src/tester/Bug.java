package tester;

import java.sql.Date;

public class Bug {

	private int p_id,d_id,b_id,t_id;
	private String priority,summary,desc,pname,status,version;
	private byte[] img;
	private Date createDate,updateDate,closeDate;
	
	public Bug(int b_id,int p_id,int t_id,String pname,String priority,String summary,String desc,String version,int d_id,String status,byte[] img, Date createDate, Date updateDate, Date closeDate){
		setT_id(t_id);
		this.b_id = b_id;
		setCreateDate(createDate);
		setUpdateDate(updateDate);
		setCloseDate(closeDate);
		setPname(pname);
		setP_id(p_id);
		setD_id(d_id);
		setPriority(priority);
		setSummary(summary);
		setDesc(desc);
		setStatus(status);
		setImg(img);
		setVersion(version);
	}
	
	public Bug(byte[] img){
		this.img = img;
	}
	
	public Bug(int b_id,String pname,String summary,String desc){
		this.b_id = b_id;
		setPname(pname);
		setSummary(summary);
		setDesc(desc);
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getB_id() {
		return b_id;
	}
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public int getP_id() {
		return p_id;
	}
	public void setP_id(int p_id) {
		this.p_id = p_id;
	}
	public int getD_id() {
		return d_id;
	}
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
