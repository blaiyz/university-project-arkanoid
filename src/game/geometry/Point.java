package game.geometry;

import game.logic.DoubleMethods;

/**
 * 2 dimensional point.
 */
public class Point {
    private final double x;
    private final double y;

    /**
     * Instantiates a new {@link Point}.
     *
     * @param x x coordinate
     * @param y y coordinate
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the distance between this and another {@link Point}.
     *
     * @param other other point
     * @return the distance
     */
    public double distance(Point other) {
        double distanceX = this.x - other.x;
        double distanceY = this.y - other.y;
        return Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
    }

    /**
     * Checks if this and another {@link Point} have the same x value.
     *
     * @param other other point
     * @return result
     */
    public boolean sameX(Point other) {
        return DoubleMethods.equals(this.x, other.x);
    }

    /**
     * Checks if this and another {@link Point} have the same y value.
     *
     * @param other other point
     * @return result
     */
    public boolean sameY(Point other) {
        return DoubleMethods.equals(this.y, other.y);
    }

    /**
     * Returns the slope between the two points.
     * Returns NaN if the two points have the same x value.
     *
     * @param other the other point
     * @return the slope between the two points
     */
    public double slope(Point other) {
        if (this.sameX(other)) {
            return Double.NaN;
        }
        // Using linear functions we get m = (y1-y2)/(x1-x2)
        return (this.y - other.y) / (this.x - other.x);
    }

    /**
     * Checks if this and another {@link Point} have the same coordinates.
     *
     * @param other the other point
     * @return the result
     */
    public boolean equals(Point other) {
        return this.sameX(other) && this.sameY(other);
    }

    /**
     * Returns the x coordinate of the point.
     *
     * @return the x coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate of the point.
     *
     * @return the y coordinate
     */
    public double getY() {
        return this.y;
    }

    public Point moveX(double x) {
        return new Point(this.x + x, this.y);
    }

    public Point moveY(double y) {
        return new Point(this.x, this.y + y);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}