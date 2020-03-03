  
/**
 * 
 */
package alife.epimarks.tests;

import alife.epimarks.Utils;
import alife.epimarks.function.Deceptive;
import alife.epimarks.function.Deceptive4;
import alife.epimarks.function.Griewank;
import alife.epimarks.function.MaxOnes;
import alife.epimarks.function.RoyalRoad;
import alife.epimarks.operator.BitMutation;
import alife.epimarks.operator.Transposition;
import alife.epimarks.operator.XOver;
import alife.epimarks.types.MarkedBitArray;
import unalcol.descriptors.Descriptors;
import unalcol.descriptors.WriteDescriptors;
import unalcol.evolution.haea.HaeaOperators;
import unalcol.evolution.haea.SimpleHaeaOperators;
import unalcol.io.Write;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.OptimizationGoal;
import unalcol.optimization.real.HyperCube;
import unalcol.search.Goal;
import unalcol.search.population.Population;
import unalcol.search.population.PopulationSearch;
import unalcol.search.selection.Tournament;
import unalcol.search.space.Space;
import unalcol.search.variation.Variation_1_1;
import unalcol.tracer.ConsoleTracer;
import unalcol.tracer.Tracer;
import unalcol.types.real.array.DoubleArray;
import unalcol.types.real.array.DoubleArrayPlainWrite;

/**
 * @author lifeth
 *
 */
public class GATest {
	
	public static int MAXITERS = 1000;
	public static int POPSIZE = 100;
	
    public static void evolveEGA(){
        // Search Space definition
        int DIM = 360; 
        Space<MarkedBitArray> space =  BinarySpace.getVarLengthBinarySpaceTags(DIM, false);
        
        // Optimization Function
 	   	//OptimizationFunction<MarkedBitArray> function = new Deceptive();
        //OptimizationFunction<MarkedBitArray> function = new Deceptive4();//352
        //OptimizationFunction<MarkedBitArray> function = new RoyalRoad(); //384
        OptimizationFunction<MarkedBitArray> function = new MaxOnes();       
        Goal<MarkedBitArray,Double> goal = new OptimizationGoal<MarkedBitArray>(function, false);     
        
        // Variation definition
        Variation_1_1<MarkedBitArray> mutation = new BitMutation();
        XOver xover = new XOver();

        EAFactory<MarkedBitArray> factory = new EAFactory<MarkedBitArray>();
        PopulationSearch<MarkedBitArray,Double> search = 
                factory.generational_ga(POPSIZE, new Tournament<MarkedBitArray>(4), mutation, xover, 0.6, MAXITERS );

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
        //Solution<MarkedBitArray> solution = 
        search.solve(space, goal);
        
        //System.out.println(solution.object()+": "+solution.info(Goal.class.getName()));
        //System.out.println(solution.object().toStringTags());
    }
    
    public static void real2binaryEGA(){
         
      //moves in the binary space, but computes fitness in the real space
        // Search Space definition
        int DIM = 10;

        double[] min = DoubleArray.create(DIM, -600);
   		double[] max = DoubleArray.create(DIM,  599);
   		HyperCube hCube = new HyperCube( min, max );
   		
        Space<MarkedBitArray> space = BinarySpace.getVarLengthBinarySpaceTags(DIM*Utils.BITS, hCube, false);
        
        // Optimization Function   
        //OptimizationFunction<MarkedBitArray> function = new Rastrigin(); 
        //OptimizationFunction<MarkedBitArray> function = new Ackley(); NO
        //OptimizationFunction<MarkedBitArray> function = new Bohachevsky(true); NO 
        //OptimizationFunction<MarkedBitArray> function = new Schwefel();  
        //OptimizationFunction<MarkedBitArray> function = new Rosenbrock(); 
        OptimizationFunction<MarkedBitArray> function = new Griewank(); 
        
        Goal<MarkedBitArray,Double> goal = new OptimizationGoal<MarkedBitArray>(function);  // minimizing, add the parameter false if maximizing       
      
        // Variation definition
        Variation_1_1<MarkedBitArray> mutation = new BitMutation();
        XOver xover = new XOver();

        EAFactory<MarkedBitArray> factory = new EAFactory<MarkedBitArray>();
        PopulationSearch<MarkedBitArray,Double> search = 
                factory.steady_ga(POPSIZE, new Tournament<MarkedBitArray>(4), mutation, xover, 0.7, MAXITERS );

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
        //System.out.println(solution.object().toStringTags());
    }
	
	public static void binaryHAEA(){
		// Search Space definition
		int DIM = 10;
        double[] min = DoubleArray.create(DIM, -600);
   		double[] max = DoubleArray.create(DIM,  599);
   		HyperCube hCube = new HyperCube( min, max );
   		
        Space<MarkedBitArray> space = BinarySpace.getVarLengthBinarySpaceTags(DIM*Utils.BITS, hCube, false);
   
        // Optimization Function
        //OptimizationFunction<MarkedBitArray> function = new Rastrigin(); 
        //OptimizationFunction<MarkedBitArray> function = new Ackley();  NO
        //OptimizationFunction<MarkedBitArray> function = new Bohachevsky(true); NO
        //OptimizationFunction<MarkedBitArray> function = new Schwefel();  
        //OptimizationFunction<MarkedBitArray> function = new Rosenbrock(); 
        OptimizationFunction<MarkedBitArray> function = new Griewank();
        Goal<MarkedBitArray,Double> goal = new OptimizationGoal<MarkedBitArray>(function);  // minimizing, add the parameter false if maximizing    
    	
    	// Variation definition
        Variation_1_1<MarkedBitArray> mutation = new BitMutation();
        XOver xover = new XOver();        
    	Variation_1_1<MarkedBitArray> transposition = new Transposition();
    	HaeaOperators<MarkedBitArray> operators = new SimpleHaeaOperators<MarkedBitArray> (mutation, transposition, xover);
        
        // Search method
        EAFactory<MarkedBitArray> factory = new EAFactory<MarkedBitArray>();
        PopulationSearch<MarkedBitArray,Double> search = 
        		factory.HAEA_Generational(POPSIZE, operators, new Tournament<MarkedBitArray>(4), MAXITERS );

        // Tracking the goal evaluations
        WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(Population.class, write_desc);
        Descriptors.set(Population.class, new PopulationDescriptors<MarkedBitArray>());
        
       /* WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(HaeaStep.class, new WriteHaeaStep<BitArray>());
        Descriptors.set(Population.class, new PopulationDescriptors<BitArray>());
        Descriptors.set(HaeaOperators.class, new SimpleHaeaOperatorsDescriptor<BitArray>());
        Write.set(HaeaOperators.class, write_desc);*/
        
        ConsoleTracer tracer = new ConsoleTracer();       
        //Tracer.addTracer(goal, tracer);  // Uncomment if you want to trace the function evaluations
        Tracer.addTracer(search, tracer); // Uncomment if you want to trace the hill-climbing algorithm
        
        // Apply the search method
        //Solution<BitArray> solution = 
        search.solve(space, goal);
	}
	
	public static void main(String[] args) throws Exception {
	
		for (int i = 0; i < 30; i++)
			 evolveEGA();
		     //real2binaryEGA();
			//binaryHAEA();
			
	}
}