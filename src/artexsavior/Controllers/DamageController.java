package artexsavior.Controllers;

import artexsavior.Damage;
import artexsavior.enums.EntityType;
import java.util.ArrayList;
import artexsavior.Coordinate;
/**
 *
 * @author Peixoto
 */

public class DamageController {
    private static volatile DamageController singleDamage = null;    
    
    private final ArrayList<Damage> poolOfDamages;
    
    private int offsetX, offsetY;
    
    private DamageController() {
        poolOfDamages = new ArrayList<>();
    }
    
    /**
     *
     * @return
     */
    public static DamageController newDamageController() {
        if(singleDamage == null) {
            singleDamage = new DamageController();
        }
        return singleDamage;
    }
        
    /**
     *
     * @param entityCoordinate
     * @param thisEntityType
     * @return
     */
    public int requestDamage(Coordinate entityCoordinate, EntityType thisEntityType) {
        int damage = 0;
        entityCoordinate = new Coordinate(entityCoordinate.getX()+offsetX, entityCoordinate.getY()+offsetY);
        for(int i = 0; i < poolOfDamages.size(); i++) {            
            if(poolOfDamages.get(i).willHurt(entityCoordinate, thisEntityType))
                    damage += poolOfDamages.get(i).getDamage();
        }            
        return damage;
    }
    
    public synchronized void changeByOffset(int OffsetX, int OffsetY) {
        this.offsetX = OffsetX;
        this.offsetY = OffsetY;                
    }
    
    /**
     *
     * @param toAdd
     */
    public void addDamage(Damage toAdd) {
        poolOfDamages.add(toAdd);
    }
    
    /**
     *
     * @param toRemove
     */
    public void removeDamage(Damage toRemove) {
        poolOfDamages.remove(toRemove);
    }
    
    int getPoolSize() {
        return poolOfDamages.size();
    }
    
}
