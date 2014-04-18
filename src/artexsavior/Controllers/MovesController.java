package artexsavior.Controllers;

import artexsavior.Coordinate;
import artexsavior.enums.Direction;
import artexsavior.EntityMap;
import artexsavior.enums.EntityType;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

/** Descrição do Código
 *******************************************************************************
 * @classname MovesController.java                                             *  
 * @date      13/03/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe tipo Singleton que controla todos os movimentos das     *   
 *              entidades, bem como sua direção, a area em que se move e       *  
 *              suas atitudes em relação às outras entidades                   *  
 *                                                                             *  
 * @warning     TODA ENTIDADE CRIADA DEVE TER SEUS MOVIMENTOS CONTROLADOS      *  
 *              PELA INSTÂNCIA "singleMoves", OBTIDA ATRAVÉS DO MÉTODO ESTÁTICO*
 *              "newMovesController".                                          *
 *******************************************************************************/

public class MovesController {
    //Instância única que será retornada
    private static volatile MovesController singleMoves = null;
    
    private int offsetX, offsetY;
    
    //Outros controladores/listas
        private final MapController MapControl;
        private final EntityMap EntitiesMap;
        //ArrayList de direções utilizada para movimentos randomizados
        private final ArrayList<Direction> randomMove;
    
    //<editor-fold defaultstate="collapsed" desc="Descrição da variável">
    
    /************************************************************************ 
     *   Variável que armazenará a direção para a qual a entidade se moveu, *   
     *   Será retornada à entidade para que ela modifique a imagem pintada  *
     *   de acordo com a direção.                                           *
     ************************************************************************/
    
    //</editor-fold>
    private Direction movedTo;
    
    //Variável usada para controlar quando a entidade estará
    //próxima o bastante para atacar
    private boolean isAttacking;
    
    //Método estatico que retornará a instância unica
    public static MovesController newMovesController() {
        if(singleMoves == null) {
            singleMoves = new MovesController();
        }        
        return singleMoves;
    }

    //Construtor privado que só será chamado uma vez
    private MovesController() {
        EntitiesMap = EntityMap.newEntityMap();
        MapControl = MapController.newMapController();
        
        //PARA randomizar uma entre as 8 direções na randomWalk()
        randomMove = new ArrayList<>();
        for(int i = 0; i < 8; i++) 
            randomMove.add(Direction.getDirection(i));        
    }    
    
    //<editor-fold defaultstate="collapsed" desc="Descrição do método">
    
    /************************************************************************ 
     *   Método que ordena a lista de entidades de modo a deixar juntas     *
     *   as entidades de cada tipo.                                         *
     ************************************************************************/
    
    //</editor-fold>
    public Coordinate walk(EntityType Type, Coordinate Coord) {
        switch(Type.getMoveType()) {
            case STATIC:
                break;
            case STALKER:
                Coord = follow(Coord, EntitiesMap.getCloserEntity(Coord));
                break;
            case OPPOSE_STALKER:
                Coord = follow(Coord, EntitiesMap.getCloserOpposeEntity(Type, Coord));
                break;
            case FRIEND_STALKER:
                Coord = follow(Coord, EntitiesMap.getCloserFriendEntity(Type, Coord));
                break;
            case PASSIVE:
                Coord = randomWalk(Coord);
                break;
            case AGGRESSIVE:
                Coord = attack(Coord, EntitiesMap.getCloserDifferentEntity(Type, Coord));
                break;
            case AGGRESSIVE_ALL:
                Coord = attack(Coord, EntitiesMap.getCloserEntity(Coord));
                break;
            case OPPOSE_AGGRESSIVE:
                Coord = attack(Coord, EntitiesMap.getCloserOpposeEntity(Type, Coord));
                break;
            case FRIGHTENED:
                Coord = flee(Coord, EntitiesMap.getCloserEntity(Coord));
                break;
            case CONTROLLED:
                break;
            case EYE_STALKER:
                movedTo = changeDirectionToUnit(Coord, EntitiesMap.getCloserEntity(Coord));
                break;
            default:
                Coord = randomWalk(Coord);
                break;
        }
        MapControl.nextArea(this.passed(Coord));
        return Coord;
    }
    
