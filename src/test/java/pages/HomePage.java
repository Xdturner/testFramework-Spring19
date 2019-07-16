package pages;

import com.xpxn.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private By loginBtn = By.cssSelector("#submit");
    private By invalidLogin = By.cssSelector("#memeberLogin #status");

    public HomePage(WebDriver driver){super(driver);}

    private static final String URL = "https://gamasutra.com";

    public static HomePage Go(WebDriver driver){
        HomePage page = new HomePage(driver);
        page.Go();
        return page;
    }

    public void Go(){driver.navigate().to(URL);}
}
