package tam.workspace;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.DELETE_ICON;
import static djf.settings.AppPropertyType.DELETE_TOOLTIP;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_ERROR_MESSAGE;
import static djf.settings.AppPropertyType.OFFICE_HOUR_UPDATE_ERROR_TITLE;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import tam.TAManagerApp;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.TAManagerProp;
import tam.data.AddTA_Transaction;
import tam.data.CSGData;
import tam.style.TAStyle;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.updateOfficeHoursTA_Transaction;
import tam.file.TAFiles;
import static tam.style.TAStyle.CLASS_ADD_TA_BUTTON;
import static tam.style.TAStyle.CLASS_CLEAR_TA_BUTTON;
import static tam.style.TAStyle.CLASS_UPDATE_TA_BUTTON;

/**
 * This class serves as the workspace component for the TA Manager application.
 * It provides all the user interface controls in the workspace area.
 *
 * Updated by: Bilal Shah
 */
public class TAWorkspace {

    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    TAManagerApp app;
    private AppGUI gui;
// THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TAController controller;
    VBox leftPane, rightPane;
    static int count;
    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    private static boolean flag = true;
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    TableColumn<TeachingAssistant, Boolean> checkCol;

    ComboBox startTime, endTime;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField, emailTextField;
    Button addButton, emailButton, upDateButton, clearButton, comboBoxButton, deleteButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;

    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;

