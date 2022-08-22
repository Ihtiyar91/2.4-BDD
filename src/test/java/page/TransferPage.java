package page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class TransferPage {
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement from = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    private SelenideElement errorNotMoney = $x("//*[text() = 'Ошибка! Недостаточно средств']");
    private SelenideElement errorSumZero = $x("//*[text() = 'Введите сумму  больше нуля']");

    public void getErrorNotMoney() {
        errorNotMoney.shouldBe(Condition.visible);
    }

    public void getErrorSumZero() {
        errorSumZero.shouldBe(Condition.visible);
    }


    public  DashboardPage topUp (DataHelper.CardInfo CardInfo, int sum) {
        amount.setValue(String.valueOf(sum));
        from.setValue(CardInfo.getCardNumber());
        transferButton.click();
        return new DashboardPage();
    }

}