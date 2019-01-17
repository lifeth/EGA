package alife.epimarks.operator;

import alife.epimarks.types.MarkedBitArray;
import unalcol.clone.*;
import unalcol.random.raw.RawGenerator;
import unalcol.search.variation.Variation_2_2;

public class XOver extends Variation_2_2<MarkedBitArray>{

  /**
   * The crossover point of the last xover execution
   */
  protected int crossOverPoint;
  
  public XOver(){}

  /**
   * Apply the simple point crossover operation over the given genomes at the given
   * cross point
   * @param child1 The first parent
   * @param child2 The second parent
   * @param xoverPoint crossover point
   * @return The crossover point
   */
  protected MarkedBitArray[] apply(MarkedBitArray child1, MarkedBitArray child2, int xoverPoint) {
      
	  try{
    	    
    	  MarkedBitArray child1_1 = (MarkedBitArray) Clone.create(child1);
    	  MarkedBitArray child2_1 = (MarkedBitArray) Clone.create(child2);
    	  MarkedBitArray child1_2 = (MarkedBitArray) Clone.create(child1);
          MarkedBitArray child2_2 = (MarkedBitArray) Clone.create(child2);

          crossOverPoint = xoverPoint;
          
          child1_2.leftSetToZero(crossOverPoint);
          child2_2.leftSetToZero(crossOverPoint);
          child1_1.rightSetToZero(crossOverPoint);
          child2_1.rightSetToZero(crossOverPoint);
          
		  child1_2.or(child2_1);
          child2_2.or(child1_1);
          
	  	  char [][] tags_child1 = {
	  			  child2_2.getTags()[0].clone(), 
	  			  child2_2.getTags()[1].clone(), 
	  			  child2_2.getTags()[2].clone()}; 
	  	  
	 	  char [][] tags_child2 = {
	 			  child1_2.getTags()[0].clone(), 
	 			  child1_2.getTags()[1].clone(), 
	 			  child1_2.getTags()[2].clone()};
	 	 
	 	  for (int i = 0; i < child1.getTagsLength(); i++) {
	 		  	 		  
	 		 System.arraycopy(child1_2.getTags()[i], crossOverPoint, tags_child1[i], 
	 					crossOverPoint, child1_2.getTags()[i].length-crossOverPoint);

	 	  	 System.arraycopy(child2_2.getTags()[i], crossOverPoint, tags_child2[i], 
	 	  				crossOverPoint, child2_2.getTags()[i].length-crossOverPoint);
		  }	
	 	  
	 	/* 	 	  
	 	 boolean [] ismarked1 = child2_2.getIsMarked().clone();  
	 	 boolean [] ismarked2 = child1_2.getIsMarked().clone();
	 	  
	 	 System.arraycopy(child1_2.getIsMarked(), crossOverPoint, ismarked1, 
					crossOverPoint, child1_2.getIsMarked().length-crossOverPoint);

	  	 System.arraycopy(child2_2.getIsMarked(), crossOverPoint, ismarked2, 
	  				crossOverPoint, child2_2.getIsMarked().length-crossOverPoint);
	  					  				
	 	 child1_2.setIsmarked(ismarked1);
	 	 child2_2.setIsmarked(ismarked2);*/
	 	  
	 	 child1_2.setTags(tags_child1);
	 	 child2_2.setTags(tags_child2);
		
         return new MarkedBitArray[]{child1_2, child2_2};
          
      }catch( Exception e ){
    	  e.printStackTrace();
      }
      return null;
  }

  /**
   * Apply the simple point crossover operation over the given genomes
   * @param child1 The first parent
   * @param child2 The second parent
   * @return The crossover point
   */
    @Override
    public MarkedBitArray[] apply( MarkedBitArray child1, MarkedBitArray child2 ){
        RawGenerator g = RawGenerator.get(this);
        int pos = g.integer(Math.min(child1.size(), child2.size()));
        //System.out.println("pos-->"+pos);
        return apply(child1, child2, pos);
      }
    
    public static void main(String[] args) {
  	  
    	XOver xo = new XOver();
    	MarkedBitArray x = new MarkedBitArray(10, 3, true);

    	x.setTags(new char[][]{
    		{'1',Character.MIN_VALUE,'0','1','1','1','1','0','1','1'}, 
    		{'0','0',Character.MIN_VALUE,'0','1','1','1','0','1','1'},
    		{'1', '1', Character.MIN_VALUE, 
    			Character.MIN_VALUE, '0', '1', 
    		 '1', '1','1',
    		 '0' }});
    	
    	MarkedBitArray y = new MarkedBitArray(10, 3, true);
    	
    	y.setTags(new char[][]{
    		{'1','0','0','1','1','0','1','0','1','1'}, 
    		{'0','0','0','1','1','1','1','0','1','1'},
    		{'0','0',Character.MIN_VALUE,'1','1','1','1','0','0',Character.MIN_VALUE}});
    	
    	xo.apply(x, y);
    }
}