package decide.model.enums;

public enum CompTypeEnum {
    LT, EQ, GT;

    /**
     * Compare two double values and return the result, same as Appendix C code in requirements
     *
     * @param A the first double value
     * @param B the second double value
     * @return the comparison result
     */
    public static CompTypeEnum doubleCompare(double A, double B) {
        final double EPSILON = 1e-6;
        if (Math.abs(A - B) < EPSILON) {
            return CompTypeEnum.EQ;
        } else if (A < B) {
            return CompTypeEnum.LT;
        } else {
            return CompTypeEnum.GT;
        }
    }
}
