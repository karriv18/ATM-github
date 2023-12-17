package Machine;
import java.util.Scanner;
public class Console {
	private Transaction transaction = new Transaction();
	private Scanner scanner = new Scanner(System.in);
	private Users p = new Users();
	private final String[] choices = {"1: Withdraw ", 
								"2: Deposit ", 
								"3: Check Balance", 
								"4: Pay Bills", 
								"5: Transfer Funds", 
								"6: Sign out", };
	
	// logging in to your account 
	public void Login() {
		try (Scanner scan = new Scanner(System.in)) {
			DBconn conn = new DBconn();
			System.out.println("Login ");
			
			System.out.print("Enter your first name: ");
			String fname = scan.nextLine();
			
			System.out.print("Enter your last name: ");
			String lname = scan.nextLine();
			
			p = new Users(fname, lname);
			conn.validateUser(p);
		}
	}
	
	// signing up using your account 
	public void SignUp() {
		try  {
			scanner = new Scanner(System.in);
			DBconn conn = new DBconn();
			Users p = new Users();
			
			System.out.println("First   Name    : ");
			String fname = scanner.nextLine();
			
			System.out.println("Middle  Name    : ");
			String mname = scanner.nextLine();
			
			System.out.println("Last    Name    : ");
			String lname = scanner.nextLine();
			
			System.out.println("Contact Number  : ");
			String contNo = scanner.nextLine();
			
			System.out.println("Birthday        : ");
			String bday = scanner.nextLine();
			
			p = new Users(fname, mname, lname, contNo, bday);
			conn.insertUser(p);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public void navigate(Users user) {
		try (Scanner scan = new Scanner(System.in)) {
			printChoices();
			
			int go = 0;
			
			do {
				System.out.print("Navigate from 1 - 6: " + user.getFirstName());
				go = scan.nextInt();
			} 
			while(go <= 0 || go >= 7);
			screen(go, user);
		}
	}
	
	public void screen(int choice, Users user) {
		switch(choice) {
			case 1: 
				gotoWithdraw(user);
				break;
			case 2:
				gotoDeposit(user);
				break;
			case 3: 
				gotoCheckBalance(user);
				break; 
			case 4: 
				gotoPayBills(user);
				break;
			case 5: 
				gotoTransfer(user);
				break;
			case 6:
				gotoSignOut();
				break;
		}	
	}
	
	private void gotoDeposit(Users user) {
		
		System.out.println("Deposit");
		
		System.out.print("How much you want to deposit? : ");
		int deposit = scanner.nextInt();
		
		transaction.setDeposit(deposit, user);
	}
	private void gotoWithdraw(Users user) {
		System.out.println("Withdraw");
		
		System.out.print("How much you want to withdraw? : ");
		int width = scanner.nextInt();
		transaction.setWithdraw(width, user);
		
	}
	private void gotoCheckBalance(Users user) {
		System.out.println("Your Balance is");
		transaction.printBalance(user);
	}

	private void gotoPayBills(Users user) {
		System.out.println("Paybills");
		
		transaction.payBills(user);
	}
	private void gotoTransfer(Users user) {
		System.out.println("Transfer");
		System.out.println("Enter account ID: ");
	
		int accID = scanner.nextInt();
		
		System.out.println("Transfer ammount: ");
		int amount = scanner.nextInt();
		transaction.setTransfer(amount, accID, user);
	}
	private void gotoSignOut() {
		System.out.println("Sign Out");
		Login();
	}
	
	private void printChoices() {
		for (String choice : choices) {
			System.out.println(choice);
		}
		
	}
}
