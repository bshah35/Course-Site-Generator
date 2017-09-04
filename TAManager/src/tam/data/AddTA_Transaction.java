/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class AddTA_Transaction implements jTPS_Transaction{
     
    ObservableList<TeachingAssistant> teachingAssistants;
    TeachingAssistant ta;
     public AddTA_Transaction(ObservableList<TeachingAssistant> teachingAssistants, TeachingAssistant ta){
         this.teachingAssistants = teachingAssistants;
         this.ta = ta;
     }
     @Override
    public void doTransaction() {
        if(!teachingAssistants.contains(ta)){
        teachingAssistants.add(ta);
        }
         Collections.sort(teachingAssistants);
       
    }
   

    @Override
    public void undoTransaction() {
      teachingAssistants.remove(ta);
    }
}
