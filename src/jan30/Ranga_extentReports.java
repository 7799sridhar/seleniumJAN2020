package jan30;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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

public class Ranga_extentReports {

WebDriver driver;
FileInputStream fi;
FileOutputStream fo;
XSSFWorkbook wb;
XSSFSheet ws;
XSSFRow row;
ExtentReports report;
ExtentTest test;
File screen;
@BeforeTest
public void setUp()
{
	report=new ExtentReports("./Reports/Login1.html");
	driver=new ChromeDriver();
}
@Test
public void verifyLogin()throws Throwable
{
//access path of file
	fi=new FileInputStream("C:\\Users\\sridhar\\Desktop\\Book1.xlsx");
	//get wb from file
	wb=new XSSFWorkbook(fi);
	//get sheet from wb
	ws=wb.getSheet("Login");
	//get first row from sheet
	row=ws.getRow(0);
	int rc=ws.getLastRowNum();
	int cc=row.getLastCellNum();
	Reporter.log("no of rows are::"+rc+"  "+"columns in first row::"+cc,true);
for(int i=1;i<=2;i++)
{
	//Start test here
test=report.startTest("Login Test Case");
driver.get("http://orangehrm.qedgetech.com/");
driver.manage().window().maximize();
//get username column from sheet
String username=ws.getRow(i).getCell(0).getStringCellValue();
//get password column from sheet
String password=ws.getRow(i).getCell(1).getStringCellValue();
//send data to login form
driver.findElement(By.name("txtUsername")).sendKeys(username);
driver.findElement(By.name("txtPassword")).sendKeys(password);
driver.findElement(By.name("Submit")).click();
//Thread.sleep(4000);
String Actval="dash";
String Expval=driver.getCurrentUrl();
if(Expval.contains(Actval))
{
//take screen shot when it is pass
screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//copy to local driver
FileUtils.copyFile(screen, new File("D:\\TestingWS\\BankingPrimus\\screens\\iterations\\"+i+"Loginpage.png"));
Reporter.log("Login Success",true);
test.log(LogStatus.PASS, "Login Success::"+Expval+"    "+Actval);
//write as login success into result
ws.getRow(i).createCell(2).setCellValue("Login Success");
//write as pass into status
ws.getRow(i).createCell(3).setCellValue("Pass");
}
else
{
//take screen shot when it is pass
screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//copy to local driver
FileUtils.copyFile(screen, new File("D:\\TestingWS\\BankingPrimus\\screens\\iterations\\"+i+"Loginpage.png"));
//get error message
String message=driver.findElement(By.id("spanMessage")).getText();
test.log(LogStatus.FAIL, message+"  "+Expval+"   "+Actval);
Reporter.log(message,true);
ws.getRow(i).createCell(2).setCellValue(message);
ws.getRow(i).createCell(3).setCellValue("Fail");
}
report.endTest(test);
report.flush();
}
//close file which is reading
fi.close();
//create new wb
fo=new FileOutputStream("D://Results_extent.xlsx");
wb.write(fo);
fo.close();
wb.close();
}
@AfterTest
public void tearDown()
{
	driver.close();
}
}
