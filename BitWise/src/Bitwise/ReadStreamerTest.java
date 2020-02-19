package Bitwise;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadStreamerTest {
    public static void main(String[] args) {
        // Using BIS, grab magicNumber, width, height, maxColorValue
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("C:\\PathTest\\cat.ppm"));
            String intial = "";
            int count = 0;
            while (count < 3) {
                char temp = (char) bis.read();
                intial += "" + temp;
                if (temp == '\n') {
                    count++;
                }
            }
            System.out.println(intial);

            // Spilt up the string
            String[] splitted = intial.split("\n");
            String magicNumber = splitted[0];// First two chars, convert Characters to String
            
            String size = splitted[1];
            int width = Integer.parseInt(size.substring(0, size.indexOf(' ')));;// After NL(13) and Return(10)
            int height = Integer.parseInt(size.substring(size.indexOf(' ') + 1, size.length()));// After Space(32)

            int maxColorValue = Integer.parseInt(splitted[2]);;// After NL(13) and Return(10) then after 13 & 10 and the image starts

            // System.out.println(magicNumber + " " + width + " " + height + " " + maxColorValue);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}