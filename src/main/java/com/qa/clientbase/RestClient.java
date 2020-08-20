package com.qa.clientbase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;


public class RestClient {

	public static Properties prop;
	
	public static Properties propToken;
	
	public static FileOutputStream fos;
	
	public static Logger logger;
	
	public static ExtentHtmlReporter reporter;
	
	public static ExtentReports extent;
	
	
	// Initialization
	public static void initialize() {
		
		//Initializing Properties File for Environment variables
		prop = Initializer.propInitializer(prop,System.getProperty("user.dir")+"/Config/config.properties");
		
		propToken = Initializer.propInitializer(propToken,System.getProperty("user.dir")+"/Config/token.properties");
		
		// Initializing Log4j logging Files
		logger = Initializer.logInitialize(logger);
		
		// Initializing Extent Reports Files
		extent = Initializer.reportInitializer(reporter,extent);
	
		
		try {
			
		 fos = new FileOutputStream(System.getProperty("user.dir")+"/Config/token.properties");
		
		} catch (FileNotFoundException e) {

			e.printStackTrace();
			
			}
	}
	
	
	// @GET API CALL
	public CloseableHttpResponse getcall(String url , HashMap<String,String> headerMap) throws ClientProtocolException, IOException {
			
		    CloseableHttpClient httpclient = HttpClients.createDefault();
			
			HttpGet httpget = new HttpGet(url);
			
			for(Entry<String,String> e : headerMap.entrySet()) {
			
			httpget.addHeader(e.getKey() , e.getValue());
			
			}
			
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			return response;
		}
	
	//@POST API CALL (Not required for now)
	
	//@PUT API CALL (Not required for now)
	
	//@DELETE API CALL (Not required for now)
	
	
	
}
