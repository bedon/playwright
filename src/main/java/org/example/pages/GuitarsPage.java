package org.example.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.annotations.FindBy;
import org.example.elements.Header;

import java.util.List;

public class GuitarsPage extends BasePage{
    private Header header;
    @FindBy(selector = "//a[@class='main-menu__link']")
    private List<Locator> locators;
    public GuitarsPage(Page page) {
        super(page);
        setMca("guitars");
    }

    public GuitarsPage suckDick() {
        header.clickContactsLink();
        return this;
    }
}
