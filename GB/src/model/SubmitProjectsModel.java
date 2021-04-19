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
			Class.forName("com.mysql.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectservice", "root", "hashini");
		}catch (Exception e){
			e.printStackTrace();
		}
			return conn;
	}
	
	public String submitProjectsInterface() throws SQLException{
		
		String output = "";
		
		
			output +="<form action='../projects/AddProject' method='post'>"
					+ "<input type='text' name='projectId' value=''>"
					+ "<input type='text' name='Projecttitle' value=''>"
					+ "<input type='text' name='ShortDesc' value=''>"
					+ "<input type='text' name='LongDesc' value=''>"
					+ "<input type='text' name='srcLink' value=''>"
					+ "<input type='text' name='videoLink' value=''>"
					+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Projects'></form>";
			
	
		
	    return output;
			
		
	}
	
	public String readProjects() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			 {return "Error while connecting to the database for reading."; }
			
			// Prepare the html table to be displayed
			output = "<table border='1'><tr><th>Item Code</th><th>randomProj_ID</th>" +
					"<th>Project Title</th>" +
					"<th>Short Discription</th>" +
					"<th>Long Discription</th>" +
					"<th>Src Link</th>" +
					"<th>Video Link</th>" +
					"<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from projects";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				
				String randomProj_ID = Integer.toString(rs.getInt("randomProj_ID"));
				String Project_Title = Integer.toString(rs.getInt("Project_Title"));
				String Project_ShortDes = Integer.toString(rs.getInt("Project_ShortDes"));
				String Project_LongDes = Integer.toString(rs.getInt("Project_LongDes"));
				String Project_Srclink = Integer.toString(rs.getInt("Project_Srclink"));
				String Project_Videolink = Integer.toString(rs.getInt("Project_Videolink"));
				
			// Add into the html table	
				output += "<tr><td>" + randomProj_ID + "</td>";
				output += "<td>" + Project_Title + "</td>"; 
				output += "<td>" + Project_ShortDes + "</td>";
				output += "<td>" + Project_LongDes + "</td>";
				output += "<td>" + Project_Srclink + "</td>";
				output += "<td>" + Project_Videolink + "</td>";
				
			// buttons
				 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
					 		+ "<td><form method='post' action='load'><input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
					 + "<input name='productId' type='hidden' value='" + randomProj_ID
					 + "'>" + "</form></td></tr>"; 
			}

			con.close();
			
			output += "</table>";
			
		}catch (Exception e) {
			
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	


}
