package game.gui;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.geometry.shapes.Line;
import game.geometry.shapes.Rectangle;
import game.logic.DoubleMethods;

import java.awt.Color;

public class Text implements Sprite {
    private static final Color DEFAULT_COLOR = Color.BLACK;
    //Size represents the height of each letter, so we fix that for DrawSurface.draw to be accurate.
    private static final double SCALE_FIX = 1.4;
    private final String text;
    private final Point position;
    private final double size;
    private Color color;

    public Text(String text, Point position, double size, Color color) {
        this.text = text;
        this.position = position;
        this.size = size;
        this.color = color;
    }

    public Text(String text, Point position, double size) {
        this(text, position, size, DEFAULT_COLOR);
    }

    //Using a static method in order to simplify the calculation (and not be forced to calculate all in a single line)
    public static Text fromRectangle(String text, Rectangle rect, Color color) {
        double width = rect.getWidth();
        double height = rect.getHeight();
        double size = Math.min(SCALE_FIX * width / text.length(), height);

        double x = rect.getLeft();
        double y = rect.getTop();

        //Setting the text to be centered in the middle of both x and y axes
        x = x + Math.max((width - text.length() * (size / SCALE_FIX)) / 2, 0);
        y = y + Math.max((height - size) / 2, 0);

        return new Text(text, new Point(x, y), size, color);
    }

    public static Text fromCenterPosition(String text, Point position, double size, Color color) {
        double x = position.getX();
        double y = position.getY();
        Point newPoint = new Point(x - (text.length() * (size / SCALE_FIX) / 2), y);
        return new Text(text, newPoint, size, color);
    }

    public static Text fromRectangle(String text, Rectangle rect) {
        return Text.fromRectangle(text, rect, DEFAULT_COLOR);
    }


    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void drawOn(DrawSurface d) {
        //Converting to int
        int x = DoubleMethods.round(this.position.getX());
        int y = DoubleMethods.round(this.position.getY() + this.size);
        int textSize = DoubleMethods.round(this.size * SCALE_FIX);

        d.setColor(this.color);
        d.drawText(x, y, this.text, textSize);
    }

    @Override
    public void timePassed() {
    }

    @Override
    public Text getCurrentSpriteFrame() {
        return this;
    }

    @Override
    public Text moveX(double x) {
        return new Text(this.text, this.position.moveX(x), this.size, this.color);
    }

    @Override
    public Text moveY(double y) {
        return new Text(this.text, this.position.moveY(y), this.size, this.color);
    }

    @Override
    public Text scale(double factor, Point p) {
        Point newPosition = new Line(this.position, p).getPointFromProportions(factor);
        return new Text(this.text, newPosition, size * factor, this.color);
    }

    @Override
    public Text scale(double factor) {
        return scale(factor, this.getCenter());
    }

    @Override
    public Point getCenter() {
        double x = this.position.getX() + this.size * this.text.length() / (2 * SCALE_FIX);
        double y = this.position.getY() + this.size / 2;
        return new Point(x, y);
    }
}
