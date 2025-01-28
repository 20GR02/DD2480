package decide.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import decide.model.enums.CompTypeEnum;

public class Coordinate {
    @JsonProperty
    private double x;
    @JsonProperty
    private double y;

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

    /**
     * Calculates the circumcircle radius of a triangle formed by three coordinates.
     *
     * @param coordinate1 the first coordinate
     * @param coordinate2 the second coordinate
     * @param coordinate3 the third coordinate
     * @return the circumcircle radius of the triangle formed by the three coordinates
     */
    public static double circumcircleRadius(Coordinate coordinate1, Coordinate coordinate2, Coordinate coordinate3) {
        double a = Coordinate.distance(coordinate1, coordinate2);
        double b = Coordinate.distance(coordinate2, coordinate3);
        double c = Coordinate.distance(coordinate1, coordinate3);

        double semiPerimeter = (a + b + c) / 2;
        double area = Math.sqrt(semiPerimeter * (semiPerimeter - a) * (semiPerimeter - b) * (semiPerimeter - c));
        double radius = (a * b * c) / (4 * area);

        return radius;
    }

    /**
     * Calculates the angle (in radians) formed at the vertex defined by three coordinates.
     *
     * The second coordinate is always considered the vertex of the angle. The function computes
     * the angle between the vectors formed by the first-to-second and third-to-second points using the dot product forumla.
     * If either of the vectors has zero magnitude (e.g., the first or third point coincides with the vertex),
     * the angle is undefined, and the method returns {@code Double.NaN}.
     *
     * @param coordinate1 The first coordinate, representing one endpoint of the angle.
     * @param coordinate2 The vertex of the angle.
     * @param coordinate3 The third coordinate, representing the other endpoint of the angle.
     * @return The angle (in radians) formed at the vertex, or {@code Double.NaN} if the angle is undefined.
     */
    public static double angleAtVertex(Coordinate coordinate1, Coordinate coordinate2, Coordinate coordinate3) {
        double vector1_X = coordinate1.x - coordinate2.x;
        double vector1_Y = coordinate1.y - coordinate2.y;

        double vector2_X = coordinate3.x - coordinate2.x;
        double vector2_Y = coordinate3.y - coordinate2.y;

        double dotProduct = (vector1_X * vector2_X) + (vector1_Y * vector2_Y);
        double magnitudeV1 = Math.sqrt(vector1_X * vector1_X + vector1_Y * vector1_Y);
        double magnitudeV2 = Math.sqrt(vector2_X * vector2_X + vector2_Y * vector2_Y);

        if (magnitudeV1 == 0 || magnitudeV2 == 0) {
            return Double.NaN;
        }

        double cosTheta = dotProduct / (magnitudeV1 * magnitudeV2);
        double theta = Math.acos(cosTheta);

        return theta;
    }

    /**
     * Calculate the circumcenter of a triangle given three vertices
     *
     * @param p1 the first vertex of the triangle
     * @param p2 the second vertex of the triangle
     * @param p3 the third vertex of the triangle
     * @return the circumcenter of the triangle
     */
    public static Coordinate calculateCircumcenter(Coordinate p1, Coordinate p2, Coordinate p3) {
        double D = 2 * (p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y));

        if (CompTypeEnum.doubleCompare(D, 0) == CompTypeEnum.EQ) {
            return null;
        }

        double x_c = ((p1.x * p1.x + p1.y * p1.y) * (p2.y - p3.y) +
                (p2.x * p2.x + p2.y * p2.y) * (p3.y - p1.y) +
                (p3.x * p3.x + p3.y * p3.y) * (p1.y - p2.y)) / D;

        double y_c = ((p1.x * p1.x + p1.y * p1.y) * (p3.x - p2.x) +
                (p2.x * p2.x + p2.y * p2.y) * (p1.x - p3.x) +
                (p3.x * p3.x + p3.y * p3.y) * (p2.x - p1.x)) / D;

        return new Coordinate(x_c, y_c);
    }

    /**
     * Calculate the distance between a point and a line
     *
     * @param p         the point
     * @param lineStart the start point of the line
     * @param lineEnd   the end point of the line
     * @return the distance between the point and the line
     */
    public static double pointToLineDistance(Coordinate p, Coordinate lineStart, Coordinate lineEnd) {
        double dx = lineEnd.x - lineStart.x;
        double dy = lineEnd.y - lineStart.y;

        double denominator = Math.sqrt(dx * dx + dy * dy);
        if (CompTypeEnum.doubleCompare(denominator, 0) == CompTypeEnum.EQ) {
            return Coordinate.distance(p, lineStart);
        }

        double numerator = Math.abs(
                dy * p.x - dx * p.y +
                        lineEnd.x * lineStart.y - lineEnd.y * lineStart.x
        );
        return numerator / denominator;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
