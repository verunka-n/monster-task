package monster.selenide;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class task {
    static Random rand;
    private WebDriver driver;
    private  String jobNumber1;
    private  String jobNumber2;
    @Test(groups = { "functest", "checkintest" })
    public void jobSearchSave() {
        setup();
        openPage();
        createAccount();
        openCompanyJobs();
        selectJobs();
        navigateToJobs();
        validateJobs();
    }

    private void setup() {
        String path = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", path + "\\packages\\chromedriver.exe");
        driver = new ChromeDriver();
        Configuration.browser="chrome";
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    private void openPage(){
        Selenide.open("https://www.monsterworksdemo.com/home/");
    }

    private void createAccount() {
        rand = new Random();
        $(byText("CREATE ACCOUNT")).click();
        $(By.id("c_elem_0")).setValue("test"+rand.nextInt()+"@test.com");
        $(By.id("a_elem_1")).setValue("T!l12345678XX");
        $(By.id("a_elem_2")).setValue("T!l12345678XX");
        $(By.id("elem_3")).selectOption(1);
        $(By.xpath("//*[@class=\"checkbox-label ng-binding\"]")).click();
        $(By.xpath("//button[contains(text(),'Create an account')]")).click();
    }

    private void openCompanyJobs(){
        $(By.xpath("//*[contains(text(),'Popular Companies')]//following::*/*[contains(text(),'Philips Jobs')]")).waitUntil(Condition.visible,90000).click();
    }

    private void selectJobs(){
        $(By.xpath("//*[@id='SearchResults']/section[2]")).waitUntil(Condition.visible,30000).click();
        jobNumber1 = $(By.xpath("//*[@id='SearchResults']/section[2]//a[1]")).waitUntil(Condition.visible,30000).text();
        $(By.xpath("//*[text()='Save']")).click();
        $(By.xpath("//*[@id='SearchResults']/section[last()]")).waitUntil(Condition.visible,30000).click();
        jobNumber2 = $(By.xpath("//*[@id='SearchResults']/section[last()]//a[1]")).waitUntil(Condition.visible,30000).text();
        $(By.xpath("//*[text()='Save']")).click();
    }

    private void navigateToJobs(){
        $(By.id("dropdown-My-job-search")).hover();
        $(By.xpath("//*[text()='Saved Jobs']")).click();
    }

    private void validateJobs(){
        $(By.xpath(getPath(jobNumber1))).should(Condition.exist);
        $(By.xpath(getPath(jobNumber2))).should(Condition.exist);
    }

    private String getPath(String jobNumber){
    String path = String.format("//*[text()='%s']",jobNumber);
        return path;
    }
}


