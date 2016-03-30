package selenium.parallel.test;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SearchKeywordsTest extends ParallelSeleniumTestBase {

    @Test
    public void searchForChromeDriver() throws InterruptedException {
        openAndSearch(getDriver(), "chromedriver");
    }

    @Test
    public void searchForJunit() throws InterruptedException {
        openAndSearch(getDriver(), "junit");
    }

    @Test
    public void searchForStackoverflow() throws InterruptedException {
        openAndSearch(getDriver(), "stackoverflow");
    }

    private void openAndSearch(WebDriver driver, String phraseToSearch) throws InterruptedException {
        driver.get("http://www.google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(phraseToSearch);
        searchBox.submit();
        Thread.sleep(3000);
    }

}