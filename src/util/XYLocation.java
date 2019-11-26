package util;

/**
 * Note: If looking at a rectangle - the coordinate (x=0, y=0) will be the top
 * left hand corner. This corresponds with Java's AWT coordinate system.
 */

public class XYLocation {
    int xCoOrdinate, yCoOrdinate;


    /**
     * Constructs and initializes a location at the specified (<em>x</em>,
     * <em>y</em>) location in the coordinate space.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    public XYLocation(int x, int y) {
        xCoOrdinate = x;
        yCoOrdinate = y;
    }

    /**
     * Returns the X coordinate of the location in integer precision.
     *
     * @return the X coordinate of the location in double precision.
     */
    public int getXCoOrdinate() {
        return xCoOrdinate;
    }

    public int getYCoOrdinate() {
        return yCoOrdinate;
    }


    @Override
    public boolean equals(Object o) {
        if (null == o || !(o instanceof XYLocation)) {
            return super.equals(o);
        }

        XYLocation anotherLoc = (XYLocation) o;
        return ((anotherLoc.getXCoOrdinate() == this.xCoOrdinate) && (anotherLoc
                .getYCoOrdinate() == this.yCoOrdinate));
    }

    @Override
    public String toString() {
        return " ( " + xCoOrdinate + " , " + yCoOrdinate + " ) ";
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 37 * result + xCoOrdinate;
        result = 43 * result + yCoOrdinate;
        return result;
    }

}