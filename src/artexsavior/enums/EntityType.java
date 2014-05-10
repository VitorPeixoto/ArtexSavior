//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior.enums;

import java.awt.Image;

/** Descrição do Código
 *******************************************************************************
 * @enumname EntityType.java                                                   *
 * @date     21/03/2014                                                        *   
 * @authors  Peixoto                                                           *              
 *                                                                             *   
 * @description Enum que define os tipos de Entidade que existirão no jogo,    *   
 *              e possui também o EntityMoveType da Entidade.                  * 
 *******************************************************************************/

public enum EntityType {  

    /**
     * Enemy Entity, evil Entity
     */
    ENEMY(0, EntityMoveType.OPPOSE_STALKER),    

    /**
     * NPC Entity, indifferent Entity 
     */
    NPC(1, EntityMoveType.EYE_STALKER),    

    /**
     * Hero Entity, good Entity
     */
    HERO(2, EntityMoveType.CONTROLLED),    

    /**
     * Friend Entity, good Entity
     */
    FRIEND(3, EntityMoveType.FRIEND_STALKER);
        
    private EntityType(int type, EntityMoveType moveType) {
        this.id = type;        
        this.moveType = moveType;
    }
    
    private final int id;
    private EntityMoveType moveType;

    /** Getter of EntityType
     *
     * @param Type the integer value of the EntityType
     * @return an EntityType based on the integer value
     */
    public static EntityType getType(int Type) {
        switch(Type) {
            case 0:
                return EntityType.ENEMY;
            case 1:
                return EntityType.NPC;    
            case 2:
                return EntityType.HERO;
            case 3:
                return EntityType.FRIEND;    
            default:
                return EntityType.NPC;    
        }        
    }

    /** Getter of Image
     *
     * @param Type the EntityType value of the Entity
     * @return the Image sprite of the EntityType
     */
    public Image getEntityImage(EntityType Type) {
        switch(Type) {
            case HERO:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Hero/Hero.png")).getImage();
            case FRIEND:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Friend/Friend.png")).getImage();
            case ENEMY:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Enemy/Enemy.png")).getImage();
            case NPC:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/NPC/NPC.png")).getImage();
            default:
                return null;
        }        
    }
    
    /** Getter of moveType
     *
     * @return the EntityMoveType from this EntityType
     */
    public EntityMoveType getMoveType() {
        return moveType;
    }

    /** Setter of moveType
     *
     * @param moveType the new EntityMoveType to set
     */
    public void setMoveType(EntityMoveType moveType) {
        this.moveType = moveType;
    }

    /** Boolean method opposes.
     *  Method that receive two Entities and return if they are oppose
     *  Entities.
     * @param Entity Entity number 1
     * @param OpposeEntity Entity number 2
     * @return an boolean value corresponding to their relation
     */
    public boolean opposes(EntityType Entity, EntityType OpposeEntity) {
        if(this.equals(Entity)) return false;
        switch(Entity) {
            case HERO:
                return OpposeEntity.equals(EntityType.ENEMY);
            case ENEMY:                
                return OpposeEntity.equals(EntityType.HERO);
            case FRIEND:
                return OpposeEntity.equals(EntityType.ENEMY);
            case NPC:
                return OpposeEntity.equals(EntityType.ENEMY);
            default:
                return false;
        }
    }
    
    /** Boolean method friends.
     *  Method that receive two Entities and return if they are friend
     *  Entities.
     * @param Entity Entity number 1
     * @param FriendEntity Entity number 2
     * @return an boolean value corresponding to their relation
     */
    public boolean friends(EntityType Entity, EntityType FriendEntity) {
        if(this.equals(Entity)) return false;
        switch(Entity) {
            case HERO:
                return (FriendEntity.equals(EntityType.FRIEND) || FriendEntity.equals(EntityType.HERO));
            case ENEMY:
                boolean b = (FriendEntity.equals(EntityType.ENEMY));
                return b;
            case FRIEND:
                return (FriendEntity.equals(EntityType.FRIEND) || FriendEntity.equals(EntityType.HERO));
            default:
                return false;
        }
    }
    
    /** Boolean method indifferents.
     *  Method that receive two Entities and return if they are indifferent
     *  Entities.
     * @param Entity Entity number 1
     * @param IndifferentEntity Entity number 2
     * @return an boolean value corresponding to their relation
     */
    public boolean indifferents(EntityType Entity, EntityType IndifferentEntity) {
        if(this.equals(Entity)) return true;
        switch(Entity) {
            case HERO:
                return (IndifferentEntity.equals(EntityType.FRIEND) || IndifferentEntity.equals(EntityType.HERO) || IndifferentEntity.equals(EntityType.NPC));
            case ENEMY:
                boolean b = (IndifferentEntity.equals(EntityType.ENEMY));
                return b;
            case FRIEND:
                return (IndifferentEntity.equals(EntityType.FRIEND) || IndifferentEntity.equals(EntityType.HERO) || IndifferentEntity.equals(EntityType.NPC));
            default:
                return false;
        }
        
    }
        
    /** Boolean method differents.
     *  Method that receive two Entities and return if they are different
     *  Entities.
     * @param Entity Entity number 1
     * @param DifferentEntity Entity number 2
     * @return an boolean value corresponding to their relation
     */
    public boolean differents(EntityType Entity, EntityType DifferentEntity) {
        if(this.equals(Entity)) return false;
        switch(Entity) {
            case HERO:
                return !(DifferentEntity.equals(EntityType.HERO));
            case ENEMY:
                return !(DifferentEntity.equals(EntityType.ENEMY));
            case FRIEND:
                return !(DifferentEntity.equals(EntityType.FRIEND));
            default:
                return false;
        }
    }

    /** Getter of DeathImage
     *
     * @param Type the EntityType
     * @return the death sprite image of this Entity
     */
    public Image getEntityDeathImage(EntityType Type) {
        switch(Type) {
            case HERO:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Hero/Death.png")).getImage();
            case FRIEND:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Friend/Death.png")).getImage();
            case ENEMY:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Enemy/Death.png")).getImage();
            case NPC:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/NPC/Death.png")).getImage();
            default:
                return null;
        }        

    }
}
