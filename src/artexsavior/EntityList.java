//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior;

import artexsavior.entities.Entity;
import artexsavior.enums.EntityType;
import java.util.ArrayList;

/** Descrição do Código
 *******************************************************************************
 * @classname EntityList.java                                                  *  
 * @date      13/03/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe tipo Singleton que estende ArrayList, armazenando       *   
 *              e organizando todas as entidades criadas.                      * 
 *                                                                             *  
 *******************************************************************************/

public class EntityList extends ArrayList<Entity> {
    //Single instance that will be returned
    private static volatile EntityList singleList = null;
    
    //Static method that will return the single instance
    public static EntityList newEntityList() {
        if(singleList == null) {
            singleList = new EntityList();
        }
        return singleList;
    }    

    //Private constructor that will be called once
    private EntityList() {}    
    
    /** Void method sortByTypes
     *  Method that sorts this list of entities by their types.
     */
    public void sortByTypes() {
        int i = 0;
        ArrayList<Entity> aux = new ArrayList<>();        
        while(i != 4) {
            for(Entity E : this) {
                if(E.getType().equals(EntityType.getType(i)))
                    aux.add(modCount, E);
            }       
        }
        this.removeAll(this);
        this.addAll(aux);
    }
}
