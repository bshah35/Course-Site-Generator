/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.HashMap;
import javafx.beans.property.StringProperty;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class ToggleTA_Transaction implements jTPS_Transaction {
     HashMap<String, StringProperty> officeHours;
     String taName;
     String cellKey;
    
     public ToggleTA_Transaction(String cellKey, String taName, HashMap<String, StringProperty> officeHours){
         this.cellKey = cellKey;
         this.taName = taName;
         this.officeHours = officeHours;
        
        
         
     }
     
     @Override
    public void doTransaction() {
          StringProperty cellProp = officeHours.get(cellKey);
        if (cellProp != null) {
            String cellText = cellProp.getValue();

            if (cellText.contains(taName)) {
                removeTAFromCell(cellProp, taName);
            } else {
                cellProp.setValue(cellText + taName + "\n");
            }
        }

    }

    @Override
    public void undoTransaction() {
         StringProperty cellProp = officeHours.get(cellKey);
        if (cellProp != null) {
            String cellText = cellProp.getValue();

            if (cellText.contains(taName)) {
                removeTAFromCell(cellProp, taName);
            } else {
                cellProp.setValue(cellText + taName + "\n");
            }
        }

       
    }
    
    
       public void removeTAFromCell(StringProperty cellProp, String taName) {
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) {
            cellProp.setValue("");
        } // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellText.indexOf(taName) == 0) {
            int startIndex = cellText.indexOf("\n") + 1;
            cellText = cellText.substring(startIndex);
            cellProp.setValue(cellText);
        } // IT MUST BE ANOTHER TA IN THE CELL
        else {
            // int startIndex = cellText.indexOf("\n" + taName);  
            cellText = cellText.replaceAll("\n" + taName, "");
            // cellText = cellText.substring(startIndex,LastIndex);

            cellProp.setValue(cellText);
        }
    }
    
}
