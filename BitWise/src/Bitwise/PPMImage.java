package Bitwise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(this.magicNumber);
            bw.write("" + this.width + " " + this.height + "\n");
            bw.write("" + this.maxColorValue + "\n");
            for (int x = 0; x < raster.length; x++) {
                for (int y = 0; y < raster[x].length; y++) {
                    for (int z = 0; z < 3; z++) {
                        bw.write(this.raster[x][y][z]);
                    }
                }
            }
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

                        // System.out.println(Arrays.toString(this.raster[x][y]));
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

    // Francis Current
    public void hideData(String message) {
        char end = '\0';
        String modMessage = message.concat("" + end);
        for (int count = 0; count < modMessage.length(); count++) {

            char temp0 = modMessage.charAt(count);
            System.out.println("Current on: " + temp0);// Print Current Letter

            int numOfBits = 8; // Determine what nth bit of message to look at
            for (int x = 0; x < this.width; x++) {
                for (int y = 0; y < this.height; y++) {
                    for (int z = 0; z < 3; z++) {
                        int statusMessage = bitStatus(temp0, numOfBits);
                        char rasterTemp = raster[x][y][z];
                        int rightEnd = (char) (rasterTemp & 1);

                        System.out.println("Before Bit Change: " + this.raster[x][y][z]);// Show rasterTemp before change

                        // check the status of the message's char & the right end of the RGB
                        if (rightEnd != statusMessage && statusMessage == 0) {
                            this.raster[x][y][z] = turnBitOn(this.raster[x][y][z], numOfBits);
                            System.out.println("Turn On!");
                        } else if (rightEnd != statusMessage && statusMessage == 1) {
                            this.raster[x][y][z] = turnBitOff(this.raster[x][y][z], numOfBits);
                            System.out.println("Turn Off!");
                        }

                        System.out.println("After Bit Change: " + this.raster[x][y][z]);// Show rasterTemp after change

                        // Decrements the numOfBits, but if it reaches zero, assign 8
                        numOfBits--;
                        if (numOfBits == 0) {
                            numOfBits = 8;
                        }
                        System.out.println("N: " + numOfBits);
                        System.out.println("*********************************************************");
                    }
                }
            }
        }
    }

    public String recoverData() {
        String result = "";
        return result;
    }

    public static void main(String[] args) {
        String fileName = "C:\\PathTest\\cat.ppm";
        File image = new File(fileName);
        PPMImage program = new PPMImage(image);
        program.hideData("Hello");

    }

    private static char onMask(int nBitInput) {
        char mask = (char) (1 << (nBitInput - 1));
        return mask;
    }

    private static char offMask(int nBitInput) {
        char mask = (char) ~(1 << (nBitInput - 1));
        return mask;
    }

    public static char turnBitOn(char thisValue, int nBitInput) {
        char result = (char) (thisValue & onMask(nBitInput));
        return result;
    }

    public static char turnBitOff(char thisValue, int nBitInput) {
        char result = (char) (thisValue & offMask(nBitInput));
        return result;
    }

    public static int bitStatus(char thisValue, int nBitInput) {
        char result = (char) (thisValue & onMask(nBitInput));
        boolean isBitOn = result > 0;
        boolean isBitOff = result == 0;
        if (isBitOn) {
            return 1;
        } else if (isBitOff) {
            return 0;
        } else {
            return -1;
        }
    }

    public static String binaryPrint(char valueInput) {
        StringBuilder sb = new StringBuilder();
        for (int i = 8; i > 0; i--) {
            sb.append(bitStatus(valueInput, i));
        }
        return sb.toString();
    }
}
/**
 * char temp = 'M'; char mask = 1 << (1 - 1); System.out.println("M: " +
 * (int)temp); // Check status of the first bit of M int status = (temp & mask);
 * System.out.println("The First bit of M: " + " " + status);
 * 
 * // Value of end right of RGB int rightEnd1 = 'A' & 1;
 * System.out.println("Value of A: " + (int)'A');
 * 
 * System.out.println("Value of end right of RGB(A): " + rightEnd1);
 * 
 * // Compage Right End with the first bit of M if (rightEnd1 != status) { //
 * Change it to result's binary number char mask2 = (char) (status << (1 - 1));
 * char newRightEnd = (char) (rightEnd1 | mask2); System.out.println("Changed
 * the RGB VALUE: " + " " + newRightEnd); } else { //So if they are the same no
 * Change System.out.println("No Change"); }
 */