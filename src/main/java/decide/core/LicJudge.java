package decide.core;

import decide.model.Coordinate;
import decide.model.Parameters;

public class LicJudge {

    public boolean[] judge(Coordinate[] coordinates, Parameters parameters) {
        boolean[] lic = new boolean[15];

        lic[0] = judgeLic0(coordinates, parameters.getLength1());
        lic[1] = judgeLic1(coordinates, parameters.getRadius1());
        lic[2] = judgeLic2();
        lic[3] = judgeLic3(coordinates, parameters.getArea1());
        lic[4] = judgeLic4();
        lic[5] = judgeLic5();
        lic[6] = judgeLic6(coordinates, parameters.getnPoints(), parameters.getDist());
        lic[7] = judgeLic7();
        lic[8] = judgeLic8();
        lic[9] = judgeLic9();
        lic[10] = judgeLic10();
        lic[11] = judgeLic11();
        lic[12] = judgeLic12();
        lic[13] = judgeLic13();
        lic[14] = judgeLic14();

        return lic;
    }


    /**
     * Checks if there exists at least one set of two consecutive data points
     * in the given coordinate system that are a distance greater than
     * the specified length, LENGTH1, apart.
     *
     * @param coordinates an array representing the coordinate system, where each
     *                    element is a pair of (x, y) coordinates
     * @param length1     the threshold distance (LENGTH1) to compare against
     * @return {@code true} if there exists at least one pair of consecutive
     * points with a distance greater than LENGTH1, {@code false} otherwise
     */
    public boolean judgeLic0(Coordinate[] coordinates, double length1) {

        if (coordinates == null || coordinates.length < 2) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 1; i++) {
            if (Coordinate.distance(coordinates[i], coordinates[i + 1]) > length1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if there exists at least one set of three consecutive data points
     * that cannot all be contained within or on a circle of radius RADIUS1.
     *
     * @param coordinates an array representing the coordinate system, where each
     *                    element is a pair of (x, y) coordinates
     * @param radius1     the threshold radius (RADIUS1) to compare the circumradius against
     * @return {@code true} if there exists at least one set of consecutive
     * points with a circumradius greater than RADIUS1, {@code false} otherwise
     */
    public boolean judgeLic1(Coordinate[] coordinates, double radius1) {

        if (coordinates == null || coordinates.length < 3) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 2; i++) {
            if (circumcircleRadius(coordinates[i], coordinates[i + 1], coordinates[i + 2]) > radius1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Calculates the circumcircle radius of a triangle formed by three coordinates.
     *
     * @param coordinate1 the first coordinate
     * @param coordinate2 the second coordinate
     * @param coordinate3 the third coordinate
     * @return the circumcircle radius of the triangle formed by the three coordinates
     */
    private double circumcircleRadius(Coordinate coordinate1, Coordinate coordinate2, Coordinate coordinate3) {
        double a = Coordinate.distance(coordinate1, coordinate2);
        double b = Coordinate.distance(coordinate2, coordinate3);
        double c = Coordinate.distance(coordinate1, coordinate3);

        double semiPerimeter = (a + b + c) / 2;
        double area = Math.sqrt(semiPerimeter * (semiPerimeter - a) * (semiPerimeter - b) * (semiPerimeter - c));
        double radius = (a * b * c) / (4 * area);

        return radius;
    }

    private boolean judgeLic2() {
        // todo: implement LIC 2 judgement
        return false;
    }

    /**
     * CThere exists at least one set of three consecutive data points that are the vertices of a triangle
     * with area greater than AREA1
     *
     * @param points        an array representing the coordinate system, where each
     *                      element is a pair of (x, y) coordinates
     * @param thresholdArea the threshold area (AREA1) to compare against
     * @return {@code true} if there exists at least one set of three consecutive
     * points that form a triangle with an area greater than AREA1, {@code false} otherwise
     */
    private boolean judgeLic3(Coordinate[] points, double thresholdArea) {

        if (points == null || points.length < 3) {
            return false;
        }

        for (int currentIndex = 0; currentIndex < points.length - 2; currentIndex++) {
            Coordinate vertexA = points[currentIndex];
            Coordinate vertexB = points[currentIndex + 1];
            Coordinate vertexC = points[currentIndex + 2];

            double triangleArea = Coordinate.areaOfTriangle(vertexA, vertexB, vertexC);

            if (triangleArea > thresholdArea) {
                return true;
            }
        }

        return false;
    }

    private boolean judgeLic4() {
        // todo: implement LIC 4 judgement
        return false;
    }

    private boolean judgeLic5() {
        // todo: implement LIC 5 judgement
        return false;
    }

    /**
     * Evaluates whether there exists at least one set of `nPoints` consecutive data
     * points
     * such that at least one of the points lies at a distance greater than `dist`
     * from the
     * line connecting the first and last points of the set.
     *
     * Special cases:
     * - If the first and last points of the set are identical, the distance is
     * calculated
     * from the coincident point to all other points in the set.
     * - The condition is not evaluated if `coordinates` is null or if the total
     * number of
     * points is less than 3.
     *
     * Constraints:
     * - 3 ≤ `nPoints` ≤ `coordinates.length`
     * - 0 ≤ `dist`
     *
     * @param coordinates An array of {@link Coordinate} objects representing the
     *                    data points.
     * @param nPoints     The number of consecutive points to evaluate (minimum 3).
     * @param dist        The threshold distance to compare against.
     * @return {@code true} if the condition is met, {@code false} otherwise.
     */
    public boolean judgeLic6(Coordinate[] coordinates, int nPoints, double dist) {

        if (coordinates == null || coordinates.length < 3) {
            return false;
        }

        for (int i = 0; i <= coordinates.length - nPoints; i++) {
            Coordinate first = coordinates[i];
            Coordinate last = coordinates[i + nPoints - 1];
            double distance = 0;
            for (int j = i + 1; j < i + nPoints - 1; j++) {
                Coordinate current = coordinates[j];
                if (first.x == last.x && first.y == last.y) {
                    distance = Coordinate.distance(current, first);
                } else {
                    distance = Coordinate.pointToLineDistance(current, first, last);
                }
                if (distance > dist) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean judgeLic7() {
        // todo: implement LIC 7 judgement
        return false;
    }

    private boolean judgeLic8() {
        // todo: implement LIC 8 judgement
        return false;
    }

    private boolean judgeLic9() {
        // todo: implement LIC 9 judgement
        return false;
    }

    private boolean judgeLic10() {
        // todo: implement LIC 10 judgement
        return false;
    }

    private boolean judgeLic11() {
        // todo: implement LIC 11 judgement
        return false;
    }

    private boolean judgeLic12() {
        // todo: implement LIC 12 judgement
        return false;
    }

    private boolean judgeLic13() {
        // todo: implement LIC 13 judgement
        return false;
    }

    private boolean judgeLic14() {
        // todo: implement LIC 14 judgement
        return false;
    }
}
