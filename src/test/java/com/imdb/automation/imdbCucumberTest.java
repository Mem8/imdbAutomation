package com.imdb.automation;


import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.imdb.automation.Constant.Driver.driver;
import static com.imdb.automation.ElementUtils.*;

@RunWith(Cucumber.class)
public class imdbCucumberTest {

    @Given("^Imdb Sayfasına gidilir$")
    public void imdb_sayfasna_gidilir() throws Throwable {
        goToUrl("https://www.imdb.com/");
        //Confirmation about right site
        String confirmation = driver.getTitle();
        Assert.assertEquals(confirmation, "IMDb: Ratings, Reviews, and Where to Watch the Best Movies & TV Shows");
        System.out.println("IMDB sayfasına gidildi");
    }
    @And("^1929 Oscar sayfasına gidilir$")
    public void oscar_sayfasna_gidilir() throws Throwable {
        clickElementByXpath("//*[@id=\"imdbHeader-navDrawerOpen--desktop\"]");

        WebDriverWait waitOscar = new WebDriverWait(driver, 10);
        waitOscar.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"imdbHeader\"]/div[2]/aside/div/div[2]/div/div[3]/span/div/div/ul/a[1]")));

        clickElementByXpath("//*[@id=\"imdbHeader\"]/div[2]/aside/div/div[2]/div/div[3]/span/div/div/ul/a[1]");
        clickElementByLinkText("1929");

        Assert.assertEquals(driver.getTitle(),"Academy Awards, USA (1929) - IMDb");
        System.out.println("1929 Oscar Sayfasına gidildi");

    }

    @When("^Film bulunur \"([^\"]*)\"$")
    public void film_bulunur(String filmname) throws Throwable {

        scrollAndClickByLinkText(filmname);
        System.out.println(filmname + " film sayfasına gidildi");


    }

    @Then("^Film bilgileri ve resimleri dogrulanır \"([^\"]*)\"$")
    public void film_bilgileri_ve_resimleri_dogrulanir(String filmname) throws Throwable {

        String firstDirectorText = returnTextWithXpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/section/div[3]/div[2]/div[1]/div[3]/ul/li[1]/div");
        String firstWriterText  = returnTextWithXpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/section/div[3]/div[2]/div[1]/div[3]/ul/li[2]/div");
        String firstStarsText  = returnTextWithXpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/section/div[3]/div[2]/div[1]/div[3]/ul/li[3]/div");


        clickElementById("home_img_holder");
        sendKeysById("suggestion-search",filmname);
        driver.findElement(By.id("suggestion-search")).sendKeys(Keys.ENTER);
        clickElementByXpath("//*[@id=\"main\"]/div/div[2]/table/tbody/tr[2]/td[2]/a");

        String secondDirectorText = returnTextWithXpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/section/div[3]/div[2]/div[1]/div[3]/ul/li[1]/div");
        String secondWriterText  = returnTextWithXpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/section/div[3]/div[2]/div[1]/div[3]/ul/li[2]/div");
        String secondStarsText  = returnTextWithXpath("//*[@id=\"__next\"]/main/div/section[1]/section/div[3]/section/section/div[3]/div[2]/div[1]/div[3]/ul/li[3]/div");


        Assert.assertEquals(firstDirectorText,secondDirectorText);
        System.out.println("Direktör isimleri doğrulandı");
        Assert.assertEquals(firstWriterText,secondWriterText);
        System.out.println("Yazar isimleri doğrulandı");
        Assert.assertEquals(firstStarsText,secondStarsText);
        System.out.println("Star isimleri doğrulandı");


        scrollAndClickByPartialLinkText("Photos");

        List<WebElement> imglist = driver.findElements(By.tagName("img"));
        List<WebElement> activeImgList = new ArrayList<WebElement>();



        for (int i=0;i < imglist.size(); i++ ){
            if (imglist.get(i).getAttribute("src")!= null && (! imglist.get(i).getAttribute("src").contains("javascript"))){
                activeImgList.add(imglist.get(i));
            }
        }

        int activeImgCount = 0;
        int totalImgCount = activeImgList.size();

        for (int j=0; j< activeImgList.size(); j++){
            HttpURLConnection connection = (HttpURLConnection) new URL(activeImgList.get(j).getAttribute("src")).openConnection();
            if (connection.getResponseCode() == 200)
                activeImgCount++;
            else
                System.out.println("link bozuk ----> :" + activeImgList.get(j).getAttribute("src") );
            connection.disconnect();

        }

    try{
     WebElement nextisDisplayed = driver.findElement(By.xpath("//*[@id=\"right\"]/a"));

        nextisDisplayed.click();
        List<WebElement> imglistnext = driver.findElements(By.tagName("img"));
        List<WebElement> activeImgListNext = new ArrayList<WebElement>();

        for (int k=0;k < imglistnext.size(); k++ ){
            if (imglistnext.get(k).getAttribute("src")!= null && (! imglistnext.get(k).getAttribute("src").contains("javascript"))){
                activeImgListNext.add(imglistnext.get(k));
            }
        }

        for (int j=0; j< activeImgListNext.size(); j++){
            HttpURLConnection connection = (HttpURLConnection) new URL(activeImgListNext.get(j).getAttribute("src")).openConnection();
            if (connection.getResponseCode() == 200)
                activeImgCount++;
            connection.disconnect();

        }

        totalImgCount += activeImgListNext.size();
        System.out.println("Toplam Resim sayısı : " + totalImgCount);
        System.out.println("Aktif Resim sayısı : "+ activeImgCount);

        Assert.assertEquals(activeImgCount,totalImgCount);

    }
    catch (Exception e) {
            System.out.println("Toplam Resim sayısı : " + totalImgCount);
            System.out.println("Aktif Resim sayısı : "+ activeImgCount);
            Assert.assertEquals(activeImgCount,totalImgCount);
    }
        System.out.println("Resim bilgileri doğrulandı");
    }

    @AfterAll
    public static void before_or_after_all() throws Throwable {
        driver.close();
        System.out.println("Driver Kapatıldı, Testler Tamamlandı");

    }


}
