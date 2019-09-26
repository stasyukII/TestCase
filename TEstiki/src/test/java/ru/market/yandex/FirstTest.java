package ru.market.yandex;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.matchers.JUnitMatchers.containsString;

public class FirstTest {
    public ChromeDriver driver;
    final String OT = "50";
    final String DO = "150";
    final String WHISKAS = "Whiskas";
    final String MNYAMS = "Мнямс";
    final int EXPECTED_SUM = 300;
    public void chooseCatFood (String company) throws Exception{
        // пишем цену от
        driver.findElement(By.cssSelector("#glpricefrom")).sendKeys(OT);
        // пишем цену до
        driver.findElement(By.cssSelector("#glpriceto")).sendKeys(DO);
        // с доставкой
        driver.findElement(By.cssSelector("li._3YKtoPKZka:nth-child(1) > div:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).click();
        driver.findElement(By.cssSelector("div._3_phr-spJh:nth-child(3) > div:nth-child(1) > div:nth-child(1) > fieldset:nth-child(1) > footer:nth-child(3) > button:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#\\37 893318-suggester")).sendKeys(company);
        Thread.sleep(2000);
    }
    public void millionStepsOfCSS()throws Exception{
        driver.get("https://market.yandex.ru/");
        driver.findElement(By.className("n-w-tab__control-hamburger")).click();

        // Выбираем товары для животных
        driver.findElement(By.cssSelector("div.n-w-tab_interaction_hover-navigation-menu:nth-child(11) > a:nth-child(1) > span:nth-child(2)")).click();

        //Выбираем лакомства
        driver.findElement(By.cssSelector("div._1YdrMWBuYy:nth-child(1) > div:nth-child(2) > ul:nth-child(1) > li:nth-child(3) > div:nth-child(1) > a:nth-child(1)")).click();

        chooseCatFood(WHISKAS);

        driver.findElement(By.cssSelector("._17C4Le-0TB")).click();

        driver.findElement(By.cssSelector("#product-158327379 > div.n-snippet-card2__part.n-snippet-card2__part_type_left > a")).click();

        driver.findElement(By.cssSelector("body > div.main > div.n-product-summary.b-zone.i-bem.n-product-summary_js_inited.b-zone_js_inited > div.n-product-summary__header > div > div > div > div.n-product-title-features__toolbar > div > div")).click();
        driver.navigate().back();

        driver.findElement(By.cssSelector("div._3_phr-spJh:nth-child(3) > div:nth-child(1) > div:nth-child(1) > fieldset:nth-child(1) > ul:nth-child(2) > li:nth-child(1) > div:nth-child(1) > a:nth-child(1) > label:nth-child(1) > div:nth-child(2)")).click();

        driver.findElement(By.cssSelector("div._3_phr-spJh:nth-child(3) > div:nth-child(1) > div:nth-child(1) > fieldset:nth-child(1) > footer:nth-child(3) > button:nth-child(1)")).click();

        driver.findElement(By.cssSelector("#\\37 893318-suggester")).sendKeys(MNYAMS);
        Thread.sleep(500);
        driver.findElement(By.cssSelector("._2RDCAZB4Gk > label:nth-child(1) > div:nth-child(2)")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("#product-250197858 > div.n-snippet-card2__part.n-snippet-card2__part_type_center > div.n-snippet-card2__header > div.n-snippet-card2__title")).click();

        driver.findElement(By.cssSelector("body > div.main > div.n-product-summary.b-zone.i-bem.n-product-summary_js_inited.b-zone_js_inited > div.n-product-summary__header > div > div > div > div.n-product-title-features__toolbar > div > div")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector("body > div.popup-informer.i-bem.popup-informer_js_inited > div > div > div.popup-informer__controls > a")).click();
    }
    @Before
    public void initialize(){
        System.setProperty("webdriver.chrome.driver", "/adb/driver/chromedriver.exe");
        driver = new ChromeDriver();
        System.out.println("Test start");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }
    @Test
    @ru.yandex.qatools.allure.annotations.Description(value = "Добавляем продукты к сравнению")
    public void openAndAddToCompare() throws Exception{
        //bigOOF

        millionStepsOfCSS();

        //Сравниваем цены

        compareFood();

        //Ищем вискас

        checkForWhiskas();
        Actions action = new Actions(driver);
        WebElement we = driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[2]/div[4]/div/div/div/div[2]"));
        action.moveToElement(we).moveToElement(driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[2]/div[4]/div/div/div/div[2]/div[2]/span"))).click().build().perform();
        checkForWhiskas();
        //конец
        driver.findElement(By.xpath("/html/body/div[1]/div[5]/div[1]/div[2]/div[2]/span")).click();

    }
    @Step
    public static void checkSumStep(int num1, int num2, int expectedSum) {
        Assert.assertTrue("Сумма слагаемых не соответствует ожидаемому значению", num1 + num2 <= expectedSum);
    }
    @Description(value = "Сравниваем цены")
    public void compareFood(){
        List<Integer> prices = new ArrayList<Integer>();
        List<WebElement> webElements = new ArrayList<WebElement>(2)  ;
            webElements.addAll(driver.findElementsByClassName("price"));
            for(WebElement element : webElements){
                prices.add(Integer.parseInt(element.getText().replaceAll("[^0-9?!\\.]","")));
            }
         int num1 = prices.get(0);
         int num2 = prices.get(1);
        System.out.println(num1+num2);
         checkSumStep(num1,num2,EXPECTED_SUM);
    }
    @Description(value = "Есть ли вискас?")
    public  void checkForWhiskas(){
        String pageText = driver.findElement(By.tagName("body")).getText();
        if (pageText.toLowerCase().contains("whiskas"))
            System.out.println("Вискас есть");
        else System.out.println("Вискаса нет");
    }
    @After
    public void closeTab(){
        driver.quit();
        System.out.println("Test done");
    }
}
