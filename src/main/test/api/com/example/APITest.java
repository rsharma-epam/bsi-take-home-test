package api.com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class APITest {
    WebDriver d;

    @BeforeClass
    public void launchBrowser(){
        d.get("https://the-internet.herokuapp.com/login");
        WebDriver d = new ChromeDriver();
    }

    @Test
    public void testValidLogin(){
        d.findElement(By.cssSelector("#username")).sendKeys("tomsmith");
        d.findElement(By.cssSelector("#password")).sendKeys("SuperSecretPassword!");
        d.findElement(By.cssSelector("#password")).submit();
    }

    @AfterClass
    public void closeBrowser(){
        d.quit();
    }

}
