package utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;

public class CrossDriver
{
    private CrossDriver(){} // default constructor'i oldurmek icin kendim parametresiz constructor yazdim
    // ve de kimse buna erisemesin, dolayisiyla da obje uretemesin diye access modifier'ini private yaptik
    // artik kimmse Drievr class'indan obje uretemez !!!!!
    public static WebDriver driver; // public yapmazsak diger package'lar csagiramaz

    public static WebDriver getDriver(String browser){ // return type WeDriver, cunku ben bu methodun bana driver vermesini istiyorum
    // buraya parametre olarak yazdigimiz browser sayesinde coklu testler calistirilirken
        // arada cross driver'a yeni browser atamasi yapabilmemize imkan tanimasi icindir

        browser = browser == null ? ConfigReader.getProperty("browser") : browser; // ternary
        // eger ben hic bir browser gondermezsem git meydancidan sistemin browserini getir de calisalim diyor

        if(driver == null){
            switch (browser){
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "opera":
                    WebDriverManager.operadriver().setup();
                    driver = new OperaDriver();
                    break;
                case "safari":
                    WebDriverManager.safaridriver().setup();
                    driver = new SafariDriver();
                    break;
                case "edge":
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                    break;
                default:
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();

            }
        }

        // eger driver'a daha onceden deger atanmamissa, ona new keyword'u ile deger ata,
        // deger atanmis ise dokunma demek
        // boyle yapmazsak methodu yani driver'i her cagirdigimizda yeni bir driver olusmus olur ve
        // islemlerimizi akici sekilde yaptiramayiz
        /*
        Driver.getDriver method'u her calistiginda
        //driver=new ChromeDriver(); komutundan dolayi yeni bir driver olusturuyor
        //Biz Driver class'dan getDriver'i calsitirdigimizda new atamasi olsun
        //sonraki calistirmalarda atama olmasin istiyoruz
        //bunun icin driver= new ChromeDriver(); satiri bir if blogu iicine alacagiz

         */

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        return driver;
    }

    public static void closeDriver(){
        // olusturuken kullandigimiz null mi degil mi kontrolunu burada da kullanmamiz gerekiyor
        // yoksa pes pese calisan testlerde her test sonunda kapatma methodunu kullanirsam,
        // ikinci method driver'i bulamaz, cunku driver null degil, atamasi yapildi ve kapatildi
        // yani kapatilmis olsa da driver'a bu class icerisinde atama yapilmis, dolayisiyla ben
        // 2. test methodunda calistirdigimda new keyword'u kullanilmiyor, eski driver kullanilmaya devam ediliyor
        // ancak onu da kapattigimiz icin 2. test methodunda driver islevsiz hale geliyor
        if(driver != null){
            driver.quit();
        }
        driver = null;

    }

}
