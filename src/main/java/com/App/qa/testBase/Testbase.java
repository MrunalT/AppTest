package com.App.qa.testBase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class Testbase {
	
	public static WebDriver driver;
	public static Properties prop;
	public static String filePath = System.getProperty("user.dir");
	public static ExtentHtmlReporter reporter;
	public static ExtentReports extentReports;
	public static ExtentTest reports;
	public static Reporter report;
	
	
	
	//load config.properties file
	public Testbase() {
		prop = new Properties();
		try {
			FileInputStream file = new FileInputStream(filePath+
					"\\src\\main\\java\\com\\App\\qa\\config\\config.properties");
			prop.load(file);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//initial set up - load driver, select browser, hit url 
	public void initialization()
	{
		System.setProperty("webdriver.chrome.driver", filePath+"\\chromedriver.exe");
	
		if(prop.getProperty("browser").contains("chrome"))
		 driver = new ChromeDriver();
		 
		 driver.manage().deleteAllCookies();
		 
		 driver.get(prop.getProperty("url"));
		 
		 driver.manage().window().maximize();
		 
	}
	
	//set reporting path
	public void reportSetting(String tcName)
	{
		reporter = new ExtentHtmlReporter(filePath+"\\Results\\"+tcName+".html");
		System.out.println(filePath+"\\Results\\"+tcName+".html");
		
		extentReports = new ExtentReports();
		
		extentReports.attachReporter(reporter);		
		
		reports = extentReports.createTest(tcName);
		
	}
	
	//quit driver
	public void close()
	{
		extentReports.flush();
		driver.quit();
	}
}
