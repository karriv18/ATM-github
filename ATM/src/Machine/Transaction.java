package Machine;
import java.sql.*;
import java.util.*;
public class Transaction {
	private final String []Transaction = {"WW", "DD", "PB", "BI", "ST"};
	private final String SET_BALANCE = "UPDATE tbl_balance SET balance = ? WHERE userId = ?";
	private final String SELECT_BALANCE = "SELECT balance FROM tbl_balance WHERE userId = ?;";
	private final String INSERT_TRAN = "INSERT INTO tbl_transaction (transactionDate, userId, transactionType)"
										+ "VALUES(CURDATE(), ?, ?)";
	private DBconn con = new DBconn();
	
	private Connection conn = null;
	private ResultSet rs = null;
	private PreparedStatement ps = null;
	
	private int ID;
	
	public Transaction() {}
	
	public Transaction(Users user) { this.ID = user.getID(); }
	
	public void setID(Users user) { this.ID = user.getID(); }
	
	public int getID() { return this.ID; }
	
	public void setWithdraw(int input, Users user) {
		try {
			Console cons = new Console();
			int UID = user.getID();
			
			/*
			 * if there is no statement on regards to update there will be no execute update
			 * function
			 */
			int balance = selectBalance(UID);
			
			checkBalance(input, balance);
			
			balance -= input;
			System.out.println(UID);
			ps = conn.prepareStatement(SET_BALANCE);
			ps.setInt(1, balance);
			ps.setInt(2, UID);
			
			ps.executeUpdate();
			System.out.println("Success");
			System.out.println("Your new Balance is : " + balance);
			// setting transaction, tagging for database
			setTransaction(UID, Transaction[0]);
			
			cons.navigate(user);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void setDeposit(int input, Users user) {
		try {
			Console cons = new Console();
			int UID = user.getID();

			int balance = selectBalance(UID);
			/*
			 * while(rs.next()) { balance = rs.getInt("balance");
			 * System.out.println(balance); }
			 */
			checkBalance(input, balance);
			balance += input;

			ps = conn.prepareStatement(SET_BALANCE);
			ps.setInt(1, balance);
			ps.setInt(2, UID); 
			
			ps.executeUpdate();
			setTransaction(UID, Transaction[1]);
//			printBalance(user);
			cons.navigate(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void printBalance(Users user) {
		try {
			Console cons = new Console();
			int UID = user.getID();

			int balance = selectBalance(UID);
			
			setTransaction(UID, Transaction[2]);
			
			cons.navigate(user);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	public void payBills(Users user) {
		System.out.println("Hi, " + user.getFirstName() + ". This is still under construction");
	}
	
	public void setTransfer(int amount, int accID, Users user) {
		try {
			Console cons = new Console();
			// this is for the main account transfer
			int balance = selectBalance(user.getID());
			ps = conn.prepareStatement(SET_BALANCE); 
			
			checkBalance(balance, amount);
			
			balance -= amount;
			ps.setInt(1, balance);
			ps.setInt(2, user.getID());
			ps.executeUpdate();
			
			// this is for transferring another account 
			int balanceTransfery = 0;
			balanceTransfery = selectBalance(accID);
			balanceTransfery += amount; 
			
			ps = conn.prepareStatement(SET_BALANCE);
			ps.setInt(1, balanceTransfery);
			ps.setInt(2, accID);
			ps.executeUpdate();
			
			cons.navigate(user);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// setting tag for transaction 
	public void setTransaction(int id, String transactionType) {
		try {
			conn = con.getConnection();
			ps = conn.prepareStatement(INSERT_TRAN);
			
			ps.setInt(1, id);
			ps.setString(2, transactionType);
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// printing balance 
	public int selectBalance(int ID) {
		try {
			conn = con.getConnection();
			ps = conn.prepareStatement(SELECT_BALANCE);
			ps.setInt(1, ID);
			rs = ps.executeQuery();
			
			int balance = 0;
			
			while (rs.next()) {
				balance = rs.getInt("balance");
			}
			return balance;
		}
		catch (Exception E) {	
			E.printStackTrace();
		}
		return 0; 
	}
	
	public void checkBalance(int input, int balance) {
		Console cons = new Console();
		if (input > balance) {
			System.out.println("Invalid, Not enough balance ");
			cons.navigate(null);
		}
	}
}
