package transport;

import buildings.CarService;
import buildings.EnvironmentalProtectionDataCenter;
import graph.Graph;
import graph.Intersection;
import graph.StartingIntersection;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by Daiy on 31.12.2017.
 */
public class Car implements Runnable {

    private Random random = new Random();
    private EngineType engineType;
    private Intersection currentIntersection;
    private int badStreetsCrossed = 0;
    private int streetsCrossed = 0;
    private  Graph graph;
    private EnvironmentalProtectionDataCenter environmentalProtectionDataCenter;
    private  StartingIntersection entryToTheCity;
    private static int totalCarCount = 0;
    private final int carId = ++totalCarCount;
    private boolean wantsToChangeEngine = false;
    private boolean hadEngineChanged = false;
    private int timesStoppedByEPDC = 0;

    public Car() {
    }

    public Car(EngineType engineType, Graph graph, EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        this.engineType = engineType;
        this.graph = graph;
        this.environmentalProtectionDataCenter = environmentalProtectionDataCenter;
        currentIntersection = getRandomEntryIntersection();
        entryToTheCity = new StartingIntersection(currentIntersection.getLabel(), environmentalProtectionDataCenter.getCarStorage());
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public boolean wantsToChangeEngine() {
        return wantsToChangeEngine;
    }

    public void setEngineType(EngineType engineType) {
        this.engineType = engineType;
        hadEngineChanged = true;
    }

    public void setWantsToChangeEngine(boolean wantsToChangeEngine) {
        this.wantsToChangeEngine = wantsToChangeEngine;
    }

    public int getCarId() {
        return carId;
    }




    @Override
    public String toString() {
        return "Car[" + carId + "/" + engineType + "]";
    }

    @Override
    public void run() {
        entryToTheCity.registerCar(this);
//        System.out.println(this + " has ENTERED the graph from Intersection[" + entryToTheCity.getLabel() +"].");
        while (true) {
            try {

                System.out.println(this + " - Current intersection: " + currentIntersection.getLabel());

                if (isOnTheIntersectionWithCarService()) {
                    // One in six chance to decide upon changing the engine
                    if (allConditionsMetToChangeEngine()) {
                        wantsToChangeEngine = true;
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
//        Thread.sleep(1000);
        int intersectionLabelWeCameFrom = currentIntersection.getLabel();
        Thread.sleep(random.nextInt(18) + 3);
        List<Intersection> adjacentIntersections = graph.getAdjIntersections(currentIntersection);
        currentIntersection = adjacentIntersections.get(random.nextInt(adjacentIntersections.size()));
        int intersectionLabelWeWentTo = currentIntersection.getLabel();
        streetsCrossed++;

        if (crossedBadStreet(intersectionLabelWeCameFrom, intersectionLabelWeWentTo)) {
            badStreetsCrossed++;

            if (badStreetsCrossed == 3) { // Goes back to 0 when it gets fixed by another car
                environmentalProtectionDataCenter.sendOutHelpingTowCar();
            }
        }

    }

    private boolean crossedBadStreet(int firstIntersectionLabel, int secondIntersectionLabel) {
        for (List<Integer> labels : graph.getIntersectionLabelsBetweenBadRoads()) {
            if (labels.contains(firstIntersectionLabel) && labels.contains(secondIntersectionLabel)) {
                return true;
            }
        }
        return false;
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

    private boolean allConditionsMetToChangeEngine() {
        return !hadEngineChanged && timesStoppedByEPDC >= 2 && random.nextInt(6) == 3
                && (engineType == EngineType.PETROL || engineType == EngineType.DIESEL);
    }

    private Intersection getRandomEntryIntersection() {
        return graph.getIntersections().stream()
                .filter(s -> graph.getEntryToCityIntersectionLabels().contains(s.getLabel()))
                .collect(Collectors.toList()).get(random.nextInt(graph.getEntryToCityIntersectionLabels().size()));

    }


}
