package ASE_Project;

import simView.*;


import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;



public class Economy extends ViewableAtomic{
	protected DEVSQueue economy_queue;
	entity customer, current_customer = null;
	int processingTime = 3;
	int price = 40;
	
	public Economy() {this("Economy");}
	
	public Economy(String type) {
		super(type);
		addInport("economyIn");
		addOutport("economyOut");
	}
	
	public void initialize(){
	     economy_queue = new DEVSQueue();
	     passivate();
	}
	
	public void  deltext(double e,message x)
	{
	    Continue(e);
	    if(phaseIs("passive")){
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "economyIn", i)) {
	    	       customer = x.getValOnPort("economyIn", i);
	    	       current_customer = customer;
	    	       holdIn("renting", processingTime);
	    	       }
	    	     }
	    }
	    else if(phaseIs("renting")) {
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "economyIn", i)) {
	    			customer = x.getValOnPort("economyIn", i);
	    			economy_queue.add(customer);
	    		}
	    	}
	    }
	}
	
	public void  deltint( ) {
		if(phaseIs("renting")) {
			if(!economy_queue.isEmpty()) {
			     current_customer = (entity)economy_queue.first();
			     holdIn("renting", processingTime);
			     economy_queue.remove();
			}
			else {
				passivate();
			}
		}
	}
	
	public message  out( )
	{
	   message  m = new message();
	   content con = makeContent("economyOut", 
			   new entity(current_customer.getName())); 
	   m.add(con);

	  return m;

	}
	
	public String getTooltipText(){
		if(current_customer!=null)
		return super.getTooltipText()+"\n number of cars in queue:"+economy_queue.size()+
		"\n my current job is:" + current_customer.toString();
		else return "initial value";
	}

}
