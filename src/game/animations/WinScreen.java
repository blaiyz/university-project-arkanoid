package game.animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.geometry.Point;
import game.gui.DoubleText;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.Text;
import game.gui.animated_sprites.ChangingSizeSpriteBuilder;
import game.logic.Counter;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class WinScreen implements Animation {
    private KeyboardSensor keyboard;
    private final Counter score;
    private final MultiSprite screen;

    public WinScreen(KeyboardSensor k, int width, int height, Counter score, int fps) {
        this.keyboard = k;
        this.score = score;

        List<Sprite> objects = new ArrayList<>();
        DoubleText winText = new DoubleText(
                Text.fromCenterPosition(
                        "You Win!",
                        new Point((double) width / 2 - 30, (double) height / 2 - 100),
                        90,
                        Color.GREEN
                ),
                Color.GREEN.darker(),
                10);
        ChangingSizeSpriteBuilder animator = new ChangingSizeSpriteBuilder(fps);
        objects.add(animator.animateSprite(winText, 0.1, 3000, 0));

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
