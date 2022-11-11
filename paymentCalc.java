package ASE_Project;
import GenCol.*;

public class paymentCalc extends entity{
	protected int price;
	protected int no_of_days;
	protected int type_of_car;
	
	public paymentCalc() {
		this("customer", 0, 0);
	}
	
	public paymentCalc(String name, int type, int days) {
		super(name);
		type_of_car = type;
		no_of_days = days;
	}

	public int getPrice() {
		return price;
	}

	public int getNo_of_days() {
		return no_of_days;
	}
	public int getPrice(int type) {
		if(type == 0)
		price = 40;
		else if(type == 1)
			price = 50;
		else if(type == 2)
			price = 70;
		return price;
	}
	
	public String toString(){
		return name+"_"+"Payment:"+getPrice(type_of_car)*no_of_days;
	  }

}
