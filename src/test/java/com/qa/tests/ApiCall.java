package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import com.qa.clientbase.RestClient;

public class ApiCall {	
		
	public CloseableHttpResponse getExecute(String uri, HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
			
		RestClient restclient = new RestClient();
				
		// RESPONSE RETURN --
			
		//To-Do >> Sahil - Form a header array here and pass directly to RestClient
			
		CloseableHttpResponse response = restclient.getcall(uri,headerMap);
			
		int code = response.getStatusLine().getStatusCode();
			
		System.out.println("Response code is : " + code);
			
		System.out.println("Response Line is : " + response.getStatusLine());
			
		return response;
			
						
		}
}

