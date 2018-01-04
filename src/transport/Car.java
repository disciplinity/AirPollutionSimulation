package transport;

import buildings.CarService;
import buildings.EnvironmentalProtectionDataCenter;
import graph.Graph;
import graph.Intersection;

import java.util.List;
import java.util.Random;

/**
 * Created by Daiy on 31.12.2017.
 */
public class Car implements Runnable {

    private Random random = new Random();
    private EngineType engineType;
    private Intersection currentIntersection;
    private int streetsCrossed = 0;
    private final Graph graph;
    private EnvironmentalProtectionDataCenter environmentalProtectionDataCenter;
    private final Intersection entryToTheCity;
    private static int totalCarCount = 0;
    private final int carId = ++totalCarCount;
    private boolean wantsToChangeEngine = false;
    private boolean hadEngineChanged = false;
    private int timesStoppedByEPDC = 0;

    public Car(EngineType engineType, Graph graph, EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        this.engineType = engineType;
        this.graph = graph;
        this.environmentalProtectionDataCenter = environmentalProtectionDataCenter;
        currentIntersection = new Intersection(graph.getEntryToCityIntersectionLabels().get(random.nextInt(4)));
        entryToTheCity = new Intersection(currentIntersection.getLabel(), environmentalProtectionDataCenter.getCarStorage());
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public boolean wantsToChangeEngine() {
        return wantsToChangeEngine;
    }

    public boolean hadEngineChanged() {
        return hadEngineChanged;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
        hadEngineChanged = true;
    }

    public int getCarId() {
        return carId;
    }

    public void letEnvironmentalProtectionDataCenterKnowAboutEngineChange() {
        environmentalProtectionDataCenter.getCarStorage().getCars().add(this);
    }

    private boolean allConditionsMetToChangeEngine() {
        return !hadEngineChanged && timesStoppedByEPDC >= 2 && random.nextInt(6) == 3
                && (engineType == EngineType.PETROL || engineType == EngineType.DIESEL);
    }


    @Override
    public String toString() {
        return "Car[" + carId + "/" + engineType + "]";
    }

    @Override
    public void run() {
        entryToTheCity.registerCar(this);
        System.out.println(this + " has ENTERED the graph from graph.Intersection[" + entryToTheCity.getLabel() +"].");
        while (true) {
            try {
                System.out.println(this + " - Current intersection: " + currentIntersection.getLabel());

                if (isOnTheIntersectionWithCarService()) {
                    // One in six chance to decide upon changing the engine
                    if (allConditionsMetToChangeEngine()) {
                        wantsToChangeEngine = true;
                        removeSelfFromEnvironmentalProtectionDataCenter();
                    }

                    goToCarService();
                }

                if (streetsCrossed % 5 == 0 &  streetsCrossed != 0) {
                    sendEngineDataToEnvironmentalProtectionDataCenter();
                }
                if (streetsCrossed % 7 == 0 && streetsCrossed != 0) {
                    boolean hasWaited = askEnvironmentalProtectionDataCenterIfShouldWait();

                    if (hasWaited) {
                        timesStoppedByEPDC++;
                    }
                }

                crossStreet();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Below only private methods

    private void sendEngineDataToEnvironmentalProtectionDataCenter() {
        environmentalProtectionDataCenter.updateTotalAmountOfAirPollution(engineType);
    }

    private void crossStreet() throws InterruptedException {
        Thread.sleep(1000);
//        Thread.sleep(random.nextInt(18) + 3);
        List<Intersection> adjacentIntersections = graph.getAdjIntersections(currentIntersection);
        currentIntersection = adjacentIntersections.get(random.nextInt(adjacentIntersections.size()));
        streetsCrossed++;
    }

    private boolean askEnvironmentalProtectionDataCenterIfShouldWait() throws InterruptedException {
        return environmentalProtectionDataCenter.tellCarToWaitIfPollutionIsTooHigh(engineType);
    }

    private void goToCarService() throws InterruptedException {
        CarService carService = graph.findCarServiceByLabel(currentIntersection.getLabel());
        carService.workOnCar(this);
    }

    private boolean isOnTheIntersectionWithCarService() {
        return graph.getCarServiceIntersectionLabels().contains(currentIntersection.getLabel());
    }

    private void removeSelfFromEnvironmentalProtectionDataCenter() {
        environmentalProtectionDataCenter.getCarStorage().getCars().remove(this);
    }


}
