package com.test.demoTest;

import com.test.util.SeleniumUtil;
import com.test.util.TestConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Vandana Agarwal on 3/29/2016.
 */
public class AdminDemoTest extends SeleniumUtil {

    private static Logger logger = LoggerFactory.getLogger(AdminDemoTest.class);
    private final String bizappDemoTest_PROPERTIES_HTML_ID_FILENAME = "DemoTest_HTML_ID.properties";
    private final String bizappDemoTest_PROPERTIES_ASSERT_VALUES_FILENAME = "DemoTest_Assert_Values.properties";
    WebElement bizapp_user_tab;
    private List<WebElement> Applist;
    private List<WebElement> GroupList;
    private List<WebElement> ManagedAppList;

    @BeforeMethod
    protected void bizappcenterTest_suite_pre_function() throws Exception {
        try {
            System.out.println("Inside before method");
            logger.debug("Preparing Test Suite");
            bizappcenterTest_properties_Assert_Values_file = bizappDemoTest_PROPERTIES_ASSERT_VALUES_FILENAME;
            bizappcenterTest_properties_HTML_ID_file = bizappDemoTest_PROPERTIES_HTML_ID_FILENAME;
            System.out.println("going to call bizappcenterTest_suite_setup");
            bizappcenterTest_suite_setup();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }


    @Test(priority = TestConstants.INT_2)
    public void loginAsAdmin() throws Exception {
        browser_wait(TestConstants.LONG_3000);
        WebElement bizapp_popup_button=doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_2000);
        bizapp_popup_button.click();
        System.out.println("TEST: AS-001 Login As Admin");
        logger.info("TEST : AS-001 Login As Admin");

        /*
        Checking for the cases that all the required fields are dispalyed.
         */

        System.out.println("Checking for elements");
        WebElement bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        browser_wait(TestConstants.LONG_1000);
        WebElement bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        browser_wait(TestConstants.LONG_1000);

        WebElement bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        browser_wait(TestConstants.LONG_1000);

        WebElement bizapp_error_div;
        List<WebElement> subElements;
        Iterator<WebElement> subElements_itrate;
        String error_message = "";
        boolean flag = false;


        /*
        Checking the case where username and password were sent as null to login.
         */

        logger.info("Checking 1st case");
        System.out.println("Checking 1st case");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        Assert.assertTrue(bizapp_error_div.isDisplayed(), "Assert Fails: Error Div is not displayed");
        subElements = bizapp_error_div.findElement(By.tagName("ul")).findElements(By.tagName("li"));
        System.out.println("Lenght of List " + subElements.size());
        subElements_itrate = subElements.iterator();
        while (subElements_itrate.hasNext()) {
            error_message = error_message + subElements_itrate.next().getText().trim();
        }
        System.out.println("Error message= " + error_message);
        Assert.assertTrue(error_message.equals(
                bizappcenterTest_properties_assert_values_get("bizappcenter_null_login_error_message")), "Wrong Error Message is dispalyed");
        logger.info("Sending null to username and password. Error message is displayed");
        browser_wait(TestConstants.LONG_1000);


        /*
        Checking the case where password was sent as null to login
         */

        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_1000);
        bizapp_popup_button.click();
        logger.info("Checking 2nd case");
        System.out.println("Checking 2nd case");
        error_message = "";
        bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        System.out.println("entering username");
        logger.info("entering username");
        bizapp_username.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Incorrect username entered");
        System.out.println("Incorrect username entered");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        Assert.assertTrue(bizapp_error_div.isDisplayed(), "Assert Fails: Error Div is not displayed");
        subElements = bizapp_error_div.findElement(By.tagName("ul")).findElements(By.tagName("li"));
        System.out.println("Lenght of List " + subElements.size());
        subElements_itrate = subElements.iterator();
        while (subElements_itrate.hasNext()) {
            error_message = error_message + subElements_itrate.next().getText().trim();
            if (error_message.equals(bizappcenterTest_properties_assert_values_get("bizappcenter_null_password_error_message"))) {
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag == true, "Wrong Error Message is dispalyed");
        logger.info("Sending incorrect username and null to password. Error message is displayed");
        browser_wait(TestConstants.LONG_1000);


        /*
        Checking the case where username was sent as null to login
         */

        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_1000);
        bizapp_popup_button.click();
        logger.info("Checking 3rd case");
        System.out.println("Checking 3rd case");
        error_message = "";
        bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        bizapp_username.clear();
        System.out.println("entering password");
        logger.info("entering password");
        bizapp_password.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_password_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Password entered");
        System.out.println("Password entered");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        Assert.assertTrue(bizapp_error_div.isDisplayed(), "Assert Fails: Error Div is not displayed");
        error_message = bizapp_error_div.getText();
        Assert.assertTrue(error_message.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_null_username_error_message")), "Wrong Error Message is displayed");
        logger.info("Sending null to username. Error message is displayed");
        browser_wait(TestConstants.LONG_1000);

        /*
        Checking the case where username and password were incorrect
         */

        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_1000);
        bizapp_popup_button.click();
        logger.info("Checking 4th case");
        System.out.println("Checking 4th case");
        error_message = "";
        bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        bizapp_username.clear();
        bizapp_password.clear();
        bizapp_username.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Incorrect username entered");
        bizapp_password.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_password_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Incorrect Password entered");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        Assert.assertTrue(bizapp_error_div.isDisplayed(), "Assert Fails: Error Div is not displayed");
        error_message = bizapp_error_div.getText();
        Assert.assertTrue(error_message.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_error_message")), "Wrong Error Message is displayed");
        logger.info("Sending incorrect username and password. Error message is displayed");
        browser_wait(TestConstants.LONG_1000);

         /*
        Checking the case where username is correct and password is incorrect
         */
        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_1000);
        bizapp_popup_button.click();
        logger.info("Checking 5th case");
        System.out.println("Checking 5th case");
        error_message = "";
        bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        bizapp_username.clear();
        bizapp_password.clear();
        bizapp_username.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Username entered");
        bizapp_password.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_password_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Incorrect Password entered");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        Assert.assertTrue(bizapp_error_div.isDisplayed(), "Assert Fails: Error Div is not displayed");
        error_message = bizapp_error_div.getText();
        Assert.assertTrue(error_message.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_error_message")), "Wrong Error Message is displayed");
        logger.info("Sending correct username and incorrect password. Error message is displayed");
        browser_wait(TestConstants.LONG_1000);


        /*
        Checking the case where username is incorrect and password is correct
         */
        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_1000);
        bizapp_popup_button.click();
        logger.info("Checking 6th case");
        System.out.println("Checking 6th case");
        error_message = "";
        bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        bizapp_username.clear();
        bizapp_password.clear();
        bizapp_username.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Incorrect Username entered");
        bizapp_password.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_login_password_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Password entered");
        bizapp_login_btn.click();
        browser_wait(TestConstants.LONG_2000);
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        Assert.assertTrue(bizapp_error_div.isDisplayed(), "Assert Fails: Error Div is not displayed");
        error_message = bizapp_error_div.getText();
        Assert.assertTrue(error_message.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_incorrect_login_error_message")), "Wrong Error Message is displayed");
        logger.info("Sending incorrect username and correct password. Error message is displayed");
        browser_wait(TestConstants.LONG_1000);


        /*
        Checking the case where username and password are correct
         */

        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_1000);
        bizapp_popup_button.click();
        logger.info("Checking 7th case");
        System.out.println("Checking 7th case");
        error_message = "";
        bizapp_username = doc_get("bizapp_login_username_xpath", browser);
        Assert.assertTrue(bizapp_username.isDisplayed(), "Assert Fails: Username Textfield is missing or not displayed");
        bizapp_password = doc_get("bizapp_password_xpath", browser);
        Assert.assertTrue(bizapp_password.isDisplayed(), "Assert Fails: Password Textfield is missing or not displayed");
        bizapp_login_btn = doc_get("bizapp_login_button_xpath", browser);
        Assert.assertTrue(bizapp_login_btn.isDisplayed(), "Assert Fails: Login Button is missing or not displayed");
        bizapp_username.clear();
        bizapp_password.clear();
        bizapp_username.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Username entered");
        bizapp_password.sendKeys(bizappcenterTest_properties_assert_values_get("bizappcenter_login_password_sendkeys"));
        browser_wait(TestConstants.LONG_2000);
        logger.info("Password entered");
        bizapp_login_btn.click();
        bizapp_error_div = doc_get("bizapp_error_div_xpath", browser);
        if (bizapp_error_div != null) {
            Assert.fail("Error div is displayed");
        }
        logger.info("Logging in as " + bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys"));
        browser_wait(TestConstants.LONG_3000);
        WebElement bizapp_user = doc_get("bizapp_username_xpath", browser);
        String user = bizapp_user.getText().trim();
        System.out.println("Logged in user= " + user);
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys").toUpperCase()), "Invalid User");
        browser_wait(TestConstants.LONG_1000);
        System.out.println("logging out");
        WebElement bizapp_logout = doc_get("bizapp_logout_xpath", browser);
        bizapp_logout.click();
        browser_wait(TestConstants.LONG_3000);
        System.out.println("logged out");
        browser_wait(TestConstants.LONG_3000);
        bizapp_popup_button = doc_get("bizapp_popup_close_button_xpath", browser);
        browser_wait(TestConstants.LONG_3000);
        bizapp_popup_button.click();
        browser_wait(TestConstants.LONG_2000);
        System.out.println("closing browser");
        browser.close();

    }

