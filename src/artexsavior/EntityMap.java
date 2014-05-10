//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior;

import artexsavior.entities.Entity;
import artexsavior.entities.Hero;
import artexsavior.entities.Enemy;
import artexsavior.entities.NPC;
import artexsavior.entities.Friend;
import artexsavior.enums.EntityType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/** Descrição do Código
 *******************************************************************************
 * @classname EntityMap.java                                                   *  
 * @date      13/03/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe tipo Singleton/Builder que controla a criação e         *   
 *              localização entre entidades, bem como sua relação de           *  
 *              posicionamento.                                                *  
 *                                                                             *  
 * @warning     TODA ENTIDADE DEVE SER CRIADA USANDO A INSTÂNCIA "singleMap",  *  
 *              OBTIDA ATRAVÉS DO MÉTODO ESTATICO "newEntityMap()"             *
 *******************************************************************************/
        
public class EntityMap {
    //Single instance that will be returned
    private static volatile EntityMap singleMap = null;                
    
    //Entity list/controller
    private final EntityList Entities;        
    
    //Thread that will clean the dead entities or entities that needs 
    //to be removed
    private final Thread cleanList = new Thread(new Runnable(){
        
        @Override
        public void run() {
            try {
                while(true) {
                    for(int i = 0; i < Entities.size(); i++) {
                        if(Entities.get(i).canRemove())
                            Entities.remove(i);
                    }
                    Thread.sleep(10);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(EntityMap.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    });
    

    //Static method that will return the single instance
    public static EntityMap newEntityMap() {
        if(singleMap == null) {
            singleMap = new EntityMap();
        }
        return singleMap;
    }

    //Private constructor that will be called once
    private EntityMap() {
        Entities = EntityList.newEntityList();     
        cleanList.start();
    }
    
    /** Coordinate method getCloserEntity
     * Method that receives an Coordinate and search in the EntityList the entity that
     * is closer to this position
     * @param Coord The coordinate to search
     * @return the coordinate of the closer entity
     */
    public Coordinate getCloserEntity(Coordinate Coord) {
        int index = -1;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            if(Entities.get(i).getCoord().getX() != (Coord.getX()) ||
               Entities.get(i).getCoord().getY() != (Coord.getY())) {
                modulox = this.getModuloX(Coord, Entities.get(i).getCoord());
                moduloy = this.getModuloY(Coord, Entities.get(i).getCoord());

                if((modulox+moduloy) >= modulo) {
                    modulo = (modulox+moduloy);
                    index = i;
                }
            }
        }
        if(index < 0) return Coord;
        return Entities.get(index).getCoord();
    }
  
    /** Coordinate method getCloserEntityOfType
     * Method that receive an Coordinate and an EntityType, and search in the 
     * EntityList the Entity of this EntityType that is closer to the received 
     * Coordinate
     * @param Type the EntityType to search
     * @param Coord the Coordinate to verify
     * @return the Coordinate of the closer Entity of EntityType
     */
    public Coordinate getCloserEntityOfType(EntityType Type, Coordinate Coord) {
        int index = 0;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            //Verifica se o tipo é igual
            if(Entities.get(i).getType().equals(Type)){ 
                modulox = this.getModuloX(Coord, Entities.get(i).getCoord());
                moduloy = this.getModuloY(Coord, Entities.get(i).getCoord());

                if((modulox+moduloy) >= modulo) {
                    modulo = (modulox+moduloy);
                    index = i;
                }
            }
        }

        return Entities.get(index).getCoord();
    }

    /** Coordinate method getCloserOpposeEntity
     * Method that receive an Coordinate and an EntityType, and search in the 
     * EntityList the Entity that opposes this EntityType and that is closer to
     * the received Coordinate
     * -> HERO opposes ENEMY
     * -> FRIEND opposes ENEMY...
     * @param Type the EntityType to get oppose EntityType
     * @param Coord the Coordinate to verify
     * @return the Coordinate of the closer Entity oppose of EntityType
     */    
    public Coordinate getCloserOpposeEntity(EntityType Type, Coordinate Coord) {
        int index = -1;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            // Verify if opposes
            if(Entities.get(i).getType().opposes(Type, Entities.get(i).getType())){
                modulox = this.getModuloX(Coord, Entities.get(i).getCoord());
                moduloy = this.getModuloY(Coord, Entities.get(i).getCoord());

                if((modulox+moduloy) >= modulo) {
                    modulo = (modulox+moduloy);
                    index = i;
                }
            }
        }
        
        if(index < 0)return null;
        return Entities.get(index).getCoord();
    }
     
    /** Coordinate method getCloserFriendEntity
     * Method that receive an Coordinate and an EntityType, and search in the 
     * EntityList the Entity that is a friend of this EntityType and that is 
     * closer to the received Coordinate
     * -> FRIEND is friend of HERO
     * -> HERO is friend of NPC...
     * @param Type the EntityType to get friendly EntityType
     * @param Coord the Coordinate to verify
     * @return the Coordinate of the closer Entity fried of EntityType
     */        
    public Coordinate getCloserFriendEntity(EntityType Type, Coordinate Coord) {
        int index = 0;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            // Verify if they are friends
            if(Entities.get(i).getType().friends(Type, Entities.get(i).getType())){
                modulox = this.getModuloX(Coord, Entities.get(i).getCoord());
                moduloy = this.getModuloY(Coord, Entities.get(i).getCoord());

                if((modulox+moduloy) >= modulo) {
                    modulo = (modulox+moduloy);
                    index = i;
                }
            }
        }

        return Entities.get(index).getCoord();
    }
    
