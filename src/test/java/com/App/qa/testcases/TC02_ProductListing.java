package com.App.qa.testcases;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.App.qa.pages.HomePage;
import com.App.qa.pages.LoginPage;
import com.App.qa.pages.ProductPage;
import com.App.qa.testBase.Testbase;
import com.App.qa.utils.CommonFunctions;

public class TC02_ProductListing extends Testbase{
	LoginPage loginPage;
	HomePage homePage;
	ProductPage productPage;
	
	private String sTestCaseName;
	
	
	TC02_ProductListing(){
		try {
			sTestCaseName = CommonFunctions.getTestCaseName(this.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@BeforeMethod
	public void setUp() throws Exception
	{
		
			initialization();
			reportSetting(sTestCaseName);
			loginPage = new LoginPage();
			homePage = loginPage.loginApp(prop.getProperty("username"), prop.getProperty("password"));
			homePage.validateHomePageTitle();
			productPage = homePage.clickProductLink();
	}
	
	@Test
	public void TC02ProductListing()
	{
		productPage.doProductListing();
	}
	
	@AfterMethod
	public void logout()
	{
		close();
	}
}
