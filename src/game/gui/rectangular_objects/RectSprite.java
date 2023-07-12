package game.gui.rectangular_objects;

import biuoop.DrawSurface;
import game.animations.GameLevel;
import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.Sprite;
import game.logic.DoubleMethods;

import java.awt.Color;

/**
 * A rectangle shaped sprite.
 */
public abstract class RectSprite implements Sprite {
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private Rectangle rect;
    private Color color;

    /**
     * Instantiates a new rectangular sprite from a rectangle and a color.
     *
     * @param rect  the rectangle
     * @param color the color
     */
    public RectSprite(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
    }

    /**
     * Instantiates a new rectangular sprite from a rectangle. Sets the color to be the {@link #DEFAULT_COLOR}.
     *
     * @param rect the rectangle
     * @see #RectSprite(Rectangle, Color)
     */
    public RectSprite(Rectangle rect) {
        this(rect, DEFAULT_COLOR);
    }

    /**
     * Returns the rectangle of the rectangular sprite.
     *
     * @return the rectangle
     */
    public Rectangle getRect() {
        return this.rect;
    }

    protected void setRect(Rectangle rect) {
        this.rect = rect;
    }

    /**
     * Returns the color of the rectangular sprite.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Sets the color of the rectangular sprite.
     *
     * @param color the color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Adds the rectangular sprite to the given game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * Removes the rectangular sprite from the given game.
     *
     * @param g the game
     */
    public void removeFromGame(GameLevel g) {
        g.removeSprite(this);
    }

    protected abstract RectSprite clone(Rectangle rect);

    //Sprite methods

    /**
     * Draws the rectangular sprite on a given {@link DrawSurface}.
     *
     * @param d the draw surface
     */
    @Override
    public void drawOn(DrawSurface d) {
        //Saving and converting values to int
        Point upperLeft = this.rect.getUpperLeft();
        int x = DoubleMethods.round(upperLeft.getX());
        int y = DoubleMethods.round(upperLeft.getY());
        int width = DoubleMethods.round(this.rect.getWidth());
        int height = DoubleMethods.round(this.rect.getHeight());

        //Drawing the rectangle
        d.setColor(this.color);
        d.fillRectangle(x, y, width, height);
    }

    protected void drawEdges(DrawSurface d, Color edgeColor) {
        //Saving and converting values to int
        Point upperLeft = this.rect.getUpperLeft();
        int x = DoubleMethods.round(upperLeft.getX());
        int y = DoubleMethods.round(upperLeft.getY());
        int width = DoubleMethods.round(this.rect.getWidth());
        int height = DoubleMethods.round(this.rect.getHeight());

        d.setColor(edgeColor);
        d.drawRectangle(x, y, width, height);
    }

    @Override
    public abstract void timePassed();

    @Override
    public RectSprite getCurrentSpriteFrame() {
        return this;
    }

    @Override
    public RectSprite moveX(double x) {
        Rectangle newRect = this.rect.moveX(x);
        return this.clone(newRect);
    }

    @Override
    public RectSprite moveY(double y) {
        Rectangle newRect = this.rect.moveY(y);
        return this.clone(newRect);
    }

    @Override
    public RectSprite scale(double factor) {
        return this.clone(this.rect.scale(factor));
    }

    @Override
    public RectSprite scale(double factor, Point p) {
        return this.clone(this.rect.scale(factor, p));
    }

    @Override
    public Point getCenter() {
        return this.rect.getCenter();
    }

    protected abstract String getName();

    @Override
    public String toString() {
        return this.getName() + " => Rectangle: [" + this.rect + "], Color: " + this.color;
    }
}
