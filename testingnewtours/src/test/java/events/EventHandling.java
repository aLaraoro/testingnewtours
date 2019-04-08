package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.openqa.selenium.WebDriver;

import helpers.Helpers;

public class EventHandling implements ActionListener {
	private WebDriver driver;
	public EventHandling(WebDriver driver) {
		// TODO Auto-generated constructor stub
		
		this.driver = driver;
		System.out.println("Running");
		this.errorTitle(driver.getTitle());
	}
	
	
	
	
	public void errorTitle(String title) {
		
		if(title.toLowerCase().contains("error")) {
			System.out.println("Error");
			/*driver.navigate().refresh();
			Helpers help = new Helpers();
			
			help.sleepSeconds(3);*/
			
			driver.close();
		}else {
			
			return;
			
		}
		
		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
