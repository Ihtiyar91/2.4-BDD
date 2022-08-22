package test;

import data.DataHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        var dashboardPage =new DashboardPage();
        var cardInfoFirst = DataHelper.getFirstCard();
        var initialBalanceFirst = dashboardPage.getCardBalance(cardInfoFirst);
        var cardInfoSecond = DataHelper.getSecondCard();
        var initialBalanceSecond = dashboardPage.getCardBalance(cardInfoSecond);
        int sum = 500;
        dashboardPage.topUpFirstCard();
        var transferPage = new TransferPage();
        var cardInfo = DataHelper.getSecondCard();
        transferPage.topUp(cardInfo,sum);
        var balanceFirst =  dashboardPage.getCardBalance(cardInfoFirst);
        var balanceSecond =  dashboardPage.getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst + sum;
        int expectedSecond = initialBalanceSecond - sum;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldTransferFromFirstToSecondCard() {
        var dashboardPage =new DashboardPage();
        var cardInfoFirst = DataHelper.getFirstCard();
        var initialBalanceFirst = dashboardPage.getCardBalance(cardInfoFirst);
        var cardInfoSecond = DataHelper.getSecondCard();
        var initialBalanceSecond = dashboardPage.getCardBalance(cardInfoSecond);
        int sum = 1000;
        dashboardPage.topUpSecondCard();
        var transferPage = new TransferPage();
        var cardInfo = DataHelper.getFirstCard();
        transferPage.topUp(cardInfo,sum);
        var balanceFirst = dashboardPage.getCardBalance(cardInfoFirst);
        var balanceSecond = dashboardPage.getCardBalance(cardInfoSecond);
        int expectedFirst = initialBalanceFirst - sum;
        int expectedSecond = initialBalanceSecond + sum;
        assertEquals(expectedFirst, balanceFirst);
        assertEquals(expectedSecond, balanceSecond);
    }

    @Test
    void shouldTransferAmountExceedingBalance() {
        var dashboardPage =new DashboardPage();
        int sum = 50000;
        dashboardPage.topUpSecondCard();
        var transferPage = new TransferPage();
        var cardInfo = DataHelper.getSecondCard();
        transferPage.topUp(cardInfo,sum);
        transferPage.getErrorNotMoney();
    }

    @Test
    void shouldTransferZeroRub() {
        var dashboardPage =new DashboardPage();
        int sum = 0;
        dashboardPage.topUpSecondCard();
        var transferPage = new TransferPage();
        var cardInfo = DataHelper.getSecondCard();
        transferPage.topUp(cardInfo,sum);
        transferPage.getErrorSumZero();
    }
}

