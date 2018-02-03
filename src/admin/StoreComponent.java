package admin;

import org.zkoss.zk.ui.Component;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;

public class StoreComponent extends GenericForwardComposer
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Combobox desig;
	private Image img;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception
	{
		super.doAfterCompose(comp);
		comp.getFellowIfAny("desig");
		comp.getFellowIfAny("img");
	}
}
