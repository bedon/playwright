import org.example.BaseTest;
import org.example.pages.GuitarsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test1 extends BaseTest {

    @Test
    public void qwe11() throws InterruptedException {
        mainPage.openGuitarsPage().suckDick();
        mainPage.open();
        Thread.sleep(1);
    }
}
