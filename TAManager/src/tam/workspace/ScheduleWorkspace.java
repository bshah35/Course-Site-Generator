/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.controller.AppFileController;
import static djf.settings.AppPropertyType.DELETE_ICON;
import static djf.settings.AppPropertyType.DELETE_TOOLTIP;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppMessageDialogSingleton;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import static tam.TAManagerProp.BOUNDRIES_MESSAGE;
import static tam.TAManagerProp.MISSING_TITLE_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TITLE_NAME_TITLE;
import static tam.TAManagerProp.RECITATION__ADD_BUTTON_TEXT;
import static tam.TAManagerProp.WRONG_BOUNDRIES_MESSAGE;
import static tam.TAManagerProp.WRONG_BOUNDRIES_TITLE;
import tam.data.CSGData;
import tam.data.RecitationItems;
import tam.data.ScheduleItems;
import tam.data.TeachingAssistant;
import static tam.style.TAStyle.CLASS_CLEAR_TA_BUTTON;
import static tam.style.TAStyle.CLASS_UPDATE_TA_BUTTON;

/**
 *
 * @author Bilal
 */
public class ScheduleWorkspace {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS

    TAManagerApp app;
    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    ScheduleController controller;

    //HEADERS
    VBox topPane, bottomPane, calendarBoundriesHeaderBox, add_editBoxHeader;
    HBox scheduleHeaderBox, calendarBoundries, scheduleItemsHeaderBox, typeBox, dateBox, titleBox, topicBox, linkBox, criteriaBox, timeBox, addBox;

    //TEXTFIELDS
    TextField titleText, topicText, linkText, criteriaText, timeText;

    //COMBOBOX
    ComboBox comboBox;

    //SCROLL PANE
    ScrollPane scroll;

    private static boolean flag = true, flag2 = true, flag3 = true, flag4 = false;
    int count = 0;
    //TABLEVIEW
    TableView<ScheduleItems> scheduleTable;
    TableColumn<ScheduleItems, String> typeColumn;
    TableColumn<ScheduleItems, String> dateColumn;
    TableColumn<ScheduleItems, String> titleColumn;
    TableColumn<ScheduleItems, String> topicColumn;

    //DATE
    DatePicker startDatePicker, endDatePicker, datePicker;

    //LABELS
    Label scheduleHeaderLabel, calendarBoundriesHeaderLabel, calendarStartLabel, calendarEndLabel, scheduleItemsHeaderLabel, add_editHeaderLabel, timeLabel, typeLabel, dateLabel, titleLabel, topicLabel, linkLabel, criteriaLabel;

    //BUTTONS
    Button deleteButton, addButton, clearButton, updateButton;

    String type = "", date = "", startDate = "", endDate = "", oldStartDay, oldStartDate, oldEndDate;

