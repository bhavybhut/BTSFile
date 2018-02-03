package admin;

public class UserStatus {
	
	private User user;
	private boolean editingStatus;
	
	public UserStatus(User user,boolean editingStatus){
		this.user = user;
		this.editingStatus = editingStatus;
	}

	public boolean isEditingStatus() {
		return editingStatus;
	}

	public void setEditingStatus(boolean editingStatus) {
		this.editingStatus = editingStatus;
	}
	 public User getUser(){
		 return user;
	 }

}
