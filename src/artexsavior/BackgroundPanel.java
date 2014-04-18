package artexsavior;

import java.awt.Graphics;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author Peixoto
 */
public class BackgroundPanel extends JPanel {
    
    private Image backgroundImage;
    
    private final int width;
    private final int height;
    
    private int offsetX;    
    private int offsetY;
    private Thread atualize;
    
    /**
     * Constructor that generate an object that will control the painting of the 
     * background image
     * @param backgroundImage area image to paint on background
     * @param width width if the container
     * @param height height if the container
     */
    public BackgroundPanel(Image backgroundImage, int width, int height) {
        this.backgroundImage = backgroundImage;
        this.width = width;
        this.height = height;
        
        atualize = new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    try {
                        repaint();
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BackgroundPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }); atualize.start();
    }                 

   @Override
   public void paintComponent(Graphics g) {
//       System.out.println("OFFX: "+offsetX+"\nOFFY: "+offsetY+"\n\n");
       g.drawImage(backgroundImage, 0, 0, width, height, offsetX, offsetY, (width+offsetX), (height+offsetY), null);
   }    
    
    
    /**
     * Set the value of the "Y" offset of the image that will controll the map
     * piece that will be painted
     * 
     * @param offsetY new value of offsetY
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Set the value of the "X" offset of the image that will controll the map
     * piece that will be painted
     *
     * @param offsetX new value of offsetX
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }        

    /**
     * Getter of backgroundImage
     *
     * @return the background image that is being painted
     */
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Setter of backgroundImage
     *
     * @param backgroundImage new background image to be painted
     */
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    
}
