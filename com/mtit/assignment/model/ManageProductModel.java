package com.mtit.assignment.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;



public class ManageProductModel {
	
//DB connection	
public static Connection connect(){
		
		Connection conn = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3030/productservise", "root", "1234");
		}catch (Exception e){
			e.printStackTrace();
			
		}
			return conn;
	}

//generate random product ID
public static String generateProductId() {
    String SALTCHARS = "ABCDE1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 6) { // length of the random string.
        int index = (int) (rnd.nextFloat() * SALTCHARS.length());
        salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    
    return  "PD-" + saltStr;

}

//Add product using a form
public String addProductInterface() throws SQLException {

String output = "";

String productId = generateProductId() ;


	
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
			+"\"<center><h3> - Add New Product -</h3></center><hr class=\"new4\"><br><div class=\"container\"><form action='../../../GB/productService/product_view/AddProduct' method='post'>"
			
			+ "<div class=\"row\"><div class=\"col-25\"><label>Product ID :</label></div><div class=\"col-75\"><input type='text' name='productId' value="+productId+" readonly></div></div><br><br>"
			+ "<div class=\"row\"><div class=\"col-25\"><label>Product Title :</label></div><div class=\"col-75\"><input type='text' name='title'  required></div></div><br><br>"
			+ "<div class=\"row\"><div class=\"col-25\"><label>Short Description :</label></div><div class=\"col-75\"><textarea class=\"form-control\" name=\"sDesc\" style=\"height: 50px;\" style=\"width: 50px;\" maxlength=\"200\" placeholder=\"Should be less than 50 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea></div></div><br><br>"
			+ "<div class=\"row\"><div class=\"col-25\"><label>Long Description  :</label></div><div class=\"col-75\"><textarea class=\"form-control\" name=\"lDesc\" style=\"height: 100px;\" maxlength=\"500\" placeholder=\"Should be less than 100 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea></div></div><br><br>"
			+ "<div class=\"row\"><div class=\"col-25\"><label>Price:</label></div><div class=\"col-75\"><input type='number'  style=\"height: 50px;\" style=\"width: 100px;\" placeholder=\"USD\"  name='price' required ></div></div><br><br>"
			+ "<div class=\"row\"><div class=\"col-25\"><label>Download Link :</label></div><div class=\"col-75\"><input type='file' name='downloadLink' required></div></div><br><br>"
			+ "<input class=\"btn btn-primary\" style=\"margin:5px;\" type=\"submit\" value='Add Products'>"
			+ "<button class=\"btn btn-primary\" onclick=\"window.location.href='../../../GB/productService/product_view/load'\"> View All Projects</button>"
			+ "</form>"
			+ "</center>";
	
	
return output;

}


//insert products to  database
public String addProduct(String title,String sDesc,String lDesc,String price,String downloadLink) {
	
	String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
	Connection conn = connect();
	
	PreparedStatement pre;
	try {
		
		
		String sql = "INSERT INTO products(productId,title,sDesc,lDesc,price,downloadLink) VALUES (?,?,?,?,?,?)";
		 pre = conn.prepareStatement(sql);
		
		
	
		pre.setString(1, generateProductId());
		pre.setString(2, title);
		pre.setString(3, sDesc);
		pre.setString(4, lDesc);
		pre.setString(5, price);
		pre.setString(6,downloadLink);
		
		
		pre.executeUpdate();
		
		
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
		
		output += "<h1>your Product Submitted Successfully </h1>"
				+ "<p>If the issue persists, &nbsp;<a href='../../../GB/productService/product_view/load'>View Added Details</a>&nbsp;in a few minutes.<br></p>";
		
		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
		
		output += "Successfully added to the table";

		
		
		
		
		
		
	} catch (SQLException e) {
		
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
		
		output += "<h1>Submission unsuccessful</h1><p><strong>Oops! Something went wrong! </strong><br></p>"
				+ "<p>If the issue persists, &nbsp;<a href='../../../GB/productService/product_view'>Try Again!</a>&nbsp;in a few minutes.<br></p>";
		
		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
		
		output += "Error  while adding the items.";
		
		System.err.println(e.getMessage());
		e.printStackTrace();
	}
	
	return output;
	
}




//fetch Products Details to a Table

public String loadProducts() {
	
	String output = "";
	
	try {
		
		Connection con = connect();
		
		if (con == null)
		 {return "Error while connecting to the database for reading."; } 
		
		// Prepare the html table to be displayed
		 output += 
				 "<style>"
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
									".button3 {background-color: #f44336;} /* Red */"
									+ " hr.new4 {\r\n" + 
									"  border: 1px solid green;\r\n" + 
									"}</style>" + 
		 		"<center><button class=\"button button2\" onclick=\"window.location.href='../../../GB/productService/product_view'\">Add New Project</button></center><br><br><table border='1'><tr><th>Product ID</th>"+
		 "<th>Product Title</th>" +
		 "<th>Short Description </th>" +
		 "<th>Long Description</th>" +
		 "<th>Price</th>" +
		 "<th>Download Link</th>";
		
		 
		 
		 String query = "select * from products";
		 Statement stmt = con.createStatement();
		 ResultSet rs = stmt.executeQuery(query); 
		 
		 
		// iterate through the rows in the result set
		 while (rs.next()) {
			 
			 String productId = rs.getString("productId");
	        	String title = rs.getString("title");
	        	String sDesc = rs.getString("sDesc");
	        	String lDesc = rs.getString("lDesc");
	        	String price = rs.getString("price");
	        	String downloadLink = rs.getString("downloadLink");
			 
			 // Add into the html table
			 output += "<tr><td>" + productId + "</td>";
			 output += "<td>" + title + "</td>";
			 output += "<td>" + sDesc + "</td>";
			 output += "<td>" + lDesc + "</td>";
			 output += "<td>" + price + "</td>";
			 output += "<td>" + downloadLink + "</td>";
			 
			 

		 }
		 
		 
		 
		 con.close();
		 // Complete the html table
		 output += "</table>"; 
		
		
	} catch (Exception e) {
		// TODO: handle exception
		
		output = "Error while reading the items.";
		 System.err.println(e.getMessage());
	}
	
	return output;
	
	
}
 
	
//delete products from Table
	public String deleteProduct( String productId) {
		
		
		String output = "";
		Connection conn = connect();
		String deletesql = "DELETE FROM products WHERE productId = '"+productId+"' ";
		
		if(conn == null) {
			
			output += "Status : Error while connecting to the database"; 
		}else {
		
		try {

			Statement stmt = conn.createStatement();
			int status=stmt.executeUpdate(deletesql);
			
			if(status>0) {
				output += "Status : Successfully deleted from the table";
			}else {
				output += "Error while deleting";
			}

		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		}
		
		return output;
	}
	
	
	//update submitted products
		public String updateProduct( String productId,String title,String sDesc,String lDesc,String price,String downloadLink) {
			String output = "";
			Connection conn = connect();
			
			if (conn == null){
				output += "Status : Error while connecting to the database"; 
			}else {
				String updateSql = "UPDATE products SET title='"+title+"',sDesc='"+sDesc+"',lDesc='"+lDesc+"',price='"+price+"',downloadLink='"+downloadLink+"' WHERE productId ='"+productId+"' ";
				try {

					Statement stmt = conn.createStatement();
					stmt.executeUpdate(updateSql);
					
					output += "Status : Successfully updated ";
					
					output += "<script>window.history.back();</script>";

					} catch (SQLException e) {
						output += "Error while updating";
						e.printStackTrace();
					}	
			}
			
			
			return output;
		}
	
	
}
