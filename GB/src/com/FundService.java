package com;

import java.sql.SQLException;

import javax.ws.rs.Consumes;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Fund;

//For JSON
import com.google.gson.*;

//For XML
	import org.jsoup.*;
	import org.jsoup.parser.*;
	import org.jsoup.nodes.Document;
	
	

@Path("/Fund")



public class FundService {
	
	
	

	Fund FundObj = new Fund();

	//Display all projects from database
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
	return FundObj.readItems();
	}
	
	
	
	//Display single view of  projects from database
	@POST
	@Path("/ProjectView")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_HTML)
	public String ProjectView(@FormParam("ProjectId") String Project_Id)
	throws SQLException{
		
		String output = FundObj.ProjectView(Project_Id);
		return output;
	}   
	
	

	
	//add to accepted project table
	@POST
	@Path("/acceptProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String acceptProject(
	@FormParam("randomProj_ID") String randomProj_ID,
	@FormParam("Project_Title") String Project_Title,
	@FormParam("Project_ShortDes") String Project_ShortDes,
	@FormParam("Project_LongDes") String Project_LongDes,
	@FormParam("Project_Srclink") String Project_Srclink,
	@FormParam("Project_Videolink") String Projrct_Videolink,
	@FormParam("acceptNote") String Project_AcceptedComment)
	{
	String output = FundObj.acceptProject(randomProj_ID, Project_Title, Project_ShortDes, Project_LongDes, Project_Srclink, Projrct_Videolink, Project_AcceptedComment);
	return output;
	}
	
	
	
	
	
	//add to rejected project table
	@POST
	@Path("/rejectProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String rejectProject(
	@FormParam("randomProj_ID") String randomProj_ID,
	@FormParam("Project_Title") String Project_Title,
	@FormParam("Project_ShortDes") String Project_ShortDes,
	@FormParam("Project_LongDes") String Project_LongDes,
	@FormParam("Project_Srclink") String Project_Srclink,
	@FormParam("Project_Videolink") String Project_Videolink,
	@FormParam("rejectNote") String Project_RejectComment)
	{
	String output = FundObj.rejectProject(randomProj_ID, Project_Title, Project_ShortDes, Project_LongDes, Project_Srclink, Project_Videolink, Project_RejectComment);
	return output;
	}
	
	
	
	
	
	//add to favourite project table
	@POST
	@Path("/favouriteProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String favouriteProject(
	@FormParam("randomProj_ID") String randomProj_ID,
	@FormParam("Project_Title") String Project_Title,
	@FormParam("Project_ShortDes") String Project_ShortDes,
	@FormParam("Project_LongDes") String Project_LongDes,
	@FormParam("Project_Srclink") String Project_Srclink,
	@FormParam("Project_Videolink") String Project_Videolink,
	@FormParam("favouriteNote") String Project_FavouriteComment)
	{
	String output = FundObj.favouriteProject(randomProj_ID, Project_Title, Project_ShortDes, Project_LongDes, Project_Srclink, Project_Videolink, Project_FavouriteComment);
	return output;
	}
	

	
	
	
	//update the project
	@PUT
	@Path("/updateItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
	//Convert the input string to a JSON object
	JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	String randomProj_ID = itemObject.get("randomProj_ID").getAsString();
	
	String acceptNote = itemObject.get("Project_AcceptedComment").getAsString();
	String output = FundObj.updateItem(randomProj_ID, acceptNote);
	return output;
	}
	

	
	// delete project 
	@DELETE
	@Path("/deleteProject")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(String itemData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
	//Read the value from the element <Project ID>
	String randomProj_ID = doc.select("randomProj_ID").text();
	String output = FundObj.deleteProject(randomProj_ID);
	return output;
	}
	

	

}
