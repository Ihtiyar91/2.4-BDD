package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
  private SelenideElement heading = $("[data-test-id=dashboard]");

  public DashboardPage() {

    heading.shouldBe(visible);
  }

  private ElementsCollection cards = $$(".list__item");
  private final String balanceStart = "баланс: ";
  private final String balanceFinish = " р.";

  public int getCardBalance (DataHelper.CardInfo cardInfo) {
    String id = cardInfo.getId();
    SelenideElement card = $ (withText(id));
    var text =card.text();
    return extractBalance(text);
  }


  private int extractBalance(String text) {
    var start = text.indexOf(balanceStart);
    var finish = text.indexOf(balanceFinish);
    var value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }
  private ElementsCollection buttons = $$("[data-test-id=action-deposit] ");
  private SelenideElement buttonPayToFirstCard = buttons.first();
  private SelenideElement buttonPayToSecondCard = buttons.last();

  public void topUpFirstCard() {
    buttonPayToFirstCard.click();
  }

  public void topUpSecondCard() {
    buttonPayToSecondCard.click();
  }

}
