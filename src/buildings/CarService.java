package buildings;


import transport.Car;
import transport.EngineType;

import java.util.Random;

/**
 * Created by Daiy on 03.01.2018.
 */
public class CarService implements Runnable {

    final private int label;
    final private ParkingLot parkingLot;

    public CarService(int label, ParkingLot parkingLot) {
        this.label = label;
        this.parkingLot = parkingLot;
    }

    public int getCarServiceLabel() {
        return label;
    }

    @Override
    public void run() {

        while (true) {
            try {
                if (parkingLot.isTaken()) {
                    workOnCar(parkingLot.getCurrentCar());
                } else {
                    waitForCarsToCome();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void workOnCar(Car car) throws InterruptedException{
        System.out.println("Working on car " + car + "...");
        if (car.wantsToChangeEngine()) {
            Random r = new Random();
            EngineType[] engineTypesToChooseFrom = {EngineType.LEMONADE, EngineType.ELECTRIC};
            car.setEngineType(engineTypesToChooseFrom[r.nextInt(2)]);
            System.out.println(car + " had engine changed.");
        }
        Thread.sleep(50);
        System.out.println("Finished work on " + car + "...");

    }

    private void waitForCarsToCome() {
        parkingLot.tellCarServiceToWaitForClients();
    }
}
