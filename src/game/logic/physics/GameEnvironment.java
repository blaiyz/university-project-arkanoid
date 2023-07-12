package game.logic.physics;

import game.geometry.Point;
import game.geometry.shapes.Line;
import game.geometry.shapes.Rectangle;

import java.util.LinkedList;
import java.util.List;


/**
 * A collection of {@link Collidable} objects.
 */
public class GameEnvironment {
    private final List<Collidable> collidables;

    /**
     * Instantiates a new game environment.
     */
    public GameEnvironment() {
        this.collidables = new LinkedList<>();
    }

    /**
     * Adds a collidable to the game environment.
     *
     * @param c the collidable
     */
    public void addCollidable(Collidable c) {
        if (c != null) {
            this.collidables.add(c);
        } else {
            System.out.println("Warning: attempted to add null to GameEnvironment");
        }
    }

    /**
     * Removes a collidable from the game environment.
     *
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end().
     * If this object will not collide with any of the collidables
     * in this collection, the methods returns null. Else, the method returns the information
     * about the closest collision that is going to occur.
     *
     * @param trajectory the trajectory
     * @return the closest collision info
     * @see CollisionInfo
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //Initializing values
        Point closestPoint = null;
        Collidable closestCollidable = null;
        double minDistance = Double.MAX_VALUE;

        List<Collidable> collidables = new LinkedList<>(this.collidables);

        //Checking all collisions points and finding the closest
        for (Collidable collidable : collidables) {
            Rectangle rect = collidable.getCollisionRectangle();
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(rect);
            if (collisionPoint != null) {
                double distance = trajectory.start().distance(collisionPoint);
                if (distance < minDistance) {
                    closestPoint = collisionPoint;
                    closestCollidable = collidable;
                    minDistance = distance;
                }
            }
        }

        if (closestPoint != null) {
            return new CollisionInfo(closestPoint, closestCollidable);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        String sb = "";
        for (Collidable collidable : this.collidables) {
            sb += collidable + "\n";
        }
        return sb;
    }
}