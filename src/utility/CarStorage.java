package utility;

import transport.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daiy on 01.01.2018.
 */
public class CarStorage {

    private List<Car> cars;

    public CarStorage() {
        cars = new ArrayList<>();
    }

    public void registerCar(Car car) {
        synchronized (cars) {
            cars.add(car);
        }
    }

    public void removeCar(Car car) {

    }

    public int countCars() {
        return cars.size();
    }

    public List<Car> getCars() {
        return cars;
    }
}
