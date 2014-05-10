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
 * @classname Enemy.java                                                       *
 * @date     18/04/2014                                                        *   
 * @authors  Peixoto                                                           *              
 *                                                                             *   
 * @description Classe java que extende a classe Entity, e define a entidade   *   
 *              "Enemy", entidade inimiga do heroi "Hero".                     *
 *******************************************************************************/

public class Enemy extends Entity {

    /**
     * MOVEMENT_SPEED static int variable
     * Static variable that controls the movement speed of this entity
     */
    public static int MOVEMENT_SPEED = 100;
    
    /**
     * Construtor of Enemy class
     * Construtor of an Enemy object, builds an Enemy.
     */
    public Enemy() {
        super(EntityType.ENEMY, new SkillType[] {SkillType.FIRE_SKILL});
        this.facingTo = Direction.EAST;
        this.Type = EntityType.ENEMY;
        this.movementSpeed = MOVEMENT_SPEED;
        this.entityImage = Type.getEntityImage(Type);                                
    }
    
}
