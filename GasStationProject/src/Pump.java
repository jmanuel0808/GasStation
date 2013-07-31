import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;





public class Pump  extends Service  {
	
	private MainFuelPool theMainFuelPool ; 

	private static int counter=0;
	
	
	
	public Pump(MainFuelPool mainFuelPool) throws SecurityException, IOException{
		
		this.waitingCars = new LinkedList<Car>();
		this.theMainFuelPool = mainFuelPool;
		this.id = ++counter;
		
		serviceHandler = new FileHandler("PumpsLog\\"+ gasStationLogger.getName()+ "_"+" Pump["+ id +"].xml");
		serviceHandler.setFormatter(new SimpleFormatter());
		serviceHandler.setFilter(new MyFilter("Pump", id));
		serviceHandler.setFormatter(new MyFormatter());
		gasStationLogger.addHandler(serviceHandler);
		
	}
	
	
	public void run() { 
		while (true) {
			if (!waitingCars.isEmpty()){
				notifyCar(); 
			}else{
				synchronized (/* dummyWaiter */ this) {
					try{
						wait(); // wait till is a car waiting (notify by Service.addWaitingCar)
					}catch (InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			
		}
	}

	
	
	

	@Override
	public  void useService(Car theCar){
		
		GetFuel(theCar);
		try {
			Thread.sleep((long) (2000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gasStationLogger.log(Level.INFO,"Car "+theCar.getId()+ " is pumping "+ theCar.getNum_of_liters() + " liters from Pump "+ getId() +" \n", this);
	}
	
	
	 public  void GetFuel(Car theCar){
		 while (theMainFuelPool.IsLessThenLimit()){
			 gasStationLogger.log(Level.INFO,"Trying to get Fuel , But Capacity smaller then limit \n", this);
			 gasStationLogger.log(Level.INFO,"Trying to get Fuel , But Capacity smaller then limit \n", theCar);
			//		try {
						
						theMainFuelPool.alertToFillFuel();
						
			//			theMainFuelPool.wait(); // stop the pumps to get fuel until the main-fuel-pool will cross the allowed limit
											   // its notify after it puts fuel
						
						
				//	} catch (InterruptedException e) {
			//			e.printStackTrace();
			//		}
				
			}
		 notifyAll(); // release from waiting
		 theMainFuelPool.updateCurrentCapacity(theCar.getNum_of_liters());
	 }
	 

	
	/************************************** Bla **************************************/
	@Override
	public boolean equals(Object obj) {	
		if (!super.equals(obj))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Pump:: id=" + id + ", income=" + income + "\n\t" + waitingCars ;
	}

	
	public MainFuelPool getTheMainFuelPool() {
		return theMainFuelPool;
	}


	public void setTheMainFuelPool(MainFuelPool theMainFuelPool) {
		this.theMainFuelPool = theMainFuelPool;
	}
	
	

	@Override
	public String getAction() {
		return " full fuel ";
	}

	
	@Override
	public String getName() {
		return getClass().getSimpleName();
	}






	

	
	
	
	
}
