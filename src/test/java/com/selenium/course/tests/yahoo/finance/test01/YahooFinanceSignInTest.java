package com.selenium.course.tests.yahoo.finance.test01;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YahooFinanceSignInTest {
    WebDriver driver = null;
    String[] inputDataArray = new String[2];

    @BeforeTest
    public void setUpDriver(){
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
    }

    @Test

    public void signUpForYahooFinance() {
        String[] inputDataArray = { "Chicho Mitko test  password +359 qaz June ff dd",
                                    "Kaka Minka test  pass +359 poi February ee rr"
        };

        for (int i = 1; i <= inputDataArray.length; i++){
            driver.get("https://finance.yahoo.com/");
            if (i == 1){
                driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
                driver.findElement(By.xpath("//button[@class='btn primary']")).click();
            }

            executeSignInButton();
            executeCreateAnAccountButton();
            setSignUpData(inputDataArray[i-1]);
            validateErrorMessages();
        }
    }
    public void executeSignInButton(){
        WebElement singInButton = driver.findElement(By.id("header-signin-link"));
        singInButton.click();
    }
    public void executeCreateAnAccountButton(){
        WebElement createAnAccountButton = driver.findElement(By.xpath("//p[contains(@class,'sign-up-link')]"));
        createAnAccountButton.click();
    }
    public void setSignUpData(String inputString){

        List<String> inputData = new ArrayList<String>(10);
//        String inputString = "Chicho Mitko test  password +359 qaz June ff dd";
        String[] tokens = inputString.split("\\s");

        for (int i=0; i<tokens.length; i++){
            inputData.add(i,tokens[i]);
        }

        final Actions actions = new Actions(driver);
        WebElement firstName = driver.findElement(By.xpath("//div[contains(@class, 'first-name')]"));
        firstName.click();
        inputData.forEach((inputToken) ->  actions
                .sendKeys(inputToken + Keys.TAB).build().perform()
        );

        WebElement continueButton = driver.findElement(By.id("reg-submit-button"));
        continueButton.click();
//        Thread.sleep(2000);
    }

    public void validateErrorMessages(){
        String actualRegErrorYid = driver.findElement(By.id("reg-error-yid")).getText();
        String expectedRegErrorYid = "This email address is not available for sign up, try something else";
        Assert.assertEquals(actualRegErrorYid, expectedRegErrorYid);

        String actualRegErrorPasword = driver.findElement(By.id ("reg-error-password")).getText();
        String expectedRegErrorPassword = "Your password isn’t strong enough, try making it longer.";
        Assert.assertEquals(actualRegErrorPasword, expectedRegErrorPassword);

        String actualRegErrorPhone = driver.findElement(By.id ("reg-error-phone")).getText();
        String expectedRegErrorPhone = "That doesn’t look right, please re-enter your phone number.";
        Assert.assertEquals(actualRegErrorPhone, expectedRegErrorPhone);

        String actualRegErrorbirthDate = driver.findElement(By.id ("reg-error-birthDate")).getText();
        String expectedRegErrorbirthDate = "That doesn’t look right, please re-enter your birthday.";
        Assert.assertEquals(actualRegErrorbirthDate, expectedRegErrorbirthDate);
    }


    @AfterTest
    public void closeDriver(){
        driver.quit();
    }

}
