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
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/productservise", "root", "Highschool23*");
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
			+ "<input type='text' name='productId' >"
			+ "<input type='text' name='title' >"
			+ "<input type='text' name='sDesc' >"
			+ "<input type='text' name='lDesc' >"
			+ "<input type='text' name='price' >"
			+ "<input type='file' name='downloadLink' >"
			+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Products'></form>";
	
	
	
	


return output;

}


//insert products to  db
public String addProduct(String productId,String title,String sDesc,String lDesc,String price,String downloadLink) {
	
	String output = "";
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
		
		
	
		
		
		//conn.close();
		
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		output = "Error ";
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
}
