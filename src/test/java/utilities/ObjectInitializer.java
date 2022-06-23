package utilities;


import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;
import pages.QualityAssurancePage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectInitializer
{
    public static WebElement we ;
    public static String weName;
    public static int stepNumber;
    public static String homeWindowHandleValue;

    public static WriteToTxt writeToTxt=new WriteToTxt();

    public static Set<Class> classes;//Arraylistin setlisi yani duplication yok
    public static List<WebElement> webElementList = new ArrayList<>();
    public static WebElement clickElement;
    public static QualityAssurancePage qualityAssurancePage = new QualityAssurancePage();
    public static SoftAssert softAssert = new SoftAssert();
    public static Set<String> windowHandleSet;
    public static String newWindowHandleValue="";



    public static final String USER_DIR=System.getProperty("user.dir");
    public static final String SCREENSHOTS_PATH= USER_DIR + "/test-output/Screenshots/";
    public static final String FAILED_STEP_SCREENSHOTS_PATH= USER_DIR + "/test-output/Screenshots/failedStep/";
}
