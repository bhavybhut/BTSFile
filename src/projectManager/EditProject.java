package projectManager;

import java.sql.Date;
import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

public class EditProject implements Composer{

	private String pname,p_id;
	private Date stDate,eDate;
	
	private Label id;
	private Textbox name;
	private Datebox startDate;
	private Datebox endDate;
	
	private Label getId(){
		return id;
	}
	public Textbox getName() {
		return name;
	}
	public void setName(Textbox name) {
		this.name = name;
	}
	public Datebox getStartDate() {
		return startDate;
	}
	public void setStartDate(Datebox startDate) {
		this.startDate = startDate;
	}
	public Datebox getEndDate() {
		return endDate;
	}
	public void setEndDate(Datebox endDate) {
		this.endDate = endDate;
	}
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		
		HashMap<String,Object> map = (HashMap<String, Object>) Executions.getCurrent().getArg();
		Project project = (Project) map.get("projectModel");
		
		p_id = project.getId();
		pname = project.getName();
		stDate = project.getStdate();
		eDate = project.getEdate();
		
		id = (Label)comp.getFellow("id");
		name = (Textbox) comp.getFellow("name");
		startDate = (Datebox) comp.getFellow("startDate");
		endDate = (Datebox) comp.getFellow("endDate");
		
		id.setValue(p_id);
		name.setValue(pname);
		startDate.setValue(stDate);
		endDate.setValue(eDate);
	}
	
	
}
