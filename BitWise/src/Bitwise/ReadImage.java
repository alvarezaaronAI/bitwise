package Bitwise;

import java.io.File;

public class ReadImage {

	   public static void main(String[] args) {
//		   char value = 'F';
//		   System.out.println( value + " : " + binaryPrint(value));
//		   value = turnBitOff(value, 2);
//		   System.out.println( value + " : " + binaryPrint(value));

		  // System.out.println(result + " : " + binaryPrint(result)); 'I','J','K', 'L'
		   char[] test= { 'A','B','C', 'D','E','F', 'G','H',  
				   		  'z','y','x', 'w','e','f', 'g','h', '\0'};
		   System.out.println(readData(test));
		   

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
		  System.out.println("Result Before : " + binaryPrint(thisValue));
		  char result = (char) (thisValue & onMask(nBitInput));
		  System.out.println("Result After : " + binaryPrint(result));
		return result;
	  }
	  public static char turnBitOff(char thisValue, int nBitInput) {
		  System.out.println("Result Before : " + binaryPrint(thisValue));
		  char result = (char) (thisValue & offMask(nBitInput));
		  System.out.println("Result After : " + binaryPrint(result));
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
		  int index = 8;
		  int pixelCounter = 0;
		  
		  //Create a tempChar and a counter to move along the bits of tempChar
		  char tempChar = 255;
		  System.out.println("Temp Char : " +  binaryPrint(tempChar));
		  binaryPrint(tempChar);
		  //Read Every Character in the Raster.
		  while (pixelsDataInput[pixelCounter] != '\0') {
			char pixel = pixelsDataInput[pixelCounter];
			System.out.println(pixel + " : " + binaryPrint(pixel));
			//modify tempChar
			int bit =  bitStatus(pixel, 1);
			System.out.println("end Bit : "  + bit);
			if(bit == 0) {
				tempChar = turnBitOff(tempChar, index);
			}
			//reset for every 8	
			if(index == 1) {
				//reset index
				index = 8;
				//add tempChar to String Builder
				sb.append(tempChar);
				//reset tempChar to 0
				System.out.println("ReadValue : " + tempChar + " : " + binaryPrint(tempChar));
				tempChar = 255;
				
			}
			else{
				//Just keep modifying each bit
				index--;
			}
			pixelCounter++;
		}
		  System.out.println("index- : " + index);
		  System.out.println("pixelcounter : " + pixelCounter);
		  return sb.toString();
	  }
}
