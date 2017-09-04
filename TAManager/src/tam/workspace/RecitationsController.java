/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import static com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker.check;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_MESSAGE;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_TITLE;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.HashMap;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import static sun.nio.ch.IOStatus.check;
import tam.TAManagerApp;
import tam.TAManagerProp;
import static tam.TAManagerProp.ADD_RECITATION_MESSAGE;
import static tam.TAManagerProp.ADD_RECITATION_TITLE;
import static tam.TAManagerProp.MISSING_DATE_NAME;
import static tam.TAManagerProp.MISSING_DATE_TITLE;
import static tam.TAManagerProp.MISSING_INSTRUCTOR_NAME;
import static tam.TAManagerProp.MISSING_INSTRUCTOR_TITLE;
import static tam.TAManagerProp.MISSING_LOCATION_NAME;
import static tam.TAManagerProp.MISSING_LOCATION_TITLE;
import static tam.TAManagerProp.MISSING_SECTION_NAME;
import static tam.TAManagerProp.MISSING_SECTION_TITLE;
import static tam.TAManagerProp.MISSING_TA_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TA_NAME_TITLE;
import static tam.TAManagerProp.RECITATION_DELETE_MESSAGE;
import static tam.TAManagerProp.RECITATION_DELETE_TITLE;
import static tam.TAManagerProp.RECITATION_INVALID_FORMAT;
import static tam.TAManagerProp.RECITATION_UNIQUE_MESSAGE;
import static tam.TAManagerProp.RECITATION_UNIQUE_TITLE;
import static tam.TAManagerProp.TA_NAME_NOT_UNIQUE_MESSAGE;
import static tam.TAManagerProp.TA_NAME_NOT_UNIQUE_TTILE;
import static tam.TAManagerProp.UPDATE_RECITATION_MESSAGE;
import static tam.TAManagerProp.UPDATE_RECITATION_TITLE;
import tam.data.AddRecitation_Transaction;
import tam.data.AddTA_Transaction;
import tam.data.CSGData;
import tam.data.EditRecitations_Transaction;
import tam.data.RecitationData;
import tam.data.RecitationItems;
import tam.data.RemoveRecitation_Transaction;
import tam.data.TeachingAssistant;

/**
 *
 * @author Bilal
 */
