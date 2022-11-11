package ASE_Project;

import simView.*;


import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;



public class Midsize extends ViewableAtomic{
	protected DEVSQueue midsize_queue;
	entity customer, current_customer = null;
	int processingTime = 5;
	int price = 50;
	
	public Midsize() {this("Midsize");}
	
	public Midsize(String type) {
		super(type);
		addInport("midsizeIn");
		addOutport("midsizeOut");
	}
	
	public void initialize(){
	     midsize_queue = new DEVSQueue();
	     passivate();
	}
	
	public void  deltext(double e,message x)
	{
	    Continue(e);
	    if(phaseIs("passive")){
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "midsizeIn", i)) {
	    	       customer = x.getValOnPort("midsizeIn", i);
	    	       current_customer = customer;
	    	       holdIn("renting", processingTime);
	    	       }
	    	     }
	    }
	    else if(phaseIs("renting")) {
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "midsizeIn", i)) {
	    			customer = x.getValOnPort("midsizeIn", i);
	    			midsize_queue.add(customer);
	    		}
	    	}
	    }
	}
	
	public void  deltint( ) {
		if(phaseIs("renting")) {
			if(!midsize_queue.isEmpty()) {
			     current_customer = (entity)midsize_queue.first();
			     holdIn("renting", processingTime);
			     midsize_queue.remove();
			}
			else {
				passivate();
			}
		}
	}
	
	public message  out( )
	{
	   message  m = new message();
	   content con = makeContent("midsizeOut", 
			   new entity(current_customer.getName())); 
	   m.add(con);

	  return m;

	}
	
	public String getTooltipText(){
		if(current_customer!=null)
		return super.getTooltipText()+"\n number of cars in queue:"+midsize_queue.size()+
		"\n my current job is:" + current_customer.toString();
		else return "initial value";
	}

}
