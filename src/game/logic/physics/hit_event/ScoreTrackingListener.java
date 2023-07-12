package game.logic.physics.hit_event;

import game.gui.Ball;
import game.gui.rectangular_objects.Block;
import game.logic.Counter;

/**
 * A hit listener which updates the score count each time a block is hit.
 */
public class ScoreTrackingListener implements HitListener {
    private static final int HIT_SCORE = 5;
    private Counter currentScore;

    /**
     * Instantiates a new Score tracking listener.
     *
     * @param scoreCounter the score counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /**
     * Increases the score by {@value #HIT_SCORE} each time the block is hit.
     *
     * @param beingHit the object being hit
     * @param hitter   the hitting object
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(HIT_SCORE);
    }
}