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
        lic[6] = judgeLic6();
        lic[7] = judgeLic7();
        lic[8] = judgeLic8(coordinates, parameters.getaPoints(), parameters.getbPoints(), parameters.getRadius1());
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
     * There exists at least one set of three consecutive data points that are the vertices of a triangle
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

    private boolean judgeLic6() {
        // todo: implement LIC 6 judgement
        return false;
    }

    private boolean judgeLic7() {
        // todo: implement LIC 7 judgement
        return false;
    }

    /**
     * Determines whether there exists at least one set of three data points in the
     * given array that are separated
     * by exactly {@code aPoints} and {@code bPoints} consecutive intervening
     * points, respectively, such that
     * the three points cannot be contained within or on a circle of radius
     * {@code radius1}.
     *
     * @param coordinates an array of {@code Coordinate} objects representing the
     *                    data points.
     * @param aPoints     the number of consecutive intervening points between the
     *                    first and second data points.
     * @param bPoints     the number of consecutive intervening points between the
     *                    second and third data points.
     * @param radius1     the radius of the circle.
     * @return {@code true} if such a set of three data points exists; {@code false}
     * otherwise.
     */
    public boolean judgeLic8(Coordinate[] coordinates, int aPoints, int bPoints, double radius1) {
        if (coordinates == null || coordinates.length < 5) {
            return false;
        }

        for (int i = 0; i < coordinates.length - aPoints - bPoints - 2; i++) {
            Coordinate center = Coordinate.calculateCircumcenter(coordinates[i], coordinates[i + aPoints + 1],
                    coordinates[i + aPoints + bPoints + 2]);
            if (center != null && Coordinate.distance(coordinates[i], center) > radius1) {
                return true;
            }
        }
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
