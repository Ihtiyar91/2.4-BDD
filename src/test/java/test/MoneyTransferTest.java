package test;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.CardsPage;
import page.DashboardPage;
import page.LoginPage;
import page.TransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MoneyTransferTest {
    @BeforeEach
    void setup() {
        var loginPage = open("http://localhost:9999", LoginPage.class);
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);

    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var cardsPage = new CardsPage();
        var cardInfoFirst = DataHelper.getFirstCard();
        var initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        var cardInfoSecond = DataHelper.getSecondCard();
        var initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int sum = 500;
        cardsPage.topUpFirstCard();
        var transferPage = new TransferPage();
        transferPage.topUp(sum);
        var balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        var balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst + sum;
        int expectedSecond = initialBalanceSecond - sum;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldTransferFromFirstToSecondCard() {
        var cardsPage = new CardsPage();
        var cardInfoFirst = DataHelper.getFirstCard();
        var initialBalanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        var cardInfoSecond = DataHelper.getSecondCard();
        var initialBalanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int sum = 1000;
        cardsPage.topUpSecondCard();
        var transferPage = new TransferPage();
        transferPage.topUp(sum);
        var balanceFirst = new DashboardPage().getCardBalance(cardInfoFirst);
        var balanceSecond = new DashboardPage().getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst - sum;
        int expectedSecond = initialBalanceSecond + sum;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldTransferAmountExceedingBalance() {
        var cardsPage = new CardsPage();
        int sum = 50000;
        cardsPage.topUpSecondCard();
        var transferPage = new TransferPage();
        transferPage.topUp(sum);
        transferPage.getErrorNotMoney();
    }

    @Test
    void shouldTransferZeroRub() {
        var cardsPage = new CardsPage();
        int sum = 0;
        cardsPage.topUpSecondCard();
        var transferPage = new TransferPage();
        transferPage.topUp(sum);
        transferPage.getErrorSumZero();
    }
}

