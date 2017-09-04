/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.Collections;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class AddRecitation_Transaction implements jTPS_Transaction {

    ObservableList<RecitationItems> recitationItems;
    RecitationItems recitations;

    public AddRecitation_Transaction(ObservableList<RecitationItems> recitationItems, RecitationItems recitations) {
        this.recitationItems = recitationItems;
        this.recitations = recitations;
    }

    @Override
    public void doTransaction() {
        if (!recitationItems.contains(recitations)) {
            recitationItems.add(recitations);
        }
         Collections.sort(recitationItems);

    }

    @Override
    public void undoTransaction() {
         recitationItems.remove(recitations);
    }
}
