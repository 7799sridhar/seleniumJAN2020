package com.qedge;

import java.util.ArrayList;
import java.util.Iterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Irtc_WindowSwitching {

	public static void main(String[] args) throws Throwable {

		String parent=null;
		System.out.println("main method start");

		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.irctc.co.in/nget/train-search");

		parent=driver.getWindowHandle();
		System.out.println("parent window handle is :::::"+parent);

		driver.findElement(By.xpath("//label[contains(text(),'PNR STATUS')]")).click();
		driver.findElement(By.xpath("//label[contains(text(),'CHARTS / VACANCY')]")).click();
		driver.findElement(By.xpath("//a[contains(text(),'FUNDAMENTAL DUTIES OF THE CITIZENS OF INDIA')]")).click();

		//storing all window handles in the arraylist collection
		ArrayList< String>  alist= new ArrayList<String>(driver.getWindowHandles());
		Iterator<String> itr=alist.iterator();
		while(itr.hasNext()) {
			String child=itr.next();
			if(!child.equals(parent)) {
				driver.switchTo().window(child);
				String pagetitle=driver.switchTo().window(child).getTitle();
				System.out.println(pagetitle);
				Thread.sleep(2000);
				driver.close();
			}
		}
		driver.switchTo().window(parent);
		driver.findElement(By.xpath("//input[@placeholder='From*']")).sendKeys("HYDERABAD DECAN - HYB");
		driver.findElement(By.xpath("//input[@placeholder='To*']")).sendKeys("C SHIVAJI MAH T - CSTM");
		driver.findElement(By.xpath("//button[contains(text(),'Find trains')]")).click();

		Thread.sleep(2000);
		driver.close();
		driver.quit();
		System.out.println("main method ends");
	}

}
