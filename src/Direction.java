/**
 * Direction.java
 *
 * @author      Brandon Williams
 * @version     2.0
 * @since       1/17/15
 *
 * Modified by Xinwei Wang at 1/27/19
 */


/** DO NOT MODIFY THIS FILE */


/**
 * enum Direction
 *
 * Enumeration Type used to represent a Movement Direction.
 * Each Direction object includes the vector of Direction.
 *
 */
public enum Direction {

    // The Definitions for UP, DOWN, LEFT, and RIGHT
    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), STAY(0, 0);

    // FIELD
    private final int x;
    private final int y;

    /*
     * Constructor
     *
     * @param int x - Displacement on X dimension
     * @param int y - Displacement on Y dimension
     */
    Direction(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /*
     * Getter of x
     *
     * Retrieve the X component of direction
     *
     * @return int X component of this direction
     */
    public int getX()
    {
        return x;
    }

    /*
     * Getter of x
     *
     * Retrieve the Y component of direction
     *
     * @return int Y component of this direction
     */
    public int getY()
    {
        return y;
    }

    /*
     * toString
     *
     * Format and return the name of the enum
     *
     * @return String the string representation of this direction
     */
    @Override
    public String toString()
    {
        // Close the x and y components with parenthesis.
        return name() + "(" + x + ", " + y + ")";
    }

}
