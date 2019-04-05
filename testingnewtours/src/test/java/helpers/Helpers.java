package helpers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class Helpers {
	
	private WebDriver driver;
	public Helpers(WebDriver driver) {
		
		this.driver = driver;
		
	}

	public void sleepSeconds(int seconds) {

		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	public void refreshPage(int seconds) {
		
		String title = driver.getTitle();
		if(title.toLowerCase().contains("error")) {
			
			driver.navigate().refresh();
			sleepSeconds(seconds);
			
			
		}
		
		
	}
	


}
