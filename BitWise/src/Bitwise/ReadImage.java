package Bitwise;

import java.io.File;

public class ReadImage {

	   public static void main(String[] args) {
		   char value = 'M';
		   System.out.println(binaryPrint(value));
		   char result = turnBitOff(value, 4);
		   System.out.println(binaryPrint(result));
		  
		   

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
		  for (int i = 0; i < 8; i++) {
			sb.append(bitStatus(valueInput, i));
		}
		  return sb.toString();
	  }
}
