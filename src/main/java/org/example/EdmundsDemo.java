package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class EdmundsDemo {


    @Test
    public static void main(String[] args) throws Exception {

        WebDriver driver = null;
        try {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
            driver.get("https://www.edmunds.com/");
            driver.findElement(By.linkText("Shop Used")).click();

            Thread.sleep(500);
            // Clear the zipcode field and enter 22031
            driver.findElement(By.name("zip")).sendKeys(Keys.CONTROL, "a");
            driver.findElement(By.name("zip")).sendKeys("22031", Keys.ENTER);//id="search-radius-range-min"
            Thread.sleep(1000);
            driver.findElement(By.xpath("//span[@title='Only show local listings']")).click();
            // Check the Tesla checkbox
            Thread.sleep(500);
            Select makeDropdown = new Select(driver.findElement(By.id("usurp-make-select")));
            makeDropdown.selectByVisibleText("Tesla");

            // Step 6: Verify default options
            Thread.sleep(500);
            WebElement modelDropdown = driver.findElement(By.name("model"));
            WebElement yearMinField = driver.findElement(By.id("min-value-input-Year"));
            driver.findElement(By.id("max-value-input-Year")).sendKeys(Keys.CONTROL, "a");
            driver.findElement(By.id("max-value-input-Year")).sendKeys("2023", Keys.ENTER);
            String[] expectedModelOptions = {"Any Model", "Model 3", "Model S", "Model X", "Model Y", "Cybertruck", "Roadster"};
            System.out.println(driver.findElement(By.name("model")).getText());
            Select makeDropdown1 = new Select(driver.findElement(By.id("usurp-make-select")));

            driver.findElement(By.id("usurp-model-select")).sendKeys("Model 3", Keys.ENTER);

            // Choose Model 3 and enter 2020 for year min field
            Thread.sleep(2000);
            driver.findElement(By.id("min-value-input-Year")).sendKeys(Keys.CONTROL, "a");
            driver.findElement(By.id("min-value-input-Year")).sendKeys("2020", Keys.ENTER);
            Thread.sleep(500);
            List<WebElement> resultList = driver.findElements(By.xpath("//li[@class='d-flex mb-0_75 mb-md-1_5 col-12 col-md-6']"));
            Assert.assertEquals(resultList.size(), 21);
            Thread.sleep(500);
            List<WebElement> searchResults = driver.findElements(By.xpath("//div[contains(text(),'Tesla Model 3')]"));
            List<String> searchResultsString = new ArrayList<>();

            searchResults.forEach(s -> searchResultsString.add(s.getText()));
            searchResultsString.forEach(s -> Assert.assertTrue(s.contains("Tesla Model 3")));
            Thread.sleep(600);
            List<Integer> year = new ArrayList<>();
            searchResultsString.forEach(s -> year.add(Integer.valueOf(s.substring(0, 4))));

            for (Integer i : year) {
                Assert.assertTrue(i >= 2020 && i <= 2023);
            }
            Thread.sleep(600);

            List<WebElement> searchResults1 = driver.findElements(By.id("summury"));
            WebElement lastResult = searchResults.get(searchResults.size() - 1);

            String title = lastResult.findElement(By.xpath("(//div[@class='size-16 text-cool-gray-10 font-weight-bold mb-0_5'])[" + searchResults.size() + "]")).getText();

            String price = lastResult.findElement(By.xpath("(//span[@class='heading-3'])[" + searchResults.size() + "]")).getText();

            String miles = lastResult.findElement(By.xpath("(//div[@class='text-gray-darker row']//span[@class='text-cool-gray-30'][contains(text(), 'miles')])[" + searchResults.size() + "]")).getText().replace(" miles", "");
            Thread.sleep(500);
            //lastResult.click();
           // Thread.sleep(500);
            String titleNew = driver.findElement(By.xpath("//h1[@class='d-inline-block mb-0 heading-2 mt-0_25']")).getText();
            Thread.sleep(600);
            String priceNew = driver.findElement(By.xpath("//span[@data-testid='vdp-price-row']")).getText();
            String milesNew = driver.findElement(By.xpath("//div[@class='pr-0 font-weight-bold text-right ml-1 col'][1]")).getText();
            Thread.sleep(600);
            Assert.assertEquals(titleNew, title);
            Assert.assertEquals(priceNew, price);
            Assert.assertEquals(milesNew, miles);

            driver.navigate().back();

            String view = driver.findElement(By.xpath("//div[@class='bg-white text-gray-darker']")).getText();
            Assert.assertTrue(view.contains("Viewed"));
        } finally {


            driver.quit();
        }

    }
}

            //
