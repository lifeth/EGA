/**
 * 
 */
package alife.epimarks;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import alife.epimarks.tests.Stats;
import unalcol.types.real.array.DoubleArray;

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
					
		File experiments = new File("/Users/lifeth/desktop/experiments/binary/extended/steady/");

		//folders generational and steady
		File files[] = experiments.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".txt");
			}
		});
		
		for(File file: files){
			 
			double matrix[][] =  getMatrix(file, false);
			
		/*	for (int k = 0; k < it; k++) {
				System.out.println(k+"="+Arrays.toString(matrix[k]));
				//System.out.println(Arrays.toString(Stats.statistics_with_median(matrix[k]).get()));
			}*/

		  FileWriter plot = new FileWriter((file.getParentFile().getName().equalsIgnoreCase("real")? 
				  file.getParentFile().getParent():file.getParent()) +"/processed/"+file.getName());
					//"/Users/lifeth/desktop/experiments/classic.txt");

		   plot.write("Iteration FMin FMax FMedian FAvg Variance DeStand" + "\n");
			
	       StringBuilder sb = new StringBuilder();
		   // Statistics
		   for (int x = 0; x < 1001; x++) {
			
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
			
	 }
	 
	 public static double[][] getMatrix(File file, boolean minimizing) throws Exception{
		 
			FileInputStream in = new FileInputStream(file);
			Scanner s = new Scanner(in);
			int i = 0;
			int j = 0;
			int it = 1001;
			int runs = 30;
			double matrix[][] = new double[it][runs];

			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] tokens = line.substring(1).trim().split(" ");
				
				matrix[i][j] = Double.parseDouble(tokens[minimizing ? 0 : 1]);//min pos=0, max pos=1
						
				i++;

				if (i == it){
					i = 0;
					j++;
				}
			}
			
			s.close();
			
		return matrix;
	 }
	 
	 public static void dataToCompareAlgosAnova(List <File> files, String name, boolean minimizing) throws Exception {
		
		 ArrayList<File> eas = new ArrayList<>();
		 
		   for (File file : files) {
			   
			   eas.addAll(Arrays.asList(file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().startsWith(name);
					}}))); 			   
			}
         
		   StringBuilder sb = new StringBuilder();
		   double [][] best30 = new double[30][eas.size()]; 
		   double[] column = new double[1001];
		   Collections.sort(eas);
		   
			for (int k = 0; k < eas.size(); k++) {
				File file = eas.get(k);
				double matrix[][] =  getMatrix(file, minimizing);
				
				/*for (int w = 0; w < 1001; w++) {
					System.out.println(w+"="+Arrays.toString(matrix[w]));
				}*/
				
				for (int y = 0; y < 30; y++) {
					
					for (int x = 0; x < 1001; x++) {
						column[x] = matrix[x][y];
					}
					
					DoubleArray.merge(column);
					best30[y][k] = column[minimizing ? 0 : column.length-1];
				}
				
				String replacement = (file.getPath().contains("steady") ? "SS":"G");
				String impl = file.getPath().contains("HAEA") ? "HAEA" : "GA" + 
				file.getName().substring(file.getName().indexOf("X"), file.getName().indexOf("X")+3);
				sb.append((file.getPath().contains("extended") ? "ReGen"+ replacement+impl : replacement+impl) +" ");
				//	"="+Arrays.toString(best30)
			}
			
			 FileWriter plot = new FileWriter("/Users/lifeth/desktop/experiments/DataTestANOVA.txt");
			
			 plot.write(sb.toString()+ "\n");
			 sb.setLength(0);
			 //DoubleArray.merge(best30[1]);
			 for (int x = 0; x < 30; x++) {
					   
				   for (int y = 0; y < eas.size(); y++) {
					   sb.append(best30[x][y] +" ");
				    }
				   
				   sb.append("\n");
				   plot.write(sb.toString());
				   sb.setLength(0);
			   }

			 plot.close();	
		 }
	 
	 public static void dataToCompareAlgosTtest(List <File> files, String name, boolean minimizing) throws Exception {
		
		 ArrayList<File> eas = new ArrayList<>();
		 
		   for (File file : files) {
			   
			   eas.addAll(Arrays.asList(file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().startsWith(name);
					}}))); 			   
			}
         
		   StringBuilder sb = new StringBuilder();
		   double[] column = new double[1001];
		   Collections.sort(eas);
		   
			for (int k = 0; k < eas.size(); k++) {
				File file = eas.get(k);
				double matrix[][] =  getMatrix(file, minimizing);
				
				/*for (int w = 0; w < 1001; w++) {
					System.out.println(w+"="+Arrays.toString(matrix[w]));
				}*/
				
				
				String replacement = (file.getPath().contains("steady") ? "SS":"G");
				String impl = file.getPath().contains("HAEA") ? "HAEA" : "GA" + 
				file.getName().substring(file.getName().indexOf("X"), file.getName().indexOf("X")+3);
				String EA = (file.getPath().contains("extended") ? "ReGen"+ replacement+impl : replacement+impl);
				
				for (int y = 0; y < 30; y++) {
					
					for (int x = 0; x < 1001; x++) {
						column[x] = matrix[x][y];
					}
					
					DoubleArray.merge(column);
					sb.append(EA +" "+column[minimizing ? 0 : column.length-1]+"\n");
				}				
			}
			
			 FileWriter plot = new FileWriter("/Users/lifeth/desktop/experiments/DataTestForTtest.txt");
			
			 plot.write("EAs Fitness\n");
			 plot.write(sb.toString());
			 sb.setLength(0);
			 plot.close();	
		 }
	 
	 public static void dataToCompareAlgosWilcoxTest(List <File> files, String name, boolean minimizing) throws Exception {
				
		 ArrayList<File> eas = new ArrayList<>();
		 
		   for (File file : files) {
			   
			   eas.addAll(Arrays.asList(file.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						return pathname.getName().startsWith(name);
					}}))); 			   
			}
         
		   StringBuilder sb = new StringBuilder();
		   double[] column = new double[1001];
		   Collections.sort(eas);
		   
			for (int k = 0; k < eas.size(); k++) {
				File file = eas.get(k);
				double matrix[][] =  getMatrix(file, minimizing);
				
				/*for (int w = 0; w < 1001; w++) {
					System.out.println(w+"="+Arrays.toString(matrix[w]));
				}*/
				
				
				String replacement = (file.getPath().contains("steady") ? "SS":"G");
				String impl = file.getPath().contains("HAEA") ? "HAEA" : "GA"; 
				String EA = (file.getPath().contains("extended") ? "ReGen"+ replacement+impl : replacement+impl);
				
				for (int y = 0; y < 30; y++) {
					
					for (int x = 0; x < 1001; x++) {
						column[x] = matrix[x][y];
					}
					
					DoubleArray.merge(column);
					sb.append(EA +" "+column[minimizing ? 0 : column.length-1]+"\n");
				}				
			}
			
			 FileWriter plot = new FileWriter("/Users/lifeth/desktop/experiments/DataTestForWilcox.txt");
			
			 plot.write("EAs Fitness\n");
			 plot.write(sb.toString());
			 sb.setLength(0);
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
				
				List<File> files = 
						Arrays.asList(
								/* new File("/Users/lifeth/desktop/experiments/HAEA/classic/steady/"),
								 new File("/Users/lifeth/desktop/experiments/HAEA/extended/steady/"),
								 new File("/Users/lifeth/desktop/experiments/HAEA/classic/generational/"),
								 new File("/Users/lifeth/desktop/experiments/HAEA/extended/generational/")
						
						  new File("/Users/lifeth/desktop/experiments/HAEA/classic/steady/real"),
						  new File("/Users/lifeth/desktop/experiments/HAEA/extended/steady/real"),
						  new File("/Users/lifeth/desktop/experiments/HAEA/classic/generational/real"),
						  new File("/Users/lifeth/desktop/experiments/HAEA/extended/generational/real")
								 
						 new File("/Users/lifeth/desktop/experiments/binary/classic/steady E/"),
						 new File("/Users/lifeth/desktop/experiments/binary/extended/steady/"),
						 new File("/Users/lifeth/desktop/experiments/binary/classic/generational/"),
						 new File("/Users/lifeth/desktop/experiments/binary/extended/generational/")*/
						 
						 new File("/Users/lifeth/desktop/experiments/real/classic/steady E/"),
						 new File("/Users/lifeth/desktop/experiments/real/extended/steady/"),
						 new File("/Users/lifeth/desktop/experiments/real/classic/generational/"),
						 new File("/Users/lifeth/desktop/experiments/real/extended/generational/")
						 	 
			             );
					String name =  "plotSchwefel"; 
					boolean minimizing = true;
						 
				//Utils.dataToCompareAlgosAnova(files, name, minimizing);
				//Utils.dataToCompareAlgosTtest(files, name, minimizing);
				//Utils.dataToCompareAlgosWilcoxTest(files, name, minimizing);		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

}
