package projectManager;

public class ProjectStatus {
	
	private Project project;
	private boolean status;
	
	public ProjectStatus(Project project,boolean status){
		this.project = project;
		this.status = status;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public Project getProject(){
		return project;
	}

}
