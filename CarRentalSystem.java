package ASE_Project;

import simView.*;

import java.awt.*;
import java.io.*;

import DEVSJAVALab.carGenr;
import genDevs.modeling.*;
import genDevs.simulation.*;
import GenCol.*;


public class CarRentalSystem extends ViewableDigraph{
	
	public CarRentalSystem() {this("CarRentalSystem");}
	
	public CarRentalSystem(String name) {
		super(name);
		CarRentalSystemConstruct();
	}
	
	public void CarRentalSystemConstruct() {
		this.addOutport("exit");
		
		
		ViewableAtomic cust_genr = new customerGenerator("customerGenerator",5);
		ViewableAtomic paper_work = new PaperWork("PaperWork");
		ViewableAtomic economy_car = new Economy("Economy");
		ViewableAtomic midsize_car = new Midsize("Midsize");
		ViewableAtomic minivan_car = new MiniVan("MiniVan");
		ViewableAtomic checkout = new checkOut("checkOut");
		
		add(cust_genr);
		add(paper_work);
		add(economy_car);
		add(midsize_car);
		add(minivan_car);
		add(checkout);
		
		addCoupling(cust_genr,"cust_gen",paper_work,"Cust_In");
		addCoupling(paper_work, "CheckedIn_0", economy_car, "economyIn");
		addCoupling(economy_car, "economyOut", checkout, "type_0");
		addCoupling(paper_work, "CheckedIn_1", midsize_car, "midsizeIn");
		addCoupling(midsize_car, "midsizeOut", checkout, "type_1");
		addCoupling(paper_work, "CheckedIn_2", minivan_car, "minivanIn");
		addCoupling(minivan_car, "minivanOut", checkout, "type_2");
		addCoupling(checkout, "checkOut", this, "exit");
	}

    /**
     * Automatically generated by the SimView program.
     * Do not edit this manually, as such changes will get overwritten.
     */
    public void layoutForSimView()
    {
        preferredSize = new Dimension(1050, 439);
        if((ViewableComponent)withName("Midsize")!=null)
             ((ViewableComponent)withName("Midsize")).setPreferredLocation(new Point(425, 173));
        if((ViewableComponent)withName("checkOut")!=null)
             ((ViewableComponent)withName("checkOut")).setPreferredLocation(new Point(697, 182));
        if((ViewableComponent)withName("MiniVan")!=null)
             ((ViewableComponent)withName("MiniVan")).setPreferredLocation(new Point(450, 317));
        if((ViewableComponent)withName("customerGenerator")!=null)
             ((ViewableComponent)withName("customerGenerator")).setPreferredLocation(new Point(-2, 166));
        if((ViewableComponent)withName("Economy")!=null)
             ((ViewableComponent)withName("Economy")).setPreferredLocation(new Point(418, 28));
        if((ViewableComponent)withName("PaperWork")!=null)
             ((ViewableComponent)withName("PaperWork")).setPreferredLocation(new Point(186, 174));
    }
}
