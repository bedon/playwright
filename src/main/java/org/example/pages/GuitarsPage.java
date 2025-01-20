package org.example.pages;

import com.microsoft.playwright.Page;

public class GuitarsPage extends BasePage{
    public GuitarsPage(Page page) {
        super(page);
        setMca("guitars");
    }

    public GuitarsPage suckDick() {
        return this;
    }
}
