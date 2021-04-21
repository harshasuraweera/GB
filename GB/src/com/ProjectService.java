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
	
	//Add New Projects :
	@POST
	@Path("/AddProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String submitProjects(
			
			@FormParam("Project_Title") String Project_Title,
			@FormParam("Project_ShortDes") String Project_ShortDes,
			@FormParam("Project_LongDes") String Project_LongDes,
			@FormParam("Project_Srclink") String Project_Srclink,
			@FormParam("Project_Videolink") String Project_Videolink) throws SQLException{
	
		
		
		//String output = product.addProduct(productId,title,sDesc,lDesc,price,downloadLink);
		
		String output = project.AddProjects(Project_Title,Project_ShortDes ,Project_LongDes ,Project_Srclink ,Project_Videolink );
		return output;
	}
	
	
	//Display All Project Details :
	@GET
	@Path("/readProjects")
	@Produces(MediaType.TEXT_HTML)
	public String readProjects() throws SQLException{
		
		String output = project.readProjects();
		
		return output;
	}
	
	@PUT
	@Path("/updateProjects")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateCart(String projectData)
	{
		//Convert the input string to a JSON object
		JsonObject projectObject = new JsonParser().parse(projectData).getAsJsonObject();
		
		//Read the values from the JSON object
		String randomProj_ID = projectObject.get("randomProj_ID").getAsString();
		String Project_Title = projectObject.get("Project_Title").getAsString();
		String Project_ShortDes = projectObject.get("Project_ShortDes").getAsString();
		String Project_LongDes = projectObject.get("Project_LongDes").getAsString();
		String Project_Srclink = projectObject.get("Project_Srclink").getAsString();
		String Project_Videolink = projectObject.get("Project_Videolink").getAsString();
		
		
		String output = project.updateProjects(randomProj_ID, Project_Title,Project_ShortDes,Project_LongDes,Project_Srclink,Project_Videolink);
		return output;
	}
	
	@DELETE
	@Path("/deleteprojects")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_HTML)
	public String deleteFromCart(String projectData) throws SQLException{
		
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(projectData, "", Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String deleteProjectId = doc.select("randomProj_ID").text();
		
		String output = project.deleteProject(deleteProjectId);
		
		return output;
	}
	
	
}
