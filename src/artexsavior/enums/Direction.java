//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior.enums;

import artexsavior.Coordinate;

/** Descrição do Código
 *******************************************************************************
 * @enumname Direction.java                                                    *
 * @date     18/04/2014                                                        *   
 * @authors  Peixoto                                                           *              
 *                                                                             *   
 * @description Enum que contem constantes relacionadas às direções cardeais e *   
 *              colaterais, usado para definir direções de movimentos e faces. *
 *******************************************************************************/

public enum Direction {
    /**
     * Direction North 
     * Norte
     */
    NORTH(0),
    
    /**
     * Direction Northwest
     * Noroeste
     */
    NORTHWEST(1),
    
    /**
     * Direction Northeast
     * Nordeste
     */
    NORTHEAST(2),

    /**
     * Direction West
     * Oeste
     */
    WEST(3),

    /**
     * Direction Southwest
     * Sudoeste
     */
    SOUTHWEST(4),

    /**
     * Direction Southeast
     * Sudeste
     */
     SOUTHEAST(5),

    /**
     * Direction South
     * Sul
     */
    SOUTH(6),

    /**
     * Direction East
     * Leste
     */
    EAST(7);
    
    /**
     * Constructor of Direction enum
     * Private constructor of an constant Direction enum item used to control
     * the cardinal and collateral directions.
     * @param direction the id of the direction
     */
    private Direction(int direction) {
        this.id = direction;
    }
    
    private final int id;
    
    /**
     * Getter of id
     * Method that return the id of the current Direction
     * @return id the id of the current Direction
     */
    public int integer() {
        switch(this) {
            case NORTH:
                return 0;
            case NORTHWEST:
                return 1;
            case NORTHEAST:    
                return 2;
            case WEST:
                return 3;
            case SOUTHWEST:
                return 4;
            case SOUTHEAST:    
                return 5;
            case SOUTH:
                return 6;
            case EAST:
                return 7;
            default:
                return -1;
        }
    }

    /**
     * Getter of Direction
     * Static method that return an Direction based on an received id
     * @param integerDirection the integer id of the Direction
     * @return the Direction with the integerDirection id
     */
    public static Direction getDirection(int integerDirection) {
        switch(integerDirection) {
            case 0:
                return NORTH;
            case 1:
                return NORTHWEST;
            case 2:    
                return NORTHEAST;
            case 3:
                return WEST;
            case 4:
                return SOUTHWEST;
            case 5:    
                return SOUTHEAST;
            case 6:
                return SOUTH;
            case 7:
                return EAST;
            default:
                return null;
        }
    }
      
    /**
     * Coordinate method getCoordinatePlus
     * Method that return an Coordinate based on actual direction and integer 
     * value to add
     * @param actualCoordinate the actual coordinate of the entity
     * @param toAdd the value to add on the coordinate
     * @return the new coordinate with the new "X" or "Y" value changed
     */
    public Coordinate getCoordinatePlus(Coordinate actualCoordinate, int toAdd) {
        switch(this) {
            case NORTH:
                return new Coordinate(actualCoordinate.getX(), actualCoordinate.getY()-toAdd);
            case NORTHWEST:
                return new Coordinate(actualCoordinate.getX()-(toAdd/2), actualCoordinate.getY()-(toAdd/2));
            case NORTHEAST:    
                return new Coordinate(actualCoordinate.getX()+(toAdd/2), actualCoordinate.getY()-(toAdd/2));
            case WEST:
                return new Coordinate(actualCoordinate.getX()-toAdd, actualCoordinate.getY());
            case SOUTHWEST:
                return new Coordinate(actualCoordinate.getX()-(toAdd/2), actualCoordinate.getY()+(toAdd/2));
            case SOUTHEAST:    
                return new Coordinate(actualCoordinate.getX()+(toAdd/2), actualCoordinate.getY()+(toAdd/2));
            case SOUTH:
                return new Coordinate(actualCoordinate.getX(), actualCoordinate.getY()+ toAdd);
            case EAST:
                return new Coordinate(actualCoordinate.getX()+toAdd, actualCoordinate.getY());
            default:
                return null;
        }
    }

    /**
     * Direction Method addDirection
     * Method that receive an Direction and try to add to this Direction.
     * If both are cardinal directions, return an collateral direction.
     * @param toAdd the Direction to add
     * @return a new Direction created by the sum of both Directions
     */
    public Direction addDirection(Direction toAdd) {
        switch(this) {
            case NORTH:
                if(toAdd.equals(Direction.EAST)) return NORTHEAST;
                else if(toAdd.equals(Direction.WEST)) return NORTHWEST;
                break;
            case SOUTH:
                if(toAdd.equals(Direction.EAST)) return SOUTHEAST;
                else if(toAdd.equals(Direction.WEST)) return SOUTHWEST;                
                break;
            case EAST:
                if(toAdd.equals(Direction.NORTH)) return NORTHEAST;
                else if(toAdd.equals(Direction.SOUTH)) return SOUTHEAST;    
                break;
            case WEST:
                if(toAdd.equals(Direction.NORTH)) return NORTHWEST;
                else if(toAdd.equals(Direction.SOUTH)) return SOUTHWEST;    
                break;
        }
        return this;
    }
}
