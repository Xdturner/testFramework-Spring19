package com.xpxn.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver,15);
    }

    public void click(String element){click(getBy(element));}

    public void click(By element){getElement(element).click();}

    public By getBy(String name) {
        try {
            Field field = this.getClass().getDeclaredField(name);
            field.setAccessible(true);
            Object obj = field.get(this);
            if (!(obj instanceof By)) {
                throw new IllegalArgumentException("Field " + name + " is not of expected type (By).");
            }
            return (By) obj;
        }catch (IllegalAccessException | NoSuchFieldException e) {
            throw new IllegalArgumentException("Unable to find or access field " + name + ".");
        }
    }

    public void scrollTo(By element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", getElement(element));
    }

    public void scrollTo(String element) {scrollTo(getBy(element));}

    public String getColor(String inputField){return getElement(inputField).getCssValue("color");}

    public String getColor(By inputField){return getElement(inputField).getCssValue("color");}

    public WebElement getElement(By element){return driver.findElement(element);}

    public WebElement getElement(String name){return getElement(getBy(name));}
}
