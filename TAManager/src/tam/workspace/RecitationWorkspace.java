/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.DELETE_ICON;
import static djf.settings.AppPropertyType.DELETE_TOOLTIP;
import static djf.settings.AppPropertyType.EXPORT_ICON;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppGUI;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.TAManagerProp;
import tam.data.CSGData;
import tam.data.TeachingAssistant;
import tam.data.RecitationItems;
import static tam.style.TAStyle.CLASS_ADD_TA_BUTTON;
import static tam.style.TAStyle.CLASS_CLEAR_TA_BUTTON;
import static tam.style.TAStyle.CLASS_UPDATE_TA_BUTTON;
import static tam.style.TAStyle.RECITATION__ADD_BUTTON_TEXT;
import static tam.style.TAStyle.RECITATION__CLEAR_BUTTON_TEXT;

/**
 *
 * @author Bilal
 */
public class RecitationWorkspace {

    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS
    TAManagerApp app;
    private AppGUI gui;
    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    RecitationsController controller;
    VBox recitationBox, topPane, bottomPane, add_editBox;
    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    // FOR THE HEADER ON THE LEFT
    HBox reciationsHeaderBox, sectionLabelBox, instructorLabelBox, dateLabelBox, LocationLabelBox, taLabelBox, ta2LabelBox, addBox;
    Label reciationsHeaderLabel, add_editLabel, editLabel, section_label, instructor_label, location_label, date_label, ta_label, ta2_label;
    ScrollPane scroll;
    Button addRecitationButton, editRecitationButton, clearButton, deleteButton;

    TextField sectionText, instructorText, dateText, locationText;

    ComboBox taCombo, ta2Combo;

    String ta1 = "", ta2 = "";
    private static boolean flag = true;
    TableView<RecitationItems> recitationTable;
    TableColumn<RecitationItems, String> sectionColumn;
    TableColumn<RecitationItems, String> instructorColumn;
    TableColumn<RecitationItems, String> dateColumn;
    TableColumn<RecitationItems, String> locationColumn;
    TableColumn<RecitationItems, String> taNameColumn;
    TableColumn<RecitationItems, String> ta2NameColumn;

