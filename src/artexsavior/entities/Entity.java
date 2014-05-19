//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
// TODO solve problems with walls {Entities can be at the same position at the same time,
//                                 Entities can move through walls}

// TODO solve problems with facing directions {Entities look to unespected Directions}

// TODO solve problems with perform skills {Entity can move while perform an skill,
//                                          Time to wait while performing an skill} 
//</editor-fold>

package artexsavior.entities;

import artexsavior.Bar;
import artexsavior.Controllers.DamageController;
import artexsavior.Controllers.MovesController;
import artexsavior.Controllers.SkillController;
import artexsavior.Controllers.WallController;
import artexsavior.Coordinate;
import artexsavior.Skill;
import artexsavior.Wall;
import artexsavior.enums.Direction;
import artexsavior.enums.EntityMoveType;
import artexsavior.enums.EntityType;
import artexsavior.enums.SkillType;
import java.awt.Color;
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

public abstract class Entity extends JComponent {
    protected int x = 20,      //The "X" position on the map
                  y = 20,      //The "Y" position on the map
                  height = 32, //The image height
                  width = 30;  //The image width
    
    protected EntityType Type; //The type of the entity
    
    //Controllers of the entity moves, the damage taken and the walls on game
    protected MovesController movesControl;
    protected DamageController damageControl;
    protected WallController wallControl;
    
    protected int movementSpeed = 100;

    //Direction that the entity is looking at
    protected Direction facingTo;

    //The sprite image of the Entity
    protected Image entityImage;

    //The current variable move index of the entity
    protected int moveIndex;

    //An ArrayList containing all Skills that this Entity can cast
    protected ArrayList<SkillType> skillTypes;

    //The entity's Life and Mana
    protected int hitPoints = 100;
    protected int manaPoints = 100;
    
    //Used when the entity dies
    protected int numOfBlinks = 0;

    //Defines when the entity is alive (hitPoints > 0)
    protected boolean dead,
    
    //Used when the entity dies
                      blink,
 
    //Active after the death sprite is over        
                      canRemove,
    //Defines when the entity can walk, by default true            
                      canMove = true,
    //Active when the game pauses
                      paused = false;

    //Thread that controls the movement and changes of the entity
    protected Thread movement;

    //The max movement index of the sprite
    protected int maxMoveIndex = 3;
    
    //The "X" offset on the map
    protected int offsetX,
    //The "Y" offset on the map
                  offsetY;
    
    
    private Bar healthBar, manaBar;

