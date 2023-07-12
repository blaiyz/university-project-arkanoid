import biuoop.GUI;
import game.GameFlow;
import game.animations.AnimationRunner;
import game.levels.LevelInformation;
import game.levels.lunar.Lunar;
import game.levels.martian.Martian;
import game.levels.solar.Solar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A program that runs a game of Arkanoid.
 */
public class main {
    private static final int FPS = 60;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final LevelInformation[] LEVELS = {
            new Martian(WIDTH, HEIGHT, FPS),
            new Solar(WIDTH, HEIGHT, FPS),
            new Lunar(WIDTH, HEIGHT, FPS)
    };

    /**
     * The entry point of the program.
     *
     * @param args the input arguments (not used)
     */
    public static void main(String[] args) {
        List<LevelInformation> levels = new ArrayList<>();
        for (String arg : args) {
            try {
                int levelNumber = Integer.parseInt(arg);
                //In the level range
                if (1 <= levelNumber && levelNumber <= LEVELS.length) {
                    levels.add(LEVELS[levelNumber - 1]);
                }
            } catch (NumberFormatException e) {
                //String is not a number, ignoring.
            }
        }
        if (levels.size() == 0) {
            levels = Arrays.asList(LEVELS);
        }


        GUI gui = new GUI("Arkanoid", WIDTH, HEIGHT);
        AnimationRunner runner = new AnimationRunner(gui, FPS);
        GameFlow gf = new GameFlow(runner , gui.getKeyboardSensor(), WIDTH, HEIGHT, FPS);

        gf.runLevels(levels);
        gui.close();
    }
}