    public RecitationWorkspace(TAManagerApp initApp) {
        app = initApp;
        gui = app.getGUI();
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        recitationBox = new VBox();
        reciationsHeaderBox = new HBox(10);
        add_editBox = new VBox();
        sectionLabelBox = new HBox(245);
        instructorLabelBox = new HBox(210);
        dateLabelBox = new HBox(235);
        LocationLabelBox = new HBox(235);
        taLabelBox = new HBox(165);
        ta2LabelBox = new HBox(165);
        addBox = new HBox(20);

        sectionText = new TextField();
        instructorText = new TextField();
        dateText = new TextField();
        locationText = new TextField();
        taCombo = new ComboBox();
        ta2Combo = new ComboBox();

        sectionText.prefWidthProperty().bind(add_editBox.widthProperty().multiply(.3));
        instructorText.prefWidthProperty().bind(add_editBox.widthProperty().multiply(.3));
        dateText.prefWidthProperty().bind(add_editBox.widthProperty().multiply(.3));
        locationText.prefWidthProperty().bind(add_editBox.widthProperty().multiply(.3));
        taCombo.prefWidthProperty().bind(add_editBox.widthProperty().multiply(.3));
        ta2Combo.prefWidthProperty().bind(add_editBox.widthProperty().multiply(.3));

        //HEADERS
        String reciationsHeaderLabelText = props.getProperty(TAManagerProp.RECITATION_HEADER_TEXT.toString());

        //COLUMNS
        String sectionColText = props.getProperty(TAManagerProp.SECTION_COLUMN_TEXT.toString());
        String instructorColText = props.getProperty(TAManagerProp.INSTRUCTOR_COLUMN_TEXT.toString());
        String dateColText = props.getProperty(TAManagerProp.DAY_TIME_COLUMN_TEXT.toString());
        String locationColText = props.getProperty(TAManagerProp.LOCATION_COLUMN_TEXT.toString());
        String taNameColText = props.getProperty(TAManagerProp.TA_NAME_COLUMN_TEXT.toString());

        //LABELS
        String add_editLabelText = props.getProperty(TAManagerProp.RECITATION_ADD_HEADER_TEXT.toString());
        String editLabelText = props.getProperty(TAManagerProp.RECITATION_EDIT_HEADER_TEXT.toString());
        String section_labelText = props.getProperty(TAManagerProp.SECTION_LABEL_TEXT.toString());
        String instructor_labelText = props.getProperty(TAManagerProp.INSTRUCTOR_LABEL_TEXT.toString());
        String location_labelText = props.getProperty(TAManagerProp.LOCATION_LABEL_TEXT.toString());
        String date_labelText = props.getProperty(TAManagerProp.DAY_TIME_LABEL_TEXT.toString());
        String ta_labelText = props.getProperty(TAManagerProp.TA_LABEL_TEXT.toString());

        //TEXTFIELD
        String dateTextField = props.getProperty(TAManagerProp.DAY_TIME_PROMPT_TEXT.toString());
        String sectionTextField = props.getProperty(TAManagerProp.SECTION_PROMPT_TEXT.toString());
        String instructorTextField = props.getProperty(TAManagerProp.INSTRUCTOR_PROMPT_TEXT.toString());
        String locationTextField = props.getProperty(TAManagerProp.LOCATION_PROMPT_TEXT.toString());

        //BUTTONS
        String addRecitation = props.getProperty(TAManagerProp.RECITATION__ADD_BUTTON_TEXT.toString());
        String editRecitation = props.getProperty(TAManagerProp.RECITATION_UPDATE_BUTTON_TEXT.toString());
        String clear = props.getProperty(TAManagerProp.RECITATION__CLEAR_BUTTON_TEXT.toString());
        String taNameTextField = props.getProperty(TAManagerProp.RECITATION_TA_NAME_COMBO_TEXT.toString());
        String deleteIcon = DELETE_ICON.toString();
        String tooltip = DELETE_TOOLTIP.toString();
        //LOAD DELETE ICON
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(deleteIcon);
        Image buttonImage = new Image(imagePath);
        deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        deleteButton.setTooltip(buttonTooltip);
        reciationsHeaderLabel = new Label(reciationsHeaderLabelText);
        reciationsHeaderBox.getChildren().addAll(reciationsHeaderLabel, deleteButton);

        add_editLabel = new Label(add_editLabelText);
        editLabel = new Label(editLabelText);
        section_label = new Label(section_labelText);
        instructor_label = new Label(instructor_labelText);
        location_label = new Label(location_labelText);
        date_label = new Label(date_labelText);
        ta_label = new Label(ta_labelText);
        ta2_label = new Label(ta_labelText);
        recitationTable = new TableView();
        recitationTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<RecitationItems> recitationData = data.getRecitationData().getRecitationTable();
        recitationTable.setItems(recitationData);

        sectionColumn = new TableColumn(sectionColText);
        instructorColumn = new TableColumn(instructorColText);
        dateColumn = new TableColumn(dateColText);
        locationColumn = new TableColumn(locationColText);
        taNameColumn = new TableColumn(taNameColText);
        ta2NameColumn = new TableColumn(taNameColText);
        sectionText.setPromptText(sectionTextField);
        instructorText.setPromptText(instructorTextField);
        dateText.setPromptText(dateTextField);
        locationText.setPromptText(locationTextField);
        taCombo.setPromptText(taNameTextField);
        ta2Combo.setPromptText(taNameTextField);
        addRecitationButton = new Button(addRecitation);
        editRecitationButton = new Button(editRecitation);
        clearButton = new Button(clear);
        addRecitationButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.1));
        editRecitationButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.1));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.1));

        sectionColumn.setCellValueFactory(
                new PropertyValueFactory<RecitationItems, String>("sections")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<RecitationItems, String>("date")
        );
        locationColumn.setCellValueFactory(
                new PropertyValueFactory<RecitationItems, String>("location")
        );
        instructorColumn.setCellValueFactory(
                new PropertyValueFactory<RecitationItems, String>("instructor")
        );

        taNameColumn.setCellValueFactory(
                new PropertyValueFactory<RecitationItems, String>("ta1")
        );

        ta2NameColumn.setCellValueFactory(
                new PropertyValueFactory<RecitationItems, String>("ta2")
        );

        sectionColumn.setMinWidth(135);
        dateColumn.setMinWidth(370);
        locationColumn.setMinWidth(370);
        instructorColumn.setMinWidth(255);
        taNameColumn.setMinWidth(255);
        ta2NameColumn.setMinWidth(255);

        recitationTable.getColumns().addAll(sectionColumn, instructorColumn, dateColumn, locationColumn, taNameColumn, ta2NameColumn);

        topPane = new VBox();
        bottomPane = new VBox(80);
        topPane.getChildren().add(reciationsHeaderBox);
        topPane.getChildren().add(recitationTable);

        sectionLabelBox.getChildren().addAll(section_label, sectionText);
        instructorLabelBox.getChildren().addAll(instructor_label, instructorText);
        dateLabelBox.getChildren().addAll(date_label, dateText);
        LocationLabelBox.getChildren().addAll(location_label, locationText);
        taLabelBox.getChildren().addAll(ta_label, taCombo);
        ta2LabelBox.getChildren().addAll(ta2_label, ta2Combo);
        addBox.getChildren().addAll(addRecitationButton, clearButton);
        add_editBox.getChildren().addAll(add_editLabel, sectionLabelBox, instructorLabelBox, dateLabelBox, LocationLabelBox, taLabelBox, ta2LabelBox, addBox);
        bottomPane.getChildren().addAll(add_editBox, sectionLabelBox, instructorLabelBox, dateLabelBox, LocationLabelBox, taLabelBox, ta2LabelBox, addBox);
        // SplitPane sPane = new SplitPane(topPane, bottomPane);
        // sPane.prefHeightProperty().bind(recitationBox.heightProperty().multiply(1));     
        //recitationTable.prefWidthProperty().bind(topPane.widthProperty().multiply(.4));
        recitationBox.getChildren().addAll(topPane, bottomPane);
        scroll = new ScrollPane(recitationBox);
        recitationTable.prefHeightProperty().bind(recitationBox.heightProperty().multiply(.7));
        bottomPane.prefHeightProperty().bind(recitationBox.heightProperty().multiply(.3));

        //CONTROLLER STUFF GOES HERE
        controller = new RecitationsController(app);

        // CONTROLS FOR ADDING RECITATIONS
        taCombo.setOnAction(e -> {
            ta1 = taCombo.getValue().toString();

        });

        ta2Combo.setOnAction(e -> {
            ta2 = ta2Combo.getValue().toString();

        });

        addRecitationButton.setOnAction(e -> {
            controller.handleAddReciations();

            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();

        });

        //CONTROLS FOR UPDATING RECTITATION
        recitationTable.setRowFactory(tv -> {

            TableRow<RecitationItems> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (flag) {
                    add_editLabel.setText(editLabelText);
                    addBox.getChildren().remove(addRecitationButton);
                    addBox.getChildren().remove(clearButton);
                    addBox.getChildren().add(editRecitationButton);
                    addBox.getChildren().add(clearButton);
                    clearButton.setDisable(false);
                    flag = false;
                }
                RecitationItems clickedRow = row.getItem();
                sectionText.setText(clickedRow.getSections());
                instructorText.setText(clickedRow.getInstructor());
                dateText.setText(clickedRow.getDate());
                locationText.setText(clickedRow.getLocation());
                taCombo.setValue(clickedRow.getTa1());
                ta2Combo.setValue(clickedRow.getTa2());
                editRecitationButton.setDisable(false);
                getUpdateButton().getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
                getClearButton().getStyleClass().add(CLASS_CLEAR_TA_BUTTON);
                editRecitationButton.setOnAction(e -> {
                    controller.handleEditRecitation();
                    AppFileController fileControl = new AppFileController(app);
                    fileControl.markFileAsNotSaved();
                    app.getGUI().updateToolbarControls(false);

                });
            });

            return row;

        });

        //CONTROLS FOR CLEAR FIELDS
        clearButton.setOnAction(e -> {
            if (!flag) {
                add_editLabel.setText(add_editLabelText);
                addBox.getChildren().remove(editRecitationButton);
                addBox.getChildren().remove(clearButton);
                addBox.getChildren().add(addRecitationButton);
                addBox.getChildren().add(clearButton);
                clearButton.setDisable(true);
                flag = true;
            }
            getUpdateButton().getStyleClass().add(RECITATION__ADD_BUTTON_TEXT);
            getClearButton().getStyleClass().add(RECITATION__ADD_BUTTON_TEXT);

            recitationTable.getSelectionModel().clearSelection();
            instructorText.setText("");
            dateText.setText("");
            locationText.setText("");
            sectionText.setText("");
            taCombo.setValue(null);
            ta2Combo.setValue(null);

            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            sectionText.requestFocus();

        });

    }

    public String getTA1() {
        return ta1;
    }

    public String getTA2() {
        return ta2;
    }

    public TableView getRecitationTable() {
        return recitationTable;
    }

    public HBox getRecitationHeaderBox() {
        return reciationsHeaderBox;
    }

    public VBox getAdd_Edit_HeaderBox() {
        return add_editBox;
    }

    public VBox getTopPane() {
        return topPane;
    }

    public VBox getBottomPane() {
        return bottomPane;
    }

    public Label getRecitationHeaderLabel() {
        return reciationsHeaderLabel;
    }

    public Label getAdd_Edit_HeaderLabel() {
        return add_editLabel;
    }

    public Label getRecitationSectionLabel() {
        return section_label;
    }

    public Label getRecitationInstructorLabel() {
        return instructor_label;
    }

    public Label getRecitationDateLabel() {
        return date_label;
    }

    public Label getRecitationLocationLabel() {
        return location_label;
    }

    public Label getRecitationTALabel() {
        return ta_label;
    }

    public Label getRecitationTA2Label() {
        return ta2_label;
    }

    public ComboBox getTAName() {
        return taCombo;
    }

    public ComboBox getTA2Name() {
        return ta2Combo;
    }

    public Button getAddButton() {
        return addRecitationButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getUpdateButton() {
        return editRecitationButton;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getSectionTextField() {
        return sectionText;
    }

    public TextField getLocationTextField() {
        return locationText;
    }

    public TextField getDateTextField() {
        return dateText;
    }

    public TextField getInstructorTextField() {
        return instructorText;
    }

    public void reloadWorkspace() {
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTAData().getTeachingAssistants();
        taCombo.getItems().clear();
        ta2Combo.getItems().clear();

        for (int i = 0; i < tableData.size(); i++) {
            if (tableData.get(i).getUndergrad()) {
                taCombo.getItems().add(tableData.get(i).getName());
                ta2Combo.getItems().add(tableData.get(i).getName());
            }
        }

        deleteButton.setOnAction(event -> {
            controller.handleRecitationDeletePress();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
                instructorText.setText("");
                dateText.setText("");
                locationText.setText("");
                sectionText.setText("");
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                String taNameTextField = props.getProperty(TAManagerProp.RECITATION_TA_NAME_COMBO_TEXT.toString());
                taCombo.setValue(taNameTextField);
                ta2Combo.setValue(taNameTextField);
        });

        recitationTable.setOnKeyPressed((event) -> {
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
                controller.handleRecitationDeletePress();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
                instructorText.setText("");
                dateText.setText("");
                locationText.setText("");
                sectionText.setText("");
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                String taNameTextField = props.getProperty(TAManagerProp.RECITATION_TA_NAME_COMBO_TEXT.toString());
                taCombo.setValue(taNameTextField);
                ta2Combo.setValue(taNameTextField);
            }
        });

    }

    public void resetWorkspace() {
        instructorText.setText("");
        dateText.setText("");
        locationText.setText("");
        sectionText.setText("");
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String taNameTextField = props.getProperty(TAManagerProp.RECITATION_TA_NAME_COMBO_TEXT.toString());
        taCombo.setValue(taNameTextField);
        ta2Combo.setValue(taNameTextField);
    }

}
