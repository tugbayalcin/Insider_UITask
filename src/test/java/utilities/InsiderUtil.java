package utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static utilities.ObjectInitializer.*;
import static utilities.ObjectInitializer.we;
import static utilities.WebDriverUtil.getObject;

public class InsiderUtil
{
    public static WebElement getWebElementFromList(List<WebElement> list, String element)
    {
        WebElement we = null;

        for(WebElement each : list)
        {
            if(each.getText().equals(element))
            {
                we = each;
                break;
            }
        }

        return we;
    }

    // insider method

    public static void clickElementInList(String element, String listName)
    {
        try
        {
            webElementList = getObject(listName);
            clickElement = InsiderUtil.getWebElementFromList(webElementList,element);
            JSUtils.scrollIntoVIewJS(clickElement);
            ReusableMethods.waitForVisibility(clickElement,2);
            JSUtils.clickElementByJS(clickElement);
            ReusableMethods.waitFor(1);
        }
        catch (Exception exception){
            System.out.println("exception = " + exception);
        }
    }

    public static void checkedSituationOfElement(String element, String function)
    {
        we = getObject(element);
        JSUtils.scrollIntoVIewJS(we);
        ReusableMethods.waitForVisibility(we,2);

        try
        {
            switch(function.toLowerCase())
            {
                case "displayed":
                    Assert.assertTrue(we.isDisplayed());
                    break;
                case "enabled" :
                    Assert.assertTrue(we.isEnabled());
                    break;
                case "selected" :
                    Assert.assertTrue(we.isSelected());
                    break;

            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void clicksOn(String element)
    {
        try {
            we = getObject(element);
            JSUtils.scrollIntoVIewJS(we);
            ReusableMethods.waitForVisibility(we,2);
            JSUtils.clickElementByJS(we);

        }
        catch (Exception exception){
            System.out.println("exception = " + exception);
        }
    }

    public static void selectAnItemFromDropdown(String dropdownElementi, String toBeSelectedElement)
    {
        we = getObject(dropdownElementi);
        Select select = new Select(we);
        select.selectByVisibleText(toBeSelectedElement);
    }

    public static Boolean isListEmpty(String listName)
    {
        List<WebElement> filteredjobList = getObject(listName);
        try
        {
            return filteredjobList.size()>0 ? false : true;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static void checkedItemIncludeExpectedValue(String listName,Map<String,String> checkedItem )
    {
        List<WebElement> baseElementList  = getObject(listName);


        for (String each : checkedItem.keySet())
        {
            webElementList = baseElementList.get(0).findElements(By.xpath(each));

            for (int j=0; j< webElementList.size(); j++)
            {
                we=webElementList.get(j);
                JSUtils.scrollIntoVIewJS(we);
                softAssert.assertTrue(we.getAttribute("innerText").contains(checkedItem.get(each)),
                        each+" of Element "+j+" Could Not Be Verified");
            }

        }

    }

    public static void applyJob()
    {
        webElementList = getObject("applyButtonForJobList");

        for (WebElement each : webElementList)
        {
            JSUtils.scrollIntoVIewJS(each);
            //vReusableMethods.hover(each);
            JSUtils.clickElementByJS(each);
            windowHandleSet = Driver.getDriver().getWindowHandles();

            for(String value : windowHandleSet)
            {
                if(!(each.equals(homeWindowHandleValue))){
                    newWindowHandleValue = value;
                }
            }

            Driver.getDriver().switchTo().window(newWindowHandleValue);
            Assert.assertTrue(Driver.getDriver().getCurrentUrl().contains("lever"));
            Driver.getDriver().switchTo().window(homeWindowHandleValue);

        }
    }

}







