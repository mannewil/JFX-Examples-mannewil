/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zaklad;

import javafx.scene.paint.Color;

/**
 *
 * @author wille
 */
public class Ingredients {
    
    private String name;
    private int size;
    private Color color;
    
    public Ingredients(String name, int size, Color color){
    this.name = name;
    this.size = size;
    this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }
    
    public void setTransparentColor(double opacity) {
        this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), opacity);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%s, (%d), %s", name, size, color.toString());
    }
    
    
    
}
