package game.gui.animated_sprites.animation_types;

import game.gui.Sprite;

public class MoveXAnimation extends SpriteAnimation {
    public MoveXAnimation(Sprite sprite, int fps) {
        super(sprite, fps);
    }

    @Override
    protected Sprite applyAnimation(Sprite currentSprite, double position) {
        return currentSprite.moveX(position);
    }
}
