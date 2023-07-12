package game.animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {
    private final KeyboardSensor sensor;
    private final String key;
    private final Animation animation;
    private boolean isAlreadyPressed;
    private boolean running;

    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        isAlreadyPressed = true;
        running = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);

        if (this.sensor.isPressed(this.key)) {
            if (!isAlreadyPressed) {
                running = false;
            }
        } else if (isAlreadyPressed) {
            isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }
}
