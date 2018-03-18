package tasks;

import buildings.EnvironmentalProtectionDataCenter;
import transport.Car;

public class ProgrammeEnderTask implements Runnable {

    private EnvironmentalProtectionDataCenter environmentalProtectionDataCenter;
    private final int TOTAL_AMOUNT_OF_CARS;

    public ProgrammeEnderTask(EnvironmentalProtectionDataCenter environmentalProtectionDataCenter, int TOTAL_AMOUNT_OF_CARS) {
        this.environmentalProtectionDataCenter = environmentalProtectionDataCenter;
        this.TOTAL_AMOUNT_OF_CARS = TOTAL_AMOUNT_OF_CARS;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10000);
                if (allCarsHaveMarmaladeTires()) {
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean allCarsHaveMarmaladeTires() {
        return environmentalProtectionDataCenter.getCarStorage().getCars().stream()
                .filter(Car::hasMarmaladeTires)
                .count() == TOTAL_AMOUNT_OF_CARS;
    }
}
