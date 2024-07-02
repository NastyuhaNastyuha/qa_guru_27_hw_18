package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import models.LoginResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class BookStoreTests extends TestBase {

    @Test
    @Tag("bookStore")
    @DisplayName("Проверка удаления книги из профиля пользователя")
    void deleteBookFromProfileBooksListTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        BookStoreSteps step = new BookStoreSteps();
        String userName = "nastyap";
        String userPassword = "Password1!";
        String isbn = "9781449365035";

        step.registerUserApi(userName, userPassword);
        step.generateTokenApi(userName, userPassword);
        LoginResponseModel loginResponse = step.loginUserApi(userName, userPassword);
        step.addBookToProfileApi(loginResponse, isbn);
        step.setAuthCookie(loginResponse);
        step.deleteBookFromProfileUI();
    }
}
