import java.io.IOException;

import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Car extends Thread {
	
	private int id;
	
	private static int counter=0;
	
	private Vector<Service> Services; // no need to set and get, instead use AddService:Service
	
	private int num_of_liters; // for service of pump
	
	private int amount_to_pay; // for service of cashier
	
	private static Logger gasStationLogger = Logger.getLogger("gasStationLogger");
	
	private FileHandler carHandler;
 	
	public Car() throws SecurityException, IOException{
		this.id = ++counter;
		Services = new Vector<Service>();
		
		carHandler = new FileHandler("CarsLog\\"+ gasStationLogger.getName()+ "_"+" Car["+ id +"].xml");
		carHandler.setFilter(new MyFilter("Car", id));
		carHandler.setFormatter(new MyFormatter());
		gasStationLogger.addHandler(carHandler);
		
	}
	
	
	public void AddService(Service service){
		Services.add(service);  // the car add the relevant service to its Services - vector
		service.AddWaitingCar(this);  // the relevant service add the car to its queue 
	}
	
	
	
	@Override
	public void run() {
		try {
			
			for (Service service : Services){
				needService(service);
			}

	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	

	
	public void needService(Service service) throws InterruptedException {
		synchronized (this) {
			
		
			gasStationLogger.log(Level.INFO,"Car #" + getId() + " is waiting to "+service.getAction() +" in "+ service.getName() +"("+service.getId()+")\n", this);

			wait();  // wait to be first in pump's queue
		}
		
		
		synchronized (service) { // catch the service

			gasStationLogger.log(Level.INFO,"Car #" + getId() + " started to "+service.getAction() +" in "+ service.getName()  +"("+service.getId()+")\n", this);
			
			service.useService(this); // now can use it 

			gasStationLogger.log(Level.INFO,"Car #" + getId() + " finished to "+service.getAction() +" in "+ service.getName()  +"("+service.getId()+")\n", this);
		
			service.notifyAll();
		}
	}

	
	
	
	
	
	/*************************************** bla *************************************/
	
	public boolean yesOrNo(){
		return Math.round(Math.random())== 0;
	}
	
	
	
	@Override
	public String toString() {
		return "Car:: id=" + id + "\n";
	}
	
	

	@Override
	public boolean equals(Object obj) {
		Car other = (Car) obj;
		if (id != other.id)
			return false;
		return true;
	}




	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vector<Service> getServices() {
		return Services;
	}

	public int getNum_of_liters() {
		return num_of_liters;
	}

	public void setNum_of_liters(int num_of_liters) {
		this.num_of_liters = num_of_liters;
	}


	public int getAmount_to_pay() {
		return amount_to_pay;
	}


	public void setAmount_to_pay(int amount_to_pay) {
		this.amount_to_pay = amount_to_pay;
	}
	
	
	
}
