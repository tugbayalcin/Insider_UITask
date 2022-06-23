package pages;

import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

public class PageInitializer
{

    public PageInitializer() { PageFactory.initElements(Driver.getDriver(), this);}

}
