package game.logic;

/**
 * Provides additional methods for variables of type double, instead using default operators (==, >=, <=) which may
 * result in errors and inaccuracies.
 */
public class DoubleMethods {
    private static final double THRESHOLD = 0.000000001;

    /**
     * Returns whether the two numbers are equal, with error less than the threshold.
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return whether num1 = num2
     */
    public static boolean equals(double num1, double num2) {
        return Math.abs(num1 - num2) < THRESHOLD;
    }

    /**
     * Returns whether the first number is greater than the second, using the threshold for inaccuracies.
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return num1 > num2
     */
    public static boolean greater(double num1, double num2) {
        return num1 - THRESHOLD > num2;
    }

    /**
     * Returns whether the first number is greater or equals to the second, using the threshold for inaccuracies.
     *
     * @param num1 the first number
     * @param num2 the second number
     * @return num1 >= num2
     */
    public static boolean greaterOrEquals(double num1, double num2) {
        return num1 + THRESHOLD > num2;
    }

    /**
     * Rounds a double to the closest integer. Used for conversion of double values to ints, in order to use them in
     * the gui.
     *
     * @param num the double
     * @return the rounded int
     */
    public static int round(double num) {
        return (int) Math.round(num);
    }
}
