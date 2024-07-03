package tests;

import io.qameta.allure.Step;
import models.AddListOfBookBodyModel;
import models.LoginResponseModel;
import models.RegisterUserBodyModel;
import models.RegisterUserResponseModel;
import org.openqa.selenium.Cookie;
import pages.ProfilePage;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.BookStoreSpecs.*;

public class BookStoreSteps {

    @Step("Создать нового пользователя")
    public void registerUserApi(String userName, String password) {
        RegisterUserBodyModel userData = new RegisterUserBodyModel();
        userData.setUserName(userName);
        userData.setPassword(password);
        given(requestSpec)
                .body(userData)

                .when()
                .post("/Account/v1/User")

                .then()
                .spec(responseStatusCode201Spec)
                .extract().as(RegisterUserResponseModel.class);
    }

    @Step("Получить токен авторизации")
    public void generateTokenApi(String userName, String password) {
        RegisterUserBodyModel userData = new RegisterUserBodyModel();
        userData.setUserName(userName);
        userData.setPassword(password);
        given(requestSpec)
                .body(userData)

                .when()
                .post("Account/v1/GenerateToken")

                .then()
                .spec(responseStatusCode200Spec)
                .extract().response();
    }

    @Step("Авторизовать пользователя")
    public LoginResponseModel loginUserApi(String userName, String password) {
        RegisterUserBodyModel userData = new RegisterUserBodyModel();
        userData.setUserName(userName);
        userData.setPassword(password);
        return (given(requestSpec)
                .body(userData)

                .when()
                .post("Account/v1/Login")

                .then()
                .spec(responseStatusCode200Spec)
                .extract().as(LoginResponseModel.class));
    }

    @Step("Добавить книгу в профиль пользователя")
    public void addBookToProfileApi(LoginResponseModel authResponse, String isbn) {
        AddListOfBookBodyModel booksData = new AddListOfBookBodyModel();
        booksData.setUserId(authResponse.getUserId());
        AddListOfBookBodyModel.Isbn isbns = new AddListOfBookBodyModel.Isbn();
        isbns.setIsbn(isbn);
        booksData.setCollectionOfIsbns(List.of(isbns));

        given(authorizedRequestSpec(authResponse.getToken()))
                .body(booksData)

                .when()
                .post("BookStore/v1/Books")

                .then()
                .spec(responseStatusCode201Spec);
    }

    @Step("Установить авторизационные cookie в браузере")
    public void setAuthCookie(LoginResponseModel authResponse) {
        open("/images/Toolsqa.jpg");
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.getToken()));
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.getExpires()));
    }

    @Step("Удалить книгу из профиля пользователя")
    public void deleteBookFromProfileUI() {
        ProfilePage page = new ProfilePage();
        page.openPage()
                .deleteBook()
                .checkThatBooksListIsEmpty();
    }

    @Step("Удалить профиль пользователя")
    public void deleteProfileApi(LoginResponseModel loginResponse) {
        given(authorizedRequestSpec(loginResponse.getToken()))

                .when()
                .delete("/Account/v1/User/" + loginResponse.getUserId())

                .then()
                .spec(responseStatusCode204Spec)
                .extract().response();
    }
}
