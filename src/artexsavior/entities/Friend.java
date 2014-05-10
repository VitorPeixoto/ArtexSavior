//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
// TODO define more Skills and features
//</editor-fold>

package artexsavior.entities;

import artexsavior.enums.SkillType;
import artexsavior.enums.EntityType;
import artexsavior.enums.Direction;

/** Descrição do Código
 *******************************************************************************
 * @classname Friend.java                                                      *
 * @date      21/04/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe java que extende a classe Entity, e define a entidade   *   
 *              "Friend, entidade amiga do heroi "Hero".                       *
 *******************************************************************************/

public class Friend extends Entity {

    /**
     * MOVEMENT_SPEED static int variable
     * Static variable that controls the movement speed of this entity
     */
    public static int MOVEMENT_SPEED = 70;
    
    /**
     * Constructor of Friend class.
     * Constructor of an Friend object, builds a Friend.
     */
    public Friend() {
        super(EntityType.FRIEND, new SkillType[]{SkillType.DARK_SKILL});
        this.width = 32;
        this.Type = EntityType.FRIEND;
        this.movementSpeed = MOVEMENT_SPEED;        
        this.facingTo = Direction.EAST;
        this.entityImage = Type.getEntityImage(Type);
    }

}
