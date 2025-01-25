package decide;

public class Decider {
    static final double PI = Math.PI;
    static Coordinate[] coordinates;
    static int numPoints;
    static boolean[] cmv;
    static ConnectorEnum[][] lcm;
    static boolean[][] pum;
    static boolean[] puv;
    static boolean[] fuv;
    static boolean launch;

    public static void decide(int numPoints
            , Coordinate[] coordinates
            , Parameters parameters
            , ConnectorEnum[][] lcm
            , boolean[] puv) {
        Decider.coordinates = coordinates;
        Decider.numPoints = numPoints;
        Decider.lcm = lcm;
        Decider.puv = puv;

        LicJudge licJudge = new LicJudge();
        Decider.cmv = licJudge.judge(coordinates, parameters);

        Decider.pum = generatePum(Decider.cmv, Decider.lcm);

        Decider.fuv = generateFuv(Decider.pum, Decider.puv);

        Decider.launch = makeLaunchDecision(Decider.fuv);
    }

    /**
     * Compare two double values and return the result, same as Appendix C code in requirements
     *
     * @param A the first double value
     * @param B the second double value
     * @return the comparison result
     */
    private static CompTypeEnum doubleCompare(double A, double B) {
        final double EPSILON = 1e-6;
        if (Math.abs(A - B) < EPSILON) {
            return CompTypeEnum.EQ;
        } else if (A < B) {
            return CompTypeEnum.LT;
        } else {
            return CompTypeEnum.GT;
        }
    }

    /**
     * Generate PUM (Preliminary Unlocking Matrix) based on CMV (Conditions Met Vector) and LCM (Logical Connector Matrix)
     *
     * @param cmv Conditions Met Vector
     * @param lcm Logical Connector Matrix
     * @return generated PUM
     */
    private static boolean[][] generatePum(boolean[] cmv, ConnectorEnum[][] lcm) {
        int length = cmv.length;
        boolean[][] pum = new boolean[length][length];

        /* for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                switch (lcm[i][j]) {
                    case ConnectorEnum.ORR:
                        pum[i][j] = cmv[i] || cmv[j];
                        break;
                    case ConnectorEnum.ANDD:
                        pum[i][j] = cmv[i] && cmv[j];
                        break;
                    case ConnectorEnum.NOTUSED:
                        pum[i][j] = true;
                        break;
                }
            }
        } */

        return pum;
    }

    /**
     * Generate FUV (Final Unlocking Vector) based on PUM (Preliminary Unlocking Matrix) and PUV (Preliminary Unlocking Vector)
     *
     * @param pum Preliminary Unlocking Matrix
     * @param puv Preliminary Unlocking Vector
     * @return generated FUV
     */
    private static boolean[] generateFuv(boolean[][] pum, boolean[] puv) {
        int length = puv.length;
        boolean[] fuv = new boolean[length];

        for (int i = 0; i < length; i++) {
            if (!puv[i]) {
                fuv[i] = true;
            } else {
                boolean allTrue = true;
                for (int j = 0; j < length; j++) {
                    if (!pum[i][j]) {
                        allTrue = false;
                        break;
                    }
                }
                fuv[i] = allTrue;
            }
        }

        return fuv;
    }

    /**
     * Whether to generate launch-unlock signal
     *
     * @param fuv Final Unlocking Vector
     * @return final launch / no launch decision
     */
    public static boolean makeLaunchDecision(boolean[] fuv) {
        boolean launchDecision = true;

        for (int i = 0; i < fuv.length; i++) {
            if (!fuv[i]) {
                launchDecision = false;
                break;
            }
        }

        return launchDecision;
    }
}

