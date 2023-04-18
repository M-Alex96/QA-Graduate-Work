package netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

    private SelenideElement paymentOptionCredit = $$("h3").findBy(Condition.text("Кредит по данным карты"));
    private static SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private static SelenideElement month = $("[placeholder='08']");
    private static SelenideElement year = $("[placeholder='22']");
    private static SelenideElement owner = $$("form div:nth-child(3) .input__control").first();
    private static SelenideElement cvc = $("[placeholder='999']");
    private static SelenideElement continueButton = $$(".button").findBy(Condition.text("Продолжить"));
    private static SelenideElement successNotification = $(".notification_status_ok");
    private static SelenideElement errorNotification = $(".notification_status_error");
    private static SelenideElement wrongFormat = $(byText("Неверный формат"));
    private static SelenideElement invalidExpirationDate = $(byText("Неверно указан срок действия карты"));
    private static SelenideElement expired = $(byText("Истёк срок действия карты"));
    private static SelenideElement empty = $(byText("Поле обязательно для заполнения"));

    public static CreditPage successNotification() {
        successNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
        return new CreditPage();
    }

    public static CreditPage errorNotification() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
        return new CreditPage();
    }

    public static CreditPage wrongFormat() {
        wrongFormat.shouldBe(Condition.visible);
        return new CreditPage();
    }

    public static CreditPage invalidExpirationDate() {
        invalidExpirationDate.shouldBe(Condition.visible);
        return new CreditPage();
    }

    public static CreditPage cardExpired() {
        expired.shouldBe(Condition.visible);
        return new CreditPage();
    }

    public static CreditPage empty() {
        empty.shouldBe(Condition.visible);
        return new CreditPage();
    }

    public static CreditPage paymentData(CardInfo info) {
        cardNumber.setValue(info.getCardNumber());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
        return new CreditPage();
    }
}

