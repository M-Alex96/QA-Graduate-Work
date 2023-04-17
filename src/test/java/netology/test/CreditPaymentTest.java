package netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netology.data.CardInfo;
import netology.data.DataBaseHelper;
import netology.page.MainPage;
import netology.page.CreditPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.open;
import static netology.data.DataGenerator.*;

public class CreditPaymentTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        DataBaseHelper.cleanDataBase();
    }

    @Test
    public void shouldBuyByCreditApproved() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.successNotification();
        var paymentDBStatus = DataBaseHelper.getCreditEntity();
        Assertions.assertEquals("APPROVED", paymentDBStatus);
    }
    @Test
    public void shouldBuyByCreditDeclined() {
        CardInfo card = new CardInfo(getDeclinedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.successNotification();
        var paymentDBStatus = DataBaseHelper.getCreditEntity();
        Assertions.assertEquals("DECLINED", paymentDBStatus);
    }
}
