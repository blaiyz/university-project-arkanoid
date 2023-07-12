package game.geometry.shapes;

import biuoop.DrawSurface;
import game.geometry.Point;
import game.logic.DoubleMethods;

import java.util.List;

/**
 * 2 dimensional line, defined by two {@link Point}s.
 *
 * @see Point
 */
public class Line {
    //The two defining points
    private final Point start;
    private final Point end;
    //The slope of the line (NaN if the line is vertical)
    private final double slope;
    //The y coordinate of the intersection between the line and the y-axis
    private double intercept;

    /**
     * Represents the orientation that a line and different point make.
     */
    public enum Orientation {
        CW,          //Clockwise
        CCW,         //Counter-clockwise
        CL           //Co-linear
    }

    /**
     * Instantiates a new {@link Line} from two {@link Point}s.
     *
     * @param start the start point
     * @param end   the end point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;

        //slope = NaN if the line is vertical
        this.slope = start.slope(end);
        if (!Double.isNaN(this.slope)) {
            //Using linear functions: y = m*x + b => b = y - m*x
            this.intercept = start.getY() - this.slope * start.getX();
        }
    }

    /**
     * Instantiates a new {@link Line} from coordinates of two points.
     *
     * @param x1 the x coordinate of the first point
     * @param y1 the y coordinate of the first point
     * @param x2 the x coordinate of the second point
     * @param y2 the y coordinate of the second point
     * @see #Line(Point start, Point end)
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    /**
     * Instantiates a new {@link Line} from a point, width and height.
     *
     * @param p      the point
     * @param width  the width
     * @param height the height
     */
    public Line(Point p, double width, double height) {
        this(p, new Point(p.getX() + width, p.getY() + height));
    }

    /**
     * Returns the length of the line.
     *
     * @return the length
     */
    public double length() {
        return this.start.distance(this.end);
    }

