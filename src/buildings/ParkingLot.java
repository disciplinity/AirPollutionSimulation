package buildings;

import transport.Car;

public class ParkingLot {

    private volatile boolean isTaken;
    private Car carOnTheParkingLot;

    public ParkingLot(boolean isTaken) {
        this.isTaken = isTaken;
    }

    public void setCarOnTheParkingLot(Car carOnTheParkingLot) {
        this.carOnTheParkingLot = carOnTheParkingLot;
        isTaken = true;
        notifyCarServiceAboutNewClient();


    }

    private void notifyCarServiceAboutNewClient() {
        notify();
    }

    public boolean isTaken() {
        return isTaken;
    }

    public Car getCurrentCar() {
        return carOnTheParkingLot;
    }

    public synchronized void tellCarServiceToWaitForClients() throws InterruptedException{
        wait();
    }
}
