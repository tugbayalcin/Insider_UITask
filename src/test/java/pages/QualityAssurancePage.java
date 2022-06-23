package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class QualityAssurancePage extends PageInitializer
{
    @FindBy(xpath = "//a[text()='See all QA jobs']")
    public WebElement seeAllQAJobsButton;

    @FindBy(xpath = "//select[@id='filter-by-location']")
    public WebElement filterByLocationDropdown;

    @FindBy(xpath = "//select[@id='filter-by-department']")
    public WebElement filterByDepartmentDropdown;

    @FindBy(xpath = "//div[@class='position-list-item-wrapper bg-light']")
    public List<WebElement> filteredJobList;

    public String positionTitleOfJob ="//p[@class='position-title font-weight-bold']";
    @FindBy(xpath = "//p[@class='position-title font-weight-bold']")
    public List<WebElement> positionTitleOfJobList;

    public String positionDepartmentOfJob = "//span[@class='position-department text-large font-weight-600 text-primary']";
    @FindBy(xpath = "//span[@class='position-department text-large font-weight-600 text-primary']")
    public List<WebElement> positionDepartmentOfJobList;

    public String positionLocationOfJob = "//div[@class='position-location text-large']";
    @FindBy(xpath = "//div[@class='position-location text-large']")
    public List<WebElement> positionLocationOfJobList;

    public String applyButtonForJob = "//a[text()='Apply Now']";
    @FindBy(xpath = "//a[text()='Apply Now']")
    public List<WebElement> applyButtonForJobList;












}
