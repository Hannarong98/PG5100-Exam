package no.kristiania.exam.pg5100.selenium.po;

import no.kristiania.exam.pg5100.selenium.po.ui.ProfilePO;
import no.kristiania.exam.pg5100.selenium.po.ui.RedeemPO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class IndexPO extends LayoutPO {

    public IndexPO(PageObject other) {
        super(other);
    }

    public IndexPO(WebDriver driver, String host, int port) {
        super(driver, host, port);
    }

    public void toStartingPage(){
        getDriver().get(host + ":" + port);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Heartstone collector");
    }


    public ProfilePO toProfilePage(){
        clickAndWait("profileId");

        ProfilePO po = new ProfilePO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public RedeemPO toRedeemPage() {
        clickAndWait("redeemId");

        RedeemPO po = new RedeemPO(this);
        assertTrue(po.isOnPage());

        return po;
    }

    public int getDisplayedLootTableSize() {
        return getDriver().findElements(By.xpath("//table//tr")).size() -1;
    }
}
