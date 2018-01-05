package transport;

import graph.Graph;
import graph.Intersection;
import threadmanagers.BrokenCarController;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class TowingCar extends Car implements Runnable {

    private BrokenCarController brokenCarController;
    private Graph graph;
    private Intersection currentIntersection;

    public TowingCar(Graph graph) {
        super();
        this.graph = graph;
        Random random = new Random();

        currentIntersection = graph.getIntersections().stream()
                .filter(s -> graph.getEntryToCityIntersectionLabels().contains(s.getLabel()))
                .collect(Collectors.toList()).get(random.nextInt(graph.getEntryToCityIntersectionLabels().size()));
    }


//    public TowingCar(BrokenCarController brokenCarController) {
//        this.brokenCarController = brokenCarController;
//    }

    public void setBrokenCarController(BrokenCarController brokenCarController) {
        this.brokenCarController = brokenCarController;
    }

    @Override
    protected void crossStreet() throws InterruptedException{
        Random random = new Random();
        Thread.sleep(random.nextInt(18) + 3);
        List<Intersection> adjacentIntersections = graph.getAdjIntersections(currentIntersection);
        currentIntersection = adjacentIntersections.get(random.nextInt(adjacentIntersections.size()));
        if (currentIntersection.getBrokenCars().size() > 0) {

            Car brokenCar = currentIntersection.getBrokenCars().remove(0);
            brokenCar.setMarmeladeTires();

            System.out.println("Towing car has found BROKEN " + brokenCar + " at Intersection " + currentIntersection.getLabel());
            if (brokenCar.getEngineType() == EngineType.ELECTRIC || brokenCar.getEngineType() == EngineType.LEMONADE) {
                brokenCar.setMarmeladeTires();
                System.out.println(brokenCar + " has now marmalade tires!");
            }
            brokenCar.blowTires();
            System.out.println("FIXED TIRES " + brokenCar);
            brokenCarController.releaseBrokenCars();

        }
    }


}
