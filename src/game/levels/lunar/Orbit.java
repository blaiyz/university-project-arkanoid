package game.levels.lunar;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.gui.Sprite;
import game.gui.animated_sprites.animation_types.MoveXAnimation;
import game.gui.animated_sprites.animation_types.MoveYAnimation;
import game.gui.animated_sprites.animation_types.ScaleAnimation;
import game.gui.animated_sprites.animation_types.SpriteAnimation;
import game.gui.animated_sprites.phases.AnimationPhase;
import game.gui.animated_sprites.phases.PositiveOnlySine;
import game.gui.animated_sprites.phases.Sine;
import game.logic.DoubleMethods;

import java.awt.Color;

public class Orbit implements Sprite {
    private static final Color MOON_COLOR = new Color(208, 208, 208);
    public static final double STAR_SIZE = 2;
    private static final Color COLOR = new Color(208, 208, 208);
    private static final double ANGLE = 2.5;
    private static final long ORBIT_TIME = 10000;
    public static final int NUMBER_OF_LINES = 30;
    private SpriteAnimation orbit;
    private final Point pos;
    private final double size;

    public Orbit(Point pos, double size, int fps) {
        this.pos = pos;
        this.size = size;

        Sprite star = new Star(pos, STAR_SIZE, fps, false).scale(0.25);
        AnimationPhase xPhase = new Sine(2 * (10 * size), 0, ORBIT_TIME);
        AnimationPhase yPhase = new Sine((10 * size), 0, ORBIT_TIME);
        orbit = new MoveXAnimation(star, fps);
        orbit.setOffset(ORBIT_TIME / 4);
        orbit.enableRepeat(xPhase);
        orbit = new MoveYAnimation(orbit, fps);
        orbit.setOffset((long) (ORBIT_TIME / 2 + ANGLE * ORBIT_TIME / 2 * Math.PI));
        orbit.enableRepeat(yPhase);

        AnimationPhase behindMoon = new PositiveOnlySine(4, 3.5, ORBIT_TIME);
        orbit = new ScaleAnimation(orbit, fps);
        orbit.setOffset((long) 5300);
        orbit.enableRepeat(behindMoon);

    }

    @Override
    public void timePassed() {
        orbit.timePassed();
    }

    @Override
    public void drawOn(DrawSurface d) {
        orbit.drawOn(d);

        d.setColor(Color.BLACK);
        int x = DoubleMethods.round(pos.getX() + size * 5);
        int y = DoubleMethods.round(pos.getY() - size * 6);
        int newRad = DoubleMethods.round(size * 8.7);
        d.fillCircle(x, y, newRad);

        d.setColor(new Color(208, 208, 208));
        x = DoubleMethods.round(pos.getX() - size * 5.7);
        y = DoubleMethods.round(pos.getY() - size * 10);
        newRad = DoubleMethods.round(size * 2.6);
        d.fillCircle(x, y, newRad);

    }

    @Override
    public Sprite getCurrentSpriteFrame() {
        return orbit.getCurrentSpriteFrame();
    }

    @Override
    public Sprite moveX(double x) {
        return orbit.moveX(x);
    }

    @Override
    public Sprite moveY(double y) {
        return orbit.moveY(y);
    }

    @Override
    public Sprite scale(double factor) {
        return orbit.scale(factor);
    }

    @Override
    public Sprite scale(double factor, Point p) {
        return orbit.scale(factor, p);
    }

    @Override
    public Point getCenter() {
        return orbit.getCenter();
    }
}