package game.logic.physics.hit_event;

import game.animations.GameLevel;
import game.gui.Ball;
import game.gui.rectangular_objects.Block;
import game.logic.Counter;

/**
 * A hit listener class that removes ("kills") balls from the game each time the ball hits a selected block.
 */
public class BallRemover implements HitListener {
    private final GameLevel gameLevel;
    private final Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel           the game
     * @param remainingBalls the remaining balls
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        this.remainingBalls.decrease(1);
    }
}
