package com.aetionChanllenge.webService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.StringTokenizer;

import org.apache.http.entity.StringEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.*;

import org.apache.http.HttpEntity;
//import org.json.*;
import org.json.JSONObject;


public class ServiceClient {

	private static final String SERVICE_URL = "http://qa-takehome-ubccre.dev.aetion.com:4440";
	private static final String SERVICE_URL_LOGIN = "http://qa-takehome-ubccre.dev.aetion.com:4440/login";
	private static final String SERVICE_URL_USER = "http://qa-takehome-ubccre.dev.aetion.com:4440/user";
	private static final String SERVICE_URL_SEARCH = "http://qa-takehome-ubccre.dev.aetion.com:4440/search";
	
	 
    private static final String  USER_NAME = "admin";
    private static final String PASSWORD = "HG9Twy4LkPtoowdpyKnc";
    
	private static String auth_token;
	
	//hold all the error messages 
	public static StringBuffer errors = new StringBuffer(); 
	  
    
	
    public static void login() throws Exception
    {
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

	    try {
	        HttpPost request = new HttpPost(SERVICE_URL_LOGIN);
	        String json = "{\"username\":\"" + USER_NAME + "\",\"password\":\""  + PASSWORD + "\"} ";
	        StringEntity params =new StringEntity(json);
	      
	       // StringEntity params =new StringEntity("{\"username\":\"admin\",\"password\":\"HG9Twy4LkPtoowdpyKnc\"} ");
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);

	        // handle response 
	            HttpEntity entity = response.getEntity();
		    BufferedReader br;
		    if (entity != null) {
		          try {
		          	
		        	 br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

					String output;
					System.out.println("response from Server .... \n");
					while ((output = br.readLine()) != null) {
						System.out.println(output);
						//Parse the authentication token
						auth_token = parseToken(output);
					}//while

		        }catch (Exception ex) {
	               // handle exception here
			    } finally {
			        httpClient.getConnectionManager().shutdown(); //Deprecated
			    }
		    }   

	    }catch(Exception e)
	    {
	    	
	    }
    }    
    	
    public static void login(String userName, String password) throws Exception
    {
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

	    try {
	        HttpPost request = new HttpPost(SERVICE_URL_LOGIN);
	        String json = "{\"username\":\"" + userName + "\",\"password\":\""  + password + "\"} ";
	        StringEntity params =new StringEntity(json);
	        request.addHeader("content-type", "application/json");
	        request.setEntity(params);
	        HttpResponse response = httpClient.execute(request);

	        // handle response 
	            HttpEntity entity = response.getEntity();
		    BufferedReader br;
		    if (entity != null) {
		          try {
		          	
		        	 br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

					String output;
					System.out.println("response from Server .... \n");
					
					while ((output = br.readLine()) != null) {
						System.out.println(output);
						if (userName.equals(USER_NAME) && password.equals(PASSWORD) )
						{
							//Parse the authentication token
							auth_token = parseToken(output);
						}else  //HERE SHALL GET invalid Credentials in response
						{ 
							if (!output.contains("Invalid Credentials"))
							    errors.append("Invalid Credentials");
							return;
						}
						//Parse the authentication token
						auth_token = parseToken(output);
					}//while

		        }catch (Exception ex) {
	               // handle exception here
			    } finally {
			        httpClient.getConnectionManager().shutdown(); //Deprecated
			    }
		    }   

	    }catch(Exception e)
	    {
	    	
	    }
    }    
    	
    
    public static void createUser( )
    {  
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

    	 try {
 	        HttpPost request = new HttpPost(SERVICE_URL_USER);
 	         request.addHeader("content-type", "application/json");
 	        request.addHeader("X-Auth-Token", auth_token);
 	        
 	       StringEntity params =new StringEntity("{\"email\":\"peta@example.org\",\"first_name\":\"Peta\", \"last_name\":\"Francis\",\"age\":\"34\"} ");
  	      
 	        request.setEntity(params);
 	        HttpResponse response = httpClient.execute(request);

 	        // handle response 
 		      HttpEntity entity = response.getEntity();
 		    BufferedReader br;
 		    if (entity != null) {
 		        //InputStream instream = entity.getContent();
 		        try {
 		            // do something useful
 		        	
 		        	 br = new BufferedReader(
 	                        new InputStreamReader((response.getEntity().getContent())));

 					String output;
 					System.out.println("response from Server .... \n");
 					while ((output = br.readLine()) != null) {
 						System.out.println(output);
 					}//while

 		        }catch (Exception ex) {
 	               // handle exception here
 		        	ex.printStackTrace();
 			    } finally {
 			        httpClient.getConnectionManager().shutdown(); //Deprecated
 			    }
 		    }   

 	    }catch(Exception e)
 	    {
 	      e.printStackTrace();
 	    }
    }
    
 // public static void createUser(User user)
    public static void createUsers(String userFile)
    {   //First, get all user data
    	
    	List<User> users = new ArrayList<User>();
    	users = readInUserData(userFile);
    	System.out.println("user count:" + users.size());
    	
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

    	 try {
 	        HttpPost request = new HttpPost(SERVICE_URL_USER);
 	         request.addHeader("content-type", "application/json");
 	        request.addHeader("X-Auth-Token", auth_token);
 	        
 	       // StringEntity params =new StringEntity("{\"email\":\"peta@example.org\",\"first_name\":\"Peta\", \"last_name\":\"Francis\",\"age\":\"34\"} ");
  	      
 	        for (User user: users)
 	        {	
 	        	String jsonString = "{\"email\":\""+ user.getEmail() + "\",\"first_name\":\"" + user.getFirstName() 
 	        			+ "\", \"last_name\":\"" + user.getLastName() + "\",\"age\":\" " + user.getAge() + "\"} ";
 	        	
 	        	System.out.println("See jsonString:" + jsonString); 	        
 	        	StringEntity params =new StringEntity(jsonString);
 	        	request.setEntity(params);
 	        	HttpResponse response = httpClient.execute(request);

 	        // handle response 
 	        	handleRepsonse_newUser(response, user);
 		    }   

 	    }catch(Exception e)
 	    {
 	      e.printStackTrace();
 	    }
    }
    
    
    private static void handleRepsonse_newUser(HttpResponse response, User user)
    {
    	 HttpEntity entity = response.getEntity();
		    BufferedReader br;
		    if (entity != null) {
		        try {
		        	 br = new BufferedReader(
	                        new InputStreamReader((response.getEntity().getContent())));

					String output;
					System.out.println("response from Server .... \n");
					while ((output = br.readLine()) != null) {
						System.out.println(output);
						//Verify result
						verifyResponse_newUser(output, user);
					}//while

		        }catch (Exception ex) {
		        	ex.printStackTrace();
			    }
		    }  
    }
    
    private static void verifyResponse_newUser(String response,User user)
    {
    	JSONObject jo = new JSONObject(response);
    	//Verfiy that id is added in the response, and all other fields are identical between these 2
    	if (jo.getInt("id") < 1)
    		errors.append("No ID is returned in the repsonse for the newly created user.");
    	if (!jo.getString("email").equals(user.getEmail()))
    		errors.append("User's email is broken. Original email/bad email:" + user.getEmail()  +"/" + jo.getString("email") +"\n");
    	if (!jo.getString("first_name").equals(user.getFirstName()))
    		errors.append("User's first_name is broken.");
    	if (!jo.getString("last_name").equals(user.getLastName()))
    		errors.append("User's last_name is broken.");
    	if (jo.getInt("age") != user.getAge())
    		errors.append("User's age is broken.");
    	
    }
    
    public static void getUser(int userID)
    {   
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

    	HttpGet getRequest = new HttpGet(SERVICE_URL_USER +"/" + userID);
		getRequest.addHeader("accept", "application/json");
		getRequest.addHeader("X-Auth-Token", auth_token);
	
    	
    	 try {
 	        
 	        HttpResponse response = httpClient.execute(getRequest);

 	        // handle response 
 	       
 		      HttpEntity entity = response.getEntity();
 		    BufferedReader br;
 		    if (entity != null) {
 		        //InputStream instream = entity.getContent();
 		        try {
 		            // do something useful
 		        	
 		        	 br = new BufferedReader(
 	                        new InputStreamReader((response.getEntity().getContent())));

 					String output;
 					System.out.println("response from Server .... \n");
 					while ((output = br.readLine()) != null) {
 						System.out.println(output);
 						//verify result
 						
 					}//while

 		        }catch (Exception ex) {
 	               // handle exception here
 		        	ex.printStackTrace();
 			    } finally {
 			        httpClient.getConnectionManager().shutdown(); //Deprecated
 			    }
 		    }   

 	    }catch(Exception e)
 	    {
 	      e.printStackTrace();
 	    }
    }
    
    
    public static void modifyUser(int userID, User newData)
    {   StringEntity params = null;
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

    	HttpPut putRequest = new HttpPut(SERVICE_URL_USER +"/" + userID);
		putRequest.addHeader("accept", "application/json");
		 putRequest.addHeader("Content-Type", "application/json");
		putRequest.addHeader("X-Auth-Token", auth_token);
	    /*
		try{
		    params =new StringEntity("{\"id\":\"1\",\"email\":\"peta@example.org\",\"first_name\":\"Peta\", \"last_name\":\"Francis\",\"age\":\"18\"} ");
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		String jsonString = "{\"id\":" + userID + ",\"email\":\""+ newData.getEmail() + "\",\"first_name\":\"" + newData.getFirstName() 
     			+ "\", \"last_name\":\"" + newData.getLastName() + "\",\"age\":\" " + newData.getAge() + "\"} ";
     	
     	System.out.println("See jsonString:" + jsonString); 
     	try{
     		 params =new StringEntity(jsonString);
     	     
     	}catch(Exception e)
		{
			e.printStackTrace();
		}
     	
		
	        putRequest.setEntity(params);
	      
    	
    	 try {
 	        
 	        HttpResponse response = httpClient.execute(putRequest);

 	        // handle response 
 	       
 		      HttpEntity entity = response.getEntity();
 		    BufferedReader br;
 		    if (entity != null) {
 		        try {
 		        	 br = new BufferedReader(
 	                        new InputStreamReader((response.getEntity().getContent())));

 					String output;
 					System.out.println("response from Server .... \n");
 					while ((output = br.readLine()) != null) {
 						System.out.println(output);
 						
 						//verify result here
 						JSONObject jo = new JSONObject(output);
 				    	//Verfiy that id is added in the response, and all other fields are identical between these 2
 				    	if (jo.getInt("id") != userID)
 				    		errors.append("userID is incorrect.");
 				    	if (!jo.getString("email").equals(newData.getEmail()))
 				    		errors.append("User's email is not modified. old/new" +  jo.getString("email") +  "/" + newData.getEmail() + "\n");
 				    	if (!jo.getString("first_name").equals(newData.getFirstName()))
 				    		errors.append("User's first_name is not modified." + "\n");
 				    	if (!jo.getString("last_name").equals(newData.getLastName()))
 				    		errors.append("User's last_name is not modified." + "\n");
 				    	if (jo.getInt("age") != newData.getAge())
 				    		errors.append("User's age is not modified." + "\n");
 						
 					}//while

 		        }catch (Exception ex) {
 	               // handle exception here
 		        	ex.printStackTrace();
 			    } finally {
 			        httpClient.getConnectionManager().shutdown(); //Deprecated
 			    }
 		    }   

 	    }catch(Exception e)
 	    {
 	      e.printStackTrace();
 	    }
    }
    
    
    
    public static void search(int startAge, int endAge)
    {   
    	HttpClient httpClient = HttpClientBuilder.create().build(); 

    	 try {
 	        HttpPost request = new HttpPost(SERVICE_URL_SEARCH);
 	         request.addHeader("content-type", "application/json");
 	        request.addHeader("X-Auth-Token", auth_token);
 	        
 	        String jsonString = "{\"start_age\":" + startAge + "," +  "\"end_age\":" + endAge + "}";
 	        System.out.println("See jsonString:" + jsonString);
 	         StringEntity params =new StringEntity(jsonString);
 	       //StringEntity params =new StringEntity("{\"start_age\":\"20\",\"end_age\":\"60\"}");
  	      
 	        request.setEntity(params);
 	        HttpResponse response = httpClient.execute(request);

 	        // handle response 
 		      HttpEntity entity = response.getEntity();
 		    BufferedReader br;
 		    if (entity != null) {
 		        //InputStream instream = entity.getContent();
 		        try {
 		           
 		        	 br = new BufferedReader(
 	                        new InputStreamReader((response.getEntity().getContent())));

 					String output;
 					System.out.println("response from Server .... \n");
 					while ((output = br.readLine()) != null) {
 						System.out.println(output);
 						System.out.println("see status:" + response.getStatusLine()	);
 						//Verify result: Here to save time, I just verify in the roughest way ever. 
 						if (startAge < 22 && endAge > 53 )  //We definetely shall see some result back
 						{ 
 							if (response.getStatusLine().toString().contains("404 Not Found"))
 								errors.append("Search is not working.");
 						}
 					
 					}//while

 		        }catch (Exception ex) {
 	               // handle exception here
 		        	ex.printStackTrace();
 			    } finally {
 			        httpClient.getConnectionManager().shutdown(); //Deprecated
 			    }
 		    }   

 	    }catch(Exception e)
 	    {
 	      e.printStackTrace();
 	    }
    }
    
    
    
    public static String parseToken(String jsonString)
    {    System.out.println("parseToken.." + jsonString);
         
          try{
        	  final JSONObject obj = new JSONObject(jsonString);
        	  System.out.println("See token:" + obj.getString("token")  );
              return obj.getString("token") ;
          }catch(Exception pe){
    		
             pe.printStackTrace();
             return null;
          }
          
      
    }	
    
    
    private static List<User> readInUserData(String file)
    {
    	List<User> users = new ArrayList<User>();
    	
    	BufferedReader br = null;
    	try{
    			br = new BufferedReader(new FileReader(file));
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	try {
    	   
    	    String line = br.readLine();

    	    while (line != null) {
    	        line = br.readLine();
    	        if (line != null)
    	           users.add(parse(line));
    	    }
    	  
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	} finally {
    		try{
    	       br.close();
    		}catch(Exception e)
    		{
    			
    		}
    	}
    	return users;
    }
    
    private static User parse(String userData)
    {   System.out.println("See userData:" + userData);
        List<String> fields = new ArrayList<String>();
    	StringTokenizer st = new StringTokenizer(userData); //"\\t");
    	 while (st.hasMoreTokens()) {
    	    // System.out.println(st.nextToken());
    	     fields.add(st.nextToken().trim());
    	 }
    	 
    	 return (new User((String)fields.get(0), (String)fields.get(1), (String)fields.get(2), Integer.parseInt(fields.get(3))));
    }
    
    public static StringBuffer getErrors()
    {
    	return errors;
    }
}
