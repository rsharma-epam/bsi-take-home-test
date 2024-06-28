package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private static final String USER_NAME = "#user-name";
    private static final String PASSWORD = "#password";
    private static final String LOGIN_BUTTON = "#login-button";
    private final WebDriver d;

    public LoginPage(WebDriver driver) {
        d = driver;
    }

    public void login(String username, String password) {
        d.findElement(By.cssSelector(USER_NAME)).sendKeys(username);
        d.findElement(By.cssSelector(PASSWORD)).sendKeys(password);
        d.findElement(By.cssSelector(LOGIN_BUTTON)).click();
    }

}
