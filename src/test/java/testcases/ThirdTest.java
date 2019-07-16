package testcases;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import steps.BaseTest;

public class ThirdTest extends BaseTest {
    private static final  String TEXT_COLOR_INVALID_LOGIN = "#FF0000";

    @Test
    private void invalidLogin(){
        HomePage pageTest = HomePage.Go(driver);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit")));
        pageTest.scrollTo("loginBtn");
        pageTest.click("loginBtn");
        Assert.assertEquals(pageTest.getColor("invalidLogin"), TEXT_COLOR_INVALID_LOGIN);
    }
}
