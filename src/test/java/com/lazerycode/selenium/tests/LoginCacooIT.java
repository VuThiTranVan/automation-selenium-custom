package com.lazerycode.selenium.tests;

import com.lazerycode.selenium.DriverBase;
import com.lazerycode.selenium.page_objects.LoginPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class LoginCacooIT extends DriverBase {
    private static final Logger LOGGER = Logger.getLogger(LoginCacooIT.class);

    private WebDriver driver;

    @BeforeMethod
    public void driver() throws  Exception {

        driver = getDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://cacoo.com/signin");
    }


    @DataProvider(name = "LoginDataProviderSuccess")
    public Iterator<Object[]> loginDataProviderSuccess() {
        Collection<Object[]> dp = new ArrayList<Object[]>();
        dp.add(new String[]{"abvn@sun-asterisk.com", "fgvbfdgb", "", "true"});
        //dp.add(new String[]{"", "", "Warning: No match for E-Mail Address and/or Password.", "false"});
//        dp.add(new String[]{"SomeUser", "somePassword", " Warning: No match for E-Mail Address and/or Password."});
        return dp.iterator();
    }

    @Test(dataProvider = "LoginDataProviderSuccess")
    public void loginTest(String emailAddres, String password, String generalError, String resultLogin) {
        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.login(emailAddres, password);

        if(resultLogin.equals("false")) {
            Assert.assertEquals(loginPage.getGeneralError(),
                    generalError,
                    "Invalid email or password");
        }

        else if(resultLogin.equals("true")){
            Assert.assertTrue(loginPage.getEmailInput().isDisplayed());

            sleepOfThread(2800);
            implicitlyWaitOfWait(driver, 100, TimeUnit.SECONDS);

            loginPage.myAccountLogout();

            new Actions(driver).moveToElement(loginPage.getLogout()).perform();
            Assert.assertTrue(loginPage.getLogout().isDisplayed());
            implicitlyWaitOfWait(driver, 10, TimeUnit.SECONDS);
        }
        LOGGER.info(">============= END TEST");
    }
/*

    @DataProvider(name = "loginDataProviderFail")
    public Iterator<Object[]> loginDataProviderFail() {
        Collection<Object[]> dp = new ArrayList<Object[]>();
        dp.add(new String[]{"vu.thi.tran.van@sun-asterisk.com", "dsvgfsdb", "Invalid email or password", "false"});
        return dp.iterator();
    }

    @Test(dataProvider = "loginDataProviderFail")
    public void loginTestFail(String emailAddres, String password, String generalError, String resultLogin) {

        LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
        loginPage.login(emailAddres, password);

        if(resultLogin.equals("false")) {
            Assert.assertEquals(loginPage.getGeneralError(),
                    generalError,
                    "Invalid email or password");
        }
    }
*/

}
