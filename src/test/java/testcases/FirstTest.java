package testcases;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import pages.GamasutraBasePage;
import steps.BaseTest;

public class FirstTest extends BaseTest {

    @Test(priority = 1)
    private void consolePcLink(){
        GamasutraBasePage pageTest = GamasutraBasePage.Go(driver);
        wait.until(ExpectedConditions.urlContains("gamasutra.com"));
        pageTest.scrollTo("consolePC");
        pageTest.click("consolePC");
        wait.until(ExpectedConditions.urlContains("console-pc"));
        pageTest.click("allTopics");
    }

    @Test(priority = 2)
    private void smartphoneTabLink(){
        GamasutraBasePage pageTest = GamasutraBasePage.Go(driver);
        wait.until(ExpectedConditions.urlContains("gamasutra.com"));
        pageTest.scrollTo("smartphoneTab");
        pageTest.click("smartphoneTab");
        wait.until(ExpectedConditions.urlContains("smartphone-tablet"));
    }

    @Test(priority = 3)
    private void independentLink(){
        GamasutraBasePage pageTest = GamasutraBasePage.Go(driver);
        wait.until(ExpectedConditions.urlContains("gamasutra.com"));
        pageTest.scrollTo("independent");
        pageTest.click("independent");
        wait.until(ExpectedConditions.urlContains("indie"));
    }

    @Test(priority = 4)
    private void vrArLink(){
        GamasutraBasePage pageTest = GamasutraBasePage.Go(driver);
        wait.until(ExpectedConditions.urlContains("gamasutra.com"));
        pageTest.scrollTo("vrAr");
        pageTest.click("vrAr");
    }

    @Test(priority = 5)
    private void socialOnlineLink (){
        GamasutraBasePage pageTest = GamasutraBasePage.Go(driver);
        wait.until(ExpectedConditions.urlContains("gamasutra.com"));
        pageTest.scrollTo("socialOnline");
        pageTest.click("socialOnline");
        wait.until(ExpectedConditions.urlContains("social-online"));
    }

    @Test(priority = 6)
    private void gamaMagLink (){
        GamasutraBasePage pageTest = GamasutraBasePage.Go(driver);
        wait.until(ExpectedConditions.urlContains("gamasutra.com"));
        pageTest.scrollTo("gameDevMag");
        pageTest.click("gameDevMag");
        wait.until(ExpectedConditions.urlContains("game-developer"));
    }

}
