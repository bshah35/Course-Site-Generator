package tam.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import djf.components.AppDataComponent;
import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_ERROR_TITLE;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_MESSAGE;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_TITLE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_MESSAGE;
import static djf.settings.AppPropertyType.SAVE_UNSAVED_WORK_TITLE;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.TAManagerProp;
import tam.data.TeachingAssistant;
import tam.file.TAFiles;
import tam.workspace.CSGWorkspace;
import tam.workspace.TAController;
import tam.workspace.TAWorkspace;

/**
 * This is the data component for TAManagerApp. It has all the data needed to be
 * set by the user via the User Interface and file I/O can set and get all the
 * data from this object
 *
 * Updated by: Bilal Shah
 */
public class TAData{// implements AppDataComponent {

    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES
    TAManagerApp app;

    // NOTE THAT THIS DATA STRUCTURE WILL DIRECTLY STORE THE
    // DATA IN THE ROWS OF THE TABLE VIEW
    ObservableList<TeachingAssistant> teachingAssistants;
    static int count = 0, count2 = 0;
    int count3=0;

    private ArrayList<TAWorkspace> transactions = new ArrayList();
    private int mostRecentTransaction = -1;
    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;
    HashMap<String, StringProperty> officeHours2;

    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
   
    static jTPS jtps = new jTPS();
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    /**
     * This constructor will setup the required data structures for use, but
     * will have to wait on the office hours grid, since it receives the
     * StringProperty objects from the Workspace.
     *
     * @param initApp The application this data manager belongs to.
     */
    public TAData(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // CONSTRUCT THE LIST OF TAs FOR THE TABLE
        teachingAssistants = FXCollections.observableArrayList();

        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;

        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();

        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(TAManagerProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(TAManagerProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }

    /**
     * Called each time new work is created or loaded, it resets all data and
     * data structures such that they can be used for new values.
     */
 //   @Override
    public void resetData() {
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        teachingAssistants.clear();
        officeHours.clear();
        count3 = 0;
    }

    // ACCESSOR METHODS
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }

    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }

    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }

    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public int getRowRange(String start, String end) {
        return ((Integer.parseInt(end) - Integer.parseInt(start)) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;

        if (time.contains("pm") && hour != 12) {
            milHour += 12;
        }

        /* if (hour < startHour) {
            milHour += 12;
        }
         */
        row += (milHour - startHour) * 2;
        if (time.contains("_30")) {
            row += 1;
        }
        return getCellKey(col, row);
    }

    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }

    /**
     * This method is for giving this data manager the string property for a
     * given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);

    }

    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
            int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }

    private void initOfficeHours(int initStartHour, int initEndHour) {
        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
        startHour = initStartHour;
        endHour = initEndHour;

        // EMPTY THE CURRENT OFFICE HOURS VALUES
        officeHours.clear();

        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
        // STRUCTURE AS WE GO
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        workspaceComponent.getTAWorkspace().reloadOfficeHoursGrid(this);
    }

    public void initHours(String startHourText, String endHourText) {
        int initStartHour = Integer.parseInt(startHourText);
        int initEndHour = Integer.parseInt(endHourText);
        /*if ((initStartHour >= MIN_START_HOUR)
                && (initEndHour <= MAX_END_HOUR)
                && (initStartHour <= initEndHour)) {
            // THESE ARE VALID HOURS SO KEEP THEM
            initOfficeHours(initStartHour, initEndHour);
        }
         */
        initOfficeHours(initStartHour, initEndHour);
    }

    public boolean containsTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName) || ta.getEmail().equals(testName)) {
                return true;
            }
        }
        return false;
    }

    public void addTA(String initName, String initEmail, Boolean check) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, check);
       
        // ADD THE TA
        if (!containsTA(initName)) {
            teachingAssistants.add(ta);

        }
       
        
        // SORT THE TAS
        Collections.sort(teachingAssistants);

    }

    //This method updates the name of Ta in office hours grid table
    public void editTA(String cellKey, String taName, String newName) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        if (cellText.contains(taName)) {
            cellText = cellText.replaceAll(taName, newName);
            cellProp.setValue(cellText);

        }

    }

    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName);
    }

    /**
     * This function toggles the taName in the cell represented by cellKey.
     * Toggle means if it's there it removes it, if it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName) {
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

    public void toggleTAOfficeHourUpdate(String cellKey, String taName) {
        StringProperty cellProp = officeHours.get(cellKey);
        if (cellProp != null) {
            String cellText = cellProp.getValue();

            if (cellText.contains(taName)) {
                removeTAFromCell(cellProp, taName);
            } else {
                cellProp.setValue(cellText + taName);
            }
        }

    }

    private int getStartRow(int newStart) {
        int row = 1;

        row += Math.abs(newStart - startHour) * 2;

        return row;
    }

    private int getEndRow(int newEnd, int newStart) {
        int row = 1;
        if (startHour < newStart) {
            row += (Math.abs(startHour - newEnd)) * 2;
        } else if (newEnd < endHour) {
            row += (Math.abs(newStart - endHour)) * 2;
        } else {
            row += (Math.abs(newStart - newEnd)) * 2;
        }
        return row;
    }

    //UPDATE THE OFFICE HOURS
    public void OfficeHoursUpdate(String start, String end, boolean flag) throws IOException {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        officeHours2 = new HashMap();
        boolean valid = true;
        if (flag) {
            if (Integer.parseInt(start) > startHour) {
                valid = validateTACells(start, end, 1);
            } else if (Integer.parseInt(end) < endHour) {
                valid = validateTACells(start, end, 2);
            } else if (Integer.parseInt(start) > startHour && Integer.parseInt(end) < endHour) {
                valid = validateTACells(start, end, 1);
                if (!valid) {
                    valid = validateTACells(start, end, 2);
                }
            }
        }
        flag = true;

        if (valid) {
            int startRow, i = 0;
            if (Integer.parseInt(start) < startHour) {
                startRow = 1;
                i = getStartRow(Integer.parseInt(start)) - 1;
            } else {
                startRow = getStartRow(Integer.parseInt(start));
                i = startRow - 1;
            }
            int endRow = getEndRow(Integer.parseInt(end), Integer.parseInt(start));
            int temp = startRow;

            for (; startRow < endRow; startRow++) {
                for (int col = 2; col < 7; col++) {
                    String cellKey = workspace.getTAWorkspace().buildCellKey(col, startRow);
                    StringProperty cellProp = officeHours.get(workspace.getTAWorkspace().buildCellKey(col, startRow));
                    officeHours2.put(cellKey, cellProp);
                }
            }

            workspace.getTAWorkspace().resetWorkspace();
            int oldStart = startHour;
            initHours(start, end);
            startRow = temp;
            for (; startRow < endRow; startRow++) {
                for (int col = 2; col < 7; col++) {
                    try {
                        StringProperty cellProp = officeHours2.get(workspace.getTAWorkspace().buildCellKey(col, startRow));

                        String taName = cellProp.getValue();
                        if (Integer.parseInt(start) < oldStart) {
                            toggleTAOfficeHourUpdate(workspace.getTAWorkspace().buildCellKey(col, startRow + i), taName);
                        } else {
                            toggleTAOfficeHourUpdate(workspace.getTAWorkspace().buildCellKey(col, startRow - i), taName);
                        }

                    } catch (NullPointerException e) {

                    }
                }
            }
        }

    }

    private boolean validateTACells(String start, String end, int flag) {
        boolean valid = true;
        try {
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            int startRow = getStartRow(Integer.parseInt(start));
            int endRow = getEndRow(Integer.parseInt(end), Integer.parseInt(start));
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();

            if (flag == 1) {
                for (int row = 1; row < startRow; row++) {
                    for (int col = 2; col < 7; col++) {
                        if (!(officeHours.get(workspace.getTAWorkspace().buildCellKey(col, row)).getValue().equals(""))) {
                            yesNoDialog.show(props.getProperty(OFFICE_HOUR_UPDATE_TITLE), props.getProperty(OFFICE_HOUR_UPDATE_MESSAGE));
                            String selection = yesNoDialog.getSelection();
                            if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
                                return true;
                            } else {
                                return false;
                            }

                        }

                    }
                }
            } else {
                for (int row = startRow; row < endRow; row++) {
                    for (int col = 2; col < 7; col++) {
                        if (!(officeHours.get(workspace.getTAWorkspace().buildCellKey(col, row)).getValue().equals(""))) {
                            yesNoDialog.show(props.getProperty(OFFICE_HOUR_UPDATE_TITLE), props.getProperty(OFFICE_HOUR_UPDATE_MESSAGE));
                            String selection = yesNoDialog.getSelection();
                            if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
                                return true;

                            } else {
                                return false;
                            }
                        }

                    }
                }
            }

        } catch (NullPointerException e) {
            return valid;
        }
        return valid;
    }
    //REMOVE TA FROM THE TABLE BY PRESSING DELETE KEY

    public void removeTaFromTable(String cellKey, String taName) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView taTable = workspace.getTAWorkspace().getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant) selectedItem;
        TAController controller = new TAController(app);
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        if (cellText.contains(taName)) {
            removeTAFromCell(cellProp, taName);
            //jTPS_Transaction remove = new RemoveTA_Transaction(cellKey, taName, officeHours, teachingAssistants,ta,false);
            //controller.getjTPS().addTransaction(remove);
        }

    }

    /**
     * This method removes taName from the office grid cell represented by
     * cellProp.
     *
     * @param cellProp
     * @param taName
     */
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
