package game.levels.solar;

import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.rectangular_objects.Block;
import game.levels.LevelInformation;
import game.logic.physics.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Solar implements LevelInformation {
    private static final int NUMBER_OF_BLOCKS = 17;
    private static final int BLOCK_WIDTH = 35;
    private static final int BLOCK_HEIGHT = 70;
    private static final int LARGE_BLOCK_WIDTH = 100;
    private static final int LARGE_BLOCK_HEIGHT = LARGE_BLOCK_WIDTH;
    private static final Color[] BLOCK_COLORS = {
            new Color(179, 217, 255),
            new Color(86, 190, 255),
            new Color(64, 140, 255),
            new Color(24, 85, 255),
            new Color(255, 239, 16)
    };

    //Border constants
    private static final int BORDER_THICKNESS = 30;
    private static final int TOP_BORDER_THICKNESS = BORDER_THICKNESS + 10;
    private static final Color BORDER_COLOR = Color.GRAY;
    //Might be confusing, but it just means the border (edge) color of the border block.
    private static final Color BORDER_BORDER_COLOR = BORDER_COLOR;
    private static final int PADDLE_WIDTH = 350;
    private static final int UNADJUSTED_PADDLE_SPEED = 300;
    private static final int UNADJUSTED_BALL_SPEED = 300;
    private static final Color PADDLE_COLOR = new Color(247, 194, 0);

    private static final String NAME = "Solar";
    private final int paddleSpeed;
    private final double ballSpeed;
    private final int width;
    private final int height;
    private final int fps;
    private MultiSprite background;
    private final List<Velocity> velocities;
    private final List<Block> blocks;
    private final List<Block> borders;

    public Solar(int width, int height, int fps) {
        this.width = width;
        this.height = height;
        this.fps = fps;
        paddleSpeed = UNADJUSTED_PADDLE_SPEED / fps;
        ballSpeed = (double) UNADJUSTED_BALL_SPEED / fps;
        velocities = new ArrayList<>();
        blocks = new ArrayList<>();
        borders = new ArrayList<>();
        createBlocks();
        createBorders();
        createVelocities();
        createBackground();
    }

    private void createBlocks() {
        Rectangle blockRect = new Rectangle((double) (this.width - BLOCK_WIDTH) / 2, 100, BLOCK_WIDTH, BLOCK_HEIGHT);
        for (int i = 0; i < 4; i++) {
            Color currentColor = BLOCK_COLORS[i];
            Block current = new Block(blockRect, currentColor);
            this.blocks.add(current.moveX(-150));
            this.blocks.add(current.moveX(-150 - BLOCK_WIDTH));
            this.blocks.add(current.moveX(-300));
            this.blocks.add(current.moveX(-300 - BLOCK_WIDTH));
            this.blocks.add(current.moveX(150));
            this.blocks.add(current.moveX(150 + BLOCK_WIDTH));
            this.blocks.add(current.moveX(300));
            this.blocks.add(current.moveX(300 + BLOCK_WIDTH));

            blockRect = blockRect.moveY(BLOCK_HEIGHT);
        }

        blockRect = new Rectangle(((double) (this.width - LARGE_BLOCK_WIDTH) / 2),
                200,
                LARGE_BLOCK_WIDTH,
                LARGE_BLOCK_HEIGHT);
        this.blocks.add(new Block(blockRect, BLOCK_COLORS[BLOCK_COLORS.length - 1]));
    }

    private void createBorders() {
        //Creating borders
        Block top = new Block(new Rectangle(0, 0, this.width, TOP_BORDER_THICKNESS),
                BORDER_COLOR, BORDER_BORDER_COLOR);
        Block left = new Block(new Rectangle(0, 0, BORDER_THICKNESS, this.height),
                BORDER_COLOR, BORDER_BORDER_COLOR);
        Block right = new Block(new Rectangle(this.width - BORDER_THICKNESS, 0, BORDER_THICKNESS, this.height),
                BORDER_COLOR, BORDER_BORDER_COLOR);

        this.borders.add(top);
        this.borders.add(left);
        this.borders.add(right);
    }

    private void createVelocities() {
        this.velocities.add(Velocity.fromAngleAndSpeed(300, this.ballSpeed));
        this.velocities.add(Velocity.fromAngleAndSpeed(330, this.ballSpeed));
        this.velocities.add(Velocity.fromAngleAndSpeed(390, this.ballSpeed));
        this.velocities.add(Velocity.fromAngleAndSpeed(420, this.ballSpeed));
    }

    private void createBackground() {
        List<Sprite> objects = new ArrayList<>();
        objects.add(new Block(new Rectangle(0, 0, this.width, this.height), new Color(135, 206, 235)));
        objects.add(new Sun(new Point((double) this.width / 2, 250), 80, this.fps));
        objects.add(new Cloud(new Point(400, 450), 50, 3957, this.fps,
                new Color(238, 238, 238, 255)));
        objects.add(new Cloud(new Point(150, 500), 30, 0, this.fps, Color.WHITE));
        objects.add(new Cloud(new Point(100, 150), 30, 2000, this.fps, Color.WHITE));
        objects.add(new Cloud(new Point(650, 300), 50, 4500, this.fps, Color.WHITE));
        objects.add(new Cloud(new Point(700, 75), 10, 2549, this.fps, Color.WHITE));

        this.background = new MultiSprite(objects);
    }


    @Override
    public int numberOfBalls() {
        return this.velocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.velocities;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return PADDLE_WIDTH;
    }

    @Override
    public String levelName() {
        return NAME;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return NUMBER_OF_BLOCKS;
    }

    @Override
    public List<Block> borders() {
        return this.borders;
    }

    @Override
    public int width() {
        return this.width;
    }

    @Override
    public int height() {
        return this.height;
    }

    @Override
    public int rightBorder() {
        return this.width - BORDER_THICKNESS;
    }

    @Override
    public int leftBorder() {
        return BORDER_THICKNESS;
    }

    @Override
    public int fps() {
        return this.fps;
    }

    @Override
    public Color paddleColor() {
        return PADDLE_COLOR;
    }

}
