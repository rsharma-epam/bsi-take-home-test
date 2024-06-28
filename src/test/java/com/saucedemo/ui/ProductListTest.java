package com.saucedemo.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DURATION;

public class ProductListTest {
    public static final String FIRST_PRODUCT_PRICE = "div[data-test=inventory-item]:first-child [data-test=inventory-item-price]";
    public static final String LAST_PRODUCT_PRICE = "div[data-test=inventory-item]:last-child [data-test=inventory-item-price]";
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
    public void testProductSortByPriceAscending() throws InterruptedException {
        assertThat(d.findElement(By.cssSelector(FIRST_PRODUCT_PRICE)).getText()).contains("29.99");
        assertThat(d.findElement(By.cssSelector(LAST_PRODUCT_PRICE)).getText()).contains("15.99");

        Select sortSelector = new Select(d.findElement(By.cssSelector("select[data-test=product-sort-container]")));
        sortSelector.selectByVisibleText("Price (low to high)");

        assertThat(d.findElement(By.cssSelector(FIRST_PRODUCT_PRICE)).getText()).contains("7.99");
        assertThat(d.findElement(By.cssSelector(LAST_PRODUCT_PRICE)).getText()).contains("49.99");
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
