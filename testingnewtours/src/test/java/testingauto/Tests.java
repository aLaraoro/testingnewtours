package testingauto;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import assertpage.AssertPages;
import data.Data;
import events.EventHandling;
import pages.PageLogin;
import pages.PageReservation;
import pages.Register;


public class Tests {

	private WebDriver driver;
	private Data data;
	private List<Map<String,String>> list;
	private List<String> loginList;
	private Boolean displayTc = true;
	//private List<Map<String,String>> autoLogin;
	private Integer row;
	private String expectedResult;
	private String description;
	ArrayList<String> tabs;

	
	@BeforeClass//(groups= {"run"})
	public void setUpTest() throws IOException, Exception, InvalidFormatException {
		
		System.out.println("Test");
		//Data
		data = new Data("./data.xlsx");
		
		list = data.getData();
		//autoLogin = data.autoList(loginList, 5);

		DesiredCapabilities caps = new DesiredCapabilities();				
		//Inicia driver
		String exePath = "Chrome Driver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
		driver = new ChromeDriver();
		
		

		
		

	}
		

	@BeforeMethod//(groups= {"parallel"})
	public void init() {
		EventHandling task = new EventHandling(driver);
		Timer timer = new Timer();
		timer.schedule(task, 1000L, 3000);
		
		
	}
	
	@BeforeMethod//(groups= {"Test"})
	public void runTest() {
		
		driver.manage().deleteAllCookies();
		driver.navigate().to("http://newtours.demoaut.com/");
				
	}
	/*
	@Test
	public void incorrectLogin() {

		PageLogin pageLogin = new PageLogin(driver);
		PageLogon pageLogon = new PageLogon(driver);
		pageLogin.login("user", "user");	
		pageLogon.assertLogonPage();

	}*/
	
	
	/*
	
	@Test
	public void multipleLogin() throws Exception {
		System.out.println("Multi Login");
		System.out.println("From Data Excell");
		
		PageLogin pageLogin = new PageLogin(driver);
		AssertPages assertPages = new AssertPages(driver);
		pageLogin.loginXTimes("http://newtours.demoaut.com/", list);
		tabs = new ArrayList<String> (driver.getWindowHandles());
		
		assertPages.assertMultipleTabs(tabs, list);
		
	}
	
	@Test
	public void correctLoginNTimes() throws Exception{
		System.out.println("Correct Login");
		System.out.println("5 times");
		PageLogin pageLogin = new PageLogin(driver);
		pageLogin.loginXTimes("http://newtours.demoaut.com/", autoLogin);
		driver.close();
		
		
	}*/
	
	
	/*@Test
	public void compareTwoFlights() throws InterruptedException {
		
		System.out.println("Login and book");
		displayTc = false;
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.filter(0, list, false, true);
		
		
		Map<String,String[]> details = pageReservation.getDetails();
		
		pageReservation.bookFlight(details);
		
		List<String> departList = pageReservation.confirmation(details,"from");
		List<String> returnList = pageReservation.confirmation(details,"to");
		AssertPages assertPages = new AssertPages(driver);
		Boolean departAssert = assertPages.assertSelectFlight(departList);
		Boolean returnAssert = assertPages.assertSelectFlight(returnList);
		
		Assert.assertTrue((departAssert&&returnAssert));
		
		List<Map<String,String>> departureList = pageReservation.flightsData(departList);
		List<Map<String,String>> returnedList =pageReservation.flightsData(returnList);
		System.out.println("Departure List size: "+departureList.size());
		System.out.println("Return List size: "+returnList.size());
		
		
		driver.findElement(By.partialLinkText("Flights")).click();
		Map<String,String> changes = new HashMap<String,String>();
		changes.put("toPort", "Frankfurt");
		Map<String,String[]> newDetails = pageReservation.changeOptions(details, changes);
		pageReservation.bookFlight(newDetails);
		driver.close();
		
		
	}*/
	
