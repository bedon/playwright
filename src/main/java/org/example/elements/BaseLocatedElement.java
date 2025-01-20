package org.example.elements;

import com.microsoft.playwright.Locator;
import org.example.annotations.FindBy;
import org.example.interfaces.LocatorWrapper;

@FindBy(selector = "//*[@class='secondary-menu container-fluid']")
public class BaseLocatedElement implements LocatorWrapper {
    private Locator locator;

    @Override
    public Locator getWrappedLocator() {
        return locator;
    }
}