    public Coordinate walk(EntityType Type, Coordinate Coord, KeyEvent Event, KeyEvent Event2) {        
        //<editor-fold defaultstate="collapsed" desc="TODO Solve bug with diagonal movements">
        /*if(Type.getMoveType().equals(EntityMoveType.CONTROLLED)) {            
            if(Event != null && Event2!= null) {
                if(middleOfScreen(Coord, Event)) {return Coord;}
                else if(((Event.getKeyCode() == KeyEvent.VK_RIGHT) || (Event2.getKeyCode() == KeyEvent.VK_RIGHT)) &&
                   ((Event.getKeyCode() == KeyEvent.VK_UP) || (Event2.getKeyCode() == KeyEvent.VK_UP))) { 
                            movedTo = Direction.NORTHEAST;
                            Coord = new Coordinate(Coord.getX()+5, Coord.getY()-5);
                            MapControl.nextArea(this.passed(Coord));
                            return Coord;
                }        
                
                else if(((Event.getKeyCode() == KeyEvent.VK_RIGHT) || (Event2.getKeyCode() == KeyEvent.VK_RIGHT)) &&
                        ((Event.getKeyCode() == KeyEvent.VK_DOWN) || (Event2.getKeyCode() == KeyEvent.VK_DOWN))) {
                            movedTo = Direction.SOUTHEAST;
                            Coord = new Coordinate(Coord.getX()+5, Coord.getY()+5);
                            MapControl.nextArea(this.passed(Coord));
                            return Coord;    
                }       
                
                else if(((Event.getKeyCode() == KeyEvent.VK_LEFT) || (Event2.getKeyCode() == KeyEvent.VK_LEFT)) &&
                        ((Event.getKeyCode() == KeyEvent.VK_UP) || (Event2.getKeyCode() == KeyEvent.VK_UP))) {
                            movedTo = Direction.NORTHWEST;
                            Coord = new Coordinate(Coord.getX()-5, Coord.getY()-5);
                            MapControl.nextArea(this.passed(Coord));
                            return Coord;
                }        
                
                else if(((Event.getKeyCode() == KeyEvent.VK_LEFT) || (Event2.getKeyCode() == KeyEvent.VK_LEFT)) &&
                        ((Event.getKeyCode() == KeyEvent.VK_DOWN) || (Event2.getKeyCode() == KeyEvent.VK_DOWN))) {
                            movedTo = Direction.SOUTHWEST;
                            Coord = new Coordinate(Coord.getX()-5, Coord.getY()+5);
                            MapControl.nextArea(this.passed(Coord));
                            return Coord;
                }        
                
                else return Coord;
            }
            else {*/
            //</editor-fold>
                
                Coordinate CoordCopy = Coord;
                
                if(middleOfScreen(Coord, Event)) {
                    offsetX = MapControl.getOffsetX();
                    offsetY = MapControl.getOffsetY();
                    return (MapControl.canWalk(Coord) ? Coord : CoordCopy);
                }
                else if(Event.getKeyCode() == KeyEvent.VK_LEFT) {
                    movedTo = Direction.WEST;
                    Coord = new Coordinate(Coord.getX()-10, Coord.getY());
                    MapControl.nextArea(this.passed(Coord));
                    return (MapControl.canWalk(Coord) ? Coord : CoordCopy); 
                }
                else if(Event.getKeyCode() == KeyEvent.VK_RIGHT) {
                    movedTo = Direction.EAST;
                    Coord = new Coordinate(Coord.getX()+10, Coord.getY());
                    MapControl.nextArea(this.passed(Coord));
                    return (MapControl.canWalk(Coord) ? Coord : CoordCopy);
                }
                else if(Event.getKeyCode() == KeyEvent.VK_UP) {
                    movedTo = Direction.NORTH;
                    Coord = new Coordinate(Coord.getX(), Coord.getY()-10);
                    MapControl.nextArea(this.passed(Coord));
                    return (MapControl.canWalk(Coord) ? Coord : CoordCopy);
                }
                else if(Event.getKeyCode() == KeyEvent.VK_DOWN) {
                    movedTo = Direction.SOUTH;
                    Coord = new Coordinate(Coord.getX(), Coord.getY()+10);
                    MapControl.nextArea(this.passed(Coord));    
                    return (MapControl.canWalk(Coord) ? Coord : CoordCopy);
                }
                else return CoordCopy;
        //<editor-fold defaultstate="collapsed" desc="Solve bug with diagonal movements"> 
        //    }                     
        //}
        //else return Coord;
        //</editor-fold>        
    }
    
