//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
// TODO define more Skills and features
// TODO solve problem with diagonal movement {Sometimes the key store don't work well}
//</editor-fold>

package artexsavior.entities;

import artexsavior.Coordinate;
import artexsavior.enums.SkillType;
import artexsavior.enums.EntityType;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Descrição do Código
 *******************************************************************************
 * @classname Hero.java                                                        *
 * @date      21/04/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe java que extende a classe Entity, e define a entidade   *   
 *              "Hero", que será controlada pelo jogador.                      *
 *******************************************************************************/

public class Hero extends Entity {
    private final KeyList Listener;
    private KeyEvent Event1, Event2;
    /**
     *
     */
    public static int MOVEMENT_SPEED = 50;
    
    /** Construtor of Hero class.
     * Constructor of an Hero object, builds an Hero
     */
    public Hero() {        
        super(EntityType.HERO, new SkillType[]{SkillType.ULTIMATE_FIRE_SKILL});
        this.Type = EntityType.HERO;
        this.movementSpeed = MOVEMENT_SPEED;
        Listener = new KeyList();        
        this.addKeyListener(Listener);
        this.entityImage = this.Type.getEntityImage(Type);        
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        if(!dead)
            g.drawImage(this.entityImage, x, y, x+width, y+height, (this.moveIndex*width), ((this.facingTo.integer()*height)), ((this.moveIndex*width)+width), ((this.facingTo.integer()*height)+height), this);
        else if(numOfBlinks < 10){ //FIXME
            if(blink)
                g.drawImage(this.entityImage, x, y, x+width, y+height, (this.moveIndex*32), (0), ((this.moveIndex*32)+32), (height), this);
        }            
        else {
            canRemove = true;
        }
    }
    
    @Override
    public Coordinate getCoord() {
        return new Coordinate(x-offsetX, y-offsetY);
    }
    
    @Override
    protected Coordinate callWalk(EntityType type, Coordinate Coord) {                
        offsetX = (movesControl.getOffsetX()*-1);
        offsetY = (movesControl.getOffsetY()*-1);
        return movesControl.walk(Type, new Coordinate(x, y), Event1, Event2);    
    }
    
    private class KeyList implements KeyListener {
        
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if(Event1 == null) Event1 = e;
            else if(Event2 == null) Event2 = e;
            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                //Coordinate skillCoord = facingTo.getCoordinatePlus(new Coordinate(x, y), offsetX, offsetY, 100);
                Coordinate skillCoord = facingTo.getCoordinatePlus(getCoord(), 100);
                performSkill(SkillType.ULTIMATE_FIRE_SKILL, skillCoord.getX()-(width/2), skillCoord.getY()-(height/2));
            }    
        }

        @Override
        public void keyReleased(KeyEvent e) {
            try {
                if(e.getKeyCode() == Event1.getKeyCode()) Event1 = null;
                //Event1 = null;
                if(e.getKeyCode() == Event2.getKeyCode()) Event2 = null;
            } catch(NullPointerException NPE) {}    
        }        
    }
}
