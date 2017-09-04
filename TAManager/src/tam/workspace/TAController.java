package tam.workspace;

import static tam.TAManagerProp.*;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.util.Collection;
import java.util.HashMap;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.AddTA_Transaction;
import tam.data.CSGData;
import tam.data.EditTA_Transaction;
import tam.data.RecitationItems;
import tam.data.RemoveTA_Transaction;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.ToggleTA_Transaction;
import tam.style.TAStyle;
import tam.workspace.TAWorkspace;

/**
 * This class provides responses to all workspace interactions, meaning
 * interactions with the application controls not including the file toolbar.
 *
 * Updated by: Bilal Shah
 *
 * @version 1.0
 */
public class TAController {

    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    TAManagerApp app;
    static jTPS jtps = new jTPS();
    HashMap<String, Pane> officeTACellPane = new HashMap();
    TableView ta_table = new TableView();

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TAController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    /**
     * This method responds to when the user requests to add a new TA via the
     * UI. Note that it must first do some validation to make sure a unique name
     * and email address has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField nameTextField = workspace.getTAWorkspace().getNameTextField();
        TextField emailTextField = workspace.getTAWorkspace().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        Boolean check = false;

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        TAData data = csgData.getTAData();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));

        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name) || data.containsTA(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));
        } //Validate email address
        else if (!isValidEmailAddress(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_EMAIL_TITLE), props.getProperty(EMAIL_NOT_VALID));
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA

            //data.addTA(name, email);
            jTPS_Transaction add;
            TeachingAssistant ta = new TeachingAssistant(name, email, check);
            add = new AddTA_Transaction(data.getTeachingAssistants(), ta);
            jtps.addTransaction(add);
            app.getGUI().updateToolbarControls(false);
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

        }
    }

    //Helper method to validate an email address
    private boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        if (email.length() > 64) {
            return false;
        }
        return m.matches();
    }

    public void upDateOfficeHours() {

    }

    /**
     * This method gets called when the update button is pressed to handle the
     * updating of TA name and email
     */
    public void handleEditTA() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData csgData = (CSGData) app.getDataComponent();
        TAData data = csgData.getTAData();
        TableView taTable = workspace.getTAWorkspace().getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant) selectedItem;
        String oldTaName = ta.getName();
        String oldEmail = ta.getEmail();
        TextField nameTextField = workspace.getTAWorkspace().getNameTextField();
        String newTaName = nameTextField.getText();
        TextField emailTextField = workspace.getTAWorkspace().getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        Boolean check = false;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));

        } // DID THE USER NEGLECT TO PROVIDE A TA EMAIL?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        } else if (!isValidEmailAddress(email)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_EMAIL_TITLE), props.getProperty(EMAIL_NOT_VALID));
        } // EVERYTHING IS FINE, ADD A NEW TA
        else {
            ta_table = workspace.getTAWorkspace().getTATable();
            // taTable.getItems().remove(selectedItem);
            // ADD THE NEW TA TO THE DATA
            //data.addTA(name, email);
            jTPS_Transaction edit;
            TeachingAssistant newTa = new TeachingAssistant(name, email, check);
            officeTACellPane = workspace.getTAWorkspace().getOfficeHoursGridTACellPanes();
            edit = new EditTA_Transaction(oldTaName, newTaName, oldEmail, email, data.getOfficeHours(), data.getTeachingAssistants(), ta, newTa, officeTACellPane, ta_table, false,workspace,csgData);
            jtps.addTransaction(edit);
            app.getGUI().updateToolbarControls(false);
            // CLEAR THE TEXT FIELDS

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            /*for (Pane pane : workspace.getOfficeHoursGridTACellPanes().values()) {
                String cellKey = pane.getId();
                data.editTA(cellKey, oldTaName, newTaName);
            }
             */
            nameTextField.setText("");
            emailTextField.setText("");
            taTable.getSelectionModel().clearSelection();
        }

    }

    /**
     * This method highlights the row and column until it reaches the current
     * cell key which the mouse is hovering over
     *
     * @param pane
     */
    public void handleCellHighlightMouseEnter(Pane pane) {
        pane.setStyle("-fx-border-color: #f5f500; -fx-background-color: #f5f500");
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        String currentKey = workspace.getTAWorkspace().getCellKey(pane);
        String rowString = "", colString = "";
        int row, col;
        boolean flag = false;
        for (int i = 0; i < currentKey.length(); i++) {
            if (currentKey.charAt(i) != '_' && !flag) {
                colString += currentKey.charAt(i);
            }

            if (currentKey.charAt(i) == '_') {
                flag = true;
            }
            if (flag) {
                if (currentKey.charAt(i) != '_') {
                    rowString += currentKey.charAt(i);
                }
            }
        }
        col = Integer.parseInt(colString);
        row = Integer.parseInt(rowString);

        for (int j = 2; j < col; j++) {
            String cellPane = workspace.getTAWorkspace().buildCellKey(j, row);
            Pane p = workspace.getTAWorkspace().getTACellPane(cellPane);
            p.setStyle("-fx-border-color: #ffff99 black #ffff99 black; -fx-border-width: 2.5px 1px 2.5px 1px");

        }

        for (int j = 1; j < row; j++) {
            String cellPane = workspace.getTAWorkspace().buildCellKey(col, j);
            Pane p = workspace.getTAWorkspace().getTACellPane(cellPane);
            p.setStyle("-fx-border-color: black #ffff99 black #ffff99; -fx-border-width: 1px 2.5px 1px 2.5px");

        }

        for (int j = 0; j < 2; j++) {
            String cellPane = workspace.getTAWorkspace().buildCellKey(j, row);
            Pane p = workspace.getTAWorkspace().getGridTimeCellPane(cellPane);
            if (j == 0) {
                p.setStyle("-fx-border-color: #ffff99 white #ffff99 #ffff99; -fx-border-width: 2.5px 1px 2.5px 3px");

            } else {
                p.setStyle("-fx-border-color: #ffff99 white #ffff99 white; -fx-border-width: 2.5px 1px 2.5px 1px");

            }

        }

        String cellPane = workspace.getTAWorkspace().buildCellKey(col, 0);
        Pane p = workspace.getTAWorkspace().getGridDayHeaderCellPane(cellPane);

        p.setStyle("-fx-border-color: #ffff99 #ffff99 white #ffff99; -fx-border-width: 2.5px 2.5px 1px 2.5px");

    }

    /**
     * This method Unhighlights the row and column until it reaches the current
     * cell key which the mouse is hovering over
     *
     * @param pane
     *
     */
    public void handleCellUnHighlightMouseExited(Pane pane) {
        pane.setStyle("-fx-border-color: black");

        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        String currentKey = workspace.getTAWorkspace().getCellKey(pane);
        String rowString = "", colString = "";
        int row, col;
        boolean flag = false;
        for (int i = 0; i < currentKey.length(); i++) {
            if (currentKey.charAt(i) != '_' && !flag) {
                colString += currentKey.charAt(i);
            }

            if (currentKey.charAt(i) == '_') {
                flag = true;
            }
            if (flag) {
                if (currentKey.charAt(i) != '_') {
                    rowString += currentKey.charAt(i);
                }
            }
        }
        col = Integer.parseInt(colString);
        row = Integer.parseInt(rowString);

        for (int j = 2; j < col; j++) {
            String cellPane = workspace.getTAWorkspace().buildCellKey(j, row);
            Pane p = workspace.getTAWorkspace().getTACellPane(cellPane);
            p.setStyle("-fx-border-color: black");

        }

        for (int j = 1; j < row; j++) {
            String cellPane = workspace.getTAWorkspace().buildCellKey(col, j);
            Pane p = workspace.getTAWorkspace().getTACellPane(cellPane);
            p.setStyle("-fx-border-color: black");

        }

        for (int j = 0; j < 2; j++) {
            String cellPane = workspace.getTAWorkspace().buildCellKey(j, row);
            Pane p = workspace.getTAWorkspace().getGridTimeCellPane(cellPane);
            p.setStyle("-fx-border-color: white");

        }

        String cellPane = workspace.getTAWorkspace().buildCellKey(col, 0);
        Pane p = workspace.getTAWorkspace().getGridDayHeaderCellPane(cellPane);
        p.setStyle("-fx-border-color: white");

    }

    /**
     * This function provides a response for when the user clicks on the office
     * hours grid to add or remove a TA to a time slot.
     *
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAWorkspace().getTATable();

        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();

        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant) selectedItem;
        String taName = ta.getName();
        CSGData csgData = (CSGData) app.getDataComponent();
        TAData data = csgData.getTAData();
        String cellKey = pane.getId();

        // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
        // data.toggleTAOfficeHours(cellKey, taName);
        jTPS_Transaction toggle = new ToggleTA_Transaction(cellKey, taName, data.getOfficeHours());
        jtps.addTransaction(toggle);
    }

    private boolean validateDelete() {
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.setMinWidth(450);
        yesNoDialog.show(props.getProperty(TA_DELETE_TITLE), props.getProperty(TA_DELETE_MESSAGE));
        String selection = yesNoDialog.getSelection();
        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This function provides a response for when the user presses the delete
     * button to delete TA from table and office grid
     */
    public void handleTADeletePress() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAWorkspace().getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        if (selectedItem != null && validateDelete()) {
            TeachingAssistant ta = (TeachingAssistant) selectedItem;
            String taName = ta.getName();
            CSGData csgData = (CSGData) app.getDataComponent();
            TAData data = csgData.getTAData();

            officeTACellPane = workspace.getTAWorkspace().getOfficeHoursGridTACellPanes();
            jTPS_Transaction remove = new RemoveTA_Transaction(taName, data.getOfficeHours(), data.getTeachingAssistants(), ta, officeTACellPane, false,workspace,csgData);
            jtps.addTransaction(remove);
            for (Pane pane : workspace.getTAWorkspace().getOfficeHoursGridTACellPanes().values()) {
                String cellKey = pane.getId();
                data.removeTaFromTable(cellKey, taName);
            }
        
            taTable.getItems().remove(selectedItem);
        }
    }

    public void handleUndo() {
        jtps.undoTransaction();
    }

    public void handleredo() {
        jtps.doTransaction();
    }

    public jTPS getjTPS() {
        return jtps;
    }
}
