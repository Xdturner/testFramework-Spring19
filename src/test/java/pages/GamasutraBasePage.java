package pages;

import com.xpxn.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class GamasutraBasePage extends BasePage {
    private By allTopics = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(1) a");
    private By consolePC = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(2) a");
    private By smartphoneTab = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(3) a");
    private By independent = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(4) a");
    private By vrAr = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(5) a");
    private By socialOnline = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(6) a");
    private By gameDevMag = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu .gdmag a");


    public GamasutraBasePage(WebDriver driver){super(driver);}

    private static final String URL = "https://gamasutra.com";

    public static GamasutraBasePage Go(WebDriver driver){
        GamasutraBasePage page = new GamasutraBasePage(driver);
        page.Go();
        return page;
    }

    public void Go(){driver.navigate().to(URL);}
}
