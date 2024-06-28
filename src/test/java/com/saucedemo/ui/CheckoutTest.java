package com.saucedemo.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CheckoutTest {
    public static final String BACKPACK_ADD_TO_CART = "//div[@data-test='inventory-item-name' and text()='Sauce Labs Backpack']//ancestor::div[@data-test='inventory-item']//button[text()='Add to cart']";
    public static final String BIKELIGHT_ADD_TO_CART = "//div[@data-test='inventory-item-name' and text()='Sauce Labs Bike Light']//ancestor::div[@data-test='inventory-item']//button[text()='Add to cart']";
    public static final String TSHIRT_ADD_TO_CART = "//div[@data-test='inventory-item-name' and text()='Sauce Labs Bolt T-Shirt']//ancestor::div[@data-test='inventory-item']//button[text()='Add to cart']";
    public static final String CART_BADGE = "a.shopping_cart_link span.shopping_cart_badge";
    public static final String SHOPPING_CART_LINK = "a.shopping_cart_link";
    public static final String CART_ITEM_NAME = "div.cart_list div.cart_item div.inventory_item_name";
    public static final String CART_ITEM_QUANTITY = "div.cart_list div.cart_item div.cart_quantity";
    public static final String HEADER = "div.header_secondary_container > span";
    public static final String PAGE_HEADING = "div.header_secondary_container > span";
    public static final String MSG_HEADER = "h2.complete-header";
    public static final String MSG_TEXT = "div.complete-text";
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
        addItemsToCart();
        visitCartPage();
    }

    private void login(String username, String password) {
        d.findElement(By.cssSelector("#user-name")).sendKeys(username);
        d.findElement(By.cssSelector("#password")).sendKeys(password);
        d.findElement(By.cssSelector("#login-button")).click();
    }

    private void addItemsToCart() {
        d.findElement(By.xpath(BACKPACK_ADD_TO_CART)).click();
        d.findElement(By.xpath(BIKELIGHT_ADD_TO_CART)).click();
        d.findElement(By.xpath(TSHIRT_ADD_TO_CART)).click();
    }

    private void visitCartPage() {
        d.findElement(By.cssSelector(SHOPPING_CART_LINK)).click();
    }

    @Test
    public void testCheckout(){
        submitCheckoutDetails();

        assertThat(d.findElement(By.cssSelector(HEADER)).getText()).isEqualTo("Checkout: Overview");
        assertThat(getCheckoutItemNames()).contains("Sauce Labs Backpack", "Sauce Labs Bike Light", "Sauce Labs Bolt T-Shirt");

        d.findElement(By.cssSelector("button#finish")).click();

        assertThat(d.findElement(By.cssSelector(PAGE_HEADING)).getText()).isEqualTo("Checkout: Complete!");
        assertThat(d.findElement(By.cssSelector(MSG_HEADER)).getText()).isEqualTo("Thank you for your order!");
        assertThat(d.findElement(By.cssSelector(MSG_TEXT)).getText()).isEqualTo("Your order has been dispatched, and will arrive just as fast as the pony can get there!");
    }

    private void submitCheckoutDetails() {
        d.findElement(By.cssSelector("#checkout")).click();
        d.findElement(By.cssSelector("#first-name")).sendKeys("fname");
        d.findElement(By.cssSelector("#last-name")).sendKeys("fname");
        d.findElement(By.cssSelector("#postal-code")).sendKeys("12345");
        d.findElement(By.cssSelector("#continue")).click();
    }

    private List<String> getCheckoutItemNames() {
        List<String> cartItemNames = new ArrayList<>();
        List<WebElement> cartItems = d.findElements(By.cssSelector(CART_ITEM_NAME));

        for (WebElement itemName: cartItems) {
            cartItemNames.add(itemName.getText());
        }

        return cartItemNames;
    }

    @AfterMethod
    public void closeBrowser(){
        d.quit();
    }
}
