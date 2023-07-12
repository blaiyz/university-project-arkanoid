package game;

import biuoop.KeyboardSensor;
import game.animations.AnimationRunner;
import game.animations.GameLevel;
import game.animations.KeyPressStoppableAnimation;
import game.animations.LoseScreen;
import game.animations.WinScreen;
import game.levels.LevelInformation;
import game.logic.Counter;

import java.util.List;

public class GameFlow {
    private final AnimationRunner runner;
    private final KeyboardSensor keyboard;
    private final int width;
    private final int height;
    private final int fps;
    private final Counter score;

    public GameFlow(AnimationRunner ar, KeyboardSensor ks, int width, int height, int fps) {
        this.runner = ar;
        this.keyboard = ks;
        this.width = width;
        this.height = height;
        this.fps = fps;
        this.score = new Counter();
    }

    public void runLevels(List<LevelInformation> levels) {
        // ...
        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo,
                    this.keyboard,
                    this.runner,
                    this.score);

            level.initialize();
            level.run();

            if (!level.won()) {
                runner.run(new KeyPressStoppableAnimation(
                        this.keyboard,
                        KeyboardSensor.SPACE_KEY,
                        new LoseScreen(this.keyboard, this.width, this.height, this.score, this.fps)));
                return;
            }
        }
        runner.run(new KeyPressStoppableAnimation(
                this.keyboard,
                KeyboardSensor.SPACE_KEY,
                new WinScreen(this.keyboard, this.width, this.height, this.score, this.fps)));
    }
}
