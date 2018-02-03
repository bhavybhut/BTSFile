package admin;

public class User {
    private String name;
    private String username;
    private String designation;
    private String email;
    private String pm;
    private String dept;
    private String u_id;
    private String sec_que;
    private String sec_ans;
    private boolean checked;
    
 
    public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String u_id,String name, String username, String designation, String email, String pm, String dept) {
    	this.u_id = u_id;
		setName(name);
    	setUsername(username);
    	setDesignation(designation);
    	setEmail(email);
    	setPm(pm);
    	setDept(dept);
    }
	
	public User(String name,String username,String email,String sec_que,String sec_ans){
		setName(name);
		setUsername(username);
		setEmail(email);
		setSec_ans(sec_ans);
		setSec_que(sec_que);
	}
	
	public User(String u_id,String name){
		this.u_id = u_id;
		setName(name);
	}
	
	public String getSec_que() {
		return sec_que;
	}

	public void setSec_que(String sec_que) {
		this.sec_que = sec_que;
	}

	public String getSec_ans() {
		return sec_ans;
	}

	public void setSec_ans(String sec_ans) {
		this.sec_ans = sec_ans;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(final boolean checked) {
		this.checked = checked;
	}

	public String getId(){
		return u_id;
	}
	
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getDesignation() {
        return designation;
    }
 
    public void setDesignation(String designation) {
        this.designation = designation;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPm() {
        return pm;
    }
 
    public void setPm(String pm) {
        this.pm = pm;
    }
 
    public String getDept() {
        return dept;
    }
 
    public void setDept(String dept) {
        this.dept = dept;
    }
 
}