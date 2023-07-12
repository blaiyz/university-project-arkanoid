package game.levels.lunar;

import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.Circle;
import game.gui.MultiSprite;
import game.gui.Sprite;
import game.gui.rectangular_objects.Block;
import game.levels.LevelInformation;
import game.logic.physics.Velocity;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Lunar implements LevelInformation {
    //Block rows constants
    private static final int BLOCK_WIDTH = 60;
    private static final int BLOCK_HEIGHT = 30;

    //First block group
    private static final int NUMBER_OF_ROWS = 6;
    private static final int NUMBER_OF_BLOCKS_IN_BOTTOM_ROW = 6;
    private static final int TOP_ROW_Y = 100;
    private static final Color[] BLOCK_COLORS = {
            new Color(255, 255, 255),
            new Color(208, 211, 216),
            new Color(89, 104, 118),
            new Color(29, 60, 82),
            new Color(3, 11, 35),
            new Color(10, 10, 10),
    };
    //Second block group
    private static final int NUMBER_OF_ROWS2 = 4;
    private static final int NUMBER_OF_BLOCKS_IN_TOP_ROW = 4;
    private static final int TOP_ROW_Y2 = 300;
    private static final Color[] BLOCK_COLORS2 = {
            new Color(255, 255, 255),
            new Color(68, 79, 121),
            new Color(15, 29, 63),
            new Color(10, 10, 10),
    };
    private static final Color EDGE_COLOR = Color.WHITE;

    //Border constants
    private static final int BORDER_THICKNESS = 30;
    private static final int TOP_BORDER_THICKNESS = BORDER_THICKNESS + 10;
    private static final Color BORDER_COLOR = Color.GRAY;
    //Might be confusing, but it just means the border (edge) color of the border block.
    private static final Color BORDER_BORDER_COLOR = BORDER_COLOR;
    private static final int PADDLE_WIDTH = 200;
    private static final int UNADJUSTED_PADDLE_SPEED = 450;
    private static final Color PADDLE_COLOR = Color.WHITE;
    private static final int UNADJUSTED_BALL_SPEED = 300;

    private static final String NAME = "Lunar";
    private final int paddleSpeed;
    private final double ballSpeed;
    private final int width;
    private final int height;
    private final int leftBorder;
    private final int rightBorder;
    private final int topBorder;
    private final int fps;
    private MultiSprite background;
    private final List<Velocity> velocities;
    private final List<Block> blocks;
    private final List<Block> borders;
    private int numberOfBlocks;

    public Lunar(int width, int height, int fps) {
        this.width = width;
        this.height = height;
        this.leftBorder = BORDER_THICKNESS;
        this.rightBorder = width - BORDER_THICKNESS;
        this.topBorder = TOP_BORDER_THICKNESS;
        this.fps = fps;
        this.paddleSpeed = UNADJUSTED_PADDLE_SPEED / fps;
        this.ballSpeed = (double) UNADJUSTED_BALL_SPEED / fps;
        this.velocities = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.borders = new ArrayList<>();
        this.numberOfBlocks = 0;
        createBlocks();
        createBorders();
        createVelocities();
        createBackground();
    }

    private void createBlocks() {
        //Iterating over rows
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            //Number of blocks in current row
            int numberOfBlocks = i + 1;
            createBlockRow(this.leftBorder, TOP_ROW_Y + i * BLOCK_HEIGHT, numberOfBlocks, BLOCK_COLORS, true);
        }
        for (int i = 0; i < NUMBER_OF_ROWS2; i++) {
            int numberOfBlocks = NUMBER_OF_BLOCKS_IN_TOP_ROW - i;
            int x = this.rightBorder - numberOfBlocks * BLOCK_WIDTH;
            createBlockRow(x, TOP_ROW_Y2 + i * BLOCK_HEIGHT, numberOfBlocks, BLOCK_COLORS2, false);
        }
    }

    private void createBlockRow(int x, int y, int numberOfBlocks, Color[] colors, boolean order) {
        for (int i = 0; i < numberOfBlocks; i++) {
            Color color;
            if (order) {
                color = colors[numberOfBlocks - i - 1];
            } else {
                color = colors[i];
            }
            Rectangle rect = new Rectangle(new Point(x + BLOCK_WIDTH * i, y), BLOCK_WIDTH, BLOCK_HEIGHT);
            Block block = new Block(rect, color, color);
            this.blocks.add(block);
            this.numberOfBlocks++;
        }
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
        this.velocities.add(Velocity.fromAngleAndSpeed(360, this.ballSpeed));
        this.velocities.add(Velocity.fromAngleAndSpeed(370, this.ballSpeed));
        this.velocities.add(Velocity.fromAngleAndSpeed(390, this.ballSpeed));
        this.velocities.add(Velocity.fromAngleAndSpeed(420, this.ballSpeed));
    }

    private void createBackground() {
        List<Sprite> objects = new ArrayList<>();
        objects.add(new Block(new Rectangle(0, 0, this.width, this.height), new Color(0, 0, 0)));
        objects.add(new Circle(
                new Point((double) this.width / 2 - 100, 4 * this.height),
                3 * this.height + 100,
                new Color(20, 20, 20)));
        objects.add(new Circle(
                new Point((double) -4 * this.width, (double) 100),
                4 * this.width + 123,
                new Color(60, 60, 60)));

        objects.add(new Star(new Point(100, 100), 4, fps, true));
        objects.add(new Star(new Point(400, 200), 2, fps, true));
        objects.add(new Star(new Point(300, 550), 4, fps, true));
        objects.add(new Star(new Point(600, 450), 2, fps, true));
        objects.add(new Moon(new Point(600, 175), 100));
        objects.add(new Star(new Point(150, 475), 10, fps, true));
        objects.add(new Orbit(new Point(600, 175), 7, fps));

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
        return this.numberOfBlocks;
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
