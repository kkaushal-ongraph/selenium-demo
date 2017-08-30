package com.test.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Vandana Agarwal on 3/29/2016.
 */
public class SeleniumCore {
    public static final String BROWSER;
    public static final String BIZAPPCENTER_LOGIN_URL;
    private static final String FIREFOX = "Firefox";
    private static final String CHROME = "Chrome";
    private static final String IE = "IE";
    private static final String SAFARI = "Safari";
    private static Properties BIZAPPCENTERTEST_PROPERTIES;
    private static Logger logger = LoggerFactory.getLogger(SeleniumCore.class);

    static {
        InputStream instream;
        instream = SeleniumCore.class
                .getClassLoader()
                .getResourceAsStream(
                        TestConstants.BIZAPPCENTERTEST_ENVIORNMENT_FOLDERNAME
                                + File.separator
                                + TestConstants.BIZAPPCENTERTEST_PROPERTIES_ENVIORNMENT_FILENAME
                );
        BIZAPPCENTERTEST_PROPERTIES = new Properties();
        try {
            BIZAPPCENTERTEST_PROPERTIES.load(instream);
        } catch (Exception e) {
            logger.debug("Exception in loading environment property file "
                    + TestConstants.BIZAPPCENTERTEST_PROPERTIES_ENVIORNMENT_FILENAME, e);
        }
        BROWSER = getSpecificProperty(TestConstants.BROWSER);
        BIZAPPCENTER_LOGIN_URL = TestConstants.Url;
    }

    private static String getSpecificProperty(String property) {
        return (null != System.getProperty(property)) ? System.getProperty(property) :
                BIZAPPCENTERTEST_PROPERTIES.getProperty(property);
    }

    public static WebDriver getDriver() {
        System.out.println("Inside getdriver function");
        String runBrowser = BROWSER;
        logger.info("current browser initialized");
        WebDriver driver = null;
        DesiredCapabilities capabilities = null;
        ChromeOptions options = null;
        logger.info("going to check browser");
        System.out.println("going to check browser");
        if (FIREFOX.equalsIgnoreCase(runBrowser)) {
            logger.info("browser=firefox");
            System.out.println("browser=firefox");
            FirefoxProfile profile = new FirefoxProfile();
            System.out.println("initializing properties");
            profile.setPreference("browser.download.folderList", TestConstants.INT_2);
            profile.setPreference("browser.download.manager.showWhenStarting", false);
            profile.setAssumeUntrustedCertificateIssuer(false);
            profile.setPreference("browser.download.dir", System.getProperty("user.dir"));
            profile.setPreference("plugin.state.npbizappcenterdetector", 2);
            System.out.println("properties initialized");

            driver = new FirefoxDriver(profile);

            System.out.println("exiting firefox block");
        } else if (CHROME.equalsIgnoreCase(runBrowser)) {
            HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
            try {
                chromePrefs.put("profile.default_content_settings.popups", TestConstants.INT_0);
                logger.info(System.getProperty("user.dir") + " System working dir ");
                chromePrefs.put("download.default_directory", System.getProperty("user.dir"));
                chromePrefs.put("plugin.state.npbizappcenterdetector", 2);
                capabilities = DesiredCapabilities.chrome();
                options = new ChromeOptions();
            } catch (Exception e) {
                logger.error("SeleniumCore ChromeOption Exception : " + e);
            }
            options.setExperimentalOption("prefs", chromePrefs);
            options.addArguments(Arrays.asList("--test-type", "--start-maximized"));
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            try {
                driver = new RemoteWebDriver(new URL(TestConstants.REMOTEWEBDRIVER_URL + ":" + TestConstants.CHROME_PORT), capabilities);
                System.out.println("driver initialized");
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                logger.info("Malform URL exception :" + ex);
            }
        } else if (IE.equalsIgnoreCase(runBrowser)) {
            capabilities = DesiredCapabilities.internetExplorer();
            capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
            capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
            capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
            capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
            capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, false);
            try {
                driver = new RemoteWebDriver(new URL(TestConstants.REMOTEWEBDRIVER_URL + ":" + TestConstants.IE_PORT),
                        capabilities);
            } catch (Exception ex) {
                logger.error("" + ex);
            }
        } else if (SAFARI.equalsIgnoreCase(runBrowser)) {
            if (SeleniumUtil.isWindows() || SeleniumUtil.isMac()) {
                driver = new SafariDriver();
            } else {
                driver = new FirefoxDriver();
            }
        } else {
            driver = new HtmlUnitDriver();
        }
        driver.manage().window().maximize();
        System.out.println("returning driver");
        return driver;

    }
}
