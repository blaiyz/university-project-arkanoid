package game.gui;

import biuoop.DrawSurface;
import game.geometry.Point;

/**
 * Objects that can be displayed and moved on a screen.
 */
public interface Sprite {
    /**
     * Draws the sprite on the given {@link DrawSurface}.
     *
     * @param d the d
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the sprite that time has passed - the sprite will move according to its implementation.
     */
    void timePassed();

    /**
     * Used for animated sprites - if the sprite is animated, is returns its current sprite frame in its animation.
     * Animated sprites do not change their values (position, size, etc.) but they can have a different
     * sprite representing values in their current animation frame.
     *
     * @return current sprite frame
     */
    Sprite getCurrentSpriteFrame();

    /**
     * Returns an identical sprite that has moved across the x-axis according to the given argument.
     *
     * @param x the distance to move
     * @return the new sprite
     */
    Sprite moveX(double x);

    /**
     * Returns an identical sprite that has moved across the y-axis according to the given argument.
     *
     * @param y the distance to move
     * @return the new sprite
     */
    Sprite moveY(double y);

    /**
     * Returns an identical sprite that has been scaled up or down according to the given factor.
     * The factor acts as a relative new size, for example '1' would be no size change, '0.5' would shrink by half,
     * and 2 would grow to double the size.
     *
     * @param factor resize factor
     * @return the new sprite
     */
    Sprite scale(double factor);

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
    Sprite scale(double factor, Point p);

    /**
     * Returns the center point of the sprite.
     *
     * @return the center point
     */
    Point getCenter();
}