package com.bravotic.nematode.html.properties;

import com.bravotic.nematode.html.Element;

/**
 * Represents a bounding rect for laying an element out on a page. This should
 * only be used as a structure, and never directly called for information by
 * anything other than an Element.
 * @see Element#getX()
 * @see Element#getRelativeX()
 */
public class ElementProperties {
    private double x;
    private double y;
    private double width;
    private double height;

    private Margin margin;
    private Padding padding;

    public ElementProperties(double x, double y, double width, double height,
                             Margin margin, Padding padding) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.margin = margin;
        this.padding = padding;
    }

    public ElementProperties(double x, double y, double width, double height) {
        this(x, y, width, height, new Margin(), new Padding());
    }
    public ElementProperties() {
        this(0, 0, 0, 0, new Margin(), new Padding());
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Margin getMargin() {
        return margin;
    }

    public Padding getPadding() {
        return padding;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

}
