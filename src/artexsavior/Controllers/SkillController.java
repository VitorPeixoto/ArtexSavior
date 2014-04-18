package artexsavior.Controllers;

import artexsavior.Constants;
import artexsavior.Controllers.DamageController;
import artexsavior.Damage;
import artexsavior.entities.Enemy;
import artexsavior.enums.EntityType;
import artexsavior.Skill;
import artexsavior.enums.SkillType;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author Peixoto
 */

public class SkillController extends JComponent implements Constants {
    private static volatile SkillController singleSkill = null;
    
    private int offsetX, offsetY;
    
    private final ArrayList<Skill> printQueue;
    private final ArrayList<Thread> threadQueue;
    private final DamageController damageControl;
    
    private SkillController() {
        printQueue = new ArrayList<>();
        threadQueue = new ArrayList<>();
        damageControl = DamageController.newDamageController();
    }
    
    public static SkillController newSkillController() {
        if(singleSkill == null) singleSkill = new SkillController();
        return singleSkill;
    }
    
    @Override
    public void paintComponent(Graphics g) {    
        for(int i = 0; i < printQueue.size(); i++) {
                g.drawImage(printQueue.get(i).getType().getSkillImage(), 
                           (printQueue.get(i).getX()-30+offsetX),
                           (printQueue.get(i).getY()-30+offsetY), 
                           ((printQueue.get(i).getX()+offsetX)+printQueue.get(i).getType().getSkillWidth()), 
                           ((printQueue.get(i).getY()+offsetY)+printQueue.get(i).getType().getSkillHeight()),
                           ((printQueue.get(i).currentIndex)*(printQueue.get(i).getType().getSkillWidth())),
                           0,
                           ((printQueue.get(i).currentIndex)*(printQueue.get(i).getType().getSkillWidth())+
                                   (printQueue.get(i).getType().getSkillWidth())),
                           
                            printQueue.get(i).getType().getSkillHeight(),
                            this);            

            }
    }
    
    public int getTimeToWait(SkillType Type) {
        return (Type.getNumberOfSteps()*Type.getDelayBySteps());
    }
    
    public synchronized void changeByOffset(int OffsetX, int OffsetY) {
        this.offsetX = OffsetX*-1;
        this.offsetY = OffsetY*-1;
        damageControl.changeByOffset(OffsetX, OffsetY);
    }
    
    public void paintSkill(final Skill toPaint, final EntityType typeOfWhoPerformed) {
        Thread Skill = new Thread(new Runnable() {            
            Damage toAddAndRemove = null;
            
            @Override
            public void run() {
                try {                    
                    printQueue.add(toPaint);                         
                    
                    toAddAndRemove = toPaint.getDamage(toPaint.getType().getDamageByHit(toPaint.currentIndex), typeOfWhoPerformed);
                    damageControl.addDamage(toAddAndRemove);
                    
                    Thread.sleep(toPaint.getType().getDelayBySteps());
                    
                    damageControl.removeDamage(toAddAndRemove);
                    
                    while(++toPaint.currentIndex < toPaint.getType().getNumberOfSteps()) {                                                
                        toAddAndRemove = toPaint.getDamage(toPaint.getType().getDamageByHit(toPaint.currentIndex), typeOfWhoPerformed);
                        damageControl.addDamage(toAddAndRemove);
                        Thread.sleep(toPaint.getType().getDelayBySteps());
                        damageControl.removeDamage(toAddAndRemove);
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
                }
            }            
        });
        
        threadQueue.add(Skill); 
        this.initThreads();
    }
    
    public void initThreads() {
        for(int i = 0; i <  threadQueue.size(); i++) {
            threadQueue.get(i).start();
            threadQueue.remove(i);
        }    
    }
}
