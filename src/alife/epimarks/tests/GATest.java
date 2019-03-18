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

    public static void evolveGA(int i){
        // Search Space definition
        int DIM = 360;
        Space<BitArray> space = new unalcol.optimization.binary.BinarySpace( DIM );
        
        // Optimization Function
 	   	OptimizationFunction<BitArray> function = new Deceptive().new Classic();
 	   //OptimizationFunction<BitArray> function = new Deceptive_().new Classic();
        //OptimizationFunction<BitArray> function = new RoyalRoad().new Classic();
        //OptimizationFunction<BitArray> function = new MaxOnes().new Classic();        
        Goal<BitArray,Double> goal = new OptimizationGoal<BitArray>(function, false);     
        
        // Variation definition
        Variation_1_1<BitArray> mutation = new unalcol.optimization.binary.BitMutation();
        unalcol.optimization.binary.XOver xover = new unalcol.optimization.binary.XOver();

        unalcol.evolution.EAFactory<BitArray> factory = new EAFactory<BitArray>();
        PopulationSearch<BitArray,Double> search = 
                factory.steady_ga(POPSIZE, new Tournament<BitArray>(4), mutation, xover, 0.7, MAXITERS );

        // Tracking the goal evaluations
        WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(Population.class, write_desc);
        Descriptors.set(Population.class, new PopulationDescriptors<BitArray>());
        
        FileTracer tracer = new FileTracer("/Users/lifeth/Desktop/classic/steady/plotD3"+i+".txt");   
        //ConsoleTracer tracer = new ConsoleTracer();       

        //Tracer.addTracer(goal, tracer); 
        Tracer.addTracer(search, tracer);
        
        // Apply the search method        
        Solution<BitArray> solution = 
        search.solve(space, goal);
        
        System.out.println(solution.object()+": "+solution.info(Goal.class.getName()));
    }
	
    public static void evolveEGA(int i){
        // Search Space definition
        int DIM = 360;
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
        
        //FileTracer tracer = new FileTracer("/Users/lifeth/Desktop/extended/steady/plotD4"+i+".txt");   
        ConsoleTracer tracer = new ConsoleTracer();       

        //Tracer.addTracer(goal, tracer); 
        Tracer.addTracer(search, tracer);
        
        // Apply the search method        
       // Solution<MarkedBitArray> solution = 
        search.solve(space, goal);
        
        //System.out.println(solution.object()+": "+solution.info(Goal.class.getName()));
       // System.out.println(solution.object().toStringTags());
    }
	
	public static void main(String[] args) throws Exception {
	
		for (int i = 0; i < 30; i++)
			evolveEGA(i);
	}
}
