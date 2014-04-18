package artexsavior;

import java.awt.Image;

/** Descrição do Código
 *******************************************************************************
 * @classname Area.java                                                        *
 * @date      18/04/2014                                                       *   
 * @authors   Peixoto                                                          *              
 *                                                                             *   
 * @description Classe que representa uma a área atual na tela e no jogo,      *   
 *              controlando o posicionamento das sucessivas áreas.             *  
 *******************************************************************************/

public class Area {
    private final int numberOf;   //Single number of the area
    
    private Area upperArea,       //Reference for the upper area on Map
                 downArea,        //Reference for the down area on Map
                 leftArea,        //Reference for the left area on Map
                 rightArea;       //Reference for the right area on Map

    /**
     *
     * @param Area
     */
    public Area(int Area) {
        this.numberOf = Area;
    }

    /**
     * Getter of numberOf
     * @return the single number of the area
     */
    public int getNumber() {
        return numberOf;
    }

    /**
     * Getter of UpperArea
     * @return the area that is above this area on map
     */
    public Area getUpperArea() {
        return upperArea;
    }

    /**
     * Getter of downArea
     * @return the area that is below this area on map
     */
    public Area getDownArea() {
        return downArea;
    }

    /**
     * Getter of leftArea
     * @return the area that is left of this area on map
     */
    public Area getLeftArea() {
        return leftArea;
    }

    /**
     * Getter of rightArea
     * @return the area that is right of this area on map
     */
    public Area getRightArea() {
        return rightArea;
    }
    
    /**
     * Getter of this area images
     * @param wichType char value of the type of image.
     * 'U': Upperground; 'B': Background;
     * @return the background/upperground image of this area
     */
    public Image getImage(char wichType) {
        return (new javax.swing.ImageIcon(getClass().getResource("/Areas/"+numberOf+"/"+wichType+numberOf+".png"))).getImage();
    }
       
    
}
