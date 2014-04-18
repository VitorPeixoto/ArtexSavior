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
    //Instância única que será retornada
    private static volatile EntityList singleList = null;
    
    //Método estatico que retornará a instância unica
    public static EntityList newEntityList() {
        if(singleList == null) {
            singleList = new EntityList();
        }
        return singleList;
    }    

    //Construtor privado que só será chamado uma vez
    private EntityList() {}    
    
    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que ordena a lista de entidades de modo a deixar juntas     *
     *   as entidades de cada tipo.                                         *
     ************************************************************************/
    
    //</editor-fold>    
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
