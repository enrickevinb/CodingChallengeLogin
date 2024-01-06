package com.saucedemo;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {
	
	@BeforeClass
	public void initialize() {
		//Driver Path
		WebDriverManager.chromedriver().setup();
	}
			
	//WebDriver
	WebDriver wd;
	
	@BeforeTest
	public void openBrowser() {
		
		wd = new ChromeDriver();
		wd.get("https://www.saucedemo.com/");	
		wd.manage().window().maximize();
		
	}
	
	@AfterTest
	public void closeBrowser() {
		
		wd.close();
	}
	
	
	@Test
	public void testScenario1() {
		
		System.out.println("");
		System.out.println("Login Test Scenario 1");
		
		//Scenario 1
			//- log in using standard user
		System.out.println("Username: standard_user");
		System.out.println("Password: secret_sauce");
		wd.findElement(By.id("user-name")).sendKeys("standard_user");
		wd.findElement(By.id("password")).sendKeys("secret_sauce");
		wd.findElement(By.id("login-button")).click();
			
			//- verify that user is able to navigate to home page
		String url = wd.getCurrentUrl();
		Assert.assertEquals(url, "https://www.saucedemo.com/inventory.html");
		System.out.println("Login Successful");
		
			//- log out
		wd.findElement(By.xpath("//*[@id=\"react-burger-menu-btn\"]")).click();
		wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		wd.findElement(By.linkText("Logout")).click();
		
			//- verify that user is navigated to login page
		url = wd.getCurrentUrl();
		Assert.assertEquals(url, "https://www.saucedemo.com/");
		System.out.println("Logout Successful\n");

	}
	
	@Test
	public void testScenario2() {
		
		System.out.println("");
		System.out.println("Login Test Scenario 2");

		//Scenario 2
			//- log in using locked out user
		System.out.println("Username: locked_out_user");
		System.out.println("Password: secret_sauce");
		wd.findElement(By.id("user-name")).sendKeys("locked_out_user");
		wd.findElement(By.id("password")).sendKeys("secret_sauce");
		wd.findElement(By.id("login-button")).click();

			//- verify error message
		Assert.assertEquals(wd.getCurrentUrl(), "https://www.saucedemo.com/");	
		String exp = "Epic sadface: Sorry, this user has been locked out.";
		WebElement mess = wd.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));
		String shwn = mess.getText();
		System.out.println("Error Message is: " + shwn + "\n");
		Assert.assertEquals(exp, shwn);

	}
	
	

}