    /**
     * Construtor of Entity class
     * Construtor of an Entity object, creates an new variable Entity
     * @param type The type of the entity that will be created
     * @param Types An array of skills that the entity can perform
     */
    public Entity(EntityType type, SkillType[] Types) {
        movesControl = MovesController.newMovesController();
        damageControl = DamageController.newDamageController();
        wallControl = WallController.newWallController();
        
        this.Type = type;
        this.facingTo = Direction.EAST;
        wallControl.addWall(this.getWall());
        
        skillTypes = new ArrayList<>();
        
        healthBar = new Bar(hitPoints, hitPoints, Color.red);
        manaBar = new Bar(100, 100, Color.blue);
        
        setLayout(null);
        this.add(healthBar);
        healthBar.setBounds(0, 0, width, 5);
        this.add(manaBar);
        manaBar.setBounds(x, y, width, 5);
        
        if(Types != null) Collections.addAll(skillTypes, Types);               
        
        this.movement = new Thread(new Runnable() {
            
            @Override
            public void run() {
                try {
                    while (true) {
                        try {
                            synchronized (Entity.this) {
                                if(!dead) {
                                    walk(callWalk(Type, new Coordinate(x, y)));
                                    facingTo = movesControl.getDirection();
                                    if(canMove) if(++moveIndex >= maxMoveIndex) moveIndex = 0;                                        
                                    hitPoints -= damageControl.requestDamage(new Coordinate(x, y), Type);
                                    if(hitPoints <= 0) die();
                                    if(manaPoints < 100) manaPoints++;
                                    if(Type.equals(EntityType.HERO)) {
                                        healthBar.setBounds(x, y-10, width, 5);
                                        manaBar.setBounds(x, y-5, width, 5);
                                    }
                                    else {
                                        healthBar.setBounds(x+offsetX, y-10+offsetY, width, 5);
                                        manaBar.setBounds(x+offsetX, y-5+offsetY, width, 5);
                                    }
                                    healthBar.setValue(100-hitPoints);                                    
                                    manaBar.setValue(100-manaPoints);
                                }    
                            }
                        } catch (NullPointerException npe) {
                            walk(movesControl.walk(EntityType.NPC, new Coordinate(x, y)));
                        }
                        if(canMove && !dead) {
                                                            
                        }
                        else {
                            moveIndex = (maxMoveIndex-1);
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
     * Coordinate method callWalk
     * Method that perform some changes and so calls the MovesController 
     * method walk
     * 
     * @param type The type of the entity
     * @param Coord The actual Coordinate of the entity
     * @return an new Coordinate based on the response of movesControl
     */
    protected Coordinate callWalk(EntityType type, Coordinate Coord) {
        offsetX = (movesControl.getOffsetX()*-1);
        offsetY = (movesControl.getOffsetY()*-1);
        return (canMove ? movesControl.walk(Type, new Coordinate(x, y)) : new Coordinate(x, y));
    }
    
    /**
     * Void method walk
     * Method that changes the "X" and "Y" values of this entity based on 
     * an received Coordinate
     * @param walk The coordinate with the values
     */
    private void walk(Coordinate walk) {
        x = walk.getX();
        y = walk.getY();
    }
    
    @Override
    public void paintComponent(Graphics g) {        
        if(!dead)
            g.drawImage(this.entityImage, x+offsetX, y+offsetY, x+offsetX+width, y+offsetY+height, (this.moveIndex*width), ((this.facingTo.integer()*height)), ((this.moveIndex*width)+width), ((this.facingTo.integer()*height)+height), this);
        else if(numOfBlinks < 10){ //FIXME
            if(blink)
                g.drawImage(this.entityImage, x+offsetX, y+offsetY, x+offsetX+width, y+offsetY+height, (this.moveIndex*32), (0), ((this.moveIndex*32)+32), (height), this);
        }            
        else {
            canRemove = true;
        }
    }
    
    /**
     * Void method performSkill
     * Method that receive the necessary information and performs an Skill on 
     * the screen at the received "X-Y" position
     * @param skillToPerform The type of the skill that will be cast
     * @param x The "X" position to perform the skill
     * @param y The "Y" position to perform the skill
     */
    public void performSkill(SkillType skillToPerform, int x, int y) {
        if(skillTypes.contains(skillToPerform)) {            
            for(int i = 0; i < skillTypes.size(); i++) {
                if(skillTypes.get(i).equals(skillToPerform)) {
                    if(skillTypes.get(i).canCast(manaPoints)) {
                        //canMove = false;
                        manaPoints -= skillToPerform.getManaCost();
                        SkillController.newSkillController().paintSkill(new Skill(skillToPerform, x, y), Type);
                        //delayOfSkill = SkillController.newSkillController().getTimeToWait(skillToPerform);
                        skillTypes.get(i).cast();
                    }    
                }
            }
        }    
    }

    /**
     * Void method performSkill
     * Method that performs an Skill on the current entity position
     * @param skillToPerform The type of the skill that will be cast
     */
    public void performSkill(SkillType skillToPerform) {
        if(skillTypes.contains(skillToPerform)) {            
            for(int i = 0; i < skillTypes.size(); i++) {
                if(skillTypes.get(i).equals(skillToPerform)) {
                    if(skillTypes.get(i).canCast(manaPoints)) {
                        //canMove = false;
                        Coordinate skillCoord = facingTo.getCoordinatePlus(new Coordinate(x, y), 100);
                        performSkill(skillToPerform, skillCoord.getX()-(skillToPerform.getSkillWidth()/2)+offsetX, skillCoord.getY()-(skillToPerform.getSkillHeight()/2)+offsetY);
                        //delayOfSkill = SkillController.newSkillController().getTimeToWait(skillToPerform);
                        skillTypes.get(i).cast();
                    }    
                }
            }
        }    
    }        

    /** Void method die
     *  Method that perform necessary checks and changes before an entity 
     *  can die.
     */
    public void die() {
        this.entityImage = Type.getEntityDeathImage(Type);
        this.maxMoveIndex = 2;
        this.movementSpeed *= 3;
        this.removeAll();
        dead = true;
    }
    
    /** Void method setXY
     * Setter of x y
     * @param C an Coordinate with the "X-Y" values to change
     */
    public void setXY(Coordinate C) {
        x = C.getX();
        y = C.getY();
    }

    /**Getter of canMove
     * Defines when the entity can move
     * @return an boolean value corresponding to the move state of the entity
     */
    public boolean canMove() {
        return canMove;
    }

    /**Setter of canMove
     * Sets the bolean canMove
     * @param canMove an boolean value corresponding to the move state of the entity
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

    /** Setter of Type
     * Sets the entity type
     * @param Type the type to set
     */
    public void setType(EntityType Type) {
        this.Type = Type;
    }

    /** Getter of entity's Coordinate
     * Returns an Coordinate format of "X" and "Y" values affected by offsets
     * @return new Coordinate(X, Y)
     */
    public Coordinate getCoord() {
        return new Coordinate(x+offsetX, y+offsetY);
    }

    /** Setter of MoveType
     * Sets the entity move type
     * @param MoveType an EntityMoveType to be setted
     */
    public void setMoveType(EntityMoveType MoveType) {
        this.Type.setMoveType(MoveType);
    }

    /** Getter of canRemove
     * Returns an boolean value corresponding to the actual state of the 
     * Entity component
     * @return true if Entity is dead and has no more animations to do, false otherwise 
     */
    public boolean canRemove() {
        return canRemove;
    }
    
    /** Getter of entity Wall
     * Returns an Wall corresponding to the entity location
     * @return new Wall(x, y, x+width, y+height)
     */
    public Wall getWall() {
        return new Wall(x, y, x+width, y+height);
    }

}
