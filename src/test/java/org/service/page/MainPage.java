package org.service.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.service.data.DataGenerator;
import org.service.data.DataHelper;


import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    private static final SelenideElement buyWithDebitCardButton = $(byText("Купить"));
    private static final SelenideElement buyWithCreditCardButton = $(byText("Купить в кредит"));
    private static final SelenideElement continueButton = $(byText("Продолжить"));

    public void payWithDebitCard() {
        buyWithDebitCardButton.click();
    }

    public void payWithCreditCard() {
        buyWithCreditCardButton.click();
    }

    public void clickContinueButton() {
        continueButton.click();
    }

    private final ElementsCollection fields = $$(".input__control");
    private final SelenideElement inputCardNumber = $("[placeholder=\"0000 0000 0000 0000\"]");
    private final SelenideElement inputMonth = $("[placeholder=\"08\"]");
    private final SelenideElement inputYear = $("[placeholder=\"22\"]");
    private final SelenideElement inputOwner = fields.get(3);
    private final SelenideElement inputCVV = $("[placeholder=\"999\"]");

    private final SelenideElement cardNumberError = $x("//*[text()='Номер карты']/..//*[@class='input__sub']");
    private final SelenideElement monthError = $x("//*[text()='Месяц']/..//*[@class='input__sub']");
    private final SelenideElement yearError = $x("//*[text()='Год']/..//*[@class='input__sub']");
    private final SelenideElement cardholderError = $x("//*[text()='Владелец']/..//*[@class='input__sub']");
    private final SelenideElement CVCError = $x("//*[text()='CVC/CVV']/..//*[@class='input__sub']");

    public void getCardNumberError(String text) {
        cardNumberError.shouldBe(visible);
        cardNumberError.shouldHave(exactText(text));
    }
    public void getMonthError(String text) {
        monthError.shouldBe(visible);
        monthError.shouldHave(exactText(text));
    }

    public void getYearError(String text) {
        yearError.shouldBe(visible);
        yearError.shouldHave(exactText(text));
    }


    public void getCardholderError(String text) {
        cardholderError.shouldBe(visible);
        cardholderError.shouldHave(exactText(text));
    }



    public void getCVCError(String text) {
        CVCError.shouldBe(visible);
        CVCError.shouldHave(exactText(text));
    }




    public void findSuccessNotification() {
        $(byText("Операция одобрена Банком.")).shouldBe(visible, Duration.ofSeconds(15));
    }

    public void findErrorNotification() {
        $(byText("Ошибка! Банк отказал в проведении операции.")).shouldBe(visible, Duration.ofSeconds(15));
    }


    public void findContinue() {
        $(byText("Продолжить")).shouldBe(visible, Duration.ofSeconds(15));
    }



    public void fillDateOwnerCVV() {
        inputMonth.setValue(DataGenerator.generateCurrentMonth());
        inputYear.setValue(DataGenerator.generateCurrentYear());
        inputOwner.setValue(DataGenerator.generateValidName());
        inputCVV.setValue(DataGenerator.generateCVC());
        clickContinueButton();
    }



    public void fillRandomCardForm() {
        inputCardNumber.setValue(DataGenerator.generateCreditCard());
        inputMonth.setValue(DataGenerator.generateCurrentMonth());
        inputYear.setValue(DataGenerator.generateCurrentYear());
        inputOwner.setValue(DataGenerator.generateValidName());
        inputCVV.setValue(DataGenerator.generateCVC());
        clickContinueButton();
    }

    public void fillValidOwner(){
        inputOwner.setValue(DataGenerator.generateValidName());
    }

    public void fillValidCvv(){
        inputCVV.setValue(DataGenerator.generateCVC());
    }




    public void fillCardNumber1() {
        inputCardNumber.setValue(DataHelper.getCardNumber1());
    }

    public void fillCardNumber2() {
        inputCardNumber.setValue(DataHelper.getCardNumber2());
    }


    public void fillCardNumberShort() {
        inputCardNumber.setValue(DataGenerator.generateShortCardNumber());
    }


    public void fillMonthPrevious() {
        inputMonth.setValue(DataGenerator.generatePreviousMonth());
    }

    public void fillNextMonth() {
        inputMonth.setValue(DataGenerator.generateNextMonth());
    }

    public void fillMonthGreaterThanTwelve() {
        inputMonth.setValue(DataGenerator.generateMonthNumberGreaterThanTwelve());
    }

    public void fillMonthZero() {
        inputMonth.setValue("00");
    }


    public void fillYearCurrent() {
        inputYear.setValue(DataGenerator.generateCurrentYear());
    }

    public void fillYearPrevious() {
        inputYear.setValue(DataGenerator.generatePreviousYear());
    }

    public void fillYearInvalidFuture() {
        inputYear.setValue(DataGenerator.generateInvalidYearFuture());
    }


    public void fillOwnerFirstName() {
        inputOwner.setValue(DataGenerator.generateFirstName());
    }

    public void fillOwnerCyrillic() {
        inputOwner.setValue(DataGenerator.generateCyrillicName("ru"));
    }

    public void fillCVVInvalid() {
        inputCVV.setValue(DataGenerator.generateInvalidCVC());
    }




}