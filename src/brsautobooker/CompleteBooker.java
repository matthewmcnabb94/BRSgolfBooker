/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brsautobooker;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author matth
 */
public class CompleteBooker {
    
    
    
    static final boolean DEBUG = true; //Set debug mode, so you can run the bot in debug mode.
    
    private static Console console = System.console();
    
    //Initialize components
    private static WebDriver driver;
    private static WebDriverWait driverWait;
    private static Actions builder, seriesOfActions;
    private static Scanner s = new Scanner(System.in);
    private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    private static Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
    
    
    
    
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
        
        
        book();
        
        
               
        
        
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
      
      
      
      public static void navigateToDateToBook()
    {
        
        
        
        driver.get("https://members.brsgolf.com/moyola/bookings/book/1/20200914/1530");
        
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
            seriesOfActions = builder.moveToElement(player1).click().sendKeys("McNabb, Matthew");
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            seriesOfActions = builder.moveToElement(player2).click().sendKeys("Chambers, Stephen");
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            seriesOfActions = builder.moveToElement(player3).click().sendKeys("Kennedy, Ben");
            seriesOfActions.sendKeys(Keys.ENTER);
            seriesOfActions.perform();
            
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(CompleteBooker.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            seriesOfActions = builder.moveToElement(player4).click().sendKeys("McGuigan, Patrick");
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
            
            System.out.println("Booking made!!!!1");
            
            
                  
            
        }
        else
        {
            System.out.println("Did not find player 1");
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
            Logger.getLogger(BRSAutoBooker11.class.getName()).log(Level.SEVERE, null, ex);
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
    
    
    
    
    
    
    
    
    
    
    
    
}
