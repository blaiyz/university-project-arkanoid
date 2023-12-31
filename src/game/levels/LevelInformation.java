package game.levels;

import game.gui.Sprite;
import game.gui.rectangular_objects.Block;
import game.logic.physics.Velocity;

import java.awt.Color;
import java.util.List;

public interface LevelInformation {
    int numberOfBalls();

    // The initial velocity of each ball
    // Note that initialBallVelocities().size() == numberOfBalls()
    List<Velocity> initialBallVelocities();

    int paddleSpeed();

    int paddleWidth();

    // the level name will be displayed at the top of the screen.
    String levelName();

    // Returns a sprite with the background of the level
    Sprite getBackground();

    // The Blocks that make up this level, each block contains
    // its size, color and location.
    List<Block> blocks();

    // Number of blocks that should be removed
    // before the level is considered to be "cleared".
    // This number should be <= blocks.size();
    int numberOfBlocksToRemove();

    List<Block> borders();

    int width();

    int height();
    int rightBorder();
    int leftBorder();

    int fps();

    Color paddleColor();
}