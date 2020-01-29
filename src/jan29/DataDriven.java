package jan29;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;



import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class DataDriven {
	WebDriver driver;
	FileInputStream fi;
	Workbook wb;
	Sheet ws;
	Row row;
	FileOutputStream fo;
	ExtentReports reports;
	ExtentTest test;
	File screen;


	@BeforeTest
	public void setUp() {
		reports=new ExtentReports("./reports/login.html");
		driver=new ChromeDriver();
		
	}


	@Test
	public void Login() throws Throwable {
		fi=new FileInputStream("C:\\Users\\sridhar\\Desktop\\Book1.xlsx");
		wb=new XSSFWorkbook(fi);
		ws=wb.getSheet("Login");
		row=ws.getRow(0);
		int rc=ws.getLastRowNum();
		int cc=row.getLastCellNum();
		Reporter.log("no of rows are=="+rc+"     "+"no of columns are=="+cc);
		
		for(int i=1;i<rc;i++) {
			//test case starts from here for extent reports
		test=reports.startTest("Verfying Login test case");
		driver.navigate().to("http://orangehrm.qedge.com");
		//driver.manage().window().maximize();
		
		String username=ws.getRow(i).getCell(0).getStringCellValue();
		String password=ws.getRow(i).getCell(1).getStringCellValue();
		
		driver.findElement(By.name("txtUsername")).sendKeys(username);
		driver.findElement(By.name("txtPassword")).sendKeys(password);
		driver.findElement(By.name("Submit")).click();
		String ExpUrl="dash";
		String ActUrl=driver.getCurrentUrl();
		if(ActUrl.contains(ExpUrl)) {
			//take screenshot for test case pass
			screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//write the screenshot file to a folder
			FileUtils.copyFile(screen, new File("D://selenium//screens//iterations	"+i+"Loginpage.png"));
			//
			Reporter.log("Login success",true);
			test.log(LogStatus.PASS, "Login success=="+ExpUrl+"   "+ActUrl);
			//write login result into excel
			ws.getRow(i).createCell(2).setCellValue("login success");
			//write test status pass into excel
			ws.getRow(i).createCell(3).setCellValue("PASS");
						
		}//if
		else {
			
			//take screenshot for test case pass
			screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//write the screenshot file to a folder
			FileUtils.copyFile(screen, new File("D://selenium//screens//iterations	"+i+"Loginpage.png"));
			//
			Reporter.log("Login Failed",true);
			test.log(LogStatus.FAIL, "Login failed=="+ExpUrl+"   "+ActUrl);
			//write login result into excel
			String msg=driver.findElement(By.xpath("//span[@id='spanMessage']")).getText();
			ws.getRow(i).createCell(2).setCellValue(msg);
			//write test status pass into excel
			ws.getRow(i).createCell(3).setCellValue("FAIL");
						
		}//else
		reports.endTest(test);
		reports.flush();
		
		}//for
		fi.close();
		//write this excel into final excel which is sent to testlead
		fo=new FileOutputStream("D://Results.xlsx");
		wb.write(fo);
		fo.close();
		wb.close();
	}//login test case
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
	
}
