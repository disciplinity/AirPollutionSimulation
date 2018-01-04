package buildings;


import transport.Car;
import transport.EngineType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Daiy on 03.01.2018.
 */
public class CarService {

    final private int label;
    final private List<Car> fixedCars;

    public CarService(int label) {
        this.label = label;
        fixedCars = new ArrayList<>();
    }

    public int getCarServiceLabel() {
        return label;
    }
    
    public synchronized void workOnCar(Car car) throws InterruptedException{
        System.out.println("Working on car " + car + "...");
        Thread.sleep(50);
        if (car.wantsToChangeEngine()) {

            Random r = new Random();
            EngineType[] engineTypesToChooseFrom = {EngineType.LEMONADE, EngineType.ELECTRIC};
            car.setEngineType(engineTypesToChooseFrom[r.nextInt(2)]);

            System.out.println(car + " had engine changed.");
            car.setWantsToChangeEngine(false);
        }

        System.out.println("Finished working on " + car + "...");
        if (!fixedCars.contains(car)) {
            fixedCars.add(car);
        }
    }


}
