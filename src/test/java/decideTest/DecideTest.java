package decideTest;

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

    /* @Test
    public void testjudgeLic0Equality() {
        LicJudge LicJudge = new LicJudge();
        Boolean expected = false;
        Boolean actual = LicJudge.judgeLic10();

        assertEquals(expected, actual, "Booleans should be equal.");
    } */

}