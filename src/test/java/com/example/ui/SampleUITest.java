package com.example.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

public class SampleUITest {
    WebDriver d;

    @BeforeClass
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        d = new ChromeDriver();
        d.get("https://the-internet.herokuapp.com/login");
    }

    @Test
    public void testValidLogin(){
        d.findElement(By.cssSelector("#username")).sendKeys("tomsmith");
        d.findElement(By.cssSelector("#password")).sendKeys("SuperSecretPassword!");
        d.findElement(By.cssSelector("#password")).submit();

        assertThat(d.findElement(By.cssSelector("#flash.success")).getText()).contains("You logged into a secure area!");
    }

    @AfterClass
    public void closeBrowser(){
        d.quit();
    }

}
