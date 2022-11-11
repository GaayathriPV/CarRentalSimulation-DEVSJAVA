package ASE_Project;
import GenCol.*;

public class customer extends entity{
  protected int type_of_car;
  protected int no_of_days;
  protected int priority;
  
  public customer(){
	  this("cust", 0, 1, 1);
  }
  
  public customer(String name, int type, int days, int _priority){
	  super(name);
	  type_of_car = type;
	  no_of_days = days;
	  priority = _priority;
  }
  
  public int getType_of_car() {
	return type_of_car;
}

public int getNo_of_days() {
	return no_of_days;
}

public int getPriority() {
	return priority;
}

public String toString(){
	return name+"_"+"type"+type_of_car; 
  }
		
}

