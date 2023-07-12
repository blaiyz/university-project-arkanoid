package game.gui.animated_sprites.phases;

public class PositiveOnlySine extends Sine {
    public PositiveOnlySine(double amplitude, double start, long animationTime) {
        super(amplitude, start, animationTime);
    }

    @Override
    public double getPosition(long timePassed) {
        return Math.max(super.getPosition(timePassed), 0);
    }
}
