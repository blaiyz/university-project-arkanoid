package game.logic.physics;

import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.Ball;

/**
 * Objects that can collide with instances of {@link Ball}.
 */
public interface Collidable {
    /**
     * Returns the rectangle that defines the collision shape (aka "Hit-box") of the collidable.
     *
     * @return the collision rectangle
     */
    Rectangle getCollisionRectangle();

    /**
     * Notifies the object that we collided with it at collisionPoint with a given {@link Velocity}.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param hitter the hitting object
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
