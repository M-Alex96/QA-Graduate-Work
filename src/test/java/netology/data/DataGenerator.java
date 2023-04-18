package netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataGenerator {

    private static Faker faker = new Faker(new Locale("en"));


    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getInvalidCardNumber() {
        return "4444 4444 4444 4445";
    }

    public static String getIncompleteCardNumber() {
        return "4444 4444 4444 444";
    }

    public static String getEmptyCardNumber() {
        return "";
    }


    public static String getCurrentMonth() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getInvalidMonthV1() {
        return "00";
    }

    public static String getInvalidMonthV2() {
        return "1";
    }

    public static String getInvalidMonthV3() {
        return "13";
    }

    public static String getPreviousMonth() {
        return LocalDate.now().minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    }

    public static String getEmptyMonth() {
        return "";
    }


    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getInvalidYearV1() {
        return "00";
    }

    public static String getInvalidYearV2() {
        return "9";
    }

    public static String getPreviousYear() {
        return LocalDate.now().minusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getYearOverTheLimit() {
        return LocalDate.now().plusYears(6).format(DateTimeFormatter.ofPattern("yy"));
    }

    public static String getEmptyYear() {
        return "";
    }


    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOwnerCyrillic() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getNameOnlyLatin() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName();
    }

    public static String getSurnameOnlyLatin() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().lastName();
    }

    public static String getOwnerWithPatronymic() {
        return "Ivan Ivanov Ivanovich";
    }

    public static String getOwnerWithNumbers() {
        return "123";
    }

    public static String getOwnerWithSymbols() {
        return "Iv@n_Iv%nov!";
    }

    public static String getOwnerWithLowerCase() {
        return "ivan ivanov";
    }

    public static String getOwnerNameOverTheLimit() {
        return faker.lorem().fixedString(100);
    }

    public static String getOwnerWithOnly1LatinLetter() {
        return "I";
    }

    public static String getOwnerEmpty() {
        return "";
    }


    public static String getValidCVC() {
        return faker.numerify("###");
    }

    public static String getInvalidCVC() {
        return "000";
    }

    public static String getIncompleteCVC() {
        return "1";
    }

    public static String getEmptyCVC() {
        return "";
    }
}