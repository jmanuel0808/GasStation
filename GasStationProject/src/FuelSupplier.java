

public class FuelSupplier extends Thread {
	
	@Override
	public void run() {
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			fuelPools.putFuel();
			
	}
	
	private MainFuelPool fuelPools; 
	
	public FuelSupplier(MainFuelPool fuelPools ){
		this.setFuelPools(fuelPools);
		
	}

	public MainFuelPool getFuelPools() {
		return fuelPools;
	}

	public void setFuelPools(MainFuelPool fuelPools) {
		this.fuelPools = fuelPools;
	}



	
	
	

}
