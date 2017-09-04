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
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
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
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.TAManagerProp;
import tam.data.CSGData;
import tam.data.ProjectStudentItems;
import tam.data.ProjectTeamItems;
import tam.data.RecitationItems;
import tam.data.ScheduleItems;
import static tam.style.TAStyle.CLASS_CLEAR_TA_BUTTON;
import static tam.style.TAStyle.CLASS_UPDATE_TA_BUTTON;

/**
 *
 * @author Bilal
 */
public class ProjectWorkspace {
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS

    TAManagerApp app;
    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    ProjectsController controller;

    //HEADERS
    VBox projectTabBox, topBox, bottomBox, add_editBoxHeaderTeams, add_editBoxHeaderStudents;
    HBox projectHeaderBox, teamsHeaderBox, teamNameLabelBox, teamColorBox, teamLinkBox, teamAddBox, studentsHeaderPane, studentFirstNameBox, studentLastNameBox,
            studentTeamBox, studentRoleBox, studentsAddBox;

    //TEXTFIELDS
    TextField teamNameText, teamLinkText, firstNameText, lastNameText, roleText;

    //COLOR PICKER
    ColorPicker teamColorPicker, teamTextColorPicker;

    //TABLEVIEW
    TableView<ProjectTeamItems> teamTable;
    TableView<ProjectStudentItems> studentsTable;
    //TABLECOLUMNS
    TableColumn<ProjectTeamItems, String> teamNameColumn;
    TableColumn<ProjectTeamItems, String> teamColorColumn;
    TableColumn<ProjectTeamItems, String> teamTextColorColumn;
    TableColumn<ProjectTeamItems, String> teamLinkColumn;
    TableColumn<ProjectStudentItems, String> firstNameColumn;
    TableColumn<ProjectStudentItems, String> lastNameColumn;
    TableColumn<ProjectStudentItems, String> teamColumn;
    TableColumn<ProjectStudentItems, String> roleColumn;
    //COMBOBOX
    ComboBox comboBox;

    //SCROLL PANE
    ScrollPane scroll;

    //LABELS
    Label projectsHeaderLabel, teamsHeaderLabel, add_editTeamHeaderLabel, add_editStudentHeaderLabel, teamNameLabel, teamColorLabel, teamTextColorLabel, teamLinkLabel, studentsHeaderLabel,
            firstNameLabel, lastNameLabel, teamLabel, roleLabel;

    //BUTTONS
    Button addButton, clearButton, updateButton, teamDeleteButton, studentDeleteButton, studentAddButton, studentClearButton, studentUpdateButton;
    String color, textColor, teamSelect;

    private static boolean flag = true, flag2 = true;

