package game.gui.animated_sprites.phases;

public class Sine implements AnimationPhase {

    private final double amplitude;
    private final double start;
    private final long animationTime;

    public Sine(double amplitude, double start, long animationTime) {
        this.amplitude = amplitude;
        this.start = start;
        this.animationTime = animationTime;
    }

    @Override
    public double getPosition(long timePassed) {
        return amplitude * Math.sin(timePassed * 2 * Math.PI / this.animationTime) + start;
    }

    @Override
    public long getAnimationTime() {
        return this.animationTime;
    }
}
