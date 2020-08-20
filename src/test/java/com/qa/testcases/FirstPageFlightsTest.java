package com.qa.testcases;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.qa.clientbase.Constants;
import com.qa.clientbase.RestClient;
import com.qa.tests.ApiCall;
import com.qa.testsupports.TestSupport;

public class FirstPageFlightsTest extends RestClient {
	
	private static HashMap<String,String> headerMap;
	
	private static CloseableHttpResponse response;
	
	private static ExtentTest elog;
	
	private static ApiCall testGet;

	private static String token = null;
	
	@BeforeTest
	public void setUpTest() {
		
		initialize();

		testGet = new ApiCall();
		
		elog = extent
				.createTest("GET Flights");
	}
	
	// Initial API Get call for fetching search token
	
	@Test (priority = 1)
	public void SearchTokenExtractTest() {
		
		try {
			
			headerMap = TestSupport.setUpFirstPageHeader();
			
			String uri = Constants.baseUrl + Constants.formFillUri + TestSupport.formDynamicUri(prop);
			
			System.out.println(uri);
			
			response = testGet.getExecute(uri,headerMap);
			
			Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
			
			//Extract Search Token from first response
			
			token = TestSupport.extractSearchToken(response);
			
			TestSupport.sendTokenToProp(token,propToken,fos);
			
			System.out.println(token);
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		elog.log(Status.PASS," TestCase id@01 is Verified : PASS");
	}
	
	
	
	// Takes the token from first search and hits another uri for fetching flights json (GET Call)
	
	@Test (priority = 2)
	public void FlightsSearchTest() {
		
		try {
			
			headerMap = TestSupport.setUpSecondPageHeader();
			
			String uri =  Constants.baseUrl + Constants.flightSearchUrlPart1 + TestSupport.extractTokenFromProp(propToken) + Constants.flightSearchUrlPart2;
			
			System.out.println(uri);
			
			response = testGet.getExecute(uri,headerMap);
			
			Assert.assertEquals(response.getStatusLine().getStatusCode(),200);
			
			//Logic for finding top 20 cheapest flights
			
			TestSupport.findTopCheapestFlights(response);
							
		} 
		
		catch (IOException e) {
			
			e.printStackTrace();
		}
		
		elog.log(Status.PASS," TestCase id@02 is Verified : PASS");
	}
}
