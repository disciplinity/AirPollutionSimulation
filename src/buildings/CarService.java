package buildings;


import transport.Car;

/**
 * Created by Daiy on 03.01.2018.
 */
public class CarService {

    private int label;

    public CarService(int label) {
        this.label = label;
    }

    public int getCarServiceLabel() {
        return label;
    }

    public void workOnCar(Car car) {
        System.out.println("working on car");
        System.out.println("done");

    }
}
