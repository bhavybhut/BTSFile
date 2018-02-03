package tester;

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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;


public class FilterViewModel implements Composer{

	private static final String footerMessage = "Total %d bugs";
	private ViewFilter bugFilter = new ViewFilter();
	private List<Bug> currentBug = ViewBugs.getAllBugs();
	private final List<BugStatus> bugStatuses = generateStatusList(ViewBugs.getAllBugs());
	
	public ViewFilter getBugFilter(){
		return bugFilter;
	}
	
	public ListModel<Bug> getBugModel(){
		return new ListModelList<Bug>(currentBug);
	}
	
	public String getFooter(){
		return String.format(footerMessage, currentBug.size());
	}
	
	public List<BugStatus> getBugStatus(){
		return bugStatuses;
	}
	
	@Command
	public void viewBugDetail(@BindingParam("bugStatus") final BugStatus bs){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("bugModel", bs.getBug());
			Sessions.getCurrent().setAttribute("bugDetail", map);
			Executions.sendRedirect("bug_details.zul");
	}
	
	 public void refreshRowTemplate(BugStatus bs) {
			/*
			 * This code is special and notifies ZK that the bean's value
			 * has changed as it is used in the template mechanism.
			 * This stops the entire Grid's data from being refreshed
			 */
			BindUtils.postNotifyChange(null, null, bs, "editingStatus");
	}
	    
	private static List<BugStatus> generateStatusList(List<Bug> bugs) {
		List<BugStatus> bs = new ArrayList<BugStatus>();
		for(Bug b : bugs) {
			bs.add(new BugStatus(false,b));
		}
		return bs;
	}
	
	@Command
	@NotifyChange({"bugModel","footer"})
	public void changeFilter(){
		currentBug = ViewBugs.getFilterBug(bugFilter);
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		
	}

	
}
