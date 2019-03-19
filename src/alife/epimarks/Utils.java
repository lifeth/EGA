/**
 * 
 */
package alife.epimarks;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import unalcol.types.integer.IntUtil;

/**
 * @author lifeth
 *
 */
public class Utils {

	private static Random r = new Random();

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
		long range = (long) aEnd - (long) aStart + 1;
		// compute a fraction of the range, 0 <= frac < range
		long fraction = (long) (range * r.nextDouble());
		return (int) (fraction + aStart);
	}

	public static void timer(int secs) {

		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			int i = secs;

			public void run() {
				System.out.println(i--);
				if (i < 0)
					timer.cancel();
			}
		}, 0, 1000);
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

	public static void computeResults() throws Exception {

		FileInputStream in = new FileInputStream(
				new File("/Users/lifeth/Google Drive UNAL/UNACIONAL/TESIS/EXPERIMENTOS/extended/GRR2.txt"));

		Scanner s = new Scanner(in);
		int i = 0;
		int it = 1001;
		int col = 6;
		double statictics[][] = new double[it][6];

		while (s.hasNextLine()) {
			String line = s.nextLine();
			String[] tokens = line.split(" ");
			if(tokens.length>col){
				tokens = line.substring(1).split(" ");
			}
			for (int y = 0; y < col; y++) {
				statictics[i][y] += Double.parseDouble(tokens[y]);
			}

			i++;

			if (i == it)
				i = 0;
		}

		s.close();

		FileWriter plot = new FileWriter(
				"/Users/lifeth/Google Drive UNAL/UNACIONAL/TESIS/EXPERIMENTOS/extended/PlotGRR2.txt");

		plot.write("Iteration FWorst FBest FMedian FAvg Variance DeStand" + "\n");

		// Statistics
		for (int x = 0; x < it; x++) {
			for (int j = 0; j < col; j++) {
				statictics[x][j] /= 30;
			}

			plot.write((x + 1) + " " +  Math.rint(statictics[x][0]) + " " + Math.rint(statictics[x][1]) + " " + Math.rint(statictics[x][2]) + " "
					+ Math.rint(statictics[x][3]) + " " + statictics[x][4] + " " + statictics[x][5] + "\n");
		}

		plot.close();
	}
}
