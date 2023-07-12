package game.logic.physics.hit_event;

import game.gui.Ball;
import game.gui.rectangular_objects.Block;

/**
 * An object which listens to a hit event from {@link HitNotifier} and responds according
 * to its implementation. Using the listener design pattern.
 *
 * @see HitNotifier
 */
public interface HitListener {
    // This method is called whenever the beingHit object is hit.
    // The hitter parameter is the Ball that's doing the hitting.

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the object being hit
     * @param hitter   the hitting object
     */
    void hitEvent(Block beingHit, Ball hitter);
}

