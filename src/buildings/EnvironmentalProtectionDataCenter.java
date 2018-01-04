package buildings;

import graph.Intersection;
import transport.Car;
import transport.EngineType;
import transport.CarStorage;
import utility.Flag;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.DoubleAdder;



/**
 * Created by Daiy on 03.01.2018.
 */
public class EnvironmentalProtectionDataCenter {

    private CarStorage carStorage;
    private Flag flag;
    private DoubleAdder amountOfAirPollution;

    public EnvironmentalProtectionDataCenter(CarStorage carStorage) {
        this.carStorage = carStorage;
        amountOfAirPollution = new DoubleAdder();
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    public CarStorage getCarStorage() {
        return carStorage;
    }

    public synchronized void allowCarsToDrive() {
        resetAirPollutionDependingOnCarsInTown();
        System.out.println("Waiting cars can move again. transport.Car pollution reduced.");
        notifyAll();

    }

    public synchronized void updateTotalAmountOfAirPollution(EngineType engineType) {

        final double DIESEL_AIR_POLLUTION_PER_FIVE_STREETS = 5 * 3.0;
        final double PETROL_AIR_POLLUTION_PER_FIVE_STREETS = 5 * 2.0;
        final double LEMONADE_AIR_POLLUTION_PER_FIVE_STREETS = 5 * 0.5;
        final double ELECTRIC_AIR_POLLUTION_PER_FIVE_STREETS = 5 * 0.1;

        switch (engineType) {
            case DIESEL:
                amountOfAirPollution.add(DIESEL_AIR_POLLUTION_PER_FIVE_STREETS);
                break;
            case PETROL:
                amountOfAirPollution.add(PETROL_AIR_POLLUTION_PER_FIVE_STREETS);
                break;
            case LEMONADE:
                amountOfAirPollution.add(LEMONADE_AIR_POLLUTION_PER_FIVE_STREETS);
                break;
            case ELECTRIC:
                amountOfAirPollution.add(ELECTRIC_AIR_POLLUTION_PER_FIVE_STREETS);
                break;
            default:
                System.out.println("The engine type has not been recognize and therefore we cannot update the air pollution.");
                break;
        }

    }

    public synchronized boolean tellCarToWaitIfPollutionIsTooHigh(EngineType engineType) throws InterruptedException {
        if ((engineType == EngineType.DIESEL && amountOfAirPollution.doubleValue() >= 20) ||
                (engineType == EngineType.PETROL && amountOfAirPollution.doubleValue() >= 30)) {

            if (flag.timerIsOff()) {
                flag.turnTimerOn();
            }
            wait();
            return true;
        }
        return false;

    }

    public double getAmountOfAirPollution() {
        return amountOfAirPollution.doubleValue();
    }

    private void resetAirPollutionDependingOnCarsInTown() {
        long carsWithInternalCombustionEngine = carStorage.getCars().stream()
                .filter(s -> s.getEngineType() == EngineType.DIESEL || s.getEngineType() == EngineType.PETROL)
                .count();

        double fortyPercentOfAmountOfAirPollutionBeforeReset = amountOfAirPollution.doubleValue() * 0.4;
        // Now rounding to two decimal places
        fortyPercentOfAmountOfAirPollutionBeforeReset = Math.round(fortyPercentOfAmountOfAirPollutionBeforeReset * 100);
        fortyPercentOfAmountOfAirPollutionBeforeReset = fortyPercentOfAmountOfAirPollutionBeforeReset/100;

        amountOfAirPollution.reset();

        if (carsWithInternalCombustionEngine >= 70) {

            amountOfAirPollution.add(fortyPercentOfAmountOfAirPollutionBeforeReset);
        }
        System.out.println("Polution reduced back to: " + amountOfAirPollution.doubleValue());
    }

    public synchronized void sendOutHelpingTowCar() throws InterruptedException {
        System.out.println("Tow-in car sent to help.");
        wait();
    }

}
