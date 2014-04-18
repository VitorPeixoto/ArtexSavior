package artexsavior;

/**
 *
 * @author Peixoto
 */

public class Coordinate {
    private int x;
    private int y;
       
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Setter of x
     * @param x the new "X" value of the coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Setter of y
     * @param y the new "Y" value of the coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Setter of this Coordinate from new x and y values
     * @param x the new "X" value of the coordinate
     * @param y the new "Y" value of the coordinate
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter of an String value of the coordinate
     * @return String on format "(X, Y)"  
     */
    public String getCoord() {
        return (new StringBuilder()).append("").append(x).append(",").append(y).toString();
    }

    /**
     * Getter of x
     * @return the "X" value of this coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Getter of Y
     * @return the "Y" value of this coordinate
     */
    public int getY() {
        return y;
    }
}

