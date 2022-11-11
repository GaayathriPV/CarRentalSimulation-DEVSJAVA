package ASE_Project;

import simView.*;

import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;

public class checkOut extends ViewableAtomic{
	int processingTime = 4;
	protected DEVSQueue checkout_queue;
	entity customer, current_customer = null;
	
	public checkOut() {this("checkOut");}
	
	public checkOut(String name) {
		super(name);
		addInport("type_0");
		addInport("type_1");
		addInport("type_2");
		addOutport("checkOut");
	}
	
	public void initialize(){
	     checkout_queue = new DEVSQueue();
	     passivate();
	}
	
	public void  deltext(double e,message x)
	{
	    Continue(e);
	    if(phaseIs("passive")){
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "type_0", i)) {
	    	       customer = x.getValOnPort("type_0", i);
	    	       current_customer = customer;
	    	       holdIn("checkingOut", processingTime);
	    	       }
	    	     }
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "type_1", i)) {
	    	       customer = x.getValOnPort("type_1", i);
	    	       current_customer = customer;
	    	       holdIn("checkingOut", processingTime);
	    	       }
	    	     }
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "type_2", i)) {
	    	       customer = x.getValOnPort("type_2", i);
	    	       current_customer = customer;
	    	       holdIn("checkingOut", processingTime);
	    	       }
	    	     }
	    }
	    else if(phaseIs("checkingOut")) {
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "type_0", i)) {
	    			customer = x.getValOnPort("type_0", i);
	    			checkout_queue.add(customer);
	    		}
	    	}
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "type_1", i)) {
	    			customer = x.getValOnPort("type_1", i);
	    			checkout_queue.add(customer);
	    		}
	    	}
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "type_2", i)) {
	    			customer = x.getValOnPort("type_2", i);
	    			checkout_queue.add(customer);
	    		}
	    	}
	    }
	}
	
	public void  deltint( ) {
		if(phaseIs("checkingOut")) {
			if(!checkout_queue.isEmpty()) {
			     current_customer = (entity)checkout_queue.first();
			     holdIn("checkingOut", processingTime);
			     checkout_queue.remove();
			}
			else {
				passivate();
			}
		}
	}
	
	public message  out( )
	{
	   message  m = new message();
	   content con = makeContent("checkOut", 
			   new entity(current_customer.getName())); 
	   m.add(con);

	  return m;

	}
	
	public String getTooltipText(){
		if(current_customer!=null)
		return super.getTooltipText()+"\n number of cars in queue:"+checkout_queue.size()+
		"\n my current job is:" + current_customer.toString();
		else return "initial value";
	}

}
