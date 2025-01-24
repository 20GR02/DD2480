package decide;

public class Coordinate {
    double x;
    double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double distance(Coordinate coordinate1, Coordinate coordinate2) {
        double distance = Math.sqrt(
                Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));

        return distance;

    }
}
