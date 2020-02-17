package Bitwise;

import java.io.File;

public class ReadImage {

	   public static void main(String[] args) {
		   char value = 'F';
		   System.out.println( value + " : " + binaryPrint(value));
		   char result = turnBitOff(value, 2);
		   System.out.println(result + " : " + binaryPrint(result));
		   //char[] test= { 'A','B','C', 'D','E','F', 'G','H','I', 'J','K', 'L' , '\0'};
		   //readData(test);
		   

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
		int result = (char) (thisValue & onMask(nBitInput));
		
		boolean isBitOn = result > 0;
		boolean isBitOff = result == 0;
		if(isBitOn) {
			return 1;
		}
		else if(isBitOff) {
			return 0;
		}
		else {
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
	  
	  //read data
	  public static String readData(char[] pixelsDataInput) {
		  StringBuilder sb = new StringBuilder();
		  int index = 0;
		  
		  //Create a tempChar and a counter to move along the bits of tempChar
		  char tempchar = '0';
		  binaryPrint(tempchar);
		  //Read Every Character in the Raster.
		  while (pixelsDataInput[index] != '\0') {
			char pixel = pixelsDataInput[index];
			System.out.println(pixel + " : " + binaryPrint(pixel));
			index++;
		}
		  return sb.toString();
	  }
}
