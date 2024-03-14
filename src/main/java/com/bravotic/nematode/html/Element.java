package com.bravotic.nematode.html;

import com.bravotic.nematode.html.properties.ElementProperties;
import com.bravotic.nematode.html.properties.Margin;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents an HTML element. This is used to render the page regardless if
 * HTML is being rendered or not.
 */
public class Element {

    private List<Element> children;
    private final String tagName;

    private ElementProperties prop;

    private Element parent;

    private boolean block;

    /**
     * Set the element parent.
     * @param p The parent.
     */
    protected void setParent(Element p) {
        this.parent = p;
    }

    /**
     * Reflows our content and updates child positions.
     */
    protected void flow() {
        // This represents our pen which will traverse the page drawing the
        // elements (relatively). It will start at 0, 0 and travel to the
        // edge of the page. We add our padding in here since that is where our
        // content will start.
        double penX = 0 + this.prop.getPadding().getLeft();
        double penY = 0 + this.prop.getPadding().getTop();

        Margin previousMargin = null;

        // Go through every element that is a child
        for (Element e : children) {
            double penXoffset = 0;
            double penYoffset = 0;

            // If our element is block, we know two things about it. It will
            // draw on its own line, and it will be drawn at the left most
            // position.
            if (e.block) {

                // Relativeize our margins before we draw
                // If our previousMargin is null, it means we haven't seen a
                // margin yet. So we use the top of our parent instead of the
                // bottom of the previous element.
                if (previousMargin == null) {

                    // If our current top margin is larger or the same as our
                    // first child's, we effectively cancel the first child's
                    // margins. So offset their drawing position by the child's
                    // margin.
                    if (this.prop.getMargin().getTop()
                            >= e.prop.getMargin().getTop()) {
                        penYoffset = e.prop.getMargin().getTop();
                    }

                    // Otherwise if our child's margin is larger than our top margin, we can still overlay part of it.
                    // Offset the y coord by the amount we can overlay.
                    else {
                        // TODO: 3/13/24 Perform this same relativize step but on the 'this's parent  
                        penYoffset = e.prop.getMargin().getTop()
                                - this.prop.getMargin().getTop();
                    }
                }

                // If we already encountered an element, we will use the bottom
                // of its margin to compare against the top of our margin.
                else {
                    // If the previous bottom margin is larger or the same, we
                    // effectively lose our top margin.
                    if (previousMargin.getBottom()
                            >= e.prop.getMargin().getTop()) {
                        penYoffset = e.prop.getMargin().getTop();
                    }

                    // Otherwise attempt to overlay.
                    else {
                        penYoffset = e.prop.getMargin().getTop()
                                - previousMargin.getBottom();
                    }
                }

                e.prop.setX(penX - penXoffset);
                e.prop.setY(penY - penYoffset);
                // TODO: 3/13/24 Also set width to the parent width

                // TODO: 3/13/24 In the future, we might want unset width block
                //  elements to take up the entire width of their parent
            }

            // If our element is inline, we just
            else {
                // TODO: 3/13/24 Inline is not yet implemented
            }
            
            previousMargin = e.prop.getMargin();
        }
    }

    /**
     * Creates a new Element.
     * @param tagName The name of the tag
     * @param rect The bounding rect where the tag is located (relative to the
     *             parent)
     */
    public Element(String tagName, ElementProperties rect) {
        this.tagName = tagName;
        this.prop = rect;
        this.parent = null;
        this.children = new ArrayList<>();
        this.block = true;
    }

    /**
     * Creates a new Element with the bounding rect initialized to all zeros.
     * @param tagName Specifies the tag which this element represents.
     */
    public Element(String tagName) {
        this(tagName, new ElementProperties());
    }

    /**
     * Return the tag name.
     * @return the parameter tagName.
     */
    public String getTagName() {
        return tagName;
    }

    /**
     * Add a child to the current element.
     * @param e The element to add as a child.
     */
    public void addChild(Element e) {
        e.setParent(this);
        children.add(e);

        this.flow();
    }

    /**
     * Get the screen space x-coordinate of the element.
     * @return The x coordinate relative to the top left corner of the screen.
     */
    public double getX() {
        // TODO: 3/13/24 Make this better for tail call recursion
        if (parent == null) {
            return this.getRelativeX();
        }
        else {
            return this.getRelativeX() + this.parent.getX();
        }
    }

    /**
     * Get the relative x-coordinate of the element. For the x-position on the
     * screen, please use getX().
     * @return The x coordinate relative to the top left corner of the parent.
     * @see Element#getX()
     */
    public double getRelativeX() {
        return this.prop.getX()
                + this.prop.getMargin().getLeft();
    }

    /**
     * Get the x-coordinate of the content. Basically the relativeX plus the
     * padding.
     * @return The content x-coordinate.
     * @see Element#getRelativeX()
     */
    public double getContentX() {
        return this.getX() + this.prop.getPadding().getLeft();
    }

    /**
     * Get the screen space y-coordinate of the element.
     * @return The y coordinate relative to the top left corner of the screen.
     */
    public double getY() {
        // TODO: 3/13/24 Make this better for tail call recursion
        if (parent == null) {
            return this.getRelativeY();
        }
        else {
            return this.getRelativeY() + this.parent.getY();
        }
    }

    /**
     * Get the relative y-coordinate of the element. For the y-position on the
     * screen, please use getY().
     * @return The y coordinate relative to the top left corner of the parent.
     * @see Element#getY()
     */
    public double getRelativeY() {
        return this.prop.getY()
                + this.prop.getMargin().getTop();
    }

    /**
     * Get the y-coordinate of the content. Basically the relativeY plus the
     * padding.
     * @return The content y-coordinate.
     * @see Element#getRelativeY()
     */
    public double getContentY() {
        return this.getRelativeY() + this.prop.getPadding().getTop();
    }

    /**
     * Get the width of the element. Note that this width only contains the
     * actual width of the content, plus the margins. If you want the width the
     * element will take up on screen, use getEffectiveWidth
     * @return Width of the content + padding
     * @see Element#getEffectiveWidth()
     */
    public double getWidth() {
        return this.prop.getWidth()
                + this.prop.getPadding().getLeft()
                + this.prop.getPadding().getRight();
    }

    /**
     * Get the width that an element will take up on screen. Factors in margin
     * as well as content + padding size.
     * @return The effective width of the element.
     * @see Element#getWidth()
     */
    public double getEffectiveWidth() {
        return this.getWidth()
                + this.prop.getMargin().getLeft()
                + this.prop.getMargin().getRight();
    }

    /**
     * Get the height of the element. Note that this height only contains the
     * actual height of the content, plus the margins. If you want the height
     * the element will take up on screen, use getEffectiveHeight
     * @return Height of the content + padding
     * @see Element#getEffectiveHeight()
     */
    public double getHeight() {
        return this.prop.getHeight()
                + this.prop.getPadding().getTop()
                + this.prop.getPadding().getBottom();
    }

    /**
     * Get the height that an element will take up on screen. Factors in margin
     * as well as content + padding size.
     * @return The effective height of the element.
     * @see Element#getHeight()
     */
    public double getEffectiveHeight() {
        return this.getHeight()
                + this.prop.getMargin().getTop()
                + this.prop.getMargin().getBottom();
    }

}
