import java.util.logging.Filter;
import java.util.logging.LogRecord;


public class MyFilter implements Filter {
	
	private String className;
	
	private int id;
	
	public MyFilter(String className,int id){
		this.className = className;
		this.id = id;
	}

	@Override
	public boolean isLoggable(LogRecord record) {
		Object o = record.getParameters()[0];
		
		if (o instanceof Car){
			Car tmpCar = (Car)o;
			if (className == "Car" && tmpCar.getId() == id)
				return true;
		}
		else if(o instanceof Pump){
			Pump tmpPump = (Pump)o;
			if (className == "Pump" && tmpPump.getId() == id)
				return true;
		} 
		else if(o instanceof Cashier){
			Cashier tmpCashier = (Cashier)o;
			if (className == "Cashier" && tmpCashier.getId() == id)
				return true;
		} 
		else if(o instanceof CoffeeHouse){
			if (className == "CoffeeHouse" && id == 1)
				return true;
		} 
		else if(o== null){
				return true;
		} 
		
		
		return false;
		
		
	}
	

}
