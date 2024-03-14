package com.bravotic.nematode.html.properties;

/**
 * Represents a field with multiple values for each direction. This is used for
 * both padding as well as margins.
 * @see Padding
 */
public class Direction {
    protected double top;
    protected double right;
    protected double bottom;
    protected double left;

    /**
     * Represents a field with multiple values for each direction.
     * @param top
     * @param right
     * @param bottom
     * @param left
     */
    public Direction(double top, double right, double bottom, double left) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }
    public Direction() {
        this(0, 0, 0, 0);
    }

    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }

    public double getTop() {
        return top;
    }

    public double getBottom() {
        return bottom;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setRight(double right) {
        this.right = right;
    }
}
