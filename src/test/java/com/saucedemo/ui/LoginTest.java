package com.saucedemo.ui;

import com.saucedemo.pages.LoginPage;
import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

public class LoginTest {
    LoginPage loginPage;
    WebDriver d;
    @BeforeClass
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        d = new ChromeDriver();
        loginPage = new LoginPage(d);
    }

    @BeforeMethod
    public void visitSite(){
        d.get("https://www.saucedemo.com/");
    }


    @Test
    public void testValidLogin(){
        loginPage.login("standard_user", "secret_sauce");

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(d.findElement(By.cssSelector("a[data-test=shopping-cart-link]")).isDisplayed()).isTrue();
        softly.assertThat(d.findElement(By.cssSelector("#react-burger-menu-btn")).isDisplayed()).isTrue();

        assertThat(d.findElement(By.cssSelector("div.header_secondary_container > span")).getText()).isEqualTo("Products");
    }

    @Test(dataProvider = "invalidCreds")
    public void testInvalidLogin(String username, String password, String expectedErrorMsg){
        loginPage.login(username, password);
        assertThat(d.findElement(By.cssSelector("h3[data-test=error]")).getText()).isEqualTo(expectedErrorMsg);
    }

    @DataProvider (name = "invalidCreds")
    public Object[][] getInvalidCreds(){
        return new Object[][] {{"locked_out_user", "secret_sauce", "Epic sadface: Sorry, this user has been locked out."}};
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
