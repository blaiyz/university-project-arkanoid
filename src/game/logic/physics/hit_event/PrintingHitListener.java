package game.logic.physics.hit_event;

import game.gui.Ball;
import game.gui.rectangular_objects.Block;

/**
 * Prints a message whenever a block is being hit.
 */
public class PrintingHitListener implements HitListener {
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("A Block was hit.");
    }
}
