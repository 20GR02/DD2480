package decide;

public class Coordinate {
    double x;
    double y;

    public Coordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Coordinate coordinate) {
        double distance = Math.sqrt(
            Math.pow(coordinate.x - this.x, 2) + Math.pow(coordinate.y - this.y, 2)
        );

        return distance;

    }
}
