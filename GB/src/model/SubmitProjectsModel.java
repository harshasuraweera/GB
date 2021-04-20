package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SubmitProjectsModel {
	
	

	//A common method to connect to the DB
	private Connection connect(){
		
		Connection conn = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectservice", "root", "hashini");
		}catch (Exception e){
			e.printStackTrace();
		}
			return conn;
	}
	
	public String submitProjectsInterface() throws SQLException{
		
		String output = "";
		
		
			output +="<form action='../projects/AddProject' method='post'>"
					+ "<input type='text' name='randomProj_ID' value=''>"
					+ "<input type='text' name='Project_Title' value=''>"
					+ "<input type='text' name='Project_ShortDes' value=''>"
					+ "<input type='text' name='Project_LongDes' value=''>"
					+ "<input type='text' name='Project_Srclink' value=''>"
					+ "<input type='text' name='Project_Videolink' value=''>"
					+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Projects'></form>";
			
	
		
	    return output;
			
		
	}
	
	//Add New Projects To the system :
	public String AddProjects(String randomProj_ID,String Project_Title, String Project_ShortDes , String Project_LongDes , String Project_Srclink , String Project_Videolink )
	{
		
		String output = "";
  	  
	    Connection con = connect();

		
		 PreparedStatement pstmt;
	     try {
	    	 String sql = "insert into projects (randomProj_ID, Project_Title, Project_ShortDes, Project_LongDes, Project_Srclink, Project_Videolink) values (?,?,?,?,?,?)";
	    	   	pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    	   
	    	   
	    	 	pstmt.setString(1, randomProj_ID);
	    	 	pstmt.setString(2, Project_Title);
	    	 	pstmt.setString(3, Project_ShortDes);
	    	 	pstmt.setString(4, Project_LongDes);
	    	 	pstmt.setString(5, Project_Srclink);
	    	 	pstmt.setString(6, Project_Videolink);
	    	    
	    	    
	    	 	pstmt.executeUpdate();
	    	    
	    	    
	     }catch (Exception e){
	    	 output += "error";
	    	     e.printStackTrace();
	     }
	
	
		return output;
		
	}
	
	
	//Display All Submitted Projects :
	public String readProjects() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			 {return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Project ID</th>" +
					"<th>Project Title</th>" +
					"<th>Short Discription</th>" +
					"<th>Long Discription</th>" +
					"<th>Src Link</th>" +
					"<th>Video Link</th>" +
					"<th>Update</th><th>Remove</th></tr>";
			
			String queryz = "select * from projects";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(queryz);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				
				String randomProj_ID = rs.getString("randomProj_ID");
				String Project_Title = rs.getString("Project_Title");
				String Project_ShortDes = rs.getString("Project_ShortDes");
				String Project_LongDes = rs.getString("Project_LongDes");
				String Project_Srclink = rs.getString("Project_Srclink");
				String Project_Videolink = rs.getString("Project_Videolink");
				
			// Add into the html table	
				output += "<tr><td>" + randomProj_ID + "</td>";
				output += "<td>" + Project_Title + "</td>"; 
				output += "<td>" + Project_ShortDes + "</td>";
				output += "<td>" + Project_LongDes + "</td>";
				output += "<td>" + Project_Srclink + "</td>";
				output += "<td>" + Project_Videolink + "</td>";
				
			// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					 		+ "<td><form method='post' action='../projects/removeProject'><input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					 + "<input name='productId' type='hidden' value='" + randomProj_ID
					 + "'>" + "</form></td></tr>"; 
			}

		
			
			output += "</table>";
			
		}catch (Exception e) {
			
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	//delete project from system
	public String deleteProjects(String randomProj_ID) {
		
		String output = "";

		try {
			
			Connection con = connect();
			
			// create a prepared statement
			String query = "delete from projects where randomProj_ID=?"; 
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1,randomProj_ID);		
				
			// execute the statement
			preparedStmt.execute();
			con.close(); 
			
			output = "Deleted successfully";
			
			
		}catch (Exception e) {
			
			output = "Error while deleting the item.";
			 System.err.println(e.getMessage());
			
		}
		
	
		return output;
	
	
	}
	

}
