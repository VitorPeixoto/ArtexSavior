package artexsavior.entities;

import artexsavior.enums.SkillType;
import artexsavior.enums.EntityType;
import artexsavior.enums.Direction;

/**
 *
 * @author Peixoto
 */

public class Enemy extends Entity {
    public static int MOVEMENT_SPEED = 100;
    
    public Enemy() {
        super(EntityType.ENEMY, new SkillType[] {SkillType.FIRE_SKILL});
        this.facingTo = Direction.EAST;
        this.Type = EntityType.ENEMY;
        this.movementSpeed = MOVEMENT_SPEED;
        this.entityImage = Type.getEntityImage(Type);                                
    }
    
}