public class RecitationsController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED

    TAManagerApp app;
    static jTPS jtps = new jTPS();
    TableView recitation_table = new TableView();

    /**
     * Constructor, note that the app must already be constructed.
     */
    public RecitationsController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    public void handleAddReciations() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField sectionTextField = workspace.getRecitationWorkspace().getSectionTextField();
        TextField instructorTextField = workspace.getRecitationWorkspace().getInstructorTextField();
        TextField datelTextField = workspace.getRecitationWorkspace().getDateTextField();
        TextField locationTextField = workspace.getRecitationWorkspace().getLocationTextField();
        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String date = datelTextField.getText();
        String location = locationTextField.getText();
        String ta1 = workspace.getRecitationWorkspace().getTA1();
        String ta2 = workspace.getRecitationWorkspace().getTA2();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        RecitationData data = csgData.getRecitationData();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (section.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_SECTION_TITLE), props.getProperty(MISSING_SECTION_NAME));
        } else if (data.containsRecitations(section)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(RECITATION_UNIQUE_TITLE), props.getProperty(RECITATION_UNIQUE_MESSAGE));
        } else if (!validateSection(section)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_SECTION_TITLE), props.getProperty(RECITATION_INVALID_FORMAT));
        } else if (instructor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(MISSING_INSTRUCTOR_NAME));
        } else if ((!validateInstructor(instructor))) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(RECITATION_INVALID_FORMAT));
        } else if (date.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_DATE_TITLE), props.getProperty(MISSING_DATE_NAME));
        } else if (!validateDate(date)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_DATE_TITLE), props.getProperty(RECITATION_INVALID_FORMAT));
        } else if (location.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_NAME));
        } else if (ta1.equals("") || ta2.equals("")) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } else if (ta2.equalsIgnoreCase(ta1)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(TA_NAME_NOT_UNIQUE_TTILE), props.getProperty(TA_NAME_NOT_UNIQUE_MESSAGE));
        } else {
            jTPS_Transaction add;
            RecitationItems recitations = new RecitationItems(section, instructor, date, location, ta1, ta2);
            add = new AddRecitation_Transaction(data.getRecitationTable(), recitations);
            jtps.addTransaction(add);

            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(ADD_RECITATION_TITLE), props.getProperty(ADD_RECITATION_MESSAGE));

            sectionTextField.setText("");
            instructorTextField.setText("");
            datelTextField.setText("");
            locationTextField.setText("");
            String taNameTextField = props.getProperty(TAManagerProp.RECITATION_TA_NAME_COMBO_TEXT.toString());
            workspace.getRecitationWorkspace().taCombo.setValue(taNameTextField);
            workspace.getRecitationWorkspace().ta2Combo.setValue(taNameTextField);
        }
    }

    public void handleEditRecitation() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recitationTable = workspace.getRecitationWorkspace().getRecitationTable();
        Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
        RecitationItems recit = (RecitationItems) selectedItem;
        TextField sectionTextField = workspace.getRecitationWorkspace().getSectionTextField();
        TextField instructorTextField = workspace.getRecitationWorkspace().getInstructorTextField();
        TextField datelTextField = workspace.getRecitationWorkspace().getDateTextField();
        TextField locationTextField = workspace.getRecitationWorkspace().getLocationTextField();
        String newSection = sectionTextField.getText();
        String newInstructor = instructorTextField.getText();
        String newDate = datelTextField.getText();
        String newLocation = locationTextField.getText();
        String newTa1 = workspace.getRecitationWorkspace().getTA1();
        String newTa2 = workspace.getRecitationWorkspace().getTA2();
        String oldSection = recit.getSections();
        String oldInstructor = recit.getInstructor();
        String oldDate = recit.getDate();
        String oldLocation = recit.getLocation();
        String oldTa1 = recit.getTa1();
        String oldTa2 = recit.getTa2();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (newSection.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_SECTION_TITLE), props.getProperty(MISSING_SECTION_NAME));
        } else if (!validateSection(newSection)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_SECTION_TITLE), props.getProperty(RECITATION_INVALID_FORMAT));
        } else if (newInstructor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(MISSING_INSTRUCTOR_NAME));
        } else if ((!validateInstructor(newInstructor))) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_INSTRUCTOR_TITLE), props.getProperty(RECITATION_INVALID_FORMAT));
        } else if (newDate.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_DATE_TITLE), props.getProperty(MISSING_DATE_NAME));
        } else if (!validateDate(newDate)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_DATE_TITLE), props.getProperty(RECITATION_INVALID_FORMAT));
        } else if (newLocation.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_LOCATION_TITLE), props.getProperty(MISSING_LOCATION_NAME));
        } else if (newTa1 == null || newTa2 == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));
        } else if (newTa2.equalsIgnoreCase(newTa1)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(TA_NAME_NOT_UNIQUE_TTILE), props.getProperty(TA_NAME_NOT_UNIQUE_MESSAGE));
        } else {
            recitation_table = workspace.getRecitationWorkspace().getRecitationTable();
            jTPS_Transaction edit;
            edit = new EditRecitations_Transaction(recit, oldSection, oldInstructor, oldDate, oldLocation, oldTa1, oldTa2, newSection, newInstructor, newDate,
                    newLocation, newTa1, newTa2, recitation_table);
            jtps.addTransaction(edit);

            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(UPDATE_RECITATION_TITLE), props.getProperty(UPDATE_RECITATION_MESSAGE));

            sectionTextField.setText("");
            instructorTextField.setText("");
            datelTextField.setText("");
            locationTextField.setText("");
            String taNameTextField = props.getProperty(TAManagerProp.RECITATION_TA_NAME_COMBO_TEXT.toString());
            workspace.getRecitationWorkspace().taCombo.setValue(taNameTextField);
            workspace.getRecitationWorkspace().ta2Combo.setValue(taNameTextField);
            recitationTable.getSelectionModel().clearSelection();
        }
    }

    private boolean validateSection(String section) {
        if ((section.matches("R" + ".*\\d+.*")) && (section.length() == 3)) {
            return true;
        } else {
            return false;
        }

    }

    private boolean validateInstructor(String instructor) {
        if (instructor.contains(".*\\d+.*")) {
            return false;

        } else {
            return true;
        }
    }

    private boolean validateDate(String date) {
        //
        if (((date.matches("Mondays, " + "(?i).*\\b(?:(?<twelve>(?:0?[0-9]|1[0-2]):[0-5][0-9](?::[0-5][0-9])?[ ]*[ap]\\.?m\\.?)|(?<twfour>(?:[01]?[0-9]|2[0-3]):[0-5][0-9](?::[0-5][0-9])?))\\b.*"))
                || (date.matches("Tuesdays, " + "(?i).*\\b(?:(?<twelve>(?:0?[0-9]|1[0-2]):[0-5][0-9](?::[0-5][0-9])?[ ]*[ap]\\.?m\\.?)|(?<twfour>(?:[01]?[0-9]|2[0-3]):[0-5][0-9](?::[0-5][0-9])?))\\b.*")
                || (date.matches("Wednesdays, " + "(?i).*\\b(?:(?<twelve>(?:0?[0-9]|1[0-2]):[0-5][0-9](?::[0-5][0-9])?[ ]*[ap]\\.?m\\.?)|(?<twfour>(?:[01]?[0-9]|2[0-3]):[0-5][0-9](?::[0-5][0-9])?))\\b.*"))
                || (date.matches("Thursdays, " + "(?i).*\\b(?:(?<twelve>(?:0?[0-9]|1[0-2]):[0-5][0-9](?::[0-5][0-9])?[ ]*[ap]\\.?m\\.?)|(?<twfour>(?:[01]?[0-9]|2[0-3]):[0-5][0-9](?::[0-5][0-9])?))\\b.*"))
                || (date.matches("Fridays, " + "(?i).*\\b(?:(?<twelve>(?:0?[0-9]|1[0-2]):[0-5][0-9](?::[0-5][0-9])?[ ]*[ap]\\.?m\\.?)|(?<twfour>(?:[01]?[0-9]|2[0-3]):[0-5][0-9](?::[0-5][0-9])?))\\b.*"))))
                && ((date.contains("pm")) || date.contains("am"))) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateDelete() {
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.setMinWidth(450);
        yesNoDialog.show(props.getProperty(RECITATION_DELETE_TITLE), props.getProperty(RECITATION_DELETE_MESSAGE));
        String selection = yesNoDialog.getSelection();
        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            return true;
        } else {
            return false;
        }
    }

    public void handleRecitationDeletePress() {
        CSGData csgData = (CSGData) app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView recitationTable = workspace.getRecitationWorkspace().getRecitationTable();

        if (!recitationTable.getItems().isEmpty()) {
            Object selectedItem = recitationTable.getSelectionModel().getSelectedItem();
            if (selectedItem != null && validateDelete()) {
                RecitationItems recit = (RecitationItems) selectedItem;
                RecitationData data = csgData.getRecitationData();

                jTPS_Transaction remove = new RemoveRecitation_Transaction(data.getRecitationTable(), recit);
                jtps.addTransaction(remove);
                recitationTable.getItems().remove(selectedItem);
            }
        }
    }

    public void handleUndo() {
        jtps.undoTransaction();
    }

    public void handleredo() {
        jtps.doTransaction();
    }

}
