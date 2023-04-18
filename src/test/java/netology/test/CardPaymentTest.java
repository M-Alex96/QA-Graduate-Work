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

    //positive ValidCardNumber
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
    public void shouldGetErrorBuyingByCardDeclined() {
        CardInfo card = new CardInfo(getDeclinedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.errorNotification();
        var paymentDBStatus = DataBaseHelper.getCardPaymentEntity();
        Assertions.assertEquals("DECLINED", paymentDBStatus);
    }

    //negative tests

    //CardNumber
    @Test
    public void shouldGetErrorBuyingByCardInvalidCardNumber() {
        CardInfo card = new CardInfo(getInvalidCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.errorNotification();
    }

    @Test
    public void shouldGetErrorBuyingByCardEmptyCardNumber() {
        CardInfo card = new CardInfo(getEmptyCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.empty();
    }

    @Test
    public void shouldGetErrorBuyingByCardIncompleteCardNumber() {
        CardInfo card = new CardInfo(getIncompleteCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    //Month
    @Test
    public void shouldGetErrorBuyingByCardInvalidMonthValue00() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getInvalidMonthV1(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCardInvalidMonthValue1() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getInvalidMonthV2(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardInvalidMonthValue13() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getInvalidMonthV3(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCardPreviousMonth() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getPreviousMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.cardExpired();
    }

    @Test
    public void shouldGetErrorBuyingByCardEmptyMonth() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getEmptyMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.empty();
    }

    //Year
    @Test
    public void shouldGetErrorBuyingByCardInvalidYearValue00() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getInvalidYearV1(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCardInvalidYearValue9() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getInvalidYearV2(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardPreviousYear() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getPreviousYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.cardExpired();
    }

    @Test
    public void shouldGetErrorBuyingByCardYearOverTheLimit() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getYearOverTheLimit(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCardEmptyYear() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getEmptyYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.empty();
    }

    //owner
    @Test
    public void shouldGetErrorBuyingByCardCyrillicOwnerName() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerCyrillic(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOnlyNameLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getNameOnlyLatin(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOnlySurnameLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getSurnameOnlyLatin(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardNameWithPatronymicLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithPatronymic(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOwnerNameNumbers() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithNumbers(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOwnerNameSymbols() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithSymbols(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOwnerNameLowerCaseLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithLowerCase(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOwnerNameOverTheLimit() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerNameOverTheLimit(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOwnerNameOnlyOneLatinLetter() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithOnly1LatinLetter(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardOwnerNameEmpty() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerEmpty(), getValidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.empty();
    }

    //CVC
    @Test
    public void shouldGetErrorBuyingByCardInvalidCVCNumber() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getInvalidCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardIncompleteCVCNumber() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getIncompleteCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCardEmptyCVCNumber() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getEmptyCVC());
        var paymentMethod = MainPage.buy();
        var paymentData = DebitCardPage.paymentData(card);
        var paymentSuccess = DebitCardPage.empty();
    }
}