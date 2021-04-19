package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

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
		        	output +="<form action='../productService/products/addToCart' method='post'>"
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
	
	
	//Product add to cart 
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
			
			output += "<h1 class=\"text-center\">Successfully Added to Cart</h1><br>"
					+ "<div class=\"btn-group\" role=\"group\">"
					+ "<a href='../../../GB/productService/products'><button type='submit' class='btn btn-primary' >Continue Shopping</button></a>"
					+ "<form method='post' action='../../../GB/paymentService/products/cart'>"
					+ "<button type='submit' class='btn btn-danger' >Processed to Checkout</button></form>";
			
			output +="</div><div class=\"col-md-4 col-lg-2\"></div></div></div>";
			
			
			conn.close();
			
			
		} catch (SQLException e) {
			output = "Error while adding to cart";
			System.err.println(e.getMessage());
			e.printStackTrace();
			
		}
		
		return output;
	}
	
	
	//load cart item
	public String loadCartItems(String loggedUsername) {
		
		String output = "<script>setTimeout('location.reload(true);', 2000);</script>";
		Connection conn = connect();
		
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		
		//get total cost of the cart items
		int total = 0;
		try {
			String sql = "SELECT SUM(productPrice) FROM cart c WHERE c.loggedUsername = '"+loggedUsername+"'  ";
			Statement st;
			st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			rs.next(); // SELECT count(*) always returns exactly 1 row
			total = rs.getInt(1); // Get value of first column
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		//generate orderId
		
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = "GB-" + salt.toString();
        //end generate randomID
		
		//buttons
		output +="<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-2\"></div><div class=\"col-md-4 col-lg-8 text-center\">";
		output += "<h1 class=\"text-center\">Shopping Cart</h1><br>"
				+ "<div class=\"btn-group\" role=\"group\">"
				+ "<a href='../products'><button type='submit' class='btn btn-primary' >Continue Shopping</button>"
				+ "</a><a href='../products'><button type='submit' class='btn btn-success' disabled >Total : "+total+".00 LKR </button></a>"
		
		
				
				//payment gateway redirect
				+ "<form method='post' action='https://sandbox.payhere.lk/pay/checkout'>"
				+ "<input type='hidden' name='merchant_id' value='1217060'>"
				+ "<input type='hidden' name='return_url' value='http://localhost:7070/GB/paymentService/products/paymentSuccess'>"
				+ "<input type='hidden' name='cancel_url' value='http://localhost:7070/GB/paymentService/paymentUnsuccess'>"
				+ "<input type='hidden' name='notify_url' value=''>"
				+ "<input type='hidden' name='order_id' value='"+saltStr+"'>"
				+ "<input type='hidden' name='items' value='Buy products from GadgetBadget'>"
				+ "<input type='hidden' name='currency' value='LKR'>"
				+ "<input type='hidden' name='amount' value='"+total+"'>"
				+ "<input type='hidden' name='country' value='Sri Lanka'>"
				+ "<input class='form-control' type='hidden' name='first_name' value='Amila' >"
				+ "<input class='form-control' type='hidden' name='last_name'  value='Bandara' >"
				+ "<input class='form-control' type='hidden' name='email' value='testpayment@gmail.com'  >"
				+ "<input class='form-control' type='hidden' name='phone' value='0777123456'  >"
				+ "<input class='form-control' type='hidden' name='city' value='Colombo' >"
				+ "<textarea class='form-control' name='address'  hidden>No56, Colombo 07</textarea>"
				+ "<button type='submit' class='btn btn-danger' >Make Payment</button>"
				+ "</form>";
		
		
		output += "</div><div class=\"col-md-4 col-lg-2\"></div></div></div>";
		//end buttons
		
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\"><div class=\"table-responsive\"><table class=\"table\">";
		output +="<thead><th><strong>PRODUCT</strong><br></th><th><strong>PRICE</strong><br></th><th><strong>QUANTITY</strong><br></th><th><strong>REMOVE</strong><br></th><tr></thead>";
		
		
		String loadCartItemsSql = "SELECT * FROM cart c WHERE c.loggedUsername = '"+loggedUsername+"'";
		
		
		try {
			PreparedStatement stmt2;
			stmt2 = conn.prepareStatement(loadCartItemsSql);
			ResultSet rs2 = stmt2.executeQuery();
			
			while (rs2.next()) {
	        	
	        	String productNameInCart = rs2.getString("productName");
	        	String quantityInCart = rs2.getString("quantity");
	        	String productPriceInCart = rs2.getString("productPrice");
	        	String cartId = rs2.getString("cartId");
	        	
	        	
	        	output += "<tbody><tr><td>"+productNameInCart+"</td><td>"+productPriceInCart+".00 LKR</td><td>"+quantityInCart+"</td>"
	        			
	        			//remove from cart
	        			+ "<td><form action='../../../GB/paymentService/products/removeFromCart' method='post'><input type='hidden' name='cartId' value="+cartId+" ><button type='submit'>Remove</button></form></td></tr></tbody>";
	        	
	        	
	        }
			
			conn.close();
			
		} catch (SQLException e) {
			
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
			output += "<script>window.history.back();</script>";

		} catch (SQLException e) {
			output += "Error while removing";
			e.printStackTrace();
		}
		
		return output;
	}
	
	
	
	//paymentSuccess success page
	public String paymentSuccessPage(String loggedUsername , String orderId) {
		
		String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
		
		output += "<h1>Payment was successful</h1><p><strong>Yay! It's done. Your&nbsp;<em>ORDER_ID</em>&nbsp;is "+orderId+"</strong><br></p>"
				+ "<p>Visit &nbsp;<a href='../../../GB/paymentService/products/myDownloads'>My Downloads</a>&nbsp;page to access above products any time!<br></p>";
		
		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
		
		return output;
		
	}
	

}
