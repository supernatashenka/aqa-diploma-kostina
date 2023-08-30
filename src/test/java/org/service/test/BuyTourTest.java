package org.service.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.service.data.DataHelper;

public class BuyTourTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void openPage() {
        DataHelper.openPage();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    org.service.page.MainPage mainPage = new org.service.page.MainPage();

    @Test
    @DisplayName("1. Оплата с помощью одобренной карты, поля заполнены валидными значениями, путем нажатия кнопки \"Купить\"")
    void shouldDebitPaymentApprovedCard() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidCvv();
        mainPage.fillValidOwner();
        mainPage.clickContinueButton();
        mainPage.findSuccessNotification();
    }

    @Test
    @DisplayName("2. Оплата с помощью одобренной карты, поля заполнены валидными значениями, путем нажатия кнопки \"Купить в кредит\"")
    void shouldCreditPaymentApprovedCard() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.findSuccessNotification();
    }


//    // Bug
//    @Test
//    @DisplayName("3. Оплата с помощью отклоненной карты, поля заполнены валидными значениями, путем нажатия кнопки \"Купить\"")
//    void shouldNotDebitPaymentDeclinedCard() {
//        mainPage.payWithDebitCard();
//        mainPage.fillCardNumber2();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillValidOwner();
//        mainPage.fillValidCvv();
//        mainPage.clickContinueButton();
//        mainPage.findErrorNotification();
//    }


//    // bug
//    @Test
//    @DisplayName("4. Оплата с помощью отклоненной карты, поля заполнены валидными значениями, путем нажатия кнопки \"Купить в кредит\"")
//    void shouldNotCreditPaymentDeclinedCard() {
//        mainPage.payWithCreditCard();
//        mainPage.fillCardNumber2();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillValidOwner();
//        mainPage.fillValidCvv();
//        mainPage.clickContinueButton();
//        mainPage.findErrorNotification();
//
//    }

//    //bug
//    @Test
//    @DisplayName("5.Отправка пустой формы покупки тура путем нажатия кнопки \"Купить\"")
//    void shouldNotDebitPaymentEmptyForm() {
//        mainPage.payWithDebitCard();
//        mainPage.clickContinueButton();
//        mainPage.getCardNumberError("Поле обязательно для заполнения");
//        mainPage.getMonthError("Поле обязательно для заполнения");
//        mainPage.getYearError("Поле обязательно для заполнения");
//        mainPage.getCardholderError("Поле обязательно для заполнения");
//        mainPage.getCVCError("Поле обязательно для заполнения");
//    }

//    //bug
//    @Test
//    @DisplayName("6. Отправка пустой формы покупки тура путем нажатия кнопки \"Купить в кредит\"")
//    void shouldNotCreditPaymentEmptyForm() {
//        mainPage.payWithCreditCard();
//        mainPage.clickContinueButton();
//        mainPage.getCardNumberError("Поле обязательно для заполнения");
//        mainPage.getMonthError("Поле обязательно для заполнения");
//        mainPage.getYearError("Поле обязательно для заполнения");
//        mainPage.getCardholderError("Поле обязательно для заполнения");
//        mainPage.getCVCError("Поле обязательно для заполнения");
//    }

    @Test
    @DisplayName("7. Отправка формы покупки тура с невалидными значениями номера карты, путем нажатия кнопки \"Купить\"")
    void shouldNOtPayCardNumberShortDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumberShort();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("8. Отправка формы покупки тура с невалидными значениями номера карты, путем нажатия кнопки \"Купить в кредит\"")
    void shouldNOtPayCardNumberShortCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumberShort();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("9. Оплата по несуществующей карте, путем нажатия кнопки \"Купить\"")
    void shouldNOtPayRandomCardDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillRandomCardForm();

        mainPage.findErrorNotification();
    }


    @Test
    @DisplayName("10. Оплата по несуществующей карте, путем нажатия кнопки \"Купить в кредит\"")
    void shouldNOtPayRandomCardCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillRandomCardForm();
        mainPage.findErrorNotification();
    }

    @Test
    @DisplayName("11. Отправка формы покупки тура с пустым полем \"Номер карты\", остальные поля заполнены валидными значениями, путем нажатия кнопки \"Купить")
    void shouldNOtPayCardNumberEmptyDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillDateOwnerCVV();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("12. Отправка формы покупки тура с пустым полем \"Номер карты\", остальные поля заполнены валидными значениями, путем нажатия кнопки \"Купить в кредит")
    void shouldNOtPayCardNumberEmptyCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillDateOwnerCVV();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("13. Оплата по карте с истекшим сроком действия (Месяц), путем нажатия кнопки \"Купить")
    void shouldNotPayPreviousMonthDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("14. Оплата по карте с истекшим сроком действия (Месяц), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayPreviousMonthCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("15. Оплата по карте с истекшим сроком действия (Год), путем нажатия кнопки \"Купить")
    void shouldNotPayPreviousYearDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillYearPrevious();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getYearError("Истёк срок действия карты");

    }

    @Test
    @DisplayName("16. Оплата по карте с истекшим сроком действия (Год), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayPreviousYearCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillYearPrevious();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getYearError("Истёк срок действия карты");

    }

    @Test
    @DisplayName("17. Оплата по карте с указанием года более 5ти лет от текущего, путем нажатия кнопки \"Купить\"")
    void shouldNotPayFiveYearFutureDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillYearInvalidFuture();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("18. Оплата по карте с указанием года более 5ти лет от текущего, путем нажатия кнопки \"Купитьв кредит\"")
    void shouldNotPayFiveYearFutureCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillYearInvalidFuture();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("19. Оплата по карте, где в поле указан невалидный месяц(00), путем нажатия кнопки \"Купить")
    void shouldNotPayZeroMonthDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthZero();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("20. Оплата по карте, где в поле указан невалидный месяц(00), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayZeroMonthCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthZero();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("21. Оплата по карте, где в поле указан невалидный месяц (13 и более), путем нажатия кнопки \"Купить")
    void shouldNotPayMonthGreaterThanTwelveDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthGreaterThanTwelve();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("22. Оплата по карте, где в поле указан невалидный месяц (13 и более), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayMonthGreaterThanTwelveCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthGreaterThanTwelve();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }


    @Test
    @DisplayName("23. Оплата по карте с пустым полем \"Месяц\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyMonthDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверный формат");
    }


    @Test
    @DisplayName("24. Оплата по карте с пустым полем \"Месяц\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyMonthCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверный формат");
    }


    @Test
    @DisplayName("25. Оплата по карте с пустым полем \"Год\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyYearDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверный формат");
    }

    @Test
    @DisplayName("26. Оплата по карте с пустым полем \"Год\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyYearCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillMonthPrevious();
        mainPage.fillValidOwner();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверный формат");
    }


