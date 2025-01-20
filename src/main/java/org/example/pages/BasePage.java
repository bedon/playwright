package org.example.pages;

import com.microsoft.playwright.Page;
import io.qameta.allure.testng.AllureTestNg;
import org.example.BaseTest;
import org.example.annotations.FindBy;
import org.example.utils.PageFactory;
import org.testng.annotations.Listeners;

import java.lang.reflect.Field;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
public abstract class BasePage {
    private String url = "https://jam.ua/";
    protected Page page;
    protected String mca = "";

    public BasePage(Page page) {
        initPage(page);
        initLocators();
    }

    public void initLocators() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(FindBy.class)) {
                FindBy annotation = field.getAnnotation(FindBy.class);
                String selector = annotation.selector();
                try {
                    field.setAccessible(true);
                    field.set(this, page.locator(selector));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Не удалось инициализировать локатор: " + field.getName(), e);
                }
            }
        }
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
        return (T) this;
    }

    public String getUrl() {
        return page.url();
    }
}
