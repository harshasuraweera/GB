package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class user {
	
	//A common method to connect to the DB
	private Connection connect(){
		
		Connection con = null;
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Provide the correct details: DBServer/DBName, username, password 
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/userservice","root","1234");
			
		}
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		return con;///
	}
	
	public String insertuser(String userName, String userEmail, String userMobile, String userPW) {
		String output = "";
		try{
			
			Connection con = connect();
			
			if (con == null) {
				
				return "Error while connecting to the database for inserting."; 
			
			}
			
			// create a prepared statement
			String query = "insert into user( userName, userEmail, userMobile, userPW)"
			+ " values ( ?, ?, ?, ?)";		
			PreparedStatement Stmt;
	        Stmt = con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			
			// binding values
			Stmt.setString(1, userName);
			Stmt.setString(2, userEmail);
			Stmt.setString(3, userMobile);
			Stmt.setString(4, userPW);
			
			// execute the statement
			Stmt.executeUpdate();
			con.close();
			output = "Insert Successfull";
			
			}
		catch(Exception e) {
			
			output = "Error while inserting the users.";
			System.err.println(e.getMessage());
			
		}
		
		return output;
		
		}
	
	public String readuser() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				
				return "Error while connecting to the database for reading.";
			
			}
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>User Name</th><th>User Email</th>" +
					 "<th>User Mobile</th>" + 
					 "<th>User Passwor</th>" +
					 "<th>Update</th><th>Remove</th></tr>"; 
			
			String query = "select * from user";
			Statement stmt = con.createStatement();
			ResultSet rs =  stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String userID = Integer.toBinaryString(rs.getInt("userID"));
				String userName = rs.getString("userName");
				String userEmail = rs.getString("userEmail");
				String userMobile = rs.getString("userMobile");
				String userPW = rs.getString("userPW");
				
				// Add into the html table
				output += "<tr><td>" + userName + "</td>"; 
				 output += "<td>" + userEmail + "</td>"; 
				 output += "<td>" + userMobile + "</td>"; 
				 output += "<td>" + userPW + "</td>"; 
				 
				// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update' class='btn btn-secondary'></td>"+ "<td><form method='post' action='user.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>"
						 + "<input name='userID' type='hidden' value='" + userID 
						 + "'>" + "</form></td></tr>"; 
 			}
			con.close();
			
			// Complete the html table
			 output += "</table>"; 
			 
		}
		catch (Exception e) {
			
			output = "Error while reading the users."; 
			System.err.println(e.getMessage());
			
		}
		
		return output;

		}
	
	public String updateuser(String userID, String userName, String userEmail, String userMobile, String userPW) {
	
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if(con == null) {
				return "Error while connecting to the database for updating.";
			
			}
			
			// create a prepared statement
			String query = "UPDATE user Set userName=?, userEmail=?, userMobile=?, userPW=? WHERE userID=?";
			PreparedStatement preparedsStmt = con.prepareStatement(query);
			
			// binding values
			preparedsStmt.setString(1, userName);
			preparedsStmt.setString(2, userEmail);
			preparedsStmt.setString(3, userMobile);
			preparedsStmt.setString(4, userPW);
			preparedsStmt.setInt(5, Integer.parseInt(userID));
			
			// execute the statement
			preparedsStmt.executeUpdate();
			con.close();
			output = "Updated successfully";
			
		}
		catch(Exception e) {
			
			output = "Error while updating the user."; 
			 System.err.println(e.getMessage()); 
		
		}
		
		return output;
	
	}
	
	public String deleteuser(String userID) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			if(con == null) {
				
				return "Error while connecting to the database for deleting.";
			
			}
			
			// create a prepared statement
			String query = "delete from user where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			con.close(); 
			output = "Deleted successfully"; 
			
		}
		catch(Exception e){
			
			output = "Error while deleting the users."; 
			 System.err.println(e.getMessage()); 
		
		}
		
		return output;
		
	}
		
}
