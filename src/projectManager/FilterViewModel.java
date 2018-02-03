package projectManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import admin.User;
import admin.ViewUser;

 
public class FilterViewModel extends PMBindComposer<Component>{
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String footerMessage = "Total %d Project(s)";
    private ViewFilter projectFilter = new ViewFilter();
    List<Project> currentProject = ViewProject.getAllProjects();
    List<User> developers = ViewUser.getAllDevelopers();
    List<User> testers = ViewUser.getAllTesters();
    private final List<ProjectStatus> projectStatuses = 
			generateStatusList(ViewProject.getAllProjects());
    private boolean displayEdit = true;
    private AssignProject project;
    
    
    private Project pobj = new Project();

    public Project getPobj() {
		return pobj;
	}

	public void setPobj(Project pobj) {
		this.pobj = pobj;
	}

	public boolean isDisplayEdit(){
    	return displayEdit;
    }
    
    @NotifyChange({"projectModel","displayEdit"})
    public void setDisplayEdit(boolean displayEdit){
    	this.displayEdit = displayEdit;
    }
    
    public ListModel<User> getDeveloperModel() {
        return new ListModelList<User>(developers);
    }
    
    @Command
    public void checkDev(final Event e){
    	for(final User u : developers){
    		if(u.isChecked()){
    			project = new AssignProject();
    			project.assignProject(u.getName(),pobj.getName());
    		}
    	}
    }
    
    public ListModel<User> getTesterModel() {
        return new ListModelList<User>(testers);
    }
 
    public ViewFilter getProjectFilter(){
    	return projectFilter;
    }
 
    public ListModel<Project> getProjectModel() {
        return new ListModelList<Project>(currentProject);
    }
     
    public String getFooter() {
        return String.format(footerMessage, currentProject.size());
    }
 
    @Command
    @NotifyChange({"projectModel","footer"})
    public void changeFilter(){
    	currentProject = ViewProject.getFilterProject(projectFilter);
    }
    
    public List<ProjectStatus> getProjectStatus(){
    	return projectStatuses;
    }
    
    @Command
	public void changeEditableStatus(@BindingParam("projectStatus") final ProjectStatus ps) {

		final String CLOSE_EVENT = "close-event";
		
		Messagebox.show("Something is changed. Are you sure?",
			    "Question", Messagebox.YES | Messagebox.NO , Messagebox.QUESTION,
			    new org.zkoss.zk.ui.event.EventListener(){
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_YES.equals(event.getName())){
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("projectModel", ps.getProject());
							Component comp = Executions.createComponents("edit_proj.zul", null, map);
							if(comp instanceof Window){
								((Window)comp).addEventListener(Events.ON_CLOSE, new org.zkoss.zk.ui.event.EventListener<Event>() {

									@Override
									public void onEvent(Event event)
											throws Exception {
										// TODO Auto-generated method stub
										event.getTarget().setAttribute(CLOSE_EVENT, true);
									}
									
								});
								((Window) comp).doModal();
							}
						}
						else if(Messagebox.ON_NO.equals(event.getName())){
							
						}
					}
			
		}
		);
	}
    
    @Command
    public void delete(@BindingParam("projectStatus")final ProjectStatus delps){
    	Messagebox.show("Are you sure you want to delete?","Question",Messagebox.YES|Messagebox.NO,
    			Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener(){

					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_YES.equals(event.getName())){
							InsertData in = new InsertData();
					    	in.deleteProject(delps.getProject());
						}
						else if(Messagebox.ON_NO.equals(event.getName())){}
					}
    		
    	});
    }
    
    @Command
    public void selectProject(@BindingParam("projectStatus")final ProjectStatus ps){
    	Map<String,Object> map = new HashMap<String, Object>();
    	map.put("projectModel", ps.getProject());
    	Sessions.getCurrent().setAttribute("projectDetail", map);
    	Executions.sendRedirect("proj_detail.zul");
    }
    
    public void refreshRowTemplate(ProjectStatus ps) {
		/*
		 * This code is special and notifies ZK that the bean's value
		 * has changed as it is used in the template mechanism.
		 * This stops the entire Grid's data from being refreshed
		 */
		BindUtils.postNotifyChange(null, null, ps, "editingStatus");
	}
    
    private static List<ProjectStatus> generateStatusList(List<Project> projects) {
		List<ProjectStatus> ps = new ArrayList<ProjectStatus>();
		for(Project p : projects) {
			ps.add(new ProjectStatus(p, false));
		}
		return ps;
	}
}