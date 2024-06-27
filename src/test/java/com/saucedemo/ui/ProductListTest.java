package com.saucedemo.ui;

import org.assertj.core.api.SoftAssertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductListTest {
    WebDriver d;
    @BeforeClass
    public void launchBrowser(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        d = new ChromeDriver();
    }

    @BeforeMethod
    public void visitProductPage(){
        d.get("https://www.saucedemo.com/");
        login("standard_user", "secret_sauce");
    }

    private void login(String username, String password) {
        d.findElement(By.cssSelector("#user-name")).sendKeys(username);
        d.findElement(By.cssSelector("#password")).sendKeys(password);
        d.findElement(By.cssSelector("#login-button")).click();
    }

    @Test
    public void testProductSortByPrice(){
        WebElement firstProductPrice = d.findElement(By.cssSelector("div[data-test=inventory-item]:first-child [data-test=inventory-item-price]"));
        WebElement lastProductPrice = d.findElement(By.cssSelector("div[data-test=inventory-item]:last-child [data-test=inventory-item-price]"));

        assertThat(firstProductPrice.getText()).contains("29.99");
        assertThat(lastProductPrice.getText()).contains("15.99");

        Select sortSelector = new Select(d.findElement(By.cssSelector("select[data-test=product-sort-container]")));
        sortSelector.selectByVisibleText("Price (low to high)");

        assertThat(firstProductPrice.getText()).contains("7.99");
        assertThat(lastProductPrice.getText()).contains("49.99");
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
