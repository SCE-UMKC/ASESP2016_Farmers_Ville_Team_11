package com.mongo.service.example;


import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;

import org.json.*;
//import com.ibm.json.java.JSON;
//import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

/**
 * Servlet implementation class UserServlet
 */
@Path("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		MongoClientURI uri = new MongoClientURI("mongodb://naga:naga@ds019990.mlab.com:19990/musicstudio");
		MongoClientURI uri = new MongoClientURI("mongodb://user:password@ds011459.mlab.com:11459/farmville");
		
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
//		DBCollection users = db.getCollection("roombooking");
		DBCollection users = db.getCollection("logindetails");
		BasicDBObject query = new BasicDBObject();
//		System.out.println("value of email: " +request.getParameter("email"));
//		query.put("email", request.getParameter("email"));
		query.put("email", request.getParameter("email"));
		query.put("password", request.getParameter("password"));
		DBCursor docs = users.find(query);
		response.getWriter().write(docs.toArray().toString());
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuilder buffer = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		System.out.println(data);

		JSONObject params = new JSONObject(data);
		BasicDBObject user1 = new BasicDBObject();
		
		for(Object key:params.keySet().toArray())
		{
			user1.put(key.toString(),params.get(key.toString()));
		}
		System.out.println(user1.toJson());
		
//		MongoClientURI uri = new MongoClientURI("mongodb://naga:naga@ds019990.mlab.com:19990/musicstudio");
		MongoClientURI uri = new MongoClientURI("mongodb://user:password@ds011459.mlab.com:11459/farmville");
		MongoClient client = new MongoClient(uri);

		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("logindetails");
		WriteResult result = users.insert(user1);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		response.getWriter().write(result.toString());
	}

	@Override
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doOptions(arg0, response);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, HEAD, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
	}
}

