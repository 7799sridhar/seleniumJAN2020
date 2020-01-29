package jan29;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class PropertiesFile {

	WebDriver driver;
	Properties props;
	FileInputStream fi;
	@BeforeTest
	public void setUp() throws IOException {
		props =new Properties();
		fi=new FileInputStream("D:\\TestingWS\\BankingPrimus\\OR.properties");
		props.load(fi);

		driver=new ChromeDriver();
		driver.manage().window().maximize();
	}

	@Test
	public void Login() {
		driver.navigate().to(props.getProperty("objUrl"));
		driver.findElement(By.xpath(props.getProperty("objusername"))).sendKeys("Admin");
		driver.findElement(By.xpath(props.getProperty("objpassword"))).sendKeys("Qedge123!@#");
		driver.findElement(By.xpath(props.getProperty("objlginbtn"))).click();
		String ExpUrl="dash";
		String ActUrl=driver.getCurrentUrl();
		try {
			Assert.assertEquals(ActUrl.contains(ExpUrl), true)	;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
			}
	
	@AfterTest
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
