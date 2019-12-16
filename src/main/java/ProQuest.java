import com.google.common.base.Strings;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProQuest {

    WebDriver driver;

    @Test
    public void Test1() throws IOException {
        System.setProperty("webdriver.chrome.driver", "src/main/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.navigate().to("https://www.google.com/");
        WebElement searchBox = driver.findElement(By.xpath("//input[@name='q']"));
        searchBox.sendKeys("ProQuest");
        searchBox.sendKeys(Keys.RETURN);
        List<WebElement> titles = driver.findElements(By.xpath("//a/h3"));

        File newFile = new File("output.txt");
        newFile.createNewFile();
        FileWriter writeFile = new FileWriter(newFile);


        for (WebElement result:titles)
        {
            if(!Strings.isNullOrEmpty(result.getText()))

            {
                System.out.println("Title :" + result.getText());
                writeFile.write("Title :" + result.getText());
            }
        }

        writeFile.close();

    }

    @Test

    public void Test2() throws IOException {
        Test1();
        WebElement proQuest = driver.findElement(By.xpath("//h3[contains(text(),'EBooks and Technology')]"));
        proQuest.click();
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//i[@class=\" fa  fa-2  fa-search \"]"))).click();
        WebElement search = driver.findElement(By.xpath("//li//input[@name='searchKeyword']"));
        search.sendKeys("QA");
        search.sendKeys(Keys.RETURN);
        TakesScreenshot screenshot = ((TakesScreenshot)driver);
        File  screenshotFile = screenshot.getScreenshotAs(OutputType.FILE);
    }

    @AfterTest
    public void CloseBrowser()
    {
        driver.quit();
    }

}
