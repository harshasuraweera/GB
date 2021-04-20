package com;

import model.user;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;
@Path("/Users")

public class userService {
	
	user userObj = new user();
	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readusers() {
		return userObj.readuser();
	}
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertuser(@FormParam("userName") String userName,
							 @FormParam("userEmail") String userEmail,
							 @FormParam("userMobile") String userMobile,
							 @FormParam("userPW")  String userPW)
	{
		String output = userObj.insertuser(userName, userEmail, userMobile, userPW);
		return output;
	}
	
	@PUT
	@Path("/") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN)
	public String updateuser(String userData) {
		
		//Convert the input string to a JSON object 
		JsonObject userObject = new JsonParser().parse(userData).getAsJsonObject();
		
		//Read the values from the JSON object
		String userID = userObject.get("userID").getAsString();
		String userName = userObject.get("userName").getAsString();
		String userEmail = userObject.get("userEmail").getAsString();
		String userMobile = userObject.get("userMobile").getAsString();
		String userPW = userObject.get("userPW").getAsString();
		String output = userObj.updateuser(userID, userName, userEmail, userMobile, userPW);
		return output;
	}
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteuser(String userData) {
		
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(userData, "", Parser.xmlParser());
		
		//Read the value from the element <itemID>
		String userID = doc.select("userID").text();
		String output = userObj.deleteuser(userID);
		return output;
	}
}
