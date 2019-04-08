package testingauto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.Timer;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import data.Data;
import events.EventHandling;

public class Tests2 {
	
	
	private WebDriver driver;
	private Timer timer;
	
	ArrayList<String> tabs;

	
	@BeforeMethod
	public void setUp() throws IOException, Exception, InvalidFormatException {
		
		System.out.println("Tests 2");
		//Data
		
		//autoLogin = data.autoList(loginList, 5);

		DesiredCapabilities caps = new DesiredCapabilities();				
		//Inicia driver
		String exePath = "Chrome Driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		EventHandling evt = new EventHandling(driver);
		timer = new Timer(3000,evt);
		timer.start();


	}
	
	
	
	@AfterMethod
	public void finishTC() {
		
		timer.stop();
		
		
	}

}
