package game.logic.physics;

import game.geometry.Point;

/**
 * Holds information about a collision.
 */
public class CollisionInfo {
    private final Point collisionPoint;
    private final Collidable collisionObject;

    /**
     * Instantiates a new collision info instance.
     *
     * @param collisionPoint  the collision point
     * @param collisionObject the collision object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * Returns the point at which the collision occurs.
     *
     * @return the collision point
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * Returns the collidable object involved in the collision.
     *
     * @return the collidable
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }

    @Override
    public String toString() {
        return "Collision Point:" + this.collisionPoint + ", Collision Object:" + this.collisionObject;
    }
}