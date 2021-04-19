package com;

import model.SubmitProjectsModel;

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


@Path("/projects")
public class ProjectService {
	
	SubmitProjectsModel project = new SubmitProjectsModel();
	@GET
	  @Path("/")
	  @Produces(MediaType.TEXT_HTML)
	  public String readItems() throws SQLException{
	    
	    return project.submitProjectsInterface();
	    
	  }
	
	
	@POST
	@Path("/AddProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String submitProjects() throws SQLException{
	
		
		
		//String output = product.addProduct(productId,title,sDesc,lDesc,price,downloadLink);
		return "done";
		//String output = project.submitProjectsInterface(randomProj_ID,Project_Title,Project_ShortDes ,Project_LongDes ,Project_Srclink ,Project_Videolink );
		//return output;
	}
	
	@POST
	@Path("/readProjects")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String readProjects() throws SQLException{
		
		String output = project.readProjects();
		
		return output;
	}
	
	
	
	
	

}
