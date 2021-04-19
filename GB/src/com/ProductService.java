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
	
	
	@POST
	@Path("/AddProduct")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String addProduct(@FormParam("productId") String productId,
		 @FormParam("title") String title,
		 @FormParam("sDesc") String sDesc,
		 @FormParam("lDesc") String lDesc,
		 @FormParam("price") String price,
		@FormParam("downloadLink") String downloadLink)
	throws SQLException{
		
		String output = product.addProduct(productId,title,sDesc,lDesc,price,downloadLink);
		return output;
	}
	
	
	
	

}
