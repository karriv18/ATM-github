package Machine;
public class Users {
	private String firstName; 
	private String middleName; 
	private String lastName; 
	private String contactNo; 
	private String birthDate;
	private int ID;
	public Users() {}
	
	public Users(String firstName, String lastName, String contactNo, String birthDate, int ID) {
		super();
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.contactNo = contactNo;
		this.birthDate = birthDate; 
		this.ID = ID; 
	}
	public Users(String firstName, String lastName, int ID) {
		super();
		this.firstName = firstName; 
		this.lastName = lastName;
		this.ID = ID;
	}
	public Users(String firstName, String lastName) {
		super();
		this.firstName = firstName; 
		this.lastName = lastName;
	}
	public Users(String firstName, String lastName, String contactNo, String birthDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.birthDate = birthDate;
	}
	
	public Users(String firstName, String middleName, String lastName, String contactNo, String birthDate) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.contactNo = contactNo;
		this.birthDate = birthDate;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID; 
	}
}
