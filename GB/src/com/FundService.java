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
	@FormParam("PVID") String PVID,
	@FormParam("comment") String PComment)
	{
	String output = FundObj.insertComment(PVID, PComment);
	return output;
	}
	
	
	//add to rejected project table
	@POST
	@Path("/rejectProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String rejectProject(
	@FormParam("PVID") String PVID,
	@FormParam("comment") String PComment)
	{
	String output = FundObj.insertComment(PVID, PComment);
	return output;
	}
	
	
	//add to favourite project table
	@POST
	@Path("/favouriteProject")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String favouriteProject(
	@FormParam("PVID") String PVID,
	@FormParam("comment") String PComment)
	{
	String output = FundObj.insertComment(PVID, PComment);
	return output;
	}
	
	@POST
	@Path("/insertComment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertComment(
	@FormParam("PVID") String PVID,
	@FormParam("comment") String PComment)
	{
	String output = FundObj.insertComment(PVID, PComment);
	return output;
	}
	
	
	
	@PUT
	@Path("/updateItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItem(String itemData)
	{
	//Convert the input string to a JSON object
	JsonObject itemObject = new JsonParser().parse(itemData).getAsJsonObject();
	//Read the values from the JSON object
	String PVID = itemObject.get("PVID").getAsString();
	String PComment = itemObject.get("PComment").getAsString();
	String output = FundObj.updateItem(PVID, PComment);
	return output;
	}
	
	
	
	@DELETE
	@Path("/deleteItem")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteItem(String itemData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
	//Read the value from the element <Project ID>
	String PMID = doc.select("PMID").text();
	String output = FundObj.deleteItem(PMID);
	return output;
	}
	
	
	
	@DELETE
	@Path("/deleteProject")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteProject(String itemData)
	{
	//Convert the input string to an XML document
	Document doc = Jsoup.parse(itemData, "", Parser.xmlParser());
	//Read the value from the element <Project ID>
	String PVID = doc.select("PVID").text();
	String output = FundObj.deleteProject(PVID);
	return output;
	}

}
