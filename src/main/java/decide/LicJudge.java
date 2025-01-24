package decide;

public class LicJudge {

    public boolean[] judge(Coordinate[] coordinates, Parameters parameters) {
        boolean[] lic = new boolean[15];

        lic[0] = judgeLic0(coordinates, parameters.getLength1());
        lic[1] = judgeLic1();
        lic[2] = judgeLic2();
        lic[3] = judgeLic3();
        lic[4] = judgeLic4();
        lic[5] = judgeLic5();
        lic[6] = judgeLic6();
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
	* in the given 2D coordinate system that are a distance greater than
	* the specified length, LENGTH1, apart.
	*
	* @param coordinates a 2D matrix representing the coordinate system, where each
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
            if (coordinates[i].distance(coordinates[i+1]) > length1) {
                return true;
            }
		}


		return false;
    }

    private boolean judgeLic1() {
        // todo: implement LIC 1 judgement
        return false;
    }

    private boolean judgeLic2() {
        // todo: implement LIC 2 judgement
        return false;
    }

    private boolean judgeLic3() {
        // todo: implement LIC 3 judgement
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
