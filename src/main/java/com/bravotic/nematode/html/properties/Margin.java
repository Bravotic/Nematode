package com.bravotic.nematode.html.properties;

/**
 * Alias to Direction.
 * @see Direction
 */
public class Margin extends Direction {
    public Margin() {
        super();
    }

    public Margin (double top, double right, double bottom, double left) {
        super(top, right, bottom, left);
    }

    public void relativizeMargins(Margin other) {
        // This applies for all operations here. If the margin we are comparing to is
        if (other.left >= this.left) {
            this.left = 0;
        }
        else {
            this.left -= other.left;
        }

        if (other.right >= this.right) {
            this.right = 0;
        }
        else {
            this.right -= other.right;
        }

        if (other.top >= this.top) {
            this.top = 0;
        }
        else {
            this.top -= other.top;
        }

        if (other.bottom >= this.bottom) {
            this.bottom = 0;
        }
        else {
            this.bottom -= other.bottom;
        }
    }
}
