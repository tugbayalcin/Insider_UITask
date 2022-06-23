package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InsiderHomePage extends PageInitializer
{

    @FindBy(xpath = "//div[@class='d-flex flex-column align-items-center justify-content-center text-center']//h1")
    public WebElement receptionElement;

    @FindBy(xpath = "//ul[@class='navbar-nav overflow-y']/li/a/span")
    List<WebElement> navigationBarItemsList;

    @FindBy(xpath = "//div[@class='dropdown-menu show']//h5")
    List<WebElement> careersItemsList;

    @FindBy(xpath = "//a[@id='wt-cli-accept-btn']")
    public WebElement onlyNeccessaryCookiesButton;








}
