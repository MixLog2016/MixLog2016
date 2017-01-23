
import javax.swing.JOptionPane;
import javax.annotation.processing.Messager;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class MySql 
{
	
	   public static boolean check = true;
	   public static int count;
	   // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	   static final String connectionURL="jdbc:mysql://localhost/busticketproject";

	   //  Database credentials
	   static final String usernamefordatabase = "root";
	   static final String passwordfordatabase = "";
	   
	  
	
	//print table inside admin panel.
	public static DefaultTableModel PrintTableDB(DefaultTableModel model)
	{
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DriverManager.getConnection(connectionURL,usernamefordatabase,passwordfordatabase);
			stmt = conn.createStatement();
			ResultSet  rs = stmt.executeQuery("SELECT * FROM user");
			while(rs.next())
			{
				String id = rs.getString("ID");
			    String d = rs.getString("username");
			    String e = rs.getString("password");
			    String f = rs.getString("accessLevel");
			    model.addRow(new Object[]{id,d, e, f});
			    
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return model;
		
	}
	
	
	
	//verify if the given input matches any row inside the db
	public static void VerifyLogin(String username,String Password)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
		
			Connection conn = null;
			Statement stmt = null;
			
			conn = DriverManager.getConnection(connectionURL,usernamefordatabase,passwordfordatabase);
			stmt = conn.createStatement();
			ResultSet  rs = stmt.executeQuery("SELECT * FROM user");
			
			while(rs.next())
			{
				
				String nameFromDb= rs.getString("username").toString().trim().toLowerCase();
				String passwordFromDb = rs.getString("password").toString().trim().toLowerCase();
				int accesslvl = rs.getInt("accessLevel");
				LoginScreen login = new LoginScreen();
				if ((username.equals(nameFromDb)) && (passwordFromDb.equals(Password) ))
				{
					
					if ( (accesslvl == 1) )
					{
						
						AdminScreen.AdminScreen();
						check = true;
						break;
					}
					else
					{
						login.setVisible(false);
						check = true;
						CashierScreen.CashierScreen();
						break;
						
					}
					
				}
				else
				{
					check=false;
				}
				
				
				
			}
			
			if (!check)
			{
				JOptionPane.showMessageDialog(null, "Wrong Username && || Password.\nPlease Try Again.");
				check = false;
				
			}
	
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static boolean LoggedIN(){
		return check;
	}
	
	//check if there was a problem with inserting a user to the db
	public static boolean success(){
		boolean success1 = false;
		if (count>0){
			boolean success11 = true;
			return success11;
		}
		else{
			return success1;
		}
		
	}
	
	
	
	public static void UpdateDB(String Username,String Password, int AccessLVL) 
	{
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = null;
			Statement stmt = null;
			
			conn = DriverManager.getConnection(connectionURL,usernamefordatabase,passwordfordatabase);
			stmt = conn.createStatement();
			String sql = "INSERT INTO user " +
	                   "VALUES (NULL, '"+Username+"','"+Password+"',"+AccessLVL+")";
			count = stmt.executeUpdate(sql);
			
			
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}



	public static int getnextTicketNo() {
		int lastNumber = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			Connection conn = null;
			Statement stmt = null;
			
			conn = DriverManager.getConnection(connectionURL,usernamefordatabase,passwordfordatabase);
			stmt = conn.createStatement();
			ResultSet  rs = stmt.executeQuery("SELECT ticketNumber FROM tickets ORDER BY ticketNumber DESC LIMIT 1");
			if (rs.next()){
				lastNumber = rs.getInt("ticketNumber");
				return lastNumber;
			}
		}catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lastNumber;
		
	}



	public static void saveTickets(String Name, String lastName, String certificate, int ticketNumber,Date retDate, Date departDate,boolean receiptValue){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			Statement stmt = null;
			conn = DriverManager.getConnection(connectionURL,usernamefordatabase,passwordfordatabase);
			stmt = conn.createStatement();
			
			String sql2 = "INSERT INTO tickets " +
	                   "VALUES (NULL, '"+Name+"','"+lastName+"','"+certificate+"',"+ticketNumber+",'"+retDate+"','"+departDate+"',"+receiptValue+")";
			count = stmt.executeUpdate(sql2);
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
