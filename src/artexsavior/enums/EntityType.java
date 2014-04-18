package artexsavior.enums;

import java.awt.Image;

/**
 *
 * @author Peixoto
 */

public enum EntityType {  
    ENEMY(0, EntityMoveType.OPPOSE_STALKER),    
    NPC(1, EntityMoveType.EYE_STALKER),    
    HERO(2, EntityMoveType.CONTROLLED),    
    FRIEND(3, EntityMoveType.FRIEND_STALKER);
        
    private EntityType(int type, EntityMoveType moveType) {
        this.id = type;        
        this.moveType = moveType;
    }
    
    private final int id;
    private EntityMoveType moveType;

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
    
    public EntityMoveType getMoveType() {
        return moveType;
    }

    public void setMoveType(EntityMoveType moveType) {
        this.moveType = moveType;
    }

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
    
    public boolean friends(EntityType Entity, EntityType OpposeEntity) {
        if(this.equals(Entity)) return false;
        switch(Entity) {
            case HERO:
                return (OpposeEntity.equals(EntityType.FRIEND) || OpposeEntity.equals(EntityType.HERO));
            case ENEMY:
                boolean b = (OpposeEntity.equals(EntityType.ENEMY));
                return b;
            case FRIEND:
                return (OpposeEntity.equals(EntityType.FRIEND) || OpposeEntity.equals(EntityType.HERO));
            default:
                return false;
        }
    }
    
    public boolean indifferents(EntityType Entity, EntityType OpposeEntity) {
        if(this.equals(Entity)) return true;
        switch(Entity) {
            case HERO:
                return (OpposeEntity.equals(EntityType.FRIEND) || OpposeEntity.equals(EntityType.HERO) || OpposeEntity.equals(EntityType.NPC));
            case ENEMY:
                boolean b = (OpposeEntity.equals(EntityType.ENEMY));
                return b;
            case FRIEND:
                return (OpposeEntity.equals(EntityType.FRIEND) || OpposeEntity.equals(EntityType.HERO) || OpposeEntity.equals(EntityType.NPC));
            default:
                return false;
        }
        
    }
        
    public boolean differents(EntityType Entity, EntityType OpposeEntity) {
        if(this.equals(Entity)) return false;
        switch(Entity) {
            case HERO:
                return !(OpposeEntity.equals(EntityType.HERO));
            case ENEMY:
                return !(OpposeEntity.equals(EntityType.ENEMY));
            case FRIEND:
                return !(OpposeEntity.equals(EntityType.FRIEND));
            default:
                return false;
        }
    }

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
