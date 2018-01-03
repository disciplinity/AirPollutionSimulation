package controller;

import builders.ChildrenConnectedBinaryTreeCityBuilder;
import builders.CityBuilder;
import buildings.EnvironmentalProtectionDataCenter;
import graph.Graph;
import transport.Car;
import transport.EngineType;
import utility.BirdSingingTask;
import utility.CarStorage;
import utility.Flag;
import utility.PollutionResetTimerTask;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Daiy on 31.12.2017.
 */
public class Controller {

    /**
     *     City map is this kind of binary tree but with each child element connected with adjacent child elements.
     *
     *                                          O 1
     *                                        /  \
     *                                      /     \
     *                                   2 O _____ O 3
     *                                   / \      / \
     *                                  /   \    /   \
     *                               4 O____O___O____O 7
     *                                /\   /\   /\  /\
     *                               / \  / \  / \ /  \
     *                            8 O__O_O__O_O__O_O__O 15
     *                              ...
     *                              ...
     */
    public void startSimulation() {

        CarStorage carStorage = new CarStorage();
        EnvironmentalProtectionDataCenter environmentalProtectionDataCenter = new EnvironmentalProtectionDataCenter(carStorage);
        Flag flag = new Flag(environmentalProtectionDataCenter);
        environmentalProtectionDataCenter.setFlag(flag);
        Graph graph = new Graph(environmentalProtectionDataCenter);
        CityBuilder interConnectedBinaryTreeCityBuilder = new ChildrenConnectedBinaryTreeCityBuilder(graph);
        interConnectedBinaryTreeCityBuilder.build();

        EngineType[] engineTypes = {EngineType.ELECTRIC, EngineType.LEMONADE, EngineType.DIESEL,  EngineType.PETROL};
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        final int CARS_IN_THE_CITY = 200;
        EngineType engineType;
        Random random = new Random();

        Thread timerThread = new Thread(new PollutionResetTimerTask(flag));
        timerThread.start();

        Thread birdThread = new Thread(new BirdSingingTask(environmentalProtectionDataCenter));
        birdThread.start();

        // Putting cars in the graph
        for (int i = 1; i <= CARS_IN_THE_CITY; i++) {
            if (i <= CARS_IN_THE_CITY / 10) { // First 10% of cars will be either with lemonade or electric engine
                engineType = engineTypes[random.nextInt(2)];
            } else {
                engineType = engineTypes[random.nextInt(2) + 2]; // Rest will be diesel or petrol
            }
            executorService.submit(new Car(engineType, graph, environmentalProtectionDataCenter));
        }
    }

}
