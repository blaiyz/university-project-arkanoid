package game.gui.animated_sprites.phases;

public class FadeIn implements AnimationPhase {
    private static final double FACTOR = -6;
    private double start;
    private double end;
    private long animationTime;

    public FadeIn(double start, double end, long animationTime) {
        this.start = start;
        this.end = end;
        this.animationTime = animationTime;
    }

    @Override
    public double getPosition(long timePassed) {
        //Renaming
        double a = this.start;
        double b = this.end;
        double c = this.animationTime;


        //Using the exponent for the animation
        return (a - b) * Math.exp(FACTOR * (timePassed / c)) + b;
    }

    @Override
    public long getAnimationTime() {
        return this.animationTime;
    }
}
