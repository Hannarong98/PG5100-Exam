package no.kristiania.exam.pg5100.selenium.po;



public class SignUpPO extends LayoutPO{

    public SignUpPO(PageObject other) {
        super(other);
    }

    @Override
    public boolean isOnPage() {
        return getDriver().getTitle().contains("Sign Up");
    }

    public IndexPO createUser(String userName, String password, String firstname, String lastname){

        setText("username", userName);
        setText("password", password);
        setText("firstname", firstname);
        setText("lastname", lastname);
        clickAndWait("submit");

        IndexPO po = new IndexPO(this);
        if(po.isOnPage()){
            return po;
        }

        return null;
    }
}