//    //27 баг
//    @Test
//    @DisplayName("27. Оплата по карте, где в поле \"Владелец\" заполнено кирилицей, путем нажатия кнопки \"Купить")
//    void shouldNotPayCyrillicNameDebitPayment() {
//        mainPage.payWithDebitCard();
//        mainPage.fillCardNumber1();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillOwnerCyrillic();
//        mainPage.fillValidCvv();
//        mainPage.clickContinueButton();
//        mainPage.getCardholderError("Неверный формат");
//    }

//    //28. bug
//
//    @Test
//    @DisplayName("28. Оплата по карте, где в поле \"Владелец\" заполнено кирилицей, путем нажатия кнопки \"Купить в кредит")
//    void shouldNotPayCyrillicNameCreditPayment() {
//        mainPage.payWithCreditCard();
//        mainPage.fillCardNumber1();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillOwnerCyrillic();
//        mainPage.fillValidCvv();
//        mainPage.clickContinueButton();
//        mainPage.getCardholderError("Неверный формат");
//    }


//    //29.bug
//    @Test
//    @DisplayName("29. Оплата по карте, где в поле \"Владелец\" указаны только имя, путем нажатия кнопки \"Купить")
//    void shouldNotPayOnlyNameDebitPayment() {
//        mainPage.payWithDebitCard();
//        mainPage.fillCardNumber1();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillOwnerFirstName();
//        mainPage.fillValidCvv();
//        mainPage.clickContinueButton();
//        mainPage.getCardholderError("Неверный формат");
//    }


//    //30. баг
//
//    @Test
//    @DisplayName("30. Оплата по карте, где в поле \"Владелец\" указаны только имя, путем нажатия кнопки \"Купить в кредит")
//    void shouldNotPayOnlyNameCreditPayment() {
//        mainPage.payWithCreditCard();
//        mainPage.fillCardNumber1();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillOwnerFirstName();
//        mainPage.fillValidCvv();
//        mainPage.clickContinueButton();
//        mainPage.getCardholderError("Неверный формат");
//    }

    @Test
    @DisplayName("31. Оплата по карте с пустым полем \"Владелец\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyHolderDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Поле обязательно для заполнения");
    }


    @Test
    @DisplayName("32.Оплата по карте с пустым полем \"Владелец\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyHolderCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidCvv();
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Поле обязательно для заполнения");
    }


    @Test
    @DisplayName("33. Оплата по карте, где в поле \"CVV\" указаны невалидные данные, путем нажатия кнопки \"Купить")
    void shouldNotPayInvalidCVVDebitPayment() {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillCVVInvalid();
        mainPage.clickContinueButton();
        mainPage.getCVCError("Неверный формат");

    }


    @Test
    @DisplayName("34. Оплата по карте, где в поле \"CVV\" указаны невалидные данные, путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayInvalidCVVCreditPayment() {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillNextMonth();
        mainPage.fillYearCurrent();
        mainPage.fillValidOwner();
        mainPage.fillCVVInvalid();
        mainPage.clickContinueButton();
        mainPage.getCVCError("Неверный формат");

    }

//    //35. баг
//    @Test
//    @DisplayName("35.  Оплата по карте с пустым полем \"CVV\", путем нажатия кнопки \"Купить")
//    void shouldNotPayEmptyCVVDebitPayment() {
//        mainPage.payWithDebitCard();
//        mainPage.fillCardNumber1();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillValidOwner();
//        mainPage.clickContinueButton();
//        mainPage.getCVCError("Поле обязательно для заполнения");
//
//    }


//    //36. bug
//    @Test
//    @DisplayName("36.  Оплата по карте с пустым полем \"CVV\", путем нажатия кнопки \"Купить в кредит")
//    void shouldNotPayEmptyCVVCreditPayment() {
//        mainPage.payWithCreditCard();
//        mainPage.fillCardNumber1();
//        mainPage.fillNextMonth();
//        mainPage.fillYearCurrent();
//        mainPage.fillValidOwner();
//        mainPage.clickContinueButton();
//        mainPage.getCVCError("Поле обязательно для заполнения");
//
//    }
}

































































































