package tester;

public class BugStatus {

	private boolean status;
	private Bug bug;
	
	public BugStatus(boolean status, Bug bug){
		setStatus(status);
		this.bug = bug;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Bug getBug() {
		return bug;
	}
	
}
