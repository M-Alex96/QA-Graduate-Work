package netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement heading = $$("h2").findBy(Condition.text("Путешествие дня"));
    private SelenideElement buy = $(byText("Купить"));
    private SelenideElement buyInCredit = $(byText("Купить в кредит"));
    private SelenideElement paymentOptionCard = $$("h3").findBy(Condition.text("Оплата по карте"));
    private SelenideElement paymentOptionCredit = $$("h3").findBy(Condition.text("Кредит по данным карты"));

    public MainPage(){
        heading.shouldBe(Condition.visible);
    }

    public PaymentProcessPage buy() {
        buy.click();
        return new PaymentProcessPage();
    }

    public PaymentProcessPage buyInCredit() {
        buyInCredit.click();
        return new PaymentProcessPage();
    }
}
