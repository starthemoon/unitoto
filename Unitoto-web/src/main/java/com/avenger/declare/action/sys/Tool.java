package com.avenger.declare.action.sys;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tool {
	private static final String subscriptionKey = "c5eed38a2a8b40408c8b4ac00298d849";
    	
	private static final String uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/tag";
	
	public static ArrayList<String> getLabelString(String photoURL) {
	    HttpClient httpclient = HttpClients.createDefault();
	    ArrayList<String> tags = new ArrayList<String>();
	       try
	       {
	            URIBuilder builder = new URIBuilder(uriBase);

	            // Request parameters. All of them are optional.
	            builder.setParameter("language", "zh");
	            
	            // Prepare the URI for the REST API call.
	            URI uri = builder.build();
	            HttpPost request = new HttpPost(uri);
	            
	            // Request headers.
	            request.setHeader("Content-Type", "application/octet-stream");
	            request.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);

	            // Request body.
	            File file = new File(photoURL);
		    @SuppressWarnings("deprecation")
		    FileEntity fileEntity = new FileEntity(file, "binary/octet-stream");
	            request.setEntity(fileEntity);

	            // Execute the REST API call and get the response entity.
	            HttpResponse response = httpclient.execute(request);
	            HttpEntity entity = response.getEntity();

	            if (entity != null) {
	                // Format and display the JSON response.
	                String jsonString = EntityUtils.toString(entity);
	                JSONObject json = new JSONObject(jsonString);
	                JSONArray jsonArray = json.getJSONArray("tags");
	                for (int i = 0; i < jsonArray.length(); i++) {
	                    tags.add(jsonArray.getJSONObject(i).getString("name"));
	                }
	            }
	        } catch (Exception e) {
	            // Display error message.
	            System.out.println(e.getMessage());
	        }
	       return tags;
	}

}
