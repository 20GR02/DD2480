package decideTest;

import decide.Coordinate;
import decide.LicJudge;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class DecideTest {

    @Test
    public void testAddition() {
        int result = 1 + 1;
        assertEquals(2, result, "Addition should work correctly.");
    }

    @Test
    public void testStringEquality() {
        String expected = "Hello, World!";
        String actual = "Hello, World!";

        assertEquals(expected, actual, "Strings should be equal.");
    }

    @Test
    public void testJudgeLic0SingleCoordinate() {
	
		Coordinate coordinate = new Coordinate(0, 0);
		Coordinate[] coordinates = new Coordinate[]{coordinate};

        LicJudge LicJudge = new LicJudge();
        Boolean expected = false;
        Boolean actual = LicJudge.judgeLic0(coordinates, 0);

        assertEquals(expected, actual, "Booleans should be equal.");
    }

	@Test
    public void testJudgeLic0Positve() {
		double length1 = 1;
		Coordinate coordinate1 = new Coordinate(0, 0);
		Coordinate coordinate2 = new Coordinate(2, 2);
		Coordinate[] coordinates = new Coordinate[]{coordinate1, coordinate2};

        LicJudge LicJudge = new LicJudge();
        Boolean expected = true;
        Boolean actual = LicJudge.judgeLic0(coordinates, length1);

        assertEquals(expected, actual, "Booleans should be equal.");
    }

	@Test
    public void testJudgeLic0Negative() {
	
		double length1 = 4;
		Coordinate coordinate1 = new Coordinate(0, 0);
		Coordinate coordinate2 = new Coordinate(2, 0);
		Coordinate coordinate3 = new Coordinate(5, 0);
		Coordinate[] coordinates = new Coordinate[]{coordinate1, coordinate2, coordinate3};

        LicJudge LicJudge = new LicJudge();
        Boolean expected = false;
        Boolean actual = LicJudge.judgeLic0(coordinates, length1);

        assertEquals(expected, actual, "Booleans should be equal.");
    }

    @Test
    public void testJudgeLic1Positive() {
        double radius1 = 3;
        Coordinate coordinate1 = new Coordinate(0, 0);
		Coordinate coordinate2 = new Coordinate(5, 0);
		Coordinate coordinate3 = new Coordinate(0, 5);
        Coordinate[] coordinates = new Coordinate[]{coordinate1, coordinate2, coordinate3};

        LicJudge LicJudge = new LicJudge();
        Boolean expected = true;
        Boolean actual = LicJudge.judgeLic1(coordinates, radius1);

        assertEquals(expected, actual, "Booleans should be equal.");
    }

    @Test
    public void testJudgeLic1Negative() {
        double radius1 = 5;
        Coordinate coordinate1 = new Coordinate(0, 0);
		Coordinate coordinate2 = new Coordinate(3, 0);
		Coordinate coordinate3 = new Coordinate(0, 3);
        Coordinate[] coordinates = new Coordinate[]{coordinate1, coordinate2, coordinate3};

        LicJudge LicJudge = new LicJudge();
        Boolean expected = false;
        Boolean actual = LicJudge.judgeLic1(coordinates, radius1);

        assertEquals(expected, actual, "Booleans should be equal.");
    }

    @Test
    public void testJudgeLic1TwoCoordinates() {
        double radius1 = 1;
        Coordinate coordinate1 = new Coordinate(0, 0);
		Coordinate coordinate2 = new Coordinate(3, 0);
        Coordinate[] coordinates = new Coordinate[]{coordinate1, coordinate2};

        LicJudge LicJudge = new LicJudge();
        Boolean expected = false;
        Boolean actual = LicJudge.judgeLic1(coordinates, radius1);

        assertEquals(expected, actual, "Booleans should be equal.");
    }
}