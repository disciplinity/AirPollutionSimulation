package graph;

import buildings.CarService;
import buildings.EnvironmentalProtectionDataCenter;
import transport.Car;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Daiy on 31.12.2017.
 * Credits for the graph implementation go to https://gist.github.com/smddzcy/bf8fc17dedf4d40b0a873fc44f855a58.
 */
public class Graph {
    private Set<Intersection> intersections;
    private Set<Street> streets;
    private Map<Intersection, Set<Street>> adjList;

    private List<CarService> carServices;
    private EnvironmentalProtectionDataCenter environmentalProtectionDataCenter;
    private final List<Integer> entryToCityIntersectionLabels = Arrays.asList(1, 16, 22, 31);
    private final List<Integer> carServiceIntersectionLabels = Arrays.asList(8, 10, 12, 14);
    private final List<List<Integer>> intersectionLabelsBetweenBadRoads = Arrays.asList(Arrays.asList(1, 2), Arrays.asList(12, 13));

    public Graph(EnvironmentalProtectionDataCenter environmentalProtectionDataCenter) {
        carServices = Arrays.asList(new CarService(8), new CarService(10), new CarService(12), new CarService(14));
        intersections = new HashSet<>();
        streets = new HashSet<>();
        adjList = new HashMap<>();
        this.environmentalProtectionDataCenter = environmentalProtectionDataCenter;
    }

    public List<Integer> getEntryToCityIntersectionLabels() {
        return entryToCityIntersectionLabels;
    }

    public List<Integer> getCarServiceIntersectionLabels() {
        return carServiceIntersectionLabels;
    }

    public List<List<Integer>> getIntersectionLabelsBetweenBadRoads() { return intersectionLabelsBetweenBadRoads; }

    public CarService findCarServiceByLabel(int label) {
        for (CarService carService : carServices) {
            if (carService.getCarServiceLabel() == label) {
                return carService;
            }
        }
        return null; // Shouldn't happen
    }

    public EnvironmentalProtectionDataCenter getEnvironmentalProtectionDataCenter() {
        return environmentalProtectionDataCenter;
    }

    public boolean addIntersection(int label) {
        return intersections.add(new Intersection(label));
    }

    public boolean addIntersection(Intersection intersection) {
        return intersections.add(intersection);
    }

    public boolean addIntersection(Collection<Intersection> intersections) {
        return this.intersections.addAll(intersections);
    }

    public boolean removeIntersection(int label) {
        return intersections.remove(new Intersection(label));
    }

    public boolean removeIntersection(Intersection intersection) {
        return intersections.remove(intersection);
    }

    public boolean addStreet(Street street) {
        if (!streets.add(street)) return false;

        adjList.putIfAbsent(street.getIntersection1(), new HashSet<>());
        adjList.putIfAbsent(street.getIntersection2(), new HashSet<>());

        adjList.get(street.getIntersection1()).add(street);
        adjList.get(street.getIntersection2()).add(street);

        return true;
    }

    public boolean addStreet(int intersectionLabel1, int intersectionLabel2) {
        return addStreet(new Street(new Intersection(intersectionLabel1),
                new Intersection(intersectionLabel2)));
    }

    public boolean removeStreet(Street street) {
        if (!streets.remove(street)) return false;
        Set<Street> edgesOfV1 = adjList.get(street.getIntersection1());
        Set<Street> edgesOfV2 = adjList.get(street.getIntersection2());

        if (edgesOfV1 != null) edgesOfV1.remove(street);
        if (edgesOfV2 != null) edgesOfV2.remove(street);

        return true;
    }

    public boolean removeStreet(int intersectionLabel1, int intersectionLabel2) {
        return removeStreet(new Street(new Intersection(intersectionLabel1),
                new Intersection(intersectionLabel2)));
    }

    public List<Intersection> getAdjIntersections(Intersection intersection) {
        return adjList.get(intersection).stream()
                .map(street -> street.getIntersection1().equals(intersection) ? street.getIntersection2()
                        : street.getIntersection1()).collect(Collectors.toList());
    }

    public Set<Intersection> getIntersections() {
        return Collections.unmodifiableSet(intersections);
    }

    public Set<Street> getStreets() {
        return Collections.unmodifiableSet(streets);
    }

    public Map<Intersection, Set<Street>> getAdjList() {
        return Collections.unmodifiableMap(adjList);
    }
}