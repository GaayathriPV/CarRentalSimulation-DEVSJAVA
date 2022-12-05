package ASE_Project;

import simView.*;


import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;



public class MiniVan extends ViewableAtomic{
	protected DEVSQueue minivan_queue;
	entity customer, current_customer = null;
	int processingTime = 3;
	int price = 70;
	
	public MiniVan() {this("MiniVan");}
	
	public MiniVan(String type) {
		super(type);
		addInport("minivanIn");
		addOutport("minivanOut");
	}
	
	public void initialize(){
	     minivan_queue = new DEVSQueue();
	     passivate();
	}
	
	public void  deltext(double e,message x)
	{
	    Continue(e);
	    if(phaseIs("passive")){
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "minivanIn", i)) {
	    	       customer = x.getValOnPort("minivanIn", i);
	    	       current_customer = customer;
	    	       holdIn("renting", processingTime);
	    	       }
	    	     }
	    }
	    else if(phaseIs("renting")) {
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "minivanIn", i)) {
	    			customer = x.getValOnPort("minivanIn", i);
	    			minivan_queue.add(customer);
	    		}
	    	}
	    }
	}
	
	public void  deltint( ) {
		if(phaseIs("renting")) {
			if(!minivan_queue.isEmpty()) {
			     current_customer = (entity)minivan_queue.first();
			     holdIn("renting", processingTime);
			     minivan_queue.remove();
			}
			else {
				passivate();
			}
		}
	}
	
	public message  out( )
	{
	   message  m = new message();
	   content con = makeContent("minivanOut", 
			   new entity(current_customer.getName())); 
	   m.add(con);

	  return m;

	}
	
	public String getTooltipText(){
		if(current_customer!=null)
		return super.getTooltipText()+"\n number of cars in queue:"+minivan_queue.size()+
		"\n my current job is:" + current_customer.toString();
		else return "initial value";
	}

}