    /**
     * The contstructor initializes the user interface, except for the full
     * office hours grid, since it doesn't yet know what the hours will be until
     * a file is loaded or a new one is created.
     */
    public TAWorkspace(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        gui = app.getGUI();

        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox(10);
        String tasHeaderText = props.getProperty(TAManagerProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(tasHeaderText);

        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTAData().getTeachingAssistants();
        taTable.setItems(tableData);
        String nameColumnText = props.getProperty(TAManagerProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(TAManagerProp.EMAIL_COLUMN_TEXT.toString());
        String undergradColumnText = props.getProperty(TAManagerProp.UNDERGRAD_COLLUMN_TEXT.toString());

        nameColumn = new TableColumn(nameColumnText);
        emailColumn = new TableColumn(emailColumnText);
        checkCol = new TableColumn(undergradColumnText);

        checkCol.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, Boolean>("undergrad"));

        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );

        checkCol.setCellFactory(column -> new CheckBoxTableCell());
        
        checkCol.setCellValueFactory(
                param -> param.getValue().isChecked(app)
                
        );
        

        taTable.setEditable(true);
        taTable.getColumns().add(checkCol);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        checkCol.setMinWidth(135);
        emailColumn.setMinWidth(450);
        nameColumn.setMinWidth(350);
        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(TAManagerProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(TAManagerProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(TAManagerProp.ADD_BUTTON_TEXT.toString());
        String updateButtonText = props.getProperty(TAManagerProp.UPDATE_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(TAManagerProp.CLEAR_BUTTON_TEXT.toString());
        String startTimeButtonText = props.getProperty(TAManagerProp.START_TIME_BUTTON_TEXT.toString());
        String endTimeButtonText = props.getProperty(TAManagerProp.END_TIME_BUTTON_TEXT.toString());
        String updateOfficeHoursButtonText = props.getProperty(TAManagerProp.UPDATE_OFFICE_HOURS_BUTTON_TEXT.toString());
        String deleteIcon = DELETE_ICON.toString();
        String tooltip = DELETE_TOOLTIP.toString();

        nameTextField = new TextField();
        emailTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        upDateButton = new Button(updateButtonText);
        clearButton = new Button(clearButtonText);
        comboBoxButton = new Button(updateOfficeHoursButtonText);
        //LOAD DELETE ICON
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(deleteIcon);
        Image buttonImage = new Image(imagePath);
        deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        deleteButton.setTooltip(buttonTooltip);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        upDateButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));

        tasHeaderBox.getChildren().addAll(tasHeaderLabel, deleteButton);

        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);
        clearButton.setDisable(true);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        startTime = new ComboBox();
        startTime.setPromptText(startTimeButtonText);
        endTime = new ComboBox();
        endTime.setPromptText(endTimeButtonText);

        String officeHoursGridText = props.getProperty(TAManagerProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(officeHoursGridText);
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        officeHoursHeaderBox.getChildren().add(startTime);
        officeHoursHeaderBox.getChildren().add(endTime);
        officeHoursHeaderBox.getChildren().add(comboBoxButton);
        officeHoursHeaderBox.setSpacing(14);
        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
        // officeHoursGridTimeHeaderPanes.p.bind(officeHoursGridPane.widthProperty().multiply(1));
        // ORGANIZE THE LEFT AND RIGHT PANES
        leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);
        leftPane.getChildren().add(taTable);
        leftPane.getChildren().add(addBox);
        rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);

        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));

        //workspace = new BorderPane();
        // AND PUT EVERYTHING IN THE WORKSPACE
        // ((BorderPane) workspace).setCenter(sPane);
        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        //taTable.prefHeightProperty().bind(workspace.heightProperty().multiply(1));
        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TAController(app);

        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();

        });

        emailTextField.setOnAction(e -> {
            controller.handleAddTA();

        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();

            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();

        });
        comboBoxButton.setDisable(true);

        comboBoxButton.setOnAction(e -> {

            String start = standardToMilitary(startTime.getValue().toString());
            String end = standardToMilitary(endTime.getValue().toString());
            if (Integer.parseInt(end) <= Integer.parseInt(start)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.show(props.getProperty(OFFICE_HOUR_UPDATE_ERROR_TITLE), props.getProperty(OFFICE_HOUR_UPDATE_ERROR_MESSAGE));
                comboBoxButton.setDisable(true);
            } else {
                try {
                    CSGData dataComponent = (CSGData) app.getDataComponent();
                    AppFileController fileControl = new AppFileController(app);
                    TAFiles file = (TAFiles) app.getFileComponent();
                    jTPS_Transaction updateHours = new updateOfficeHoursTA_Transaction(start, end, dataComponent.getTAData(), this);
                    controller.getjTPS().addTransaction(updateHours);
                    dataComponent.getTAData().OfficeHoursUpdate(start, end, true);
                } catch (IOException ex) {
                    Logger.getLogger(TAWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                }
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);

                startTime.setValue(startTimeButtonText);
                endTime.setValue(endTimeButtonText);
                comboBoxButton.setDisable(true);

            }

        });

        endTime.setOnAction(e -> {
            comboBoxButton.setDisable(false);
        });

        startTime.setOnAction(e -> {

            comboBoxButton.setDisable(false);
        });

        //CONTROLS FOR UPDATING TA
        taTable.setRowFactory(tv -> {

            TableRow<TeachingAssistant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (flag) {
                    addBox.getChildren().remove(addButton);
                    addBox.getChildren().remove(clearButton);
                    addBox.getChildren().add(upDateButton);
                    addBox.getChildren().add(clearButton);
                    clearButton.setDisable(false);
                    flag = false;
                }
                TeachingAssistant clickedRow = row.getItem();
                nameTextField.setText(clickedRow.getName());
                emailTextField.setText(clickedRow.getEmail());
                upDateButton.setDisable(false);
                getUpdateButton().getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
                getClearButton().getStyleClass().add(CLASS_CLEAR_TA_BUTTON);
                upDateButton.setOnAction(e -> {
                    controller.handleEditTA();
                    AppFileController fileControl = new AppFileController(app);
                    fileControl.markFileAsNotSaved();
                    app.getGUI().updateToolbarControls(false);

                });
            });

            return row;

        });

        for (int i = 0; i < 24; i++) {
            startTime.getItems().add(buildCellText(i, "00"));
        }

        for (int i = 0; i < 24; i++) {
            endTime.getItems().add(buildCellText(i, "00"));

        }
        //CONTROLS FOR CLEAR FIELDS
        clearButton.setOnAction(e -> {
            if (!flag) {
                addBox.getChildren().remove(upDateButton);
                addBox.getChildren().remove(clearButton);
                addBox.getChildren().add(addButton);
                addBox.getChildren().add(clearButton);
                clearButton.setDisable(true);
                flag = true;
            }
            getUpdateButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
            getClearButton().getStyleClass().add(CLASS_CLEAR_TA_BUTTON);

            taTable.getSelectionModel().clearSelection();
            nameTextField.setText("");
            emailTextField.setText("");

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();

        });
    }

    private String standardToMilitary(String time) {
        String s = "";
        int hour = Integer.parseInt(time.substring(0, time.indexOf(":")));

        if (hour == 12 && time.contains("pm")) {
            hour = 12;
        }
        if (hour == 12 && time.contains("am")) {
            hour = 0;
        }
        if (time.contains("pm") && hour != 12) {
            hour += 12;
        }
        s = Integer.toString(hour);
        return s;
    }

    // WE'LL PROVIDE AN ACCESSOR METHOD FOR EACH VISIBLE COMPONENT
    // IN CASE A CONTROLLER OR STYLE CLASS NEEDS TO CHANGE IT
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public VBox getLeftPane() {
        return leftPane;
    }

    public VBox getRightPane() {
        return rightPane;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getUpdateButton() {
        return upDateButton;
    }

    public Button getUpdateOfficeHoursButton() {
        return comboBoxButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public ComboBox getStartButton() {
        return startTime;
    }

    public ComboBox getEndButton() {
        return endTime;
    }

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }

    public String getCellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);

    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }

    public Pane getGridTimeCellPane(String cellPane) {
        return officeHoursGridTimeCellPanes.get(cellPane);
    }

    public Pane getGridDayHeaderCellPane(String cellPane) {
        return officeHoursGridDayHeaderPanes.get(cellPane);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour < 12) {
            hour += 12;
        }
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }

    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();

        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }

    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData taData = (CSGData) dataComponent;
        reloadOfficeHoursGrid(taData.getTAData());
    }

    public void reloadOfficeHoursGrid(TAData dataComponent) {
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }

        // THEN THE TIME AND TA CELLS
        int row = 1;
        //dataComponent.cc(start);
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {

            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));

            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));

            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row + 1);
            dataComponent.getCellTextProperty(col, row + 1).set(buildCellText(endHour + 1, "00"));

            col++;

            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row + 1);

                col++;

            }

            System.out.println(i);
            row += 2;

        }

        deleteButton.setOnAction(event -> {
            controller.handleTADeletePress();
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            nameTextField.setText("");
            emailTextField.setText("");
            upDateButton.setDisable(true);  
            clearButton.setDisable(false);
            app.getGUI().updateToolbarControls(false);

        });

        taTable.setOnKeyPressed((event) -> {
            if ((event.getCode() == KeyCode.Z) && event.isControlDown()) {

                controller.handleUndo();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
                getUpdateButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);

            } else if ((event.getCode() == KeyCode.Y) && (event.isControlDown())) {

                controller.handleredo();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);

            } else if (event.getCode() == KeyCode.DELETE) {
                controller.handleTADeletePress();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                nameTextField.setText("");
                emailTextField.setText("");
                upDateButton.setDisable(true); 
                clearButton.setDisable(false);
                app.getGUI().updateToolbarControls(false);
            }
        });

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
            });
        }
        // CONTROLS for Highlighitng TA
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseEntered(e -> {

                controller.handleCellHighlightMouseEnter((Pane) e.getSource());
                ;
            });
        }
        // CONTROLS FOR UnHighlighting TA
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseExited(e -> {

                controller.handleCellUnHighlightMouseExited((Pane) e.getSource());

            });
        }

        // AND MAKE SURE ALL THE COMPONENTS HAVE THE PROPER STYLE
        TAStyle taStyle = (TAStyle) app.getStyleComponent();
        taStyle.initOfficeHoursGridStyle();
    }

    public void addCellToGrid(TAData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);

        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);

        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);

        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());

    }
}
