//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior.enums;

/** Descrição do Código
 *******************************************************************************
 * @enumname EntityMoveType.java                                               *  
 * @date     21/03/2014                                                        *   
 * @authors  Peixoto                                                           *              
 *                                                                             *   
 * @description Enum que define os tipos de movimento que as unidades podem    *   
 *              ter, usado no MovesController para decidir qual método usar.   * 
 *******************************************************************************/

public enum EntityMoveType {

    /**
     * The entity does not move.
     */
    STATIC(0),

    /**
     * The Entity follows the closer Entity
     */
    STALKER(1),

    /**
     * The Entity follows the closer oppose Entity
     */
    OPPOSE_STALKER(2),

    /**
     * The Entity follows the closer friend Entity
     */
    FRIEND_STALKER(3),

    /**
     * The Entity walks randomly, ignoring the others
     */
        PASSIVE(4),

    /**
     * Attacks all entities that are from an different type
     */
        AGGRESSIVE(5),

    /**
     * Attacks all entities
     */
        AGGRESSIVE_ALL(6),

    /**
     * Attacks the closer oppose entity
     */
        OPPOSE_AGGRESSIVE(7),

    /**
     * Flee from all units
     */
        FRIGHTENED(8),

    /**
     * Controlled by the player
     */
        CONTROLLED(9),

    /**
     * Static unit that keep looking at the player
     */
        EYE_STALKER(10);
    
    EntityMoveType(int moveType) {
        this.id = moveType;
    }
    
    private final int id;
    
    /** Getter of EntityMoveType
     * 
     * @param moveType the integer value for the EntityMoveType
     * @return the corresponding EntityMoveType
     */
    public static EntityMoveType getMoveType(int moveType) {
        switch(moveType) {
            case 0:
                return EntityMoveType.STATIC;
            case 1:
                return EntityMoveType.STALKER;    
            case 2:
                return EntityMoveType.OPPOSE_STALKER;
            case 3:
                return EntityMoveType.FRIEND_STALKER;    
            case 4:
                return EntityMoveType.PASSIVE;    
            case 5:
                return EntityMoveType.AGGRESSIVE;    
            case 6:
                return EntityMoveType.AGGRESSIVE_ALL;
            case 7:
                return EntityMoveType.OPPOSE_AGGRESSIVE;
            case 8:
                return EntityMoveType.FRIGHTENED;
            case 9:
                return EntityMoveType.CONTROLLED;
            case 10:
                return EntityMoveType.EYE_STALKER;
            default:
                return EntityMoveType.STATIC;    
        }        
    }
        
}
