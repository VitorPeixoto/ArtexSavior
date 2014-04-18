//<editor-fold defaultstate="collapsed" desc="CODE issues and TODO operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior;

/** Descrição do Código
 *******************************************************************************
 * @classname Coordinate.java                                                  *
 * @date      18/04/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe que representa uma cordenada "X-Y" na tela, usada para  *   
 *              controlar o posicionamente de objetos e entidades.             *
 *******************************************************************************/

public class Coordinate {
    private int x;    // Valor "X" da cordenada  
    private int y;    // Valor "Y" da cordenada

    /**
     * Construtor of Coordinate class
     * Construtor that generates an object that controls and coordinate on 
     * screen
     * @param x the "X" value of the Coordinate
     * @param y the "Y" value of the Coordinate
     */
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
     * Setter of this Coordinate
     * Sets the coordinate from new x and y values
     * @param x the new "X" value of the coordinate
     * @param y the new "Y" value of the coordinate
     */
    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * toString() of Coordinate
     * Gets an String value of the coordinate
     * @return String on format "(X, Y)"  
     */
    @Override
    public String toString() {
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

