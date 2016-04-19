package mmt.scripts;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class,
  MethodListener.class })
public class TestNGScript {


       //Set Property for ATU Reporter Configuration
       {
         System.setProperty("atu.reporter.config", "C:\\Users\\rajinder_singh\\Desktop\\atu.properties");

       }


       WebDriver driver;

       @BeforeClass
       public void init() {
              driver = new FirefoxDriver();
              ATUReports.setWebDriver(driver);
              setIndexPageDescription();
       }

       private void setAuthorInfoForReports() {
              ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),
                           "1.0");
       }

       private void setIndexPageDescription() {
              ATUReports.indexPageDescription = "My Project Description <br/> <b>Can include Full set of HTML Tags</b>";
       }

            //Deprecated Methods
       @Test
       public void testME() {
              setAuthorInfoForReports();
              ATUReports.add("Step Desc", false);
              ATUReports.add("Step Desc", "inputValue", false);
              ATUReports.add("Step Desc", "expectedValue", "actualValue", false);
              ATUReports.add("Step Desc", "inputValue", "expectedValue",
                           "actualValue", false);
       }


       //New Way of Logging

       @Test
       public void testNewLogs() throws AWTException, IOException {

              ATUReports.add("INfo Step", LogAs.INFO, new CaptureScreen(
                           ScreenshotOf.BROWSER_PAGE));
              ATUReports.add("Pass Step", LogAs.PASSED, new CaptureScreen(
                           ScreenshotOf.DESKTOP));
              WebElement element = driver
                           .findElement(By.xpath("/html/body/div/h1/a"));
              ATUReports.add("Warning Step", LogAs.WARNING,
                           new CaptureScreen(element));
              ATUReports.add("Fail step", LogAs.FAILED, new CaptureScreen(
                           ScreenshotOf.DESKTOP));
       }

}
