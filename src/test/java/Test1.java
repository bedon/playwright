import org.example.BaseTest;
import org.example.pages.GuitarsPage;
import org.example.pages.MainPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Test1 extends BaseTest {

    @Test
    public void qwe11() throws InterruptedException {
        mainPage.openContacts();
        Thread.sleep(1);
    }

    @Test
    public void qwe112() throws InterruptedException {
        Assert.assertTrue(false);
    }
}
