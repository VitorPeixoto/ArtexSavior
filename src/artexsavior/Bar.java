//<editor-fold defaultstate="collapsed" desc="CODE issues and "to do" operations">
// Write here operations that are missing
//</editor-fold>

package artexsavior;

import java.awt.Color;

/** Descrição do Código
 *******************************************************************************
 * @classname Bar.java                                                         *
 * @date     14/05/2014                                                        *   
 * @authors  Peixoto                                                           *              
 *                                                                             *   
 * @description Classe que representa e controla todo tibo de "Barras" no jogo,*   
 *              como barras de vida ou mana.                                   * 
 *******************************************************************************/

public class Bar extends javax.swing.JProgressBar {
    private int value;
    private final int maxValue;
    private Color barFillColor;
    
    public Bar(int initialValue, int maxValue, Color barFillColor) {
        this.value = initialValue;
        this.maxValue = maxValue;
        this.barFillColor = barFillColor;
        this.setBackground(barFillColor);
        setMaximum(maxValue);
        setValue(value);
    }        
    
}
