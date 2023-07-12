package game.gui.animated_sprites.animation_types;

import game.gui.Sprite;

public class ScaleAnimation extends SpriteAnimation {
    public ScaleAnimation(Sprite sprite, int fps) {
        super(sprite, fps);
    }

    @Override
    protected Sprite applyAnimation(Sprite currentSprite, double position) {
        return currentSprite.scale(position);
    }
}
