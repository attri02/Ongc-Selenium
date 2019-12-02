package com.selenium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium {


    public static void hitUrl(){

        WebDriver driver = new ChromeDriver();
        driver.get("https://etender.ongc.co.in/irj/portal");

        driver.findElement(By.cssSelector("input[name=Guest]")).click();


        //new WebDriverWait(driver, 20)
        //    .until(d -> d.findElement(By.id("WD0192")));

        //WebDriverWait wait = new WebDriverWait(driver, Timespan.FromSeconds(4));

        //driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        try {
            int ch=System.in.read();
            System.out.println("recieved");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<WebElement> entryList = parseList(driver);

        for (int i=1;i<entryList.size();i++){
            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("Bid Processing");
            String listPage = driver.getWindowHandle(); // save list page

            entryList.get(i).findElements(By.cssSelector("td")).get(2).click();

            //switch to detail page
            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea");

            String detailPage = driver.getWindowHandle(); // save detail page

            driver.findElement(By.id("WD1C-r")).click();



            //switch to docPage
            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

            driver.findElement(By.id("cfx_folder_detail_cell_0_0")).click();



            String docListPage = driver.getWindowHandle();

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

            List<WebElement> docList = driver.findElement(By.className("sapTbvStd")).findElements(By.cssSelector("tr"));

            /*****************/

            for (int j=0;j<docList.size();j++){

                docList.get(j).findElements(By.cssSelector("td")).get(1).click();
                driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");
                driver.findElement(By.className("sapTbvStd")).findElement(By.id("cfx_dktabid_row_0")).findElements(By.cssSelector("td")).get(1).click();




            }



        }




        /***





        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("Bid Processing");

        String listPage = driver.getWindowHandle(); // save list page

        driver.findElement(By.id("WD0195")).click();



        //switch to detail page
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea");

        String detailPage = driver.getWindowHandle(); // save detail page

        driver.findElement(By.id("WD1C-r")).click();



        //switch to docPage
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }

        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

        driver.findElement(By.id("cfx_folder_detail_cell_0_0")).click();



        String docListPage = driver.getWindowHandle();

        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");




***/







        //List<WebElement> detailElements = driver.findElements(By.cssSelector("a[class=  urLnkFunction urTxtStd]"));
        //
        //
        //detailElements.get(3).click();

        //WebElement element = driver.findElement(By.name("q"));
        //element.sendKeys("terminator\n");

        //System.out.println("Title: " + driver.getTitle());
        //driver.quit();

    }
    public static void main(String[] args){
        hitUrl();
    }


    public static List<WebElement> parseList(WebDriver driver){

        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("Bid Processing");
        //List<WebElement> detailList = driver.findElement(By.id("WD67-contentTBody")).findElements(By.cssSelector("tr"));
        By myBy = new ByChained(By.id("WD67-contentTBody"),By.xpath("//tr[@sst='0']"));

        List<WebElement> detailList = driver.findElements(myBy);

        return detailList;

        //return detailList.get(i).findElements(By.cssSelector("td")).get(2);

    }






}
