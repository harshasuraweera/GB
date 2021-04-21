package com;

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

	
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readItems()
	{
	return FundObj.readItems();
	}

	
	
	@POST
	@Path("/insertItem")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertItem(
	@FormParam("Pname") String Pname,
	@FormParam("Pdescription") String Pdescription,
	@FormParam("Plink") String Plink,
	@FormParam("PVlink") String PVlink,
	@FormParam("PComment") String PComment)
	{
	String output = FundObj.insertItem(Pname, Pdescription, Plink, PVlink, PComment);
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
	String Pname = itemObject.get("Pname").getAsString();
	String Pdescription = itemObject.get("Pdescription").getAsString();
	String Plink = itemObject.get("Plink").getAsString();
	String PVlink = itemObject.get("PVlink").getAsString();
	String PComment = itemObject.get("PComment").getAsString();
	String output = FundObj.updateItem(PVID, Pname, Pdescription, Plink, PVlink, PComment);
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

}
