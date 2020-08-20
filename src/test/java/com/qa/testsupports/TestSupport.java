package com.qa.testsupports;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.jayway.jsonpath.JsonPath;

public class TestSupport {
	
	private static final HashMap<List<String>,Float> combinedRatesMap = new HashMap<List<String>, Float>(); 	
	
	private static HashMap<String,String> headerMap;
	
	private static String Source = null;
	
	private static String Dest = null;

	
	// Header1 for GET Call
	public static HashMap<String, String> setUpFirstPageHeader() {
		
		headerMap = new HashMap<String, String>();
		
		headerMap.put("apikey","ixiweb!2$");
		
		return headerMap;
	}
	
	
	// Header2 for GET Call
    public static HashMap<String, String> setUpSecondPageHeader() {
		
		headerMap = new HashMap<>();
		
		headerMap.put("apikey","ixiweb!2$");
		
		headerMap.put("clientid","ixiweb");
		  
		return headerMap;
	}

    
    
   // Extract Search token from first response
    public static String extractSearchToken(CloseableHttpResponse httpresponse) throws ParseException, IOException {
			
		 String response = EntityUtils.toString(httpresponse.getEntity());
		  
		 JSONObject jsonObject = new JSONObject(response);
			
		 return jsonObject.get("searchToken").toString();
	  
	}
    
   
    //Writes search token from first response to token properties file
    public static void sendTokenToProp (String token , Properties prop ,FileOutputStream fos) throws IOException {
    		
    	prop.setProperty("Search-token",token);
    			
    	prop.store(fos,"token");		
    }
    	
    
    	
    //Returns search token for second request
    public static String extractTokenFromProp(Properties prop) {
    	
    	return prop.getProperty("Search-token");
    	
    }
    
    public static String formDynamicUri(Properties prop) {
    	
    	Source = TestSupport.getUriParams("Source",prop);
    	
    	Dest = TestSupport.getUriParams("Dest",prop);
    	
    	String DepDate = TestSupport.getUriParams("DepDate",prop);
    	
    	String ArrDate = TestSupport.getUriParams("ArrDate",prop);
    	
    	String Adults = TestSupport.getUriParams("Adults",prop);
 	
    	String url = "origin="+Source+"&destination="+Dest+"&leave="+DepDate+"&return="+ArrDate+"&adults="+Adults+"&children=0&infants=1&class=e&version=2.0&searchSrc=ixibook";
    			
    	return url;
    }
    
    public static String getUriParams(String key , Properties prop) {
    	
    	return prop.getProperty(key);
    }
    	
    
    // Finds top 20 cheapest flights combinations
    public static void findTopCheapestFlights(CloseableHttpResponse httpresponse) throws ParseException, IOException {
    	
    	String totalResponse = EntityUtils.toString(httpresponse.getEntity());
    	
    	//Using JsonPath Library to parse JSON file
    	Map<String,JSONObject> combinedFlightsData = JsonPath.parse(totalResponse).read("$.results.1026.comR.groupedFares");

      	for (String sourceToDestFlights : combinedFlightsData.keySet()) {
    	
			Map<String, Object> destToSourceFlights = (Map<String, Object>) combinedFlightsData.get(sourceToDestFlights);
      	  	    
      	    for (String eachreturnFl : destToSourceFlights.keySet()) {
      	    	
				Map<String,Object> eachRF  = (Map<String, Object>) destToSourceFlights.get(eachreturnFl);
      	    	    	    	
      	    	ArrayList<String> tempList = new ArrayList<>();
    			
      	    	tempList.add(sourceToDestFlights);
    			
      	    	tempList.add(eachreturnFl);
    			
    			combinedRatesMap.put(tempList,Float.valueOf(eachRF.get("total").toString()));
      	    }
      	}
 	
    		System.out.println("Total Combinations are : " + combinedRatesMap.size()); 
    		
    		List<List<String>> combinedFlights = new ArrayList<List<String>>(combinedRatesMap.keySet());
    		
    		//Using Lambda to sort the Collection
    		Collections.sort(combinedFlights , (a,b) -> (combinedRatesMap.get(a)).compareTo(combinedRatesMap.get(b)));
    		
    		System.out.println();
    		
    		System.out.println(" Top 20 Cheapest Flight Options from "+Source+" to "+Dest+" are : ");
    		
    		System.out.println();
    		
    		for(int counter = 0 ; counter < 20; counter++) {
    		 	
    			System.out.println(combinedFlights.get(counter) + " >> Rs."+ combinedRatesMap.get(combinedFlights.get(counter)));
    		}			
    	}
    

	public static String setUpBody() throws JSONException { 
		
		// No Body required in current use case 
		
		return null;
	}

	
	
}
