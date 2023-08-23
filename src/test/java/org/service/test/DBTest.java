package org.service.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.service.data.DBHelper;
import org.service.data.DataHelper;
import org.service.page.MainPage;
import org.junit.jupiter.api.*;

import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

public class DBTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void openPage() {
        DataHelper.openPage();
    }

    @AfterEach
    void clearTable() {
        DBHelper.cleanDatabase();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }



    MainPage mainPage = new MainPage();

    @Test
    void shouldApproveDebitPaymentCard1() throws SQLException {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber1();
        mainPage.fillDateOwnerCVV();
        mainPage.findSuccessNotification();
        String actualPaymentStatus = DBHelper.getDebitCardStatus();
        assertEquals(DBHelper.expectedPaymentStatusApproved, actualPaymentStatus);

    }

    @Test
    void shouldDeclineDebitPaymentCard2() throws SQLException {
        mainPage.payWithDebitCard();
        mainPage.fillCardNumber2();
        mainPage.fillDateOwnerCVV();
        mainPage.findContinue();
        String actualPaymentStatus = DBHelper.getDebitCardStatus();
        assertEquals(DBHelper.expectedPaymentStatusDeclined, actualPaymentStatus);

    }

    @Test
    void shouldApproveCreditPaymentCard1() throws SQLException {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber1();
        mainPage.fillDateOwnerCVV();
        mainPage.findContinue();
        String actualPaymentStatus = DBHelper.getCreditStatus();
        assertEquals(DBHelper.expectedPaymentStatusApproved, actualPaymentStatus);
    }

    @Test
    void shouldDeclineCreditPaymentCard2() throws SQLException {
        mainPage.payWithCreditCard();
        mainPage.fillCardNumber2();
        mainPage.fillDateOwnerCVV();
        mainPage.findContinue();
        String actualPaymentStatus = DBHelper.getCreditStatus();
        assertEquals(DBHelper.expectedPaymentStatusDeclined, actualPaymentStatus);
    }

}