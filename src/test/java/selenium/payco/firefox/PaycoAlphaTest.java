//package selenium.payco.firefox;
//
//import static org.junit.Assert.fail;
//
//import java.net.URL;
//import java.util.concurrent.TimeUnit;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Platform;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.support.ui.Select;
//
//import selenium.Properties;
//
//public class PaycoAlphaTest {
//    private static final String BASE_URL = "http://alpha-www.payco.com";
//    private static final String BASE_WINDOW = "";
//    private static final String LOGIN_WINDOW = "PaycoLoginPopup";
//    private static final String ID = "example@gmail.com";
//    private static final String PW = "qweasd!@#";
//    private static final int INQUERY_FRAME_NUMBER = 0;
//    private static final long WAIT_TIME = 2000;
//
//    private WebDriver driver;
//    private StringBuffer verificationErrors = new StringBuffer();
//
//    @Before
//    public void setUp() throws Exception {
//        DesiredCapabilities capability = DesiredCapabilities.firefox();
//        capability.setBrowserName("firefox");
//        capability.setPlatform(Platform.WINDOWS);
//        driver = new RemoteWebDriver(new URL(Properties.NODE_FIREFOX), capability);
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }
//
//    @Test
//    public void testPaycoAlpha_FAQ_1대1문의하기_문의답변확인() throws InterruptedException {
//        driver.get(BASE_URL + "/cs/faq.nhn");
//
//        // FAQ
//        driver.findElement(By.linkText("FAQ")).click();
//        driver.switchTo().frame(INQUERY_FRAME_NUMBER);
//        new Select(driver.findElement(By.id("category_sel"))).selectByVisibleText("쿠폰&포인트");
//        driver.findElement(By.id("inp_search")).clear();
//        driver.findElement(By.id("inp_search")).sendKeys("포인트");
//        driver.findElement(By.id("searchBtn")).click();
//        driver.findElement(By.linkText("2개 이상의 아이디를 가지고 사용하고 있습니다. 포인트를 공유할 수 있나요?")).click();
//        driver.switchTo().defaultContent();
//
//        // 1:1 문의하기
//        driver.findElement(By.linkText("1:1 문의하기")).click();
//
//        loginProcess();
//
//        driver.findElement(By.linkText("1:1 문의하기")).click();
//        driver.switchTo().frame(INQUERY_FRAME_NUMBER);
//        new Select(driver.findElement(By.id("categoryLevel-1"))).selectByVisibleText("결제/포인트");
//        driver.findElement(By.cssSelector("option[value=\"4907\"]")).click();
//        driver.findElement(By.id("inp_title")).clear();
//        driver.findElement(By.id("inp_title")).sendKeys("test입니다");
//        driver.findElement(By.id("inquiryContent")).clear();
//        driver.findElement(By.id("inquiryContent")).sendKeys("test입니다");
//        driver.findElement(By.id("checkAgree")).click();
//        
////        driver.findElement(By.id("btnSend")).click();
////
////        // 문의/답변 확인
////        driver.findElement(By.linkText("문의/답변 확인")).click();
////        driver.switchTo().frame(INQUERY_FRAME_NUMBER);
////        driver.findElement(By.linkText("test입니다")).click();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        driver.quit();
//        String verificationErrorString = verificationErrors.toString();
//        if (!"".equals(verificationErrorString)) {
//            fail(verificationErrorString);
//        }
//    }
//
//    private void loginProcess() throws InterruptedException {
//        driver.switchTo().window(LOGIN_WINDOW);
//
//        WebElement id = driver.findElement(By.id("id"));
//        WebElement pw = driver.findElement(By.id("pw"));
//
//        id.click();
//        id.clear();
//        id.sendKeys(ID);
//        Thread.sleep(WAIT_TIME);
//        pw.clear();
//        pw.sendKeys(PW);
//        Thread.sleep(WAIT_TIME);
//
//        driver.findElement(By.id("loginBtn")).click();
//        Thread.sleep(WAIT_TIME);
//        driver.switchTo().window(BASE_WINDOW);
//    }
//
//}
