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

    public double areaOfTriangle(Coordinate vertexB, Coordinate vertexC) {
        double deltaY_BC = vertexB.y - vertexC.y;
        double deltaY_CA = vertexC.y - this.y;
        double deltaY_AB = this.y - vertexB.y;

        double term1 = this.x * deltaY_BC;
        double term2 = vertexB.x * deltaY_CA;
        double term3 = vertexC.x * deltaY_AB;

        double totalSum = term1 + term2 + term3;

        double triangleArea = Math.abs(totalSum) / 2;
        
        return triangleArea;
    }
}
