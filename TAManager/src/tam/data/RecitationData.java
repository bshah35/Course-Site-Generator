/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author Bilal
 */
public class RecitationData {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    TAManagerApp app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<RecitationItems> recitationTable;

    public RecitationData(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        recitationTable = FXCollections.observableArrayList();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
    }

    public void resetData() {
        recitationTable.clear();
    }

    public ObservableList getRecitationTable() {
        return recitationTable;
    }

    public boolean containsRecitations(String testName) {
        for (RecitationItems recit : recitationTable) {
            if (recit.getSections().equals(testName)) {
                return true;
            }
        }
        return false;
    }

    public void addRecitation(String initSections, String initInstructor, String initData, String initLocation, String initName, String initName2, Boolean flag) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationItems recitation = new RecitationItems(initSections, initInstructor, initData, initLocation, initName, initName2);
        if (!containsRecitations(initSections)) {
            recitationTable.add(recitation);
        }
        if (flag) {
            workspace.getRecitationWorkspace().reloadWorkspace();
        }

    }

}