    private Coordinate follow(Coordinate Entity, Coordinate closerEntity) {
        int moduloX = getModuloX(Entity, closerEntity);
        int moduloY = getModuloY(Entity, closerEntity);
        Coordinate EntityCopy = Entity;
        
        if(moduloX > moduloY) {
            if(Entity.getX() > closerEntity.getX()) {
                movedTo = Direction.WEST;
                Entity.setX(Entity.getX()-10);                
                MapControl.nextArea(this.passed(Entity));
            }
            else if(Entity.getX() < closerEntity.getX()) {
                movedTo = Direction.EAST;
                Entity.setX(Entity.getX()+10);                
                MapControl.nextArea(this.passed(Entity));
            }            
        }
        else if(moduloX < moduloY) {
            if(Entity.getY() > closerEntity.getY()) {
                movedTo = Direction.NORTH;                
                Entity.setY(Entity.getY()-10);                
                MapControl.nextArea(this.passed(Entity));
            }
            else if(Entity.getY() < closerEntity.getY()) {
                movedTo = Direction.SOUTH;
                Entity.setY(Entity.getY()+10);                
                MapControl.nextArea(this.passed(Entity));
            }                        
        }
        else {
            if(Entity.getX() > closerEntity.getX()) {
                movedTo = Direction.WEST;
                Entity.setX(Entity.getX()-5);                
            }
            else {
                movedTo = Direction.EAST;
                Entity.setX(Entity.getX()+5);                
            }
            if(Entity.getY() < closerEntity.getY()) {
                movedTo = movedTo.addDirection(Direction.SOUTH);
                Entity.setY(Entity.getY()+5);                
                MapControl.nextArea(this.passed(Entity));
            }                        
            else {
                movedTo = movedTo.addDirection(Direction.NORTH);
                Entity.setY(Entity.getY()-5);                
                MapControl.nextArea(this.passed(Entity));
            }
        }
        return (MapControl.canWalk(Entity) ? Entity : EntityCopy);
    }
    
