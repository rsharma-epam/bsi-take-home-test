package com.saucedemo.ui;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {
    WebDriver d;
    @BeforeClass
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        d = new ChromeDriver();
        d.get("https://www.saucedemo.com/");
    }

    @Test
    public void testValidLogin(){
        d.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
        d.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
        d.findElement(By.cssSelector("#password")).submit();

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(d.findElement(By.cssSelector("#react-burger-menu-btn")).isDisplayed()).isTrue();
        softly.assertThat(d.findElement(By.cssSelector("a[data-test=shopping-cart-link]")).isDisplayed()).isTrue();

        assertThat(d.findElement(By.cssSelector("div.header_secondary_container > span")).getText()).isEqualTo("Products");
    }

    @AfterClass
    public void closeBrowser(){
        d.quit();
    }

}
