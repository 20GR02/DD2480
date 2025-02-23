package decide.core;

import decide.model.Coordinate;
import decide.model.Parameters;

public class LicJudge {

    public boolean[] judge(Coordinate[] coordinates, Parameters parameters) {
        boolean[] lic = new boolean[15];

        lic[0] = judgeLic0(coordinates, parameters.getLength1());
        lic[1] = judgeLic1(coordinates, parameters.getRadius1());
        lic[2] = judgeLic2(coordinates, parameters.getEpsilon());
        lic[3] = judgeLic3(coordinates, parameters.getArea1());
        lic[4] = judgeLic4(coordinates, parameters.getkPoints(), parameters.getQuads());
        lic[5] = judgeLic5(coordinates);
        lic[6] = judgeLic6(coordinates, parameters.getnPoints(), parameters.getDist());
        lic[7] = judgeLic7(coordinates, parameters.getkPoints(), parameters.getLength1());
        lic[8] = judgeLic8(coordinates, parameters.getaPoints(), parameters.getbPoints(), parameters.getRadius1());
        lic[9] = judgeLic9(coordinates, parameters.getcPoints(), parameters.getdPoints(), parameters.getEpsilon());
        lic[10] = judgeLic10(coordinates, parameters.getePoints(), parameters.getfPoints(), parameters.getArea1());
        lic[11] = judgeLic11(coordinates, parameters.getgPoints());
        lic[12] = judgeLic12(coordinates, parameters.getLength1(), parameters.getLength2(), parameters.getkPoints());
        lic[13] = judgeLic13(coordinates, parameters.getaPoints(), parameters.getbPoints(), parameters.getRadius1(),
                parameters.getRadius2());
        lic[14] = judgeLic14(coordinates, parameters.getePoints(), parameters.getfPoints(), parameters.getArea1(),
                parameters.getArea2());

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
     *         points with a distance greater than LENGTH1, {@code false} otherwise
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
     * @param radius1     the threshold radius (RADIUS1) to compare the circumradius
     *                    against
     * @return {@code true} if there exists at least one set of consecutive
     *         points with a circumradius greater than RADIUS1, {@code false}
     *         otherwise
     */
    public boolean judgeLic1(Coordinate[] coordinates, double radius1) {

        if (coordinates == null || coordinates.length < 3) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 2; i++) {
            if (Coordinate.circumcircleRadius(coordinates[i], coordinates[i + 1], coordinates[i + 2]) > radius1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if there exists at least one set of three consecutive coordinates in
     * the given array
     * where the angle at the vertex (the second point) does not lie within the
     * range
     * {@code [π - epsilon, π + epsilon]}.
     *
     * @param coordinates an array representing the coordinate system, where each
     *                    element is a pair of (x, y) coordinates
     * @param epsilon     The allowable angular deviation in radians. Must be in the
     *                    range {@code [0, Math.PI]}.
     * @return {@code true} if at least one angle deviates beyond the range
     *         {@code [π - epsilon, π + epsilon]}, otherwise {@code false}.
     */
    public boolean judgeLic2(Coordinate[] coordinates, double epsilon) {

        if (epsilon < 0 || epsilon > Math.PI || coordinates.length < 3) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 2; i++) {
            double angle = Coordinate.angleAtVertex(coordinates[i], coordinates[i + 1], coordinates[i + 2]);
            if (angle < Math.PI - epsilon || angle > Math.PI + epsilon) {
                return true;
            }
        }

        return false;
    }

    /**
     * There exists at least one set of three consecutive data points that are the
     * vertices of a triangle
     * with area greater than AREA1
     *
     * @param points        an array representing the coordinate system, where each
     *                      element is a pair of (x, y) coordinates
     * @param thresholdArea the threshold area (AREA1) to compare against
     * @return {@code true} if there exists at least one set of three consecutive
     *         points that form a triangle with an area greater than AREA1,
     *         {@code false} otherwise
     */
    public boolean judgeLic3(Coordinate[] points, double thresholdArea) {

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

    /**
     * Checks if there exists at least one set of Q_PTS consecutive data points that
     * lie in more than QUADS distinct quadrants.
     *
     * @param points            an array representing the coordinate system, where
     *                          each
     *                          element is a pair of (x, y) coordinates
     * @param numberOfPoints    the number of consecutive points to evaluate (Q_PTS)
     * @param requiredQuadrants the minimum number of distinct quadrants the points
     *                          must span (QUADS)
     * @return {@code true} if there exists at least one set of Q_PTS consecutive
     *         points
     *         that span more than QUADS distinct quadrants, {@code false} otherwise
     */
    public boolean judgeLic4(Coordinate[] points, int numberOfPoints, int requiredQuadrants) {

        if (points == null) {
            return false;
        }

        if (points.length < numberOfPoints) {
            return false;
        }

        if (requiredQuadrants < 1 || requiredQuadrants > 4) {
            return false;
        }

        for (int startIndex = 0; startIndex <= points.length - numberOfPoints; startIndex++) {

            boolean hasQuadrant1 = false;
            boolean hasQuadrant2 = false;
            boolean hasQuadrant3 = false;
            boolean hasQuadrant4 = false;

            for (int offset = 0; offset < numberOfPoints; offset++) {
                Coordinate currentPoint = points[startIndex + offset];

                int quadrant = Coordinate.findQuadrant(currentPoint);

                if (quadrant == 1) {
                    hasQuadrant1 = true;
                } else if (quadrant == 2) {
                    hasQuadrant2 = true;
                } else if (quadrant == 3) {
                    hasQuadrant3 = true;
                } else if (quadrant == 4) {
                    hasQuadrant4 = true;
                }
            }

            int distinctQuadrants = 0;
            if (hasQuadrant1)
                distinctQuadrants++;
            if (hasQuadrant2)
                distinctQuadrants++;
            if (hasQuadrant3)
                distinctQuadrants++;
            if (hasQuadrant4)
                distinctQuadrants++;

            if (distinctQuadrants > requiredQuadrants) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if there are two consecutive data points where the x-coordinate of the
     * second point is smaller than the first.
     *
     * @param coordinates an array of {@link Coordinate} objects, where each element
     *                    represents a point with x and y coordinates
     * @return {@code true} if there exists at least one pair of consecutive points
     *         with X[j] - X[i] < 0, {@code false} otherwise
     */
    public boolean judgeLic5(Coordinate[] coordinates) {

        if (coordinates == null || coordinates.length < 2) {
            return false;
        }

        for (int i = 0; i < coordinates.length - 1; i++) {
            if (coordinates[i + 1].getX() - coordinates[i].getX() < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evaluates whether there exists at least one set of `nPoints` consecutive data
     * points
     * such that at least one of the points lies at a distance greater than `dist`
     * from the
     * line connecting the first and last points of the set.
     * <p>
     * Special cases:
     * <ul>
     * <li>
     * If the first and last points of the set are identical, the distance is
     * calculated from the coincident point to all other points in the set.
     * </li>
     * <li>
     * The condition is not evaluated if `coordinates` is null or if the total
     * number of points is less than 3.
     * </li>
     * </ul>
     * <p>
     * Constraints:
     * <ul>
     * <li>3 <= {@code nPoints} <= `coordinates.length`</li>
     * <li>0 <= {@code dist}</li>
     * </ul>
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
            double distance;
            for (int j = i + 1; j < i + nPoints - 1; j++) {
                Coordinate current = coordinates[j];
                if (first.getX() == last.getX() && first.getY() == last.getY()) {
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

    /**
     * Checks whether there exists at least one pair of data points separated by
     * exactly
     * {@code K_PTS} consecutive intervening points, which are a distance greater
     * than {@code LENGTH1}.
     * The condition is automatically not met if the number of points is less than
     * 3.
     * </p>
     *
     * @param coordinates Array of data points. If {@code null} or length is less
     *                    than 3,
     *                    returns {@code false}.
     * @param kPoints     The number of consecutive intervening points between two
     *                    data points (K_PTS).
     *                    Must satisfy: {@code 1 ≤ K_PTS ≤ (NUMPOINTS - 2)}. Caller
     *                    must ensure validity.
     * @param length1     The threshold distance (LENGTH1). A pair of points must be
     *                    separated by a
     *                    distance greater than this value to satisfy the condition.
     * @return {@code true} if the condition is met (at least one valid pair
     *         exists),
     *         {@code false} otherwise.
     */
    public boolean judgeLic7(Coordinate[] coordinates, int kPoints, double length1) {
        if (coordinates == null || coordinates.length < 3) {
            return false;
        }

        for (int i = 0; i < coordinates.length - kPoints - 1; i++) {
            if (Coordinate.distance(coordinates[i], coordinates[i + kPoints + 1]) > length1)
                return true;
        }
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
     *         otherwise.
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

    /**
     * Determines whether there exists at least one set of three data points in the
     * given
     * array of coordinates, separated by exactly {@code cPoints} and
     * {@code dPoints}
     * consecutive intervening points, respectively, such that the angle formed at
     * the vertex
     * satisfies one of the following conditions:
     * <ul>
     * <li>{@code angle < (PI - epsilon)}</li>
     * <li>{@code angle > (PI + epsilon)}</li>
     * </ul>
     * The vertex is always the second point of the set.
     *
     * @param coordinates An array of {@link Coordinate} objects representing the
     *                    data points.
     * @param cPoints     The number of consecutive intervening points between the
     *                    first
     *                    point and the vertex of the angle. Must be at least 1.
     * @param dPoints     The number of consecutive intervening points between the
     *                    vertex
     *                    of the angle and the third point. Must be at least 1.
     * @param epsilon     A small positive value used to define the angular
     *                    deviation
     *                    from {@code PI} (straight angle).
     * @return {@code true} if such a set of points exists that satisfies the
     *         conditions;
     *         {@code false} otherwise.
     */
    public boolean judgeLic9(Coordinate[] coordinates, int cPoints, int dPoints, double epsilon) {

        if (coordinates == null || coordinates.length < 5 || cPoints < 1 || dPoints < 1
                || cPoints + dPoints > coordinates.length - 3) {
            return false;
        }

        for (int i = 0; i < coordinates.length - cPoints - dPoints - 2; i++) {
            double angle = Coordinate.angleAtVertex(coordinates[i], coordinates[i + cPoints + 1],
                    coordinates[i + cPoints + 1 + dPoints + 1]);
            if (angle < Math.PI - epsilon || angle > Math.PI + epsilon) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evaluates whether there exists at least one set of three data points from the
     * given coordinates that form a triangle
     * with an area greater than the specified threshold. The three points must be
     * separated by exactly {@code ePoints} and {@code fPoints}
     * consecutive intervening points, respectively.
     *
     * @param coordinates An array of {@code Coordinate} objects representing the
     *                    data
     *                    points.
     * @param ePoints     The number of consecutive points between the first and
     *                    second vertices of the triangle.
     * @param fPoints     The number of consecutive points between the second and
     *                    third vertices of the triangle.
     * @param area1       The threshold area value. The function will return true if
     *                    a triangle with an area greater than this value is found.
     * @return {@code true} if there exists a set of three points forming a triangle
     *         with
     *         area greater than {@code area1}; {@code false} otherwise.
     */
    public boolean judgeLic10(Coordinate[] coordinates, int ePoints, int fPoints, double area1) {

        if (coordinates == null || coordinates.length < 5 || ePoints < 1 || fPoints < 1
                || ePoints + fPoints > coordinates.length - 3) {
            return false;
        }

        for (int i = 0; i < (coordinates.length - ePoints - fPoints - 2); i++) {

            Coordinate vertexA = coordinates[i];
            Coordinate vertexB = coordinates[i + ePoints + 1];
            Coordinate vertexC = coordinates[i + ePoints + fPoints + 2];

            double triangleArea = Coordinate.areaOfTriangle(vertexA, vertexB, vertexC);
            if (triangleArea > area1) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if there exists at least one pair of points (X[i], Y[i]) and (X[j],
     * Y[j]) in the given
     * array of coordinates, separated by exactly {@code gPoints} consecutive
     * intervening points,
     * such that {@code X[j] - X[i] < 0} (where {@code i < j}).
     *
     * <p>
     * The condition is only evaluated if the following requirements are met:
     * <ul>
     * <li>The number of points in the array (NUMPOINTS) is at least 3.</li>
     * <li>{@code gPoints} is within the range
     * {@code 1 ≤ gPoints ≤ NUMPOINTS - 2}.</li>
     * </ul>
     * If these requirements are not satisfied, the function returns {@code false}.
     *
     * @param coordinates An array of {@link Coordinate} objects representing the
     *                    points to evaluate.
     * @param gPoints     The gap (number of intervening points) between the two
     *                    points being compared.
     * @return {@code true} if there exists a pair of points that satisfy the
     *         condition; {@code false} otherwise.
     */
    public boolean judgeLic11(Coordinate[] coordinates, int gPoints) {

        if (coordinates == null || coordinates.length < 3 || gPoints < 1 || gPoints > coordinates.length - 2) {
            return false;
        }

        for (int i = 0; i < coordinates.length - gPoints - 1; i++) {
            if (coordinates[i + gPoints + 1].getX() - coordinates[i].getX() < 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether there exist two data points in the given array, separated by
     * exactly {@code kPoints} consecutive intervening points, such that one pair's
     * distance is
     * greater
     * than {@code length1} and another pair's distance is less than
     * {@code length2}. Both
     * conditions must be
     * satisfied for the function to return {@code true}.
     *
     * @param coordinates an array of {@code Coordinate} objects representing the
     *                    data points.
     * @param length1     The distance threshold for the greater distance condition.
     * @param length2     The distance threshold for the smaller distance condition
     *                    (must be >= 0).
     * @param kPoints     The number of consecutive intervening points between the
     *                    two points being checked.
     * @return {@code true} if both conditions are satisfied, {@code false}
     *         otherwise.
     */
    public boolean judgeLic12(Coordinate[] coordinates, double length1, double length2, int kPoints) {

        if (coordinates == null || coordinates.length < 3 || length2 < 0) {
            return false;
        }

        boolean cond1 = false;
        boolean cond2 = false;

        for (int i = 0; i < coordinates.length - kPoints - 1; i++) {
            double dist = Coordinate.distance(coordinates[i], coordinates[i + kPoints + 1]);
            if (dist > length1) {
                cond1 = true;
            }
            if (dist < length2) {
                cond2 = true;
            }

            if (cond1 && cond2) {
                return true;
            }
        }

        return false;
    }

    /**
     * Checks whether there exist at least one set of three data points in the given
     * array,
     * separated by exactly {@code aPoints} and {@code bPoints} consecutive
     * intervening points,
     * that satisfy both of the following conditions:
     * <ul>
     * <li>At least one such set of three points cannot be contained within or on a
     * circle of radius {@code radius1}.</li>
     * <li>At least one such set of three points (which can be the same or different
     * from the previous set) can be contained within or on a circle of radius
     * {@code radius2}.</li>
     * </ul>
     * Both conditions must be met for the function to return {@code true}.
     * The condition is not met when {@code coordinates.length < 5}.
     *
     * @param coordinates An array of {@link Coordinate} objects representing the
     *                    data points.
     * @param aPoints     The number of consecutive intervening points between the
     *                    first and second points.
     * @param bPoints     The number of consecutive intervening points between the
     *                    second and third points.
     * @param radius1     The radius threshold for the first condition (points must
     *                    not fit in or on this circle).
     * @param radius2     The radius threshold for the second condition (points must
     *                    fit in or on this circle).
     * @return {@code true} if both conditions are satisfied, otherwise
     *         {@code false}.
     */
    public boolean judgeLic13(Coordinate[] coordinates, int aPoints, int bPoints, double radius1, double radius2) {

        if (coordinates == null || coordinates.length < 5 || radius2 < 0) {
            return false;
        }

        boolean cond1 = false;
        boolean cond2 = false;

        for (int i = 0; i < coordinates.length - aPoints - bPoints - 2; i++) {
            double circumradius = Coordinate.circumcircleRadius(coordinates[i], coordinates[i + aPoints + 1],
                    coordinates[i + aPoints + 1 + bPoints + 1]);
            if (circumradius > radius1) {
                cond1 = true;
            }
            if (circumradius <= radius2) {
                cond2 = true;
            }

            if (cond1 && cond2) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evaluates a condition based on the areas of triangles formed by three points
     * in a coordinate array.
     * <p>
     * The function checks if there exists at least one triangle, formed by points
     * separated by exactly {@code ePoints}
     * and {@code fPoints} consecutive points, that has an area greater than
     * {@code area1}. Additionally, it checks if
     * there exists at least one triangle with an area less than {@code area2}. Both
     * conditions must be satisfied for the
     * function to return {@code true}.
     * </p>
     *
     * @param coordinates An array of {@link Coordinate} objects representing the
     *                    points in the 2D space.
     * @param ePoints     The number of points separating the first and second
     *                    vertex of the triangle.
     * @param fPoints     The number of points separating the second and third
     *                    vertex of the triangle.
     * @param area1       The minimum area for the first triangle condition (greater
     *                    than area1).
     * @param area2       The maximum area for the second triangle condition (less
     *                    than area2).
     * @return {@code true} if both conditions are satisfied (a triangle with area >
     *         {@code area1} and a triangle with
     *         area < {@code area2}); {@code false} otherwise.
     */
    public boolean judgeLic14(Coordinate[] coordinates, int ePoints, int fPoints, double area1, double area2) {

        if (coordinates == null || coordinates.length < 5 || ePoints < 1 || fPoints < 1
                || ePoints + fPoints > coordinates.length - 3 || area2 < 0) {
            return false;
        }

        boolean condition1 = false;

        for (int i = 0; i < (coordinates.length - ePoints - fPoints - 2) && !condition1; i++) {

            Coordinate vertexA = coordinates[i];
            Coordinate vertexB = coordinates[i + ePoints + 1];
            Coordinate vertexC = coordinates[i + ePoints + fPoints + 2];

            double triangleArea = Coordinate.areaOfTriangle(vertexA, vertexB, vertexC);
            if (triangleArea > area1) {
                condition1 = true;
            }
        }

        if (!condition1) {
            return false;
        }

        for (int i = 0; i < (coordinates.length - ePoints - fPoints - 2); i++) {

            Coordinate vertexA = coordinates[i];
            Coordinate vertexB = coordinates[i + ePoints + 1];
            Coordinate vertexC = coordinates[i + ePoints + fPoints + 2];

            double triangleArea = Coordinate.areaOfTriangle(vertexA, vertexB, vertexC);
            if (triangleArea < area2) {
                return true;
            }
        }

        return false;
    }
}
