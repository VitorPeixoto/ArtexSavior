//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior;

import java.awt.Graphics;
import java.awt.Image;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/** Descrição do Código
 *******************************************************************************
 * @classname BackgroundPanel.java                                             *
 * @date      18/04/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe que controla a pintura das imagens de fundo na tela.    *   
 *              Extende javax.swing.JPanel, de modo a ser um componente a ser  *  
 *              adicionado ao frame.                                           *
 *******************************************************************************/

public class BackgroundPanel extends JPanel implements Constants {    
    private Image backgroundImage;  //Imagem referente ao fundo da área atual
    
    private final int width;        //Comprimento do painel
    private final int height;       //Altura do painel 
    
    private int offsetX;            // Offsets X e Y da imagem, que é maior que o     
    private int offsetY;            // painel. Utilizados para pintar diferentes 
                                    // partes da imagem.
    
    private final Thread atualize;  // Thread que cuida da atualização das
                                    // informações do painel, atualizando a pintura
                                    // com o repaint();    
    
    /**
     * Constructor of BackdroundPanel class
     * Construtor that generate an object that will control the painting of the 
     * background image on the screen
     * @param backgroundImage area image to paint on background
     * @param width width if the container
     * @param height height if the container
     */
    public BackgroundPanel(Image backgroundImage, int width, int height) {
        this.backgroundImage = backgroundImage;
        this.width = IMAGE_WIDTH;
        this.height = IMAGE_HEIGHT;
        
        atualize = new Thread(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    try {
                        repaint();  //Atualiza a pintura
                        Thread.sleep(1000); //Espera um pouco
                    } catch (InterruptedException ex) {
                        Logger.getLogger(BackgroundPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        }); atualize.start();
    }                 
  
   @Override
   public void paint(Graphics g) {
       //g.setClip(-offsetX, 10, width, height);
       g.drawImage(backgroundImage, 0, 0, width, height, offsetX, offsetY, (width+offsetX), (height+offsetY), null);
       //g.drawImage(backgroundImage, g.getClipBounds().x, 0, null);
   }    
    
    
    /**
     * Setter of offsetY
     * Set the value of the "Y" offset of the image that will controll the map
     * piece that will be painted
     * 
     * @param offsetY new value of offsetY
     */
    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    /**
     * Setter of offsetX
     * Set the value of the "X" offset of the image that will controll the map
     * piece that will be painted
     *
     * @param offsetX new value of offsetX
     */
    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }
    
    /**
     * Getter of offsetX
     * Get the value of the "X" offset of the image to controll the map
     * piece that will be painted
     *
     * @return the "X" offset
     */
    public int getOffsetX() {
        return offsetX;
    }

     /**
     * Getter of offsetY
     * Get the value of the "Y" offset of the image to controll the map
     * piece that will be painted
     *
     * @return the "Y" offset
     */
    public int getOffsetY() {
        return offsetY;
    }        

    /**
     * Getter of backgroundImage
     * Return the Image of the actual area
     * @return the background image that is being painted
     */
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    /**
     * Setter of backgroundImage
     * Set the Image of the actual area
     * @param backgroundImage new background image to be painted
     */
    public void setBackgroundImage(Image backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    
}
