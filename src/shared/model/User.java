package shared.model;

/**
 * 
 * @author tchambs
 * 
 */
public class User {

	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private int recordsIndexed;
	private int selectedImage; //image that the user has checked out
	
	/**
	 * 
	 * @param id the id of the user
	 * @param username username of the user
	 * @param password password of the user
	 * @param firstName user's first name
	 * @param lastName user's last name
	 * @param email user's email
	 * @param recordsIndexed the number of records the user has indexed
	 * @param selectedImage id of the image the user has checked out
	 */
	User(int id, String username, String password, String firstName, String lastName, 
			String email, int recordsIndexed, int selectedImage) {
		setId(id);
		setUsername(username);
		setPassword(password);
		setFirstName(firstName);
		setLastName(lastName);
		setEmail(email);
		setRecordsIndexed(recordsIndexed);
		setSelectedImage(selectedImage);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getRecordsIndexed() {
		return recordsIndexed;
	}
	public void setRecordsIndexed(int recordsIndexed) {
		this.recordsIndexed = recordsIndexed;
	}
	public int getSelectedImage() {
		return selectedImage;
	}
	public void setSelectedImage(int selectedImage) {
		this.selectedImage = selectedImage;
	}
	
	
	
}
