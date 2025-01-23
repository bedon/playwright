package org.example.interfaces;

import com.microsoft.playwright.Locator;

public interface Locatable {
    Locator getWrappedLocator();
}
