package game.geometry.shapes;

import game.geometry.Point;
import game.logic.DoubleMethods;

import java.util.ArrayList;

/**
 * A rectangle shape.
 */
public class Rectangle {
    //Line types translations from int
    private static final int UPPER = 0;
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int LOWER = 3;

    //Point types translations from int
    private static final int UPPER_LEFT = 0;
    private static final int UPPER_RIGHT = 1;
    private static final int LOWER_LEFT = 2;
    private static final int LOWER_RIGHT = 3;


    private final Point upperLeft;
    private final double width;
    private final double height;
    private final Point[] points;
    private final Line[] lines;


    /**
     * Instantiates a new Rectangle from the upper left point, width and height.
     *
     * @param upperLeft the upper left point
     * @param width     the width
     * @param height    the height
     */
// Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        double x = upperLeft.getX();
        double y = upperLeft.getY();

        //Fixing awkward values
        if (width < 0) {
            x += width;
            width *= -1;
        }
        if (height < 0) {
            y += height;
            height *= -1;
        }

        this.upperLeft = new Point(x, y);
        this.width = width;
        this.height = height;
        this.points = this.getPoints();
        this.lines = this.getLines();
    }

    /**
     * Instantiates a new Rectangle from upper left and lower right points.
     *
     * @param upperLeft  the upper left point
     * @param lowerRight the lower right point
     * @see #Rectangle(Point upperLeft, double width, double height)
     */
    public Rectangle(Point upperLeft, Point lowerRight) {
        this(upperLeft, lowerRight.getX() - upperLeft.getX(), lowerRight.getY() - upperLeft.getY());
    }

    /**
     * Instantiates a new Rectangle from the coordinates of upper left point, the width and height.
     *
     * @param x      the x coordinate
     * @param y      the y coordinate
     * @param width  the width
     * @param height the height
     * @see #Rectangle(Point upperLeft, double width, double height)
     */
    public Rectangle(double x, double y, double width, double height) {
        this(new Point(x, y), width, height);
    }


    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line the line
     * @return the list of points
     * @see Line#intersectionWith(Line)
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> list = new ArrayList<>();

        //Checking intersections with each rectangle line and adding them to the list
        for (Line rectLine : this.lines) {
            Point intersection = line.intersectionWith(rectLine);
            if (intersection != null) {
                list.add(intersection);
            }
        }

        //Removing duplicates
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) != null) {
                for (int j = i + 1; j < list.size(); j++) {
                    //Setting the duplicate to null
                    if (list.get(j) != null && (list.get(i).equals(list.get(j)))) {
                        list.set(j, null);
                    }
                }
            }
        }
        //Removing nulls (duplicates which have been set to null)
        while (list.remove(null)) {
            continue;
        }

        return list;
    }

    /**
     * Returns an array of the 4 points of the rectangle.
     *
     * @return the array of 4 points
     */
    private Point[] getPoints() {
        //Initializing the array
        Point[] points = new Point[4];

        //Computing the points
        points[UPPER_LEFT] = this.upperLeft;
        points[UPPER_RIGHT] = new Point(this.upperLeft.getX() + this.width, this.upperLeft.getY());
        points[LOWER_LEFT] = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        points[LOWER_RIGHT] = new Point(points[1].getX(), points[2].getY());

        return points;
    }

    /**
     * Returns an array of the 4 lines of the rectangle.
     *
     * @return the array of the lines
     */
    private Line[] getLines() {
        //Initializing the array
        Line[] lines = new Line[4];

        //Generating the lines
        lines[UPPER] = new Line(this.points[UPPER_LEFT], this.points[UPPER_RIGHT]);
        lines[LEFT] = new Line(this.points[UPPER_LEFT], this.points[LOWER_LEFT]);
        lines[RIGHT] = new Line(this.points[UPPER_RIGHT], this.points[LOWER_RIGHT]);
        lines[LOWER] = new Line(this.points[LOWER_LEFT], this.points[LOWER_RIGHT]);

        return lines;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the y coordinate of the top of the rectangle.
     *
     * @return the y coordinate
     */
    public double getTop() {
        return this.upperLeft.getY();
    }

    /**
     * Returns the x coordinate of the left of the rectangle.
     *
     * @return the x coordinate
     */
    public double getLeft() {
        return this.upperLeft.getX();
    }

    /**
     * Returns the x coordinate of the right of the rectangle.
     *
     * @return the x coordinate
     */
    public double getRight() {
        return this.getLeft() + this.width;
    }

    /**
     * Returns the y coordinate of the bottom of the rectangle.
     *
     * @return the y coordinate
     */
    public double getBottom() {
        return this.getTop() + this.height;
    }

    /**
     * Returns the upper left point of the rectangle.
     *
     * @return the point
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Returns the upper right point of the rectangle.
     *
     * @return the point
     */
    public Point getUpperRight() {
        return this.points[UPPER_RIGHT];
    }

    /**
     * Returns the lower left point of the rectangle.
     *
     * @return the point
     */
    public Point getLowerLeft() {
        return this.points[LOWER_LEFT];
    }

    /**
     * Returns the lower right point of the rectangle.
     *
     * @return the point
     */
    public Point getLowerRight() {
        return this.points[LOWER_RIGHT];
    }

    /**
     * Returns the upper line of the rectangle.
     *
     * @return the upper line
     */
    public Line getUpperLine() {
        return this.lines[UPPER];
    }

    /**
     * Returns the left line of the rectangle.
     *
     * @return the left line
     */
    public Line getLeftLine() {
        return this.lines[LEFT];
    }

    /**
     * Returns the right line of the rectangle.
     *
     * @return the right line
     */
    public Line getRightLine() {
        return this.lines[RIGHT];
    }

    /**
     * Returns the lower line of the rectangle.
     *
     * @return the lower line
     */
    public Line getLowerLine() {
        return this.lines[LOWER];
    }

    /**
     * Returns the point located in the center of the rectangle.
     *
     * @return the center point
     */
    public Point getCenter() {
        return new Point(this.getLeft() + this.width / 2, this.getTop() + this.height / 2);
    }

    /**
     * Returns a scaled rectangle of the current rectangle, using the given factor and the center point.
     *
     * @param factor the scale factor
     * @param p      the center point
     * @return the scaled rectangle
     */
    public Rectangle scale(double factor, Point p) {
        Point newPoint = new Line(this.upperLeft, p).getPointFromProportions(factor);
        return new Rectangle(newPoint, width * factor, height * factor);
    }

    /**
     * Returns a scaled rectangle of the current rectangle relative to the center, using the given factor.
     *
     * @param factor the scale factor
     * @return the scaled rectangle
     */
    public Rectangle scale(double factor) {
        return scale(factor, this.getCenter());
    }

    /**
     * Returns true if the given point is inside the rectangle. Does not include points directly on the sides.
     *
     * @param p the point
     * @return true if the point is inside
     */
    public boolean isPointInside(Point p) {
        double x = p.getX();
        double y = p.getY();
        boolean xAxis = DoubleMethods.greater(this.getRight(), x) && DoubleMethods.greater(x, this.getLeft());
        boolean yAxis = DoubleMethods.greater(this.getBottom(), y) && DoubleMethods.greater(y, this.getTop());
        return xAxis && yAxis;
    }

    /**
     * Returns a new rectangle with its x values moved in the specified dx value.
     *
     * @param x the change in x
     * @return the new rectangle
     */
    public Rectangle moveX(double x) {
        return new Rectangle(this.upperLeft.moveX(x), this.width, this.height);
    }

    public Rectangle moveY(double y) {
        return new Rectangle(this.upperLeft.moveY(y), this.width, this.height);

    }

    @Override
    public String toString() {
        return this.upperLeft + " width: " + this.width + " height: " + this.height;
    }

}