	@Test//(groups= {"Test"})
	public void reserveDefaultFlightTest() throws InterruptedException {
		
		System.out.println("Login and book");
		displayTc = false;
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.filter(0, list, false, true);
		
		
		Map<String,String[]> details = pageReservation.getDetails();
		
		pageReservation.bookFlight(details);
		
		List<String> departList = pageReservation.confirmation(details,"from");
		List<String> returnList = pageReservation.confirmation(details,"to");
		AssertPages assertPages = new AssertPages(driver);
		Boolean departAssert = assertPages.assertSelectFlight(departList);
		Boolean returnAssert = assertPages.assertSelectFlight(returnList);

		
		Assert.assertTrue((departAssert&&returnAssert));
		
		driver.close();
		
	}
	
	
	/*public void changeFromPortTest() throws InterruptedException {
		
		
		System.out.println("Login and book");
		displayTc = false;
		PageLogin pageLogin = new PageLogin(driver);
		pageLogin.filter(0, list, false, true);
		
		PageReservation pageReservation = new PageReservation(driver);
		
		Map<String,String> changes = new HashMap<String,String>();
		changes.put("fromPort", "Frankfurt");
		Map<String,String[]> details = pageReservation.changeOptions(pageReservation.getDetails(), changes);
		pageReservation.bookFlight(details);
		
		driver.close();
	}

	/*
	@Test
	public void reservateWithoutLoginTest() {
		System.out.println("Ir p�gina reservar vuelo sin login");
		this.expectedResult = "Continue on index.php";
		this.description = "Gp to Flight finder without login";
		PageReservation pageReservation = new PageReservation(driver);
		pageReservation.reservationLogin(false,expectedResult);
		driver.close();
		
		
	}
	
	@Test
	public void registerTest() throws InterruptedException {
		System.out.println("Register");
		Register register = new Register(driver);
		Map<String, String> map = register.optional("a", "a", "654321777", "a@gmail.com", "Av. de la Torre Blanca, 57", "Sant Cugat del Vall�s", "New York", "08172");
		    
	    
	    Map<String,String> required = register.required("user", "user", "user");
	    
		register.register(map,required);
		AssertPages assertPages = new AssertPages(driver);
		assertPages.assertRegister(true,"");
		this.expectedResult = "Register Correctly";
		this.description = "Register with all required fields filled";
		driver.close();
		
	}
	
	@Test
	public void registerNoUserTest() throws InterruptedException {
		
		System.out.println("Register");
		System.out.println("No username");
		Register register = new Register(driver);
		Map<String, String> map = register.optional("a", "a", "654321777", "a@gmail.com", "Av. de la Torre Blanca, 57", "Sant Cugat del Vall�s", "New York", "08172");
		    
	    
	    Map<String,String> required = register.required("", "user", "user");
	    
		register.register(map,required);
		AssertPages assertPages = new AssertPages(driver);
		assertPages.assertRegister(false,"No username");
		this.expectedResult = "Don't allow register";
		this.description = "Register without filling username field";
		driver.close();
		
		
	}
	
	@Test
	public void registerNoPwd() throws InterruptedException {
		
		System.out.println("Register");
		System.out.println("No password");
		Register register = new Register(driver);
		Map<String, String> map = register.optional("a", "a", "654321777", "a@gmail.com", "Av. de la Torre Blanca, 57", "Sant Cugat del Vall�s", "New York", "08172");
		    
	    
	    Map<String,String> required = register.required("user", "", "user");
	    
		register.register(map,required);
		AssertPages assertPages = new AssertPages(driver);
		assertPages.assertRegister(false,"No password");
		this.expectedResult = "Don't allow register";
		this.description = "Register without filling password field";
		driver.close();
		
		
	}
	@Test
	public void registerNoConfirmationPwd() throws InterruptedException {
		
		System.out.println("Register");
		System.out.println("No confirmation password");
		Register register = new Register(driver);
		Map<String, String> map = register.optional("a", "a", "654321777", "a@gmail.com", "Av. de la Torre Blanca, 57", "Sant Cugat del Vall�s", "New York", "08172");
		    
	    Map<String,String> required = register.required("user", "user", "");
	    
		register.register(map,required);
		AssertPages assertPages = new AssertPages(driver);
		assertPages.assertRegister(false,"No confirmation password");
		this.expectedResult = "Don't allow register";
		this.description = "Register without filling confirmation password field";
		driver.close();
		
		
	}
	
	@Test
	public void loginEmptyPass() throws InterruptedException {
		System.out.println("Login");
		System.out.println("Empty password");
		PageLogin pageLogin = new PageLogin(driver);
		this.expectedResult = list.get(2).get("expectedResult");
		this.description = "Login with empty password field";
		pageLogin.filter(2, list,true, false);
		driver.close();
		
	}
	
	
	
	@Test
	public void loginEmptyUser() throws InterruptedException {
		System.out.println("Login");
		System.out.println("Empty user");
		PageLogin pageLogin = new PageLogin(driver);
		this.expectedResult = list.get(3).get("expectedResult");
		this.description = "Login with empty user field";
		pageLogin.filter(3, list,true,false);
		driver.close();
		
	}
	
	@Test
	public void loginEmptyBoth() throws InterruptedException {
		System.out.println("Login");
		System.out.println("Both empty");
		PageLogin pageLogin = new PageLogin(driver);
		this.expectedResult = list.get(4).get("expectedResult");
		this.description = "Login with empty fields";
		pageLogin.filter(4, list,true,false);
		driver.close();
		
	}

	@Test
	public void correctLogin() throws InterruptedException {

		System.out.println("Correct Login");
		PageLogin pageLogin = new PageLogin(driver);
		this.expectedResult = list.get(0).get("expectedResult");
		this.description = "Login with correct username and password";
		pageLogin.filter(0, list, true, false);
		driver.close();
		

	}/*
	@Test
	public void flyRegistration() {
		System.out.println("reservation");
		PageLogin pageLogin = new PageLogin(driver);
		PageReservation pageReservation = new PageReservation(driver);
		pageLogin.login("mercury", "mercury");
		pageReservation.selectPassengers(2);
		pageReservation.selectFromPort(3);
		pageReservation.selecttoPort("London");
	}
	
	@Test
	public void pruebaCantidadDeCampos() {
		System.out.println("Verify fields");
		PageLogin pageLogin = new PageLogin(driver);
		
		pageLogin.verifyFields();
		
	}*/

