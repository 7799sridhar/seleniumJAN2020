package jan27;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class UsingExtentReports {

	@BeforeTest
	public void beforeTest() {
		Reporter.log("executing before test method", true);
		//System.out.println("before test:: only once");
	}
	@Test
	public void tc1() {
		//System.out.println("test case 1");
	}
	@Test
	public void tc2() {
	//	System.out.println("test case 1");
	}
	@BeforeMethod
	public void beforeMethod() {
	//	System.out.println("before method ");
		
	}
	@AfterMethod
	public void afterMethod() {
		//System.out.println("after method");
	}
}
