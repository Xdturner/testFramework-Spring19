package com.xpxn.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverFactory() {
    }

    public static MutableCapabilities setup(String env) {
        switch (env) {
            case "local":
                return setupLocal();
            case "remote":
                return setupRemote();

            default:
                return null;
        }
    }

    public static WebDriver build(String env, MutableCapabilities caps) throws MalformedURLException {
        if (driver.get() != null) {
            return driver.get();
        }

        switch (env) {
            case "local":
                return buildLocal(caps);
            case "remote":
                return buildRemote(caps);
            default:
                return null;
        }
    }

    public static MutableCapabilities setupLocal(){
        String browser = Configuration.getBrowserName();
        MutableCapabilities caps = null;

        switch (browser.toLowerCase()) {
            case "chrome":
                caps = new ChromeOptions();
                break;
            case "edge":
                caps = new EdgeOptions();
                break;
            case"firefox":
                caps = new FirefoxOptions();
                break;
            default:
                break;
        }
        return caps;
    }

    public static MutableCapabilities setupRemote(){
        return new DesiredCapabilities();
    }

    public static WebDriver buildLocal(MutableCapabilities caps) {
        String browser = Configuration.getBrowserName();
        WebDriver dr;

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                dr = new ChromeDriver((ChromeOptions)caps);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                dr = new EdgeDriver((EdgeOptions)caps);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                dr = new FirefoxDriver((FirefoxOptions)caps);
                break;
            default:
                throw new IllegalArgumentException("Provided browser '" + browser + "' is not supported.");
        }
        driver.set(dr);
        return dr;
    }

    public static WebDriver buildRemote(MutableCapabilities caps) throws MalformedURLException {
        return new RemoteWebDriver(new URL(Configuration.getRemoteURL()), caps);
    }
    public static void DestroyDriver() {
        driver.get().quit();
        driver.set(null);
    }

}