	@AfterMethod//(groups= {"Test"})
	public void tearDown(ITestResult result) throws FileNotFoundException, IOException {
		if(!result.isSuccess()) {
			File myScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			try {
				System.out.println("Creando captura");
				System.out.println(result.getName());
				FileUtils.copyFile(myScreenshot, new File("Error " + System.currentTimeMillis()  + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("Tests/tearDown/TC Num:"+row);
		if(displayTc) {
			
			this.tcResultTest(result);
			
		}
			
		
			
		
		driver.quit();

	}
	
	
	public void tcResultTest(ITestResult result) throws FileNotFoundException, IOException {
		
		String[] message = {"Message","null"};
		
		if (result.getThrowable() != null) {
			
			message[1] = result.getThrowable().getMessage();
			
		}
		
		Map<Integer,String[]> map = new HashMap<Integer,String[]>();
		
		String[] tcName = {"TC Name",result.getName()};
		
		String[] expectedResu = {"Expected Result",expectedResult};
		
		String[] title = {"Page title",""};
		
		Boolean resultSuc = result.isSuccess();
		if(!resultSuc) {
			
			title[1] = driver.getTitle();
			
		}
		
		String[] success = {"Success",StringUtils.capitalize(resultSuc.toString())};
		ArrayList<String> list = new ArrayList<String>();
		LocalDateTime date = LocalDateTime.now();
		String[] day = {"Day",this.getDateOrTime(date,true)};
		String[] hour = {"Hour",this.getDateOrTime(date,false)};
		String[] descr = {"Description", this.description};
		
		map.put(0, tcName);
		map.put(1, descr);
		map.put(2, day);
		map.put(3, hour);
		map.put(4, expectedResu);
		map.put(5, success);
		map.put(6, message);
		map.put(7, title);
		data.insertRowMap(map);
		
		
	}
	
	
	public String getDateOrTime(LocalDateTime timeDate, Boolean isDate) {
		
		String date = "";

		String pattern;
		
		if(isDate) {
			
			pattern = "dd-MM-yyyy";
			DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
			
			date = timeDate.format(simpleDateFormat);
			
			
		}else {
			
			pattern = "HH:mm:ss";
			DateTimeFormatter simpleDateFormat = DateTimeFormatter.ofPattern(pattern);
			
			date = timeDate.format(simpleDateFormat);
			
		}
		
		
		
		return date;
		
		
	}
	

}
