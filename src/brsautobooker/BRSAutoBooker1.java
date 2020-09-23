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
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.openqa.selenium.JavascriptExecutor;  


public class BRSAutoBooker1 {
    
    
    
    static final boolean DEBUG = true; //Set debug mode, so you can run the bot in debug mode.
    
    private static Console console = System.console();
    
    //Initialize components
    private static WebDriver driver;
    private static WebDriverWait driverWait;
    private static Actions builder, seriesOfActions;
    private static Scanner s = new Scanner(System.in);
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    private static UserNamePassword user = new UserNamePassword();
    private static Times time = new Times();
    private static Players player = new Players();
    
    
    //Username & Password
    private static String userName = user.getMatthewUsername();
    private static String password = user.getMatthewPassword();
    
    //Time to book
    public static String timeToBook = time.getTime1020am();
    
    //Players for group
    private static String name1 = player.getMatthewMcNabb();
    private static String name2 = player.getStephenChambers();
    private static String name3 = player.getPatrickMcGuigan();
    private static String name4 = player.getBenKennedy();
    
    
    
    

    
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
        
        
       ((JavascriptExecutor)driver).executeScript("document.title = "+ "'" +"Signed in as: "+name1+"'");
        
        startTimer();
        
        
        
    }
    
    
    
    
    
    
    public static void book()
    {
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
    
    
    
    
    
    
    
    
    public static void startTimer()
    {
        
        System.out.println("Timer started..... ");
        Timer timer = new Timer(); 
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 06);
        calendar.set(Calendar.SECOND, 30);
        Date time = calendar.getTime();
        
         timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("Running program..." + new Date());
                //checkIfFilesExist();
                book();
                //runBatchFile();
                
            }
            }, time, TimeUnit.MILLISECONDS.convert(14, TimeUnit.HOURS));
         
         

    }
    
    
    
    
    
    
    
    
    
    public static boolean loginToBRSGolf()
    {
        driver.get("https://members.brsgolf.com/moyola/login");
        
        
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
    
    public static void bookTeeTime(String time, String date)
    {
        while(true)
        {
            System.out.println("Trying to book time at: "+time); 
            String formattedDate = date.replaceAll("/", "");
            driver.get("https://members.brsgolf.com/moyola/bookings/book/1/"+formattedDate+"/"+time);
            ///test date   driver.get("https://members.brsgolf.com/moyola/bookings/book/1/20200918/1830");
            boolean bookingSecured = driver.findElements(By.id("lock-countdown")).size() != 0;
            
            if(bookingSecured)
            {
                System.out.println("Booking secured. Please finish booking manually");
                addNamesToBook();
                break;
            }
            else
            {
                System.out.println("Slot not secured");
            }
            
            
           
        }
        
        System.out.println("Ending program");
    }
    
    
    
     public static void addNamesToBook()
    {
        
        
        
        WebElement player1 = driver.findElement(By.id("select2-member_booking_form_player_1-container"));
        WebElement player2 = driver.findElement(By.id("select2-member_booking_form_player_2-container"));
        WebElement player3 = driver.findElement(By.id("select2-member_booking_form_player_3-container"));
        WebElement player4 = driver.findElement(By.id("select2-member_booking_form_player_4-container"));
        WebElement submitButton = driver.findElement(By.id("member_booking_form_confirm_booking"));
        
        if(driver.findElements(By.id("select2-member_booking_form_player_1-container")).size() != 0)
        {
            System.out.println("Found player1 :)");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            seriesOfActions = builder.moveToElement(player1).click().sendKeys(name1);
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            seriesOfActions = builder.moveToElement(player2).click().sendKeys(name2);
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            seriesOfActions = builder.moveToElement(player3).click().sendKeys(name3);
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            seriesOfActions = builder.moveToElement(player4).click().sendKeys(name4);
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            if(driver.findElements(By.id("member_booking_form_confirm_booking")).size() != 0)
            {
                System.out.println("BUTTON FOUND");
            }
            
            
            
            
            
            
            
            seriesOfActions = builder.moveToElement(submitButton).click(submitButton);
            seriesOfActions.perform();
            
            
            
            
            System.out.println("Booking made!!!!");
            
            
                  
            
        }
        else
        {
            System.out.println("Did not find player 1");
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
