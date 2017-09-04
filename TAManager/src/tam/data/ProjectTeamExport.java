/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

/**
 *
 * @author Bilal
 */
public class ProjectTeamExport {
    
 
    public String name, textColor,red, green ,blue;
    public ProjectTeamExport(String name, String red, String green, String blue, String textColor){
        this.name = name;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.textColor = textColor;
    }
    
    public String getName(){
        return name;
    }
    
    public String getRed(){
        return red;
    }
    
    public String getGreen(){
        return green;
    }
    
    public String getBlue(){
        return blue;
    }
    
    public String getTextColor(){
        return textColor;
    }
    
      public void setName(String name){
        this.name = name;
    }
    
    public void setRed(String red){
        this.red = red;
    }
    
    public void setGreen(String green){
        this.green = green;
    }
    
    public void setBlue(String blue){
        this.blue = blue;
    }
    
    public void setTextColor(String textColor){
        this.textColor = textColor;
    }
    
}
