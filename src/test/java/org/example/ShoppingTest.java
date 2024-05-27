package org.example;

import com.codeborne.selenide.ClickOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.attributes;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ShoppingTest {

    @BeforeMethod
    public void setup() {
        open("https://react-shopping-cart-67954.firebaseapp.com/");
        getWebDriver().manage().window().maximize();
    }

    @Test
    public void addToCartTest() {
        $("button.jCsgpZ").click();

        $("div.hDmOrM img.jMMQhp").shouldHave(
                attribute("alt", $(byXpath("//div[contains(@class, \"csvtPz\")]")).attr("alt"))
        );
    }

    @Test
    public void addAllToCartTest() {
        $$("button.jCsgpZ").forEach((button) -> button.click(ClickOptions.usingJavaScript()));

        $$("div.hDmOrM img.jMMQhp").shouldHave(
                attributes(
                        "alt",
                        $$(byXpath("//div[contains(@class, \"csvtPz\")]")).attributes("alt")
                )
        );

    }

    @Test
    public void sizeFilter() {
        int initialProductCount = $$(byXpath("//div[contains(@class, \"csvtPz\")]")).size();

        $(".hcyKTa").click();
        waitForReloading();

        int filteredProductCount = $$(byXpath("//div[contains(@class, \"csvtPz\")]")).size();
        Assert.assertTrue(filteredProductCount < initialProductCount);
    }

    @Test
    public void sizeFilterParseNumber() {
        int initialProductCount = getCurrentProductsCount();

        $(".hcyKTa").click();
        waitForReloading();

        int filteredProductCount = getCurrentProductsCount();
        Assert.assertTrue(filteredProductCount < initialProductCount);
    }

    private int getCurrentProductsCount() {
        String countText = $(".iliWeY p").getText();
        return Integer.parseInt(countText.substring(0, countText.indexOf(" ")));
    }

    private void waitForReloading() {
        Wait().withTimeout(Duration.ofMillis(50))
                .until((t) -> $(".ruYEp").isDisplayed());
        Wait().withTimeout(Duration.ofMillis(50))
                .until((t) -> !$(".ruYEp").isDisplayed());
    }

    @AfterMethod
    public void after() {
        closeWindow();
    }
}
