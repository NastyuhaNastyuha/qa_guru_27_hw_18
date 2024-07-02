package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.executeJavaScript;

public class ProfilePage {
    public final SelenideElement deleteButton = $("#delete-record-undefined"),
        deleteBookModalHeader = $(".modal-header"),
        confirmDeletingButton = $("#closeSmallModal-ok"),
        bookRow = $(".rt-tbody").$(".rt-tr-group"),
        noRowsFoundTag = $(".profile-wrapper").$(".rt-noData");

    @Step("Открыть страницу профиля")
    public ProfilePage openPage() {
        open("/profile");
        executeJavaScript("$('#fixedban').remove()");
        executeJavaScript("$('footer').remove()");
        bookRow.shouldHave(text("Speaking JavaScript"));
        return this;
    }

    @Step("Удалить книгу из профиля пользователя")
    public ProfilePage deleteBook() {
        deleteButton.click();
        deleteBookModalHeader.shouldHave(text("Delete Book"));
        confirmDeletingButton.click();
        Selenide.confirm();
        return this;
    }

    @Step("Проверить, что список книг пользователя пуст")
    public ProfilePage checkThatBooksListIsEmpty() {
        noRowsFoundTag.shouldHave(text("No rows found"));
        return this;
    }
}
