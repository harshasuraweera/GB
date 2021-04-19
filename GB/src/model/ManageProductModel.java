package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.cj.xdevapi.Statement;

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
public String addProduct(String productId,String title,String sDesc,String lDesc,String price,String downloadLink) throws SQLException {

String output = "";
Connection conn = connect();

String addProductsql = "INSERT INTO products (productId,title, sDesc, lDesc,price,downloadLink) VALUES (?,?,?,?,?,?)";
PreparedStatement pre = conn.prepareStatement(addProductsql);

try {
	
	
	
	pre.setString(1, productId);
	pre.setString(2, title);
	pre.setString(3, sDesc);
	pre.setString(4, lDesc);
	pre.setString(5, price);
	pre.setString(6,downloadLink);
	

	
	pre.executeUpdate();
	
	output +="<form action='products/cart' method='post'>"
			+ "<input type='hidden' name='productId' value="+productId+">"
			+ "<input type='hidden' name='title' value="+title+">"
			+ "<input type='hidden' name='sDesc' value="+sDesc+">"
			+ "<input type='hidden' name='lDesc' value="+lDesc+">"
			+ "<input type='hidden' name='price' value="+price+">"
			+ "<input type='hidden' name='downloadLink' value="+downloadLink+">"
			+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add Products'></form>";
	
	
	output +="</div>";
	
	
	
	
	
} catch (SQLException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

return output;

}




//fetch Products Details to a Table
/*public String fetchAllProducts(String search){
	
	String sql = "";
	String output = "";
	Connection conn = connect();
	
	
	
	if (conn == null){
		return "Error while connecting to the database for inserting."; 
	}else {
		
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
	
		output += "<div class=\"container\"><div class='row' >";
		
		
		
		try {
			
			if(search.equals("all")) {
				 sql = "SELECT * FROM products";
			}else if(!search.equals("all")) {
				 sql = "SELECT * FROM products WHERE productId LIKE '%"+search+"%' ";
			}
			
			Statement stmt;
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			
			while (rs.next())
			{
				String productId = rs.getString("productId");
	        	String productTitle = rs.getString("title");
	        	String shortDescription = rs.getString("sDesc");
	        	String longDescription = rs.getString("lDesc");
	        	String productPrice = rs.getString("price");
	        	String productLink = rs.getString("downloadLink");
	        	
	        	
	        	output +="<div class='col-md-4' style='padding-top: 15px;padding-bottom: 15px;'>";
	        	
	        	output += "<p>Product ID :"+productId+"<br>Description : "+shortDescription+"<br>Price : "+productPrice+".00 LKR<br>Total Sales : "+totalSales+"</p>";
	        	
	        	
	        	//buttons
	        	output +="<form action='products/cart' method='post'>"
	        			+ "<input type='hidden' name='productId' value="+productId+">"
	        			+ "<input type='hidden' name='productName' value="+productName+">"
	        			+ "<input type='hidden' name='shortDescription' value="+shortDescription+">"
	        			+ "<input type='hidden' name='quantity' value="+quantity+">"
	        			+ "<input type='hidden' name='price' value="+productPrice+">"
	        			+ "<input class=\"btn btn-primary\" type=\"submit\" value='Add to Cart'></form>";
	        	
	        	
	        	output +="</div>";
	        	
	        	
			}
			
			conn.close();
			
		} catch (SQLException e) {
			output = "Error while fetching products";
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		output += "</div></div>";
			
		
	}
	
	return output;
}*/



}
