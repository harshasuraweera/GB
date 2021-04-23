package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;


public class SubmitProjectsModel {
	
	

	//A common method to connect to the DB :
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
	
	//method to generate random project ID:
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

	//creating submit project interface :
	public String submitProjectsInterface() throws SQLException{
	
		String Project_Id = generateProjectId();
		
		String output = "";
		
			output +="<style>\r\n" + 
					"* {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"input[type=text], select, textarea {\r\n" + 
					"  width: 100%;\r\n" + 
					"  padding: 12px;\r\n" + 
					"  border: 1px solid #ccc;\r\n" + 
					"  border-radius: 4px;\r\n" + 
					"  resize: vertical;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"label {\r\n" + 
					"  padding: 12px 12px 12px 0;\r\n" + 
					"  display: inline-block;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"input[type=submit] {\r\n" + 
					"  background-color: #4CAF50;\r\n" + 
					"  color: white;\r\n" + 
					"  padding: 12px 20px;\r\n" + 
					"  border: none;\r\n" + 
					"  border-radius: 4px;\r\n" + 
					"  cursor: pointer;\r\n" + 
					"  float: right;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"input[type=submit]:hover {\r\n" + 
					"  background-color: #45a049;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					".container {\r\n" + 
					"  border-radius: 50px;\r\n" + 
					"  width: 1300px;\r\n" + 
					"  height: 600px;\r\n" +
					"  margin-left: 8%;;\r\n" + 
					"  background-color: #e8fdf2;\r\n" + 
					"  padding: 50px;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					".col-25 {\r\n" + 
					"  float: left;\r\n" + 
					"  width: 25%;\r\n" + 
					"  margin-top: 6px;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					".col-75 {\r\n" + 
					"  float: left;\r\n" + 
					"  width: 75%;\r\n" + 
					"  margin-top: 6px;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"/* Clear floats after the columns */\r\n" + 
					".row:after {\r\n" + 
					"  content: \"\";\r\n" + 
					"  display: table;\r\n" + 
					"  clear: both;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */\r\n" + 
					"@media screen and (max-width: 300px) {\r\n" + 
					"  .col-25, .col-75, input[type=submit] {\r\n" + 
					"    width: 100%;\r\n" + 
					"    margin-top: 0;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"hr.new5 {\r\n" + 
					"  border: 10px solid green;\r\n" + 
					"  border-radius: 5px;\r\n" + 
					"}h3 {\r\n" + 
					"  text-align: center;\r\n" + 
					"  text-transform: uppercase;\r\n" + 
					"  color: #4CAF50;\r\n" + 
					"}\r\n" + 
					"hr.new4 {\r\n" + 
					"  border: 1px solid green;\r\n" + 
					"}</style>"
					+ "<center><button class=\"button button2\" onclick=\"window.location.href='/../../../GB/projectService/projects/readProjects'\"> View All Projects</button></center><hr class=\"new4\"><center><h3> - Add New Project -</h3></center><div class=\"container\"><form action='../../../GB/projectService/projects/AddProject' method='post'>"
					+ "<div class=\"row\"><div class=\"col-25\"><label>Project ID :</label></div><div class=\"col-75\"><input type='text' name='randomProj_ID' value='"+Project_Id+"' readonly></div></div><br>"
					+ "<div class=\"row\"><div class=\"col-25\"><label>Project Title :</label></div><div class=\"col-75\"><input type='text' name='Project_Title' required onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\"</div></div><br>>"
					+ "<div class=\"row\"><div class=\"col-25\"><label>Short Description  :</label></div><div class=\"col-75\"><textarea class=\"form-control\" name=\"Project_ShortDes\"  maxlength=\"100\" placeholder=\"Should be less than 250 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea></div></div><br>"
					+ "<div class=\"row\"><div class=\"col-25\"><label>Long Description  :</label></div><div class=\"col-75\"><textarea class=\"form-control\" name=\"Project_LongDes\"  maxlength=\"250\" placeholder=\"Should be less than 250 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea></div></div><br>"
					
					+ "<div class=\"row\"><div class=\"col-25\"><label>Source Link :<br></label></div><div class=\"col-75\"><input type='text' name='Project_Srclink' required required placeholder=\"(Upload your project into google drive and put the link)      Ex: https://example.com\" pattern=\"https://.*\" ></div></div><br>"
					+ "<div class=\"row\"><div class=\"col-25\"><label>Video Link :<br></label></div><div class=\"col-75\"><input type='text' name='Project_Videolink' value='' required required placeholder=\"(Upload your video into google drive or any social media platform and put the link)     Ex:  https://example.com\" pattern=\"https://.*\" ></div></div><br><br>"
					+ "<center><input class=\"btn btn-primary\" type=\"submit\" value='Submit' href='../../../GB/projectService/projects/readProjects'></center></form></div><br><br>";
			
	
		
	    return output;
			
		
	}
	
	//Add New Projects To the system :
	public String AddProjects(String Project_Title, String Project_ShortDes , String Project_LongDes , String Project_Srclink , String Project_Videolink )
	{
		
		String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
  	  
	    Connection con = connect();

		
		 PreparedStatement pstmt;
	     try {
	    	 String sql = "insert into projects (randomProj_ID, Project_Title, Project_ShortDes, Project_LongDes, Project_Srclink, Project_Videolink) values (?,?,?,?,?,?)";
	    	   	pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    	   
	    	   
	    	 	pstmt.setString(1, generateProjectId());
	    	 	pstmt.setString(2, Project_Title);
	    	 	pstmt.setString(3, Project_ShortDes);
	    	 	pstmt.setString(4, Project_LongDes);
	    	 	pstmt.setString(5, Project_Srclink);
	    	 	pstmt.setString(6, Project_Videolink);
	    	    
	    	 	pstmt.executeUpdate();
	    	 	
	    	 	output += "Status : Your Project Successfully Submitted";
	    	 	
	    		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
	    	
	    		output += "<p><strong>Your Project Successfully Submitted..!</strong><br></p>"
	    				+ "<p><a href='../../../GB/projectService/projects/readProjects'>View Submitted Projects</a>&nbsp;<br></p>";
	    		
	    		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
	    		
	    	 	
	    	 	
	    	   
	     }catch (Exception e){
	    	 
	    	output += "Status : Error while submitting the project";
	    	 
	 		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
			
			output += "<p><strong>Oops! Something went wrong..! </strong><br></p>"
					+ "<a href='../../../GB/projectService/projects'>Try Again</a>&nbsp;<br>";
			
			output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
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
			
			output = "<style>"
					+ "\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"th, td {\r\n" + 
					"  padding: 8px;\r\n" + 
					"  text-align: left;\r\n" + 
					"  border-bottom: 1px solid #ddd;\r\n" + 
					"}\r\n" +  ".button {\r\n" + 
							"  background-color: #4CAF50; /* Green */\r\n" + 
							"  border: none;\r\n" + 
							"  color: white;\r\n" + 
							"  padding: 10px 10px;\r\n" + 
							"  text-align: center;\r\n" + 
							"  text-decoration: none;\r\n" + 
							"  display: inline-block;\r\n" + 
							"  font-size: 14px;\r\n" + 
							"  margin: 3px 2px;\r\n" + 
							"  cursor: pointer;\r\n" + 
							"}\r\n" + 
							".button2 {background-color: #4CAF50;} /* green */\r\n" + 
							".button3 {background-color: #FF0000;} /* Red */"+
							".button4 {background-color: #1f1fff;} /* Red */"
							+ " hr.new4 {\r\n" + 
							"  border: 1px solid green;\r\n" + 
							"}</style>"
					+ "<center><button class=\"button button2\" onclick=\"window.location.href='/../../../GB/projectService/projects/'\">Add New Project</button>"
					+ "<button class=\"button button4\" onclick=\"window.location.href='/../../../GB/projectService/projects/ViewSelectedProjects'\"> Selected Projects</button>"
					+ "<button class=\"button button3\" onclick=\"window.location.href='/../../../GB/projectService/projects/ViewRejectedProjects'\"> RejectedProjects</button><hr class=\"new4\"></center><br><br><label><b>Your Projects :</b></label><br><br><table border='1' ><tr><th>Project ID</th>" +
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
				 output += "<td><input name='btnUpdate' class=\"button button2\" type='button' value='Update'class='btn btn-secondary'></td>"
		+ "<td><form action='../../../GB/projectService/projects/removeProject' method='post'><input type='hidden' name='randomProj_ID' value="+randomProj_ID+" ><button class=\"button button3\" type='submit'>Remove</button>" + 
		"</form></td></tr>"; 
			}

		
			
			output += "</table>";
			
		}catch (Exception e) {
			
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			
		}
		
		return output;
	}
	

	
	//Update submitted Project Details:
	public String updateProjects(String randomProj_ID,String Project_Title,String Project_ShortDes,String Project_LongDes,String Project_Srclink,String Project_Videolink) {
		String output = "";
		Connection conn = connect();
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			String updateProjects = "UPDATE projects SET randomProj_ID='"+randomProj_ID+"',Project_Title='"+Project_Title+"', Project_ShortDes='"+Project_ShortDes+"' , Project_LongDes = '"+Project_LongDes+"' , Project_Srclink='"+Project_Srclink+"' ,Project_Videolink='"+Project_Videolink+"' WHERE randomProj_ID ='"+randomProj_ID+"' ";
			try {

				Statement stmt = conn.createStatement();
				stmt.executeUpdate(updateProjects);
				
				output += "Status : Your project details was successfully updated";
				
				
				} catch (SQLException e) {
					output += "Error while updating project details";
					e.printStackTrace();
				}	
		}
		
		
		return output;
	}
	
	//delete submitted projects :
	public String deleteProject( String randomProj_ID) {
		
		
		String output = "";
		Connection conn = connect();
		String deleteFromCartSql = "DELETE FROM projects WHERE randomProj_ID = '"+randomProj_ID+"' ";
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			try {

			Statement stmt = conn.createStatement();
			int status = stmt.executeUpdate(deleteFromCartSql);
			
			if(status>0) {
				
			output += "Status : Your project was successfully deleted";	
			
			}else {
				output += "Status : Error while deleting project";
			}
			
			} catch (SQLException e) {
				output += "Error while deleting";
				e.printStackTrace();
			}
		
		}

		return output;
	}
	
	//Display Rejected projects:
	public String RejectedProjects() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			 {return "Error while connecting to the database for reading."; }
			
			//<button onclick="window.location.href='/page2'">Continue</button>
			
			// Prepare the html table to be displayed
			output = "<style>"
					+ "\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"th, td {\r\n" + 
					"  padding: 8px;\r\n" + 
					"  text-align: left;\r\n" + 
					"  border-bottom: 1px solid #ddd;\r\n" + 
					"}\r\n" +  ".button {\r\n" + 
							"  background-color: #4CAF50; /* Green */\r\n" + 
							"  border: none;\r\n" + 
							"  color: white;\r\n" + 
							"  padding: 10px 10px;\r\n" + 
							"  text-align: center;\r\n" + 
							"  text-decoration: none;\r\n" + 
							"  display: inline-block;\r\n" + 
							"  font-size: 14px;\r\n" + 
							"  margin: 3px 2px;\r\n" + 
							"  cursor: pointer;\r\n" + 
							"}\r\n" + 
							".button2 {background-color: #4CAF50;} /* green */\r\n" + 
							".button3 {background-color: #FF0000;} /* Red */"+
							".button4 {background-color: #1f1fff;} /* Blue */"+
							".button5 {background-color: #9e1fff;} /* Blue */"
							+ " hr.new4 {\r\n" + 
							"  border: 1px solid green;\r\n" + 
							"}</style>"
					+ "<center><button class=\"button button2\" onclick=\"window.location.href='/../../../GB/projectService/projects/'\">Add New Project</button>"
					+ "<button class=\"button button4\" onclick=\"window.location.href='/../../../GB/projectService/projects/ViewSelectedProjects'\">Selected Projects</button>"
					+ "<button class=\"button button3\" onclick=\"window.location.href='/../../../GB/projectService/projects/ViewRejectedProjects'\">RejectedProjects</button>"
					+ "<button class=\"button button5\" onclick=\"window.location.href='/../../../GB/projectService/projects/readProjects'\"> Submitted Projects</button><hr class=\"new4\"></center><br><br><label><b>Rejected Projects :</b></label><br><br><table border='1' ><tr><th>Project ID</th>" +
					"<th>Project Title</th>" +
					"<th>Short Discription</th>" +
					"<th>Long Discription</th>" +
					"<th>Src Link</th>" +
					"<th>Video Link</th>" +
					"<th>Comment</th></tr>";
			
			String queryz = "select * from rejectedprojects";
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
				String Project_RejectComment = rs.getString("Project_RejectComment");
				
			// Add into the html table	
				output += "<tr><td>" + randomProj_ID + "</td>";
				output += "<td>" + Project_Title + "</td>"; 
				output += "<td>" + Project_ShortDes + "</td>";
				output += "<td>" + Project_LongDes + "</td>";
				output += "<td>" + Project_Srclink + "</td>";
				output += "<td>" + Project_Videolink + "</td>";
				output += "<td>" + Project_RejectComment + "</td>";
			
			}

		
			
			output += "</table>";
			
		}catch (Exception e) {
			
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			
		}
		
		return output;
	}

	//Display All Selected Projects :
	public String SelectedProjects() {
		
		String output = "";
		
		try {
			
			Connection con = connect();
			
			if (con == null)
			 {return "Error while connecting to the database for reading."; }
			
			//<button onclick="window.location.href='/page2'">Continue</button>
			
			// Prepare the html table to be displayed
			output = "<style>"
					+ "\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"\r\n" + 
					"th, td {\r\n" + 
					"  padding: 8px;\r\n" + 
					"  text-align: left;\r\n" + 
					"  border-bottom: 1px solid #ddd;\r\n" + 
					"}\r\n" +  ".button {\r\n" + 
							"  background-color: #4CAF50; /* Green */\r\n" + 
							"  border: none;\r\n" + 
							"  color: white;\r\n" + 
							"  padding: 10px 10px;\r\n" + 
							"  text-align: center;\r\n" + 
							"  text-decoration: none;\r\n" + 
							"  display: inline-block;\r\n" + 
							"  font-size: 14px;\r\n" + 
							"  margin: 3px 2px;\r\n" + 
							"  cursor: pointer;\r\n" + 
							"}\r\n" + 
							".button2 {background-color: #4CAF50;} /* green */\r\n" + 
							".button3 {background-color: #1f1fff;} /* Blue */" +
							".button4 {background-color: #FF0000;} /* red */" +
							".button5 {background-color: #9e1fff;} /* red */"
							+ " hr.new4 {\r\n" + 
							"  border: 1px solid green;\r\n" + 
							"}</style>"
					+ "<center><button class=\"button button2\" onclick=\"window.location.href='/../../../GB/projectService/projects/'\">Add New Project</button>"
					+ "<button class=\"button button3\" onclick=\"window.location.href='/../../../GB/projectService/projects/ViewSelectedProjects'\">Selected Projects</button>"
					+ "<button class=\"button button4\" onclick=\"window.location.href='/../../../GB/projectService/projects/ViewRejectedProjects'\">Rejected Projects</button>"
					+ "<button class=\"button button5\" onclick=\"window.location.href='/../../../GB/projectService/projects/readProjects'\"> Submitted Projects</button></center><hr class=\"new4\"><br><br><label><b>Selected Projects :</b></label><br><br><table border='1' ><tr><th>Project ID</th>" +
					"<th>Project Title</th>" +
					"<th>Short Discription</th>" +
					"<th>Long Discription</th>" +
					"<th>Src Link</th>" +
					"<th>Video Link</th>"
					+ "<th>Comment</th></tr>";
					
			
			String queryz = "select * from selectedprojects";
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
				String Project_SelectComment = rs.getString("Project_SelectComment");
				
			// Add into the html table	
				output += "<tr><td>" + randomProj_ID + "</td>";
				output += "<td>" + Project_Title + "</td>"; 
				output += "<td>" + Project_ShortDes + "</td>";
				output += "<td>" + Project_LongDes + "</td>";
				output += "<td>" + Project_Srclink + "</td>";
				output += "<td>" + Project_Videolink + "</td>";
				output += "<td>" + Project_SelectComment + "</td>";
			
			}

		
			
			output += "</table>";
			
		}catch (Exception e) {
			
			output = "Error while reading the items.";
			 System.err.println(e.getMessage());
			
		}
		
		return output;
	}

	
}
