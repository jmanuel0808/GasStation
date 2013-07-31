import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CoffeeHouse {
	
	

	private Vector<Cashier> cashiers;
	private Vector<Car> aroundCars; // for around state 
	
	private static Logger gasStationLogger = Logger.getLogger("gasStationLogger");
	
	private FileHandler coffeeHouseHandler;
	
	public CoffeeHouse(Vector<Cashier> cashiers) throws SecurityException, IOException {
		setCashiers(cashiers);
		setAroundCars( new Vector<Car>());
		
		coffeeHouseHandler = new FileHandler("CoffeeHouseLog\\"+ gasStationLogger.getName()+ "_"+" CoffeeHouse.xml");
		coffeeHouseHandler.setFilter(new MyFilter("CoffeeHouse", 1));
		coffeeHouseHandler.setFormatter(new MyFormatter());
		gasStationLogger.addHandler(coffeeHouseHandler);

	}
	
	public void moveCartoInline(Car theCar){ //From aroundCars or on the car initializing 
		
		Cashier shortestLineCashier = Collections.min(cashiers, new Comparator<Cashier>() { // return the cashier with the shortest line

			@Override
			public int compare(Cashier o1, Cashier o2) {
				if (o1.getWaitingCars().size() < o2.getWaitingCars().size())
					return -1;
				else if (o1.getWaitingCars().size() > o2.getWaitingCars().size())
					return 1;
				else return 0;
			}
			
		});
		gasStationLogger.log(Level.INFO,"Car #" + theCar.getId() + " is moving to  Cashier "+ shortestLineCashier.getId() +"\n", this);
		aroundCars.remove(theCar);  
		theCar.AddService(shortestLineCashier);
	
	}
	
	
	
	public Car getCarById(int carId){
		for ( Car car : aroundCars){
			if (car.getId() == carId)
				return car;
		}
		return null;
	}
	
	
	


	@Override
	public String toString() {
		return "CoffeeHouse:: numOfCashier=" + cashiers.size() + "\n\t"
				+ cashiers + "\n\t" + aroundCars;
	}





	@Override
	public boolean equals(Object obj) {
		CoffeeHouse other = (CoffeeHouse) obj;
		if (cashiers == null) {
			if (other.cashiers != null)
				return false;
		} else if (!cashiers.equals(other.cashiers))
			return false;
		return true;
	}





	public Vector<Cashier> getCashiers() {
		return cashiers;
	}


	public void setCashiers(Vector<Cashier> cashiers) {
		this.cashiers = cashiers;
	}


	public Vector<Car> getAroundCars() {
		return aroundCars;
	}


	public void setAroundCars(Vector<Car> around_cars) {
		this.aroundCars = around_cars;
	}
	
	
	
	
	
	

	
	
	
	
	
}
