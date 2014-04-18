package artexsavior;

import artexsavior.entities.Hero;
import artexsavior.entities.Enemy;
import artexsavior.entities.NPC;
import artexsavior.entities.Friend;
import artexsavior.enums.EntityType;
import artexsavior.Controllers.SkillController;
import artexsavior.Controllers.MapController;
import javax.swing.JFrame;

/** Descrição do Código
 ****************************************************************************
 * @classname Main.java                        
 * @date      13/03/2014  
 * @authors   Peixoto
 * 
 * @description Classe principal que inicializa o jogo e define parte das          
 *              configurações inicias dos objetos e do Frame principal.
 *****************************************************************************/

public class Main implements Constants {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame JF = new JFrame();   //Cria o Frame principal
        
        //Cria instâncias dos controladores/builders necessários                         
        SkillController skillControl = SkillController.newSkillController();
        EntityMap  EntityBuilder = EntityMap.newEntityMap();
        MapController mapControl = MapController.newMapController();
        
        //Cria as entidades desejadas à partir do builder EntytyBuilder
        Hero hero = (Hero) EntityBuilder.newEntity(EntityType.HERO);
        Friend Friend = (Friend) EntityBuilder.newEntity(EntityType.FRIEND);
        Enemy Enemy = (Enemy) EntityBuilder.newEntity(EntityType.ENEMY);
        NPC Npc = (NPC) EntityBuilder.newEntity(EntityType.NPC);
        BackgroundPanel background = mapControl.getBackgroundPanel();
        BackgroundPanel upperground = mapControl.getUppergroundPanel();
        
        //Define as coordenadas inicias de cada entidade
        Npc.setXY(new Coordinate(200, 200));
        Friend.setXY(new Coordinate(100, 100));        
        Enemy.setXY(new Coordinate(100, 200));
        
        //Seta configurações adicionais
        hero.setFocusable(true);   //Permite leitura do teclado
        hero.MOVEMENT_SPEED = 50;
        
        //Adiciona componentes no Frame principal
        JF.add(skillControl);
        JF.add(upperground);
        JF.add(hero);
        JF.add(Friend);
        JF.add(Enemy);
        JF.add(Npc);
        JF.add(background);
        
        //Seta o tamanho do Frame principal
        JF.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        //Seta o tamanho dos componentes dentro do Frame principal
        upperground.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        skillControl.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        hero.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        Friend.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        Enemy.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        Npc.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);                
        background.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        //Seta configurações adicionais
        JF.setDefaultCloseOperation(3);
        JF.setVisible(true);
        
        //Faz as entidades desejadas castarem algum spell definido
        //Enemy.performSkill(SkillType.FIRE_SKILL);
        //Friend.performSkill(SkillType.DARK_SKILL);                
    }    
    
}
