package selenium.bugs.chrome;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import selenium.PropertiesUtil;

public class HelpBugsChromeTest {
    private static WebDriver driver;
    private static StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capability = DesiredCapabilities.chrome();
        capability.setBrowserName("chrome");
        capability.setPlatform(Platform.WINDOWS);
        capability.setVersion("7");

        driver = new RemoteWebDriver(new URL(PropertiesUtil.getProperty("NODE_CHROME")), capability);
    }

    @After
    public void tearDown() throws Exception {
        driver.switchTo().window(PropertiesUtil.getProperty("BASE_WINDOW"));
        driver.close();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    @Test
    public void test_자료실접근() {
        driver.get(PropertiesUtil.getProperty("BASE_URL"));
        driver.findElement(By.linkText("고객센터")).click();
        driver.findElement(By.linkText("자료실")).click();
        driver.findElement(By.cssSelector("li.item5 > button.btnNormal.download")).click();
        driver.close();
    }
    
    @Test
    public void test_이벤트당첨() throws InterruptedException {
        driver.get(PropertiesUtil.getProperty("BASE_URL"));
        driver.findElement(By.linkText("고객센터")).click();
        driver.findElement(By.linkText("이벤트 당첨")).click();
        
        for(String windowHandle : driver.getWindowHandles()){
            driver.switchTo().window(windowHandle);
        }
        assertEquals("http://event.bugs.co.kr/winner", driver.getCurrentUrl());
        driver.close();
    }
    
    @Test
    public void test_공지사항() throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        
        driver.get(PropertiesUtil.getProperty("BASE_URL"));
        driver.findElement(By.linkText("고객센터")).click();
        driver.findElement(By.linkText("공지사항")).click();
        
        driver.switchTo().window("_balnk");
        assertEquals("http://www.bugs.co.kr/board/notice", driver.getCurrentUrl());
        driver.manage().window().maximize();
        new WebDriverWait(driver, PropertiesUtil.getWaitTime("WAIT_TIME")).until(ExpectedConditions.urlToBe(PropertiesUtil.getProperty("BASE_URL")+"board/notice"));
        List<WebElement> noticeList = driver.findElements(By.cssSelector("tr > th > a"));
        
        for(WebElement element : noticeList) {
            new WebDriverWait(driver, PropertiesUtil.getWaitTime("WAIT_TIME")).until(ExpectedConditions.elementToBeClickable(element));
            js.executeScript("window.scrollBy(0, 135);");
            element.click();
            element.click();
        }
        driver.close();
    }
    
    @Test
    public void test_나의문의확인() throws InterruptedException {
        driver.get(PropertiesUtil.getProperty("BASE_URL"));
        driver.manage().window().maximize();
        driver.findElement(By.linkText("고객센터")).click();
        driver.findElement(By.linkText("나의 문의 확인")).click();
        driver.findElement(By.id(PropertiesUtil.getProperty("LOGIN_BUTTON"))).click();
        
        loginProcess();
        
        new WebDriverWait(driver, PropertiesUtil.getWaitTime("WAIT_TIME")).until(ExpectedConditions.urlToBe(PropertiesUtil.getProperty("BASE_URL")));
        driver.findElement(By.linkText("고객센터")).click();
        driver.findElement(By.linkText("나의 문의 확인")).click();
        assertEquals("http://help.bugs.co.kr/inquiry/list?target=bugs/myInquiry/list", driver.getCurrentUrl()); 
        driver.findElement(By.linkText("1:1 문의하기")).click();
        assertEquals("http://help.bugs.co.kr/inquiry/reg?target=bugs/inquiry/form", driver.getCurrentUrl());
    }
    
    @Test
    public void test_로그아웃() {
        driver.get(PropertiesUtil.getProperty("BASE_URL"));
        driver.manage().window().maximize();
        driver.findElement(By.linkText("로그인 / 회원가입")).click();
        driver.findElement(By.id(PropertiesUtil.getProperty("LOGIN_BUTTON"))).click();
        
        loginProcess();
        
        new WebDriverWait(driver, PropertiesUtil.getWaitTime("WAIT_TIME")).until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.bu")));
        driver.findElement(By.cssSelector("span.bu")).click();
        driver.findElement(By.linkText("로그아웃")).click();
        assertTrue(driver.findElement(By.linkText("로그인 / 회원가입")).isDisplayed());
    }
    
    private void loginProcess() {
        driver.switchTo().window(PropertiesUtil.getProperty("LOGIN_WINDOW"));

        new WebDriverWait(driver, PropertiesUtil.getWaitTime("WAIT_TIME")).until(ExpectedConditions.urlContains(PropertiesUtil.getProperty("LOGIN_OAUTH_URL")));
        WebElement id = driver.findElement(By.id("id"));
        WebElement pw = driver.findElement(By.id("pw"));
        
        id.clear();        id.sendKeys(PropertiesUtil.getProperty("ID"));
        pw.clear();        pw.sendKeys(PropertiesUtil.getProperty("PW"));
        
        driver.findElement(By.id("loginBtn")).click();
        driver.switchTo().window(PropertiesUtil.getProperty("BASE_WINDOW"));
    }

}
