package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BuyProductsModel {
	
	//A common method to connect to the DB
	private Connection connect(){
		
		Connection conn = null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver"); 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3030/paymentservice", "root", "1234");
		}catch (Exception e){
			e.printStackTrace();
		}
			return conn;
	}
	
	
	//Fetch products from the products table
	public String fetchAllProducts(String search){
		
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
					 sql = "SELECT * FROM testproducts";
				}else if(!search.equals("all")) {
					 sql = "SELECT * FROM testproducts WHERE name LIKE '%"+search+"%' ";
				}
				
				Statement stmt;
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				
				while (rs.next())
				{
					String productId = rs.getString("productId");
		        	String productName = rs.getString("name");
		        	String shortDescription = rs.getString("sDesc");
		        	String totalSales = rs.getString("sales");
		        	String productPrice = rs.getString("price");
		        	String quantity = "1";
		        	
		        	output +="<div class='col-md-4' style='padding-top: 15px;padding-bottom: 15px;'>";
		        	
		        	output += "<p>Product Name :"+productName+"<br>Description : "+shortDescription+"<br>Price : "+productPrice+".00 LKR<br>Total Sales : "+totalSales+"</p>";
		        	
		        	
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
	}
	
	
	//Product add to cart and display cart details
	public String addToCart(String loggedUsername , String productId, String productName, String shortDescription, String quantity, String price) {
		
		String output = "";
		Connection conn = connect();
		
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-2\"></div><div class=\"col-md-4 col-lg-8 text-center\">";
		
		
		String addToCartSql = "INSERT INTO cart (loggedUsername, productId, productName, quantity, productPrice) VALUES (?,?,?,?,?)";
		
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement(addToCartSql,Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, loggedUsername);
			pstmt.setString(2, productId);
			pstmt.setString(3, productName);
			pstmt.setString(4, quantity);
			pstmt.setString(5, price);
			
			pstmt.executeUpdate();
			
			output += "<h1 class=\"text-center\">Shopping Cart</h1><br>"
					+ "<div class=\"btn-group\" role=\"group\">"
					+ "<form method='post' action='../products'><button type='submit' class='btn btn-primary' >Continue Shopping</button></form>"
					+ "<form method='post' action='https://www.payhere.lk/account/user'>"
					+ "<button type='submit' class='btn btn-danger' >Processed to Payment</button></form>";
			
			output +="</div><div class=\"col-md-4 col-lg-2\"></div></div></div>";
			
			//cart item in a table
			output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\"><div class=\"table-responsive\"><table class=\"table\">";
			output +="<thead><th><strong>PRODUCT</strong><br></th><th><strong>PRICE</strong><br></th><th><strong>QUANTITY</strong><br></th><th><strong>REMOVE</strong><br></th><tr></thead>";
			
			
			String loadCartItemsSql = "SELECT * FROM cart c WHERE c.loggedUsername = '"+loggedUsername+"'";
			
			PreparedStatement stmt = conn.prepareStatement(loadCartItemsSql);
	        ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
	        	
	        	String productNameInCart = rs.getString("productName");
	        	String quantityInCart = rs.getString("quantity");
	        	String productPriceInCart = rs.getString("productPrice");
	        	String cartId = rs.getString("cartId");
	        	
	        	
	        	output += "<tbody><tr><td>"+productNameInCart+"</td><td>"+productPriceInCart+"</td><td>"+quantityInCart+"</td>"
	        			+ "<td><form action='removeFromCart' method='post'><input type='hidden' name='cartId' value="+cartId+" ><button type='submit'>Remove</button></form></td></tr></tbody>";
	        	
	        }
			
			conn.close();
			
			
		} catch (SQLException e) {
			output = "Error while adding to cart";
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		
		
		return output;
	}
	
	
	//delete item from cart
	public String deleteFromCart( String cartId) {
		
		
		String output = "";
		Connection conn = connect();
		String deleteFromCartSql = "DELETE FROM cart WHERE cartId = '"+Integer.parseInt(cartId)+"' ";
		
		try {

			Statement stmt = conn.createStatement();
			stmt.executeUpdate(deleteFromCartSql);
			output += "successfully deleted";

		} catch (SQLException e) {
			output += "Error while removing";
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	
	

}
