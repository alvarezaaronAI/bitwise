package Bitwise;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

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

    // Try printing it char by char, so combine the atrributes to a string then
    // print them like that
    // Buffered outputstream!!!
    public void writeImage(File fileName) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName));
            // BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            String intital = (this.magicNumber + "\n" + this.width + " " + this.height + "\n" + this.maxColorValue
                    + "\n");
            // bw.write(this.magicNumber +"\n");
            // bw.write(this.width + " " + this.height + "\n");
            // bw.write(this.maxColorValue + "\n");
            for (int count = 0; count < intital.length(); count++) {
                char temp = intital.charAt(count);
                bos.write(temp);
                // bw.write(temp);
            }
            for (int x = 0; x < raster.length; x++) {
                for (int y = 0; y < raster[x].length; y++) {
                    for (int z = 0; z < 3; z++) {
                        bos.write(this.raster[x][y][z]);
                        // bw.write(this.raster[x][y][z]);
                    }
                }
            }
            bos.close();
            // bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // Read file image, should be called be the constructor
    private void readImage(File image) throws IOException {
        try {
            // Using BIS, grab magicNumber, width, height, maxColorValue
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(image));

            String intial = "";
            int count = 0;
            while (count < 3) {
                char temp = (char) bis.read();
                intial += "" + temp;
                if (temp == '\n') {
                    count++;
                }
            }
            // System.out.println(intial);

            // Spilt up the string
            String[] splitted = intial.split("\n");
            this.magicNumber = splitted[0];// First two chars, convert Characters to String

            String size = splitted[1];
            this.width = Integer.parseInt(size.substring(0, size.indexOf(' ')));// After NL(13) and Return(10)
            this.height = Integer.parseInt(size.substring(size.indexOf(' ') + 1, size.length()));// After Space(32)

            this.maxColorValue = Integer.parseInt(splitted[2]);// After NL(13) and Return(10) then after 13 & 10 and the
                                                               // image starts

            // System.out.println(magicNumber + " " + width + " " + height + " " +
            // maxColorValue);
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
                        char temp1 = (char) bis.read();
                        char temp2 = (char) bis.read();
                        char temp3 = (char) bis.read();
                        this.raster[x][y][0] = temp1;
                        this.raster[x][y][1] = temp2;
                        this.raster[x][y][2] = temp3;

                        // System.out.println(Arrays.toString(this.raster[x][y]));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            bis.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void hideData(String message) {
        // Add -> '\0'
        StringBuilder sb = new StringBuilder(message);
        sb.append('\0');
        message = sb.toString();

        // Counter/TempChar
        int messageLength = message.length();
        System.out.println("Message Length : " + messageLength);
        int currentLetter = 0;
        int index = 8;

        // Loop: Raster -> '\0'
        OUTER_LOOP: for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < 3; z++) {
                    char letter = message.charAt(currentLetter);
                    // System.out.println(
                    // "Letter : " + letter + " - Index : " + index + " - Current Letter : " +
                    // currentLetter);

                    char subPixel = raster[x][y][z];
                    // System.out.print(binaryPrint(subPixel) + ": ");

                    int bit = bitStatus(subPixel, 1);
                    int status = bitStatus(letter, index);
                    // System.out.println("bit : " + bit + " - status : " + status);
                    // Added

                    boolean matching = (status == bit);
                    // System.out.println(matching);
                    if (!matching) {
                        if (status == 1) {
                            // turn on bit
                            // System.out.println("TurningBitOn B : " + binaryPrint(subPixel));
                            subPixel = turnBitOn(subPixel, 1);

                            if (bitStatus(subPixel, 1) == 0) {
                                // it didnt turn on use exclusive
                                subPixel = excluiveTurnBitOn(subPixel, 1);
                            }
                            // System.out.println("TurningBitOn A: " + binaryPrint(subPixel));
                        } else {
                            // turn off
                            // System.out.println("TurningBitOFF B : " + binaryPrint(subPixel));
                            subPixel = turnBitOff(subPixel, 1);
                            if (bitStatus(subPixel, 1) == 1) {
                                // it didnt turn off use exclusive or
                                subPixel = excluiveTurnBitOff(subPixel, 1);
                            }
                            // System.out.println("TurningBitOff A : " + binaryPrint(subPixel));

                        }
                        // modify raster using modified subPixel

                        // System.out.println("Raster : " + raster[x][y][z]);
                        // System.out.println("-----");
                    }
                    raster[x][y][z] = subPixel;

                    // reset for every 8
                    if (index == 1) {
                        if (letter == '\0') {
                            System.out.println("Break");
                            break OUTER_LOOP;
                        }
                        // Reset: Counters/Add
                        index = 8;
                        // Next Letter
                        currentLetter++;

                    } else {
                        index--;
                    }

                }
            }
        }
        // System.out.println("--------");
        // // printTest();
        // System.out.println("--------");
        // System.out.println(recoverData());
    }

    public String recoverData() {
        // Store : Letters
        StringBuilder sb = new StringBuilder();
        // Counter/TempChar
        int index = 8;
        char tempChar = 255;
        // Loop: Raster -> '\0'
        OUTER_LOOP: for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < 3; z++) {
                    char subPixel = raster[x][y][z];
                    // System.out.print(binaryPrint(subPixel) + ": ");
                    int bit = bitStatus(subPixel, 1);
                    // System.out.println(bit);
                    if (bit == 0) {
                        tempChar = turnBitOff(tempChar, index);
                    }
                    // reset for every 8
                    if (index == 1) {
                        // System.out.println("ReadValue A: " + tempChar + " : " +
                        // binaryPrint(tempChar));
                        // check if its a null value
                        if ((tempChar == '\0')) {
                            // System.out.println("break");
                            break OUTER_LOOP;
                        }
                        // Add: Letter Created
                        // System.out.println("" + tempChar);
                        sb.append(tempChar);
                        // Reset: Counters/Temp
                        index = 8;
                        tempChar = 255;

                    } else {
                        index--;
                    }
                }
            }
        }
        return sb.toString();
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
        // System.out.println("Result Before : " + binaryPrint(thisValue));
        char result = (char) (thisValue & onMask(nBitInput));
        // System.out.println("Result After : " + binaryPrint(result));
        return result;
    }

    public static char excluiveTurnBitOn(char thisValue, int nBitInput) {
        // System.out.println("Result Before : " + binaryPrint(thisValue));
        char result = (char) (thisValue ^ onMask(nBitInput));
        // System.out.println("Result After : " + binaryPrint(result));
        return result;
    }

    public static char turnBitOff(char thisValue, int nBitInput) {
        // System.out.println("Result Before : " + binaryPrint(thisValue));
        char result = (char) (thisValue & offMask(nBitInput));
        // System.out.println("Result After : " + binaryPrint(result));
        return result;
    }

    public static char excluiveTurnBitOff(char thisValue, int nBitInput) {
        // System.out.println("Result Before : " + binaryPrint(thisValue));
        char result = (char) (thisValue ^ offMask(nBitInput));
        // System.out.println("Result After : " + binaryPrint(result));
        return result;
    }

    public static int bitStatus(char thisValue, int nBitInput) {
        int result = (char) (thisValue & onMask(nBitInput));

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
            int bit = bitStatus(valueInput, i);
            sb.append(bit);
        }
        return sb.toString();
    }

    // Main
    public static void main(String[] args) {
        PPMImage program;
        System.out.println(
                "Would would you like to do? \n" + "A.) Hide Message \n" + "B.) Recover Message \n" + "C.) Exit");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println("Your Selection Was: " + input);

        // While Loop with the switch case
        while (!(input.equals("C"))) {
            switch (input) {
                case "A":
                    System.out.println("Please Specify the Source PPM filename: ");
                    String fileName = in.nextLine();
                    File inFile = new File(fileName);

                    System.out.println("Please Specify the Output filename: ");
                    String outputName = in.nextLine();
                    File outFile = new File(outputName);

                    System.out.println("Please Enter a phrase to hide: ");
                    String phrase = in.nextLine();
                    System.out.println("Your Message Was: " + phrase);

                    program = new PPMImage(inFile);
                    program.hideData(phrase);
                    program.writeImage(outFile);
                    break;

                case "B":
                    System.out.println("Please Specify the Source PPM filename to recover: ");
                    String sourceString = in.nextLine();
                    File sourceFile = new File(sourceString);
                    program = new PPMImage(sourceFile);

                    System.out.println("The following message has been recovered from " + sourceString + ": ");
                    String decoded = program.recoverData();
                    System.out.println(decoded);
                    break;

                case "C":
                    break;
            }
            System.out.println();
            System.out.println(
                    "Would would you like to do? \n" + "A.) Hide Message \n" + "B.) Recover Message \n" + "C.) Exit");
            input = in.nextLine();
        }
        in.close();
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
 * 
 */