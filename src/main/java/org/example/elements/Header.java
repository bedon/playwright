package org.example.elements;

import com.microsoft.playwright.*;
import io.qameta.allure.Step;
import org.example.annotations.FindBy;

@FindBy(selector = "//*[@class='secondary-menu container-fluid']")
public class Header extends BaseLocatedElement{
    @FindBy(selector = "//*[text()='Контакти']")
    private Locator contactsLink;

    public Header(Page page, String selector) {
        super(page, selector);
    }


    @Step("Click 'Контакти' link")
    public void clickContactsLink() {
        locator.locator(contactsLink).click();
    }
}
