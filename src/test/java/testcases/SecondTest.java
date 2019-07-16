package testcases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.ConsolePcPage;
import steps.BaseTest;

public class SecondTest extends BaseTest {

    @Test
    private void topicLinkFirst(){
        ConsolePcPage pageTest = ConsolePcPage.Go(driver);
        wait.until(ExpectedConditions.urlContains("console-pc"));
        pageTest.scrollTo("firstTopic");
        pageTest.click("firstTopic");
        wait.until(ExpectedConditions.urlContains("news"));
    }
}
