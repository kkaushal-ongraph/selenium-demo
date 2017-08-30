package com.test.util;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vandana Agarwal on 3/29/2016.
 */
public class SeleniumUtil {
    protected static WebDriver browser;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static Logger logger = LoggerFactory.getLogger(SeleniumUtil.class);
    protected String bizappcenterTest_properties_Assert_Values_file = TestConstants.NULL;
    protected String bizappcenterTest_properties_HTML_ID_file = TestConstants.NULL;
    private Properties bizappcenterTest_properties_assert_values = null;
    private Properties bizappcenterTest_properties_html_id = null;

    public static boolean isWindows() {
        return (OS.indexOf("win") >= TestConstants.INT_0);
    }

    public static boolean isMac() {
        return (OS.indexOf("mac") >= TestConstants.INT_0);
    }

    public static void browser_wait(Long waitingTime) {
        try {
            Thread.sleep(waitingTime);
        } catch (InterruptedException ie) {
            logger.error("Browser_wait.", ie.getMessage());
        }
    }

    public void bizappcenterTest_suite_setup() throws MalformedURLException,
            IOException, InterruptedException {
        System.out.println("inside bizappcenterTest_suite_setup");
        bizappcenterTest_suite_properties_initialize();
    }

    private void bizappcenterTest_suite_properties_initialize() throws MalformedURLException,
            IOException, InterruptedException {

        System.out.println("inside bizappcenterTest_suite_properties_initialize\ngoing to call getdriver");
        browser = SeleniumCore.getDriver();
        logger.info("Loading Driver for the browser");
        logger.info("Fetching the UUID of the Device ");
        String target = SeleniumCore.BIZAPPCENTER_LOGIN_URL;
        System.out.println("Opening URL = " + target);
        browser.get(target);
        try {
            bizappcenterTest_properties_assert_values = new Properties();
            bizappcenterTest_properties_html_id = new Properties();

            bizappcenterTest_properties_Assert_set(bizappcenterTest_properties_Assert_Values_file);
            bizappcenterTest_properties_HTML_ID_set(bizappcenterTest_properties_HTML_ID_file);
        } catch (Exception e) {
            logger.debug("SeleniumUtil set Assert & HTML_ID Exception : ", e.getCause());
        }

    }

    public void bizappcenterTest_properties_Assert_set(String fileName) throws IOException {
        if (fileName != null) {
            if (bizappcenterTest_properties_assert_values == null) {
                bizappcenterTest_properties_assert_values = new Properties();
            }
            InputStream instream = SeleniumUtil.class.getClassLoader().getResourceAsStream(
                    TestConstants.PROPERTIES + File.separator + fileName);
            bizappcenterTest_properties_assert_values.load(instream);
        }
    }

    public void bizappcenterTest_properties_HTML_ID_set(String fileName)
            throws IOException {
        if (fileName != null) {
            if (bizappcenterTest_properties_html_id == null) {
                bizappcenterTest_properties_html_id = new Properties();
            }
            InputStream instream = SeleniumUtil.class.getClassLoader()
                    .getResourceAsStream(TestConstants.PROPERTIES + File.separator + fileName);
            bizappcenterTest_properties_html_id.load(instream);
        }
    }

    public WebElement doc_get(String xpath, WebDriver browser) {

        WebElement elementPath = null;
        try {
            elementPath = browser.findElement(By
                    .xpath(bizappcenterTest_properties_HTML_ID_get(xpath)));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return elementPath;
    }

    public String bizappcenterTest_properties_HTML_ID_get(String propId) {
        String value = null;
        if (bizappcenterTest_properties_html_id.size() > TestConstants.INT_0) {
            value = bizappcenterTest_properties_html_id.getProperty(propId);
        }
        return value;
    }

    public String bizappcenterTest_properties_assert_values_get(String key) {
        String value = TestConstants.NULL;
        if (bizappcenterTest_properties_assert_values.size() > TestConstants.INT_0) {
            value = bizappcenterTest_properties_assert_values.getProperty(key);
        }
        return value;
    }

    public void bizapp_login(WebDriver browser) {
        browser_wait(TestConstants.LONG_3000);
        WebElement bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_2000);
        bizapp_popup_button.click();
        WebElement bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        browser_wait(TestConstants.LONG_1000);

        WebElement bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        browser_wait(TestConstants.LONG_1000);

        WebElement bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button Textfield is missing or not displayed");
        browser_wait(TestConstants.LONG_1000);

        bizapp_username.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Username entered");
        bizapp_password.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_login_password_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Password entered");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
    }

    /**
     * This method return the list of web-elements of given xpath
     */
    public List<WebElement> doc_list_get(String xpath, WebDriver browser) {
        List<WebElement> listElementPath = null;
        try {
            listElementPath = browser.findElements(By
                    .xpath(bizappcenterTest_properties_HTML_ID_get(xpath)));

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return listElementPath;
    }


    public void bizapp_logout(WebDriver browser) {
        try {
            WebElement bizzappcenter_logout_button = doc_get("bizapp_logout_xpath", browser);
            browser_wait(TestConstants.LONG_1000);
            Assert.assertFalse((bizzappcenter_logout_button == null),"logout button is not found");
            bizzappcenter_logout_button.click();
            browser_wait(TestConstants.LONG_7000);
            logger.info("bizappLogout");
        }
        catch (Exception exception) {
            logger.error("" + exception);
        }
    }
}
