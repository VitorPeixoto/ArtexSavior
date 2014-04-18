package artexsavior.entities;

import artexsavior.enums.SkillType;
import artexsavior.enums.EntityType;
import artexsavior.enums.Direction;

/**
 *
 * @author Peixoto
 */

public class Friend extends Entity {
    public static int MOVEMENT_SPEED = 70;
    
    public Friend() {
        super(EntityType.FRIEND, new SkillType[]{SkillType.DARK_SKILL});
        this.width = 32;
        this.Type = EntityType.FRIEND;
        this.movementSpeed = MOVEMENT_SPEED;        
        this.facingTo = Direction.EAST;
        this.entityImage = Type.getEntityImage(Type);
    }

}
