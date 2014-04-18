//<editor-fold defaultstate="collapsed" desc="CODE issues and TODO operations">
// Write here operations that are missing
// TODO finish javadoc
//</editor-fold>

package artexsavior.entities;

import artexsavior.enums.SkillType;
import artexsavior.enums.EntityType;
import artexsavior.enums.Direction;
import artexsavior.enums.EntityMoveType;
import artexsavior.Controllers.SkillController;
import artexsavior.Controllers.MovesController;
import artexsavior.Controllers.DamageController;
import artexsavior.Controllers.WallController;
import artexsavior.Coordinate;
import artexsavior.Skill;
import artexsavior.Wall;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/** Descrição do Código
 *******************************************************************************
 * @classname Entity.java                                                      *
 * @date     18/04/2014                                                        *   
 * @authors  Peixoto                                                           *              
 *                                                                             *   
 * @description Classe java que define as variáveis básicas de uma entidade    *   
 *              no jogo, e será herdada pelas entidades específicas.           *
 *******************************************************************************/
public class Entity extends JComponent {
    protected int x = 20,
                  y = 20,
                  height = 32,
                  width = 30;
    
    protected EntityType Type;
    
    protected MovesController movesControl;
    protected DamageController damageControl;
    
    protected int movementSpeed = 200;

    protected int direction;

    protected Direction facingTo;

    protected Image entityImage;

    protected int index;

    /**
     *
     */
    protected ArrayList<Skill> skills;

    /**
     *
     */
    protected ArrayList<SkillType> skillTypes;

    /**
     *
     */
    protected int delayOfSkill;

    /**
     *
     */
    protected int hitPoints = 100;

    /**
     *
     */
    protected int numOfBlinks = 0;

    /**
     *
     */
    protected boolean dead,
 
    /**
     *
     */
    canPaint = true,

    /**
     *
     */
    blink,
 
    /**
     *
     */
    canRemove,

    /**
     *
     */
    canMove = true,
 
    /**
     *
     */
    paused = false,

    /**
     *
     */
    active = false;
    //</editor-fold>

    /**
     *
     */
    protected Thread movement;

    /**
     *
     */
    protected int maxIndex = 3;
    
    /**
     *
     */
    protected int offsetX,

    /**
     *
     */
    offsetY;

    /**
     *
     * @param type
     * @param Types
     */
    public Entity(EntityType type, SkillType[] Types) {
        movesControl = MovesController.newMovesController();
        damageControl = DamageController.newDamageController();
        
        this.Type = type;
        this.facingTo = Direction.EAST;
        
        skills = new ArrayList<>();
        skillTypes = new ArrayList<>();
        
        if(Types != null) Collections.addAll(skillTypes, Types);
        
        WallController.newWallController().addWall(this.getWall());
        
        this.movement = new Thread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            synchronized (Entity.this) {
                                if(!dead) {
                                    if(canMove) {
                                        walk(callWalk(Type, new Coordinate(x, y)));
                                        if(++index >= maxIndex) index = 0;
                                    }
                                    facingTo = movesControl.getDirection();
                                    hitPoints -= damageControl.requestDamage(new Coordinate(x, y), Type);
                                    if(hitPoints <= 0) die();
                                    System.out.println(""+Type+": "+hitPoints);
                                }    
                            }
                        } catch (NullPointerException npe) {
                            walk(movesControl.walk(EntityType.NPC, new Coordinate(x, y)));
                        }
                        if(canMove && !dead) {
                                                            
                        }
                        else {
                            index = (maxIndex-1);
                            blink = !blink; 
                            numOfBlinks++;
                        }
                        repaint();
                        Thread.sleep(movementSpeed);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Entity.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        movement.start();

    }

    /**
     *
     * @param type
     * @param Coord
     * @return
     */
    protected Coordinate callWalk(EntityType type, Coordinate Coord) {
        offsetX = (movesControl.getOffsetX()*-1);
        offsetY = (movesControl.getOffsetY()*-1);
        return movesControl.walk(Type, new Coordinate(x, y));
    }

    private void walk(Coordinate walk) {
        x = walk.getX();
        y = walk.getY();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if(!dead)
            g.drawImage(this.entityImage, x+offsetX, y+offsetY, x+offsetX+width, y+offsetY+height, (this.index*width), ((this.facingTo.integer()*height)), ((this.index*width)+width), ((this.facingTo.integer()*height)+height), this);
        else if(numOfBlinks < 10){ //FIXME
            if(blink)
                g.drawImage(this.entityImage, x+offsetX, y+offsetY, x+offsetX+width, y+offsetY+height, (this.index*32), (0), ((this.index*32)+32), (height), this);
        }            
        else {
            canRemove = true;
        }
    }
    
    /**
     *
     * @param skillToPerform
     * @param x
     * @param y
     */
    public void performSkill(SkillType skillToPerform, int x, int y) {
        if(skillTypes.contains(skillToPerform)) {            
            for(int i = 0; i < skillTypes.size(); i++) {
                if(skillTypes.get(i).equals(skillToPerform)) {
                    if(skillTypes.get(i).canCast()) {
                        //canMove = false;
                        SkillController.newSkillController().paintSkill(new Skill(skillToPerform, x, y), Type);
                        //delayOfSkill = SkillController.newSkillController().getTimeToWait(skillToPerform);
                        skillTypes.get(i).cast();
                    }    
                }
            }
        }    
    }

    /**
     *
     * @param skillToPerform
     */
    public void performSkill(SkillType skillToPerform) {
        if(skillTypes.contains(skillToPerform)) {            
            for(int i = 0; i < skillTypes.size(); i++) {
                if(skillTypes.get(i).equals(skillToPerform)) {
                    if(skillTypes.get(i).canCast()) {
                        //canMove = false;
                        Coordinate skillCoord = facingTo.getCoordinatePlus(new Coordinate(x, y), 100);
                        performSkill(skillToPerform, skillCoord.getX()-(width/2), skillCoord.getY()-(height/2));
                        //SkillController.newSkillController().paintSkill(new Skill(skillToPerform, x, y));
                        //delayOfSkill = SkillController.newSkillController().getTimeToWait(skillToPerform);
                        skillTypes.get(i).cast();
                    }    
                }
            }
        }    
    }        

    /**
     *
     */
    public void die() {
        this.entityImage = Type.getEntityDeathImage(Type);
        this.maxIndex = 2;
        this.movementSpeed *= 3;
        dead = true;
    }
    
    /**
     *
     * @param C
     */
    public void setXY(Coordinate C) {
        x = C.getX();
        y = C.getY();
    }

    /**
     *
     * @return
     */
    public boolean CanMove() {
        return canMove;
    }

    /**
     *
     * @param canMove
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    /**
     *
     * @return
     */
    public EntityType getType() {
        return Type;
    }

    /**
     *
     * @param Type
     */
    public void setType(EntityType Type) {
        this.Type = Type;
    }

    /**
     *
     * @return
     */
    public Coordinate getCoord() {
        return new Coordinate(x+offsetX, y+offsetY);
    }

    /**
     *
     * @param MoveType
     */
    public void setMoveType(EntityMoveType MoveType) {
        this.Type.setMoveType(MoveType);
    }

    /**
     *
     * @return
     */
    public boolean canRemove() {
        return canRemove;
    }
    
    /**
     *
     * @return
     */
    public Wall getWall() {
        return new Wall(x, y, x+width, y+height);
    }

}
