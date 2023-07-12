package game.animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.DoubleText;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.Text;
import game.gui.animated_sprites.ChangingSizeSpriteBuilder;
import game.gui.rectangular_objects.Block;
import game.logic.Counter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class LoseScreen implements Animation {
    private KeyboardSensor keyboard;
    private final Counter score;
    private final MultiSprite screen;

    public LoseScreen(KeyboardSensor k, int width, int height, Counter score, int fps) {
        this.keyboard = k;
        this.score = score;

        List<Sprite> objects = new ArrayList<>();
        objects.add(new Block(new Rectangle(0, 0, width, height), Color.DARK_GRAY));


        DoubleText loseText = new DoubleText(
                Text.fromCenterPosition(
                        "Game Over.",
                        new Point((double) width / 2 - 30, (double) height / 2 - 100),
                        90,
                        Color.RED
                ),
                Color.RED.darker(),
                10);


        ChangingSizeSpriteBuilder animator = new ChangingSizeSpriteBuilder(fps);
        objects.add(animator.animateSprite(loseText, 0.05, 3000, 0));

        DoubleText continueText = new DoubleText(
                Text.fromCenterPosition(
                        "Press Space to Continue",
                        new Point((double) width / 2 - 30, (double) height / 2 + 75),
                        30,
                        Color.WHITE
                ),
                Color.GRAY.darker(),
                5);
        objects.add(continueText);

        Text scoreText = new Text(
                "Your score is " + score.getValue(),
                new Point(20, height - 50),
                25,
                Color.BLUE
        );
        objects.add(scoreText);

        this.screen = new MultiSprite(objects);
    }

    public void doOneFrame(DrawSurface d) {
        this.screen.timePassed();
        this.screen.drawOn(d);
    }

    public boolean shouldStop() {
        return false;
    }
}