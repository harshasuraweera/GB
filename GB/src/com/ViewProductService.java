package com;
import model.BuyProductsModel;

import java.sql.SQLException;

//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@SuppressWarnings("unused")
@Path("/products")
public class ViewProductService {
	
	BuyProductsModel buyProductsModel =  new BuyProductsModel();
	
	 
	/* Working as the application HomePage.
	 * In here all the products from products related database will be fetched
	 * */
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems(){
		
		return buyProductsModel.fetchAllProducts("all");
		
	}
	
	
	/* Once clicked the "Add To Cart" button this will be called. 
	 * In here the selected product is added to the cart table under the UserName.
	 * Then it will open a new page and user can choose to checkout or continue shopping.
	 * */
	@POST
	@Path("/addToCart")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String addToCart(@FormParam("productId") String productId,
		 @FormParam("productName") String productName,
		 @FormParam("shortDescription") String shortDescription,
		 @FormParam("quantity") String quantity,
		 @FormParam("price") String price) throws SQLException{
		
		String output = buyProductsModel.addToCart("U001", productId, productName, shortDescription, quantity, price);
		return output;
	}
	
	
	/* Removing some products from the cart will be done by this
	 * Once clicked the remove button, the product will delete from the cart table
	 * */
	@POST
	@Path("/removeFromCart")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String removeFromCart(@FormParam("cartId") String cartId) throws SQLException{
		
		String output = buyProductsModel.deleteFromCart(cartId);
		return output;
	}
	
	
	/* If the user clicked the checkout button, he will redirected to shopping cart through this.
	 * In here he can remove items from cart and proceed to payment page.
	 * Used PayHere SandBox environment to perform the payment and according to the payment status rest of the thing will happen.
	 * */
	@POST
	@Path("/cart")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String loadCart() throws SQLException{
		
		String output = buyProductsModel.loadCartItems("U001");
		
		return output;
	}
	
	
	
	/* If the payment gateway return that the payment was successful, this will load.
	 * Order ID is coming through IPG via URL
	 * User can click "My Downloads" button to view his purchased items
	 * */
	@GET
	@Path("/paymentSuccess")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String paymentSuccess(@QueryParam("order_id") String order_id) throws SQLException{
		
		String output = buyProductsModel.paymentSuccessPage("U001", order_id);
		return output;
	}
	
	
	/* If the payment gateway return that the payment was unsuccessful, this will load
	 * Order ID is coming through IPG via URL
	 * User can click "Try Again" button to do the payment process again
	 * */
	@GET
	@Path("/paymentUnsuccess")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String paymentUnsuccess(@QueryParam("order_id") String order_id) throws SQLException{
		
		String output = buyProductsModel.paymentUnsuccessPage("U001", order_id);
		return output;
	}
	
	
	/* If the payment gateway return that the payment was unsuccessful, this will load
	 * Order ID is coming through IPG via URL
	 * User can click "Try Again" button to do the payment process again
	 * */
	@GET
	@Path("/myDownloads")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String myDownloads(@QueryParam("order_id") String order_id) throws SQLException{
		
		String output = buyProductsModel.myDownloads("U001");
		return output;
	}
	
	

}
