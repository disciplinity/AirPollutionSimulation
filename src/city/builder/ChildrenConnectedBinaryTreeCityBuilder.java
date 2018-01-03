package city.builder;

import city.map.Graph;
import city.map.Intersection;
import city.map.Street;

import java.util.Arrays;

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

        return graph;
    }
}