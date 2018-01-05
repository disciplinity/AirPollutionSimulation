package controller;

import builders.ChildrenConnectedBinaryTreeCityBuilder;
import builders.CityBuilder;
import buildings.EnvironmentalProtectionDataCenter;
import graph.Graph;
import strategies.JsonStrategy;
import strategies.Strategy;
import transport.Car;
import transport.EngineType;
import tasks.BirdSingingTask;
import utility.CarStorage;
import threadmanagers.BrokenCarController;
import utility.Flag;
import tasks.PollutionResetTimerTask;
import tasks.ProgrammeEnderTask;

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
     *                               4 O____O   O____O 7
     *                                /\   /\   /\  /\
     *                               / \  / \  / \ /  \
     *                            8 O__O O__O O__O O__O 15
     *                              ...
     *                              ...
     *
     *                              PROGRAMME STOPS WORKING WHEN ALL CARS HAVE MARMALADE TIRES.
     */
    public void startSimulation() {

        CarStorage carStorage = new CarStorage();
        BrokenCarController brokenCarController = new BrokenCarController();
        Strategy overviewStrategy = new JsonStrategy();
        EnvironmentalProtectionDataCenter environmentalProtectionDataCenter = new EnvironmentalProtectionDataCenter(carStorage, brokenCarController, overviewStrategy);
        Flag flag = new Flag(environmentalProtectionDataCenter);
        Graph graph = new Graph(environmentalProtectionDataCenter);
        environmentalProtectionDataCenter.setFlag(flag);
        brokenCarController.setGraph(graph);

        CityBuilder interConnectedBinaryTreeCityBuilder = new ChildrenConnectedBinaryTreeCityBuilder(graph);
        interConnectedBinaryTreeCityBuilder.build();

        EngineType[] engineTypes = {EngineType.ELECTRIC, EngineType.LEMONADE, EngineType.DIESEL,  EngineType.PETROL};
        final int CARS_IN_THE_CITY = 1;
        ExecutorService executorService = Executors.newFixedThreadPool(CARS_IN_THE_CITY);
        EngineType engineType;
        Random random = new Random();

        Thread timerThread = new Thread(new PollutionResetTimerTask(flag));
        timerThread.start();

        Thread birdThread = new Thread(new BirdSingingTask(environmentalProtectionDataCenter));
        birdThread.start();

        Thread programmeStoppingThread = new Thread(new ProgrammeEnderTask(environmentalProtectionDataCenter, CARS_IN_THE_CITY));
        programmeStoppingThread.start();

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
