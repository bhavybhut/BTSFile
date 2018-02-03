package projectManager;

import java.sql.Date;



public class Project {

	private String name;
	private Date stdate;
	private Date edate;
	private String p_id;
	
	public Project(String p_id, String name, Date sdate, Date edate) {
		// TODO Auto-generated constructor stub
		this.p_id = p_id;
		setName(name);
		setStdate(sdate);
		setEdate(edate);
	}
	
	public Project(){}

	public String getId(){
		return p_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStdate() {
		return stdate;
	}

	public void setStdate(Date stdate) {
		this.stdate = stdate;
	}

	public Date getEdate() {
		return edate;
	}

	public void setEdate(Date edate) {
		this.edate = edate;
	}

	
}
