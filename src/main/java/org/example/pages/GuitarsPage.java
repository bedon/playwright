package org.example.pages;

import com.microsoft.playwright.Page;
import org.example.elements.Header;

public class GuitarsPage extends BasePage{
    private Header header;
    public GuitarsPage(Page page) {
        super(page);
        setMca("guitars");
    }

    public GuitarsPage suckDick() {
        return this;
    }
}
