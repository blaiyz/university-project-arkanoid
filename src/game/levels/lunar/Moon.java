package game.levels.lunar;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.gui.Circle;
import game.gui.Sprite;
import game.logic.DoubleMethods;

import java.awt.Color;

public class Moon implements Sprite {
    private static final Color COLOR = new Color(208, 208, 208);
    private static final Color CRATER_COLOR = new Color(106, 106, 106);
    private static final double SHADOW_WIDTH = 0.3;
    private final Circle moon;

    public Moon(Point pos, double size) {
        this.moon = new Circle(pos, size, COLOR);
    }

    @Override
    public void timePassed() {
        moon.timePassed();
    }

    @Override
    public void drawOn(DrawSurface d) {
        moon.drawOn(d);

        Point center = this.moon.getCenter();
        double radius = this.moon.getSize();

        //Drawing the craters
        d.setColor(CRATER_COLOR);
        int x = DoubleMethods.round(center.getX() - radius * 0.3);
        int y = DoubleMethods.round(center.getY());
        int newRad = DoubleMethods.round(radius * 0.4);
        d.fillCircle(x, y, newRad);

        d.setColor(CRATER_COLOR.darker());
        x = DoubleMethods.round(center.getX() - radius * 0.3);
        y = DoubleMethods.round(center.getY());
        newRad = DoubleMethods.round(radius * 0.3);
        d.fillCircle(x, y, newRad);

        d.setColor(CRATER_COLOR);
        x = DoubleMethods.round(center.getX() - radius * 0.7);
        y = DoubleMethods.round(center.getY() + radius * 0.4);
        newRad = DoubleMethods.round(radius * 0.1);
        d.fillCircle(x, y, newRad);

        x = DoubleMethods.round(center.getX());
        y = DoubleMethods.round(center.getY() + radius * 0.7);
        newRad = DoubleMethods.round(radius * 0.3);
        d.fillCircle(x, y, newRad);

        x = DoubleMethods.round(center.getX() - radius);
        y = DoubleMethods.round(center.getY() - radius * 0.1);
        int width = DoubleMethods.round(radius * 0.1);
        int height = DoubleMethods.round(radius * 0.3);
        d.fillOval(x, y, width, height);

        x = DoubleMethods.round(center.getX() - radius * 0.9);
        y = DoubleMethods.round(center.getY() - radius * 0.5);
        width = DoubleMethods.round(radius * 0.2);
        height = DoubleMethods.round(radius * 0.25);
        d.fillOval(x, y, width, height);


        //Drawing the shadow
        x = DoubleMethods.round(center.getX() - radius * SHADOW_WIDTH);
        y = DoubleMethods.round(center.getY() - radius);
        width = DoubleMethods.round(radius * SHADOW_WIDTH * 2);
        height = DoubleMethods.round(2 * radius);

        d.setColor(Color.BLACK);
        d.fillOval(x, y, width, height);

        x = DoubleMethods.round(center.getX());
        y = DoubleMethods.round(center.getY() - radius);
        width = DoubleMethods.round(radius);
        d.fillRectangle(x, y, width, height);
    }

    @Override
    public Sprite getCurrentSpriteFrame() {
        return moon.getCurrentSpriteFrame();
    }

    @Override
    public Sprite moveX(double x) {
        return moon.moveX(x);
    }

    @Override
    public Sprite moveY(double y) {
        return moon.moveY(y);
    }

    @Override
    public Sprite scale(double factor) {
        return moon.scale(factor);
    }

    @Override
    public Sprite scale(double factor, Point p) {
        return moon.scale(factor, p);
    }

    @Override
    public Point getCenter() {
        return moon.getCenter();
    }
}