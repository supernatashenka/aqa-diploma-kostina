package org.service.data;

import lombok.Value;

import static com.codeborne.selenide.Selenide.open;

public class DataHelper {

    public static void openPage() {

        open("http://localhost:8080");
    }

    @Value
    public static class CardInfo {
        String card1 = "4444 4444 4444 4441";
        String card2 = "4444 4444 4444 4442";

    }

    public static String getCardNumber1() {

        return new CardInfo().card1;
    }

    public static String getCardNumber2() {

        return new CardInfo().card2;
    }
}