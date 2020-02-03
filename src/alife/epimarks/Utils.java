/**
 * 
 */
package alife.epimarks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import alife.epimarks.tests.Stats;

/**
 * @author lifeth
 *
 */
public class Utils {

	/**
	 * 
	 * Converts a natural binary 64 bit value to Gray code.
	 * 
	 * @param natural
	 *            The natural binary value to convert.
	 * 
	 * @return The Gray code equivalent value.
	 * 
	 */

	public static long encodeGray(long natural) {

		return natural ^ natural >>> 1;
	}

	/**
	 * 
	 * Converts a Gray code 64 bit value to natural binary.
	 * 
	 * @param gray
	 *            The Gray code value to convert.
	 * 
	 * @return The natural binary equivalent value.
	 * 
	 */

	public static int decodeGray(long gray) {

		int natural = 0;

		while (gray != 0) {

			natural ^= gray;

			gray >>>= 1;
		}

		return natural;
	}

	public static int next(int aStart, int aEnd) {
		Random r = new Random();
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * r.nextDouble());
		return (int) (fraction + aStart);
	}
	
	public static int BITS = 32;
	
	public static double[] decode(String bits, double min, double max){
		
	  int index = 0;
	  
	  int endIndex = BITS;
	  
	  double accuracy = (max - min) / (Math.pow(2, BITS) - 1);
	  
	  double[] genome = new double[bits.length()/BITS];
	  
	  for (int i = 0; i < genome.length; i++) {
		  genome[i] = min + Utils.binaryToInt(bits.substring(index, endIndex)) * accuracy;
		  index = endIndex;
		  endIndex +=BITS;
	  }

	  return genome;
	}
	
	public static int[] encode(double[] real, double min, double max){
		
		int index = 0;
		
		int[] genome = new int[real.length * BITS];
		 
		for (int i = 0; i < real.length; i++) {
			  
			 System.arraycopy(Utils.encode(real[i], min, max), 0, genome, index, BITS);
			 index +=BITS; 
		 }

		return genome;
	}
	
	public static int[] encode(double real, double min, double max){
		
		return Utils.intToBinary( Utils.doubleToInt(real, min, max));
	}
	
	public static boolean[] encodeBool(double[] real, double min, double max){
		
		  int index = 0;
		  
		  boolean[] genome = new boolean[real.length * BITS];
		 
		  for (int i = 0; i < real.length; i++) {
			  
			  System.arraycopy(Utils.encodeBool(real[i], min, max), 0, genome, index, BITS);
			  index +=BITS; 
		  }

		  return genome;
	}
	
    public static boolean[] encodeBool(double real, double min, double max){
		
		return Utils.intToBinaryBool( Utils.doubleToInt(real, min, max));
	}
	
	
	public static long doubleToInt(double real, double min, double max){
		
		return Math.round((Math.pow(2, BITS) - 1) * (real - min)/(max - min));
	}
	
	public static long binaryToInt(String bits){

		return Long.parseLong(bits, 2);
	}
	 
	 public static int[] intToBinary(long n) 
	  { 
		 int[] binary = new int [BITS];

		 int j = 0;
		 
		 for (int i = BITS-1; i >= 0; i--) { 
            long k = n >> i; 
			binary[j] = (k & 1) > 0 ? 1 : 0;
			j++;
		 }     
	     return binary;
	   }
	 
	 public static boolean[] intToBinaryBool(long n) 
	  { 
		 boolean[] binary = new boolean [BITS];
		 
		 int j = 0;
		 
	     for (int i = BITS-1; i >= 0; i--) { 
	            long k = n >> i; 
				binary[j] = (k & 1) > 0;
				j++;
	      } 
	        
	        return binary;
	   }
	 
	 public static void computeResults() throws Exception {
			
	        String GA = "extended";
			String selection = "generational";
			String file = "plotRR-X06M00MK002.txt";
			
			FileInputStream in = new FileInputStream(
					//new File("/Users/lifeth/desktop/experiments/binary/"+GA+"/"+selection+"/"+file));
					new File("/Users/lifeth/desktop/experiments/Real/schwefel.txt"));
			
			Scanner s = new Scanner(in);
			int i = 0;
			int j = 0;
			int it = 1001;
			int runs = 30;
			double matrix[][] = new double[it][runs];

			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] tokens = line.substring(1).trim().split(" ");
				
				matrix[i][j] += Double.parseDouble(tokens[0]);//min 0, max 1
						
				i++;

				if (i == it){
					i = 0;
					j++;
				}
			}

			s.close();
			
		/*	for (int k = 0; k < it; k++) {
				System.out.println(k+"="+Arrays.toString(matrix[k]));
				//System.out.println(Arrays.toString(Stats.statistics_with_median(matrix[k]).get()));
			}*/

		  FileWriter plot = new FileWriter(
					//"/Users/lifeth/desktop/experiments/binary/"+GA+"/"+selection+"/processed/"+file);
					"/Users/lifeth/desktop/experiments/classic.txt");

		   plot.write("Iteration FMin FMax FMedian FAvg Variance DeStand" + "\n");
			
	       StringBuilder sb = new StringBuilder();
		   // Statistics
		   for (int x = 0; x < it; x++) {
			
			  sb.append((x + 1));
			  double[] stats = Stats.statistics_with_median(matrix[x]).get();	
				   
			   for (int y = 0; y < stats.length; y++) {
				   sb.append(" "+stats[y]);
			    }
			   
			   sb.append("\n");
			   plot.write(sb.toString());
			   sb.setLength(0);
		   }

			plot.close();
		}

		public static void main(String[] args) {
			/*
			 * System.out.println(String.format("%04d",
			 * Integer.parseInt(Integer.toBinaryString(7))));
			 * System.out.println(Integer.toBinaryString(7));
			 * System.out.println(Integer.parseInt("111", 2));
			 * System.out.println(Utils.decodeGray(7));
			 * System.out.println(Utils.encodeGray(5));
			 * System.out.println(IntUtil.grayToBinary(5));
			 * System.out.println(IntUtil.binaryToGray(5));
			 * 
			 * System.out.println("Nº: " + Utils.decodeGray(Integer.parseInt("111",
			 * 2)));
			 * 
			 * Utils.timer(10)
			 */;
			try {
				Utils.computeResults();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
