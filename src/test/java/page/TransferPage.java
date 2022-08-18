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
    private SelenideElement to = $("[data-test-id=to] input");
    private SelenideElement errorNotMoney = $x("//*[text() = 'Ошибка! Недостаточно средств']");
    private SelenideElement errorSumZero = $x("//*[text() = 'Введите сумму  больше нуля']");

    public void getErrorNotMoney() {
        errorNotMoney.shouldBe(Condition.visible);
    }

    public void getErrorSumZero() {
        errorSumZero.shouldBe(Condition.visible);
    }

    public void topUp(int sum) {
        amount.setValue(String.valueOf(sum));
        if (to.getAttribute("value").contains("0001") == true) {
            from.setValue(DataHelper.getSecondCard().getCardNumber());
        } else
            from.setValue(DataHelper.getFirstCard().getCardNumber());
        transferButton.click();

    }

}