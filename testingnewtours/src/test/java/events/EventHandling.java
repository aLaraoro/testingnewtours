package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import org.openqa.selenium.WebDriver;

import helpers.Helpers;

public class EventHandling extends TimerTask {
	private WebDriver driver;
	public EventHandling(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		System.out.println("Running");
	}
	
	
	
	
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String title = driver.getTitle();
		if(title.toLowerCase().contains("error")) {
			System.out.println("Error");
			/*driver.navigate().refresh();
			Helpers help = new Helpers();
			
			help.sleepSeconds(3);*/
			
			driver.close();
		}
		
	}

}
