package game.levels.solar;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.geometry.shapes.Line;
import game.gui.Circle;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.animated_sprites.ChangingSizeSpriteBuilder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Sun implements Sprite {
    private static final int NUMBER_OF_LINES = 40;
    private final Point center;
    private final double size;
    private final int fps;
    private final Line[] lines;
    private final MultiSprite sunCircle;

    public Sun(Point center, double size, int fps) {
        this.center = center;
        this.size = size;
        this.fps = fps;

        List<Sprite> sunCircles = new ArrayList<>();
        Circle innerCircle = new Circle(center, 1, Color.YELLOW, Color.YELLOW).scale(size);
        Color color = new Color(255, 195, 31);
        Circle middleCircle = new Circle(center, 1.15, color, color).scale(size);
        color = new Color(255, 134, 20);
        Circle outerCircle = new Circle(center, 1.25, color, color).scale(size);

        ChangingSizeSpriteBuilder animationBuilder = new ChangingSizeSpriteBuilder(fps);
        sunCircles.add(animationBuilder.animateSprite(outerCircle, 0.06, 7000, 0));
        sunCircles.add(animationBuilder.animateSprite(middleCircle, 0.06, 6000, 3000));

        sunCircles.add(innerCircle);
        this.sunCircle = new MultiSprite(sunCircles);

        this.lines = new Line[NUMBER_OF_LINES];
        for (int i = 0; i < NUMBER_OF_LINES; i++) {
            double x1 = this.center.getX() + Math.cos(i * 2 * Math.PI / NUMBER_OF_LINES) * size;
            double y1 = this.center.getY() + Math.sin(i * 2 * Math.PI / NUMBER_OF_LINES) * size;
            double x2 = this.center.getX() + Math.cos(i * 2 * Math.PI / NUMBER_OF_LINES) * (size * 1.5);
            double y2 = this.center.getY() + Math.sin(i * 2 * Math.PI / NUMBER_OF_LINES) * (size * 1.5);

            lines[i] = new Line(x1, y1, x2, y2);
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(new Color(255, 134, 20));
        for (Line line : this.lines) {
            line.drawOn(d);
        }
        this.sunCircle.drawOn(d);
    }

    @Override
    public void timePassed() {
        this.sunCircle.timePassed();
    }

    @Override
    public MultiSprite getCurrentSpriteFrame() {
        return sunCircle.getCurrentSpriteFrame();
    }

    @Override
    public MultiSprite moveX(double x) {
        return sunCircle.moveX(x);
    }

    @Override
    public MultiSprite moveY(double y) {
        return sunCircle.moveY(y);
    }

    @Override
    public MultiSprite scale(double factor) {
        return sunCircle.scale(factor);
    }

    @Override
    public MultiSprite scale(double factor, Point p) {
        return sunCircle.scale(factor, p);
    }

    @Override
    public Point getCenter() {
        return sunCircle.getCenter();
    }

}
