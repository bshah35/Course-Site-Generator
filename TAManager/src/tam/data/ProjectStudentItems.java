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
public class ProjectStudentItems<E extends Comparable<E>> implements Comparable<E>  {
     
// THE TABLE WILL STORE RECITATIONS ITEMS   
    private final StringProperty firstName, lastName, team, role;

    public ProjectStudentItems(String initFirstName, String initLastName, String initTeam, String initRole) {
        firstName = new SimpleStringProperty(initFirstName);
        lastName = new SimpleStringProperty(initLastName);
        team = new SimpleStringProperty(initTeam);
        role = new SimpleStringProperty(initRole);
       
    }

    public String getFirstName() {
        return firstName.get();
    }
    
    public String getLastName() {
        return lastName.get();
    }
    
    public String getTeam() {
        return team.get();
    }
    
    public String getRole() {
        return role.get();
    }
    

    public void setFirstName(String initFirstName) {
        firstName.set(initFirstName);
    }
    
    public void setLastName(String initLastName) {
        lastName.set(initLastName);
    }
    
    public void setTeam(String initTeam) {
        team.set(initTeam);
    }
    
    public void setRole(String initRole) {
        role.set(initRole);
    }

    @Override
    public int compareTo(E otherStudentName) {
        return getFirstName().compareTo(((ProjectStudentItems) otherStudentName).getFirstName());
    }
    
    @Override
    public String toString(){
        return firstName.getValue() + lastName.getValue() + team.getValue() + role.getValue();
    }
}
