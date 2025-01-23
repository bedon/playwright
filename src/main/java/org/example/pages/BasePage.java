package org.example.pages;

import com.microsoft.playwright.Page;
import org.example.interfaces.LocatorInitializer;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class BasePage implements LocatorInitializer {
    private String url = "https://jam.ua/ua/";
    protected Page page;
    protected String mca = "";

    public BasePage(Page page) {
        initPage(page);
        initializeLocators(this.getClass(), page);
    }

    public void initPage(Page page) {
        this.page = page;
        setMca(mca);
    }

    public void setMca(String mca) {
        this.mca = mca;
    }

    public <T extends BasePage> T validate() {
        assertThat(page).hasURL(url + mca);
        return (T) this;
    }

    public <T extends BasePage> T open() {
        page.navigate(url + mca);
        validate();
        return (T) this;
    }
}
