package artexsavior.enums;

import artexsavior.Coordinate;

/**
 *
 * @author Peixoto
 */

public enum Direction {
    NORTH(0),      // Para o norte
    NORTHWEST(1),  // Para o noroeste
    NORTHEAST(2),  // Para o nordeste
    WEST(3),       // Para o oeste
    SOUTHWEST(4),  // Para o sudoeste
    SOUTHEAST(5),  // Para o sudeste
    SOUTH(6),      // Para o sul
    EAST(7);       // Para o leste
    
    
    Direction(int direction) {
        this.id = direction;
    }
    
    private final int id;
    
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
      
    public Coordinate getCoordinatePlus(Coordinate actual, int toAdd) {
        switch(this) {
            case NORTH:
                return new Coordinate(actual.getX(), actual.getY()-toAdd);
            case NORTHWEST:
                return new Coordinate(actual.getX()-(toAdd/2), actual.getY()-(toAdd/2));
            case NORTHEAST:    
                return new Coordinate(actual.getX()+(toAdd/2), actual.getY()-(toAdd/2));
            case WEST:
                return new Coordinate(actual.getX()-toAdd, actual.getY());
            case SOUTHWEST:
                return new Coordinate(actual.getX()-(toAdd/2), actual.getY()+(toAdd/2));
            case SOUTHEAST:    
                return new Coordinate(actual.getX()+(toAdd/2), actual.getY()+(toAdd/2));
            case SOUTH:
                return new Coordinate(actual.getX(), actual.getY()+ toAdd);
            case EAST:
                return new Coordinate(actual.getX()+toAdd, actual.getY());
            default:
                return null;
        }
    }
    
    
    public Direction addDirection(Direction toAdd) {
        switch(this) {
            case NORTH:
                if(toAdd.equals(Direction.EAST)) return NORTHEAST;
                else if(toAdd.equals(Direction.WEST)) return NORTHWEST;
            case SOUTH:
                if(toAdd.equals(Direction.EAST)) return SOUTHEAST;
                else if(toAdd.equals(Direction.WEST)) return SOUTHWEST;                
            case EAST:
                if(toAdd.equals(Direction.NORTH)) return NORTHEAST;
                else if(toAdd.equals(Direction.SOUTH)) return SOUTHEAST;    
            case WEST:
                if(toAdd.equals(Direction.NORTH)) return NORTHWEST;
                else if(toAdd.equals(Direction.SOUTH)) return SOUTHWEST;    
        }
        return this;
    }
}
