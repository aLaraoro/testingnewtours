package pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import assertpage.AssertPages;

public class PageReservation {


	private WebDriver driver;
	private By tripType;
	private By passCount;
	private By fromPort;
	private By fromMonth;
	private By fromDay;
	private By toPort;
	private By toMonth;
	private By toDay;
	private By servClass;
	private By airline;
	private By flights;
	private By findFlights;
	private String rowFly;

	public PageReservation(WebDriver driver) {

		this.driver = driver;
		tripType = By.name("tripType");
		passCount = By.name("passCount");
		
		fromPort = By.name("fromPort");
		fromMonth = By.name("fromMonth");
		fromDay = By.name("fromDay");
		
		toPort = By.name("toPort");
		toMonth = By.name("toMonth");
		toDay = By.name("toDay");
		
		servClass = By.name("servClass");
		airline = By.name("airline");
		findFlights = By.name("findFlights");
		
		flights = By.partialLinkText("Flights");
		rowFly= ".//b/font[text()='PATH']/ancestor::tbody[2]/tr";
	}
	
	public void reservationLogin(Boolean bool,String condition) {
		
		driver.findElement(flights).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		AssertPages assertPages = new AssertPages(driver);
		assertPages.assertFlights(false, condition);
		
		
	}
	
	public List<String> confirmation(Map<String,String[]> details,String first){

		
		String date = details.get(first+"Month")[0] + "/" + details.get(first+"Day")[0] + "/2019";
		String place = "";

		List<String> confirmList = new ArrayList<String>();
		
		String dest = "";

		
		if(first == "to") {
			
			dest = details.get("toPort")[0] + " to " + details.get("fromPort")[0];
			place="RETURN";

			
		}else {
			
			dest = details.get("fromPort")[0] + " to " + details.get("toPort")[0];
			place ="DEPART";
			
		}
		confirmList.add(dest);
		confirmList.add(date);
		confirmList.add(place);
		
		return confirmList;
		
		
	}
	
	
	public Map<String,String[]> changeOptions(Map<String,String[]> flyDetails, Map<String,String> changes){
		
		
		for(Map.Entry<String, String> entry : changes.entrySet()) {
			
			String key = entry.getKey();
			String value = entry.getValue();
			String[] array = flyDetails.get(key);
			System.out.println(array[0]);
			array[0] = value;
			System.out.println(array[0]);
			flyDetails.put(key, array);
			
			
		}
		
		return flyDetails;
		
	}
	
	public List<Map<String,String>> flightsData(List<String> listDest) {
		
		String flyRow = rowFly.replace("PATH", listDest.get(2));
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		List<WebElement> rowElements = driver.findElements(By.xpath(flyRow));
		System.out.println(rowElements.size());
		
		rowElements.remove(1);
		rowElements.remove(0);
		int rows = rowElements.size()/2;
		
		for(int i=0;i<rows;i++) {
			int num = 3+2*i;
			int b = 4+2*i;
			System.out.println(num);
			
			Map<String,String> map = new HashMap<String,String>();
			
			
			
			String pathName1 = flyRow + "[" + num + "]/td[2]/font/b";
			String pathName2 = flyRow + "[" + num + "]/td[3]/font";
			String pathName3 = flyRow + "[" + b + "]/td/font/font/b";
			String data = driver.findElement(By.xpath(pathName1)).getText();
			String time = driver.findElement(By.xpath(pathName2)).getText();
			String priceTag = driver.findElement(By.xpath(pathName3)).getText();
			
			String price = priceTag.substring(priceTag.indexOf("$")+1, priceTag.length());
			map.put("Destination", listDest.get(0));
			map.put("Day", listDest.get(1));
			map.put("Airline", data);
			map.put("Time", time);
			map.put("Price", price);
			System.out.println("Flight: " +data + ". Depart: " +time + ". Price: " + price);
			list.add(map);
		
		}
		
		return list;
		
	}
	
	public void bookFlight(Map<String,String[]> flyDetails) {

		
		for(Map.Entry<String, String[]> entry : flyDetails.entrySet()) {
			String key = entry.getKey();
			String type = entry.getValue()[1];
			String value = entry.getValue()[0];
			//System.out.println("Key = " + entry.getKey() + ", Value = " + value + ". Type = " + type);
			
			if(type.equalsIgnoreCase("drop")) {
				
				Select select = new Select(driver.findElement(By.name(entry.getKey())));
				int noValue = driver.findElement(By.name(entry.getKey())).getAttribute("value").length();
				if(key.equalsIgnoreCase("airline")) {
					
					select.selectByVisibleText(value);
					
				}else {
					
					select.selectByValue(value);
					
				}
				
				
				
				
			}else {
				
				
				driver.findElement(By.xpath("//input[@value='"+value+"']"));
				
			}

			
		}
		
		driver.findElement(findFlights).click();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		
		
	}
	
	
	
	public Map<String,String[]> getDetails(){
		System.out.println("Get Details");
		final String[] tripTypeArr = {driver.findElement(tripType).getAttribute("value"),"radio"};
		final String[] passCountArr = {driver.findElement(passCount).getAttribute("value"),"drop"};
		final String[] fromPortArr = {driver.findElement(fromPort).getAttribute("value"),"drop"};
		final String[] fromMonthArr = {driver.findElement(fromMonth).getAttribute("value"),"drop"};
		final String[] fromDayArr = {driver.findElement(fromDay).getAttribute("value"),"drop"};
		final String[] toPortArr = {driver.findElement(toPort).getAttribute("value"),"drop"};
		final String[] toMonthArr = {driver.findElement(toMonth).getAttribute("value"),"drop"};
		final String[] toDayArr = {driver.findElement(toDay).getAttribute("value"),"drop"};
		final String[] servClassArr = {driver.findElement(servClass).getAttribute("value"),"radio"};
		
		final String[] airlineArr = {driver.findElement(airline).getAttribute("value"),"drop"};
		
		Map<String,String[]> map = new HashMap<String,String[]>(){
			
			{
				put("tripType",tripTypeArr);
				put("passCount",passCountArr);
				put("fromPort",fromPortArr);
				put("fromMonth",fromMonthArr);
				put("fromDay",fromDayArr);
				put("toPort",toPortArr);
				put("toMonth",toMonthArr);
				put("toDay",toDayArr);
				put("servClass",servClassArr);
				put("airline",airlineArr);
				
			}
			
		};
		
		
		
		return map;
		
		
	}
	
	
	
	public int getIndex(WebElement elem) {
		
		Select select = new Select(elem);
		int index = select.getAllSelectedOptions().indexOf(select.getFirstSelectedOption());
		System.out.println(index);
		return index;
		
		
	}
	

	public void selectPassengers(int count) {
		WebDriverWait wait = new WebDriverWait(driver,10);
		WebElement psngrQuant = wait.until(ExpectedConditions.presenceOfElementLocated(passCount));
		Select selectPassengers = new Select(psngrQuant);
		selectPassengers.selectByVisibleText(Integer.toString(count));

	}

	public void selectFromPort(int index) {

		Select selectFromPort = new Select(driver.findElement(fromPort));
		selectFromPort.selectByIndex(index);

	}

	public void selecttoPort(String city) {

		Select selectFromPort = new Select(driver.findElement(fromPort));
		selectFromPort.selectByValue(city);

	}

}
