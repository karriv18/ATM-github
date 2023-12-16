package Machine;
import java.sql.*;
import java.util.Scanner;
public class DBconn {
	// database information 
	private int USER_ID = 0;
	private final String DB_URL = "jdbc:mysql://localhost/db_atm";
	private final String DB_UNAME = "root"; 
	private final String DB_PASS = ""; 
	
	private final String SELECT_USERS = "SELECT * FROM tbl_info;";
	
	private final String SIGN_IN = "INSERT INTO tbl_info "
									+ "(firstName, middleName, lastName, contactNo, birthDate)"
									+ "VALUES (?, ?, ?, ?, ?);";
	
	private final String LOG_IN = "SELECT firstName, lastName, id FROM tbl_info WHERE firstName = ? AND lastName = ?; ";

	private final String IN_BALANCE = "INSERT INTO tbl_balance(balance, userId)"
									+ "VALUES (?, ?);";
	
	private final String SELECT_ID  = "SELECT id FROM tbl_info WHERE firstName = ?; ";

	private Connection conn = null;
	public DBconn() {}
	
	protected Connection getConnection() {
		try {
			conn = DriverManager.getConnection(DB_URL, DB_UNAME, DB_PASS); 
		}catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public void selectUser() {
		try {
			Connection connect = getConnection();
			PreparedStatement ps = connect.prepareStatement(SELECT_USERS);	
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String fname = rs.getString("firstName");
				String lname = rs.getString("lastName");
				System.out.println(fname + " " + lname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// user signing in 
	public void insertUser(Users user) {
		try {
			
			// signing up user 
			Connection connect = getConnection();
			PreparedStatement ps = connect.prepareStatement(SIGN_IN);
			
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getMiddleName()); 
			ps.setString(3, user.getLastName());
			ps.setString(4, user.getContactNo()); 
			ps.setString(5, user.getBirthDate());
			
			ps.executeUpdate();
			
			int id = 0;
			ps = connect.prepareStatement(SELECT_ID);
			ps.setString(1, user.getFirstName());
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				id = rs.getInt("id"); 
			}
			
			// inserting initial balance as 0 for multiple table 
			ps = connect.prepareStatement(IN_BALANCE);
			ps.setInt(1, 0);
			ps.setInt(2, id);
			ps.executeUpdate();
			System.out.println("Successfully Sign In");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	// User validation if user does exist 
	public void validateUser(Users user) {
		
		try {
			Connection connect = getConnection(); 
			PreparedStatement ps = connect.prepareStatement(LOG_IN);
			
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ResultSet rs = ps.executeQuery();
			
			/*
			 * int timeToWait = 10; //second System.out.print("Scanning");
			 */
		     
				/*
				 * try { for (int i=0; i<timeToWait ; i++) { Thread.sleep(1000);
				 * System.out.print("."); } } catch (InterruptedException ie) {
				 * Thread.currentThread().interrupt(); }
				 */
			
			 // recurring login for user the to be re-prompted 
			
			if (!rs.next()) {
				Console cons = new Console();
				try (Scanner scan = new Scanner(System.in)) {
					String ans = "";
					do {
						System.out.println("User cannot found");
						System.out.println("Re-login press 'R'");
						System.out.println("Create account press 'C'");
						
						ans = scan.nextLine();
						
						if (ans.equalsIgnoreCase("R")) {
							cons.Login();
						}
						if (ans.equalsIgnoreCase("C")) {
							cons.SignUp();
						}
						else {
							System.out.println("Invalid " + "gago ");
						}
					}while (!(ans.equalsIgnoreCase("R") || ans.equalsIgnoreCase("C")));
				}
			}
			else {
				Console cons = new Console();
				
				System.out.println("User found");
				
				String fname = rs.getString("firstName");
				String lname = rs.getString("lastName");
				
				System.out.println(fname + " " + lname);
				int ID = rs.getInt("id");
				
				user.setID(ID);
				setUser_ID(user.getID());
				System.out.println(getUser_ID()); 
				cons.navigate(user);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setUser_ID(int User_ID) {
		this.USER_ID = User_ID;
	}
	public int getUser_ID() {
		return this.USER_ID;
	}
	
}
