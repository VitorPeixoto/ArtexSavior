package artexsavior;

import artexsavior.enums.EntityType;

/**
 *
 * @author Peixoto
 */

public class Damage {
    private final int damage;
    private final Coordinate initialCoordinate, finalCoordinate;
    
    private final EntityType typeOfWhoPerformed;
    
    /**
     * Constructor of an Damage object that will be used to control the damage
     * taken and the damage dealt by entities on game
     * @param damage the integer value of the damage
     * @param initialCoordinate initial point where the damage is acting
     * @param finalCoordinate final point where the damage is acting
     * @param typeOfWhoPerformed type of entity that have performed the damage
     */
    public Damage(int damage, Coordinate initialCoordinate, Coordinate finalCoordinate, EntityType typeOfWhoPerformed) {
        this.damage = damage;
        this.initialCoordinate = initialCoordinate;
        this.finalCoordinate = finalCoordinate;
        this.typeOfWhoPerformed = typeOfWhoPerformed;
    }
    
    /**
     * Getter of damage
     * @return the integer value of the damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Getter of initialCoordinate
     * @return the initial point of the damage acting area
     */
    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }

    /**
     * Getter of finalCoordinate
     * @return the final point of the damage acting area
     */
    public Coordinate getFinalCoordinate() {
        return finalCoordinate;
    }
    
    /**
     * Function that receive the coordinate of an entity and his type, 
     * and checks if it can be and if it will be hurt by this damage
     * @param entityCoordinate the coordinate of the entity
     * @param thisEntityType the type of the entity
     * @return an boolean value corresponding to the question "will hurt?"
     */
    public boolean willHurt(Coordinate entityCoordinate, EntityType thisEntityType) {
        if(thisEntityType.indifferents(this.typeOfWhoPerformed, thisEntityType)) return false;
        else return (((entityCoordinate.getX() >= initialCoordinate.getX()) && (entityCoordinate.getX() <= finalCoordinate.getX())) &&
                     ((entityCoordinate.getY() >= initialCoordinate.getY()) && (entityCoordinate.getY() <= finalCoordinate.getY())));
    }
    
    
}
