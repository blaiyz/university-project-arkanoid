package game.levels.lunar;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.animated_sprites.animation_types.ScaleAnimation;
import game.gui.animated_sprites.animation_types.SpriteAnimation;
import game.gui.animated_sprites.phases.AnimationPhase;
import game.gui.animated_sprites.phases.PositiveOnlySine;
import game.gui.animated_sprites.phases.Sine;
import game.gui.rectangular_objects.Block;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Star implements Sprite {
    private static final double AMPLITUDE = 0.15;
    public static final int ANIMATION_TIME = 15000;
    private static final double THICKNESS = 1.64;
    private static final double LENGTH = 0.3;
    private static final Color COLOR = Color.WHITE;
    public static final int NUMBER_OF_RECTS_FACTOR = 3;
    private Sprite star;

    public Star(Point pos, double size, int fps, boolean setDefaultAnimation) {
        List<Sprite> rectangles = new ArrayList<>();
        int numberOfRects = (int) size * NUMBER_OF_RECTS_FACTOR;
        for (int i = 1; i < numberOfRects; i++) {
            double theta = i * (Math.PI) / (2 * numberOfRects);

            addRectangle(rectangles, theta);
        }
        //Small angles
        this.addRectangle(rectangles, 0.01);
        this.addRectangle(rectangles, 0.03);
        this.addRectangle(rectangles, 0.05);
        this.addRectangle(rectangles, 0.08);
        this.addRectangle(rectangles, Math.PI / 2 - 0.01);
        this.addRectangle(rectangles, Math.PI / 2 - 0.03);
        this.addRectangle(rectangles, Math.PI / 2 - 0.05);
        this.addRectangle(rectangles, Math.PI / 2 - 0.08);


        MultiSprite ms = new MultiSprite(rectangles);
        ms = ms.moveX(pos.getX());
        ms = ms.moveY(pos.getY());
        ms = ms.scale(size);

        if (setDefaultAnimation) {
            Random rng = new Random();
            this.star = new ScaleAnimation(ms, fps);
            AnimationPhase phase = new PositiveOnlySine(1, 0.9, ANIMATION_TIME);
            ((SpriteAnimation) this.star).enableRepeat(phase);
            ((SpriteAnimation) this.star).setOffset(rng.nextInt(0, ANIMATION_TIME - 1));
        } else {
            this.star = ms;
        }
    }

    private void addRectangle(List<Sprite> rectangles, double theta) {
        double x = angleToX(theta);


        double y = THICKNESS / (x + LENGTH) - LENGTH;
        Rectangle rect = new Rectangle(-x, -y, 2 * x, 2 * y);
        rectangles.add(new Block(rect, COLOR, COLOR));
    }

    private double angleToX(double theta) {
        double a = LENGTH;
        double b = THICKNESS;
        return 1d / 2 * (
                (1 / Math.tan(theta))
                        * Math.sqrt(
                        Math.pow(
                                a * Math.tan(theta) + a,
                                2
                        )
                                - 4 * (a * a - b) * Math.tan(theta)
                )
                        + a
                        * (-(1 / Math.tan(theta)))
                        - a
        );
    }

    @Override
    public void timePassed() {
        star.timePassed();
    }

    @Override
    public void drawOn(DrawSurface d) {
        star.drawOn(d);
    }

    @Override
    public Sprite getCurrentSpriteFrame() {
        return star.getCurrentSpriteFrame();
    }

    @Override
    public Sprite moveX(double x) {
        return star.moveX(x);
    }

    @Override
    public Sprite moveY(double y) {
        return star.moveY(y);
    }

    @Override
    public Sprite scale(double factor) {
        return star.scale(factor);
    }

    @Override
    public Sprite scale(double factor, Point p) {
        return star.scale(factor, p);
    }

    @Override
    public Point getCenter() {
        return star.getCenter();
    }
}