    public ProjectWorkspace(TAManagerApp initApp) {
        app = initApp;
        CSGData data = (CSGData) app.getDataComponent();
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INITIALIZE BOXES
        projectTabBox = new VBox(15);
        topBox = new VBox();
        bottomBox = new VBox();
        add_editBoxHeaderTeams = new VBox(60);
        add_editBoxHeaderStudents = new VBox(70);
        projectHeaderBox = new HBox();
        teamsHeaderBox = new HBox(10);
        teamNameLabelBox = new HBox(65);
        teamColorBox = new HBox();
        teamLinkBox = new HBox(65);
        teamAddBox = new HBox(20);
        studentsHeaderPane = new HBox(10);
        studentFirstNameBox = new HBox(65);
        studentLastNameBox = new HBox(77);
        studentTeamBox = new HBox(135);
        studentRoleBox = new HBox(135);
        studentsAddBox = new HBox(20);

        //STRING LABEL TEXT
        String projectsHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_HEADER_TEXT.toString());
        String teamsHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_TEAMS_HEADER_TEXT.toString());
        String add_editHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_ADD_EDIT_HEADER_TEXT.toString());
        String editHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_EDIT_HEADER_TEXT.toString());
        String student_add_editHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENT_ADD_EDIT_HEADER_TEXT.toString());
        String student_editHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENT_EDIT_HEADER_TEXT.toString());
        String teamNameLabelText = props.getProperty(TAManagerProp.PROJECT_NAME_LABEL_TEXT.toString());
        String teamColorLabelText = props.getProperty(TAManagerProp.PROJECT_COLOR_LABEL_TEXT.toString());
        String teamTextColorLabelText = props.getProperty(TAManagerProp.PROJECT_TEXT_COLOR_LABEL_TEXT.toString());
        String teamLinkLabelText = props.getProperty(TAManagerProp.PROJECT_LINK_LABEL_TEXT.toString());
        String teamNameColLabelText = props.getProperty(TAManagerProp.PROJECT_NAME_COLUMN_TEXT.toString());
        String teamColorColLabelText = props.getProperty(TAManagerProp.PROJECT_COLOR_COLUMN_TEXT.toString());
        String teamTextColorColLabelText = props.getProperty(TAManagerProp.PROJECT_TEXT_COLUMN_TEXT.toString());
        String teamLinkColLabelText = props.getProperty(TAManagerProp.PROJECT_LINK_COLUMN_TEXT.toString());
        String addButtonText = props.getProperty(TAManagerProp.PROJECT_ADD_BUTTON_TEXT.toString());
        String studentAddButtonText = props.getProperty(TAManagerProp.PROJECT_STUDENT_ADD_BUTTON_TEXT.toString());
        String updateButtonText = props.getProperty(TAManagerProp.PROJECT_UPDATE_BUTTON_TEXT.toString());
        String updateStudentButtonText = props.getProperty(TAManagerProp.PROJECT_STUDENT_UPDATE_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(TAManagerProp.PROJECT_CLEAR_BUTTON_TEXT.toString());
        String colorPickerText = props.getProperty(TAManagerProp.PROJECT_COLOR_PICKER_PROMPT_TEXT.toString());
        String teamNamePromptText = props.getProperty(TAManagerProp.PROJECT_TEAM_NAME_PROMPT_TEXT.toString());
        String linkPromptText = props.getProperty(TAManagerProp.PROJECT_TEAM_LINK_PROMPT_TEXT.toString());
        String studentHeaderLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_HEADER_TEXT.toString());
        String firstNameColLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_FIRST_NAME_COLUMN_TEXT.toString());
        String lastNameColLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_LAST_NAME_COLUMN_TEXT.toString());
        String teamColLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_TEAM_COLUMN_TEXT.toString());
        String roleColLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_ROLE_COLUMN_TEXT.toString());
        String firstNameLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_FIRST_NAME_TEXT.toString());
        String lastNameLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_LAST_NAME_TEXT.toString());
        String teamLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_TEAM_TEXT.toString());
        String roleLabelText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_ROLE_TEXT.toString());
        String firstNamePromptText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_FIRST_NAME_PROMPT_TEXT.toString());
        String lastNamePromptText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_LAST_NAME_PROMPT_TEXT.toString());
        String teamPromptText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_TEAM_PROMPT_TEXT.toString());
        String rolePromptText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_ROLE_PROMPT_TEXT.toString());

        //TEXTFIELDS
        teamNameText = new TextField();
        teamNameText.setPromptText(teamNamePromptText);
        teamLinkText = new TextField();
        teamLinkText.setPromptText(linkPromptText);
        teamNameText.prefWidthProperty().bind(teamNameLabelBox.widthProperty().multiply(.3));
        teamLinkText.prefWidthProperty().bind(teamLinkBox.widthProperty().multiply(.3));
        firstNameText = new TextField();
        firstNameText.setPromptText(firstNamePromptText);
        lastNameText = new TextField();
        lastNameText.setPromptText(lastNamePromptText);
        roleText = new TextField();
        roleText.setPromptText(rolePromptText);
        firstNameText.prefWidthProperty().bind(studentFirstNameBox.widthProperty().multiply(.3));
        lastNameText.prefWidthProperty().bind(studentTeamBox.widthProperty().multiply(.3));
        roleText.prefWidthProperty().bind(studentRoleBox.widthProperty().multiply(.3));

        //LABELS
        projectsHeaderLabel = new Label(projectsHeaderLabelText);
        teamsHeaderLabel = new Label(teamsHeaderLabelText);
        add_editTeamHeaderLabel = new Label(add_editHeaderLabelText);
        add_editStudentHeaderLabel = new Label(student_add_editHeaderLabelText);
        teamNameLabel = new Label(teamNameLabelText);
        teamColorLabel = new Label(teamColorLabelText);
        teamTextColorLabel = new Label(teamTextColorLabelText);
        teamLinkLabel = new Label(teamLinkLabelText);
        studentsHeaderLabel = new Label(studentHeaderLabelText);
        firstNameLabel = new Label(firstNameLabelText);
        lastNameLabel = new Label(lastNameLabelText);
        teamLabel = new Label(teamLabelText);
        roleLabel = new Label(roleLabelText);

        //COLOR PICKER
        teamColorPicker = new ColorPicker();
        teamTextColorPicker = new ColorPicker();
        teamColorPicker.setPromptText(colorPickerText);
        teamTextColorPicker.setPromptText(colorPickerText);

        //TABLEVIEW
        teamTable = new TableView();
        studentsTable = new TableView();
        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ProjectTeamItems> teamData = data.getProjectsData().getProjectsTeamTable();
        teamTable.setItems(teamData);
        studentsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<ProjectStudentItems> studentData = data.getProjectsData().getProjectsStudentTable();
        studentsTable.setItems(studentData);

        //TABLE COLUMNS
        teamNameColumn = new TableColumn(teamNameColLabelText);
        teamColorColumn = new TableColumn(teamColorColLabelText);
        teamTextColorColumn = new TableColumn(teamTextColorColLabelText);
        teamLinkColumn = new TableColumn(teamLinkColLabelText);
        firstNameColumn = new TableColumn(firstNameColLabelText);
        lastNameColumn = new TableColumn(lastNameColLabelText);
        teamColumn = new TableColumn(teamColLabelText);
        roleColumn = new TableColumn(roleColLabelText);

        teamNameColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectTeamItems, String>("teamName")
        );
        teamColorColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectTeamItems, String>("teamColor")
        );
        teamTextColorColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectTeamItems, String>("teamTextColor")
        );
        teamLinkColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectTeamItems, String>("teamLink")
        );

        teamNameColumn.setMinWidth(175);
        teamColorColumn.setMinWidth(135);
        teamTextColorColumn.setMinWidth(195);
        teamLinkColumn.setMinWidth(400);

        teamTable.getColumns().addAll(teamNameColumn, teamColorColumn, teamTextColorColumn, teamLinkColumn);

        firstNameColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectStudentItems, String>("firstName")
        );
        lastNameColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectStudentItems, String>("lastName")
        );
        teamColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectStudentItems, String>("team")
        );
        roleColumn.setCellValueFactory(
                new PropertyValueFactory<ProjectStudentItems, String>("role")
        );
        firstNameColumn.setMinWidth(155);
        lastNameColumn.setMinWidth(155);
        teamColumn.setMinWidth(290);
        roleColumn.setMinWidth(305);

        studentsTable.getColumns().addAll(firstNameColumn, lastNameColumn, teamColumn, roleColumn);

        //COMBOBOX
        comboBox = new ComboBox();
        comboBox.setPromptText(teamPromptText);
        comboBox.prefWidthProperty().bind(studentTeamBox.widthProperty().multiply(.3));

        //BUTTONS
        String deleteIcon = DELETE_ICON.toString();
        String tooltip = DELETE_TOOLTIP.toString();
        //LOAD DELETE ICON
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + props.getProperty(deleteIcon);
        Image buttonImage = new Image(imagePath);
        teamDeleteButton = new Button();
        teamDeleteButton.setGraphic(new ImageView(buttonImage));
        studentDeleteButton = new Button();
        studentDeleteButton.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(tooltip));
        teamDeleteButton.setTooltip(buttonTooltip);
        studentDeleteButton.setTooltip(buttonTooltip);
        addButton = new Button(addButtonText);
        updateButton = new Button(updateButtonText);
        studentUpdateButton = new Button(updateStudentButtonText);
        clearButton = new Button(clearButtonText);
        studentAddButton = new Button(studentAddButtonText);
        studentClearButton = new Button(clearButtonText);
        addButton.prefWidthProperty().bind(teamAddBox.widthProperty().multiply(.17));
        clearButton.prefWidthProperty().bind(teamAddBox.widthProperty().multiply(.17));
        studentAddButton.prefWidthProperty().bind(teamAddBox.widthProperty().multiply(.17));
        studentClearButton.prefWidthProperty().bind(teamAddBox.widthProperty().multiply(.17));
        updateButton.prefWidthProperty().bind(teamAddBox.widthProperty().multiply(.17));
        studentUpdateButton.prefWidthProperty().bind(teamAddBox.widthProperty().multiply(.17));
        //WRAP EVERYTHING
        //TOP SIDE
        projectHeaderBox.getChildren().add(projectsHeaderLabel);
        teamsHeaderBox.getChildren().addAll(teamsHeaderLabel, teamDeleteButton);
        teamNameLabelBox.getChildren().addAll(teamNameLabel, teamNameText);
        teamColorBox.getChildren().addAll(teamColorLabel, teamColorPicker, teamTextColorLabel, teamTextColorPicker);
        teamLinkBox.getChildren().addAll(teamLinkLabel, teamLinkText);
        teamAddBox.getChildren().addAll(addButton, clearButton);
        topBox.getChildren().addAll(projectHeaderBox, teamsHeaderBox, teamTable);
        add_editBoxHeaderTeams.getChildren().addAll(add_editTeamHeaderLabel, teamNameLabelBox, teamColorBox, teamLinkBox, teamAddBox);

        //BOTTOM SIDE
        studentsHeaderPane.getChildren().addAll(studentsHeaderLabel, studentDeleteButton);
        studentFirstNameBox.getChildren().addAll(firstNameLabel, firstNameText);
        studentLastNameBox.getChildren().addAll(lastNameLabel, lastNameText);
        studentTeamBox.getChildren().addAll(teamLabel, comboBox);
        studentRoleBox.getChildren().addAll(roleLabel, roleText);
        studentsAddBox.getChildren().addAll(studentAddButton, studentClearButton);
        add_editBoxHeaderStudents.getChildren().addAll(add_editStudentHeaderLabel, studentFirstNameBox, studentLastNameBox, studentTeamBox, studentRoleBox, studentsAddBox);

        bottomBox.getChildren().addAll(studentsHeaderPane, studentsTable, add_editBoxHeaderStudents);
        SplitPane sPane = new SplitPane(topBox, add_editBoxHeaderTeams);
        SplitPane sPane2 = new SplitPane(bottomBox, add_editBoxHeaderStudents);
        sPane.prefHeightProperty().bind(projectTabBox.heightProperty().multiply(.7));
        teamTable.prefHeightProperty().bind(sPane.heightProperty().multiply(1));
        studentsTable.prefHeightProperty().bind(sPane2.heightProperty().multiply(1));
        projectTabBox.getChildren().addAll(sPane, sPane2);
        scroll = new ScrollPane(projectTabBox);

        projectTabBox.prefWidthProperty().bind(scroll.widthProperty().multiply(1));

        //CONTROLLER STUFF GOES HERE
        controller = new ProjectsController(app);
        addButton.setOnAction(e -> {

            try {
                controller.handleAddTeam();
            } catch (IOException ex) {
                Logger.getLogger(ProjectWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }

            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);

        });
        // CONTROLS FOR ADDING SCHEDULE
        teamColorPicker.setOnAction(e -> {
            color = teamColorPicker.getValue().toString();

        });

        // CONTROLS FOR ADDING SCHEDULE
        teamTextColorPicker.setOnAction(e -> {
            textColor = teamTextColorPicker.getValue().toString();

        });

        studentAddButton.setOnAction(e -> {

            controller.handleAddStudents();

            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);

        });

        comboBox.setOnAction(e -> {
            teamSelect = comboBox.getValue().toString();

        });

        //CONTROLS FOR UPDATING TA
        teamTable.setRowFactory(tv -> {

            TableRow<ProjectTeamItems> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (flag) {
                    add_editTeamHeaderLabel.setText(editHeaderLabelText);
                    teamAddBox.getChildren().remove(addButton);
                    teamAddBox.getChildren().remove(clearButton);
                    teamAddBox.getChildren().add(updateButton);
                    teamAddBox.getChildren().add(clearButton);
                    clearButton.setDisable(false);
                    flag = false;
                }
                ProjectTeamItems clickedRow = row.getItem();
                String colorHex = clickedRow.getTeamColor();
                colorHex = colorHex.replace("#", "");
                int x = Integer.parseInt(colorHex, 16);
                java.awt.Color color = new java.awt.Color(x);
                int r, g, b;
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                Color c = Color.rgb(r, g, b);
                teamColorPicker.setValue(c);
                this.color = teamColorPicker.getValue().toString();
                colorHex = clickedRow.getTeamTextColor();
                colorHex = colorHex.replace("#", "");
                x = Integer.parseInt(colorHex, 16);
                color = new java.awt.Color(x);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();
                c = Color.rgb(r, g, b);
                teamTextColorPicker.setValue(c);
                textColor = teamTextColorPicker.getValue().toString();
                teamNameText.setText(clickedRow.getTeamName());
                teamLinkText.setText(clickedRow.getTeamLink());
                updateButton.setDisable(false);
                updateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
                clearButton.getStyleClass().add(CLASS_CLEAR_TA_BUTTON);
                updateButton.setOnAction(e -> {
                    try {
                        controller.handleEditProjectTeam();
                    } catch (IOException ex) {
                        Logger.getLogger(ProjectWorkspace.class.getName()).log(Level.SEVERE, null, ex);
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
                add_editTeamHeaderLabel.setText(add_editHeaderLabelText);
                teamAddBox.getChildren().remove(updateButton);
                teamAddBox.getChildren().remove(clearButton);
                teamAddBox.getChildren().add(addButton);
                teamAddBox.getChildren().add(clearButton);
                clearButton.setDisable(true);
                flag = true;
            }
            updateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
            clearButton.getStyleClass().add(CLASS_CLEAR_TA_BUTTON);

            teamTable.getSelectionModel().clearSelection();
            teamTextColorPicker.setValue(Color.WHITE);
            teamColorPicker.setValue(Color.WHITE);
            teamNameText.setText("");
            teamLinkText.setText("");
        });

        teamDeleteButton.setOnAction(event -> {
            controller.handleDeletePress();
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            teamTable.getSelectionModel().clearSelection();
            app.getGUI().updateToolbarControls(false);
            teamColorPicker.setValue(Color.WHITE);
            teamTextColorPicker.setValue(Color.WHITE);
            teamNameText.setText("");
            teamLinkText.setText("");
            firstNameText.setText("");
            lastNameText.setText("");
            roleText.setText("");

        });

        teamTable.setOnKeyPressed((event) -> {
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
                teamTable.getSelectionModel().clearSelection();
                app.getGUI().updateToolbarControls(false);
                teamColorPicker.setValue(Color.WHITE);
                teamTextColorPicker.setValue(Color.WHITE);
                teamNameText.setText("");
                teamLinkText.setText("");

            }
        });

        //CONTROLS FOR UPDATING TA
        studentsTable.setRowFactory(tv -> {

            TableRow<ProjectStudentItems> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (flag2) {
                    add_editStudentHeaderLabel.setText(student_editHeaderLabelText);
                    studentsAddBox.getChildren().remove(studentAddButton);
                    studentsAddBox.getChildren().remove(studentClearButton);
                    studentsAddBox.getChildren().add(studentUpdateButton);
                    studentsAddBox.getChildren().add(studentClearButton);
                    studentClearButton.setDisable(false);
                    flag2 = false;
                }
                ProjectStudentItems clickedRow = row.getItem();
                firstNameText.setText(clickedRow.getFirstName());
                lastNameText.setText(clickedRow.getLastName());
                roleText.setText(clickedRow.getRole());
                comboBox.setValue(clickedRow.getTeam());
                studentUpdateButton.setDisable(false);
                studentUpdateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
                studentClearButton.getStyleClass().add(CLASS_CLEAR_TA_BUTTON);
                studentUpdateButton.setOnAction(e -> {
                    controller.handleEditProjectStudent();
                    AppFileController fileControl = new AppFileController(app);
                    fileControl.markFileAsNotSaved();
                    app.getGUI().updateToolbarControls(false);

                });
            });

            return row;

        });

        //CONTROLS FOR CLEAR FIELDS
        studentClearButton.setOnAction(e -> {
            if (!flag2) {
                add_editStudentHeaderLabel.setText(student_add_editHeaderLabelText);
                studentsAddBox.getChildren().remove(studentUpdateButton);
                studentsAddBox.getChildren().remove(studentClearButton);
                studentsAddBox.getChildren().add(studentAddButton);
                studentsAddBox.getChildren().add(studentClearButton);
                studentClearButton.setDisable(true);
                flag2 = true;
            }
            studentUpdateButton.getStyleClass().add(CLASS_UPDATE_TA_BUTTON);
            studentClearButton.getStyleClass().add(CLASS_CLEAR_TA_BUTTON);

            comboBox.setValue(null);
            firstNameText.setText("");
            lastNameText.setText("");
            roleText.setText("");
        });

        studentDeleteButton.setOnAction(event -> {
            controller.handleDeleteStudentPress();
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            studentsTable.getSelectionModel().clearSelection();
            app.getGUI().updateToolbarControls(false);
            comboBox.setValue(null);
            firstNameText.setText("");
            lastNameText.setText("");
            roleText.setText("");

        });

        studentsTable.setOnKeyPressed((event) -> {
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
                controller.handleDeleteStudentPress();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                studentsTable.getSelectionModel().clearSelection();
                app.getGUI().updateToolbarControls(false);
                comboBox.setValue(null);
                firstNameText.setText("");
                lastNameText.setText("");
                roleText.setText("");

            }
        });
    }

    public TableView getProjectTeamTable() {
        return teamTable;
    }

    public TableView getStudentsTeamTable() {
        return studentsTable;
    }

    public HBox getprojectHeaderPane() {
        return projectHeaderBox;
    }

    public HBox getTeamsHeaderPane() {
        return teamsHeaderBox;
    }

    public HBox getTeamAddPane() {
        return teamAddBox;
    }

    public HBox getStudentAddPane() {
        return studentsAddBox;
    }

    public HBox getStudentHeaderPane() {
        return studentsHeaderPane;
    }

    public VBox getAdd_editBoxHeaderTeams() {
        return add_editBoxHeaderTeams;
    }

    public VBox getAdd_editBoxHeaderStudents() {
        return add_editBoxHeaderStudents;
    }

    public Label getprojectHeaderLabel() {
        return projectsHeaderLabel;
    }

    public Label getTeamsHeaderLabel() {
        return teamsHeaderLabel;
    }

    public Label getAdd_editTeamHeaderLabel() {
        return add_editTeamHeaderLabel;
    }

    public Label getAdd_editStudentHeaderLabel() {
        return add_editStudentHeaderLabel;
    }

    public Label getTeamNameLabel() {
        return teamNameLabel;
    }

    public Label getTeamColorLabel() {
        return teamColorLabel;
    }

    public Label getTeamTextColorLabel() {
        return teamTextColorLabel;
    }

    public Label getTeamLinkLabel() {
        return teamLinkLabel;
    }

    public Label getStudentHeaderLabel() {
        return studentsHeaderLabel;
    }

    public Label getStudentFirstNameLabelLabel() {
        return firstNameLabel;
    }

    public Label getStudentLastNameLabel() {
        return lastNameLabel;
    }

    public Label getStudentTeamlLabel() {
        return teamLabel;
    }

    public Label getStudentRolelLabel() {
        return roleLabel;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public Button getAddButton() {
        return addButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getStudentAddButton() {
        return studentAddButton;
    }

    public Button getStudentClearButton() {
        return studentClearButton;
    }

    public Button getDeleteButton() {
        return teamDeleteButton;
    }

    public Button getStudentDeleteButton() {
        return studentDeleteButton;
    }

    public ColorPicker getColorPicker() {
        return teamColorPicker;
    }

    public ColorPicker getTextColorPicker() {
        return teamTextColorPicker;
    }

    public void resetWorkspace() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        String colorPickerText = props.getProperty(TAManagerProp.PROJECT_COLOR_PICKER_PROMPT_TEXT.toString());
        String teamPromptText = props.getProperty(TAManagerProp.PROJECT_STUDENTS_TEAM_PROMPT_TEXT.toString());
        teamTable.getItems().clear();
        studentsTable.getItems().clear();
        teamColorPicker.setPromptText(colorPickerText);
        teamTextColorPicker.setPromptText(colorPickerText);
        comboBox.setValue(teamPromptText);
        teamNameText.setText("");
        teamLinkText.setText("");
        firstNameText.setText("");
        lastNameText.setText("");
        roleText.setText("");

    }

    public void reloadWorkspace() {
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<ProjectTeamItems> teamItems = data.getProjectsData().getProjectsTeamTable();
        comboBox.getItems().clear();

        for (int i = 0; i < teamItems.size(); i++) {
            comboBox.getItems().add(teamItems.get(i).getTeamName());

        }
    }
}
