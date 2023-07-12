package game.animations;

import biuoop.DrawSurface;
import game.geometry.shapes.Rectangle;
import game.gui.DoubleText;
import game.gui.SpriteCollection;
import game.gui.Text;
import game.gui.animated_sprites.animation_types.ScaleAnimation;
import game.gui.animated_sprites.animation_types.SpriteAnimation;
import game.gui.animated_sprites.phases.AnimationPhase;
import game.gui.animated_sprites.phases.FadeIn;

import java.awt.Color;

// The CountdownAnimation will display the given gameScreen,
// for numOfSeconds seconds, and on top of them it will show
// a countdown from countFrom back to 1, where each number will
// appear on the screen for (numOfSeconds / countFrom) seconds, before
// it is replaced with the next one.
public class CountdownAnimation implements Animation {
    private static final Color[] NUMBER_COLORS = {Color.RED, Color.YELLOW, Color.GREEN};
    private static final double END_SIZE = 0.4;
    private static final double OFFSET = 20;
    private static final long MS_IN_SECOND = 1000;
    private final long duration;
    private final int countFrom;
    private final SpriteCollection gameScreen;
    private final int fps;
    private int currentNumber;
    private final SpriteAnimation[] numberAnimations;
    private final double screenWidth;
    private final double screenHeight;
    private boolean running;


    public CountdownAnimation(double numOfSeconds,
                              int countFrom,
                              SpriteCollection gameScreen,
                              int fps,
                              double screenWidth,
                              double screenHeight) {
        this.duration = (long) numOfSeconds * MS_IN_SECOND;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.fps = fps;
        this.numberAnimations = new SpriteAnimation[countFrom];
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.running = true;
        this.initialize();
    }

    private void initialize() {
        long singleNumberDuration = duration / (numberAnimations.length);
        AnimationPhase phase = new FadeIn(1, END_SIZE, singleNumberDuration);

        for (int i = 1; i <= numberAnimations.length; i++) {
            Color color;
            if (i < 3) {
                color = NUMBER_COLORS[i - 1];
            } else {
                color = NUMBER_COLORS[NUMBER_COLORS.length - 1];
            }

            Text text = Text.fromRectangle(String.valueOf(i),
                    new Rectangle(0, 0, this.screenWidth, this.screenHeight),
                    color);
            DoubleText number = new DoubleText(text, OFFSET);
            SpriteAnimation numberAnimation = new ScaleAnimation(number, this.fps);
            numberAnimation.beginAnimation(phase);

            this.numberAnimations[i - 1] = numberAnimation;
        }

        currentNumber = countFrom;
    }

    public void doOneFrame(DrawSurface d) {
        this.gameScreen.drawAllOn(d);

        if (currentNumber == 0) {
            this.running = false;
            return;
        }

        SpriteAnimation currentAnimation = numberAnimations[currentNumber - 1];
        if (currentAnimation.isRunning()) {
            currentAnimation.timePassed();
            currentAnimation.drawOn(d);
        } else {
            currentNumber--;
        }

    }

    public boolean shouldStop() {
        return !this.running;
    }
}
