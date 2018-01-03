package Runner;


import controller.Controller;

/**
 * Created by Daiy on 31.12.2017.
 */
public class CityTrafficStarter {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.startSimulation();
    }
}
