package com.saucedemo.ui;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {
    WebDriver d;
    @BeforeClass
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        d = new ChromeDriver();
    }

    @BeforeMethod
    public void visitSite(){
        d.get("https://www.saucedemo.com/");
    }

    private void login(String username, String password) {
        d.findElement(By.cssSelector("#user-name")).sendKeys(username);
        d.findElement(By.cssSelector("#password")).sendKeys(password);
        d.findElement(By.cssSelector("#login-button")).click();
    }

    @Test
    public void testValidLogin(){
        login("standard_user", "secret_sauce");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(d.findElement(By.cssSelector("a[data-test=shopping-cart-link]")).isDisplayed()).isTrue();
        softly.assertThat(d.findElement(By.cssSelector("#react-burger-menu-btn")).isDisplayed()).isTrue();

        assertThat(d.findElement(By.cssSelector("div.header_secondary_container > span")).getText()).isEqualTo("Products");
    }

    @Test
    public void testInvalidLogin(){
        login("locked_out_user", "secret_sauce");
        assertThat(d.findElement(By.cssSelector("h3[data-test=error]")).getText())
                .isEqualTo("Epic sadface: Sorry, this user has been locked out.");
    }

    @AfterMethod
    public void clearBrowser(){
        d.manage().deleteAllCookies();
    }

    @AfterClass
    public void closeBrowser(){
        d.quit();
    }

}
