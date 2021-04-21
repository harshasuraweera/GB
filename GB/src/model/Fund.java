package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Fund {
	
	//A common method to connect to the DB
		private Connection connect(){
			
			Connection con = null;
			try{
				Class.forName("com.mysql.cj.jdbc.Driver"); 
				//Provide the correct details: DBServer/DBName, username, password
				con = DriverManager.getConnection("jdbc:mysql://localhost:3030/paymentservice", "root", "admin");
			}catch (Exception e){
				e.printStackTrace();
			}
				return con;
		}
		

		public String insertItem(String Pname, String Pdescription, String Plink, String PVlink, String PComment)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for inserting."; }
		// create a prepared statement
		String query = " insert into project_view(`PVID`,`Pname`,`Pdescription`,`Plink`,`PVlink`,`PComment`)"
		+ " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, Pname);
		preparedStmt.setString(3, Pdescription);
		preparedStmt.setString(4, Plink);
		preparedStmt.setString(5, PVlink);
		preparedStmt.setString(6, PComment);
		// execute the statement

		preparedStmt.execute();
		con.close();
		output = "Inserted successfully";
		}
		catch (Exception e)
		{
		output = "Error while inserting the item.";
		System.err.println(e.getMessage());
		}
		return output;
		}
		
		
		
		public String readItems()
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for reading."; }
		// Prepare the html table to be displayed
		output = "<table border='1'><tr><th>Project Id</th>" +
				"<th>Project Code</th>" +
				"<th>Project Title</th>" +
				"<th>Add To Accepted</th><th>Reject</th></tr>";
		String query = "SELECT * FROM project_manage";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		// iterate through the rows in the result set
		while (rs.next())
		{
			
			
			String PMID = Integer.toString(rs.getInt("PMID")); 
			String PCode = rs.getString("PCode"); 
			String title = rs.getString("title"); 

			 
		// Add into the html table
		output += "<tr><td>" + PMID + "</td>";
		output += "<td>" + PCode + "</td>";
		output += "<td>" + title + "</td>";

		// buttons
		output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
		+ "<td><form method='post' action='"
		+ "#'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
		+ "<input name='PMID' type='hidden' value='" + PMID
		+ "'>" + "</form></td></tr>";
		}
		con.close();
		// Complete the html table
		output += "</table>";
		}
		catch (Exception e)
		{
		output = "Error while reading the items.";
		System.err.println(e.getMessage());
		}
		return output;
		}

		
		public String updateItem(String PVID, String Pname, String Pdescription, String Plink, String PVlink, String PComment)
		
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		// create a prepared statement
		String query = "UPDATE project_view SET Pname=?,Pdescription=?,Plink=?,PVlink=?,PComment=? WHERE PVID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setString(1, Pname);
		preparedStmt.setString(2, Pdescription);
		preparedStmt.setString(3, Plink);
		preparedStmt.setString(4, PVlink);
		preparedStmt.setString(5, PComment);
		preparedStmt.setInt(6, Integer.parseInt(PVID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Updated successfully";
		}
		catch (Exception e)
		{
		output = "Error while updating the item.";
		System.err.println(e.getMessage());
		}
		return output;
		}
		
		
		
		public String deleteItem(String PMID)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
		// create a prepared statement
		String query = "delete from project_manage where PMID= '"+Integer.parseInt(PMID)+"' ";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(PMID));
		// execute the statement
		preparedStmt.execute();
		con.close();
		output = "Deleted successfully";
		}
		catch (Exception e)
		{
		output = "Error while deleting the item.";
		System.err.println(e.getMessage());
		}
		return output;
		}

	}
