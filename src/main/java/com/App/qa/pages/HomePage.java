package com.App.qa.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.Reporter;

import com.App.qa.testBase.Testbase;
import com.App.qa.utils.CommonFunctions;

public class HomePage extends Testbase{
	CommonFunctions commonFunction;
	ProductPage productPage;
	
	@FindBy(linkText = "Products")
	WebElement linkProduct;
	
	public HomePage()
	{
		PageFactory.initElements(driver, this);
		commonFunction = new CommonFunctions();
	}
	
	public void validateHomePageTitle()
	{
		Assert.assertEquals("Dashboard | Qa Test", driver.getTitle(),"Failed to Login");
	}
	
	public ProductPage clickProductLink() throws Exception
	{
		commonFunction.click(linkProduct, "Product Link");
		return productPage = new ProductPage();
	}	
	
	
}
