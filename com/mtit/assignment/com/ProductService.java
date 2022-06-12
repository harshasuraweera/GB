package com.mtit.assignment.com;

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
	
	//Products Details will be fetched
	@GET
	  @Path("/")
	  @Produces(MediaType.TEXT_HTML)
	  public String readItems() throws SQLException{
	    
	    return product.addProductInterface();
	    
	  }
	
	
	//Button "Add Products" will be called this.
	//User can add new products using this.
	@POST
	@Path("/AddProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String addProduct(
			 @FormParam("title") String title,
			 @FormParam("sDesc") String sDesc,
			 @FormParam("lDesc") String lDesc,
			 @FormParam("price") String price,
			 @FormParam("downloadLink") String downloadLink) throws SQLException{
		
		String output = product.addProduct(title,sDesc,lDesc,price,downloadLink);
		return output;
	}
	
	//All the submitted products will be displayed using a table
	@GET
	@Path("/load")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String loadProducts() throws SQLException{
		
		String output = product.loadProducts();
		
		return output;
	}
	
	//Delete relevant product
	@DELETE
	@Path("/removeProduct")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String removeProduct(String productData) throws SQLException{
		
		//Convert the input string to an XML document
				Document doc = Jsoup.parse(productData, "", Parser.xmlParser());
				
				//Read the value from the element <itemID>
				String deleteproductId= doc.select("productId").text();
				
				String output = product.deleteProduct(deleteproductId);
				
				return output;
	}
	
	//Update relevant product
	@PUT
	@Path("/updateDetails")
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN)
	public String updateProduct(String productData)
	{
		
		
	//Convert the input string to a JSON object
	 JsonObject productObject = new JsonParser().parse(productData).getAsJsonObject();
	 
	//Read the values from the JSON object
	 String productId = productObject.get("productId").getAsString();
	 String title = productObject.get("title").getAsString();
	 String sDesc = productObject.get("sDesc").getAsString();
	 String lDesc = productObject.get("lDesc").getAsString();
	 String price = productObject.get("price").getAsString();
	 String downloadLink = productObject.get("downloadLink").getAsString();
	 String output = product.updateProduct(productId, title, sDesc, lDesc, price,downloadLink);
	 
	 
	return output;
	}
	
	
}
