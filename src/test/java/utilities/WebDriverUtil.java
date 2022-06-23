package utilities;

import com.google.common.reflect.ClassPath;
import org.openqa.selenium.WebElement;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.stream.Collectors;

import static utilities.ObjectInitializer.classes;
import static utilities.ObjectInitializer.weName;

public class WebDriverUtil
{

    static {
       // classes = getAllPageClasses(ConfigReader.getProperty("PackageNameOfPages"));
        try {
            classes = findAllClassesUsingGoogleGuice(ConfigReader.getProperty("PackageNameOfPages"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T> T getObjectByPageObject(Object page, String element)
    {
        try
        {
            weName=element;//!!!!!!!!

            Class<?> c = page.getClass();
            System.out.println("c = " + c);

            Field f = c.getDeclaredField(element);
            System.out.println("f = " + f);

            f.setAccessible(true);
            T webElement = (T) f.get(page);
            return webElement;

        }catch (Exception exception){
            exception.printStackTrace();
            return null;
        }

    }

    public static <T> T getObject(String element)
    {
        try
        {
            weName=element;//!!!!!!!!


            for(Class c : classes)
            {
                try{
                    Field f = c.getDeclaredField(element);
                    // System.out.println("f = " + f);

                    Object object=null;
                    try {
                        Constructor<?> cons = c.getConstructors()[0];
                        object = cons.newInstance();
                    }
                    catch (Exception exception){
                        exception.printStackTrace();
                    }

                    f.setAccessible(true);
                    T webElement =(T) f.get(object);

                    //if the locator returns a "WebElement", then wait for 5 secs
                    if(webElement instanceof WebElement)
                    {
                        ReusableMethods.waitForVisibility((WebElement) webElement,5);
                    }
                    return webElement;


                }
                catch (Exception e){
                    //e.printStackTrace();// no need to write exception
                }

            }

            return null;

        }
        catch (Exception e)
        {
            System.out.println("e = " + e);
            return null;
        }
    }

    public static Set<Class> findAllClassesUsingGoogleGuice(String packageName) throws IOException
    {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getAllClasses()
                .stream()
                .filter(clazz -> clazz.getPackageName()
                        .equalsIgnoreCase(packageName))
                .map(clazz -> clazz.load())
                .collect(Collectors.toSet());
    }


    //gets all classes of given package in a Set
    public static Set<Class> getAllPageClasses(String packageName) {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        return reflections.getSubTypesOf(Object.class)
                .stream()
                .collect(Collectors.toSet());
    }





}

