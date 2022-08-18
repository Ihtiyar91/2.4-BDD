package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class CardsPage {
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