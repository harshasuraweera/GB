package com;

import model.ManageProductModel;

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



@Path("/product_view")
public class ProductService {
	
	ManageProductModel product = new ManageProductModel();
	@GET
	  @Path("/")
	  @Produces(MediaType.TEXT_HTML)
	  public String readItems() throws SQLException{
	    
	    return product.addProductInterface();
	    
	  }
	
	@POST
	@Path("/AddProduct")
	@Produces(MediaType.TEXT_HTML)
	public String addProduct() throws SQLException{
		
		//String output = product.addProduct(productId,title,sDesc,lDesc,price,downloadLink);
		return "done";
	}
	
	@POST
	@Path("/load")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String loadProducts() throws SQLException{
		
		String output = product.loadProducts();
		
		return output;
	}
	
	

}
