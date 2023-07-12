package game.gui;

import biuoop.DrawSurface;

import java.util.LinkedList;
import java.util.List;

/**
 * A collection of {@link Sprite} objects.
 */
public class SpriteCollection {
    private final List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new LinkedList<>();
    }

    /**
     * Adds a sprite to the collection.
     *
     * @param s the s
     */
    public void addSprite(Sprite s) {
        if (s != null) {
            this.sprites.add(s);
        } else {
            System.out.println("Warning: attempted to add null to SpriteCollection");
        }
    }

    /**
     * Removes a sprite.
     *
     * @param s the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.remove(s);
    }

    /**
     * call {@link Sprite#timePassed()} on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> sprites = new LinkedList<>(this.sprites);

        for (Sprite sprite : sprites) {
            sprite.timePassed();
        }
    }

    /**
     * call {@link Sprite#drawOn(DrawSurface)} on all sprites.
     *
     * @param d the draw surface
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite sprite : this.sprites) {
            sprite.drawOn(d);
        }
    }

    @Override
    public String toString() {
        String sb = "";
        for (Sprite sprite : this.sprites) {
            sb += sprite + "\n";
        }
        return sb;
    }
}