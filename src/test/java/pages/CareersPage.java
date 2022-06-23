package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CareersPage extends PageInitializer
{


    @FindBy(xpath = "//section[@id='career-find-our-calling']")
    public WebElement teamsBlock;

    @FindBy(xpath = "//section[@id='career-find-our-calling']//h3[@class='category-title-media']")
    public WebElement teamsBlockTitle;

    @FindBy(xpath = "(//section[@id='career-find-our-calling']//div[@class='job-item col-12 col-lg-4 mt-5'])[2]")
    public WebElement salesInTeamsBlock;

    @FindBy(xpath = "//section[@id='career-our-location']")
    public WebElement locationsBlock;

    @FindBy(xpath = "//section[@id='career-our-location']//h3[@class='category-title-media ml-0']")
    public WebElement locationsBlockTitle;


    @FindBy(xpath = "(//section[@id='career-our-location']//li[@class='glide__slide'])[2]")
    public WebElement londonInLocationsBlock;

    @FindBy(xpath = "//section[@data-id='a8e7b90']")
    public WebElement lifeAtInsiderBlock;

    @FindBy(xpath = "//section[@data-id='a8e7b90']//h2[@class='elementor-heading-title elementor-size-default']")
    public WebElement lifeAtInsiderBlockTitle;

    @FindBy(xpath = "//div[@class='swiper-wrapper']")
    public WebElement picturesLifeAtInsiderBlock;

    @FindBy(xpath = "//a[text()='See all teams']")
    public WebElement seeAllTeamsButton;

    @FindBy(xpath = "//h3[text()='Quality Assurance']")
    public WebElement qualityAssuranceJobTeams;









}
