package game.levels.martian;

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

public class Martian implements LevelInformation {
    private static final int BLOCK_WIDTH = 30;
    private static final int BLOCK_HEIGHT = BLOCK_WIDTH;
    private static final int BLOCK_Y = 150;
    private static final Color BLOCK_COLOR = Color.RED;

    //Border constants
    private static final int BORDER_THICKNESS = 30;
    private static final int TOP_BORDER_THICKNESS = BORDER_THICKNESS + 10;
    private static final Color BORDER_COLOR = Color.GRAY;
    //Might be confusing, but it just means the border (edge) color of the border block.
    private static final Color BORDER_BORDER_COLOR = BORDER_COLOR;
    private static final int PADDLE_WIDTH = 150;
    private static final int UNADJUSTED_PADDLE_SPEED = 600;
    private static final int UNADJUSTED_BALL_SPEED = 400;
    private static final Color PADDLE_COLOR = new Color(94, 207, 255);
    private static final String NAME = "Martian";
    public static final Color BACKGROUND_COLOR = new Color(213, 95, 0);
    private final int paddleSpeed;
    private final double ballSpeed;
    private final int width;
    private final int height;
    private final int fps;
    private MultiSprite background;
    private final List<Velocity> velocities;
    private final List<Block> blocks;
    private final List<Block> borders;

    public Martian(int width, int height, int fps) {
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
        this.blocks.add(new Block(
                new Rectangle(
                        (double) (this.width - BLOCK_WIDTH) / 2,
                        BLOCK_Y,
                        BLOCK_WIDTH,
                        BLOCK_HEIGHT
                ),
                BLOCK_COLOR
        ));
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
        this.velocities.add(Velocity.fromAngleAndSpeed(360, this.ballSpeed));
    }

    private void createBackground() {
        List<Sprite> objects = new ArrayList<>();
        objects.add(new Block(new Rectangle(0, 0, this.width, this.height), BACKGROUND_COLOR));
        Color darker = BACKGROUND_COLOR.darker();
        objects.add(new Circle(new Point(0, 0), 200, darker, darker));
        objects.add(new Circle(new Point(500, 400), 100, darker, darker));
        objects.add(new Circle(new Point(330, 500), 200, darker, darker));
        objects.add(new Circle(new Point(330, 500), 175, darker.darker(), darker.darker()));
        objects.add(new Circle(new Point(1000, 300), 500, darker, darker));
        objects.add(new Circle(new Point(1000, 300), 450, darker.darker(), darker.darker()));
        objects.add(new Circle(new Point(200, 270), 50, darker, darker));


        objects.add(new Block(new Rectangle(0, BLOCK_Y - 20, this.width, BLOCK_HEIGHT + 40),
                Color.DARK_GRAY));
        Point center = new Point((double) this.width / 2, BLOCK_Y + (double) (BLOCK_HEIGHT / 2));
        double radius = 100;
        objects.add(new Circle(center, radius, Color.GRAY, Color.GRAY));
        objects.add(new Circle(center, radius - 20, Color.LIGHT_GRAY, Color.LIGHT_GRAY));

        int numberOfLights = 10;
        double angle = 2 * Math.PI / numberOfLights;
        for (int i = 0; i < numberOfLights; i++) {
            Point p = center.moveX(radius * Math.cos(i * angle)).moveY(radius * Math.sin(i * angle));
            objects.add(new Circle(p, 10, Color.WHITE, Color.WHITE));
        }

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
        return this.blocks.size();
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