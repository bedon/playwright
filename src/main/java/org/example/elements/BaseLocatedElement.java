package org.example.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.interfaces.LocatorInitializer;
import org.example.interfaces.Locatable;

public abstract class BaseLocatedElement implements Locatable, LocatorInitializer {
    protected Locator locator;
    protected String selector;

    public BaseLocatedElement(Page page, String selector) {
        this.locator = page.locator(selector);
        this.selector = selector;
        initializeLocators(this.getClass(), page);
    }

    @Override
    public Locator getWrappedLocator() {
        return locator;
    }
}
