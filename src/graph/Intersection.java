package graph;


import transport.Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Daiy on 31.12.2017.
 * Credits for the implementation go to https://gist.github.com/smddzcy/bf8fc17dedf4d40b0a873fc44f855a58
 */
public class Intersection {
    private int uniqueLabel;
    private List<Car> brokenCars;

    public Intersection() {
    }

    public Intersection(int uniqueLabel) {
        super();
        this.uniqueLabel = uniqueLabel;
        brokenCars = new ArrayList<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Intersection)) return false;

        Intersection _obj = (Intersection) obj;
        return _obj.uniqueLabel == uniqueLabel;
    }

    @Override
    public int hashCode() {
        return uniqueLabel;
    }

    public int getLabel() {
        return uniqueLabel;
    }

    public void setLabel(int uniqueLabel) {
        this.uniqueLabel = uniqueLabel;
    }

    @Override
    public String toString() {
        return String.valueOf(uniqueLabel);
    }

    public synchronized List<Car> getBrokenCars() {
        return brokenCars;
    }

    public synchronized void addBrokenCar(Car car) {
        if (!brokenCars.contains(car)) {
            brokenCars.add(car);
        }
    }

}