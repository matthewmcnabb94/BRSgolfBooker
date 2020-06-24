/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brsautobooker;

import java.io.Console;

import java.awt.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.awt.datatransfer.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class BRSAutoBooker {
    
    
    
    static final boolean DEBUG = true; //Set debug mode, so you can run the bot in debug mode.
    
    private static Console console = System.console();
    
    //Initialize components
    private static WebDriver driver;
    private static WebDriverWait driverWait;
    private static Actions builder, seriesOfActions;
    private static Scanner s = new Scanner(System.in);
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    public static int timeToBook = 1316;
    
    

    
    public static void main(String[] args) {
        System.out.println("BRS Time booker, Created by Matthew McNabb \n" +
                "===============================================================================");
        
        if (console == null && !DEBUG) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }
        
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
        driver = new ChromeDriver();
        builder = new Actions(driver);
        driverWait  = new WebDriverWait(driver,30);
               
        boolean loginOK = loginToBRSGolf();
        
        if(loginOK)
        {
            System.out.println("Logged into BRS Golf");
            navigateToDateToBook();
        }
        else
        {
            System.out.println("Error logging in");
        }
        
    }
    
    
    public static boolean loginToBRSGolf()
    {
        driver.get("https://members.brsgolf.com/moyola/login");
        String userName = "10780641";
        String password = "matthewemma1!";
        
        WebElement loginName = driver.findElement(By.id("login_form_username"));
        WebElement loginPassword = driver.findElement(By.id("login_form_password"));
        WebElement loginButton = driver.findElement(By.id("login_form_login"));
        
        seriesOfActions = builder.moveToElement(loginName).click().sendKeys(loginName, userName);
        
        seriesOfActions = builder.moveToElement(loginPassword).click().sendKeys(loginPassword, password);
        
        seriesOfActions.perform();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            Logger.getLogger(BRSAutoBooker.class.getName()).log(Level.SEVERE, null, ex);
        }
        loginButton.click();
        
        boolean loggedIn = checkLogin(driver);
        
        if(loggedIn)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
    
    
    
    
    private static boolean checkLogin(WebDriver driver) {
        if(driver.findElements(By.id("handheld-menu")).size() != 0){
            System.out.println("Logged in");
            return true;
        }else{
            System.out.println("Failed to logged in, please login manually. After logged in, type \"login\"");
            return false;
        }
    }
    
    
    
    public static void navigateToDateToBook()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	//Getting current date
	Calendar cal = Calendar.getInstance();
	//Displaying current date in the desired format
	System.out.println("Current Date: "+sdf.format(cal.getTime()));
	   
	//Number of Days to add
        cal.add(Calendar.DAY_OF_MONTH, 5);  
	//Date after adding the days to the current date
	String newDate = sdf.format(cal.getTime());  
	//Displaying the new Date after addition of Days to current date
	System.out.println("Date after Addition: "+newDate);
        
        //redirecting to page to book
        System.out.println("Redirecting to booking page");
        driver.get("https://members.brsgolf.com/moyola/tee-sheet/1/"+newDate);
        
        bookTeeTime(timeToBook, newDate);
        
    }
    
    public static void bookTeeTime(int time, String date)
    {
        while(true)
        {
            System.out.println("Trying to book time at: "+time); 
            String formattedDate = date.replaceAll("/", "");
            driver.get("https://members.brsgolf.com/moyola/bookings/book/1/"+formattedDate+"/"+time);
            boolean bookingSecured = driver.findElements(By.id("lock-countdown")).size() != 0;
            
            if(bookingSecured)
            {
                System.out.println("Booking secured. Please finish booking manually");
                break;
            }
            else
            {
                System.out.println("Slot not secured");
            }
            
            
           
        }
        
        System.out.println("Ending program");
    }
    
    
    
    
}
