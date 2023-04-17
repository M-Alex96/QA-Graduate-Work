package netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import netology.data.CardInfo;
import netology.data.DataBaseHelper;
import netology.page.DebitCardPage;
import netology.page.MainPage;
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
        open("http://localhost:8080");
        DataBaseHelper.cleanDataBase();
    }

    @Test
    public void shouldBuyByCardApproved() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.successNotification();
        var paymentDBStatus = DataBaseHelper.getCardPaymentEntity();
        Assertions.assertEquals("APPROVED", paymentDBStatus);
    }

    @Test
    public void shouldBuyByCardDeclined() {
        CardInfo card = new CardInfo(getDeclinedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.successNotification();
        var paymentDBStatus = DataBaseHelper.getCardPaymentEntity();
        Assertions.assertEquals("DECLINED", paymentDBStatus);
    }
}
