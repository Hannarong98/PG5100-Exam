package no.kristiania.exam.pg5100.selenium.po.ui;

import no.kristiania.exam.pg5100.selenium.po.PageObject;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfilePO extends PageObject {
    public ProfilePO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Profile");
    }

    public boolean userCollectionIsEmpty(){
        return getDriver().findElements(By.id("noItemId")).size() > 0;
    }

    public int getUserCollectionSize() {
        return getDriver().findElements(By.xpath("//table//tr")).size() -1;
    }

    public int getUserMillsInProfile(){
        return getInteger("userMillValueId");
    }

    public RedeemPO toRedeemPage(){
        clickAndWait("redeemId");

        RedeemPO po = new RedeemPO(this);

        assertTrue(po.isOnPage());

        return po;
    }

    public int getUserLootBoxCounter() {
        return getInteger("lootBoxCounterId");
    }
}
