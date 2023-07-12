package game.levels.solar;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.gui.Circle;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.animated_sprites.animation_types.MoveYAnimation;
import game.gui.animated_sprites.animation_types.SpriteAnimation;
import game.gui.animated_sprites.phases.AnimationPhase;
import game.gui.animated_sprites.phases.Sine;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Cloud implements Sprite {
    private static final double AMPLITUDE = 10;
    public static final int ANIMATION_TIME = 6000;
    private final SpriteAnimation cloud;
    private final Point pos;

    public Cloud(Point pos, double size, long offset, int fps, Color color) {
        this.pos = pos;

        List<Sprite> circles = new ArrayList<>();
        Circle circle = new Circle(new Point(0, 0), 1, color, color);
        circles.add(circle);
        circles.add(circle.moveX(1));
        circles.add(circle.moveX(-1).moveY(0.2).scale(0.8));
        circles.add(circle.moveX(-0.5).moveY(-0.7));
        circles.add(circle.moveX(0.8).moveY(-0.5));

        MultiSprite ms = new MultiSprite(circles);
        ms = ms.moveX(pos.getX());
        ms = ms.moveY(pos.getY());
        ms = ms.scale(size);

        AnimationPhase phase = new Sine(AMPLITUDE, 0, ANIMATION_TIME);
        this.cloud = new MoveYAnimation(ms, fps);
        this.cloud.setOffset(offset);
        this.cloud.enableRepeat(phase);
    }

    @Override
    public void timePassed() {
        cloud.timePassed();
    }

    @Override
    public void drawOn(DrawSurface d) {
        cloud.drawOn(d);
    }

    @Override
    public Sprite getCurrentSpriteFrame() {
        return cloud.getCurrentSpriteFrame();
    }

    @Override
    public Sprite moveX(double x) {
        return cloud.moveX(x);
    }

    @Override
    public Sprite moveY(double y) {
        return cloud.moveY(y);
    }

    @Override
    public Sprite scale(double factor) {
        return cloud.scale(factor);
    }

    @Override
    public Sprite scale(double factor, Point p) {
        return cloud.scale(factor, p);
    }

    @Override
    public Point getCenter() {
        return cloud.getCenter();
    }
}
