/**
 * 
 */
package alife.epimarks.tests;

import java.io.FileWriter;
import alife.epimarks.function.Deceptive;
import alife.epimarks.function.Deceptive_;
import alife.epimarks.function.MaxOnes;
import alife.epimarks.function.RoyalRoad;
import alife.epimarks.operator.BitMutation;
import alife.epimarks.operator.XOver;
import alife.epimarks.types.MarkedBitArray;
import unalcol.descriptors.Descriptors;
import unalcol.descriptors.WriteDescriptors;
import unalcol.io.Write;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.OptimizationGoal;
import unalcol.random.util.RandBool;
import unalcol.search.Goal;
import unalcol.search.population.Population;
import unalcol.search.population.PopulationDescriptors;
import unalcol.search.population.PopulationSearch;
import unalcol.search.selection.Selection;
import unalcol.search.selection.Tournament;
import unalcol.search.solution.Solution;
import unalcol.search.space.Space;
import unalcol.search.variation.Variation_1_1;
import unalcol.tracer.ConsoleTracer;
import unalcol.tracer.FileTracer;
import unalcol.tracer.Tracer;
import unalcol.types.collection.bitarray.BitArray;
import unalcol.types.real.array.DoubleArray;
import unalcol.types.real.array.DoubleArrayPlainWrite;

/**
 * @author lifeth
 *
 */
public class GATest {
	
	public static int MAXITERS = 1000;
	public static int POPSIZE = 100;
	public static double resultsByIte[][] = new double[MAXITERS][6];

	@SuppressWarnings("unchecked")
	public static void evolveGA() {
       
		//Optimization Function
		OptimizationFunction<BitArray> function = new Deceptive().new Classic();
		//OptimizationFunction<BitArray> function = new Deceptive_().new Classic();
	    //OptimizationFunction<BitArray> function = new MaxOnes().new Classic();
		//OptimizationFunction<BitArray> function = new RoyalRoad().new Classic();

		Goal<BitArray, Double> goal = new OptimizationGoal<>(function, false);
		Solution<BitArray>[] pop = new Solution[POPSIZE];

		// Initial Population
		for (int i = 0; i < POPSIZE; i++) {
			// Aptitude Calculation
			pop[i] = new Solution<>(new BitArray(300, true), goal);
		}

		// Operators
		Selection<BitArray> pselection = new Tournament<>(4);// Parent selection
		unalcol.optimization.binary.XOver xover = new unalcol.optimization.binary.XOver();
		unalcol.optimization.binary.BitMutation mutation = new unalcol.optimization.binary.BitMutation();
		String gName = Goal.class.getName();
		RandBool generator = new RandBool( 1.0 - 0.7 );

		for (int i = 0; i < MAXITERS; i++) {

			System.out.println("\n==================Iteration " + i + "===================");
			
			Solution<BitArray>[] parents = pselection.pick(pop.length, pop);
			pop = new Solution[POPSIZE];
			
			double fitness[] = new double [POPSIZE];
			
	        for (int j = 0; j < parents.length; j += 2) {
	            
	            Solution<BitArray>[] offspring;
	            
	            if (generator.next()) {
	            	offspring = mutation.apply(xover.apply(new Solution[]{parents[j], parents[j+1]}));
	            } else {
	            	offspring = new Solution[2];
	                offspring[0] = parents[j];
	                offspring[1] = parents[j+1];
	            }
	            
	            fitness[j] = (double) offspring[0].info(gName);
				fitness[j + 1] = (double) offspring[1].info(gName);
				
				pop[j] = offspring[0];
				pop[j + 1] = offspring[1];
	        }
			
	         calculateStatistics(i, fitness);

			/*for (int j = 0; j < pop.length; j++) {
				System.out.println(pop[j].object().toString() + ":" + pop[j].info(gName));
			}*/
		}
	}
	
    public static void evolveEGA(){
        // Search Space definition
        int DIM = 320;
        Space<MarkedBitArray> space = new alife.epimarks.tests.BinarySpace( DIM );
        
        // Optimization Function
 	   //OptimizationFunction<MarkedBitArray> function = new Deceptive().new Extended();
 	   //OptimizationFunction<MarkedBitArray> function = new Deceptive_().new Extended();
 	   OptimizationFunction<MarkedBitArray> function = new RoyalRoad().new Extended();
       //OptimizationFunction<MarkedBitArray> function = new MaxOnes().new Extended();        
        Goal<MarkedBitArray,Double> goal = new OptimizationGoal<MarkedBitArray>(function, false);     
        
        // Variation definition
        Variation_1_1<MarkedBitArray> mutation = new BitMutation();
        XOver xover = new XOver();

        EAFactory<MarkedBitArray> factory = new EAFactory<MarkedBitArray>();
        PopulationSearch<MarkedBitArray,Double> search = 
                factory.generational_ga(POPSIZE, new Tournament<MarkedBitArray>(4), mutation, xover, 0.7, MAXITERS );

        // Tracking the goal evaluations
        WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(Population.class, write_desc);
        Descriptors.set(Population.class, new PopulationDescriptors<MarkedBitArray>());
        
        //FileTracer tracer = new FileTracer("/Users/lifeth/Desktop/plotMO.txt");   
        ConsoleTracer tracer = new ConsoleTracer();       

        //Tracer.addTracer(goal, tracer); 
        Tracer.addTracer(search, tracer);
        
        // Apply the search method        
        Solution<MarkedBitArray> solution = search.solve(space, goal);
        
        System.out.println(solution.object()+": "+solution.info(Goal.class.getName()));
        System.out.println(solution.object().toStringTags());
    }

	public static void calculateStatistics(int i, double fitness[]) {

		double[] data = DoubleArray.statistics_with_median(fitness).get();
		resultsByIte[i][0] += data[0];// min
		resultsByIte[i][1] += data[1];// max
		resultsByIte[i][2] += data[2];// median
		resultsByIte[i][3] += data[3];// avg
		resultsByIte[i][4] += data[4];// variance
		resultsByIte[i][5] += data[5];// stand deviation
		/*
		//Deviation with Median
		int n = fitness.length;
		double variance = 0;
		for (int k = 0; k < n; k++) {
			variance += (fitness[k] - data[2]) * (fitness[k] - data[2]);
		}
		variance /= (n > 1) ? (n - 1) : 1.0;	
		resultsByIte[i][4] += variance;// variance
		double deviation = Math.sqrt(variance);
		resultsByIte[i][5] += deviation;// stand deviation*/
	}
	
	public void perform() throws Exception {

		int n = 30;

		for (int i = 0; i < n; i++) {
			//evolveGA();
			evolveEGA();
		}

		FileWriter plot = new FileWriter("/Users/lifeth/Desktop/extended/plotMO.csv");
		
		plot.write("Iteration, FWorst, FBest, FMedian, FAvg, Variance, DeStand" + "\n");

		// Statistics
		for (int i = 0; i < MAXITERS; i++) {

			for (int j = 0; j < 6; j++) {
				resultsByIte[i][j] /= n;
			}
			
			plot.write((i+1) + 
					", " + resultsByIte[i][0] + 
					", " + resultsByIte[i][1] + 
					", " + resultsByIte[i][2] + 
					", " + resultsByIte[i][3] + 
					", " + resultsByIte[i][4] + 
					", " + resultsByIte[i][5] +"\n");
		}

		plot.close();
	}
	
	public static void main(String[] args) throws Exception {
		//new GATest().perform();
		//evolveEGA();
		//evolveGA();
		for (int i = 0; i < 1; i++)
			evolveEGA();
	}
}
