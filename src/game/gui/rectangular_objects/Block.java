package game.gui.rectangular_objects;

import biuoop.DrawSurface;
import game.animations.GameLevel;
import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.Ball;
import game.logic.physics.Collidable;
import game.logic.physics.Velocity;
import game.logic.physics.hit_event.HitListener;
import game.logic.physics.hit_event.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A collidable block in a shape of a rectangle that can be used in a GUI.
 *
 * @see Collidable
 * @see game.gui.Sprite
 */
public class Block extends RectSprite implements Collidable, HitNotifier {
    private static final Color DEFAULT_EDGE_COLOR = Color.BLACK;
    private Color edgeColor;
    private final List<HitListener> hitListeners;


    /**
     * Instantiates a new Block from a given rectangle, color and an edge color.
     *
     * @param rect        the rectangle
     * @param color       the color
     * @param edgeColor the border color
     */
    public Block(Rectangle rect, Color color, Color edgeColor) {
        super(rect, color);
        this.edgeColor = edgeColor;
        hitListeners = new ArrayList<>();
    }

    /**
     * Instantiates a new Block from a given rectangle and color.
     *
     * @param rect  the rectangle
     * @param color the color
     */
    public Block(Rectangle rect, Color color) {
        this(rect, color, DEFAULT_EDGE_COLOR);
    }

    /**
     * Instantiates a new Block from a rectangle.
     *
     * @param rect the rectangle
     * @see #Block(Rectangle, Color)
     */
    public Block(Rectangle rect) {
        super(rect);
        this.edgeColor = DEFAULT_EDGE_COLOR;
        hitListeners = new ArrayList<>();
    }

    /**
     * Instantiates a new Block from a point, width, height and color.
     *
     * @param p      the point
     * @param width  the width
     * @param height the height
     * @param color  the color
     * @see #Block(Rectangle, Color)
     */
    public Block(Point p, double width, double height, Color color) {
        this(new Rectangle(p, width, height), color);
    }

    /**
     * Returns the rectangle of the block.
     *
     * @return the rectangle
     */
    public Rectangle getRect() {
        return super.getRect();
    }

    /**
     * Returns the color of the block.
     *
     * @return the color
     */
    public Color getColor() {
        return super.getColor();
    }

    /**
     * Sets the color of the block.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        super.setColor(color);
    }

    /**
     * Adds the block to the given game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        super.addToGame(g);
        g.addCollidable(this);
    }

    /**
     * Removes the block from the given game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        super.removeFromGame(g);
        g.removeCollidable(this);
    }

    @Override
    protected Block clone(Rectangle rect) {
        return new Block(rect, super.getColor(), this.edgeColor);
    }
    @Override
    public Block moveX(double x) {
        Rectangle newRect = super.getRect().moveX(x);
        return this.clone(newRect);
    }

    @Override
    public Block moveY(double y) {
        Rectangle newRect = super.getRect().moveY(y);
        return this.clone(newRect);
    }

    @Override
    public Block scale(double factor) {
        return this.clone(super.getRect().scale(factor));
    }

    @Override
    public Block scale(double factor, Point p) {
        return this.clone(super.getRect().scale(factor, p));
    }

    //Collidable methods
    @Override
    public Rectangle getCollisionRectangle() {
        return getRect();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        Rectangle rect = getRect();
        double dx = currentVelocity.getDx();
        double dy = currentVelocity.getDy();

        //Checking which lines the point is on
        boolean upper = rect.getUpperLine().contains(collisionPoint);
        boolean left = rect.getLeftLine().contains(collisionPoint);
        boolean right = rect.getRightLine().contains(collisionPoint);
        boolean lower = rect.getLowerLine().contains(collisionPoint);
        notifyHit(hitter);

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
            return new Velocity(dx, -Math.abs(dy));
        }
        if (left && dx > 0) {
            return new Velocity(-Math.abs(dx), dy);
        }
        if (right && dx < 0) {
            return new Velocity(Math.abs(dx), dy);
        }
        if (lower && dy < 0) {
            return new Velocity(dx, Math.abs(dy));
        }
        return currentVelocity;
    }

    //Sprite methods

    /**
     * Draws the block on a given {@link DrawSurface}.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        super.drawOn(d);
        super.drawEdges(d, this.edgeColor);
    }

    /**
     * Blocks do not change after they have been created => Empty implementation.
     */
    @Override
    public void timePassed() {
    }

    //HitNotifier methods
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    @Override
    public void addHitListener(HitListener hl) {
        if (!this.hitListeners.contains(hl)) {
            this.hitListeners.add(hl);
        }
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }


    @Override
    protected String getName() {
        return "Block";
    }
}
