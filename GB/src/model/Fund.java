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
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fundingservice", "root", "admin");
			}catch (Exception e){
				e.printStackTrace();
			}
				return con;
		}
		
		
		//Display all projects from database
		public String readItems(){
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for reading."; }
		
		String query = "SELECT * FROM projects";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		
		// Prepare the html table to be displayed
		output += "<table border='1'><tr><th>Project Id</th>" +
						"<th>Project Name</th>" +
						"<th>Project Description</th>" 
						+"<th>View Project</th></tr>";
		
		// iterate through the rows in the result set
		while (rs.next())
		{
			
			
			String Project_Id = Integer.toString(rs.getInt("Project_Id")); 
			String Project_Title = rs.getString("Project_Title"); 
			String Project_ShortDes = rs.getString("Project_ShortDes"); 
			
			
		output +="<div class='col-md-4' style='padding-top: 15px;padding-bottom: 15px;'>";
				
		 
		// Add into the html table
		output += "<tr><td>" + Project_Id + "</td>";
		output += "<td>" + Project_Title + "</td>";
		output += "<td>" + Project_ShortDes + "</td>";

		// buttons
		output += "<td><form method='post' action='../../../GB/fundingService/Fund/ProjectView'>"
				+ "<button type='submit' class='btn btn-primary'>View Projects</button>"
				+ "<input type= 'hidden' name ='ProjectId' value ='"+Project_Id+"'></form></td>"
				+ "</tr>";
		
    	
    	
    	output +="</div>";
		
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
		
		
		
		//Display single project view 
		public String ProjectView(String Project_Id)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for reading."; }
		
		String query = "SELECT * FROM projects where Project_Id ='"+Project_Id+"'" ;
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		// iterate through the rows in the result set
		
		output += "<table border='1'><tr><th>Project Id</th>"
						+ "<th>Project Title</th>" 
						+ "<th>Project Short Description</th>" 
						+ "<th>Project Long Description</th>" 
						+ "<th>Project Source link</th>"
						+ "<th>Project Video link</th>"
						+ "<th>Accept</th>"
						+ "<th>Reject</th>"
						+ "<th>Favourite</th></tr>";
		
		while (rs.next())
		{
			 
			String randomProj_ID = rs.getString("randomProj_ID"); 
			String Project_Title = rs.getString("Project_Title");
			String Project_ShortDes = rs.getString("Project_ShortDes");
			String Project_LongDes = rs.getString("Project_LongDes");
			String Project_Srclink = rs.getString("Project_Srclink");
			String Project_Videolink = rs.getString("Project_Videolink");
			
			
		output +="<div class='col-md-4' style='padding-top: 15px;padding-bottom: 15px;'>"
				+ "<a href='../../../GB/fundingService/Fund'><button type='submit' class='btn btn-primary' >Back To Projects</button></a>";
		
		// Prepare the html table to be displayed
				
		
			 
		// Add into the html table
		output += "<tr><td>" + randomProj_ID + "</td>";
		output += "<td>" + Project_Title + "</td>";
		output += "<td>" + Project_ShortDes + "</td>";
		output += "<td>" + Project_LongDes + "</td>";
		output += "<td>" + Project_Srclink + "</td>";
		output += "<td>" + Project_Videolink + "</td>";

		// buttons
		output += "<td>"
				+ "<form method='post' action='../../../GB/fundingService/Fund/insertComment'>"
				+ "<input type='hidden' value='"+Project_Id+"' name= 'PVID'>"
				+ "<input type='text' name= 'acceptNote' placeholder='Note Here'>"
				+ "<input  type='submit' value='Accept'class='btn btn-secondary'>"
				+ "</form>"
				+ "</td>"
				+ "<td>"
				
			+ "<form action='../../../GB/fundingService/Fund/updateItem' method='post'>"
				+ "<input type='hidden' name=PVID' value="+Project_Id+" >"
				+ "<input type='text' name= 'rejectNote' placeholder='Reason to reject'>"
				+ "<button type='submit'>Reject</button></form>"
			+ "</td>"
				
			+ "<td>"

			+ "<form action='../../../GB/fundingService/Fund/updateItem' method='post'>"
				+ "<input type='hidden' name=PVID' value="+Project_Id+" >"
				+ "<input type='text' name= 'favouriteNote' placeholder='Reason to favourite'>"
				+ "<button type='submit'>favourite</button></form>"
			+ "</td>"

		
		+ "</tr>";
		
	
    	output +="</div>";
		
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
		
		
		
		//Pass data to the accept table
		public String acceptProject(String randomProj_ID, String Project_Title, String Project_ShortDes, String Project_LongDes, String Project_Srclink, String Projrct_Videolink, String Project_AcceptedComment)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for inserting."; }
		// create a prepared statement
		String query = " insert into acceptedprojects( Project_Id , randomProj_ID , Project_Title , Project_ShortDes , Project_LongDes , Project_Srclink , Projrct_Videolink , Project_AcceptedComment) "
				+ "select * from projects( Project_Id , randomProj_ID , Project_Title , Project_ShortDes , Project_LongDes , Project_Srclink , Projrct_Videolink)"
		+ " values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, 0);
		preparedStmt.setString(2, randomProj_ID);
		preparedStmt.setString(3, Project_Title);
		preparedStmt.setString(4, Project_ShortDes);
		preparedStmt.setString(5, Project_LongDes);
		preparedStmt.setString(6, Project_Srclink);
		preparedStmt.setString(7, Projrct_Videolink);
		preparedStmt.setString(8, Project_AcceptedComment);
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
		
		
			
	
		
		
		
		
		
		public String updateItem(String PVID, String PComment)
		
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for updating."; }
		// create a prepared statement
		String query = "UPDATE project_view SET PComment=? WHERE PVID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		
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
		
		
		public String deleteProject(String PVID)
		{
		String output = "";
		try
		{
		Connection con = connect();
		if (con == null)
		{return "Error while connecting to the database for deleting."; }
		// create a prepared statement
		String query = "delete from project_view where PVID= '"+Integer.parseInt(PVID)+"' ";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		// binding values
		preparedStmt.setInt(1, Integer.parseInt(PVID));
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
