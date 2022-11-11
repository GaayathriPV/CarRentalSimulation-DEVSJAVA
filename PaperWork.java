package ASE_Project;
import simView.*;


import java.lang.*;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;


public class PaperWork extends ViewableAtomic{
	protected DEVSQueue customer_queue;
	entity customer, current_customer = null;
	int processingTime = 7;
	int price;
	
	public PaperWork() {this("PaperWork");}
	public PaperWork(String name) {
		super(name);
		addInport("Cust_In");
		addOutport("CheckedIn_0");
		addOutport("CheckedIn_1");
		addOutport("CheckedIn_2");
	}
	
	public void initialize(){
	     customer_queue = new DEVSQueue();
	     passivate();
	}
	
	public void  deltext(double e,message x)
	{
	    Continue(e);
	    if(phaseIs("passive")){
	    	for (int i=0; i< x.getLength();i++){
	    	     if (messageOnPort(x, "Cust_In", i)) {
	    	       customer = x.getValOnPort("Cust_In", i);
	    	       current_customer = customer;
	    	       holdIn("processing", processingTime);
	    	       }
	    	     }
	    }
	    else if(phaseIs("processing")) {
	    	for(int i=0; i<x.getLength(); i++) {
	    		if(messageOnPort(x, "Cust_In", i)) {
	    			customer = x.getValOnPort("Cust_In", i);
	    			customer_queue.add(customer);
	    		}
	    	}
	    }
	}
	
	public void  deltint( ) {
		if(phaseIs("processing")) {
			if(!customer_queue.isEmpty()) {
			     current_customer = (entity)customer_queue.first();
			     holdIn("processing", processingTime);
			     customer_queue.remove();
			}
			else {
				passivate();
			}
		}
	}
	
	public message  out( ) {
		message  m = new message();
		content con;
		//System.out.println(current_customer);
		if(((customer)current_customer).getType_of_car() == 0) {
		    con = makeContent("CheckedIn_0", 
		    		new paymentCalc(current_customer.getName(), (int)price, (int)((customer)current_customer).getNo_of_days()));
		    m.add(con);
		}
		else if(((customer)current_customer).getType_of_car() == 1) {
			con = makeContent("CheckedIn_1", 
					new paymentCalc(current_customer.getName(), (int)((customer)current_customer).getType_of_car(), (int)((customer)current_customer).getNo_of_days()));
			m.add(con);
		}
		else if(((customer)current_customer).getType_of_car() == 2) {
			con = makeContent("CheckedIn_2", 
					new paymentCalc(current_customer.getName(), (int)price, (int)((customer)current_customer).getNo_of_days()));
			m.add(con);
		}
		
		return m;
	}
	
	public String getTooltipText(){
		if(current_customer!=null)
		return super.getTooltipText()+"\n number of cars in queue:"+customer_queue.size()+
		"\n my current job is:" + current_customer.toString();
		else return "initial value";
	}


}
