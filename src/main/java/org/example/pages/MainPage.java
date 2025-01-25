package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import org.example.annotations.FindBy;
import org.example.elements.Header;
import org.example.utils.PageFactory;

import java.util.List;

public class MainPage extends BasePage{
    @FindBy(selector = "//*[@href='/guitars' and @class='main-menu__link']")
    private Locator link;
    @FindBy(selector = "//div[@class='main-logo']")
    private Header header;
    @FindBy(selector = "//a[@class='main-menu__link']")
    private List<Locator> locators;

    public MainPage(Page page) {
        super(page);
    }

    @Step("Open guitars page")
    public GuitarsPage openGuitarsPage() {
        link.click();
        return PageFactory.createInstance(page, GuitarsPage.class);
    }

    public Locator getLink() {
        return link;
    }

    public void openContacts() {
        header.clickContactsLink();
    }

    public List<Locator> qwe() {
        return locators;
    }
}
