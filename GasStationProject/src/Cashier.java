import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;




public class Cashier extends Service {

	private CoffeeHouse theCoffeeHouse; //no need to set this object
	
	private static int counter=0;
	
	public Cashier(CoffeeHouse coffeeHouse) throws SecurityException, IOException{
		this.waitingCars = new LinkedList<Car>();
		this.theCoffeeHouse = coffeeHouse;
		this.id = ++counter;
		
		
		serviceHandler = new FileHandler("CashierLog\\"+ gasStationLogger.getName()+ "_"+" Cashier["+ id +"].xml");
		serviceHandler.setFormatter(new SimpleFormatter());
		serviceHandler.setFilter(new MyFilter("Cashier", id));
		serviceHandler.setFormatter(new MyFormatter());
		gasStationLogger.addHandler(serviceHandler);
	}
	
	

	@Override
	public void run() {
		while (true)
		{
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
	

	/**************************************silly  ******************************************/

	@Override
	public String getAction() {
		return "to pay ";
	}
	





	@Override
	public String toString() {
		return "Cashier:: id=" + id + ",income=" + income +"\n\t"+ waitingCars ;
	}


	@Override
	public boolean equals(Object obj) {	
		if (!super.equals(obj))
			return false;
		return true;
	}




	public void setTheCoffeeHouse(CoffeeHouse theCoffeeHouse) {
		this.theCoffeeHouse = theCoffeeHouse;
	}


	public CoffeeHouse getTheCoffeeHouse() {
		return theCoffeeHouse;
	}


	@Override
	public void useService(Car theCar) {

		gasStationLogger.log(Level.INFO,"Car "+theCar.getId()+ " is getting Service from Cashier (maybe pay..) " + getId()+"\n", this);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	


	@Override
	public String getName() {
		return getClass().getSimpleName();
	}


	
	
}
