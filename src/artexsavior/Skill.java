package artexsavior;

import artexsavior.enums.SkillType;
import artexsavior.enums.EntityType;

/**
 *
 * @author Peixoto
 */

public class Skill {    
    private int x, y;
    private SkillType Type;
    public int currentIndex = 0;
    
    public Skill(SkillType Type, int x, int y) {
        this.Type = Type;
        this.x = x;
        this.y = y;
    }              

    public Damage getDamage(int damageInteger, EntityType typeOfWhoPerformed) {
        return new Damage(damageInteger,
                          new Coordinate(x, y),
                          new Coordinate((x+Type.getSkillWidth()), (y+Type.getSkillHeight())), typeOfWhoPerformed);
    }
    
    public SkillType getType() {
        return Type;
    }

    public void setType(SkillType Type) {
        this.Type = Type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
}
