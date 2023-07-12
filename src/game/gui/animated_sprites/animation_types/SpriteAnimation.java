package game.gui.animated_sprites.animation_types;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.gui.Sprite;
import game.gui.animated_sprites.phases.AnimationPhase;

public abstract class SpriteAnimation implements Sprite {
    //Number of milliseconds in a second
    private static final int MS_IN_SECOND = 1000;
    private final Sprite sprite;
    private boolean running;
    private boolean repeat;
    private long currentFrame;
    private long offset;
    private final int fps;
    private AnimationPhase phase;

    public SpriteAnimation(Sprite sprite, int fps) {
        this.sprite = sprite;
        this.running = false;
        this.repeat = false;
        this.fps = fps;
        this.offset = 0;
    }

    protected abstract Sprite applyAnimation(Sprite currentSprite, double position);

    protected Sprite getSprite() {
        return this.sprite;
    }

    protected long toFrames(long time) {
        return (long) this.fps * time / MS_IN_SECOND;
    }

    public void enableRepeat(AnimationPhase phase) {
        this.repeat = true;
        this.phase = phase;
    }

    public void disableRepeat() {
        this.repeat = false;
    }

    public void setOffset(long time) {
        this.offset = toFrames(time);
    }

    public void beginAnimation(AnimationPhase phase) {
        running = true;
        currentFrame = 0;
        this.phase = phase;
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void timePassed() {
        this.sprite.timePassed();


        if (repeat) {
            if (!running) {
                beginAnimation(this.phase);
            }
        }
        if (running) {
            currentFrame++;

            long numberOfAnimationFrames = (long) this.fps * this.phase.getAnimationTime() / MS_IN_SECOND;
            //Finished an animation loop
            if (currentFrame == numberOfAnimationFrames) {
                running = false;
            }
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        Sprite currentSprite = this.getCurrentSpriteFrame();
        currentSprite.drawOn(d);
    }

    @Override
    public Sprite getCurrentSpriteFrame() {
        long timePassed = (long) MS_IN_SECOND * (this.currentFrame + offset) / this.fps;
        double position = this.phase.getPosition(timePassed % (this.phase.getAnimationTime() + 1000 / this.fps + 1));
        Sprite subSpriteFrame = this.sprite.getCurrentSpriteFrame();
        return this.applyAnimation(subSpriteFrame, position);
    }


    @Override
    public Sprite moveX(double x) {
        return this.sprite.moveX(x);
    }

    @Override
    public Sprite moveY(double y) {
        return this.sprite.moveY(y);
    }

    @Override
    public Sprite scale(double factor) {
        return this.sprite.scale(factor);
    }

    @Override
    public Sprite scale(double factor, Point p) {
        return this.sprite.scale(factor, p);
    }

    @Override
    public Point getCenter() {
        return this.sprite.getCenter();
    }
}
