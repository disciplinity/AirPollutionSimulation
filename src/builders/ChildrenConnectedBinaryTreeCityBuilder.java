package builders;

import graph.Graph;
import graph.Intersection;
import graph.Street;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Daiy on 31.12.2017.
 */
public class ChildrenConnectedBinaryTreeCityBuilder implements CityBuilder {

    private Graph graph;

    public ChildrenConnectedBinaryTreeCityBuilder(Graph graph) {
        this.graph = graph;
    }

    public Graph build() {

        // Making binary tree (the root element is 1)
        Intersection intersection1, intersection2, intersection3;

        for (int i = 1; i < 16; i++) {
            intersection1 = new Intersection(i); // Parent
            intersection2 = new Intersection(2 * i); // Child
            intersection3 = new Intersection(2 * i + 1); // Child

            graph.addIntersection(Arrays.asList(intersection1, intersection2, intersection3));
            graph.addStreet(new Street(intersection1, intersection2));
            graph.addStreet(new Street(intersection1, intersection3));
            graph.addStreet(new Street(intersection2, intersection3));
        }

        System.out.println(graph.getIntersectionLabelsBetweenBadRoads());
        List<Intersection> intersections = new ArrayList<>(graph.getIntersections());
        System.out.println(graph.getAdjIntersections(intersections.get(13)));
        return graph;
    }




}
