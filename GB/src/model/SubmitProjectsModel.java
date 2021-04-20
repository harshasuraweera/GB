package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


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
	
	
	public static String generateProjectId() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        
        return  "PROJ-" + saltStr;

    }

	
	public String submitProjectsInterface() throws SQLException{
	
		String Project_Id = generateProjectId();
		
		String output = "";
		
			output +="<center><label>- Add New Project -</label><form action='../../../GB/projectService/projects/AddProject' method='post'><br><br>"
					+ "<input type='text' name='randomProj_ID' value='"+Project_Id+"' readonly><br><br>"
					+ "<input type='text' name='Project_Title' required onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\"><br><br>"
					+ "<textarea class=\"form-control\" name=\"Project_ShortDes\" style=\"height: 170px;\" maxlength=\"100\" placeholder=\"Should be less than 250 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea><br><br>"
					+ "<textarea class=\"form-control\" name=\"Project_LongDes\" style=\"height: 170px;\" maxlength=\"250\" placeholder=\"Should be less than 250 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea><br><br>"
					//+ "<input type='text' name='Project_ShortDes'  required maxlength=\"2\" placeholder=\"Should be less than 250 letters\"><br><br>"
					+ "<input type='text' name='Project_Srclink' required required placeholder=\\\"https://example.com\\\" pattern=\\\"https://.*\\\" ><br><br>"
					+ "<input type='text' name='Project_Videolink' value='' required required placeholder=\\\"https://example.com\\\" pattern=\\\"https://.*\\\" ><br><br>"
					+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Projects'></form></center><br><br>";
			
	
		
	    return output;
			
		
	}
	
	//Add New Projects To the system :
	public String AddProjects(String randomProj_ID,String Project_Title, String Project_ShortDes , String Project_LongDes , String Project_Srclink , String Project_Videolink )
	{
		
		String output = "<a href='../../../GB/projectService/projects/readProjects'></a>";
  	  
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
		+ "<td><form action='../../../GB/projectService/projects/removeProject' method='post'><input type='hidden' name='randomProj_ID' value="+randomProj_ID+" ><button type='submit'>Remove</button>" + 
		"</form></td></tr>"; 
			}

		
			
			output += "</table>";
			
		}catch (Exception e) {
			
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	
	//delete project from system
	public String deleteProjects( String randomProj_ID) {
		
		
		String output = "";
		Connection conn = connect();
		String deleteProjects = "DELETE FROM projects WHERE randomProj_ID = '"+randomProj_ID+"' ";
		
		try {

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(deleteProjects);
			output += "<script>window.history.back();</script>";

		} catch (SQLException e) {
			output += "Error while removing";
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	//Update Project Details:
	public String updateItem(String randomProj_ID, String Project_Title, String Project_ShortDes, String Project_LongDes, String Project_Srclink , String Project_Videolink) {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			{return "Error while connecting to the database for updating."; }	
			
			// create a prepared statement
			String query = "UPDATE projects SET randomProj_ID=?,Project_Title=?,Project_ShortDes=?,Project_LongDes=?,Project_Srclink=?,Project_Videolink=? WHERE randomProj_ID=?";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setString(1, randomProj_ID);
			preparedStmt.setString(2, Project_Title);
			preparedStmt.setString(3, Project_ShortDes);
			preparedStmt.setString(4, Project_LongDes);
			preparedStmt.setString(5, Project_Srclink);
			preparedStmt.setString(5, Project_Videolink);
			
			// execute the statement
			preparedStmt.execute(); 
			con.close(); 
			
			output = "Updated successfully";
			
		}catch (Exception e){
			
			 output = "Error while updating the item.";
			 System.err.println(e.getMessage());
		}
		
		return output;
	}
	

}
