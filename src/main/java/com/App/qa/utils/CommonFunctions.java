package com.App.qa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Reporter;

import com.App.qa.testBase.Testbase;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

public class CommonFunctions extends Testbase{

private static XSSFSheet ExcelWSheet;
private static XSSFWorkbook ExcelWBook;
private static XSSFCell Cell;
private static XSSFRow Row;


	/*public void click(WebElement element, String objectName)
	{
		element.click();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Reporter.log("<br> Clicked on <b>"+objectName+"</b></br>");
	}*/

	public void enterVal(WebElement element, String value, String objectName, Boolean mask) throws Exception
	{
		element.clear();
		element.sendKeys(value);
		if(mask)
		{
			value = maskString(value);
		}
		String path = getScreenShot();
		reports.log(Status.PASS, value+" is entered in "+objectName,MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}



	/*public void selectValFromDrpdwn(WebElement element, String visibleText, String objectName)
	{
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		Reporter.log("<br><b>"+visibleText+"</b> is selected in <b>"+objectName+"</b></br>");
	}*/

	public String maskString(String val)
	{
		String maskedString = "";
		for(int i = 0; i < val.length() ;i++ )
		{
			maskedString = maskedString+("*");
		}

		return maskedString;

	}
	
	public void click(WebElement element, String objectName) throws Exception
	{
		element.click();
		
		String path = getScreenShot();
		reports.log(Status.PASS, "Clicked on "+objectName, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
	}

	public void assertEqual(String expeString, String actString, String objectName) throws Exception
	{
		String path = getScreenShot();
		if(actString.contains(expeString))
		{
		reports.log(Status.PASS, objectName+"is matched. Verification value: "+actString, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}
		else
		{
			reports.log(Status.FAIL, objectName+"is mismatched. Expected value: "+expeString+", Actual Value"+actString, MediaEntityBuilder.createScreenCaptureFromPath(path).build());
		}	
	}
	
	public static void setExcelFile(String Path, String SheetName) throws Exception{
		try {
			// Open the Excel file
			
			FileInputStream ExcelFile = new FileInputStream(Path);
			
			// Access the required test data sheet
			
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			
			throw (e);
		}
	}
	
	public static Object[] getTableArray(String FilePath, String SheetName, int iTestCaseRow) throws Exception{

		String[] tabArray = null;
		
		try {
			
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			
			// Access the required test data sheet
			
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			
			int totalCols = getColumnUsed();
			
			tabArray=new String[totalCols-1];
			
			for(int j=1;j<totalCols;j++)
			{
				
				tabArray[j-1]=getCellData(iTestCaseRow,j);
				
			}
		}
		catch (FileNotFoundException e)
		{
			System.out.println("Could not read the Excel sheet");
			
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("Could not read the Excel sheet");
			
			e.printStackTrace();
		}
		
		return (tabArray);
	}

	public static String getCellData(int RowNum, int ColNum) throws Exception{
		
		try {
			
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			
			String CellData = Cell.getStringCellValue();
			
			return CellData;
		} catch (Exception e) {
			return "";
		}
	}
	
	public static String getTestCaseName(String sTestCase) throws Exception{
		
		String value = sTestCase;
		
		try {
			
			int posi = value.indexOf("@");
			
			value = value.substring(0,posi);
			
			posi = value.lastIndexOf(".");
			
			value = value.substring(posi+1);
			
			return value;
		} catch (Exception e)
		{
			throw(e);
		}
	}
	
	
public static int getRowContains(String sTestCaseName, int colNum) throws Exception{
	
	int i;
	
	try {
		
		int rowCount = CommonFunctions.getRowUsed();
		
		for(i=0;i<rowCount;i++)
		{
			if(CommonFunctions.getCellData(i,colNum).equalsIgnoreCase(sTestCaseName)) {
				break;
			}
		}
		
		return i;
	} catch (Exception e) {
		
		throw (e);
	}
}
	
public static int getRowUsed() throws Exception{
	
	try {
		
		int RowCount = ExcelWSheet.getLastRowNum()+1;
		
		return RowCount;
		
	}catch (Exception e) {
	
		System.out.println(e.getMessage());
		
		throw (e);
	}
}

public static int getColumnUsed() throws Exception{
	
	try {
		
		int ColumnCount = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
		
		return ColumnCount;
		
	}catch (Exception e) {
	
		System.out.println(e.getMessage());
		
		throw (e);
	}
}

public static Object[][] getExcelData(String sTestCaseName, String fileLoc, String sheetName) throws Exception{

	int i;
	int rowCount = CommonFunctions.getRowUsed();
	int usefulRows = 0;
	for(i=1;i<rowCount;i++) {
		if(CommonFunctions.getCellData(i,0).equalsIgnoreCase(sTestCaseName)) {
			usefulRows++;
		}		
	}
	
	Object [][] tabArray = null;
	tabArray = new String[usefulRows][CommonFunctions.getColumnUsed()-1];
	
	try {
		int currentRow = 0;
		for( i=1;i<rowCount;i++) {
			
			if(CommonFunctions.getCellData(i,0).equalsIgnoreCase(sTestCaseName)) {
				
				tabArray[currentRow]=getTableArray(fileLoc, sheetName,i);
				
				currentRow++;
			}
		}
		
		return tabArray;
	}catch (Exception e) {
	
		throw (e);
	}
}

public static String getScreenShot() {
	TakesScreenshot ts = (TakesScreenshot) driver;
	File src = ts.getScreenshotAs(OutputType.FILE);
	String path = filePath+"\\screenshots\\"+System.currentTimeMillis()+".png";
	File defile = new File(path);
	try {
		FileUtils.copyFile(src, defile);
	}
	catch (Exception e) {
		
		System.out.println("Capture failed"+e.getMessage());
		
	}
	return path;
}

	public String createNewUser(String email, String siteNames) {

		DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		String timestamp = df.format(dateobj).toString()
				.replaceAll(" ", "").replaceAll(":", "").replaceAll("/", "");
		String userId  = email+"_"+timestamp+"@"+siteNames.trim()+".com";
		return userId;
		
	}
}
