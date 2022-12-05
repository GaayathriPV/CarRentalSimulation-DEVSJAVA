/*      Copyright 2002 Arizona Board of regents on behalf of
 *                  The University of Arizona
 *                     All Rights Reserved
 *         (USE & RESTRICTION - Please read COPYRIGHT file)
 *
 *  Version    : DEVSJAVA 2.7
 *  Date       : 08-15-02
 */


package ASE_Project;

import simView.*;


import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;
import util.*;
import statistics.*;
import java.util.Random;

public class customerGenerator extends ViewableAtomic{


  protected int generation_time;
  protected int count;
  protected Random r;

  public customerGenerator() {this("customerGenerator", 2);}

public customerGenerator(String name,int period){
   super(name);
   //addInport("in");
   addOutport("cust_gen");

   generation_time = period ;
}

public void initialize(){
   holdIn("generates", generation_time);
   r = new Random();
   count = 0;
}


public void  deltint( )
{

if(phaseIs("generates")){
   count = count +1;
   //holdIn("active",int_gen_time);
   holdIn("generates",r.nextInt(2));
}
else passivate();
}

public message  out( )
{
//System.out.println(name+" out count "+count);
   message  m = new message();
//   content con = makeContent("out", new entity("car" + count));
   content con = makeContent("cust_gen", new customer("customer" + count, (int)(r.nextInt(2)), (int)(r.nextInt(7)), 1));
   m.add(con);

  return m;
}


}


