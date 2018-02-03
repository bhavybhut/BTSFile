package admin;

import java.util.HashMap;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Composer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;


public class EditUser implements Composer{

	private String id,nm,usernm,design,mail,projmgr,depart;
	private Label u_id;
	private Textbox name;
	private Textbox unm;
	private Combobox desig;
	private Textbox email;
	private Textbox pm;
	private Textbox dept;
	
	
	public Label getId(){
		return u_id;
	}
	
	public Textbox getName() {
		return name;
	}


	public void setName(Textbox name) {
		this.name = name;
	}


	public Textbox getUnm() {
		return unm;
	}


	public void setUnm(Textbox unm) {
		this.unm = unm;
	}


	public Combobox getDesig() {
		return desig;
	}


	public void setDesig(Combobox desig) {
		this.desig = desig;
	}


	public Textbox getEmail() {
		return email;
	}


	public void setEmail(Textbox email) {
		this.email = email;
	}


	public Textbox getPm() {
		return pm;
	}


	public void setPm(Textbox pm) {
		this.pm = pm;
	}


	public Textbox getDept() {
		return dept;
	}


	public void setDept(Textbox dept) {
		this.dept = dept;
	}


	@Override
	public void doAfterCompose(Component comp) throws Exception {
		// TODO Auto-generated method stub
		HashMap<String,Object> map = (HashMap<String,Object>) Executions.getCurrent().getArg();
		User user = (User)map.get("userModel");
		id = user.getId();
		nm = user.getName();
		usernm = user.getUsername();
		design = user.getDesignation();
		mail = user.getEmail();
		projmgr = user.getPm();
		depart = user.getDept();
		
		u_id = (Label)comp.getFellow("u_id");
		name = (Textbox)comp.getFellow("name");
		unm = (Textbox)comp.getFellow("unm");
		desig = (Combobox) comp.getFellow("desig");
		email = (Textbox)comp.getFellow("email");
		pm = (Textbox)comp.getFellow("pm");
		dept = (Textbox)comp.getFellow("dept");
		
		u_id.setValue(id);
		name.setValue(nm);
		unm.setValue(usernm);
		Comboitem item = getSelectedIndexComboboxItem(desig, design);
		desig.setSelectedItem(item);
		email.setValue(mail);
		pm.setValue(projmgr);
		dept.setValue(depart);
	}
	private Comboitem getSelectedIndexComboboxItem(Combobox combobox, String value) {
        List<Comboitem> items = combobox.getItems();
        Comboitem item = items.get(0);
        for (int i = 0; i < items.size(); i++) {
            Comboitem comboitem = items.get(i);
            String label = (String)comboitem.getLabel();
            String cval = (String)comboitem.getValue();
            if ((label!=null && label.equalsIgnoreCase(value)) || (cval != null  && cval.equalsIgnoreCase(value))) {
                item = comboitem;
                break;
            }
        }
        return item;
    }

	
}
