package org.service.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.*;


public class DataGenerator {
    public static String generateCreditCard() {
        Faker faker = new Faker();
        return faker.finance().creditCard(CreditCardType.MASTERCARD);
    }

    public static String generateShortCardNumber() {
        Faker faker = new Faker();
        return faker.number().digits(8);
    }

    public static String generateRandomText() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String generateCurrentMonth() {
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue();
        if (currentMonth < 10) {
            return ("0" + currentMonth);
        } else
            return (String.valueOf(currentMonth));
    }

    public static String generatePreviousMonth() {
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue();
        int previousMonth = currentMonth - 1;
        if (previousMonth < 10) {
            return ("0" + previousMonth);
        } else
            return (String.valueOf(previousMonth));
    }




    public static String generateNextMonth() {
        LocalDate date = LocalDate.now();
        int currentMonth = date.getMonthValue();
        int nextMonth = currentMonth + 1;
        if (nextMonth < 10) {
            return ("0" + nextMonth);
        } else
            return (String.valueOf(nextMonth));
    }




    public static String generateMonthNumberGreaterThanTwelve() {
        int greaterTwelve = (int) (13+ Math.random()*99);;
        return (String.valueOf(greaterTwelve));
    }

    public static String generateCurrentYear() {
        LocalDate date = LocalDate.now();
        int currentYear = date.getYear();
        return String.valueOf(currentYear % 100);
    }

    public static String generatePreviousYear() {
        LocalDate date = LocalDate.now();
        int previousYear = date.getYear() - 1;
        return String.valueOf(previousYear % 100);
    }

    public static String generateInvalidYearFuture() {
        LocalDate date = LocalDate.now();
        int invalidFutureYear = 6 + date.getYear();
        return String.valueOf(invalidFutureYear % 100);
    }

    public static String generateValidName() {
        Faker faker = new Faker(new Locale("en"));
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    public static String generateFirstName() {
        Faker faker = new Faker();
        return faker.name().firstName();
    }

    public static String generateCyrillicName(String locale) {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String generateToUpperCaseName() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        return name.toUpperCase();
    }


    public static String generateToLowerCaseName() {
        Faker faker = new Faker();
        String name = faker.name().fullName();
        return name.toLowerCase();
    }

    public static String generateCVC() {
        Faker faker = new Faker();
        return faker.number().digits(3);
    }

    public static String generateInvalidCVC() {
        Faker faker = new Faker();
        return faker.number().digits(2);
    }
}