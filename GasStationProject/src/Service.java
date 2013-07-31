import java.util.Queue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class Service implements Runnable  {
	
	protected int id;
	
	protected Queue<Car> waitingCars;
	
	protected int income;
	
	protected static Logger gasStationLogger = Logger.getLogger("gasStationLogger");
	
	protected FileHandler serviceHandler;
	
	
	public abstract void useService(Car theCar);
	
	public abstract String getAction(); //  get the activity of the Service
	public abstract String getName(); //  get the Name of the Service
	
	
	public void notifyCar() {
		Car firstCar = waitingCars.peek();
		if (firstCar != null) {

			gasStationLogger.log(Level.INFO,getName()+" ("+ id + ") is notifying car #"+ firstCar.getId()+"\n", this);

			synchronized (firstCar) { 
				firstCar.notifyAll(); // first car in Q can use service now (useService)
			}
		}
		
		synchronized (this) { // WAIT the current car to finish
			try {   

				gasStationLogger.log(Level.INFO,getName()+" ("+id+") waits that car #"+ firstCar.getId()+ " will announce it is finished\n", this);

				wait(); // Service is wait until the car release it 
				
				
				gasStationLogger.log(Level.INFO,getName()+" ("+id+") was announced that  car #"+ firstCar.getId() + " is finished\n", this);
				waitingCars.remove(); // remove first car 

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	

	@Override
	public boolean equals(Object obj) {
		Service other = (Service) obj;
		if (id != other.id)
			return false;
		return true;
		
		
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Queue<Car> getWaitingCars() {
		return waitingCars;
	}

	public void AddWaitingCar(Car newCar) {
		this.waitingCars.add(newCar);
		gasStationLogger.log(Level.INFO,getName()+" ("+ id + ") have  car #"+ newCar.getId()+"\n", this);
		newCar.start();
		
		//
		synchronized (this /* dummyWaiter */) { // to let know there is 
			if (waitingCars.size() == 1)	   // an airplane waiting 
				/*dummyWaiter. */notify();       
			
		}
		
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}
	





}
