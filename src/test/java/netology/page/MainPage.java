package netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private SelenideElement heading = $$("h2").findBy(Condition.text("Путешествие дня"));
    private static SelenideElement buy = $(byText("Купить"));
    private static SelenideElement buyInCredit = $(byText("Купить в кредит"));


    public MainPage(){
        heading.shouldBe(Condition.visible);
    }

    public static DebitCardPage buy() {
        buy.click();
        return new DebitCardPage();
    }

    public static CreditPage buyInCredit() {
        buyInCredit.click();
        return new CreditPage();
    }
}
