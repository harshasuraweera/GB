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

@Path("/products")
public class ViewProductService {
	
	BuyProductsModel buyProductsModel =  new BuyProductsModel();
	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems(){
		
		return buyProductsModel.fetchAllProducts("all");
		
	}
	
	
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
	
	@POST
	@Path("/cart")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String loadCart() throws SQLException{
		
		String output = buyProductsModel.loadCartItems("U001");
		
		return output;
	}
	
	
	@POST
	@Path("/payment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String addToCart1(@FormParam("productId") String productId,
		 @FormParam("productName") String productName,
		 @FormParam("shortDescription") String shortDescription,
		 @FormParam("quantity") String quantity,
		 @FormParam("price") String price) throws SQLException{
		
		String output = buyProductsModel.addToCart("U001", productId, productName, shortDescription, quantity, price);
		return output;
	}
	
	@POST
	@Path("/removeFromCart")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String removeFromCart(@FormParam("cartId") String cartId) throws SQLException{
		
		String output = buyProductsModel.deleteFromCart(cartId);
		return output;
	}
	

}
