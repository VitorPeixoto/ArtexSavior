//<editor-fold defaultstate="collapsed" desc="CODE issues and TODO operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior.Controllers;

import artexsavior.Damage;
import artexsavior.enums.EntityType;
import java.util.ArrayList;
import artexsavior.Coordinate;

/** Descrição do Código
 *******************************************************************************
 * @classname Damage.java                                                      *
 * @date      18/04/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe que representa um dano que está ocorrendo na area atual,*   
 *              definindo sua localização, seu valor e seus alvos.             *
 *******************************************************************************/

public class DamageController {
    //Instância única que será retornada
    private static volatile DamageController singleDamage = null;    
    
    //Lista de danos no decorrer da area
    private final ArrayList<Damage> poolOfDamages;
    
    private int offsetX, // Offsets aplicado aos danos de acordo com o offset
                offsetY; // do mapa.
    
    /**
     * Constructor of DamageController class
     * Private constructor of an singleton DamageController object that will be
     * used to control the damages on the area and verify the afected Entities.
     */
    private DamageController() {
        poolOfDamages = new ArrayList<>();
    }
    
    /**
     * Singleton static new Instance method 
     * Return the same single, not null instance of this classe every time that
     * is called
     * @return the single instance of DamageController class
     */
    public static DamageController newDamageController() {
        if(singleDamage == null) {
            singleDamage = new DamageController();
        }
        return singleDamage;
    }
        
    /**
     * Integer method requestDamage
     * Method that receive an coordinate and verify if damage is being dealt on
     * this coordinate, and if this damage affects entities of this type.
     * @param entityCoordinate the coordinate to verify
     * @param thisEntityType the type of the Entity to verify
     * @return the integer value of all the damages being dealt on this coordinate
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
    
    /**
     * Void method changeByOffset
     * Method that changes the offsets used on requestDamage()
     * @param OffsetX the new "X" value of the offset
     * @param OffsetY the new "Y" value of the offset
     */
    public synchronized void changeByOffset(int OffsetX, int OffsetY) {
        this.offsetX = OffsetX;
        this.offsetY = OffsetY;                
    }
    
    /**
     * Void method addDamage
     * Adds on the pool an new Damage
     * @param toAdd the new Damage object to be added
     */
    public void addDamage(Damage toAdd) {
        poolOfDamages.add(toAdd);
    }
    
    /**
     * Void method removeDamage
     * Method that removes an Damage object of the pool
     * @param toRemove the Damage object to be removed
     */
    public void removeDamage(Damage toRemove) {
        poolOfDamages.remove(toRemove);
    }
    
    /**
     * Integer method getPoolSize
     * Method that returns the size of the pool of damages
     * @return the poolOfDamages list size
     */
    public int getPoolSize() {
        return poolOfDamages.size();
    }
    
}
