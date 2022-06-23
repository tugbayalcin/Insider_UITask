package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static utilities.ObjectInitializer.SCREENSHOTS_PATH;

public abstract class TestBaseReports
{
    protected static ExtentReports extentReports;
    protected static ExtentTest extentTest;
    protected static ExtentHtmlReporter extentHtmlReporter;

    @BeforeTest(alwaysRun = true)
    public void setUpTest() throws IOException {
        FileUtils.cleanDirectory(new File(SCREENSHOTS_PATH)); //cleans all screenshots
        extentReports = new ExtentReports();


       // String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
       // String filePath = System.getProperty("user.dir") + "/test-output/Report_"+date+".html";
        String filePath = System.getProperty("user.dir") + "/test-output/Report.html";

        extentHtmlReporter = new ExtentHtmlReporter(filePath);
        extentReports.attachReporter(extentHtmlReporter);
        extentReports.setSystemInfo("Enviroment","QA");
        extentReports.setSystemInfo("Browser", ConfigReader.getProperty("browser")); // chrome, firefox
        extentReports.setSystemInfo("Automation Engineer", "Tugba");
        extentHtmlReporter.config().setDocumentTitle("Insider Job Apply Automation ");
        extentHtmlReporter.config().setReportName("Insider Job Apply Automation Reports");


        // ???
        extentHtmlReporter.config().setAutoCreateRelativePathMedia(true);


    }

    @AfterMethod(alwaysRun = true)
    public void tearDownMethod(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotLocation = ReusableMethods.getScreenshot(result.getName());
            extentTest.fail(result.getName());
            // extentTest.addScreenCaptureFromPath(screenshotLocation);
            extentTest.addScreencastFromPath(screenshotLocation);
            extentTest.fail(result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.skip("Test Case is skipped: " + result.getName());
        }
       // Driver.closeDriver();

    }



    @AfterTest(alwaysRun = true)
    public void tearDownTest() {

        extentReports.flush();
        Driver.closeDriver();
    }
}
