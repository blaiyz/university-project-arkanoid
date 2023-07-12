package game.gui.rectangular_objects;

import biuoop.DrawSurface;
import game.animations.GameLevel;
import game.geometry.shapes.Rectangle;
import game.gui.Text;
import game.logic.Counter;

import java.awt.Color;

/**
 * A sprite which displays a score count in the middle.
 */
public class ScoreIndicator extends RectSprite {
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final Color DEFAULT_FOREROUND_COLOR = Color.BLACK;
    public static final double PADDING_RATIO = 14d / 15;
    private final Counter score;
    private final Color foregroundColor;

    /**
     * Instantiates a new Block from a given rectangle and color.
     *
     * @param rect            the rectangle
     * @param score           the score counter
     * @param backgroundColor the background color
     * @param foregroundColor the foreground color
     */
    public ScoreIndicator(Rectangle rect, Counter score, Color backgroundColor, Color foregroundColor) {
        super(rect, backgroundColor);
        this.score = score;
        this.foregroundColor = foregroundColor;
    }

    /**
     * Instantiates a new Block from a rectangle. Sets the color to be the default colors.
     *
     * @param rect  the rectangle
     * @param score the score counter
     * @see #ScoreIndicator(Rectangle, Counter, Color, Color)
     */
    public ScoreIndicator(Rectangle rect, Counter score) {
        this(rect, score, DEFAULT_BACKGROUND_COLOR, DEFAULT_FOREROUND_COLOR);
    }

    @Override
    public void addToGame(GameLevel g) {
        super.addToGame(g);
    }

    @Override
    protected RectSprite clone(Rectangle rect) {
        return new ScoreIndicator(rect, this.score, super.getColor(), this.foregroundColor);
    }


    //Sprite methods

    @Override
    public void drawOn(DrawSurface d) {
        super.drawOn(d);

        String text = "Score: " + score.getValue();
        Text textBox = Text.fromRectangle(text, super.getRect().scale(PADDING_RATIO), this.foregroundColor);
        textBox.drawOn(d);
    }

    @Override
    public void timePassed() {
    }

    @Override
    protected String getName() {
        return "Score indicator";
    }
}
