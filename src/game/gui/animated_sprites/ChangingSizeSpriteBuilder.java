package game.gui.animated_sprites;

import game.gui.Sprite;
import game.gui.animated_sprites.animation_types.ScaleAnimation;
import game.gui.animated_sprites.animation_types.SpriteAnimation;
import game.gui.animated_sprites.phases.AnimationPhase;
import game.gui.animated_sprites.phases.Sine;

public class ChangingSizeSpriteBuilder {
    private final int fps;

    public ChangingSizeSpriteBuilder(int fps) {
        this.fps = fps;
    }

    public SpriteAnimation animateSprite(Sprite sprite, double scale, long duration, long offset) {
        AnimationPhase phase = new Sine(scale, 1, duration);
        ScaleAnimation animation = new ScaleAnimation(sprite, fps);
        animation.setOffset(offset);
        animation.enableRepeat(phase);
        return animation;
    }
}
