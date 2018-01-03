package graph;

/**
 * Created by Daiy on 31.12.2017.
 * Credits for implementation go to https://gist.github.com/smddzcy/bf8fc17dedf4d40b0a873fc44f855a58
 */
public class Street {

    private static final int DEFAULT_WEIGHT = 1;
    private Intersection intersection1, intersection2;
    private int weight;

    public Street(Intersection intersection1, Intersection intersection2) {
        this(intersection1, intersection2, DEFAULT_WEIGHT);
    }

    public Street(Intersection intersection1, Intersection intersection2, int weight) {
        super();
        this.intersection1 = intersection1;
        this.intersection2 = intersection2;
        this.weight = weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Street)) return false;

        Street _obj = (Street) obj;
        return _obj.intersection1.equals(intersection1) && _obj.intersection2.equals(intersection2) &&
                _obj.weight == weight;
    }

    @Override
    public int hashCode() {
        int result = intersection1.hashCode();
        result = 31 * result + intersection2.hashCode();
        result = 31 * result + weight;
        return result;
    }

    public Intersection getIntersection1() {
        return intersection1;
    }

    public Intersection getIntersection2() {
        return intersection2;
    }
}