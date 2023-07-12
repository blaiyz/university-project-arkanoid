package game.logic.physics.hit_event;

import game.animations.GameLevel;
import game.gui.Ball;
import game.gui.rectangular_objects.Block;
import game.logic.Counter;


/**
 * A hit listener that removes block from the game, and updates the remaining block count.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block adder.
     *
     * @param gameLevel          the game
     * @param removedBlocks the block counter
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * Removes the block from the game, and decreases the block count.
     *
     * @param beingHit the block being hit
     * @param hitter   the ball hitting the block
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.gameLevel);
        this.remainingBlocks.decrease(1);
    }
}
