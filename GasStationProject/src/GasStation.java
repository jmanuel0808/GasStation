import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;
import java.util.logging.Logger;


public class GasStation {
	
	private MainFuelPool mainFuelPool;

	private CoffeeHouse coffeeHouse;
	
	private Vector<Pump> pumps;
	
	
	
	private static double pricePerLiter=7.82;  // for updating the price, otherwise final

	public GasStation(MainFuelPool mainFuelPool, CoffeeHouse coffeeHouse, Vector<Pump> pumps ){
		this.mainFuelPool = mainFuelPool;

		this.coffeeHouse = coffeeHouse;
		this.pumps = pumps;
		
	}
	
	

	@Override
	public String toString() {
		return "GasStation::\n----> " + mainFuelPool + "\n----> "
				+ coffeeHouse + "\n----> pumps:: " + pumps;
	}


	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coffeeHouse == null) ? 0 : coffeeHouse.hashCode());
		result = prime * result
				+ ((mainFuelPool == null) ? 0 : mainFuelPool.hashCode());
		result = prime * result + ((pumps == null) ? 0 : pumps.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {

		GasStation other = (GasStation) obj;
		if (coffeeHouse == null) {
			if (other.coffeeHouse != null)
				return false;
		} else if (!coffeeHouse.equals(other.coffeeHouse))
			return false;
		if (mainFuelPool == null) {
			if (other.mainFuelPool != null)
				return false;
		} else if (!mainFuelPool.equals(other.mainFuelPool))
			return false;
		if (pumps == null) {
			if (other.pumps != null)
				return false;
		} else if (!pumps.equals(other.pumps))
			return false;
		return true;
	}



	public MainFuelPool getMainFuelPool() {
		return mainFuelPool;
	}

	public void setMainFuelPool(MainFuelPool mainFuelPool) {
		this.mainFuelPool = mainFuelPool;
	}

	public CoffeeHouse getCoffeeHouse() {
		return coffeeHouse;
	}

	public void setCoffeeHouse(CoffeeHouse coffeeHouse) {
		this.coffeeHouse = coffeeHouse;
	}

	public Vector<Pump> getPumps() {
		return pumps;
	}

	public void setPumps(Vector<Pump> pumps) {
		this.pumps = pumps;
	}

	

	public static double getPricePerLiter() {
		return pricePerLiter;
	}

	public static void setPricePerLiter(double pricePerLiter) {
		GasStation.pricePerLiter = pricePerLiter;
	}

	
	public void insertToShortestLinePump(Car theCar) {
		
		for (Pump p : pumps){
			int size = p.getWaitingCars().size();
			int x =3;
		}
		
		Pump shortestLinePump = Collections.min(pumps, new Comparator<Pump>() { // return the cashier with the shortest line
		
			@Override
			public int compare(Pump o1, Pump o2) {
				if (o1.getWaitingCars().size() < o2.getWaitingCars().size())
					return -1;
				else if (o1.getWaitingCars().size() > o2.getWaitingCars().size())
					return 1;
				else return 0;
			}
			
		});
		theCar.AddService(shortestLinePump);
	}


	
	
	
	
	
}
