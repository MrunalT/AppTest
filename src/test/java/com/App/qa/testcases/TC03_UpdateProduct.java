package com.App.qa.testcases;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.App.qa.pages.HomePage;
import com.App.qa.pages.LoginPage;
import com.App.qa.pages.ProductPage;
import com.App.qa.testBase.Testbase;
import com.App.qa.utils.CommonFunctions;

public class TC03_UpdateProduct extends Testbase {
	LoginPage loginPage;
	HomePage homePage;
	ProductPage productPage;
	
	private String sTestCaseName;
	
	
	TC03_UpdateProduct(){
		try {
			sTestCaseName = CommonFunctions.getTestCaseName(this.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@BeforeMethod
	public void setUp(Method method) throws Exception
	{
		if(!method.getName().equals("TC03UpdateProduct") && !method.getName().equals("logout"))
		{
			initialization();
			reportSetting(sTestCaseName);
			loginPage = new LoginPage();
			homePage = loginPage.loginApp(prop.getProperty("username"), prop.getProperty("password"));
			homePage.validateHomePageTitle();
		}
	}
	
	@Test(priority = 0)
	public void navigateToProductLink() throws Exception
	{
		productPage = homePage.clickProductLink();
	}
	
	@Test(dataProvider = "ProductInputData", priority = 1)
	public void TC03UpdateProduct(String title,String sku, String description, String errorMessage) throws Exception
	{
		homePage.clickProductLink();
		productPage.selectProduct(title);
		productPage.updateProduct(title, sku, description);

	}
	
	@Test(priority = 2)
	public void logout() throws Exception
	{
		productPage.clickLogout();
	}
	
	
	@AfterMethod()
	public void close(Method method) {
		if(method.getName().equals("logout"))
		{
			close();
		}
	}
	
	@DataProvider
	public Object[][] ProductInputData() throws Exception
	{
		CommonFunctions.setExcelFile(filePath+"\\src\\test\\java\\com\\App\\qa\\testdata\\ProductDetails.xlsx", "Sheet1");
		
		Object[][] testObjArray = CommonFunctions.getExcelData(sTestCaseName,
				filePath+"\\src\\test\\java\\com\\App\\qa\\testdata\\ProductDetails.xlsx", "Sheet1");
		
		return (testObjArray);
	}
}
