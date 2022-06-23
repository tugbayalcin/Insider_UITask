package tests.smokeTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import static utilities.InsiderUtil.*;
import static utilities.ObjectInitializer.*;

import utilities.Driver;
import utilities.ReusableMethods;
import utilities.TestBaseReports;
import java.util.HashMap;
import java.util.Map;
import static utilities.ConfigReader.getProperty;


public class InsiderUITest extends TestBaseReports
{


    // step 1
    @Test(priority = 1)
    public static void theUserNavigateToURLandCheckHomePageIsOpened() {
        extentTest = extentReports.createTest("Navigate to URL", "The User Navigate to URL And Check Home Page Is Opened");

        String testData = "_receptionText";

        ReusableMethods.navigateToURL(getProperty("insider.uat.url"), getProperty("browser"));
        extentTest.info("Navigated To URL");

        homeWindowHandleValue = Driver.getDriver().getWindowHandle();
        extentTest.info("Taken Window Handle Value");

        Assert.assertTrue(ReusableMethods.getTextAndVerify("receptionElement", testData));
        extentTest.pass("Complated Verification Successfully");

        clicksOn("onlyNeccessaryCookiesButton");
        extentTest.info("Accepted Only Necessary Cookies ");
    }

    // step 2
    @Test(priority = 2)
    public static void clickAnItemFromNavigationBar() {
        extentTest = extentReports.createTest("Select Category From Nav-Bar", "Click An Item From Navigation Bar");

        String itemName = "More";
        String listName = "navigationBarItemsList";
        clickElementInList(itemName, listName);
        extentTest.info("Clickeded Category: More ");
    }

    @Test(priority = 3)
    public static void clickAnItemFrom_More_navItem() {
        extentTest = extentReports.createTest("Select Sub-Category From Category Of More", "Click An Item From More Nav-Item");

        String itemName = "Careers";
        String listName = "careersItemsList";
        clickElementInList(itemName, listName);
        extentTest.info("Clickeded Sub-Category: Careers ");
    }

    @Test(priority = 4)
    public static void checkedBlocksAreOpenedInCareersPage() {

        extentTest = extentReports.createTest("Verify Some Blocks On Careers Page", "Verify Locations Block, Teams Block and Life at Insider Block ");


        checkedSituationOfElement("locationsBlock","displayed");
        checkedSituationOfElement("locationsBlockTitle","displayed");
        checkedSituationOfElement("londonInLocationsBlock","enabled");
        extentTest.info("Verificated Locations Block");

        checkedSituationOfElement("teamsBlock","displayed");
        checkedSituationOfElement("teamsBlockTitle","displayed");
        checkedSituationOfElement("salesInTeamsBlock","enabled");
        extentTest.info("Verificated Teams Block");

        checkedSituationOfElement("lifeAtInsiderBlock","displayed");
        checkedSituationOfElement("lifeAtInsiderBlockTitle","displayed");
        checkedSituationOfElement("picturesLifeAtInsiderBlock","enabled");
        extentTest.info("Verificated Life at Insider Block");
    }

    // step 3
    @Test(priority = 5)
    public static void clickATeamAndFilterJobs ()
    {
        extentTest = extentReports.createTest("Go To QA Teams",
                "Go To QA Teams And Filter, " +
                        "Location:Istanbul, Turkey, " +
                        "Department:Quality Assurance");

        clicksOn("seeAllTeamsButton");
        extentTest.info("Clicked 'see All Teams' Button");
        clicksOn("qualityAssuranceJobTeams");
        extentTest.info("Clicked 'Quality Assurance Job Teams' Area");
        ReusableMethods.waitForPageToLoad(10);
        clicksOn("seeAllQAJobsButton");
        extentTest.info("Clicked 'see All QA Jobs' Button");
        ReusableMethods.waitForPageToLoad(10);

        selectAnItemFromDropdown("filterByLocationDropdown","Istanbul, Turkey");
        extentTest.info("Location Filter Selected as Istanbul, Turkey");
        selectAnItemFromDropdown("filterByDepartmentDropdown","Quality Assurance");
        extentTest.info("Department Filter Selected as Quality Assurance");

        ReusableMethods.waitForPageToLoad(10);
        Assert.assertFalse(isListEmpty("filteredJobList"));
        extentTest.pass("Displayed Filtering Result Successfully");

    }

    // step 4
    @Test(priority = 6)
    public static void checkTheFilterResult()
    {
        extentTest = extentReports.createTest("Check The Filter Result",
                "Position Title must include 'Quality Assurance', " +
                        "Position Department must include 'Quality Assurance', " +
                        "Position Location must include Istanbul, Turkey, " +
                        "And All Job Advertisement must have 'Apply Now' Button");


        Map<String,String> checkedItemsMap = new HashMap<>();
        checkedItemsMap.put(qualityAssurancePage.positionTitleOfJob,"Quality Assurance");
        checkedItemsMap.put(qualityAssurancePage.positionDepartmentOfJob,"Quality Assurance");
        checkedItemsMap.put(qualityAssurancePage.positionLocationOfJob,"Istanbul, Turkey");
        checkedItemsMap.put(qualityAssurancePage.applyButtonForJob,"Apply Now");

        checkedItemIncludeExpectedValue("filteredJobList",checkedItemsMap);
        softAssert.assertAll();
        extentTest.fail("Not All Validations Were Obtained");

    }

    // step 5
    @Test(priority = 7)
    public static void applyAJob()
    {
        extentTest = extentReports.createTest("Apply A Job and Navigate Lever Application Form Page", "");

        applyJob();
        extentTest.pass("Applyed All Job Successfully");

    }





}
