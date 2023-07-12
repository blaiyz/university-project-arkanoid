package game.gui;

import biuoop.DrawSurface;
import game.geometry.Point;

import java.awt.Color;

public class DoubleText implements Sprite {
    private static final Color DEFAULT_SHADOW_COLOR = Color.BLACK;
    private final Text text;
    private final Color shadowColor;
    private final double offset;

    public DoubleText(Text text, Color shadowColor, double offset) {
        this.text = text;
        this.shadowColor = shadowColor;
        this.offset = offset;
    }

    public DoubleText(Text text, double offset) {
        this.text = text;
        this.shadowColor = DEFAULT_SHADOW_COLOR;
        this.offset = offset;
    }

    @Override
    public void drawOn(DrawSurface d) {
        Text offsettedText = this.text.moveX(this.offset).moveY(this.offset);
        offsettedText.setColor(this.shadowColor);
        offsettedText.drawOn(d);
        this.text.drawOn(d);
    }

    @Override
    public void timePassed() {
    }

    @Override
    public Sprite getCurrentSpriteFrame() {
        return this;
    }

    @Override
    public Sprite moveX(double x) {
        return new DoubleText(this.text.moveX(x), this.shadowColor, this.offset);
    }

    @Override
    public Sprite moveY(double y) {
        return new DoubleText(this.text.moveY(y), this.shadowColor, this.offset);
    }

    @Override
    public Sprite scale(double factor) {
        return new DoubleText(this.text.scale(factor), this.shadowColor, this.offset * factor);
    }

    @Override
    public Sprite scale(double factor, Point p) {
        return new DoubleText(this.text.scale(factor, p), this.shadowColor, this.offset * factor);
    }

    @Override
    public Point getCenter() {
        return this.text.getCenter();
    }
}
