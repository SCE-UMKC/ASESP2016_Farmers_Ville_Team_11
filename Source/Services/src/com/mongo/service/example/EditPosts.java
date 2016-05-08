package com.mongo.service.example;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;

/**
 * Servlet implementation class EditPosts
 */
@WebServlet("/EditPosts")
public class EditPosts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPosts() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MongoClientURI uri = new MongoClientURI("mongodb://user:password@ds011459.mlab.com:11459/farmville");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("temp");

		BasicDBObject newDocument1 = new BasicDBObject();
		BasicDBObject newDocument2 = new BasicDBObject();
		BasicDBObject newDocument3 = new BasicDBObject();
		BasicDBObject newDocument4 = new BasicDBObject();
		BasicDBObject searchQuery = new BasicDBObject().append("created", request.getParameter("created"));
		newDocument1.append("$set", new BasicDBObject().append("Product", request.getParameter("Product")));
		newDocument2.append("$set", new BasicDBObject().append("Quantity", request.getParameter("Quantity")));
		newDocument3.append("$set", new BasicDBObject().append("Description", request.getParameter("Description")));
		newDocument4.append("$set", new BasicDBObject().append("Image", request.getParameter("Image")));
		
		
		users.update(searchQuery, newDocument1);
		users.update(searchQuery, newDocument2);
		users.update(searchQuery, newDocument3);
		users.update(searchQuery, newDocument4);
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
		response.getWriter().write("updated successfully");
		
	}

	
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
		String created = (String) params.get("created");
		String Product = (String) params.get("Product");
		String Quantity = (String) params.get("Quantity");
		String Description = (String) params.get("Description");
		String Image = (String) params.get("Image");
		
		
		MongoClientURI uri = new MongoClientURI("mongodb://user:password@ds011459.mlab.com:11459/farmville");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("temp");

		BasicDBObject newDocument1 = new BasicDBObject();
		BasicDBObject newDocument2 = new BasicDBObject();
		BasicDBObject newDocument3 = new BasicDBObject();
		BasicDBObject newDocument4 = new BasicDBObject();
		BasicDBObject searchQuery = new BasicDBObject().append("created", created);
		newDocument1.append("$set", new BasicDBObject().append("Product", Product));
		newDocument2.append("$set", new BasicDBObject().append("Quantity", Quantity));
		newDocument3.append("$set", new BasicDBObject().append("Description", Description));
		newDocument4.append("$set", new BasicDBObject().append("Image", Image));
		
		
		WriteResult result1 = users.update(searchQuery, newDocument1);
		WriteResult result2 = users.update(searchQuery, newDocument2);
		WriteResult result3 = users.update(searchQuery, newDocument3);
		WriteResult result4 = users.update(searchQuery, newDocument4);

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");

		String result = result1.toString() + result2.toString() + result3.toString() + result4.toString();
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
