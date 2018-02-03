package admin;

public class ViewFilter {
    private String name="", username="", dept="";
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name==null?"":name.trim();
    }
 
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username==null?"":username.trim();
    }
 
    public String getDept() {
        return dept;
    }
 
    public void setDept(String dept) {
        this.dept = dept==null?"":dept.trim();
    }
}