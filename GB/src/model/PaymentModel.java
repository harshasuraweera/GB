package model;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class PaymentModel {
	
	//This method will be connect the payment service database to the project
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
	
	
	/* By this method, all the products will be retrieving HTML grid view 
	 * */
	public String fetchAllProducts(){
		
		String output = "";
		Connection conn = ManageProductModel.connect();
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			
			
			try {
				
				String sql = "SELECT * FROM products";
				
				Statement stmt;
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()) {
					output += "Status : Successfully fetched products";
				}else {
					output += "Status : Error while fetching products";
				}
				
				
				
				output += "<br><br><div class=\"container\"><div class='row' >";
				
				while (rs.next())
				{
					String productId = rs.getString("productId");
		        	String productName = rs.getString("title");
		        	String shortDescription = rs.getString("sDesc");
		        	String totalSales = rs.getString("sales");
		        	String productPrice = rs.getString("price");
		        	String quantity = "1";
		        	
		        	output +="<div class='col-md-4' style='padding-top: 15px;padding-bottom: 15px;'>";
		        	
		        	output += "<p>Product Name :"+productName+"<br>Description : "+shortDescription+"<br>Price : "+productPrice+".00 LKR<br>Total Sales : "+totalSales+"</p>";
		        	
		        	
		        	//buttons
		        	output +="<form action='../../../GB/productService/products/addToCart' method='post'>"
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
				output += "Status : Error while fetching products";
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			
			output += "</div></div>";
				
			
		}
		
		return output;
	}
	
	
	/* If the use clicked the "Add To Cart button" the adding to cart table process is fulfilled in here 
	 * */
	public String addToCart(String loggedUsername , String productId, String productName, String shortDescription, String quantity, String price) {
		
		String output = "";
		Connection conn = connect();
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
			
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			
				
			try {
				
				String addToCartSql = "INSERT INTO cart (loggedUsername, productId, productName, quantity, productPrice) VALUES (?,?,?,?,?)";
				
				PreparedStatement pstmt;
				pstmt = conn.prepareStatement(addToCartSql,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, loggedUsername);
				pstmt.setString(2, productId);
				pstmt.setString(3, productName);
				pstmt.setString(4, quantity);
				pstmt.setString(5, price);
				
				int status = pstmt.executeUpdate();
				
				if(status>0) {
					output +="Status : Successfully Added to Cart";
				}else {
					output +="Status : Error while adding to cart";
				}
				
					
					
					output += "<br><br><div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-2\"></div><div class=\"col-md-4 col-lg-8 text-center\">";
			
					output += "<h1 class=\"text-center\">Successfully Added to Cart</h1><br>"
						+ "<div class=\"btn-group\" role=\"group\">"
						+ "<a href='../../../GB/productService/products'><button type='submit' class='btn btn-primary' >Continue Shopping</button></a>"
						+ "<form method='post' action='../../../GB/paymentService/products/cart'>"
						+ "<button type='submit' class='btn btn-danger' >Processed to Checkout</button></form>";
				
					output +="</div><div class=\"col-md-4 col-lg-2\"></div></div></div>";
				
				conn.close();
				
				
			} catch (SQLException e) {
				output +="Status : Error while adding to cart";
				System.err.println(e.getMessage());
				e.printStackTrace();
				
			}
		}
		
		return output;
	}
	
	
	/* When user viewing the cart page, the products under his UserName will be fetching through this 
	 * */
	public String loadCartItems(String loggedUsername) {
		
		String output = "";
		
		Connection conn = connect();
		
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			
			//get total cost of the cart items
			int total = 0;
			try {
				String sql = "SELECT * FROM cart c WHERE c.loggedUsername = '"+loggedUsername+"' ";
				Statement st;
				st = conn.createStatement();
				ResultSet rs = st.executeQuery(sql);
				
				while(rs.next()) {
					String singleProductPrice = rs.getString("productPrice");
					String singleProductQuantity = rs.getString("quantity");
					total = total + (Integer.parseInt(singleProductPrice) * Integer.parseInt(singleProductQuantity));
					
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			//start generate random orderId
			String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	        StringBuilder salt = new StringBuilder();
	        Random rnd = new Random();
	        while (salt.length() < 10) { 
	            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
	            salt.append(SALTCHARS.charAt(index));
	        }
	        String saltStr = "GB-" + salt.toString();
	        //end generate random orderId
			
	      
			
			String loadCartItemsSql = "SELECT * FROM cart c WHERE c.loggedUsername = '"+loggedUsername+"'";
			
			
			try {
				PreparedStatement stmt2;
				stmt2 = conn.prepareStatement(loadCartItemsSql);
				ResultSet rs2 = stmt2.executeQuery();
				
				if(rs2.next()) {
					output += "Status : Successfully fetched cart items";
				}else {
					output += "Status : Error while fetching items";
				}
				
				
					  
				//buttons to "continue shopping" and "processed to payment"
				output +="<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-2\"></div><div class=\"col-md-4 col-lg-8 text-center\">";
				output += "<h1 class=\"text-center\">Shopping Cart</h1><br>"
						+ "<div class=\"btn-group\" role=\"group\">"
						+ "<a href='../products'><button type='submit' class='btn btn-primary' >Continue Shopping</button>"
						+ "</a><a href='../products'><button type='submit' class='btn btn-success' disabled >Total : "+total+".00 LKR </button></a>"
				//end buttons
				
						
						//start payment gateway SDK form 
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
						+ "<input type='hidden' name='first_name' value='Amila' >"
						+ "<input type='hidden' name='last_name'  value='Bandara' >"
						+ "<input type='hidden' name='email' value='testpayment@gmail.com'  >"
						+ "<input type='hidden' name='phone' value='0777123456'  >"
						+ "<input type='hidden' name='city' value='Colombo' >"
						+ "<textarea  name='address'  hidden>No56, Colombo 07</textarea>"
						+ "<button type='submit' class='btn btn-danger' >Make Payment</button>"
						+ "</form>";
						//end payment gateway SDK form 
				
				output += "</div><div class=\"col-md-4 col-lg-2\"></div></div></div>";
				
				
				output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\"><div class=\"table-responsive\"><table class=\"table\">";
				output +="<thead><th><strong>PRODUCT</strong><br></th><th><strong>PRICE</strong><br></th><th><strong>QUANTITY</strong><br></th><th><strong>REMOVE</strong><br></th><tr></thead>";
			
				while (rs2.next()) {
		        	
		        	String productNameInCart = rs2.getString("productName");
		        	String quantityInCart = rs2.getString("quantity");
		        	String productPriceInCart = rs2.getString("productPrice");
		        	String cartId = rs2.getString("cartId");
		        	int totPrice = Integer.parseInt(productPriceInCart)*Integer.parseInt(quantityInCart);
		   
		        	output += ""; 
		        	
		        	output += "<tbody><tr>"
		        			+ "<td>"+productNameInCart+"</td>"
		        					+ "<td>"+totPrice+".00 LKR</td>"
		        							+ "<td>"
		        									+ "<form action='../../../GB/paymentService/products/updateCart' method='post'><input style='width:50px;' type='number' name='newQuantity' value='"+quantityInCart+"'><input type='hidden' name='cartId' value="+cartId+" ><button type='submit'>Update</button></form>"
		        									+ "</td>"
		        			
		        			//remove from cart
		        			+ "<td><form action='../../../GB/paymentService/products/deleteFromCart' method='post'><input type='hidden' name='cartId' value="+cartId+" ><button type='submit'>Remove</button></form></td></tr></tbody>";
		        	
		        	
		        }
				
				conn.close();
				
			} catch (SQLException e) {
				output += "Status : Error while fetching items";
				e.printStackTrace();
			}
		}
		
		
		return output;
        
		
	}
	
	//update the cart quantity
	public String updateCart( int cartId, String quantity) {
		String output = "";
		Connection conn = connect();
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			String updateCartSql = "UPDATE cart SET quantity='"+quantity+"' WHERE cartId ='"+cartId+"' ";
			try {

				Statement stmt = conn.createStatement();
				int status = stmt.executeUpdate(updateCartSql);
				
				if(status>0) {
					output += "Status : Successfully updated the cart";	
				}else {
					output += "Status : Error while updating";
				}
				
				

				} catch (SQLException e) {
					output += "Error while updating";
					e.printStackTrace();
				}	
		}
		
		
		return output;
	}
	
	
	//delete item from cart
	public String deleteFromCart( String cartId) {
		
		
		String output = "";
		Connection conn = connect();
		String deleteFromCartSql = "DELETE FROM cart WHERE cartId = '"+Integer.parseInt(cartId)+"' ";
		
		if (conn == null){
			output += "Status : Error while connecting to the database"; 
		}else {
			try {

			Statement stmt = conn.createStatement();
			int status = stmt.executeUpdate(deleteFromCartSql);
			
			
			if(status>0) {
				output += "Status : Successfully deleted from the cart";
			}else {
				output += "Status : Error while deleting";
			}
			

			} catch (SQLException e) {
				output += "Error while deleting";
				e.printStackTrace();
			}
		
		}
		
		
		
		return output;
	}
	
	
	
	//paymentSuccess  page
	public String paymentSuccessPage(String loggedUsername , String orderId) {
		
		String output = "";
		//fetch cart items and insert them into mydownloads table
		String loadCartItemsSql =  "SELECT * FROM cart c WHERE c.loggedUsername = '"+loggedUsername+"'";
		Connection conn = connect();
		
		
		try {
			PreparedStatement stmt;
			stmt = conn.prepareStatement(loadCartItemsSql);
			ResultSet rs = stmt.executeQuery();
						
			while (rs.next()) {
				
				String productId = rs.getString("productId");
				
				
				//InsertInto mydownloads
				String insertIntoMyDownloads = "INSERT INTO mydownloads (paidUsername, paidProductId) VALUES (?,?)";
				PreparedStatement pstmt2;
				pstmt2 = conn.prepareStatement(insertIntoMyDownloads,Statement.RETURN_GENERATED_KEYS);
				pstmt2.setString(1, loggedUsername);
				pstmt2.setString(2, productId);
				
				pstmt2.executeUpdate();
				//end insert into mydownloads
				
				
				
				
				//INSERT INTO COMPLETED ORDERS
				
				String insertToCompletedOrders = "INSERT INTO completedorders (orderId, productId, orderdate, orderedUser) VALUES (?,?,?,?)";
				PreparedStatement pstmt3;
				pstmt3 = conn.prepareStatement(insertToCompletedOrders,Statement.RETURN_GENERATED_KEYS);
					
					//get current date
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
					LocalDateTime now = LocalDateTime.now();  
				
				pstmt3.setString(1, orderId);
				pstmt3.setString(2, productId);
				pstmt3.setString(3, dtf.format(now));
				pstmt3.setString(4, loggedUsername);
				pstmt3.executeUpdate();
				
				
				
				
				//make the cart empty
				String makeCartEmptySql = "DELETE FROM cart WHERE loggedUsername = '"+loggedUsername+"' ";
				try {

					Statement stmt3 = conn.createStatement();
					stmt3.executeUpdate(makeCartEmptySql);
					

				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
			}
			
			
			
		} catch (SQLException e) {


			e.printStackTrace();
		}
		
		
		
		output += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
		
		output += "<h1>Payment was successful</h1><p><strong>Yay! It's done. Your&nbsp;<em>ORDER_ID</em>&nbsp;is "+orderId+"</strong><br></p>"
				+ "<p>Visit &nbsp;<a href='../../../GB/paymentService/products/myDownloads'>My Downloads</a>&nbsp;page to access your products any time!<br></p>";
		
		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
		
		return output;
		
	}
	
	
	//paymentUnsuccess  page
	public String paymentUnsuccessPage(String loggedUsername , String orderId) {
		
		String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		
		output += "<div class=\"container\"><div class=\"row\"><div class=\"col-md-4 col-lg-1\"></div><div class=\"col-md-4 col-lg-10 text-center\">";
		
		output += "<h1>Payment was unsuccessful</h1><p><strong>Oops! Something went wrong! Your&nbsp;<em>ORDER_ID</em>&nbsp;is "+orderId+"</strong><br></p>"
				+ "<p>If the issue persists, try again in few minitues. &nbsp;<a href='../../../GB/paymentService/products/'>Back To Home</a><br></p>";
		
		output += "</div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
		
		return output;
		
	}	
	
	
	//my downloads page
	public String myDownloads(String loggedUsername) {
		
		String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
	
		Connection conn = connect();
		
		if (conn == null){
			return "Error while connecting to the database for inserting."; 
		}else {
			
			
			output += "<div class=\"container\"> <div class=\"row\"> <div class=\"col-md-4 col-lg-1\"></div> <div class=\"col-md-4 col-lg-10 text-center\"> <h1 class=\"text-center\">My Downloads</h1><br>"
					+ "<div class=\"table-responsive\">"
					+ "<table class=\"table\">"
					+ "<thead> <tr> "
					+ "<th>Product Name</th> "
					+ "<th>Short Description</th> "
					+ "<th>Download Link<br></th> "
					+ "</tr> </thead> ";
			
			
			
			try {
				
				String getDownloadAccessSql = "SELECT * FROM mydownloads md WHERE md.paidUsername = '"+loggedUsername+"' ";
				
				Statement stmt;
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(getDownloadAccessSql);
				
				
				while (rs.next())
				{
					String paidProductId = rs.getString("paidProductId");
		        	
		        	Connection productConn = ManageProductModel.connect();
		        	//get Product details from product table
					String getProductDetailsSql = "SELECT * FROM products tp WHERE tp.productId = '"+paidProductId+"' ";
					Statement stmt2;
					stmt2 = productConn.createStatement();
					ResultSet rs2 = stmt2.executeQuery(getProductDetailsSql);
					
		        	while(rs2.next()) {
		        		
		        		String productName = rs2.getString("name");
		        		String shortDescription = rs2.getString("sDesc");
		        		String downloadLink = rs2.getString("downloadLink");
		        		
		        		output += "<tbody> <tr> <td>"+productName+"</td> <td>"+shortDescription+"</td> <td><a href ="+downloadLink+" target='_blank'>Click Here</a></td></tr></tbody>";
		        	
		        	}
		        	
		        	
		       
		        	
		        	
				}
				
				conn.close();
				
			} catch (SQLException e) {
				output = "Error while fetching products";
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			
			output += "</table></div></div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
				
			
		}
		
		return output;
		
		
	}
	
	
	
	
	//completed Orders List
	public String completedOrders(String loggedUsername){
		
		String output = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"> <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">";
		
		Connection conn = connect();
		
		if (conn == null){
			return "Error while connecting to the database for inserting."; 
		}else {
			
			
			output += "<div class=\"container\"> <div class=\"row\"> <div class=\"col-md-4 col-lg-1\"></div> <div class=\"col-md-4 col-lg-10 text-center\"> <h1 class=\"text-center\">All Completed Orders</h1><br>"
					+ "<div class=\"table-responsive\">"
					+ "<table class=\"table\">"
					+ "<thead> <tr> "
					+ "<th>Order Id</th> "
					+ "<th>Product ID</th> "
					+ "<th>Order Date<br></th>"
					+ "<th>Ordered Username<br></th>"
					+ "</tr> </thead> ";
			
			
			
			try {
				
				String fetchAllOrders = "SELECT * FROM completedorders co WHERE co.orderedUser = '"+loggedUsername+"' ";
				
				Statement stmt;
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(fetchAllOrders);
				
				
				while (rs.next())
				{
					
		        		
		        		String orderId = rs.getString("orderId");
		        		String productId = rs.getString("productId");
		        		String OrderedDate = rs.getString("orderdate");
		        		String orderedUsername = rs.getString("orderedUser");
		        		
		        		output += "<tbody> <tr> <td>"+orderId+"</td> <td>"+productId+"</td><td>"+OrderedDate+"</td><td>"+orderedUsername+"</td> </tr></tbody>";
			        	
		        	
				}
				
				conn.close();
				
			} catch (SQLException e) {
				output = "Error while fetching order list";
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			
			output += "</table></div></div><div class=\"col-md-4 col-lg-1\"></div></div></div>";
				
			
		}
		
		return output;
	}
	
	
	
	
	
}
