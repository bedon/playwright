package org.example;

import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.qameta.allure.testng.AllureTestNg;
import org.example.pages.MainPage;
import org.example.utils.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.ByteArrayInputStream;
import java.util.Arrays;

public class BaseTest {
    private Playwright playwright;
    private Browser browser;
    public BrowserContext browserContext;
    public Page page;
    public MainPage mainPage;

    @BeforeSuite
    public void init() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(Arrays.asList("--start-maximized")));
    }

    @BeforeMethod
    public void initPage() {
        browserContext = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = browserContext.newPage();
        page.navigate("https://jam.ua/ua/");
        mainPage = PageFactory.createInstance(page, MainPage.class).open();
    }


    @AfterMethod
    public void closePage(ITestResult testResult) {
        if (!testResult.isSuccess()) {
            makeScreenshot(testResult.getName());
        }
        page.close();
        browserContext.close();
    }

    @Step("qweqweqwe")
    @Attachment(value = "{name}_screenshot", type = "image/png")
    public byte[] makeScreenshot(String name) {
        return page.screenshot();
    }

    @AfterSuite
    public void closeBrowser() {
        browser.close();
        playwright.close();
    }

    public BrowserContext getContext() {
        return browserContext;
    }
}
