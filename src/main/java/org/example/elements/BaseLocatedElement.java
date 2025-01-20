package org.example.elements;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.example.interfaces.LocatorInitializer;
import org.example.interfaces.Locatable;

public abstract class BaseLocatedElement implements Locatable, LocatorInitializer {
    protected Locator locator;

    public BaseLocatedElement(Page page) {
        initializeLocators(this, page);
    }

    @Override
    public Locator getWrappedLocator() {
        return locator;
    }

    @Override
    public void initLocator(Locator locator) {
        this.locator = locator;
    }
}
