package Machine;
import java.sql.*;
import java.util.*;
public class Transaction {
//	private final String GET_ID  = "SELECT id FROM tbl_info WHERE firstName = ?;";
	private final String SET_BALANCE = "UPDATE tbl_balance SET balance = ? WHERE userId = ?";
	private final String SELECT_BALANCE = "SELECT balance FROM tbl_balance WHERE userId = ?;";
	private DBconn con = new DBconn();
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
//	private final String ID = "SELECT id FROM tbl_info WHERE firstname = ?";
	private int ID;
	
	public Transaction() {}
	
	public Transaction(Users user) { this.ID = user.getID(); }
	
	public void setID(Users user) { this.ID = user.getID(); }
	
	public int getID() { return this.ID; }
	
	public void setWithdraw(int input, Users user) {
		try {
			int UID = user.getID();
			conn = con.getConnection();
			ps = conn.prepareStatement(SELECT_BALANCE);
			ps.setInt(1, UID);
			
			/*
			 * if there is no statement on regards to update there will be no execute update
			 * function
			 */
			
			
			rs = ps.executeQuery();
			int balance = 0;
			
			while (rs.next()) {
				balance = rs.getInt("balance");
				System.out.println(balance + "balance");
			}
			
			balance -= input;
			System.out.println(UID);
			ps = conn.prepareStatement(SET_BALANCE);
			ps.setInt(1, balance);
			ps.setInt(2, UID);
			
			ps.executeUpdate();
			System.out.println("Success");
			System.out.println("Your new Balance is : " + balance);
			
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void setDeposit(int input, Users user) {
		try {
			
			int UID = user.getID();
			System.out.println(user.getID() + "UID");
			conn = con.getConnection();

			ps = conn.prepareStatement(SELECT_BALANCE);
			ps.setInt(1, UID);
			rs = ps.executeQuery();
			
			int balance = 0;
			while(rs.next()) {
				balance = rs.getInt("balance");
			}
			/*
			 * while(rs.next()) { balance = rs.getInt("balance");
			 * System.out.println(balance); }
			 */

			balance += input;

			ps = conn.prepareStatement(SET_BALANCE);
			ps.setInt(1, balance);
			ps.setInt(2, UID); 
			
			ps.executeUpdate();

//			printBalance(user);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int printBalance(Users user) {
		try {
			int UID = user.getID();
			conn = con.getConnection();
			ps = conn.prepareStatement(SELECT_BALANCE);
			ps.setInt(1, UID);

			rs = ps.executeQuery();
			int balance = 0;
			
			while (rs.next()) {
				balance = rs.getInt("balance");
				System.out.println(balance);
			}
			
			return balance;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	public void payBills(Users user) {
		System.out.println("Hi, " + user.getFirstName() + ". This is still under construction");
	}
	
	public void setTransfer(int balance, Users user) {
		try {
			Scanner scan = new Scanner(System.in);
			
			conn = con.getConnection();
			ps = conn.prepareStatement(SELECT_BALANCE);
			ps.setInt(1, balance);
		}catch(Exception e) {
			
		}
	}
}
