package game.gui.rectangular_objects;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.animations.GameLevel;
import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.Ball;
import game.logic.physics.Velocity;

import java.awt.Color;

/**
 * A rectangle shaped paddle that can be moved on the screen according to keyboard input, and collided with instances
 * of {@link Ball}.
 */
public class Paddle extends Block {

    //Number of section that the top of the paddle is divided to
    private static final int NUMBER_OF_SECTIONS = 5;
    //The angle at which the ball bounces off at the right most section of the paddle.
    private static final int UP_ANGLE = 360;
    //The angle at which the ball bounces off at the left most section of the paddle.
    private static final int MIN_ANGLE = 300;
    private static final double DEFAULT_MOVE_SPEED = 10;
    private biuoop.KeyboardSensor keyboard;
    private final double movingSpeed;
    private double leftBorder;
    private double rightBorder;

    /**
     * Instantiates a new Paddle from a rectangle, moving speed, color, left and right borders, and a keyboard sensor.
     *
     * @param rect        the rectangle
     * @param movingSpeed the moving speed
     * @param color       the color
     * @param keyboard    the keyboard sensor
     * @param leftBorder  the left border
     * @param rightBorder the right border
     */
    public Paddle(Rectangle rect, double movingSpeed, Color color, KeyboardSensor keyboard, double leftBorder,
                  double rightBorder) {
        super(rect, color);
        this.movingSpeed = movingSpeed;
        this.keyboard = keyboard;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    /**
     * Instantiates a new Paddle from a rectangle, left and right borders, and a keyboard sensor. Uses default values
     * for the rest of the fields.
     *
     * @param rect        the rectangle
     * @param keyboard    the keyboard sensor
     * @param leftBorder  the left border
     * @param rightBorder the right border
     * @see #Paddle(Rectangle, double movingSpeed, Color, KeyboardSensor, double leftBorder, double rightBorder)
     */
    public Paddle(Rectangle rect, KeyboardSensor keyboard, double leftBorder, double rightBorder) {
        super(rect);
        this.movingSpeed = DEFAULT_MOVE_SPEED;
        this.keyboard = keyboard;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }

    /**
     * Sets the rectangle of the paddle.
     *
     * @param rect the rectangle
     */
    public void setRectangle(Rectangle rect) {
        super.setRect(rect);
    }

    /**
     * Moves the paddle left.
     */
    public void moveLeft() {
        double x = getRect().getLeft();
        double movingDistance = -Math.min(this.movingSpeed, x - this.leftBorder);
        this.setRectangle(getRect().moveX(movingDistance));
    }

    /**
     * Moves the paddle right.
     */
    public void moveRight() {
        double x = getRect().getRight();
        double movingDistance = Math.min(this.movingSpeed, this.rightBorder - x);
        this.setRectangle(getRect().moveX(movingDistance));
    }

    /**
     * Returns the x coordinate of the middle of the paddle.
     *
     * @return the middle of the paddle
     */
    public double getMiddleX() {
        return getRect().getLeft() + getRect().getWidth() / 2;
    }

    @Override
    protected Paddle clone(Rectangle rect) {
        return new Paddle(rect, this.movingSpeed, this.getColor(), this.keyboard, this.leftBorder, this.rightBorder);
    }

    //Sprite methods

    /**
     * Moves the paddle to the direction inputted by the keyboard.
     */
    @Override
    public void timePassed() {
        boolean left = this.keyboard.isPressed(KeyboardSensor.LEFT_KEY);
        boolean right = this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY);

        //Only moves if exactly one of the keys is pressed
        if (left && !right) {
            moveLeft();
        } else if (right && !left) {
            moveRight();
        }
    }

    /**
     * Draws the paddle on the given drawing surface.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        super.drawOn(d);
    }

    //Collidable methods
    @Override
    public Rectangle getCollisionRectangle() {
        return super.getCollisionRectangle();
    }

    /**
     * Returns the new velocity of a ball that has collided with the paddle.
     *
     * <p>If the collision is on the sides, the new velocity a normal collision.
     * If the collision is on the top, the new velocity depends on which section the collision occurred -
     * The more left the section is, the steeper the return angle of the new velocity to the left.
     * The more right the section is, the steeper the return angle of the new velocity to the right.
     * If the section is in the middle, the new velocity is a normal collision (the return angle is the same).
     * Bottom collisions are not handled, and the new velocity is the same as the old one.
     *
     * @param hitter          the hitting ball
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @return the new velocity
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle rect = getRect();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        //Checking which lines the point is on
        boolean upper = rect.getUpperLine().contains(collisionPoint);
        boolean left = rect.getLeftLine().contains(collisionPoint);
        boolean right = rect.getRightLine().contains(collisionPoint);

        /*
         * To deal with rectangle corner collision, we can check the sign of the components of the velocity:
         * if the sign of the component is opposite to the side of the rectangle the collision point is on, that
         * means that the collision happens in the other side of the rectangle, since an object can't collide a side
         * from the inside of the rectangle.
         * For example, if an object is colliding exactly with the top left corner of the rectangle and his velocity
         * is (10, -10), we can be sure that it is colliding with the left side and not the upper side, since it's
         * impossible for an object to hit the upper side with a negative y velocity.
         */
        if (upper && dy > 0) {
            double collisionOnRectX = collisionPoint.getX() - rect.getLeft();
            double sectionLength = rect.getWidth() / NUMBER_OF_SECTIONS;
            int sectionNum = (int) (collisionOnRectX / sectionLength);

            //Fixing the section num to be within range
            sectionNum = Math.min(NUMBER_OF_SECTIONS - 1, Math.max(0, sectionNum));

            //Calculating new angle
            int angleDifference = 2 * (UP_ANGLE - MIN_ANGLE) / (NUMBER_OF_SECTIONS - 1);
            int newAngle = MIN_ANGLE + angleDifference * sectionNum;

            if (newAngle != UP_ANGLE) {
                return Velocity.fromAngleAndSpeed(newAngle, currentVelocity.getSpeed());
            } else {
                return new Velocity(dx, -Math.abs(dy));
            }
        }
        if (left && dx > 0) {
            return new Velocity(-Math.abs(dx), dy);
        }
        if (right && dx < 0) {
            return new Velocity(Math.abs(dx), dy);
        }
        return currentVelocity;
    }

    /**
     * Add the paddle to the game.
     *
     * @param g the game
     */
    @Override
    public void addToGame(GameLevel g) {
        super.addToGame(g);
    }

    @Override
    protected String getName() {
        return "Paddle";
    }
}
