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
    private SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement month = $("[placeholder='08']");
    private SelenideElement year = $("[placeholder='22']");
    private SelenideElement owner = $$("form div:nth-child(3) .input__control").first();
    private SelenideElement cvc = $("[placeholder='999']");
    private SelenideElement continueButton = $$(".button").findBy(Condition.text("Продолжить"));
    private SelenideElement successNotification = $(".notification_status_ok");
    private SelenideElement errorNotification = $(".notification_status_error");
    private SelenideElement wrongFormat = $(byText("Неверный формат"));
    private SelenideElement invalidExpirationDate = $(byText("Неверно указан срок действия карты"));
    private SelenideElement expired = $(byText("Истёк срок действия карты"));
    private SelenideElement empty = $(byText("Поле обязательно для заполнения"));

    public void successNotification() {
        successNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void errorNotification() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void wrongFormat() {
        wrongFormat.shouldBe(Condition.visible);
    }

    public void invalidExpirationDate() {
        invalidExpirationDate.shouldBe(Condition.visible);
    }

    public void cardExpired() {
        expired.shouldBe(Condition.visible);
    }

    public void empty() {
        empty.shouldBe(Condition.visible);
    }

    public void paymentData(CardInfo info) {
        cardNumber.setValue(info.getCardNumber());
        month.setValue(info.getMonth());
        year.setValue(info.getYear());
        owner.setValue(info.getOwner());
        cvc.setValue(info.getCvc());
        continueButton.click();
    }
}