    @Test(priority = TestConstants.INT_0)
    public void mandatoryGroupStructureDemo() throws Exception {
        browser_wait(TestConstants.LONG_3000);
        System.out.println("TEST: AS-002 Mandatory Group Structure");
        logger.info("TEST : AS-002 Mandatory Group Structure");
        List<String> elements_list = new ArrayList<String>();
        List<String> sub_element_list = new ArrayList<String>();
        bizapp_login(browser);
        System.out.println("logged in...");
        browser_wait(TestConstants.LONG_3000);
        WebElement bizapp_user = doc_get("bizapp_username_xpath", browser);
        String user = bizapp_user.getText().trim().toLowerCase();
        System.out.println("user="+user);
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys")), "Invalid User");
        browser_wait(TestConstants.LONG_1000);
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_admin_user_role")), "Invalid Role");
        browser_wait(TestConstants.LONG_1000);

        logger.info("Logging in as " + bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys") + " as " +
                bizappcenterTest_properties_assert_values_get("bizappcenter_user_role"));
        browser_wait(TestConstants.LONG_2000);

        /*
        checking for the presence of switch
         */
        WebElement bizapp_switch_user = doc_get("bizapp_switch_user_xpath", browser);
        System.out.println("switch user=" + bizapp_switch_user);
        browser_wait(TestConstants.LONG_1000);
        Assert.assertTrue(bizapp_switch_user.isDisplayed(), "Assert Fails: Switch User is missing or not displayed");
        browser_wait(TestConstants.LONG_2000);

        /*
        Checking for the presence of environments
         */
        List<WebElement> mandatory_struct_div = doc_get("select_userOrGroup_xpath", browser).findElements(By.tagName(TestConstants.DIV_TAG));
        Iterator<WebElement> mandatory_struct_iterate = mandatory_struct_div.iterator();
        logger.info("Creating a List of all the Group Structure Available");
        while (mandatory_struct_iterate.hasNext()) {
            String list_elements = mandatory_struct_iterate.next().getText();
            logger.info("Adding \"" + list_elements + "\" to the List");
            elements_list.add(list_elements);
        }
        logger.info("Verifying that the List contains all the Mandatory Group");
        Boolean isAllEnvExist = elements_list.containsAll(Arrays.asList(TestConstants.BIZAPP_ENVIROMENT));
        Assert.assertTrue(isAllEnvExist, "The group hierachy is not containing all four env"
                + Arrays.toString(TestConstants.BIZAPP_ENVIROMENT)
                + " it's containing " + Arrays.toString(elements_list.toArray()));
        if (isAllEnvExist) {
            logger.info("All the required Groups exist in the list... Verfying for Unassigned User/Device group under PROD enviroment");
            List<WebElement> img_list=doc_get("select_userOrGroup_xpath", browser).findElements(By.tagName(TestConstants.IMG_TAG));
            Iterator<WebElement> img_list_iterate = img_list.iterator();
            while(img_list_iterate.hasNext()){
                WebElement image_tag=img_list_iterate.next();
                String list_element = image_tag.getAttribute("alt").toString();
                if(list_element.equals("prod")){
                    image_tag.click();
                    break;
                }
            }
            browser_wait(TestConstants.LONG_2000);
            List<WebElement> subElements = doc_get("sublist_xpath", browser).findElements(By.tagName(TestConstants.ANCHOR_TAG));
            System.out.println(subElements.size());
            Iterator<WebElement> subElements_itrate = subElements.iterator();
            while (subElements_itrate.hasNext()) {
                WebElement span_list = subElements_itrate.next();
                String sub_element = span_list.getAttribute("status").toString();
                if(sub_element.equals("prod")){
                    sub_element_list.add(span_list.getText());
                }
                browser_wait(TestConstants.LONG_1000);
            }

        }
        else {
            logger.error("List does not Contain Mandatory Group Sturcture");
            Assert.fail("All fields of mandatory group structure are not present");
        }
        if (sub_element_list.contains(TestConstants.UNASSIGNED_USER) && sub_element_list.contains(TestConstants.UNASSIGNED_DEVICES)) {
            logger.info("Unasssigned Group/Devices present under PROD Environment");
            logger.info("Contains all required fields");
        } else {
            logger.info("Cannot Find Unasssigned Group/Devices present under PROD Environment");
            Assert.fail("Not contains sub required fields");
        }


        /*
        switching user

        System.out.println("switching user");
        bizapp_switch_user.click();
        browser_wait(TestConstants.LONG_7000);
        bizapp_user = doc_get("bizapp_username_xpath", browser);
        System.out.println("user xpath= "+ bizapp_user);
        user = bizapp_user.getText().trim().toLowerCase();
        System.out.println("user= "+user);
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys")), "Invalid User");
        browser_wait(TestConstants.LONG_1000);
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_user_role")), "Invalid Role");
        browser_wait(TestConstants.LONG_3000);


        switching user again

        System.out.println("switching user again");
        bizapp_switch_user.click();
        browser_wait(TestConstants.LONG_7000);
        bizapp_user = doc_get("bizapp_username_xpath", browser);
        user = bizapp_user.getText().trim().toLowerCase();
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_login_username_sendkeys").toUpperCase()), "Invalid User");
        browser_wait(TestConstants.LONG_2000);
        Assert.assertTrue(user.contains(
                bizappcenterTest_properties_assert_values_get("bizappcenter_admin_user_role").toUpperCase()), "Invalid Role");
        browser_wait(TestConstants.LONG_3000);
        */


        /*
        logging out
         */
        System.out.println("logging out...");
        bizapp_logout(browser);
        browser_wait(TestConstants.LONG_7000);
        browser.close();

    }

}