    /**
     * Return the middle point on the line.
     *
     * @return the middle point
     */
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2;
        double midY = (this.start.getY() + this.end.getY()) / 2;
        return new Point(midX, midY);
    }

    /**
     * Returns the start {@link Point}.
     *
     * @return the point
     */
    public Point start() {
        return this.start;
    }

    /**
     * Returns the end {@link Point}.
     *
     * @return the point
     */
    public Point end() {
        return this.end;
    }

    /**
     * Returns the slope of the line.
     * If the line is vertical the slope is NaN.
     *
     * @return the slope
     */
    public double slope() {
        return this.slope;
    }

    /**
     * Returns the relative orientation between the line and the {@link Point}.
     * For example: the line (1,1), (2,2) and the point (3,2) return CW (Clockwise) since the point "rotated"
     * clockwise relative to the line.
     * The line (1,1), (2,2) and the point (3,3) return CL (Co-linear) since they are on the same line.
     *
     * @param p the point
     * @return the orientation
     */
    public Orientation getOrientation(Point p) {
        //Case where the two points collide
        if (this.end.equals(p)) {
            return Orientation.CL;
        }

        //The slope between the end of this line and the point
        double pointSlope = this.end.slope(p);

        if (Double.isNaN(this.slope)) {
            //Cases where one of the lines is vertical
            if (Double.isNaN(pointSlope)) {
                return Orientation.CL;
            } else if (p.getX() > this.end.getX() == this.end.getY() > this.start.getY()) {
                return Orientation.CW;
            } else {
                return Orientation.CCW;
            }
        } else if (Double.isNaN(pointSlope)) {
            //Cases where the second line is vertical and the first one is not
            if (this.start.getX() > p.getX() == this.end.getY() < p.getY()) {
                return Orientation.CW;
            } else {
                return Orientation.CCW;
            }
        } else {
            //Cases where both of the lines are not vertical
            double thisLineDirection = this.end.getX() - this.start.getX();
            if (DoubleMethods.equals(this.slope, pointSlope)) {
                return Orientation.CL;
            } else if ((thisLineDirection > 0) == (this.slope > pointSlope == p.getX() > this.end.getX())) {
                return Orientation.CW;
            } else {
                return Orientation.CCW;
            }
        }
    }

    /**
     * Returns whether the point c is contained in the line segment.
     *
     * @param p the point
     * @return is the point contained in the line
     */
    public boolean contains(Point p) {
        /*
         * The point is contained only if the distance between it and the points on the line equals the length of the
         * line.
         */
        return DoubleMethods.equals(p.distance(this.start) + p.distance(this.end), this.length());
    }

    /**
     * Returns whether two line segments intersect.
     * Intersection is defined when there exists at least one point that is contained in both lines.
     *
     * @param other the other line
     * @return the result
     */
    public boolean isIntersecting(Line other) {
        //Getting all possible orientations
        Orientation thisStart = this.getOrientation(other.start);
        Orientation thisEnd = this.getOrientation(other.end);
        Orientation otherStart = other.getOrientation(this.start);
        Orientation otherEnd = other.getOrientation(this.end);

        /*
         * Using some geometry, the lines intersect if both orientation pairs for each line are different, i.e. for
         * each line the points on the opposite line are not on the same sides of the line.
         */
        if (thisStart != thisEnd && otherStart != otherEnd) {
            return true;
        } else if (thisStart == Orientation.CL && thisEnd == Orientation.CL) {
            //Another case where all the points are on the same line (Checking 2 orientations is enough to verify)

            return this.contains(other.start) || this.contains(other.end)
                    || other.contains(this.start) || other.contains(this.end);
        }
        return false;
    }

    /**
     * Returns the intersection {@link Point} of two lines.
     * Returns null if there is no intersection point or there are infinite intersection points.
     *
     * @param other the other line
     * @return the intersection point
     */
    public Point intersectionWith(Line other) {
        //Checking if the lines intersect
        if (!this.isIntersecting(other)) {
            return null;
        }

        //Cases where the start and end point collide (the line is a single point)
        if (this.start.equals(this.end)) {
            return new Point(this.start.getX(), this.start.getY());
        } else if (other.start.equals(other.end)) {
            return new Point(other.start.getX(), other.end.getY());
        }

        //Cases where all the points are co-linear
        if (this.getOrientation(other.start) == Orientation.CL && this.getOrientation(other.end) == Orientation.CL) {
            /*
             * The only way there is a single intersection point in this case is if one point in this line and one
             * point from the other line collide, and the other points are not contained in the opposite lines.
             * For example: (<*> represents a point)
             *  <*>--------------------<*>---------------------<*>
             * thisEnd        thisStart+otherStart         otherEnd
             *
             * The following if statements simply check every possible case stated above.
             */

            if (this.start.equals(other.start) && (!other.contains(this.end) && !this.contains(other.end))) {
                return new Point(this.start.getX(), this.start.getY());
            } else if (this.start.equals(other.end) && (!other.contains(this.end) && !this.contains(other.start))) {
                return new Point(this.start.getX(), this.start.getY());
            } else if (this.end.equals(other.end) && (!other.contains(this.start) && !this.contains(other.start))) {
                return new Point(this.end.getX(), this.end.getY());
            } else if (this.end.equals(other.start) && (!other.contains(this.start) && !this.contains(other.end))) {
                return new Point(this.end.getX(), this.end.getY());
            } else {
                return null;
            }
        }

        //Other cases (where the points are not co-linear)
        if (Double.isNaN(this.slope)) {
            //First line is vertical

            //Using the equation y = m*x+b we can calculate y:
            return new Point(this.start.getX(), other.slope * this.start.getX() + other.intercept);
        } else if (Double.isNaN(other.slope)) {
            //Second line is vertical
            return new Point(other.start.getX(), this.slope * other.start.getX() + this.intercept);
        } else {
            //Solving a system of 2 linear equations:
            double newX = (other.intercept - this.intercept) / (this.slope - other.slope);
            double newY = this.slope * newX + this.intercept;
            return new Point(newX, newY);
        }
    }

    /**
     * Returns if the line are the same, i.e. they are defined by the same two points (order does not matter)
     *
     * @param other the other line
     * @return the result
     */
    public boolean equals(Line other) {
        boolean normalDirection = this.start.equals(other.start) && this.end.equals(other.end);
        boolean oppositeDirection = this.start.equals(other.end) && this.end.equals(other.start);
        return normalDirection || oppositeDirection;
    }

    /**
     * Returns the closest intersection point between this line and the given {@link Rectangle}, in relation to the
     * start of the line.
     * Returns null if no intersection exists.
     *
     * @param rect the rectangle
     * @return the closest intersection point.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        //Edge case where the point is inside the rectangle
        if (rect.isPointInside(this.start)) {
            return null;
        }

        List<Point> intersections = rect.intersectionPoints(this);
        Point closestPoint = null;
        double minDistance = Double.MAX_VALUE;

        //Finding the closest point
        for (Point p : intersections) {
            double distance = this.start.distance(p);
            if (distance <= minDistance) {
                minDistance = distance;
                closestPoint = p;
            }
        }
        return closestPoint;
    }

    /**
     * Returns a point located on the line (not necessarily on the segment) using proportions between the length of the
     * line between the point and the start to the length of the line.
     *
     * @param proportions the proportions
     * @return the point
     */
    public Point getPointFromProportions(double proportions) {
        double x = this.start.getX() * proportions + this.end.getX() * (1 - proportions);
        double y = this.start.getY() * proportions + this.end.getY() * (1 - proportions);
        return new Point(x, y);
    }

    /**
     * Draws a line on a draw-surface.
     *
     * @param d the draw surface
     */
    public void drawOn(DrawSurface d) {
        int x1 = DoubleMethods.round(this.start.getX());
        int y1 = DoubleMethods.round(this.start.getY());
        int x2 = DoubleMethods.round(this.end.getX());
        int y2 = DoubleMethods.round(this.end.getY());

        d.drawLine(x1, y1, x2, y2);
    }

    @Override
    public String toString() {
        return this.start + "-->" + this.end;
    }
}