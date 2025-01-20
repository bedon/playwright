package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.annotations.FindBy;

public class MainPage extends BasePage{
    @FindBy(selector = "//*[@href='/guitars' and @class='main-menu__link']")
    private Locator link;

    public MainPage(Page page) {
        super(page);
    }

    @Step("Open guitars page")
    public GuitarsPage openGuitarsPage() {
        link.click();
        return new GuitarsPage(page);
    }

    public Locator getLink() {
        return link;
    }
}
