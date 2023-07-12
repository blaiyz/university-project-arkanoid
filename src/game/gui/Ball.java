package game.gui;

import biuoop.DrawSurface;
import game.animations.GameLevel;
import game.geometry.Point;
import game.geometry.shapes.Line;
import game.logic.physics.Collidable;
import game.logic.physics.CollisionInfo;
import game.logic.physics.GameEnvironment;
import game.logic.physics.Velocity;

import java.awt.Color;

/**
 * 2-dimensional ball (circle) that can be moved around using velocity.
 *
 * @see Velocity
 * @see Point
 */
public class Ball extends Circle {
    private static final double DEFAULT_VELOCITY_DX = 0;
    private static final double DEFAULT_VELOCITY_DY = 0;
    private static final double COLLISION_TOLERANCE = 0.01;
    private Velocity velocity;
    //Holds all collidables
    private final GameEnvironment environment;

    /**
     * Instantiates a new ball from a center, radius, {@link Color}, {@link Velocity} and {@link GameEnvironment}.
     *
     * @param center      the center point
     * @param r           the radius
     * @param color       the color
     * @param v           the starting velocity
     * @param environment the game environment
     */
    public Ball(Point center, int r, Color color, Velocity v, GameEnvironment environment) {
        super(center, r, color);
        this.velocity = new Velocity(v.getDx(), v.getDy());
        this.environment = environment;
    }

    /**
     * Instantiates a new ball from the center point, radius and {@link Color}.
     * Defaults {@link #velocity} to
     * {@link Velocity#Velocity}({@value #DEFAULT_VELOCITY_DX}, {@value #DEFAULT_VELOCITY_DY})
     *
     * @param center      the center point
     * @param r           the radius
     * @param color       the color
     * @param environment the game environment
     * @see #Ball(Point center, int r, Color color, Velocity v, GameEnvironment environment)
     */
    public Ball(Point center, int r, Color color, GameEnvironment environment) {
        this(center, r, color, new Velocity(DEFAULT_VELOCITY_DX, DEFAULT_VELOCITY_DY), environment);
    }

    /**
     * Instantiates a new ball from the center point coordinates, the radius and the {@link Color}.
     *
     * @param x           the x coordinate
     * @param y           the y coordinate
     * @param r           the radius
     * @param color       the color
     * @param environment the game environment
     * @see #Ball(Point center, int r, Color color, GameEnvironment environment)
     */
    public Ball(double x, double y, int r, Color color, GameEnvironment environment) {
        this(new Point(x, y), r, color, environment);
    }

    /**
     * Sets the ball's velocity from the velocity components.
     *
     * @param dx the dx value
     * @param dy the dy value
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * Sets ball velocity from a {@link Velocity}.
     *
     * @param v the velocity
     * @see #setVelocity(double dx, double dy) #setVelocity(double dx, double dy)
     */
    public void setVelocity(Velocity v) {
        this.setVelocity(v.getDx(), v.getDy());
    }

    /**
     * Returns the ball's {@link #velocity}.
     *
     * @return the velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * Moves the ball a single step according to its velocity.
     * If a collision has occurred with on of the collidables in gameEnvironment, the velocity is changed
     * accordingly and the ball is position to be {@value #COLLISION_TOLERANCE} distance away from the
     * collision point.
     */
    public void moveOneStep() {
        //Getting collision information
        Line trajectory = new Line(super.getCenter(), this.velocity.applyToPoint(super.getCenter()));
        CollisionInfo collisionInfo = this.environment.getClosestCollision(trajectory);

        //No collision
        if (collisionInfo == null) {
            this.setPoint(trajectory.end());
            return;
        }

        //Collision occurred
        Point collisionPoint = collisionInfo.collisionPoint();
        Collidable collisionObject = collisionInfo.collisionObject();

        //Setting new values
        double x = collisionPoint.getX();
        double y = collisionPoint.getY();
        Velocity newVelocity = collisionObject.hit(this, collisionPoint, this.velocity);

        //Setting the new point to be slightly before the collision
        if (!this.velocity.equals(newVelocity)) {
            //If velocity has changed, that means we have collided from the outside and not inside

            //Moving the ball COLLISION_TOLERANCE of distance before the collision point
            double dx = this.velocity.getDx();
            double dy = this.velocity.getDy();
            double collisionParameter = COLLISION_TOLERANCE / this.velocity.getSpeed();
            this.setPoint(new Point(x - dx * collisionParameter, y - dy * collisionParameter));
        } else {
            this.setPoint(trajectory.end());
        }
        this.setVelocity(newVelocity);

    }

    /**
     * Adds the ball to the given game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Removes the ball from the given game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    //Sprite methods

    /**
     * Moves the ball one step.
     *
     * @see #moveOneStep()
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }

    /**
     * Draws the ball on a given {@link DrawSurface}.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        super.drawOn(d);
    }

    @Override
    public String toString() {
        return "Ball => Center: " + super.getCenter() + ", Radius: " + super.getSizeRounded() + ", Velocity: " + this.velocity;
    }
}
