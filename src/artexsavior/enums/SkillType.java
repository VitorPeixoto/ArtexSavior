package artexsavior.enums;

import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peixoto
 */

public enum SkillType {
    FIRE_SKILL(0, 10, 6, 150, 60, 60, 30, new int[] {1,1,1,1,1,1}),
    DARK_SKILL(1, 10, 25, 50, 60, 60, 30, new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}),
    ULTIMATE_DARK_SKILL(2, 50, 25, 50, 100, 100, 60, new int[] {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}),
    ULTIMATE_FIRE_SKILL(3, 50, 24, 100, 100, 100, 5, new int[] {1,2,3,4,5,6,7,8,9,10,10,10,10,10,10,10,9,8,7,6,5,4,3,2,1});
    
    private SkillType(int id, int manaCost, int numberOfSteps, int delayBySteps, int skillWidth, int skillHeight, int skillCooldown, int[] damageByHit) {
        this.id = id;
        this.manaCost = manaCost; 
        this.numberOfSteps = numberOfSteps;
        this.delayBySteps = delayBySteps;
        this.skillWidth = skillWidth;        
        this.skillHeight = skillHeight;        
        this.skillCooldown = skillCooldown;
        this.damageByHit = damageByHit;
    }
    
    private final int id,
                      manaCost,
                      numberOfSteps,
                      delayBySteps,
                      skillWidth,
                      skillHeight,
                      skillCooldown,
                      damageByHit[];
    
    private int actualCooldown;
    private final String name = this.toString();
    
    private Thread countdown;
    
    public Image getSkillImage() {
        return getSkillImage(this);
    }
    
    public Image getSkillImage(SkillType Skill) {
        switch(Skill) {
            case FIRE_SKILL:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/Enemy/Skills/FireSkill.png")).getImage();
            case DARK_SKILL:
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Entities/NPC/Skills/DarkSkill.png")).getImage();                
            case ULTIMATE_DARK_SKILL:    
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Skills/UltimateDarkSkill.png")).getImage();
            case ULTIMATE_FIRE_SKILL:    
                return new javax.swing.ImageIcon(getClass().getResource("/Images/Skills/UltimateFireSkill.png")).getImage();                
            default:
                return null;
        }        
    }

    public int getDamageByHit(int indexOfHit) {
        return damageByHit[indexOfHit];
    }        
    
    public int getNumberOfSteps() {
         return numberOfSteps;
    }

    public int getSkillWidth() {
        return skillWidth;
    }

    public int getSkillHeight() {
        return skillHeight;
    }

    public int getDelayBySteps() {
        return delayBySteps;
    }
    
    public boolean canCast(int actualManaPoints) {
        return (actualCooldown == 0) && (actualManaPoints >= manaCost);
    }

    public int getManaCost() {
        return manaCost;
    }    
    
    public void startCountdown() {
        countdown = new Thread(new Runnable() {        
            @Override
            public void run() {
                while(actualCooldown > 0) {
                    try {
                        actualCooldown--;
                        //System.out.println(name+": "+actualCooldown);
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SkillType.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }        
        }); countdown.start();
    }
    
    public void cast() {
        actualCooldown = skillCooldown;
        startCountdown();
    }
}
