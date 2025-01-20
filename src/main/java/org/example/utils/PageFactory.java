package org.example.utils;

import com.microsoft.playwright.Page;
import org.example.pages.BasePage;

public class PageFactory {

    public static <T extends BasePage> T createInstance(Page page, Class<T> clazz) {
        try {
            BasePage instance = clazz.getDeclaredConstructor(Page.class).newInstance(page);
            return clazz.cast(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new NullPointerException("Page class instantiation failed.");
    }
}
