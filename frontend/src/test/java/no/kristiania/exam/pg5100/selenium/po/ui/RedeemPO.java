package no.kristiania.exam.pg5100.selenium.po.ui;

import no.kristiania.exam.pg5100.selenium.po.PageObject;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RedeemPO extends PageObject {
    public RedeemPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Redeem");
    }

    public ProfilePO toProfile() {
        clickAndWait("profileId");

        ProfilePO po = new ProfilePO(this);

        assertTrue(po.isOnPage());

        return po;
    }

    public boolean redeemBtnIsVisible(){
        return getDriver().findElements(By.id("redeemBtnId")).size() > 0;
    }

    public int getLayoutLootBoxCounter(){
        return getInteger("layoutLootBoxCounterId");
    }

    public int getLayoutMillValue(){
        return getInteger("layoutMillValueId");
    }
}
