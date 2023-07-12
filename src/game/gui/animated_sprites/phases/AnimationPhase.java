package game.gui.animated_sprites.phases;

public interface AnimationPhase {
    double getPosition(long timePassed);
    long getAnimationTime();
}
