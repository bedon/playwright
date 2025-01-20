import org.example.BaseTest;
import org.example.pages.GuitarsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Test1 extends BaseTest {

    @Test
    public void qwe11() throws InterruptedException {
        GuitarsPage guitarsPage = mainPage.openGuitarsPage();
        guitarsPage.suckDick();
        guitarsPage.validate();
    }

    @Test
    public void qwe112() throws InterruptedException {
        Assert.assertTrue(false);
    }
}
