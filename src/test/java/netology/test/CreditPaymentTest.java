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

    //POSITIVE VALID CARD NUMBER
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
    //BUG
    public void shouldGetErrorBuyingByCreditDeclined() {
        CardInfo card = new CardInfo(getDeclinedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.errorNotification();
        var paymentDBStatus = DataBaseHelper.getCreditEntity();
        Assertions.assertEquals("DECLINED", paymentDBStatus);
    }

    //NEGATIVE TESTS

    //CARD NUMBER
    @Test
    public void shouldGetErrorBuyingByCreditInvalidCardNumber() {
        CardInfo card = new CardInfo(getInvalidCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.errorNotification();
    }

    @Test
    public void shouldGetErrorBuyingByCreditEmptyCardNumber() {
        CardInfo card = new CardInfo(getEmptyCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCreditIncompleteCardNumber() {
        CardInfo card = new CardInfo(getIncompleteCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    //MONTH
    @Test
    public void shouldGetErrorBuyingByCreditInvalidMonthValue00() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getInvalidMonthV1(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCreditInvalidMonthValue1() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getInvalidMonthV2(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCreditInvalidMonthValue13() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getInvalidMonthV3(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCreditPreviousMonth() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getPreviousMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCreditEmptyMonth() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getEmptyMonth(), getCurrentYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    //YEAR
    @Test
    public void shouldGetErrorBuyingByCreditInvalidYearValue00() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getInvalidYearV1(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.cardExpired();
    }

    @Test
    public void shouldGetErrorBuyingByCreditInvalidYearValue9() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getInvalidYearV2(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCreditPreviousYear() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getPreviousYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.cardExpired();
    }

    @Test
    public void shouldGetErrorBuyingByCreditYearOverTheLimit() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getYearOverTheLimit(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.invalidExpirationDate();
    }

    @Test
    public void shouldGetErrorBuyingByCreditEmptyYear() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getEmptyYear(), getValidOwner(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    //OWNER
    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditCyrillicOwnerName() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerCyrillic(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOnlyNameLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getNameOnlyLatin(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOnlySurnameLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getSurnameOnlyLatin(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditNameWithPatronymicLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithPatronymic(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOwnerNameNumbers() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithNumbers(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOwnerNameSymbols() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithSymbols(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOwnerNameLowerCaseLatin() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithLowerCase(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOwnerNameOverTheLimit() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerNameOverTheLimit(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditOwnerNameOnlyOneLatinLetter() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerWithOnly1LatinLetter(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCreditOwnerNameEmpty() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getOwnerEmpty(), getValidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.empty();
    }

    //CVC
    @Test
    //BUG
    public void shouldGetErrorBuyingByCreditInvalidCVCNumber() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getInvalidCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCreditIncompleteCVCNumber() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getIncompleteCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.wrongFormat();
    }

    @Test
    public void shouldGetErrorBuyingByCreditEmptyCVCNumber() {
        CardInfo card = new CardInfo(getApprovedCardNumber(), getCurrentMonth(), getCurrentYear(), getValidOwner(), getEmptyCVC());
        var paymentMethod = MainPage.buyInCredit();
        var paymentData = CreditPage.paymentData(card);
        var paymentSuccess = CreditPage.empty();
    }
}