package com.aetionChanllenge.webService;


import static org.junit.Assert.*; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After; 
import org.junit.Rule;
import org.junit.rules.TestName;


public class TestWebService {
	
	@Rule public TestName name = new TestName();

    ServiceClient client;
   

	@Before
    public void init() {
      
		ServiceClient.getErrors().delete(0, ServiceClient.getErrors().length()); //clear the error
    }

	    @Test
	    public void testLogin() throws Exception
	    {
	        System.out.println("test method:" +  name.getMethodName() );
	        try{
	        	client.login();
	         }catch(Exception e)
	         {
	             handleException(e);
	         }
	        
	         System.out.println(name.getMethodName() + " is Done.");
	            
	    }
	
	    @Test
	    public void testLogin_badCredential() throws Exception
	    {
	        System.out.println("test method:" +  name.getMethodName() );
	        try{
	        	client.login("what", "ever");
	         }catch(Exception e)
	         {
	             handleException(e);
	         }
	        
	         System.out.println(name.getMethodName() + " is Done.");
	            
	    }
	    
	    
	    @Test
	    public void testCreateUsers() throws Exception
	    {
	        System.out.println("test method:" +  name.getMethodName() );
	        try{
	        	ServiceClient.createUsers("users.txt");
	         }catch(Exception e)
	         {
	             handleException(e);
	         }
	        
	         System.out.println(name.getMethodName() + " is Done.");
	            
	    }

	    @Test
	    public void testSearch() throws Exception
	    {
	        System.out.println("test method:" +  name.getMethodName() );
	        try{
	        	ServiceClient.search(20, 60);
	         }catch(Exception e)
	         {
	             handleException(e);
	         }
	        
	         System.out.println(name.getMethodName() + " is Done.");
	            
	    }
	    

	    @Test
	    public void testGetUser() throws Exception
	    {
	        System.out.println("test method:" +  name.getMethodName() );
	        try{
	        	ServiceClient.getUser(2);
	         }catch(Exception e)
	         {
	             handleException(e);
	         }
	        
	         System.out.println(name.getMethodName() + " is Done.");
	            
	    }
	    
	    @Test
	    public void testModifyUser() throws Exception
	    {
	        System.out.println("test method:" +  name.getMethodName() );
	        try{
	        	ServiceClient.modifyUser(1, new User("george@example.com", "newGoerge", "newPearson", 55));
	         }catch(Exception e)
	         {
	             handleException(e);
	         }
	        
	         System.out.println(name.getMethodName() + " is Done.");
	            
	    }
	    
	     @After
	    public void tearDown() throws Exception{
    	   
    	 if (ServiceClient.getErrors().length() > 0)
			 fail(ServiceClient.getErrors().toString());
	    	
	    }
	
	    private void handleException(Exception e)
	    {   
	        e.printStackTrace();
	        fail("Exception is thrown.");
            
	    }
	

}