    /** Coordinate method getCloserDifferentEntity
     * Method that receive an Coordinate and an EntityType, and search in the 
     * EntityList the Entity that is different from this EntityType and that is 
     * closer to the received Coordinate
     * @param Type the EntityType to get different EntityType
     * @param Coord the Coordinate to verify
     * @return the Coordinate of the closer different Entity of EntityType
     */            
    public Coordinate getCloserDifferentEntity(EntityType Type, Coordinate Coord) {
        int index = 0;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            // Verify if they are different
            if(Entities.get(i).getType().differents(Type, Entities.get(i).getType())){
                modulox = this.getModuloX(Coord, Entities.get(i).getCoord());
                moduloy = this.getModuloY(Coord, Entities.get(i).getCoord());

                if((modulox+moduloy) >= modulo) {
                    modulo = (modulox+moduloy);
                    index = i;
                }
            }
        }

        return Entities.get(index).getCoord();
    }

    /** Private Integer method getModuloX
     * Method that receive two different Coordinates and returns the "X" distance 
     * betwen them. That will help to choose what move the Entity will do
     * @param Entity the Entity Coordinate
     * @param closerEntity the closer Entity Coordinate
     * @return an integer value corresponding to the "X" distance betwen both Coordinates
     */
    private int getModuloX(Coordinate Entity, Coordinate closerEntity) {
        int modulo = Entity.getX() - closerEntity.getX();
        return (modulo >= 0 ? modulo : (modulo*-1));
    }

    /** Private Integer method getModuloY
     * Method that receive two different Coordinates and returns the "Y" distance 
     * betwen them. That will help to choose what move the Entity will do
     * @param Entity the Entity Coordinate
     * @param closerEntity the closer Entity Coordinate
     * @return an integer value corresponding to the "Y" distance betwen both Coordinates
     */    
    private int getModuloY(Coordinate Entity, Coordinate closerEntity) {
        int modulo = Entity.getY() - closerEntity.getY();
        return (modulo >= 0 ? modulo : (modulo*-1));
    }
 
    /** Entity method newEntity
     *  Method that receive an EntityType and create an Entity based on it.
     *  Returns an Entity, so after the call, an cast must be done to the right
     *  class. 
     * @warning THIS METHOD MUST BE USED TO CREATE ALL ENTITIES!
     * @param Type the EntityType of the Entity
     * @return an Entity of the EntityType
     */
    public Entity newEntity(EntityType Type) {
        switch(Type) {
            case HERO:
                Hero newHero = new Hero();
                Entities.add(newHero);
                return newHero;
            case ENEMY:
                Enemy newEnemy = new Enemy();
                Entities.add(newEnemy);
                return newEnemy;
            case FRIEND:
                Friend newFriend = new Friend();
                Entities.add(newFriend);
                return newFriend;
            case NPC:
                NPC newNPC = new NPC();
                Entities.add(newNPC);
                return newNPC;
            default:
                return null;
        }
    }
    
    /** Getter of Entities
     * 
     * @return the ArrayList of Entities
     */
    public ArrayList<Entity> getEntityList() {
        return Entities;
    }           

}
