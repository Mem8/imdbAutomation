package com.imdb.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.util.List;

import static com.imdb.automation.Constant.Driver.*;
public class ElementUtils {

    public static void clickElementById(String id){
        driver.findElement(By.id(id)).click();
    }

    public static void clickElementByXpath(String xpath){
        driver.findElement(By.xpath(xpath)).click();
    }

    public static void sendKeysById(String id,String keys){
        driver.findElement(By.id(id)).sendKeys(keys);
    }

    public static void clickElementByLinkText(String linkText){
        driver.findElement(By.linkText(linkText)).click();
    }

    public static void goToUrl(String url) { driver.get(url);}

    public static String returnTextWithXpath(String xpath){
        List<WebElement> returnTextWithXpathElement = driver.findElements(By.xpath(xpath));
        WebElement returnTextElement = returnTextWithXpathElement.get(0);
        return returnTextElement.getText();
    }
    public static void scrollAndClickByPartialLinkText(String partialLinkText){
        WebElement scrollClick = driver.findElement(By.partialLinkText(partialLinkText));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)",scrollClick);
        scrollClick.click();
    }
    public static void scrollAndClickByLinkText(String LinkText){
        WebElement scrollClick = driver.findElement(By.linkText(LinkText));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)",scrollClick);
        scrollClick.click();
    }
}
