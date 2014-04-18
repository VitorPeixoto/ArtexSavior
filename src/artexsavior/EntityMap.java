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
    //Instância única que será retornada
    private static volatile EntityMap singleMap = null;

    // Lista/Controlador de entidades 
    private final EntityList Entities;
    
    //Thread que cuidará da limpeza de Entidades no caso de mortes ou outras 
    //necessidades de remoção
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
    

    //Método estatico que retornará a instância unica
    public static EntityMap newEntityMap() {
        if(singleMap == null) {
            singleMap = new EntityMap();
        }
        return singleMap;
    }

    //Construtor privado que só será chamado uma vez
    private EntityMap() {
        //Inicia "Entities" à partir de instância única
        //(EntityList também é Singleton)
        Entities = EntityList.newEntityList();     
        cleanList.start();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que recebe uma coordenada e retorna a coordenada            *
     *   da unidade mais próxima.                                           *
     ************************************************************************/
    
    //</editor-fold>    
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

    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que recebe uma coordenada e um tipo de entidade e           *
     *   retorna a coordenada da unidade desse tipo mais próxima.           *
     ************************************************************************/ 
    
    //</editor-fold>
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
    
    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************  
     *   Método que recebe uma coordenada e um tipo de entidade e           *
     *   retorna a coordenada da unidade oposta à esse tipo mais próxima    *
     *   EXEMPLO: Type   = EntityType.HERO                                  *
     *            Oposta = EntityType.ENEMY.                                 *
     ************************************************************************/
    
    //</editor-fold>
    public Coordinate getCloserOpposeEntity(EntityType Type, Coordinate Coord) {
        int index = -1;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            // Verifica se são opostas
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
    
    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que recebe uma coordenada e um tipo de entidade e           *
     *   retorna a coordenada da unidade amiga desse tipo mais próxima      *
     *   EXEMPLO: Type  = EntityType.HERO                                   *
     *            Amiga = EntityType.FRIEND.                                *
     ************************************************************************/         
    
    //</editor-fold>
    public Coordinate getCloserFriendEntity(EntityType Type, Coordinate Coord) {
        int index = 0;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            // Verifica se são amigas
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

    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /**************************************************************************
     *   Método que recebe uma coordenada e um tipo de entidade e             *
     *   retorna a coordenada da unidade diferente desse tipo mais próxima    *
     *   EXEMPLO: Type      = EntityType.HERO                                 * 
     *   Diferente = EntityType.ENEMY || EntityType.NPC || EntityType.FRIEND. * 
     **************************************************************************/
    
    //</editor-fold>
    public Coordinate getCloserDifferentEntity(EntityType Type, Coordinate Coord) {
        int index = 0;
        int modulox, moduloy, modulo = 0;

        for (int i = 0; i < Entities.size(); i++) {
            // Verifica se são diferentes
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

    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que recebe duas coordenadas diferentes e retorna o módulo   *
     *   da distância X entre elas, utilizado para escolher qual movimento  *
     *   a entidade irá fazer.                                              *
     ************************************************************************/
        
    //</editor-fold>
    private int getModuloX(Coordinate Entity, Coordinate closerEntity) {
        int modulo = Entity.getX() - closerEntity.getX();
        return (modulo >= 0 ? modulo : (modulo*-1));
    }

    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que recebe duas coordenadas diferentes e retorna o módulo   *
     *   da distância Y entre elas, utilizado para escolher qual movimento  *
     *   a entidade irá fazer.                                              *
     ************************************************************************/    
    
    //</editor-fold>    
    private int getModuloY(Coordinate Entity, Coordinate closerEntity) {
        int modulo = Entity.getY() - closerEntity.getY();
        return (modulo >= 0 ? modulo : (modulo*-1));
    }

    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que recebe um tipo de entidade e retorna uma entidade do    *
     *   tipo, que depois passará por um cast para a classe correta         *
     *   DEVE SER UTILIZADO NA CRIAÇÃO DE QUALQUER ENTIDADE.                *
     *   @param Type: Tipo da entidade                                      *
     *   @return Entidade mais próxima                                      *
     ************************************************************************/
        
    //</editor-fold>    
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

    public ArrayList<Entity> getEntityList() {
        return Entities;
    }
}
