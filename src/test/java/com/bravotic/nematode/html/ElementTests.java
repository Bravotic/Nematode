package com.bravotic.nematode.html;

import com.bravotic.nematode.html.*;
import com.bravotic.nematode.html.properties.*;
import org.junit.*;

public class ElementTests {
    /**
     * Tests element positions are properly reported, factoring in Margin.
     */
    @Test
    public void testElementPositionsAndSizesProperlyHandleMargin() {
        Element e = new Element("BODY",
                new ElementProperties(0, 0, 10, 10,
                        new Margin(5, 5, 5, 5), new Padding()));

        // For both our relative and actual X, our position should be 5,5
        // This is because margins are not considered part of the element, so
        // they are not discarded like Padding would be in relativeX. ContentX
        // should also not change.
        Assert.assertEquals(5, e.getX(), 0);
        Assert.assertEquals(5, e.getRelativeX(), 0);
        Assert.assertEquals(5, e.getContentX(), 0);

        // The same should hold true for the Y coordinate as well.
        Assert.assertEquals(5, e.getY(), 0);
        Assert.assertEquals(5, e.getRelativeY(), 0);
        Assert.assertEquals(5, e.getContentY(), 0);

        // Margin however should have absolutely no effect on the width or
        // height of the element.
        Assert.assertEquals(10, e.getWidth(), 0);
        Assert.assertEquals(10, e.getHeight(), 0);

        // Effective sizes should however reflect the margin.
        Assert.assertEquals(20, e.getEffectiveWidth(), 0);
        Assert.assertEquals(20, e.getEffectiveHeight(), 0);
    }

    @Test
    public void testElementPositionsAndSizesProperlyHandlePadding() {
        Element e = new Element("BODY",
                new ElementProperties(0, 0, 10, 10,
                        new Margin(), new Padding(5, 5, 5, 5)));

        // With padding, our x and y should not be updated, as padding is part
        // of the content view technically.
        Assert.assertEquals(0, e.getX(), 0);
        Assert.assertEquals(0, e.getRelativeX(), 0);
        Assert.assertEquals(0, e.getY(), 0);
        Assert.assertEquals(0, e.getRelativeY(), 0);

        // With padding however, our content x/y should update to reflect that
        // the content has been padded to start at (5, 5)
        Assert.assertEquals(5, e.getContentX(), 0);
        Assert.assertEquals(5, e.getContentY(), 0);

        // Our width and height should take our content width/height and add
        // our padding to it.
        Assert.assertEquals(20, e.getWidth(), 0);
        Assert.assertEquals(20, e.getHeight(), 0);
    }

    @Test
    public void testElementPositionsProperlyHandleInheritance() {
        Element body = new Element("BODY",
                new ElementProperties(0, 0, 10, 10,
                        new Margin(5, 5, 5, 5),
                        new Padding()));

        Element parent = new Element("PARENT",
                new ElementProperties(0, 0, 10, 5,
                        new Margin(5, 0, 5, 0),
                        new Padding(5, 5, 5, 5)));

        Element child = new Element("CHILD",
                new ElementProperties(0, 0, 5, 5,
                        new Margin(),
                        new Padding()));

        body.addChild(parent);
        parent.addChild(child);

        // Make sure body is giving us the position we expect
        Assert.assertEquals(5, body.getX(), 0);
        Assert.assertEquals(5, body.getY(), 0);

        // For our parent element, it should be located at (0, 0) relative to
        // our body which would place the content at (5, 5). Normally we would
        // add the margin, so for our element parent, it would be drawn at
        // (5, 10) since it has a 5 margin at the top. However, since we can
        // combine margins, we expect the x/y to instead be (5,5).
        Assert.assertEquals("Parent X is expected to be 5", 5, parent.getX(), 0);
        Assert.assertEquals("Parent Y is expected to be 5", 5, parent.getY(), 0);

        // Now for our child, it should bet at x/y 10, 10.
        Assert.assertEquals(10, child.getX(), 0);
        Assert.assertEquals(10, child.getY(), 0);
    }

}
