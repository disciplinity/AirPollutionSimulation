package graph;

import transport.Car;
import utility.CarStorage;

public class StartingIntersection extends Intersection {

    private int uniqueLabel;
    private CarStorage carStorage;

    public StartingIntersection(int uniqueLabel, CarStorage carStorage) {
        super(uniqueLabel);
        this.carStorage = carStorage;
    }

    public synchronized void registerCar(Car car) {
        carStorage.registerCar(car);
    }
}
