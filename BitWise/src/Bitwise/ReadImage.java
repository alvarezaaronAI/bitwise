package Bitwise;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ReadImage {

	String magicNumber;// First two chars, convert Characters to String
	int width;// After NL(13) and Return(10)
	int height;// After Space(32)
	int maxColorValue;// After NL(13) and Return(10) then after 13 & 10 and the image starts
	char[][][] raster;

	public ReadImage(File image) {
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
					System.out.println(
							"Letter : " + letter + " - Index : " + index + " - Current Letter : " + currentLetter);
					char subPixel = raster[x][y][z];
					System.out.print(binaryPrint(subPixel) + ": ");
					int bit = bitStatus(subPixel, 1);
					int status = bitStatus(letter, index);
					System.out.println("bit : " + bit + " - status : " + status);
					// Added
					boolean matching = (status == bit);
					System.out.println(matching);
					if (!matching) {
						if (status == 1) {
							// turn on bit
							System.out.println("TurningBitOn B : " + binaryPrint(subPixel));
							subPixel = turnBitOn(subPixel, index);

							if (bitStatus(subPixel, 1) == 0) {
								// it didnt turn on use exclusive
								subPixel = excluiveTurnBitOn(subPixel, 1);
							}
							System.out.println("TurningBitOn A: " + binaryPrint(subPixel));
						} else {
							// turn off
							System.out.println("TurningBitOFF B : " + binaryPrint(subPixel));
							subPixel = turnBitOff(subPixel, index);
							if (bitStatus(subPixel, 1) == 1) {
								// it didnt turn off use exclusive or
								subPixel = excluiveTurnBitOff(subPixel, 1);
							}
							System.out.println("TurningBitOff A : " + binaryPrint(subPixel));

						}
						// modify raster using modified subPixel
						raster[x][y][z] = subPixel;
						System.out.println("Raster : " + raster[x][y][z]);
						System.out.println("-----");
					}

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
		System.out.println("--------");
		// printTest();
		System.out.println("--------");
		System.out.println(recoverData());
	}

	public void printTest() {
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				for (int z = 0; z < 3; z++) {
					char subPixel = raster[x][y][z];
					System.out.print(binaryPrint(subPixel) + " ");
				}
				System.out.println("");
			}
		}
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
					System.out.print(binaryPrint(subPixel) + ": ");
					int bit = bitStatus(subPixel, 1);
					System.out.println(bit);
					if (bit == 0) {
						tempChar = turnBitOff(tempChar, index);
					}
					// reset for every 8
					if (index == 1) {
						System.out.println("ReadValue A: " + tempChar + " : " + binaryPrint(tempChar));
						// check if its a null value
						if ((tempChar == '\0')) {
							System.out.println("break");
							break OUTER_LOOP;
						}
						// Add: Letter Created
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

	public String decode() {
		StringBuilder sb = new StringBuilder();
		char adding;
		OUTER_LOOP: for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				for (int z = 0; z < 3; z++) {
					char temp = this.raster[x][y][z];
					bitStatus(temp, 1);
				}
			}
		}		

		return sb.toString();
	}

	public static void main(String[] args) {
		String blackEncoded = "C:\\PathTest\\EncodeMessages\\black_encoded.ppm";
		String black = "C:\\PathTest\\black.ppm";
		File image = new File(black);
		// File image = new File(blackEncoded);
		ReadImage program = new ReadImage(image);
		program.hideData("Hello World");
		System.out.println(program.recoverData());
	}
}
