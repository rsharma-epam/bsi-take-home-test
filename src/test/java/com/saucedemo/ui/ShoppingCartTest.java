package com.saucedemo.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartTest {
    public static final String BACKPACK_ADD_TO_CART = "//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']//ancestor::div[@data-test='inventory-item']//button[text()='Add to cart']";
    public static final String BIKELIGHT_ADD_TO_CART = "//div[@data-test='inventory-item-name' and text()='Sauce Labs Bike Light']//ancestor::div[@data-test='inventory-item']//button[text()='Add to cart']";
    public static final String TSHIRT_ADD_TO_CART = "//div[@data-test='inventory-item-name' and text()='Sauce Labs Bolt T-Shirt']//ancestor::div[@data-test='inventory-item']//button[text()='Add to cart']";
    public static final String CART_BADGE = "a.shopping_cart_link span.shopping_cart_badge";
    public static final String SHOPPING_CART_LINK = "a.shopping_cart_link";
    public static final String CART_ITEM_NAME = "div.cart_list div.cart_item div.inventory_item_name";
    public static final String CART_ITEM_QUANTITY = "div.cart_list div.cart_item div.cart_quantity";
    WebDriver d;
    @BeforeClass
    public void configureBrowser(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
    }

    @BeforeMethod
    public void visitProductPage(){
        d = new ChromeDriver();
        d.get("https://www.saucedemo.com/");
        login("standard_user", "secret_sauce");
    }

    private void login(String username, String password) {
        d.findElement(By.cssSelector("#user-name")).sendKeys(username);
        d.findElement(By.cssSelector("#password")).sendKeys(password);
        d.findElement(By.cssSelector("#login-button")).click();
    }

    @Test
    public void testAddSingleItemToCart() {
        d.findElement(By.xpath(BACKPACK_ADD_TO_CART)).click();

        assertThat(d.findElement(By.cssSelector(CART_BADGE)).getText()).isEqualTo("1");

        d.findElement(By.cssSelector(SHOPPING_CART_LINK)).click();

        assertThat(d.findElement(By.cssSelector(CART_ITEM_NAME)).getText()).isEqualTo("Sauce Labs Backpack");
        assertThat(d.findElement(By.cssSelector(CART_ITEM_QUANTITY)).getText()).isEqualTo("1");
    }

    @Test(enabled = false)
    public void testAddMultipleItemsToCart(){
        d.findElement(By.xpath(BACKPACK_ADD_TO_CART)).click();

        assertThat(d.findElement(By.cssSelector(CART_BADGE)).getText()).isEqualTo("1");

        d.findElement(By.xpath(BIKELIGHT_ADD_TO_CART)).click();
        d.findElement(By.xpath(TSHIRT_ADD_TO_CART)).click();

        assertThat(d.findElement(By.cssSelector(CART_BADGE)).getText()).isEqualTo("3");
    }

    @AfterMethod
    public void closeBrowser(){
        d.quit();
    }
}
