package game.logic;

/**
 * Holds a value that can be shared and changed everywhere.
 */
public class Counter {
    private int counter;

    /**
     * Instantiates a new Counter.
     *
     * @param start the start count
     */
    public Counter(int start) {
        this.counter = start;
    }

    /**
     * Instantiates a new Counter. Defaults the start count to 0.
     *
     * @see #Counter(int)
     */
    public Counter() {
        this(0);
    }

    /**
     * Increases the count by the given number.
     *
     * @param number the number
     */
    public void increase(int number) {
        this.counter += number;
    }

    /**
     * Decreases the count by the given number.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.counter -= number;
    }

    /**
     * Returns the current count.
     *
     * @return the value
     */
    public int getValue() {
        return this.counter;
    }
}
