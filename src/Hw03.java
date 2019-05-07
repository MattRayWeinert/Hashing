import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Hw03 {

	public static void main(String[] args) throws IOException {
		System.err.println("NID: 422842; 4.0 ; 7.0");
		int hashValue = 0;
		BufferedReader bufferedReader = new BufferedReader(new FileReader(args[0]));
		String input = bufferedReader.readLine();
		while(input != null)
		{
			int length = input.length();
			hashValue = UCFxram(input, length);
			System.out.format("%10x:%s\n", hashValue, input);
			input = bufferedReader.readLine();
		}
		System.out.println("Input file processed");
		bufferedReader.close();
	}
	
	public static int UCFxram(String input, int length)
	{
		int randVal1 = 0xbcde98ef;
		int randVal2 = 0x7890face;
		int hashValue = 0xfa01bc96;
		int roundedEnd = length & 0xfffffffc;
		
		for(int i = 0; i < roundedEnd; i = i+4)
		{
						int tempData = (input.charAt(i) & 0xff) | ((input.charAt(i + 1) & 0xff) << 8) | ((input.charAt(i+2) & 0xff) << 16) | (input.charAt(i+3) << 24);

			tempData = tempData * randVal1;
			tempData = Integer.rotateLeft(tempData ,  12);
			tempData = tempData * randVal2;
			hashValue = hashValue ^ tempData;
			hashValue = Integer.rotateLeft(hashValue, 13);
			hashValue = hashValue * 5 + 0x46b6456e;
		}
		
		int tempData = 0;
		if((length & 0x03) == 3)
		{
			tempData = (input.charAt(roundedEnd + 2) & 0xff) << 16;
			length = length - 1;
		}
		
		if((length & 0x03) == 2)
		{
			tempData |= (input.charAt(roundedEnd + 1) & 0xff) << 8;
			length = length - 1;
		}
		
		if((length & 0x03) == 1)
		{
			tempData |= (input.charAt(roundedEnd) & 0xff);
			tempData = tempData * randVal1;
			tempData = Integer.rotateLeft(tempData, 14);
			tempData = tempData * randVal2;
			hashValue = hashValue ^ tempData;
		}
		
		hashValue = hashValue ^ length;
		hashValue = hashValue & 0xb6acbe58;
		hashValue = hashValue ^ hashValue >>> 13;
		hashValue = hashValue * 0x53ea2b2c;
		hashValue = hashValue ^ hashValue >>> 16;
		return hashValue;
	}
}
