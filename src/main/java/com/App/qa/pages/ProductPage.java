package com.App.qa.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.App.qa.testBase.Testbase;
import com.App.qa.utils.CommonFunctions;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;


public class ProductPage extends Testbase {

	HomePage homePage;
	CommonFunctions commonFunction;
	ProductPage productPage;
	
	@FindBy(linkText = "New Product")
	WebElement linkNewProduct;
	
	@FindBy(id = "product_title")
	WebElement txtProductTitle;
	
	@FindBy(id = "product_sku")
	WebElement txtProductSku;
	
	@FindBy(id = "product_description")
	WebElement txtProductDesc;
	
	@FindBy(name = "commit")
	WebElement btnCreateProduct,btnUpdateProduct;
	
	@FindBy(linkText = "Batch Actions")
	WebElement btnBatchAction;
	
	@FindBy(linkText = "Edit Product")
	WebElement btnEditProduct;
	
	@FindBy(linkText = "Delete Product")
	WebElement btnDeleteProduct;
	
	@FindBy(xpath = "//*[@id= 'wrapper']/div[3]/div")
	WebElement flashNotice;
	
	@FindBy(linkText = "Logout")
	WebElement btnSignOff;
	
	
	
	public ProductPage() {
		
		PageFactory.initElements(driver, this);
		commonFunction = new CommonFunctions();
		homePage = new HomePage();
//		productPage = new ProductPage();
	
	}
	
	
	public void addNewProduct(String title, String sku, String desc, String error) throws Exception
	{
		Assert.assertEquals(driver.getTitle(), "New Product | Qa Test", 
				"Failed to open New Product Link");
		
		commonFunction.enterVal(txtProductTitle, title, "Product Title", false);
		
		
		String typeTitle = txtProductTitle.getAttribute("value");
		int size = typeTitle.length();
		if(size > 40)
		{
			Assert.fail("title crosses max limit");
		}
		
		commonFunction.enterVal(txtProductSku, sku, "Product Sku", false);
		
		String typeSku = txtProductSku.getAttribute("value");
		int size1 = typeSku.length();
		if(size1 > 10)
		{
			Assert.fail("sku crosses max limit");
		}
		
		commonFunction.enterVal(txtProductDesc, desc, "Product Decription", false);
		
		commonFunction.click(btnCreateProduct, "Create Product Link");
		
		
		if(!error.isEmpty())
		{
			String locator = null;
			if(error.contains("Title"))
				locator = "product_title_input";
			else if(error.contains("Sku"))
				locator = "product_sku_input";
			else if(error.contains("Description"))
				locator = "product_description_input";
			
			
			String errortext = driver.findElement(By.xpath("//*[@id = '"+locator+"']/p")).getText();
			
			commonFunction.assertEqual(errortext, error, "Error Message");
		}
		else
		{
			Assert.assertEquals(driver.getTitle(), typeTitle+" | Qa Test");
			homePage.clickProductLink();
			commonFunction.click(linkNewProduct, "New Product Link");
		}
	}
	
	
	
	public void doProductListing()
	{
		Assert.assertTrue(btnBatchAction.isEnabled(), "No product found");
		
		int cols = driver.findElements(By.xpath("//*[@id='index_table_products']/thead/tr/th")).size();
		System.out.println("total cols"+cols);
		
		int rows = driver.findElements(By.xpath("//*[@id='index_table_products']/tbody/tr/td[1]")).size();
		System.out.println("total rows"+rows);
		
		for(int i = 1 ;i<= rows ; i++)
		{
			for(int j = 2 ;j<= cols ; j++)
			{
				String getProduct = driver.findElement(By.xpath("//*[@id='index_table_products']/tbody/tr["+i+"]/td["+j+"]")).getText();
				
				String getColumn = driver.findElement(By.xpath("//*[@id='index_table_products']/thead/tr/th["+j+"]")).getText();
				reports.log(Status.PASS, getColumn+" : "+getProduct);
				
			}
		}
	}
	
	
	
	public void selectProduct(String title) throws Exception
	{
		Assert.assertTrue(btnBatchAction.isEnabled(), "No product found");
		
		int rows = driver.findElements(By.xpath("//*[@id='index_table_products']/tbody/tr/td[1]")).size();
		System.out.println("total rows"+rows);
		
		for(int i = 1 ;i<= rows ; i++)
		{
			
				WebElement ele = driver.findElement(By.xpath("//*[@id='index_table_products']/tbody/tr["+i+"]/td[3]"));
				
				String getTitle = ele.getText();
				if(getTitle.contains(title))
				{
					driver.findElement(By.xpath("//*[@id='index_table_products']/tbody/tr["+i+"]/td[2]")).click();
					
					String path = CommonFunctions.getScreenShot();
					reports.log(Status.PASS, getTitle+" product selected.", MediaEntityBuilder.createScreenCaptureFromPath(path).build());
					
					break;
				}
			
		}
	}
	
	
	public void updateProduct(String title, String sku, String desc) throws Exception
	{
		commonFunction.click(btnEditProduct, "Edit Product Button");
		
		Assert.assertEquals(driver.getTitle(), "Edit Product | Qa Test");
		
		if(!sku.isEmpty())
			commonFunction.enterVal(txtProductSku, sku, "Product Sku", false);
		
		if(!desc.isEmpty())
			commonFunction.enterVal(txtProductDesc, desc, "Product Description", false);
		
		commonFunction.click(btnUpdateProduct, "Update Product Button");
		
		String getMdg = flashNotice.getText();
		
		commonFunction.assertEqual("Product was successfully updated.", getMdg, "Update Message");
		
	}
	
	

	public void deleteProduct(String title) throws Exception
	{
		btnDeleteProduct.click();
		
		reports.log(Status.PASS, "Following confirmation alert displayed for deleting record "
		+driver.switchTo().alert().getText());
		
		driver.switchTo().alert().accept();
		
		String getMdg = flashNotice.getText();
		
		commonFunction.assertEqual("Product was successfully destroyed.", getMdg, "Delete Message");
		
	}
	
	public void clickNewProduct() throws Exception
	{
		Assert.assertEquals(driver.getTitle(), "Products | Qa Test", "Failed to open product page");
		
		commonFunction.click(linkNewProduct, "New Product Link");
		
		Assert.assertEquals(driver.getTitle(), "New Product | Qa Test", "Failed to open new product page");
	}
	
	public void clickLogout() throws Exception
	{
		commonFunction.click(btnSignOff, "Sign Off Button");
	}
}
