package game.logic.physics;

import game.geometry.Point;
import game.logic.DoubleMethods;

/**
 * Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private final double dx;
    private final double dy;

    /**
     * Instantiates a new {@link Velocity} from given velocity components.
     *
     * @param dx the change in x
     * @param dy the change in y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns a new {@link Velocity} from given angle and speed (magnitude).
     *
     * @param angle the angle
     * @param speed the speed
     * @return the velocity
     * @see #Velocity(double dx, double dy)
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = -speed * Math.cos(angle);
        return new Velocity(dx, dy);
    }

    /**
     * Gets the change in x.
     *
     * @return dx
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * Gets the change in y.
     *
     * @return dy
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Takes a {@link Point} with position (x,y) and return a new {@link Point} with position (x+dx, y+dy).
     *
     * @param p the point
     * @return the new point
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

    /**
     * Returns whether a given Velocity equals to this Velocity.
     *
     * @param other the other Velocity
     * @return whether the other Velocity equals to this
     */
    public boolean equals(Velocity other) {
        return DoubleMethods.equals(this.dx, other.dx) && DoubleMethods.equals(this.dy, other.dy);
    }

    /**
     * Gets the speed value of the velocity - the length of the velocity vector.
     *
     * @return the speed
     */
    public double getSpeed() {
        return Math.sqrt(this.dx * this.dx + this.dy * this.dy);
    }

    @Override
    public String toString() {
        return "<" + this.dx + ", " + this.dy + ">";
    }
}