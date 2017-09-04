/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Bilal
 */
public class ProjectTeamItems<E extends Comparable<E>> implements Comparable<E>  {
     
// THE TABLE WILL STORE RECITATIONS ITEMS   
    private final StringProperty teamName, teamColor, teamTextColor, teamLink;

    public ProjectTeamItems(String initTeamName, String initTeamColor, String initTeamTextColor, String initTeamLink) {
        teamName = new SimpleStringProperty(initTeamName);
        teamColor = new SimpleStringProperty(initTeamColor);
        teamTextColor = new SimpleStringProperty(initTeamTextColor);
        teamLink = new SimpleStringProperty(initTeamLink);
       
    }

    public String getTeamName() {
        return teamName.get();
    }
    
    public String getTeamColor() {
        return teamColor.get();
    }
    
    public String getTeamTextColor() {
        return teamTextColor.get();
    }
    
    public String getTeamLink() {
        return teamLink.get();
    }
    

    public void setTeamName(String initTeamName) {
        teamName.set(initTeamName);
    }
    
    public void setTeamColor(String initTeamColor) {
        teamColor.set(initTeamColor);
    }
    
    public void setTeamTextColor(String initTeamTextColor) {
        teamTextColor.set(initTeamTextColor);
    }
    
    public void setTeamLink(String initTeamLink) {
        teamLink.set(initTeamLink);
    }

    @Override
    public int compareTo(E otherTeamName) {
        return getTeamName().compareTo(((ProjectTeamItems) otherTeamName).getTeamName());
    }
    
    @Override
    public String toString(){
        return teamName.getValue() + teamColor.getValue() + teamTextColor.getValue() + teamLink.getValue();
    }
}
