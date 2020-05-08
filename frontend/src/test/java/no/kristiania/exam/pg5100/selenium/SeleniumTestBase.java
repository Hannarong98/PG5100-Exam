package no.kristiania.exam.pg5100.selenium;


import no.kristiania.exam.pg5100.selenium.po.IndexPO;
import no.kristiania.exam.pg5100.selenium.po.SignUpPO;
import no.kristiania.exam.pg5100.selenium.po.ui.ProfilePO;
import no.kristiania.exam.pg5100.selenium.po.ui.RedeemPO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;


import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;


public abstract class SeleniumTestBase {


    protected abstract WebDriver getDriver();

    protected abstract String getServerHost();

    protected abstract int getServerPort();


    private static final AtomicInteger counter = new AtomicInteger(0);

    private String getUniqueId() {
        return "foo_SeleniumLocalIT_" + counter.getAndIncrement() + "@test.com";
    }


    private IndexPO home;


    private IndexPO createNewUser(String username, String password, String firstname, String lastname) {

        home.toStartingPage();

        SignUpPO signUpPO = home.toSignUp();

        IndexPO indexPO = signUpPO.createUser(username, password, firstname, lastname);
        assertNotNull(indexPO);

        return indexPO;
    }

    @BeforeEach
    public void initTest() {

        /*
            we want to have a new session, otherwise the tests
            will share the same Session beans
         */
        getDriver().manage().deleteAllCookies();

        home = new IndexPO(getDriver(), getServerHost(), getServerPort());

        home.toStartingPage();

        assertTrue(home.isOnPage(), "Failed to start from Home Page");
    }

    @Test
    public void testCreateAndLogoutUser() {

        assertFalse(home.isLoggedIn());

        String username = getUniqueId();
        String password = "123456789";
        String firstname = "Selenium";
        String lastname = "Test";
        home = createNewUser(username, password, firstname, lastname);

        assertTrue(home.isLoggedIn());
        assertTrue(home.getDriver().getPageSource().contains(firstname));

        home.doLogout();

        assertFalse(home.isLoggedIn());
        assertFalse(home.getDriver().getPageSource().contains(firstname));
    }

    @Test
    public void testDisplayHomePage() {
        assertEquals(20, home.getDisplayedLootTableSize());
    }

    @Test
    public void testEmptyCollection() {
        createNewUser(getUniqueId(), "123", "Selenium", "Test");

        ProfilePO po = home.toProfilePage();

        assertTrue(po.userCollectionIsEmpty());
        assertEquals(3, po.getUserLootBoxCounter());
    }

    @Test
    public void testRedeemLootBox() {
        createNewUser(getUniqueId(), "123", "Selenium", "Test");

        RedeemPO redeemPO = home.toRedeemPage();

        assertEquals(3, redeemPO.getLayoutLootBoxCounter());

        redeemPO.clickAndWait("redeemBtnId");

        assertEquals(2, redeemPO.getLayoutLootBoxCounter());

        ProfilePO profilePO = redeemPO.toProfile();

        assertEquals(3, profilePO.getUserCollectionSize());
    }

    @Test
    public void testFailedRedeemLootBox() {
        createNewUser(getUniqueId(), "123", "Selenium", "Test");

        RedeemPO redeemPO = home.toRedeemPage();

        assertTrue(redeemPO.redeemBtnIsVisible());

        redeemPO.clickAndWait("redeemBtnId");
        redeemPO.clickAndWait("redeemBtnId");
        redeemPO.clickAndWait("redeemBtnId");


        assertEquals(0, redeemPO.getLayoutLootBoxCounter());

        assertFalse(redeemPO.redeemBtnIsVisible());

    }


    @Test
    public void testMillItem(){
        createNewUser(getUniqueId(), "123", "Selenium", "Test");

        ProfilePO profilePO = home.toProfilePage();

        assertEquals(3000, profilePO.getUserMillsInProfile());
        assertTrue(profilePO.userCollectionIsEmpty());

        RedeemPO redeemPO = profilePO.toRedeemPage();

        redeemPO.clickAndWait("redeemBtnId");

        ProfilePO backToProfilePo = redeemPO.toProfile();

        assertEquals(3, backToProfilePo.getUserCollectionSize());

        backToProfilePo.clickAndWaitByXpath("//table/tbody/tr[1]/td[6]/form/input[2]");

        assertEquals(2, backToProfilePo.getUserCollectionSize());
        assertTrue(backToProfilePo.getUserMillsInProfile() > 3000);

    }

    @Test
    public void testBuyLootBox() {
        createNewUser(getUniqueId(), "123", "Selenium", "Test");

        RedeemPO redeemPO = home.toRedeemPage();

        assertEquals(3000, redeemPO.getLayoutMillValue());

        redeemPO.clickAndWait("buyLootBoxBtnId");

        // Lootbox is worth 700 mills
        assertEquals(2300, redeemPO.getLayoutMillValue());
    }


}
