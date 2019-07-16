package steps;

import com.xpxn.automation.config.Configuration;
import com.xpxn.automation.config.DriverFactory;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.SoftAssert;

import java.net.MalformedURLException;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected SoftAssert softAssert;


    @BeforeClass
    protected void _InitDriver() throws MalformedURLException {
        MutableCapabilities caps = DriverFactory.setup(Configuration.getDriverEnvironment());
        driver = DriverFactory.build(Configuration.getDriverEnvironment(), caps);
        wait = new WebDriverWait(driver, 15);
        softAssert = new SoftAssert();
    }

    @AfterClass
    protected void _DestroyDriver() {
        DriverFactory.DestroyDriver();
        softAssert.assertAll();
    }
}
