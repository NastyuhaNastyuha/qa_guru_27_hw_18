package tests;

import com.codeborne.selenide.Configuration;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 10000;
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }
}
