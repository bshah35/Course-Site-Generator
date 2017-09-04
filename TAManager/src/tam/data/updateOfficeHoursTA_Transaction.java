/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import jtps.jTPS_Transaction;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Bilal
 */
public class updateOfficeHoursTA_Transaction implements jTPS_Transaction {

    private TAData data;
    private TAWorkspace workspace;
    private String end;
    private String start;
    private int oldStartHour, oldEndHour;
    private HashMap<String, StringProperty> officeHours;
    private HashMap<String, StringProperty> officeHours2 = new HashMap();

    public updateOfficeHoursTA_Transaction(String start, String end, TAData data, TAWorkspace workspace) {
        this.start = start;
        this.end = end;
        this.data = data;
        this.workspace = workspace;
        oldStartHour = data.getStartHour();
        oldEndHour = data.getEndHour();
        officeHours = data.getOfficeHours();
        
         for (HashMap.Entry<String, StringProperty> entry : officeHours.entrySet()) {
               String key = entry.getKey();
               StringProperty value = entry.getValue();
               
               officeHours2.put(key, value);

        }
    }

    @Override
    public void doTransaction() {
        try {
            data.OfficeHoursUpdate(start, end,true);
        } catch (IOException ex) {
            Logger.getLogger(updateOfficeHoursTA_Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void undoTransaction() {
         workspace.resetWorkspace();
        try {
            data.OfficeHoursUpdate(Integer.toString(oldStartHour), Integer.toString(oldEndHour), false);
        } catch (IOException ex) {
            Logger.getLogger(updateOfficeHoursTA_Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
         
               
                data.initHours(Integer.toString(oldStartHour),Integer.toString(oldEndHour));
               for (int row = 1; row < ((oldEndHour - oldStartHour) * 2 + 1); row++) {
                for (int col = 2; col < 7; col++) {
                    try {
                        StringProperty cellProp = officeHours2.get(buildCellKey(col, row));

                        String taName = cellProp.getValue();
                        data.toggleTAOfficeHourUpdate(buildCellKey(col, row), taName);
                        

                    } catch (NullPointerException e) {

                    }
                }
            }
       
    }
    
      public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

}
