package org.service.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.service.data.DBHelper;
import org.service.page.MainPage;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.service.data.DataGenerator.*;

public class BuyTourTest {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void clearTable()  {
        DBHelper.cleanDatabase();
    }

    @BeforeEach
        void openPage() {
        open("http://localhost:8080");
    }

    @AfterAll
    static  void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("1. Оплата с помощью одобренной карты, " +
            "поля заполнены валидными значениями, путем нажатия кнопки \"Купить\"")
    void shouldDebitPaymentApprovedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findSuccessNotification();
        assertEquals("APPROVED", DBHelper.getDebitCardStatus());
    }


    @Test
    @DisplayName("2. Оплата с помощью одобренной карты, " +
            "поля заполнены валидными значениями, путем нажатия кнопки \"Купить в кредит\"")
    void shouldCreditPaymentApprovedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findSuccessNotification();
        assertEquals("APPROVED", DBHelper.getCreditStatus());
    }


    // Bug
    @Test
    @DisplayName("3. Оплата с помощью отклоненной карты, " +
            "поля заполнены валидными значениями, путем нажатия кнопки \"Купить\"")
    void shouldNotDebitPaymentDeclinedCard() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getDeclinedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
        assertEquals("DECLINED", DBHelper.getDebitCardStatus());
    }

    // bug
    @Test
    @DisplayName("4. Оплата с помощью отклоненной карты, " +
            "поля заполнены валидными значениями, путем нажатия кнопки \"Купить в кредит\"")
    void shouldNotCreditPaymentDeclinedCard() {
        CardInfo card = new CardInfo(getDeclinedCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        var mainPage = new MainPage();
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
        assertEquals("DECLINED", DBHelper.getCreditStatus());

    }

    //    bug
    @Test
    @DisplayName("5.Отправка пустой формы покупки тура путем нажатия кнопки \"Купить\"")
    void shouldNotDebitPaymentEmptyForm() {
        var mainPage = new MainPage();
        mainPage.payWithDebitCard();
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Поле обязательно для заполнения");
        mainPage.getMonthError("Поле обязательно для заполнения");
        mainPage.getYearError("Поле обязательно для заполнения");
        mainPage.getCardholderError("Поле обязательно для заполнения");
        mainPage.getCVCError("Поле обязательно для заполнения");

    }

    //    bug
    @Test
    @DisplayName("6. Отправка пустой формы покупки тура путем нажатия кнопки \"Купить в кредит\"")
    void shouldNotCreditPaymentEmptyForm() {
        var mainPage = new MainPage();
        mainPage.payWithCreditCard();
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Поле обязательно для заполнения");
        mainPage.getMonthError("Поле обязательно для заполнения");
        mainPage.getYearError("Поле обязательно для заполнения");
        mainPage.getCardholderError("Поле обязательно для заполнения");
        mainPage.getCVCError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("7. Отправка формы покупки тура с невалидными значениями номера карты, " +
            "путем нажатия кнопки \"Купить\"")
    void shouldNOtPayCardNumberShortDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateShortCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");

    }

    @Test
    @DisplayName("8. Отправка формы покупки тура с невалидными значениями номера карты, " +
            "путем нажатия кнопки \"Купить в кредит\"")
    void shouldNOtPayCardNumberShortCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateShortCardNumber(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");
    }

    @Test
    @DisplayName("9. Оплата по несуществующей карте, путем нажатия кнопки \"Купить\"")
    void shouldNOtPayRandomCardDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateCreditCard(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
    }

    @Test
    @DisplayName("10. Оплата по несуществующей карте, путем нажатия кнопки \"Купить в кредит\"")
    void shouldNOtPayRandomCardCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(generateCreditCard(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.findErrorNotification();
    }


    @Test
    @DisplayName("11. Отправка формы покупки тура с пустым полем \"Номер карты\", " +
            "остальные поля заполнены валидными значениями, путем нажатия кнопки \"Купить")
    void shouldNOtPayCardNumberEmptyDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getEmptyCArd(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");

    }


    @Test
    @DisplayName("12. Отправка формы покупки тура с пустым полем \"Номер карты\", " +
            "остальные поля заполнены валидными значениями, путем нажатия кнопки \"Купить в кредит")
    void shouldNOtPayCardNumberEmptyCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getEmptyCArd(), nextMonth, nextYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardNumberError("Неверный формат");

    }

    @Test
    @DisplayName("13. Оплата по карте с истекшим сроком действия (Месяц), путем нажатия кнопки \"Купить")
    void shouldNotPayPreviousMonthDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), previousMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("14. Оплата по карте с истекшим сроком действия (Месяц), " +
            "путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayPreviousMonthCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), previousMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("15. Оплата по карте с истекшим сроком действия (Год), " +
            "путем нажатия кнопки \"Купить")
    void shouldNotPayPreviousYearDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, previousYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Истёк срок действия карты");

    }

    @Test
    @DisplayName("16. Оплата по карте с истекшим сроком действия (Год), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayPreviousYearCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), currentMonth, previousYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Истёк срок действия карты");

    }

    @Test
    @DisplayName("17. Оплата по карте с указанием года более 5ти лет от текущего, путем нажатия кнопки \"Купить\"")
    void shouldNotPayFiveYearFutureDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, generateInvalidYearFuture, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("18. Оплата по карте с указанием года более 5ти лет от текущего, путем нажатия кнопки \"Купитьв кредит\"")
    void shouldNotPayFiveYearFutureCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, generateInvalidYearFuture, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("19. Оплата по карте, где в поле указан невалидный месяц(00), путем нажатия кнопки \"Купить")
    void shouldNotPayZeroMonthDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), zeroMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");

    }

    @Test
    @DisplayName("20. Оплата по карте, где в поле указан невалидный месяц(00), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayZeroMonthCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), zeroMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("21. Оплата по карте, где в поле указан невалидный месяц (13 и более), путем нажатия кнопки \"Купить")
    void shouldNotPayMonthGreaterThanTwelveDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), generateMonthNumberGreaterThanTwelve(), currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("22. Оплата по карте, где в поле указан невалидный месяц (13 и более), путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayMonthGreaterThanTwelveCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), generateMonthNumberGreaterThanTwelve(), currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("23. Оплата по карте с пустым полем \"Месяц\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyMonthDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), emptyMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверный формат");
    }

    @Test
    @DisplayName("24. Оплата по карте с пустым полем \"Месяц\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyMonthCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), emptyMonth, currentYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getMonthError("Неверный формат");
    }

    @Test
    @DisplayName("25. Оплата по карте с пустым полем \"Год\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyYearDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, emptyYear, generateValidName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверный формат");
    }

    @Test
    @DisplayName("26. Оплата по карте с пустым полем \"Год\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyYearCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, emptyYear, generateValidName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getYearError("Неверный формат");
    }

    //27 баг
    @Test
    @DisplayName("27. Оплата по карте, где в поле \"Владелец\" заполнено кирилицей, путем нажатия кнопки \"Купить")
    void shouldNotPayCyrillicNameDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateCyrillicName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }
    //28. bug

    @Test
    @DisplayName("28. Оплата по карте, где в поле \"Владелец\" заполнено кирилицей, путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayCyrillicNameCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateCyrillicName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    //29.bug
    @Test
    @DisplayName("29. Оплата по карте, где в поле \"Владелец\" указаны только имя, путем нажатия кнопки \"Купить")
    void shouldNotPayOnlyNameDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateFirstName(), generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    //30. баг

    @Test
    @DisplayName("30. Оплата по карте, где в поле \"Владелец\" указаны только имя, путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayOnlyNameCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateFirstName(), generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Неверный формат");
    }

    @Test
    @DisplayName("31. Оплата по карте с пустым полем \"Владелец\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyHolderDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, emptyCardholder, generateCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("32.Оплата по карте с пустым полем \"Владелец\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyHolderCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, emptyCardholder, generateCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCardholderError("Поле обязательно для заполнения");
    }

    @Test
    @DisplayName("33. Оплата по карте, где в поле \"CVV\" указаны невалидные данные, путем нажатия кнопки \"Купить")
    void shouldNotPayInvalidCVVDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), generateInvalidCVC());
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Неверный формат");

    }

    @Test
    @DisplayName("34. Оплата по карте, где в поле \"CVV\" указаны невалидные данные, путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayInvalidCVVCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), generateInvalidCVC());
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Неверный формат");
    }

    //35. баг
    @Test
    @DisplayName("35.  Оплата по карте с пустым полем \"CVV\", путем нажатия кнопки \"Купить")
    void shouldNotPayEmptyCVVDebitPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), emptyCvv);
        mainPage.payWithDebitCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Поле обязательно для заполнения");

    }

    //36. bug
    @Test
    @DisplayName("36.  Оплата по карте с пустым полем \"CVV\", путем нажатия кнопки \"Купить в кредит")
    void shouldNotPayEmptyCVVCreditPayment() {
        var mainPage = new MainPage();
        CardInfo card = new CardInfo(getApprovedCardNumber(), nextMonth, currentYear, generateValidName(), emptyCvv);
        mainPage.payWithCreditCard();
        mainPage.fillForm(card);
        mainPage.clickContinueButton();
        mainPage.getCVCError("Поле обязательно для заполнения");

    }

}
