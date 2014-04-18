package artexsavior.entities;

import artexsavior.enums.EntityType;
import artexsavior.enums.Direction;

/**
 *
 * @author Peixoto
 */

public class NPC extends Entity {
            
    public NPC() {
        super(EntityType.NPC, null);
        this.facingTo = Direction.EAST;
        this.Type = EntityType.NPC;
        this.entityImage = Type.getEntityImage(Type);
        this.canMove = false;
    }
        
}
