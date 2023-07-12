package game.gui;

import game.animations.GameLevel;
import game.gui.rectangular_objects.Block;
import game.logic.Counter;
import game.logic.physics.hit_event.BlockRemover;
import game.logic.physics.hit_event.ScoreTrackingListener;

/**
 * Adds blocks to a game. Gives each block some basic properties (die on hit) and some special properties
 * (kill block, adder block) on a random chance.
 *
 * <p>Not to be confused with classes with similar names under {@link game.logic.physics.hit_event}.
 */
public class BlockAdder {
    //The bigger the number, the smaller the chance
    private final GameLevel gameLevel;
    private final Counter blockCounter;
    private final BlockRemover blockRemover;
    private final ScoreTrackingListener scoreTracker;

    /**
     * Instantiates a new Block adder.
     *
     * @param gameLevel         the game
     * @param blockCounter the block counter
     * @param ballCounter  the ball counter
     * @param score        the score counter
     */
    public BlockAdder(GameLevel gameLevel, Counter blockCounter, Counter ballCounter, Counter score) {
        this.gameLevel = gameLevel;
        this.blockCounter = blockCounter;
        this.blockRemover = new BlockRemover(gameLevel, blockCounter);
        this.scoreTracker = new ScoreTrackingListener(score);
    }

    /**
     * Adds a block to the game, while giving it properties - subscribing various hit listeners to the block.
     *
     * @param block the block
     */
    public void addBlock(Block block) {
        block.addToGame(this.gameLevel);
        this.blockCounter.increase(1);

        //Basic properties
        block.addHitListener(this.blockRemover);
        block.addHitListener(this.scoreTracker);
    }
}
