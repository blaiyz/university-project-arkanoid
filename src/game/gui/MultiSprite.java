package game.gui;

import biuoop.DrawSurface;
import game.geometry.Point;

import java.util.ArrayList;
import java.util.List;

public class MultiSprite implements Sprite {
    private final List<Sprite> sprites;

    public MultiSprite(List<Sprite> sprites) {
        this.sprites = sprites;
    }

    @Override
    public void timePassed() {
        for (Sprite sprite : this.sprites) {
            sprite.timePassed();
        }
    }

    public void drawOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }

    @Override
    public MultiSprite getCurrentSpriteFrame() {
        List<Sprite> currentSprites = new ArrayList<>();
        for (Sprite sprite : this.sprites) {
            currentSprites.add(sprite.getCurrentSpriteFrame());
        }
        return new MultiSprite(currentSprites);
    }

    @Override
    public MultiSprite moveX(double x) {
        List<Sprite> currentSprites = new ArrayList<>();
        for (Sprite sprite : this.sprites) {
            currentSprites.add(sprite.moveX(x));
        }
        return new MultiSprite(currentSprites);
    }

    @Override
    public MultiSprite moveY(double y) {
        List<Sprite> currentSprites = new ArrayList<>();
        for (Sprite sprite : this.sprites) {
            currentSprites.add(sprite.moveY(y));
        }
        return new MultiSprite(currentSprites);
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
    public MultiSprite scale(double factor) {
        return this.scale(factor, this.getCenter());
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
    public MultiSprite scale(double factor, Point p) {
        List<Sprite> currentSprites = new ArrayList<>();
        for (Sprite sprite : this.sprites) {
            currentSprites.add(sprite.scale(factor, p));
        }
        return new MultiSprite(currentSprites);
    }

    /**
     * Returns the average center point of all sprites.
     *
     * @return the center point
     */
    @Override
    public Point getCenter() {
        double xSum = 0;
        double ySum = 0;

        for (Sprite sprite : this.sprites) {
            Point center = sprite.getCenter();
            xSum += center.getX();
            ySum += center.getY();
        }

        int numberOfSprites = this.sprites.size();
        return new Point(xSum / numberOfSprites, ySum / numberOfSprites);
    }
}
