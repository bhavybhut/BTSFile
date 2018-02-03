package developer;

public class ViewFilter {

	private String priority,pname;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname==null?"":pname.trim();
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority==null?"":priority.trim();
	}
	
}
