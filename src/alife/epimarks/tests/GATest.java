  
/**
 * 
 */
package alife.epimarks.tests;

import alife.epimarks.Utils;
import alife.epimarks.function.Ackley;
import alife.epimarks.function.Bohachevsky;
import alife.epimarks.function.Deceptive;
import alife.epimarks.function.Deceptive_;
import alife.epimarks.function.Griewank;
import alife.epimarks.function.MaxOnes;
import alife.epimarks.function.Rastrigin;
import alife.epimarks.function.Rosenbrock;
import alife.epimarks.function.RoyalRoad;
import alife.epimarks.function.Schwefel;
import alife.epimarks.operator.BitMutation;
import alife.epimarks.operator.Marking;
import alife.epimarks.operator.XOver;
import alife.epimarks.types.MarkedBitArray;
import unalcol.descriptors.Descriptors;
import unalcol.descriptors.WriteDescriptors;
import unalcol.evolution.haea.HaeaOperators;
import unalcol.evolution.haea.SimpleHaeaOperators;
import unalcol.io.Write;
import unalcol.optimization.OptimizationFunction;
import unalcol.optimization.OptimizationGoal;
import unalcol.optimization.binary.Transposition;
import unalcol.optimization.real.HyperCube;
import unalcol.optimization.real.testbed.Griewangk;
import unalcol.optimization.real.testbed.RosenbrockSaddle;
import unalcol.search.Goal;
import unalcol.search.population.Population;
import unalcol.search.population.PopulationSearch;
import unalcol.search.selection.Tournament;
import unalcol.search.solution.Solution;
import unalcol.search.space.Space;
import unalcol.search.variation.Variation_1_1;
import unalcol.tracer.ConsoleTracer;
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

    public static void evolveGA(){
        // Search Space definition
        int DIM = 360;
        Space<BitArray> space = BinarySpace.getVarLengthBinarySpace(DIM);
        
        // Optimization Function
 	   	//OptimizationFunction<BitArray> function = new Deceptive().new Classic();
 	    //OptimizationFunction<BitArray> function = new Deceptive_().new Classic();
        //OptimizationFunction<BitArray> function = new RoyalRoad().new Classic();
        OptimizationFunction<BitArray> function = new MaxOnes().new Classic();        
        Goal<BitArray,Double> goal = new OptimizationGoal<BitArray>(function, false);     
        
        // Variation definition
        Variation_1_1<BitArray> mutation = new unalcol.optimization.binary.BitMutation();
        unalcol.optimization.binary.XOver xover = new unalcol.optimization.binary.XOver();

        unalcol.evolution.EAFactory<BitArray> factory = new EAFactory<BitArray>();
        PopulationSearch<BitArray,Double> search = 
                factory.steady_ga(POPSIZE, new Tournament<BitArray>(4), mutation, xover, 1.0, MAXITERS );

        // Tracking the goal evaluations
        WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(Population.class, write_desc);
        Descriptors.set(Population.class, new PopulationDescriptors<BitArray>());
        
        //FileTracer tracer = new FileTracer("/Users/lifeth/Desktop/experiments/classic/generational/plotD3"+i+".txt");   
       ConsoleTracer tracer = new ConsoleTracer();       

        //Tracer.addTracer(goal, tracer); 
        Tracer.addTracer(search, tracer);
        
        // Apply the search method        
        //Solution<BitArray> solution = 
        search.solve(space, goal);
        
       // System.out.println(solution.object()+": "+solution.info(Goal.class.getName()));
    }
	
    public static void evolveEGA(){
        // Search Space definition
        int DIM = 360;
        Space<MarkedBitArray> space =  BinarySpace.getVarLengthBinarySpaceTags(DIM);
        
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
    
    
    public static void real2binary(){
    	//moves in the binary space, but computes fitness in the real space
        // Search Space definition
        int DIM = 10;
        double[] min = DoubleArray.create(DIM, -5.12);
   		double[] max = DoubleArray.create(DIM,  5.11);
   		HyperCube hCube = new HyperCube( min, max );
   		
        Space<BitArray> space = BinarySpace.getVarLengthBinarySpace(DIM*Utils.BITS, hCube);
   
        // Optimization Function
        OptimizationFunction<BitArray> function = new Rastrigin().new Classic();  
        //OptimizationFunction<BitArray> function = new Ackley().new Classic();  
        //OptimizationFunction<BitArray> function = new Bohachevsky(true).new Classic();  
        //OptimizationFunction<BitArray> function = new Schwefel().new Classic();  
        //OptimizationFunction<BitArray> function = new Rosenbrock().new Classic(); 
        //OptimizationFunction<BitArray> function = new Griewank().new Classic(); 
        
        Goal<BitArray,Double> goal = new OptimizationGoal<BitArray>(function);  // minimizing, add the parameter false if maximizing       
        
         // Variation definition
        Variation_1_1<BitArray> mutation = new unalcol.optimization.binary.BitMutation();
        unalcol.optimization.binary.XOver xover = new unalcol.optimization.binary.XOver();
        
        EAFactory<BitArray> factory = new EAFactory<BitArray>();
        PopulationSearch<BitArray,Double> search = 
                factory.steady_ga(POPSIZE, new Tournament<BitArray>(4), mutation, xover, 0.6, MAXITERS );  

        // Tracking the goal evaluations
        WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(Population.class, write_desc);
        Descriptors.set(Population.class, new PopulationDescriptors<BitArray>());
        
        //FileTracer tracer = new FileTracer("/Users/lifeth/Desktop/extended/steady/plotD4"+i+".txt");   
        ConsoleTracer tracer = new ConsoleTracer();       

        //Tracer.addTracer(goal, tracer); 
        Tracer.addTracer(search, tracer);
        
        // Apply the search method        
       //Solution<BitArray> solution = 
        search.solve(space, goal);     
       
      // System.out.println(solution.object()+": "+solution.info(Goal.class.getName()));   
    }
    
    public static void real2binaryEGA(){
         
      //moves in the binary space, but computes fitness in the real space
        // Search Space definition
        int DIM = 10;

        double[] min = DoubleArray.create(DIM, -2.048);
   		double[] max = DoubleArray.create(DIM,  2.047);
   		HyperCube hCube = new HyperCube( min, max );
   		
        Space<MarkedBitArray> space = BinarySpace.getVarLengthBinarySpaceTags(DIM*Utils.BITS, hCube);
        
        // Optimization Function   
        //OptimizationFunction<MarkedBitArray> function = new Rastrigin().new Extended(); 
        //OptimizationFunction<MarkedBitArray> function = new Ackley().new Extended();  
        //OptimizationFunction<MarkedBitArray> function = new Bohachevsky(true).new Extended();  
        //OptimizationFunction<MarkedBitArray> function = new Schwefel().new Extended();  
        OptimizationFunction<MarkedBitArray> function = new Rosenbrock().new Extended(); 
        //OptimizationFunction<MarkedBitArray> function = new Griewank().new Extended(); 
        
        Goal<MarkedBitArray,Double> goal = new OptimizationGoal<MarkedBitArray>(function);  // minimizing, add the parameter false if maximizing       
      
        // Variation definition
        Variation_1_1<MarkedBitArray> mutation = new BitMutation();
        XOver xover = new XOver();

        EAFactory<MarkedBitArray> factory = new EAFactory<MarkedBitArray>();
        PopulationSearch<MarkedBitArray,Double> search = 
                factory.generational_ga(POPSIZE, new Tournament<MarkedBitArray>(4), mutation, xover, 0.8, MAXITERS );

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
        // Number of bits per integer (i.e. per real)
        
        double[] min = DoubleArray.create(DIM, -600);
   		double[] max = DoubleArray.create(DIM, 599);
   		HyperCube hCube = new HyperCube( min, max );
   		
        Space<BitArray> space = BinarySpace.getVarLengthBinarySpace(DIM*Utils.BITS, hCube);
   
        // Optimization Function
         // OptimizationFunction<BitArray> function = new Rastrigin().new Classic();  
         //OptimizationFunction<BitArray> function = new Ackley().new Classic();  
         //OptimizationFunction<BitArray> function = new Bohachevsky(true).new Classic();  
        // OptimizationFunction<BitArray> function = new Schwefel().new Classic(); 
        // OptimizationFunction<BitArray> function = new Rosenbrock().new Classic(); 
        OptimizationFunction<BitArray> function = new Griewank().new Classic(); 

        Goal<BitArray,Double> goal = new OptimizationGoal<BitArray>(function); // maximizing, remove the parameter false if minimizing   	
    	
    	// Variation definition
        Variation_1_1<BitArray> mutation = new unalcol.optimization.binary.BitMutation();
    	Variation_1_1<BitArray> transposition = new Transposition();
        unalcol.optimization.binary.XOver xover = new unalcol.optimization.binary.XOver();
    	HaeaOperators<BitArray> operators = new SimpleHaeaOperators<BitArray> (mutation, transposition, xover);
        
        // Search method
        EAFactory<BitArray> factory = new EAFactory<BitArray>();
        PopulationSearch<BitArray,Double> search = 
        		factory.HAEA(POPSIZE, operators, new Tournament<BitArray>(4), MAXITERS );

        // Tracking the goal evaluations
        WriteDescriptors write_desc = new WriteDescriptors();
        Write.set(double[].class, new DoubleArrayPlainWrite(false));
        Write.set(Population.class, write_desc);
        Descriptors.set(Population.class, new PopulationDescriptors<BitArray>());
        
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
	
	public static void binaryHAEAMarker(){
		// Search Space definition
		int DIM = 10;
        double[] min = DoubleArray.create(DIM, -600);
   		double[] max = DoubleArray.create(DIM,  599);
   		HyperCube hCube = new HyperCube( min, max );
   		
        Space<MarkedBitArray> space = BinarySpace.getVarLengthBinarySpaceTags(DIM*Utils.BITS, hCube);
   
        // Optimization Function
        //OptimizationFunction<MarkedBitArray> function = new Rastrigin().new Extended(); 
        //OptimizationFunction<MarkedBitArray> function = new Ackley().new Extended();  
        //OptimizationFunction<MarkedBitArray> function = new Bohachevsky(true).new Extended();  
        //OptimizationFunction<MarkedBitArray> function = new Schwefel().new Extended();  
        //OptimizationFunction<MarkedBitArray> function = new Rosenbrock().new Extended(); 
        OptimizationFunction<MarkedBitArray> function = new Griewank().new Extended();
        Goal<MarkedBitArray,Double> goal = new OptimizationGoal<MarkedBitArray>(function);  // minimizing, add the parameter false if maximizing    
    	
    	// Variation definition
        Variation_1_1<MarkedBitArray> mutation = new BitMutation();
        XOver xover = new XOver();        
    	//Variation_1_1<BitArray> transposition = new Transposition();
        Marking marking = new Marking(0.02);
    	HaeaOperators<MarkedBitArray> operators = new SimpleHaeaOperators<MarkedBitArray> (mutation, marking, xover);
        
        // Search method
        EAFactory<MarkedBitArray> factory = new EAFactory<MarkedBitArray>();
        PopulationSearch<MarkedBitArray,Double> search = 
        		factory.HAEA(POPSIZE, operators, new Tournament<MarkedBitArray>(4), MAXITERS );

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
			//evolveGA();
			//evolveEGA();
			//real2binary();
		    real2binaryEGA();
			//binaryHAEA();
			//binaryHAEAMarker();
			
	}
}