package utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToTxt
{
    public BufferedWriter writeToTxt(String path,String... addData)
    {
        try {
            File fileObject = new File(path);
            FileWriter fileWriter;
            BufferedWriter writer;

            System.out.println("fileObject.getName() = " + fileObject.getName());

            if (fileObject.createNewFile()) {
                // true ise : dosya basarıyla olusturuldu
                // dosyaya yazma yapabiliriz
                fileWriter = new FileWriter(path);
                writer = new BufferedWriter(fileWriter);
                for (String each: addData)
                {
                    writer.write(each);
                    //writer.newLine();
                }
                writer.newLine();

                writer.close();

                return writer;

            } else {
                // false ise: dosya zaten var
                // dosyanın icini temizleyip yeniden yazmak istersen append = false
                // dosyanın icini silmeden yazmaya devam etmek istersen append = true

                // writer = new FileWriter(path,false); // --> Dosyayi temizleyerek yazacak
                // writer = new FileWriter(path); // append icin true ya da false vermezsen default olarak false aliyor
                fileWriter = new FileWriter(path,true); // --> Dosyayi temzilemeyecek ve yanından yazmaya devam edecek
                writer = new BufferedWriter(fileWriter);
                for (String each: addData)
                {
                    writer.write(each);
                   // writer.newLine();
                }
                writer.newLine();
                writer.close();

                return writer;
            }
        } catch (IOException e) {
            // hata olustuysa, logla
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }
}
