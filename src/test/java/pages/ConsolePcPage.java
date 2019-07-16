package pages;

import com.xpxn.automation.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConsolePcPage extends BasePage {
    private By consolePC = By.cssSelector(".container .content-body-wrapper .span-20 .hide-phone .last .topicmenu ul li:nth-child(2) a");
    private By firstTopic = By.cssSelector(".content_box_middle:nth-child(1) .feed_item .story_title a");

    public ConsolePcPage(WebDriver driver) {
        super(driver);
    }

    private static final String URL = "https://www.gamasutra.com/topic/console-pc";

    public static ConsolePcPage Go(WebDriver driver) {
        ConsolePcPage page = new ConsolePcPage(driver);
        page.Go();
        return page;
    }

    public void Go() {
        driver.navigate().to(URL);
    }
}
