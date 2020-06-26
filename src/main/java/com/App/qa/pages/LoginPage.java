package com.App.qa.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

import com.App.qa.testBase.Testbase;
import com.App.qa.utils.CommonFunctions;


public class LoginPage extends Testbase {
	
	
	CommonFunctions commonFunction;
	HomePage homePage;
	
	@FindBy(id = "admin_user_email")
	WebElement txtEmail;
	
	@FindBy(id = "admin_user_password")
	WebElement txtPassword;
	
	@FindBy(name = "commit")
	WebElement btnLogin;
	
	
	public LoginPage() {
		
//		AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 5);
		PageFactory.initElements(driver, this);
		commonFunction = new CommonFunctions();
		
	}
	
	
	//Application Login
	
	public HomePage loginApp(String emailId ,String password ) throws Exception
	{
		commonFunction.enterVal( txtEmail,emailId, "Registered Email Address", false);
		
		commonFunction.enterVal( txtPassword,password, " Password", true);
		
		commonFunction.click(btnLogin, "Login Button");
		
		return homePage =  new HomePage();
		
	}
	
	
	

}
