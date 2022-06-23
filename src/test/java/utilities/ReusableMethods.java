package utilities;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;

import static utilities.ObjectInitializer.*;
import static utilities.WebDriverUtil.getObject;

public class ReusableMethods {


    public static void navigateToURL(String url, String browserName)
    {
        weName=url;//!!!!!!!!

        Driver.getDriver(browserName).get(url);
        ReusableMethods.waitForPageToLoad(10);
        Assert.assertTrue(getTextAndVerify("@currentURL",url));
    }


    public static WebElement getAnyWebElementFromTheList(String webElementList)
    {
        try
        {
            List<WebElement> list= getObject(webElementList);
            if(list.size()>0)
            {
                int randomNum = (int)(Math.random() * list.size()); // [0,(x-1)]
                return list.get(randomNum);
            }
            else
                throw new Exception("The list has no item");

        }
        catch (Exception e){
            System.out.println("exception = " + e);
            return null;
        }


    }

    public static void sendKey(WebElement we, String text)
    {
        if(we.isDisplayed())
        {
            we.sendKeys(text);
        }
        else
            throw new NoSuchElementException(text+ "could not have been sent since "+weName +" is not displayed");
    }

    public static String changeTheOptionInDropdown(String currentValue, String expectedValue)
    {
        char sign= expectedValue.charAt(0);
        String updatedValue="";

        switch (sign)
        {
            case '+':
                updatedValue=String.valueOf(Integer.parseInt(currentValue)+Integer.parseInt(expectedValue.substring(1)));break;
            case '-':
                updatedValue=String.valueOf(Integer.parseInt(currentValue)-Integer.parseInt(expectedValue.substring(1)));break;
            default:
                updatedValue=expectedValue;
        }
        return updatedValue;
    }

    public static  <T> String takeScreenshot(T ssOfWhat, String defaultPath, String fileName) throws IOException {
        // naming the screenshot with the current date to avoid duplication
        String date = new SimpleDateFormat("EEE yyyy-MMMM-dd hhmmss a ").format(new Date());
        // TakesScreenshot is an interface of selenium that takes the screenshot
        TakesScreenshot ts = (TakesScreenshot) ssOfWhat;
        waitFor(5);
        File source = ts.getScreenshotAs(OutputType.FILE);

        // full path to the screenshot location
        String target = defaultPath + "Step"+ stepNumber +"_"+fileName +"_"+ date + ".png";
        File finalDestination = new File(target);
        // save the screenshot to the path given
        try {
            FileUtils.copyFile(source, finalDestination);
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
        return target;
    }

    public static String replaceTurkishCharsWithEnglishOnes(String str) {
        String ret = str;
        char[] turkishChars = new char[] {0x131, 0x130, 0xFC, 0xDC, 0xF6, 0xD6, 0x15F, 0x15E, 0xE7, 0xC7, 0x11F, 0x11E};
        char[] englishChars = new char[] {'i', 'I', 'u', 'U', 'o', 'O', 's', 'S', 'c', 'C', 'g', 'G'};
        for (int i = 0; i < turkishChars.length; i++) {
            ret = ret.replaceAll(new String(new char[]{turkishChars[i]}), new String(new char[]{englishChars[i]}));
        }
        return ret;
    }


    public static boolean getTextAndVerify(String element,String testData)
    {
        String actualText="";

        if(testData.startsWith("_"))//It means expectedTestData is coming from configuration.properties file
        {
            testData=ConfigReader.getProperty(testData);
        }


        switch (element)
        {
            //@input means we getText from UI
            case "@currentURL":
                actualText = Driver.getDriver().getCurrentUrl();
                break;
            case "@currentPageTitle":
                actualText = Driver.getDriver().getTitle();
                break;

            default://this is a WebElement's Text)
                we = getObject(element);
                actualText = we.getText();

        }

       // boolean verification= ReusableMethods.replaceTurkishCharsWithEnglishOnes(actualText).contains(testData);//for non-english characters
        boolean verification = actualText.contains(testData);

        return verification;

    }


    public static void setUpDropDownSelection(String value, String element)
    {
        try {
            we=getObject(element);
            Select select =new Select(we);
            String currentOption=select.getFirstSelectedOption().getText().replaceAll("[^\\d]", "");

            try//if it is numerical manipulation
            {
                value= changeTheOptionInDropdown(currentOption,value);
            }
            catch (Exception exception)//if not, catch the exception and work with string value
            {
                exception.printStackTrace();

            }
            finally {
                select.selectByVisibleText(value);
            }

        }
        catch (Exception exception){
            System.out.println("exception = " + exception);
        }

    }





    //========Switching Window=====//
    public static void switchToWindow(String targetTitle) {
        String origin = Driver.getDriver().getWindowHandle();
        for (String handle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(handle);
            if (Driver.getDriver().getTitle().equals(targetTitle)) {
                return;
            }
        }
        Driver.getDriver().switchTo().window(origin);
    }

    //========Hover Over=====//
    public static void hover(WebElement element) {
        Actions actions = new Actions(Driver.getDriver());
        actions.moveToElement(element).perform();
    }

    //==========Return a list of string given a list of Web Element====////
    public static List<String> getElementsText(List<WebElement> list) {
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : list) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //========Returns the Text of the element given an element locator==//
    public static List<String> getElementsText(By locator) {
        List<WebElement> elems = Driver.getDriver().findElements(locator);
        List<String> elemTexts = new ArrayList<>();
        for (WebElement el : elems) {
            if (!el.getText().isEmpty()) {
                elemTexts.add(el.getText());
            }
        }
        return elemTexts;
    }

    //   HARD WAIT WITH THREAD.SLEEP
//   waitFor(5);  => waits for 5 second
    public static void waitFor(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //===============Explicit Wait==============//
    public static WebElement waitForVisibility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForVisibility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForClickablility(WebElement element, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement waitForClickablility(By locator, int timeout) {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }


    public static void clickWithTimeOut(WebElement element, int timeout) {
        for (int i = 0; i < timeout; i++) {
            try {
                element.click();
                return;
            } catch (WebDriverException e) {
                waitFor(1);
            }
        }
    }

    public static void waitForPageToLoad(long timeout) {
        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        };
        try {
            System.out.println("Waiting for page to load...");
            //Explicitly wait
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(timeout));
            wait.until(expectation);
        } catch (Throwable error) {
            System.out.println(
                    "Timeout waiting for Page Load Request to complete after " + timeout + " seconds");
        }
    }

    //======Fluent Wait====//
    public static WebElement fluentWait(final WebElement webElement, int timeout) {
        //FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver()).withTimeout(timeinsec, TimeUnit.SECONDS).pollingEvery(timeinsec, TimeUnit.SECONDS);
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(Driver.getDriver())
                .withTimeout(Duration.ofSeconds(3))//Wait 3 second each time
                .pollingEvery(Duration.ofSeconds(1));//Check for the element every 1 second

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return webElement;
            }
        });

        return element;
    }


    //bu da revize sonrası silinebbilir
    // şimdilik çalışsın diyekoydum
    public static String getScreenshot(String name) throws IOException {
        String date = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);
        String target = SCREENSHOTS_PATH + name + date + ".png";
        File finalDestination = new File(target);
        FileUtils.copyFile(source, finalDestination);
        return target;
    }



}