    public ScheduleWorkspace(TAManagerApp initApp) {
        app = initApp;
        CSGData data = (CSGData) app.getDataComponent();
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        flag = true;
        // INITIALIZE BOXES 
        topPane = new VBox();
        bottomPane = new VBox();
        scheduleHeaderBox = new HBox();
        calendarBoundriesHeaderBox = new VBox();
        calendarBoundries = new HBox();
        scheduleItemsHeaderBox = new HBox(10);
        add_editBoxHeader = new VBox(40);
        typeBox = new HBox(180);
        dateBox = new HBox(190);
        titleBox = new HBox(178);
        timeBox = new HBox(189);
        topicBox = new HBox(176);
        linkBox = new HBox(188);
        criteriaBox = new HBox(140);
        addBox = new HBox(20);

        //STRING LABELTEXT
        String scheduleHeaderLabelText = props.getProperty(TAManagerProp.SCHEDULE_HEADER_TEXT.toString());
        String calendarBoundriesHeaderLabelText = props.getProperty(TAManagerProp.SCHEDULE_CALENDAR_HEADER_TEXT.toString());
        String calendarStartLabelText = props.getProperty(TAManagerProp.SCHEDULE_START_CALENDAR_TEXT.toString());
        String calendarEndLabelText = props.getProperty(TAManagerProp.SCHEDULE_END_CALENDAR_TEXT.toString());
        String startDateLabelText = props.getProperty(TAManagerProp.SCHEDULE_START_DATE_TEXT.toString());
        String endDateLabelText = props.getProperty(TAManagerProp.SCHEDULE_END_DATE_TEXT.toString());
        String scheduleItemsHeaderLabelText = props.getProperty(TAManagerProp.SCHEDULE_ITEMS_TEXT.toString());
        String typeColLabelText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COLUMN_TEXT.toString());
        String dateColLabelText = props.getProperty(TAManagerProp.SCHEDULE_DATE_COLUMN_TEXT.toString());
        String titleColLabelText = props.getProperty(TAManagerProp.SCHEDULE_TITLE_COLUMN_TEXT.toString());
        String topicColLabelText = props.getProperty(TAManagerProp.SCHEDULE_TOPIC_COLUMN_TEXT.toString());
        String add_editHeaderLabelText = props.getProperty(TAManagerProp.SCHEDULE_ADD_EDIT_HEADER_TEXT.toString());
        String editHeaderLabelText = props.getProperty(TAManagerProp.SCHEDULE_EDIT_HEADER_TEXT.toString());
        String topicLabelText = props.getProperty(TAManagerProp.SCHEDULE_TOPIC_LABEL_TEXT.toString());
        String dateLabelText = props.getProperty(TAManagerProp.SCHEDULE_DATE_LABEL_TEXT.toString());
        String titleLabelText = props.getProperty(TAManagerProp.SCHEDULE_TITLE_LABEL_TEXT.toString());
        String typeLabelText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_LABEL_TEXT.toString());
        String linkLabelText = props.getProperty(TAManagerProp.SCHEDULE_LINK_LABEL_TEXT.toString());
        String timeLabelText = props.getProperty(TAManagerProp.SCHEDULE_TIME_LABEL_TEXT.toString());
        String criteriaLabelText = props.getProperty(TAManagerProp.SCHEDULE_CRITERIA_LABEL_TEXT.toString());
        String addSchedule = props.getProperty(TAManagerProp.SCHEDULE_ADD_BUTTON_TEXT.toString());
        String editSchedule = props.getProperty(TAManagerProp.SCHEDULE_UPDATE_BUTTON_TEXT.toString());
        String clear = props.getProperty(TAManagerProp.SCHEDULE_CLEAR_BUTTON_TEXT.toString());
        String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
        String datePromptText = props.getProperty(TAManagerProp.SCHEDULE_DATE_PROMPT_TEXT.toString());
        String titlePromptText = props.getProperty(TAManagerProp.SCHEDULE_TITLE_PROMPT_TEXT.toString());
        String topicPromptText = props.getProperty(TAManagerProp.SCHEDULE_TOPIC_PROMPT_TEXT.toString());
        String linkPromptText = props.getProperty(TAManagerProp.SCHEDULE_LINK_PROMPT_TEXT.toString());
        String criteriaPromptText = props.getProperty(TAManagerProp.SCHEDULE_CRITERIA_PROMPT_TEXT.toString());

        //LABELS
        scheduleHeaderLabel = new Label(scheduleHeaderLabelText);
        calendarBoundriesHeaderLabel = new Label(calendarBoundriesHeaderLabelText);
        calendarStartLabel = new Label(calendarStartLabelText);
        calendarEndLabel = new Label(calendarEndLabelText);
        scheduleItemsHeaderLabel = new Label(scheduleItemsHeaderLabelText);
        add_editHeaderLabel = new Label(add_editHeaderLabelText);
        typeLabel = new Label(typeLabelText);
        dateLabel = new Label(dateLabelText);
        titleLabel = new Label(titleLabelText);
        timeLabel = new Label(timeLabelText);
        topicLabel = new Label(topicLabelText);
        linkLabel = new Label(linkLabelText);
        criteriaLabel = new Label(criteriaLabelText);

        //DATE PICKER 
        startDatePicker = new DatePicker();
        startDatePicker.setPromptText(startDateLabelText);
        endDatePicker = new DatePicker();
        endDatePicker.setPromptText(endDateLabelText);
        datePicker = new DatePicker();
        datePicker.setPromptText(datePromptText);

        //TABLEVIEW
        scheduleTable = new TableView();
        scheduleTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ScheduleItems> scheduleData = data.getScheduleData().getScheduleTable();
        scheduleTable.setItems(scheduleData);
        typeColumn = new TableColumn(typeColLabelText);
        dateColumn = new TableColumn(dateColLabelText);
        titleColumn = new TableColumn(titleColLabelText);
        topicColumn = new TableColumn(topicColLabelText);

        typeColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItems, String>("type")
        );
        dateColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItems, String>("date")
        );
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItems, String>("title")
        );
        topicColumn.setCellValueFactory(
                new PropertyValueFactory<ScheduleItems, String>("topic")
        );

        typeColumn.setMinWidth(135);
        dateColumn.setMinWidth(135);
        titleColumn.setMinWidth(270);
        topicColumn.setMinWidth(400);

        scheduleTable.getColumns().addAll(typeColumn, dateColumn, titleColumn, topicColumn);

        //TEXTFIELDS & COMBOBOX
        comboBox = new ComboBox();
        comboBox.setPromptText(comboBoxText);
        comboBox.prefWidthProperty().bind(topicBox.widthProperty().multiply(.2));
        comboBox.getItems().addAll("Lecture", "Recitation", "HW", "Holiday", "References");
        titleText = new TextField();
        timeText = new TextField();
        topicText = new TextField();
        linkText = new TextField();
        criteriaText = new TextField();
        titleText.setPromptText(titlePromptText);
        topicText.setPromptText(topicPromptText);
        linkText.setPromptText(linkPromptText);
        criteriaText.setPromptText(criteriaPromptText);
        titleText.prefWidthProperty().bind(titleBox.widthProperty().multiply(.6));
        timeText.prefWidthProperty().bind(timeBox.widthProperty().multiply(.6));
        topicText.prefWidthProperty().bind(topicBox.widthProperty().multiply(.6));
        linkText.prefWidthProperty().bind(linkBox.widthProperty().multiply(.6));
        criteriaText.prefWidthProperty().bind(criteriaBox.widthProperty().multiply(.6));

        //BUTTONS
        String deleteIcon = DELETE_ICON.toString();
        String tooltip = DELETE_TOOLTIP.toString();
        //LOAD DELETE ICON
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(deleteIcon);
        Image buttonImage = new Image(imagePath);
        deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        deleteButton.setTooltip(buttonTooltip);
        addButton = new Button(addSchedule);
        updateButton = new Button(editSchedule);
        clearButton = new Button(clear);
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.17));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.17));
        updateButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.17));

        //WRAP EVERYTHING
        scheduleHeaderBox.getChildren().addAll(scheduleHeaderLabel);
        calendarBoundries.getChildren().addAll(calendarStartLabel, startDatePicker, calendarEndLabel, endDatePicker);
        calendarBoundriesHeaderBox.getChildren().addAll(calendarBoundriesHeaderLabel, calendarBoundries);
        scheduleItemsHeaderBox.getChildren().addAll(scheduleItemsHeaderLabel, deleteButton);
        typeBox.getChildren().addAll(typeLabel, comboBox);
        dateBox.getChildren().addAll(dateLabel, datePicker);
        titleBox.getChildren().addAll(titleLabel, titleText);
        timeBox.getChildren().addAll(timeLabel, timeText);
        topicBox.getChildren().addAll(topicLabel, topicText);
        linkBox.getChildren().addAll(linkLabel, linkText);
        criteriaBox.getChildren().addAll(criteriaLabel, criteriaText);
        addBox.getChildren().addAll(addButton, clearButton);
        add_editBoxHeader.getChildren().addAll(add_editHeaderLabel, typeBox, dateBox, titleBox, timeBox, topicBox, linkBox, criteriaBox, addBox);
        topPane.getChildren().addAll(scheduleHeaderBox, calendarBoundriesHeaderBox);
        SplitPane split = new SplitPane(scheduleTable, add_editBoxHeader);
        bottomPane.getChildren().addAll(scheduleItemsHeaderBox, split);
        //scroll = new ScrollPane(bottomPane);
        scheduleTable.prefHeightProperty().bind(bottomPane.heightProperty().multiply(1));

        //CONTROLLER STUFF GOES HERE
        controller = new ScheduleController(app);

        // CONTROLS FOR ADDING SCHEDULE
        comboBox.setOnAction(e -> {
            type = comboBox.getValue().toString();

        });

        // CONTROLS FOR ADDING SCHEDULE
        datePicker.setOnAction(e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

            LocalDate d = datePicker.getValue();

            date = formatter.format(d);

        });

        startDatePicker.setOnAction(e -> {
            startDatePicker.setOnMouseClicked(event -> {
                flag2 = true;
            }
            );
            if (flag2) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                LocalDate d = startDatePicker.getValue();
                Calendar c = Calendar.getInstance();

                java.util.Date da = java.sql.Date.valueOf(d);
                c.setTime(da);

                int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                if (dayOfWeek != 2) {
                    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                    dialog.setMinWidth(400);
                    dialog.show(props.getProperty(WRONG_BOUNDRIES_TITLE), props.getProperty(WRONG_BOUNDRIES_MESSAGE));
                    String year = Integer.toString(data.getScheduleData().getYear());
                    String month = Integer.toString(data.getScheduleData().getStartMonth());
                    String day = Integer.toString(data.getScheduleData().getStartDay());
                    String date = "";
                    if (Integer.parseInt(month) < 10) {
                        month = "0" + month;
                    }
                    if (Integer.parseInt(day) < 10) {
                        day = "0" + day;
                    }
                    date = month + "/" + day + "/" + year;
                    d = LocalDate.parse(date, formatter);
                    startDatePicker.setValue(d);

                } else {
                    startDate = formatter.format(d);
                    flag4 = true;

                }
            }
        });

        endDatePicker.setOnAction(e -> {
            endDatePicker.setOnMouseClicked(event -> {
                flag3 = true;
                count = 0;
            }
            );
            if (flag3) {
                if (!flag4) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate d = endDatePicker.getValue();
                    if (count != 1) {
                        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                        dialog.setMinWidth(400);
                        dialog.show(props.getProperty(WRONG_BOUNDRIES_TITLE), props.getProperty(BOUNDRIES_MESSAGE));
                        String year = Integer.toString(data.getScheduleData().getYear());
                        String month = Integer.toString(data.getScheduleData().getEndMonth());
                        String day = Integer.toString(data.getScheduleData().getEndDay());

                        String date = "";
                        if (Integer.parseInt(month) < 10) {
                            month = "0" + month;
                        }
                        if (Integer.parseInt(day) < 10) {
                            day = "0" + day;
                        }
                        date = month + "/" + day + "/" + year;
                        d = LocalDate.parse(date, formatter);
                        count++;
                        endDatePicker.setValue(d);
                    }
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                    LocalDate d = endDatePicker.getValue();
                    Calendar c = Calendar.getInstance();

                    java.util.Date da = java.sql.Date.valueOf(d);
                    c.setTime(da);

                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    if (dayOfWeek != 6) {
                        AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                        dialog.setMinWidth(400);
                        dialog.show(props.getProperty(WRONG_BOUNDRIES_TITLE), props.getProperty(WRONG_BOUNDRIES_MESSAGE));
                        String year = Integer.toString(data.getScheduleData().getYear());
                        String month = Integer.toString(data.getScheduleData().getEndMonth());
                        String day = Integer.toString(data.getScheduleData().getEndDay());
                        String date = "";
                        if (Integer.parseInt(month) < 10) {
                            month = "0" + month;
                        }
                        if (Integer.parseInt(day) < 10) {
                            day = "0" + day;
                        }
                        date = month + "/" + day + "/" + year;
                        d = LocalDate.parse(date, formatter);
                        endDatePicker.setValue(d);

                    } else {
                        endDate = formatter.format(d);
                        controller.handleAddCalenderBoundries();
                        AppFileController fileControl = new AppFileController(app);
                        fileControl.markFileAsNotSaved();
                        app.getGUI().updateToolbarControls(false);

                        flag4 = false;

                    }
                }

            }
        });

        addButton.setOnAction(e -> {
            try {
                controller.handleAddSchedule();
            } catch (IOException ex) {
                Logger.getLogger(ScheduleWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }

            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);

        });

        //CONTROLS FOR UPDATING TA
        scheduleTable.setRowFactory(tv -> {

            TableRow<ScheduleItems> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (flag) {
                    add_editHeaderLabel.setText(editHeaderLabelText);
                    addBox.getChildren().remove(addButton);
                    addBox.getChildren().remove(clearButton);
                    addBox.getChildren().add(updateButton);
                    addBox.getChildren().add(clearButton);
                    clearButton.setDisable(false);
                    flag = false;
                }
                ScheduleItems clickedRow = row.getItem();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                date = clickedRow.getDate();
                LocalDate d = LocalDate.parse(date, formatter);
                datePicker.setValue(d);
                comboBox.setValue(clickedRow.getType());
                titleText.setText(clickedRow.getTitle());
                timeText.setText(clickedRow.getTime());
                topicText.setText(clickedRow.getTopic());
                criteriaText.setText(clickedRow.getCriteria());
                linkText.setText(clickedRow.getLink());
                updateButton.setDisable(false);
                updateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
                clearButton.getStyleClass().add(CLASS_CLEAR_TA_BUTTON);
                updateButton.setOnAction(e -> {
                    try {
                        controller.handleEditSchedule();
                    } catch (IOException ex) {
                        Logger.getLogger(ScheduleWorkspace.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
                add_editHeaderLabel.setText(add_editHeaderLabelText);
                addBox.getChildren().remove(updateButton);
                addBox.getChildren().remove(clearButton);
                addBox.getChildren().add(addButton);
                addBox.getChildren().add(clearButton);
                clearButton.setDisable(true);
                flag = true;
            }
            updateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
            clearButton.getStyleClass().add(CLASS_CLEAR_TA_BUTTON);

            scheduleTable.getSelectionModel().clearSelection();
            datePicker.setValue(null);
            comboBox.setValue(null);
            titleText.setText("");
            timeText.setText("");
            topicText.setText("");
            criteriaText.setText("");
            linkText.setText("");

        });

    }

    public void setFlag4(boolean flag) {
        flag4 = flag;
    }

    public void setFlag3(boolean flag) {
        flag3 = flag;
    }

    public void setFlag(boolean flag) {
        flag2 = flag;
    }

    public boolean getFlag3() {
        return flag3;
    }

    public boolean getFlag() {
        return flag2;
    }

    public DatePicker getStartDatePicker() {
        return startDatePicker;
    }

    public DatePicker getEndDatePicker() {
        return endDatePicker;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public TableView getScheduleTable() {
        return scheduleTable;
    }

    public VBox getCalendarBoundriesHeaderBox() {
        return calendarBoundriesHeaderBox;
    }

    public VBox getAdd_editBoxHeader() {
        return add_editBoxHeader;
    }

    public VBox getTopPane() {
        return topPane;
    }

    public VBox getBottomPane() {
        return bottomPane;
    }

    public HBox getScheduleHeaderBox() {
        return scheduleHeaderBox;
    }

    public HBox getScheduleItemsHeaderBox() {
        return scheduleItemsHeaderBox;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public Label getCalendarBoundriesHeaderLabel() {
        return calendarBoundriesHeaderLabel;
    }

    public Label getScheduleHeaderLabel() {
        return scheduleHeaderLabel;
    }

    public Label getCalendarStartLabel() {
        return calendarStartLabel;
    }

    public Label getCalendarEndLabel() {
        return calendarEndLabel;
    }

    public Label getScheduleItemsHeaderLabel() {
        return scheduleItemsHeaderLabel;
    }

    public Label getAdd_editHeaderLabel() {
        return add_editHeaderLabel;
    }

    public Label getTypeLabel() {
        return typeLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getTopicLabel() {
        return topicLabel;
    }

    public Label getLinkLabel() {
        return linkLabel;
    }

    public Label getCriteriaLabel() {
        return criteriaLabel;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public ComboBox getTopicCombo() {
        return comboBox;
    }

    public void resetWorkspace() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
        flag3 = false;
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        datePicker.setValue(null);
        comboBox.setValue(comboBoxText);
        titleText.setText("");
        timeText.setText("");
        topicText.setText("");
        criteriaText.setText("");
        linkText.setText("");

    }

    public void reloadWorkspace() {
        CSGData data = (CSGData) app.getDataComponent();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        flag3 = true;
        String year = Integer.toString(data.getScheduleData().getYear());
        String month = Integer.toString(data.getScheduleData().getStartMonth());
        String day = Integer.toString(data.getScheduleData().getStartDay());
        String date = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        date = month + "/" + day + "/" + year;
        LocalDate d = LocalDate.parse(date, formatter);
        startDatePicker.setValue(d);

        LocalDate b = startDatePicker.getValue();
        oldStartDate = formatter.format(b);

        year = Integer.toString(data.getScheduleData().getYear());
        month = Integer.toString(data.getScheduleData().getEndMonth());
        day = Integer.toString(data.getScheduleData().getEndDay());
        date = "";
        if (Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if (Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        date = month + "/" + day + "/" + year;
        d = LocalDate.parse(date, formatter);
        endDatePicker.setValue(d);

        b = endDatePicker.getValue();
        oldEndDate = formatter.format(b);

        deleteButton.setOnAction(event -> {
            controller.handleDeletePress();
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
            datePicker.setValue(null);
            comboBox.setValue(comboBoxText);
            titleText.setText("");
            timeText.setText("");
            topicText.setText("");
            criteriaText.setText("");
            linkText.setText("");

        });
        
  

        scheduleTable.setOnKeyPressed((event) -> {
            if ((event.getCode() == KeyCode.Z) && event.isControlDown()) {

                controller.handleUndo();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
                updateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);

            } else if ((event.getCode() == KeyCode.Y) && (event.isControlDown())) {

                controller.handleRedo();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);

            } else if (event.getCode() == KeyCode.DELETE) {
                controller.handleDeletePress();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
                PropertiesManager props = PropertiesManager.getPropertiesManager();
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                datePicker.setValue(null);
                comboBox.setValue(comboBoxText);
                titleText.setText("");
                timeText.setText("");
                topicText.setText("");
                criteriaText.setText("");
                linkText.setText("");
            }
        });
    }
}
