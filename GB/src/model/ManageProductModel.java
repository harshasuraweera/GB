package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class ManageProductModel {
	
private Connection connect(){
		
		Connection conn = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3030/productservise", "root", "Highschool23*");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
			return conn;
	}




//Add product using a form
public String addProductInterface() throws SQLException {

String output = "";

//randId


	
	output +="<form action='../product_view/AddProduct' method='post'>"
			+ "<input type='text' name='productId' value=''>"
			+ "<input type='text' name='title' value=''>"
			+ "<input type='text' name='sDesc' value=''>"
			+ "<input type='text' name='lDesc' value=''>"
			+ "<input type='text' name='price' value=''>"
			+ "<input type='file' name='downloadLink' value=''>"
			+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Products'></form>";
	
	
	
	


return output;

}


//insert products to  db





//fetch Products Details to a Table

public String loadProducts() {
	
	String output = "";
	
	try {
		
		Connection con = connect();
		
		if (con == null)
		 {return "Error while connecting to the database for reading."; } 
		
		// Prepare the html table to be displayed
		 output = "<table border='1'><tr><th>Product ID</th>"+
		 "<th>Item Name</th>" +
		 "<th>Item Price</th>" +
		 "<th>Item Description</th>" +
		 "<th>Item Description</th>" +
		 "<th>Item Description</th>" +
		 "<th>Update</th><th>Remove</th></tr>";
		 
		 
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
			 
			 
			// buttons
			 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
			 		+ "<td><form method='post' action='load'><input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
			 + "<input name='productId' type='hidden' value='" + productId
			 + "'>" + "</form></td></tr>"; 
			 
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
 
	



}
