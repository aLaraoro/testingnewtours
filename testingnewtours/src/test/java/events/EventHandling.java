package events;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import helpers.Helpers;

public class EventHandling extends TimerTask {
	private WebDriver driver;
	long time;
	long mili;
	public EventHandling(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver = driver;
		System.out.println("Running");
		time = LocalTime.now().get(ChronoField.SECOND_OF_DAY);
		mili = 0;
	}
	
	
	
	
	
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		String title = driver.getTitle();

		mili = (LocalTime.now().get(ChronoField.SECOND_OF_DAY) - time);
		System.out.println("Time: " +mili);
		if(title.toLowerCase().contains("error")) {
			System.out.println("Error");
			/*driver.navigate().refresh();
			Helpers help = new Helpers();
			
			help.sleepSeconds(3);*/
			
			driver.close();
		}
		if(mili>=300) {
			
			System.out.println("Timeout");
			driver.close();
		}
		
		
	}

}
