package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;



public class ManageProductModel {
	
private Connection connect(){
		
		Connection conn = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productservise", "root", "Highschool23*");
		}catch (Exception e){
			e.printStackTrace();
			System.out.println(e);
		}
			return conn;
	}


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

//randId



String productId = generateProductId() ;


	
	output +="<center><form action='../../../GB/productService/product_view/AddProduct' method='post'>"
			+ "<h2>STOCK YOUR SHOP</h2><br>"
			+ "<input type='text' name='productId' value="+productId+" readonly><br><br>"
			+ "<input type='text' name='title' required><br><br>"
			+ "<textarea class=\"form-control\" name=\"sDesc\" style=\"height: 170px;\" style=\"width: 50px;\" maxlength=\"200\" placeholder=\"Should be less than 200 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea><br><br>"
			+ "<textarea class=\"form-control\" name=\"lDesc\" style=\"height: 500px;\" maxlength=\"500\" placeholder=\"Should be less than 500 letters\" onkeypress=\"return (event.charCode > 64 && event.charCode < 91) || (event.charCode > 96 && event.charCode < 123)\" required></textarea><br><br>"
			+ "<input type='text' name='price' required ><br><br>"
			+ "<input type='file' name='downloadLink' required><br><br>"
			+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Products'>"
			+ "</form></center>";
	
	
	
	


return output;

}


//insert products to  db
public String addProduct(String productId,String title,String sDesc,String lDesc,String price,String downloadLink) {
	
	String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
	Connection conn = connect();
	
	PreparedStatement pre;
	try {
		
		
		String sql = "INSERT INTO products(productId,title,sDesc,lDesc,price,downloadLink) VALUES (?,?,?,?,?,?)";
		 pre = conn.prepareStatement(sql);
		
		
	
		pre.setString(1, productId);
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

		
		
		//conn.close();
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
		
		output += "<h1>Payment was unsuccessful</h1><p><strong>Oops! Something went wrong! </strong><br></p>"
				+ "<p>If the issue persists, &nbsp;<a href='../../../GB/productService/product_view'>Try Again!</a>&nbsp;in a few minutes.<br></p>";
		
		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
		
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
		 output = "<table border='1'><tr><th>Product ID</th>"+
		 "<th>Product Title</th>" +
		 "<th>Short Description </th>" +
		 "<th>Long Description</th>" +
		 "<th>Price</th>" +
		 "<th>Download Link</th>" +
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
			 		+ "<td><form action='../../../GB/productService/product_view/removeProduct' method='post'><input type='hidden' name='productId' value="+productId+" ><button type='submit'>Remove</button>"
			 		+ "</form></td></tr>"; 
			 
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
 
	
//delete item from Table
	public String deleteProduct( String productId) {
		
		
		String output = "";
		Connection conn = connect();
		String deletesql = "DELETE FROM products WHERE productId = '"+productId+"' ";
		
		try {

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(deletesql);
			output += "<script>window.history.back();</script>";

		} catch (SQLException e) {
			output += "Error while removing";
			e.printStackTrace();
		}
		
		return output;
	}
	
	
//Update Details
	public String updateProduct(String productId,String title,String sDesc,String lDesc,String price,String downloadLink) {
		
		String output = ""; 
		try
		 {
			Connection con = connect();
			if (con == null)
			{	
				return "Error while connecting to the database for updating.";
				
			}
		 // create a prepared statement
			String query = "update products set productId=?, title=?,sDesc=?,lDesc=?,price=?,downloadLink=? where  productId=";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
		 // binding values
		 preparedStmt.setString(1, productId);
		 preparedStmt.setString(2, title);
		 preparedStmt.setString(3, sDesc);
		 preparedStmt.setString(4, lDesc);
		 preparedStmt.setString(5, price);
		 preparedStmt.setString(6, downloadLink);
		 
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
	
	
}
