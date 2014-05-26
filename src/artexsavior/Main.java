package artexsavior;

import java.awt.Dimension;
import java.awt.Toolkit;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

/** Descrição do Código
 ****************************************************************************
 * @classname Main.java                        
 * @date      13/03/2014  
 * @authors   Peixoto
 * 
 * @description Classe principal que inicializa o jogo e define parte das          
 *              configurações inicias dos objetos e do Frame principal.
 *****************************************************************************/

public class Main extends StateBasedGame implements Constants {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new Main("Artex Savior"));
        Toolkit toolkit = Toolkit.getDefaultToolkit();        
	                
        Dimension dimension = toolkit.getScreenSize();
        
        //app.setDisplayMode(dimension.width, dimension.height, false);
        app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
        
        app.setVSync(true);	
        app.setShowFPS(true);
	app.setAlwaysRender(true);        
        app.start();
    }    

    public Main(String name) {
        super(name);
        
        this.addState(new Game(0x0));
        this.enterState(0x0);
        
    }

    @Override
    public void initStatesList(GameContainer gc) throws SlickException {}
    
}
