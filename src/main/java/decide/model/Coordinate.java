package decide.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinate {
    @JsonProperty
    double x;
    @JsonProperty
    double y;

    @JsonCreator
    public Coordinate(
            @JsonProperty("x") double x,
            @JsonProperty("y") double y) {
        this.x = x;
        this.y = y;
    }

    public static double distance(Coordinate coordinate1, Coordinate coordinate2) {
        double distance = Math.sqrt(
                Math.pow(coordinate1.x - coordinate2.x, 2) + Math.pow(coordinate1.y - coordinate2.y, 2));

        return distance;

    }

    /**
     * Calculate the area of a triangle given three vertices
     *
     * @param vertexA the first vertex of the triangle
     * @param vertexB the second vertex of the triangle
     * @param vertexC the third vertex of the triangle
     * @return the area of the triangle
     */
    public static double areaOfTriangle(Coordinate vertexA, Coordinate vertexB, Coordinate vertexC) {
        double deltaY_AB = vertexA.y - vertexB.y;
        double deltaY_BC = vertexB.y - vertexC.y;
        double deltaY_CA = vertexC.y - vertexA.y;

        double term1 = vertexA.x * deltaY_BC;
        double term2 = vertexB.x * deltaY_CA;
        double term3 = vertexC.x * deltaY_AB;

        double totalSum = term1 + term2 + term3;

        double triangleArea = Math.abs(totalSum) / 2;

        return triangleArea;
    }
}
