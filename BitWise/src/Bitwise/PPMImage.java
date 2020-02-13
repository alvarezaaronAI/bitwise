package Bitwise;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

public class PPMImage {
    String magicNumber;// First two chars, convert Characters to String
    int width;// After NL(13) and Return(10)
    int height;// After Space(32)
    int maxColorValue;// After NL(13) and Return(10) then after 13 & 10 and the image starts
    char[][][] raster;

    public PPMImage(File image) {
        try {
            readImage(image);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void writeImage(File fileName) {

    }

    // Read file image, should be called be the constructor
    private void readImage(File image) throws IOException {
        try {

            BufferedReader br = new BufferedReader(new FileReader(image));// Reads the file

            this.magicNumber = br.readLine();// Magic Number

            String size = br.readLine();
            String width = size.substring(0, size.indexOf(' ')); // width
            String height = size.substring(size.indexOf(' ') + 1, size.length());// height
            this.width = Integer.parseInt(width);
            this.height = Integer.parseInt(height);

            this.maxColorValue = Integer.parseInt(br.readLine()); // Max Color Value

            // System.out.println(this.magicNumber + " " + this.width + " " + this.height +
            // " " + this.maxColorValue);

            this.raster = new char[this.width][this.height][3]; // Raster

            // int c;
            // while((c = br.read()) > 0) {//While there is still text in the file, it will
            // read the file character by character

            // System.out.print(c + " ");
            // Character string = new Character((char)c); //Make the int to char to String
            // System.out.println(string.toString()); //Prints out the String
            // }
            for (int x = 0; x < raster.length; x++) {
                for (int y = 0; y < raster[x].length; y++) {
                    try {
                        char temp1 = (char) br.read();
                        char temp2 = (char) br.read();
                        char temp3 = (char) br.read();
                        this.raster[x][y][0] = temp1;
                        this.raster[x][y][1] = temp2;
                        this.raster[x][y][2] = temp3;

                        System.out.println(Arrays.toString(this.raster[x][y]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            br.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    public void hideDate(String message) {

    }

    public String recoverDate() {
        String result = "";
        return result;
    }

    public static void main(String[] args) {
        String fileName = "C:\\PathTest\\cat.ppm";
        File image = new File(fileName);
        //PPMImage program = new PPMImage(image);
        char one = 1;
        char temp = 'A';
        char result = (char) (one & temp);
        System.out.println(result + "");
    }
}