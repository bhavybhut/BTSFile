package admin;

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
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
 
import admin.User;
import admin.ViewUser;
 
public class FilterViewModel implements Composer{
 
    private static final String footerMessage = "Total %d Users";
    private ViewFilter userFilter = new ViewFilter();
    List<User> currentUser = ViewUser.getAllUsers();
    private final List<UserStatus> userStatuses = 
			generateStatusList(ViewUser.getAllUsers());
    private boolean displayEdit = true;
    User user = new User();
    
    public boolean isDisplayEdit(){
    	return displayEdit;
    }
    
    @NotifyChange({"userModel","displayEdit"})
    public void setDisplayEdit(boolean displayEdit){
    	this.displayEdit = displayEdit;
    }
    
    public List<UserStatus> getUserStatus(){
    	return userStatuses;
    }
    
    @Command
	public void changeEditableStatus(@BindingParam("userStatus") final UserStatus us) {

		final String CLOSE_EVENT = "close-event";
		
		Messagebox.show("Something is changed. Are you sure?",
			    "Question", Messagebox.YES | Messagebox.NO , Messagebox.QUESTION,
			    new org.zkoss.zk.ui.event.EventListener(){
					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_YES.equals(event.getName())){
							Map<String,Object> map = new HashMap<String,Object>();
							map.put("userModel", us.getUser());
							Component comp = Executions.createComponents("edit_user.zul", null, map);
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
    public void delete(@BindingParam("userStatus") final UserStatus delus){
    	Messagebox.show("Are you sure you want to delete?","Question",Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
    			new org.zkoss.zk.ui.event.EventListener(){

					@Override
					public void onEvent(Event event) throws Exception {
						// TODO Auto-generated method stub
						if(Messagebox.ON_YES.equals(event.getName())){
							JdbcComposer composer = new JdbcComposer();
							composer.deleteUser(delus.getUser());
						}
						else if(Messagebox.ON_NO.equals(event.getName())){}
					}
    		
    	});
    	
    }
    
    @Command
	public void confirm(@BindingParam("userStatus") UserStatus us) {
		changeEditableStatus(us);
		refreshRowTemplate(us);
	}
    
    public void refreshRowTemplate(UserStatus us) {
		/*
		 * This code is special and notifies ZK that the bean's value
		 * has changed as it is used in the template mechanism.
		 * This stops the entire Grid's data from being refreshed
		 */
		BindUtils.postNotifyChange(null, null, us, "editingStatus");
	}
    
    private static List<UserStatus> generateStatusList(List<User> users) {
		List<UserStatus> us = new ArrayList<UserStatus>();
		for(User u : users) {
			us.add(new UserStatus(u, false));
		}
		return us;
	}
    
    @Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		
	}
 
    public ViewFilter getUserFilter() {
        return userFilter;
    }
 
    public ListModel<User> getUserModel() {
        return new ListModelList<User>(currentUser);
    }
     
    public String getFooter() {
        return String.format(footerMessage, currentUser.size());
    }
 
    @Command
    @NotifyChange({"userModel", "footer"})
    public void changeFilter() {
        currentUser = ViewUser.getFilterUsers(userFilter);
    }

}