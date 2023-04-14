package netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class ReplyPage {
    private final SelenideElement successNotification = $(".notification_status_ok");
    private final SelenideElement errorNotification = $(".notification_status_error");
    private final SelenideElement wrongFormat = $(byText("Неверный формат"));
    private final SelenideElement invalidExpirationDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement expired = $(byText("Истёк срок действия карты"));
    private final SelenideElement empty = $(byText("Поле обязательно для заполнения"));


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
}
