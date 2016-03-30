package selenium.parallel.test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import selenium.Properties;

@RunWith(Parallelized.class)
public class ParallelSeleniumTestBase {
    
    enum WebDriverType {
        CHROME,
        FIREFOX
    }

    static class WebDriverFactory {
        static WebDriver create(WebDriverType type) throws MalformedURLException {
            WebDriver driver;
            switch (type) {
            case FIREFOX:
                DesiredCapabilities capFirefox = DesiredCapabilities.firefox();
                capFirefox.setBrowserName("firefox");
                capFirefox.setPlatform(Platform.WINDOWS);
                driver = new RemoteWebDriver(new URL(Properties.LOCAL_URL), capFirefox);
                break;
            case CHROME:
                DesiredCapabilities capChrome = DesiredCapabilities.chrome();
                capChrome.setBrowserName("chrome");
                capChrome.setPlatform(Platform.WINDOWS);
                driver = new RemoteWebDriver(new URL(Properties.LOCAL_URL), capChrome);
                break;
            default:
                throw new IllegalStateException();
            }
            return driver;
        }
    }

    @Parameter
    public WebDriverType currentDriverType;

    @Parameters(name= "{0}")
    public static Collection<Object[]> driverTypes() {
        return Arrays.asList(new Object[][] {
                { WebDriverType.CHROME },
                { WebDriverType.FIREFOX }
                });
    }

    private static ThreadLocal<WebDriver> currentDriver = new ThreadLocal<WebDriver>();
    private static List<WebDriver> driversToCleanup = Collections.synchronizedList(new ArrayList<WebDriver>());

    @Before
    public void driverInit() throws MalformedURLException {
        if (currentDriver.get()==null) {
            WebDriver driver = WebDriverFactory.create(currentDriverType);
            driversToCleanup.add(driver);
            currentDriver.set(driver);
        }
    }

    protected WebDriver getDriver() {
        return currentDriver.get();
    }
    
    @Test
    public void browserPopupTest() {
        
    }

    @AfterClass
    public static void driverCleanup() {
        Iterator<WebDriver> iterator = driversToCleanup.iterator();
        while (iterator.hasNext()) {
            WebDriver driver = iterator.next();
            driver.quit();
            iterator.remove();
        }
    }
}