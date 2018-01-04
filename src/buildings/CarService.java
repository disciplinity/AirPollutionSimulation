package buildings;


import transport.Car;
import transport.EngineType;

import java.util.Random;

/**
 * Created by Daiy on 03.01.2018.
 */
public class CarService {

    final private int label;

    public CarService(int label) {
        this.label = label;
    }

    public int getCarServiceLabel() {
        return label;
    }
    
    public synchronized void workOnCar(Car car) throws InterruptedException{
        System.out.println("Working on car " + car + "...");
        if (car.wantsToChangeEngine()) {
            Random r = new Random();
            EngineType[] engineTypesToChooseFrom = {EngineType.LEMONADE, EngineType.ELECTRIC};
            car.setEngineType(engineTypesToChooseFrom[r.nextInt(2)]);
            System.out.println(car + " had engine changed.");
        }
        Thread.sleep(5000);
        System.out.println("Finished work on " + car + "...");

    }

}
