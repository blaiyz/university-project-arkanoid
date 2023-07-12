package game.gui;

import biuoop.DrawSurface;
import game.animations.GameLevel;
import game.geometry.Point;
import game.geometry.shapes.Line;
import game.logic.DoubleMethods;

import java.awt.Color;

/**
 * 2-dimensional circle.
 *
 * @see Point
 */
public class Circle implements Sprite {
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    private final double size;
    private final Color color;
    private final Color borderColor;
    private Point point;

    /**
     * Instantiates a new circle from a center, radius and two {@link Color}s.
     *
     * @param center      the center point
     * @param r           the radius
     * @param color       the color
     * @param borderColor border color
     */
    public Circle(Point center, double r, Color color, Color borderColor) {
        this.point = center;
        this.size = r;
        this.color = color;
        this.borderColor = borderColor;
    }

    /**
     * Instantiates a new circle from a center, radius and {@link Color}.
     *
     * @param center the center point
     * @param r      the radius
     * @param color  the color
     */
    public Circle(Point center, double r, Color color) {
        this(center, r, color, DEFAULT_BORDER_COLOR);
    }

    /**
     * Instantiates a new circle from the center point coordinates, the radius and the {@link Color}.
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param r     the radius
     * @param color the color
     * @see #Circle(Point center, double r, Color color,)
     */
    public Circle(double x, double y, double r, Color color) {
        this(new Point(x, y), r, color);
    }

    /**
     * Returns the x coordinate of the center point.
     *
     * @return the x coordinate
     */
    public int getX() {
        return DoubleMethods.round(this.point.getX());
    }

    /**
     * Returns the y coordinate of the center point.
     *
     * @return the y coordinate
     */
    public int getY() {
        return DoubleMethods.round(this.point.getY());
    }

    /**
     * Returns the rounded size (radius).
     *
     * @return the size
     */
    public int getSizeRounded() {
        return DoubleMethods.round(this.size);
    }

    /**
     * Returns the size (radius).
     *
     * @return the size
     */
    public double getSize() {
        return this.size;
    }

    /**
     * Returns the circle's {@link Color}.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the circle's {@link #point} to the given point.
     *
     * @param p the point
     */
    public void setPoint(Point p) {
        this.point = p;
    }

    /**
     * Adds the circle to the given game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Removes the circle from the given game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    //Sprite methods

    @Override
    public void timePassed() {
    }

    /**
     * Draws the circle on a given {@link DrawSurface}.
     *
     * @param d the surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillCircle(this.getX(), this.getY(), this.getSizeRounded());
        d.setColor(this.borderColor);
        d.drawCircle(this.getX(), this.getY(), this.getSizeRounded());
    }

    @Override
    public Circle getCurrentSpriteFrame() {
        return this;
    }

    @Override
    public Circle moveX(double x) {
        return new Circle(this.point.moveX(x), this.size, this.color, this.borderColor);
    }

    @Override
    public Circle moveY(double y) {
        return new Circle(this.point.moveY(y), this.size, this.color, this.borderColor);
    }

    /**
     * Returns an identical sprite that has been scaled up or down according to the given factor.
     * The factor acts as a relative new size, for example '1' would be no size change, '0.5' would shrink by half,
     * and 2 would grow to double the size.
     *
     * @param factor resize factor
     * @return the new sprite
     */
    @Override
    public Circle scale(double factor) {
        return this.scale(factor, this.point);
    }

    /**
     * Returns an identical sprite that has been scaled up or down according to the given factor.
     * The factor acts as a relative new size, for example '1' would be no size change, '0.5' would shrink by half,
     * and 2 would grow to double the size.
     * The sprite is scaled to the given point as the center, so the more scaled down the sprite is, the closer it is
     * to the center point.
     *
     * @param factor resize factor
     * @param p      the center point
     * @return the new sprite
     */
    @Override
    public Circle scale(double factor, Point p) {
        Point newCenter = new Line(this.point, p).getPointFromProportions(factor);
        return new Circle(newCenter, this.size * factor, this.color, this.borderColor);
    }

    /**
     * Returns the center point of the sprite.
     *
     * @return the center point
     */
    @Override
    public Point getCenter() {
        return this.point;
    }

    @Override
    public String toString() {
        return "Circle => Center: " + this.point + ", Radius: " + this.size;
    }
}
