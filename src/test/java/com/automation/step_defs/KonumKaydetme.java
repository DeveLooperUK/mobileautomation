package com.automation.step_defs;

import com.automation.Utilities.Utilities;
import io.appium.java_client.*;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class KonumKaydetme {

    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
    AppiumDriver<MobileElement> driver;

    static String ilAdi;
    static String ilceAdi;
    static String mahalleAdi;

    int upperBound;
    int random;

    @Before
    public void setup(){
        System.out.println("Test basladi!");
        //we use android phone
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        //desiredCapabilities.setCapability("platformName", "android");
        //version of android
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION,"8.0");
        //name of the device, if it is real device we need to pass UUID parameter
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"Pixel_2");
        System.out.println("device configured!");

        //either you specify app--> //path/to/app.apk
        //or if app is already installed, you need to specify appActivity and appPackage
        //this info you can find in the internet, at work - ask to developers
        String fullPath = System.getProperty("user.dir")+"/src/test/resources/hepsiburada-5-4-2.apk";
        System.out.println("fullPath = " + fullPath);
        desiredCapabilities.setCapability("app", fullPath);
        System.out.println("app was invoked!");

        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
        System.out.println("AUTOMATION_NAME is UiAutomator2");
    }

    @After
    public void teardown(){
        //close the app at the end
        driver.closeApp();
        System.out.println("\n----- hepsiburada uygulamasi kapatildi! ---------");
    }



    @Given("Uygulamayi actim")
    public void uygulamayi_actim() throws MalformedURLException, InterruptedException {
        driver = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"),desiredCapabilities);
        System.out.println("\n------ hepsiburada uygulamasi baslatildi! -------");
        Utilities.waitFor(3);
    }

    @When("Konum alanina tikladim")
    public void konum_alanina_tikladim() {

        MobileElement konum = driver.findElement(By.id("com.pozitron.hepsiburada:id/imageViewIcon"));
        System.out.println("konum before click = " + konum.getText());
        Utilities.waitFor(1);
        try {konum.click();}
        catch (Exception e){
            driver.navigate().back();
            Utilities.waitFor(1);
            konum.click();
            e.printStackTrace();
        }
        Utilities.waitFor(2);
    }


    @When("il sectim")
    public void il_sectim() {
        MobileElement il = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"İl seçin\"]"));
        System.out.println("il.getText() = " + il.getText());
        il.click();
        Utilities.waitFor(1);
        List<MobileElement> ilList = driver.findElements(By.id("com.pozitron.hepsiburada:id/tvOneProvinceSelectBox"));
        System.out.println("ilList.get(1) = " + ilList.get(1));
        upperBound = ilList.size();
        random = (int) (Math.random() * upperBound);
        Utilities.waitFor(1);
        ilAdi = ilList.get(random).getText();
        Utilities.waitFor(1);
        System.out.println("ilAdi = " + ilAdi);
        ilList.get(random).click();
        Utilities.waitFor(1);
    }

    @When("ilce sectim")
    public void ilce_sectim() {
        MobileElement ilce = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"İlçe seçin\"]"));
        ilce.click();
        Utilities.waitFor(1);
        List<MobileElement> ilceList = driver.findElements(By.id("com.pozitron.hepsiburada:id/tvOneProvinceSelectBox"));
        upperBound = ilceList.size();
        random = (int) (Math.random() * upperBound);
        Utilities.waitFor(1);
        ilceAdi = ilceList.get(random).getText();
        Utilities.waitFor(1);
        System.out.println("ilceAdi = " + ilceAdi);
        ilceList.get(random).click();
        Utilities.waitFor(1);
    }

    @When("mahallle sectim")
    public void mahallle_sectim(){
        MobileElement mahalle = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"Mahalle seçin\"]"));
        mahalle.click();
        Utilities.waitFor(1);
        List<MobileElement> mahalleList = driver.findElements(By.id("com.pozitron.hepsiburada:id/tvOneProvinceSelectBox"));
        upperBound = mahalleList.size();
        random = (int) (Math.random() * upperBound);
        Utilities.waitFor(1);
        mahalleAdi = mahalleList.get(random).getText();
        Utilities.waitFor(1);
        System.out.println("mahalleAdi = " + mahalleAdi);
        mahalleList.get(random).click();
        Utilities.waitFor(1);
    }

    @When("Kaydet butonuna tikladim")
    public void kaydet_butonuna_tikladim() throws InterruptedException {
        MobileElement kaydetButon = driver.findElement(By.id("com.pozitron.hepsiburada:id/button"));
        kaydetButon.click();
        Thread.sleep(500);
    }

    @Then("Popupta {string} yi gordum")
    public void popupta_yi_gordum(String string) {
        MobileElement popupKonumTeyit = driver.findElement(By.id("com.pozitron.hepsiburada:id/tvTitle"));
        String popup = popupKonumTeyit.getText();
        System.out.println("popup = " + popup);
        Assert.assertEquals(popup, "Konumunuz kaydedildi.");
    }
    @Given("Tab bar uzerinden kategoriler tabına tikladim")
    public void tab_bar_uzerinden_kategoriler_tabına_tikladim() {
        Utilities.waitFor(1);
        MobileElement kategorilerim = driver.findElement(By.id("Kategorilerim"));
        kategorilerim.click();
        Utilities.waitFor(2);
    }

    @When("Bir kategori sectim")
    public void bir_kategori_sectim() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("Yarin Kapinda alanindaki il bilgisini kaydettim")
    public void yarin_kapinda_alanindaki_il_bilgisini_kaydettim() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("Anasayfada secilen il bilgisi ile Yarin Kapinda alanindaki il bilgisinin ayni oldugunu dogruladim")
    public void anasayfada_secilen_il_bilgisi_ile_yarin_kapinda_alanindaki_il_bilgisinin_ayni_oldugunu_dogruladim() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
