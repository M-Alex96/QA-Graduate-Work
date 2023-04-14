package netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netology.data.CardInfo;
import netology.data.DataBaseHelper;
import netology.page.MainPage;
import netology.page.PaymentProcessPage;
import netology.page.ReplyPage;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.open;
import static netology.data.DataGenerator.*;

public class CardPaymentTest {

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
        DataBaseHelper.cleanDataBase();
    }

    @Test
    public void shouldBuyByCardApproved() {
        open("http://localhost:8080");
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var mainPage = new MainPage();
        mainPage.buy();
        var paymentProcess = new PaymentProcessPage();
        paymentProcess.PaymentData(card);
        var replyPage = new ReplyPage();
        replyPage.successNotification();
        var paymentStatus = DataBaseHelper.getPaymentEntity();
        Assertions.assertEquals("APPROVED", paymentStatus);
    }

    @Test
    public void shouldBuyByCreditApproved() {
        open("http://localhost:8080");
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var mainPage = new MainPage();
        mainPage.buyInCredit();
        var paymentProcess = new PaymentProcessPage();
        paymentProcess.PaymentData(card);
        var replyPage = new ReplyPage();
        replyPage.successNotification();
        var paymentStatus = DataBaseHelper.getCreditEntity();
        Assertions.assertEquals("APPROVED", paymentStatus);
    }
}
