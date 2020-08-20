package com.qa.clientbase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Initializer {
	
	static int logcheck=0;
	
	static int reportcheck=0;
	
	//Logging Utility
	public static Logger logInitialize(Logger logger) {
		
		logcheck++;
		
		if (logcheck < 2) {
			
			logger = Logger.getLogger(Initializer.class);
			
			PropertyConfigurator.configure(System.getProperty("user.dir")+"/src/main/resources/log4j.properties");
			
			return logger;
		}
		
		return logger;
	}

	//Reporting Utility
	public static ExtentReports reportInitializer(ExtentHtmlReporter reporter,ExtentReports extent) {
	
		reportcheck++;
		
		if (reportcheck < 2) {
			
		reporter=new ExtentHtmlReporter("./Extent-Reports/BlueOptima.html");
		
		extent=new ExtentReports();
		
		extent.attachReporter(reporter);
		
		extent.setSystemInfo("User Name","Sahil Puri");
		
		extent.setSystemInfo("Environment","Ixigo.QA");
		
		}
		
		return extent;
	}
	
	///Properties Utility
	public static Properties propInitializer(Properties prop,String path) {
		
		try { 
			
			FileInputStream file=new FileInputStream(path); 
			
			prop=new Properties(); 
			
			prop.load(file); 
			
		} 
		
		catch(IOException e) { 
			
			e.printStackTrace(); 
		} 
		 
		return prop;
	}
}
