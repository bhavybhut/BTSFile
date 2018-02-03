package projectManager;

public class Assign {

	private int p_id,d_id,t_id;
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	Assign(int p_id,int d_id,int t_id){
		setD_id(d_id);
		setP_id(p_id);
		setT_id(t_id);
	}
	Assign(int p_id,int d_id){
		setD_id(d_id);
		setP_id(p_id);
	}

	public Assign() {
		// TODO Auto-generated constructor stub
	}
	public int getP_id() {
		return p_id;
	}

	public void setP_id(int p_id) {
		this.p_id = p_id;
	}

	public int getD_id() {
		return d_id;
	}

	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	public int getT_id() {
		return t_id;
	}

	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	
	
}
