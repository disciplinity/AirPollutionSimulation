package threadmanagers;

import graph.Graph;
import transport.TowingCar;

public class BrokenCarController {

    private Graph graph;

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public synchronized void fixCar() throws InterruptedException{
        sendOutHelpingCar();
        System.out.println("Helping car has been sent out!");
        wait();
    }

    private void sendOutHelpingCar() {
        TowingCar towingCar = new TowingCar(graph);
        towingCar.setBrokenCarController(this);
        Thread thread = new Thread(towingCar);
        thread.start();
    }

    public synchronized void releaseBrokenCars() {
        Thread.interrupted();
        notifyAll();
    }
}
