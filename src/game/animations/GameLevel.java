package game.animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.geometry.Point;
import game.geometry.shapes.Rectangle;
import game.gui.Ball;
import game.gui.BlockAdder;
import game.gui.Sprite;
import game.gui.SpriteCollection;
import game.gui.Text;
import game.gui.rectangular_objects.Block;
import game.gui.rectangular_objects.Paddle;
import game.gui.rectangular_objects.ScoreIndicator;
import game.levels.LevelInformation;
import game.logic.Counter;
import game.logic.physics.Collidable;
import game.logic.physics.GameEnvironment;
import game.logic.physics.Velocity;
import game.logic.physics.hit_event.BallRemover;

import java.awt.Color;
import java.util.List;

/**
 * Manages a game of Arkanoid.
 */
public class GameLevel implements Animation {
    //The smoothness of the animation


    //Paddle constants
    private static final double PADDLE_HEIGHT = 10;
    private static final int PADDLE_POS_HEIGHT = 560;


    //Ball constants
    private static final int BALL_SIZE = 5;
    private static final Color BALL_COLOR = Color.WHITE;
    private static final int BALL_STARTING_Y = PADDLE_POS_HEIGHT - 10;


    private static final Point SCORE_INDICATOR_STARTING_POS = new Point(0, 0);
    private static final int SCORE_INDICATOR_HEIGHT = 20;

    private static final int CLEAR_LEVEL_SCORE = 100;


    public static final int COUNTDOWN_SECONDS = 2;
    public static final int COUNT_FROM = 3;

    //Normal fields
    private final LevelInformation levelInfo;
    private final int width;
    private final int height;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Paddle paddle;
    private final Counter remainingBlocks;
    private final Counter remainingBalls;
    private final Counter score;
    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private boolean running;
    private boolean won;

    /**
     * Instantiates a new Game.
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboard, AnimationRunner runner, Counter score) {
        this.levelInfo = levelInfo;
        this.width = levelInfo.width();
        this.height = levelInfo.height();
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.remainingBlocks = new Counter();
        this.remainingBalls = new Counter();
        this.score = score;
        this.running = false;
        this.keyboard = keyboard;
        this.runner = runner;
    }

    /**
     * Adds a {@link Collidable} to the game.
     *
     * @param c the collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Removes a {@link Collidable} from the game.
     *
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }


    /**
     * Adds a {@link Sprite} to the game.
     *
     * @param s the sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Removes a {@link Sprite} from the game.
     *
     * @param s the sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initializes a new game.
     * Creates the blocks, balls, the paddle and adds them to the game.
     */
    public void initialize() {
        //Adding the background
        this.addSprite(levelInfo.getBackground());

        //Creating the game objects
        BlockAdder adder = new BlockAdder(this, this.remainingBlocks, this.remainingBalls, this.score);
        addBorders();
        addBlocks(adder);

        //Adding the paddle
        Rectangle paddleRect = new Rectangle(
                (double) (this.width - levelInfo.paddleWidth()) / 2,
                PADDLE_POS_HEIGHT,
                levelInfo.paddleWidth(),
                PADDLE_HEIGHT);
        this.paddle = new Paddle(
                paddleRect,
                levelInfo.paddleSpeed(),
                levelInfo.paddleColor(),
                this.keyboard,
                levelInfo.leftBorder(), levelInfo.rightBorder());
        this.paddle.addToGame(this);

        //Adding the score indicator
        Rectangle scoreIndicRect = new Rectangle(
                SCORE_INDICATOR_STARTING_POS,
                this.width,
                SCORE_INDICATOR_HEIGHT);
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreIndicRect, this.score);
        scoreIndicator.addToGame(this);

        Point levelNamePos = new Point(this.width * ((double) 2 / 3), 0);
        Text levelName = Text.fromRectangle(
                "Level Name: " + levelInfo.levelName(),
                new Rectangle(levelNamePos,
                        (double) width / 3,
                        SCORE_INDICATOR_HEIGHT).scale(ScoreIndicator.PADDING_RATIO));
        this.addSprite(levelName);
    }

    /**
     * Creates the border blocks and adds them to the game.
     */
    public void addBorders() {
        List<Block> borders = levelInfo.borders();


        //Adding borders to collections
        for (Block border : borders) {
            border.addToGame(this);
        }

        //The bottom border is the ball death region
        Block bottom = new Block(new Rectangle(0, this.height, width, 1),
                Color.GRAY, Color.GRAY);

        //Adding a ball remover to the death region, to remove the ball when it hits the region.
        BallRemover br = new BallRemover(this, this.remainingBalls);
        bottom.addToGame(this);
        bottom.addHitListener(br);
    }

    /**
     * Creates the game blocks within the borders and adds them to the game.
     *
     * @param adder block adder
     */
    public void addBlocks(BlockAdder adder) {
        List<Block> blocks = levelInfo.blocks();
        for (Block block : blocks) {
            adder.addBlock(block);
        }
    }

    /**
     * Adds balls to the game.
     */
    public void addBalls() {
        List<Velocity> velocities = levelInfo.initialBallVelocities();
        Ball ball;
        for (Velocity velocity : velocities) {
            Point start = new Point(this.paddle.getMiddleX(), BALL_STARTING_Y);
            ball = new Ball(start, BALL_SIZE, BALL_COLOR, velocity, this.environment);
            ball.addToGame(this);
        }
        this.remainingBalls.increase(levelInfo.numberOfBalls());
    }

    private void drawAll(DrawSurface d) {
        this.sprites.drawAllOn(d);
    }


    //Animation methods
    public boolean shouldStop() {
        return !this.running;
    }

    public boolean won() {
        return this.won;
    }

    public void doOneFrame(DrawSurface d) {
        // the logic from the previous run method goes here.
        // the `return` or `break` statements should be replaced with
        // this.running = false;
        if (this.remainingBlocks.getValue() == 0) {
            this.score.increase(CLEAR_LEVEL_SCORE);
            this.running = false;
            this.won = true;
        } else if (this.remainingBalls.getValue() == 0) {
            this.running = false;
            this.won = false;
        }
        this.drawAll(d);
        this.sprites.notifyAllTimePassed();
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(
                    this.keyboard,
                    KeyboardSensor.SPACE_KEY,
                    new PauseScreen(this.keyboard)));
        }
    }

    public void run() {
        this.addBalls();
        this.runner.run(new CountdownAnimation(
                COUNTDOWN_SECONDS,
                COUNT_FROM,
                this.sprites,
                levelInfo.fps(),
                levelInfo.width(),
                levelInfo.height()));
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }
}
