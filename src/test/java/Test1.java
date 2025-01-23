import org.example.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Test1 extends BaseTest {

    @Test
    public void qwe11() throws InterruptedException {
        mainPage.openGuitarsPage();
        Thread.sleep(1);
    }

    @Test
    public void qwe112() throws InterruptedException {
        Assert.assertTrue(false);
    }
}
