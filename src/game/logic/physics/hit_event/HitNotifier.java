package game.logic.physics.hit_event;

/**
 * An object that notifies all listeners that are registered to him when it has been hit, using the listener design
 * pattern.
 *
 * @see HitListener
 */
public interface HitNotifier {
    /**
     * Adds a listener to the list of listeners that will be notified whenever the object is being hit.
     *
     * @param hl the hit listener
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hit listener from the list.
     *
     * @param hl the hit listener
     */
    void removeHitListener(HitListener hl);
}
