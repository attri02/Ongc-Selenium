package com.selenium;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestSelenium {


    public static void hitUrl(){

        WebDriver driver1 = new ChromeDriver();
        driver1.get("https://etender.ongc.co.in/irj/portal");

        driver1.findElement(By.cssSelector("input[name=Guest]")).click();

        try{ Thread.sleep(10000); }
        catch(InterruptedException ex){Thread.currentThread().interrupt();}

        List<String> idList = parseList(driver1);
        driver1.close();

        File file1 = new File("/home/aayush/Desktop/Ongc/");
        file1.mkdir();


        for (int i=0;i<idList.size();i++){

            String path = String.format("/home/aayush/Desktop/Ongc/%s/",idList.get(i));
            File file = new File(path);
            file.mkdir();

            ChromeOptions options = new ChromeOptions();
            Map<String,Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_settings.popups", 0);
            prefs.put("download.default_directory",path);
            options.setExperimentalOption("prefs",prefs);
            WebDriver driver = new ChromeDriver(options);
            driver.get("https://etender.ongc.co.in/irj/portal");
            driver.findElement(By.cssSelector("input[name=Guest]")).click();
            try{ Thread.sleep(10000); }
            catch(InterruptedException ex){Thread.currentThread().interrupt();}
            String listPage = driver.getWindowHandle();// save list page

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("Bid Processing");

            String xpath1 = String.format("//*[contains(text(),'%s')]",idList.get(i));
            driver.findElement(By.xpath(xpath1)).click();

            try{ Thread.sleep(10000); }
            catch(InterruptedException ex){Thread.currentThread().interrupt();}

            //switch to detail page
            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea");

            String detailPage = driver.getWindowHandle(); // save detail page

            String detailHtml = driver.getPageSource();
            try {
                File file2 = new File(path+"detailPageHtml.txt");
                FileWriter fileWriter = new FileWriter(file2);
                fileWriter.write(detailHtml);
                fileWriter.flush();
                fileWriter.close();
            }catch (Exception e){}

            driver.findElement(By.id("WD1D-r")).click();
            try{ Thread.sleep(10000); }
            catch(InterruptedException ex){Thread.currentThread().interrupt();}



            //switch to docPage
            for(String winHandle : driver.getWindowHandles()){
                driver.switchTo().window(winHandle);
            }

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

            String xpath2 = "//*[contains(text(),'TENDER DOCUMENTS')]";

            driver.findElement(By.xpath(xpath2)).click();
            try{ Thread.sleep(3000); }
            catch(InterruptedException ex){Thread.currentThread().interrupt();}

            String docListPage = driver.getWindowHandle();

            driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

            List<WebElement> docList = driver.findElement(By.className("sapTbvStd")).findElements(By.cssSelector("tr"));

            /*****************/

            List<String> docListName = new LinkedList<>();


            for (int j=3;j<docList.size();j++) {
                docListName.add(docList.get(j).findElements(By.cssSelector("td")).get(1).getText());
            }

            for (int k=0;k<docListName.size();k++){
                String xpath = String.format("//*[contains(text(),'%s')]",docListName.get(k));
                driver.findElement(By.xpath(xpath)).click();

                try{ Thread.sleep(4000); }
                catch(InterruptedException ex){Thread.currentThread().interrupt();}

                try{driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");
                driver.findElement(By.className("sapTbvStd")).findElement(By.id("cfx_dktabid_row_0")).findElements(By.cssSelector("td")).get(1).findElement(By.cssSelector("a")).click();
                }catch (Exception e){
                    String docListPage2 = driver.getWindowHandle();

                    driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

                    List<WebElement> docList2 = driver.findElement(By.className("sapTbvStd")).findElements(By.cssSelector("tr"));

                    /*****************/

                    List<String> docListName2 = new LinkedList<>();


                    for (int m=3;m<docList2.size();m++) {
                        docListName2.add(docList2.get(m).findElements(By.cssSelector("td")).get(1).getText());
                    }

                    for (int l=0;l<docListName2.size();l++) {
                        String xpath3 =
                            String.format("//*[contains(text(),'%s')]", docListName2.get(l));
                        driver.findElement(By.xpath(xpath3)).click();

                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        }

                        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");
                        driver.findElement(By.className("sapTbvStd")).findElement(By.id("cfx_dktabid_row_0")).findElements(By.cssSelector("td")).get(1).findElement(By.cssSelector("a")).click();

                        try{ Thread.sleep(4000); }
                        catch(InterruptedException ex){Thread.currentThread().interrupt();}

                        ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
                        driver.switchTo().window(windows.get(windows.size()-1));
                        driver.findElement(By.cssSelector("a")).click();
                        driver.close();
                        driver.switchTo().window(docListPage2);
                        driver.navigate().back();
                        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");
                    }
                    driver.navigate().back();
                    driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");
                    continue;
                }


                try{ Thread.sleep(4000); }
                catch(InterruptedException ex){Thread.currentThread().interrupt();}

                ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
                driver.switchTo().window(windows.get(windows.size()-1));
                driver.findElement(By.cssSelector("a")).click();
                driver.close();
                driver.switchTo().window(docListPage);
                driver.navigate().back();
                driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("isolatedWorkArea").switchTo().frame("WD0F");

            }

            //close all windows
            driver.switchTo().window(docListPage);
            driver.close();
            driver.switchTo().window(detailPage);
            driver.close();
            driver.switchTo().window(listPage);
            driver.close();



        }


    }
    public static void main(String[] args){
        hitUrl();
    }


    public static List<String> parseList(WebDriver driver){

        driver.switchTo().defaultContent().switchTo().frame("contentAreaFrame").switchTo().frame("Bid Processing");
        By myBy = new ByChained(By.id("WD67-contentTBody"),By.xpath("//tr[@sst='0']"));

        List<WebElement> detailList = driver.findElements(myBy);
        List<String> idList = new LinkedList<>();
        for (int i=0;i<detailList.size();i++){
            idList.add(detailList.get(i).findElements(By.cssSelector("td")).get(5).getText());
        }

        return idList;

    }






}