    private Coordinate randomWalk(Coordinate Entity) {        
        //RANDOMIZA a coleção
        Collections.shuffle(randomMove);
        
        Coordinate EntityCopy = Entity;
        
        movedTo = randomMove.get(0);
        switch(movedTo) {
            case NORTH:
                Entity = (new Coordinate(Entity.getX(), Entity.getY()-10));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);
            case NORTHWEST:
                Entity = (new Coordinate(Entity.getX()-5, Entity.getY()-5));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);
            case NORTHEAST:
                Entity = (new Coordinate(Entity.getX()+5, Entity.getY()-5));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);                
            case WEST:
                Entity = (new Coordinate(Entity.getX()-10, Entity.getY()));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);                
            case SOUTHWEST:
                Entity = (new Coordinate(Entity.getX()-5, Entity.getY()+5));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);                
            case SOUTHEAST:
                Entity = (new Coordinate(Entity.getX()+5, Entity.getY()+5));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);                
            case SOUTH:
                Entity = (new Coordinate(Entity.getX(), Entity.getY()+10));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);                
            case EAST:
                Entity = (new Coordinate(Entity.getX()+10, Entity.getY()));
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
                return (MapControl.canWalk(Entity) ? Entity : EntityCopy);                
            default:
                return null;
        }
    }
    
    private Coordinate attack(Coordinate Entity, Coordinate closerEntity) {
        if(closeEnough(Entity, closerEntity)) isAttacking = true;
        return follow(Entity, closerEntity);
    }
    
    private Coordinate flee(Coordinate Entity, Coordinate closerEntity) {
        int moduloX = getModuloX(Entity, closerEntity);
        int moduloY = getModuloY(Entity, closerEntity);
        
        Coordinate EntityCopy = Entity;
        
        if(moduloX < moduloY) {
            if(Entity.getX() > closerEntity.getX()) {                
                movedTo = Direction.EAST;
                Entity.setX(Entity.getX()+10);                
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
            }
            else if(Entity.getX() < closerEntity.getX()) {
                movedTo = Direction.WEST;
                Entity.setX(Entity.getX()-10);
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
            }            
        }
        else {
            if(Entity.getY() > closerEntity.getY()) {
                movedTo = Direction.SOUTH;
                Entity.setY(Entity.getY()+10);
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
            }
            else if(Entity.getY() < closerEntity.getY()) {
                movedTo = Direction.NORTH;
                Entity.setY(Entity.getY()-10);
                MapControl.nextArea(this.passed((MapControl.canWalk(Entity) ? Entity : EntityCopy)));
            }                        
        }
        return (MapControl.canWalk(Entity) ? Entity : EntityCopy);
    }

    private Direction passed(Coordinate Coord) {
        if(Coord.getX() < 0) return Direction.WEST;
        else if(Coord.getX() > 1000) return Direction.WEST;
        else if(Coord.getY() < 0) return Direction.NORTH;
        else if(Coord.getY() > 700) return Direction.SOUTH;
        else return Direction.NORTHEAST; // Só para não retornar null, não faz nada
    }
    
    private int getModuloX(Coordinate Entity, Coordinate closerEntity) {
        int modulo = Entity.getX() - closerEntity.getX();
        return (modulo >= 0 ? modulo : (modulo*-1));        
    }

    private int getModuloY(Coordinate Entity, Coordinate closerEntity) {
        int modulo = Entity.getY() - closerEntity.getY();
        return (modulo >= 0 ? modulo : (modulo*-1));
    }

    private boolean closeEnough(Coordinate Entity, Coordinate closerEntity) {        
        return (Entity.getX() >= (closerEntity.getX()-10)) && (Entity.getX() <= (closerEntity.getX()+10))
                && (Entity.getY() >= (closerEntity.getY()-10)) && (Entity.getY() <= (closerEntity.getY()+10));        
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }        

    public Direction getDirection() {
        return movedTo;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public void setAttacking(boolean isAttacking) {
        this.isAttacking = isAttacking;       
    }

    //TODO cleanAndFIXTHIS   
    private Direction changeDirectionToUnit(Coordinate Entity, Coordinate Unit) {
        if(Entity.getX() >= (Unit.getX() - 30) && Entity.getX() <= (Unit.getX() + 30))
            movedTo = ((Entity.getY() > Unit.getY()) ? Direction.NORTH : Direction.SOUTH);
        else if(Entity.getY() >= (Unit.getY() - 30) && Entity.getY() <= (Unit.getY() + 30))
            movedTo = ((Entity.getX() > Unit.getX()) ? Direction.WEST : Direction.EAST);
        else {
            if(Entity.getX() > Unit.getX()) {
                movedTo = Direction.WEST;
            }
            else {
                movedTo = Direction.EAST;
            }
            if(Entity.getY() < Unit.getY()) {
                movedTo = movedTo.addDirection(Direction.SOUTH);
            }                        
            else {
                movedTo = movedTo.addDirection(Direction.NORTH);
            }
        }
        return movedTo;        
    }

    private boolean middleOfScreen(Coordinate Coord, KeyEvent Event) {
        Coordinate toOffset = new Coordinate(0, 0);
        if(Coord.getX() >= 490 && Coord.getX() <= 510) {
            if(Event.getKeyCode() == KeyEvent.VK_RIGHT) toOffset = new Coordinate(10, 0);
            else if(Event.getKeyCode() == (KeyEvent.VK_LEFT)) toOffset = new Coordinate(-10, 0);
            return MapControl.addOffset(toOffset);
        }
        else if(Coord.getY() >= 290 && Coord.getY() <= 310) {
            if(Event.getKeyCode() == (KeyEvent.VK_UP)) toOffset = new Coordinate(0, -10);
            else if(Event.getKeyCode() == (KeyEvent.VK_DOWN)) toOffset = new Coordinate(0, 10);
            return MapControl.addOffset(toOffset);
        }
        else return false;
    }        
}
