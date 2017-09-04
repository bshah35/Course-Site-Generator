/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.controller.AppFileController;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.TAManagerProp;
import tam.data.CSGData;
import tam.data.CourseDetailsItems;
import tam.data.CourseInfoItem;
import tam.data.ProjectTeamItems;

/**
 *
 * @author Bilal
 */
public class CourseDetailsWorkspace {

    TAManagerApp app;
    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    CourseDetailsController controller;

    //HEADERS
    VBox topBox, siteTemplateBox, bottomBox, leftSidePane, sitePageBox, pageStyleBox, pageStyleHeaderPane, courseInfoBackgroundPane;
    HBox courseInfoBox, siteTemplateHeaderBox, subjectBox, semesterBox, titleBox, instructorNameBox, instructorHomeBox, exportDirBox, bannerSchoolImageBox,
            leftFooterImageBox, rightFooterImageBox, styleSheetBox, noteBox, siteTemplateDirBox;

    //TEXTFIELDS
    TextField titleText, instructorNameText, instructorHomeText;

    //LABELS
    Label courseInfoHeaderLabel, subjectLabel, numberLabel, semesterLabel, yearLabel, titleLabel, instructorNameLabel, instructorHomeLabel,
            exportDirLabel, exportDirSiteLabel, siteTemplateHeaderLabel, siteTemplateSentLabel, siteTempDirLabel, sitePagesHeaderLabel,
            pageStyleHeaderLabel, bannerSchoolImageLabel, leftFooterImageLabel, rightFooterImageLabel, styleSheetLabel, noteLabel;

    //TABLEVIEW
    TableView sitePageTabel;

    //TABLECOLUMNS
    TableColumn<CourseDetailsItems, Boolean> useCol;
    TableColumn<CourseDetailsItems, String> navBarCol;
    TableColumn<CourseDetailsItems, String> fileCol;
    TableColumn<CourseDetailsItems, String> scriptCol;

    //COMBOBOX
    ComboBox subjectCombo, yearCombo, semesterCombo, numberCombo, pageStyleCombo;

    //BUTTONS
    Button courseChangeButton, selectTemplateDirButton, pageStyleButton1, pageStyleButton2, pageStyleButton3;

    //SCROLL
    ScrollPane scroll;
    String subject, semester, number, year, imagePath1, imagePath2, imagePath3;

    CourseInfoItem courseInfo;
    ImageView imageview = new ImageView();
    ImageView imageview2 = new ImageView();
    ImageView imageview3 = new ImageView();

    public CourseDetailsWorkspace(TAManagerApp initApp) {
        app = initApp;
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        courseInfo = new CourseInfoItem();
        controller = new CourseDetailsController(app);

        CSGData data = (CSGData) app.getDataComponent();
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        // INITIALIZE BOXES
        topBox = new VBox();
        leftSidePane = new VBox(15);
        courseInfoBackgroundPane = new VBox(45);
        courseInfoBox = new HBox();
        bottomBox = new VBox();
        siteTemplateBox = new VBox(25);
        siteTemplateHeaderBox = new HBox();
        pageStyleBox = new VBox(85);
        pageStyleHeaderPane = new VBox();
        subjectBox = new HBox();
        semesterBox = new HBox();
        titleBox = new HBox(165);
        instructorNameBox = new HBox(60);
        instructorHomeBox = new HBox(60);
        exportDirBox = new HBox(30);
        sitePageBox = new VBox();
        bannerSchoolImageBox = new HBox(460);
        leftFooterImageBox = new HBox(470);
        rightFooterImageBox = new HBox(465);
        styleSheetBox = new HBox(1275);
        noteBox = new HBox();
        siteTemplateDirBox = new HBox(20);

        //STRING LABEL TEXT
        String courseInfoHeaderLabelText = props.getProperty(TAManagerProp.COURSE_INFO_HEADER_TEXT.toString());
        String subjectLabelText = props.getProperty(TAManagerProp.COURSE_SUBJECT_LABEL_TEXT.toString());
        String numberLabelText = props.getProperty(TAManagerProp.COURSE_NUMBER_LABEL_TEXT.toString());
        String semesterLabelText = props.getProperty(TAManagerProp.COURSE_SEMESTER_LABEL_TEXT.toString());
        String yearLabelText = props.getProperty(TAManagerProp.COURSE_YEAR_LABEL_TEXT.toString());
        String titleLabelText = props.getProperty(TAManagerProp.COURSE_TITLE_LABEL_TEXT.toString());
        String instructorNameLabelText = props.getProperty(TAManagerProp.COURSE_INSTRUCTOR_NAME_LABEL_TEXT.toString());
        String instructorHomeLabelText = props.getProperty(TAManagerProp.COURSE_INSTRUCTOR_HOME_LABEL_TEXT.toString());
        String exportDirText = props.getProperty(TAManagerProp.COURSE_EXPORT_DIR_LABEL_TEXT.toString());
        String exportDirSiteLabelText = props.getProperty(TAManagerProp.COURSE_EXPORT_DIR_SITE_LABEL_TEXT.toString());
        String changeButtonText = props.getProperty(TAManagerProp.COURSE_CHANGE_BUTTON_TEXT.toString());
        String siteTemplateHeaderLabelText = props.getProperty(TAManagerProp.COURSE_SITE_TEMPLATE_HEADER_TEXT.toString());
        String siteTemplateLabelText = props.getProperty(TAManagerProp.COURSE_SITE_LABEL_TEXT.toString());
        String siteTemplateDirLabelText = props.getProperty(TAManagerProp.COURSE_SITE_TEMPLATE_DIR_LABEL_TEXT.toString());
        String siteTemplateButtonText = props.getProperty(TAManagerProp.COURSE_TEMPLATE_BUTTON_TEXT.toString());
        String sitePagesHeaderLabelText = props.getProperty(TAManagerProp.COURSE_SITE_PAGES_HEADER_TEXT.toString());
        String useColText = props.getProperty(TAManagerProp.COURSE_USE_COLUMN_TEXT.toString());
        String navBarText = props.getProperty(TAManagerProp.COURSE_NAVBAR_TITLE_COLUMN_TEXT.toString());
        String fileNameText = props.getProperty(TAManagerProp.COURSE_FILE_NAME_COLUMN_TEXT.toString());
        String scriptText = props.getProperty(TAManagerProp.COURSE_SCRIPT_COLUMN_TEXT.toString());
        String subjectPromptText = props.getProperty(TAManagerProp.COURSE_SUBJECT_COMBO_PROMPT_TEXT.toString());
        String numberPromptText = props.getProperty(TAManagerProp.COURSE_NUMBER_COMBO_PROMPT_TEXT_TEXT.toString());
        String semesterPromptText = props.getProperty(TAManagerProp.COURSE_SEMESTER_COMBO_PROMPT_TEXT.toString());
        String yearPromptText = props.getProperty(TAManagerProp.COURSE_YEAR_COMBO_PROMPT_TEXT.toString());
        String titlePromptText = props.getProperty(TAManagerProp.COURSE_TITLE_PROMPT_TEXT.toString());
        String instructorNamePromptText = props.getProperty(TAManagerProp.COURSE_INSTRUCTOR_NAME_PROMPT_TEXT.toString());
        String instructorHomePromptText = props.getProperty(TAManagerProp.COURSE_INSTRUCTOR_HOME_PROMPT_TEXT.toString());
        String pageStyleHeaderLabelText = props.getProperty(TAManagerProp.COURSE_PAGE_STYLE_HEADER_LABEL_TEXT.toString());
        String bannerSchoolImageLabelText = props.getProperty(TAManagerProp.COURSE_BANNER_SCHOOL_IMAGE_TEXT.toString());
        String leftFooterImageLabelText = props.getProperty(TAManagerProp.COURSE_LEFT_FOOTER_IMAGE_TEXT.toString());
        String rightFooterImageLabelText = props.getProperty(TAManagerProp.COURSE_RIGHT_FOOTER_IMAGE_TEXT.toString());
        String styleSheetLabelText = props.getProperty(TAManagerProp.COURSE_STYLE_SHEET_TEXT.toString());
        String pageStyleComboLabelText = props.getProperty(TAManagerProp.COURSE_STYLE_SHEET_COMBO_PROMPT_TEXT.toString());
        String noteLabelText = props.getProperty(TAManagerProp.COURSE_NOTE_LABEL_TEXT.toString());

        //TEXTFIELDS
        titleText = new TextField();
        titleText.setPromptText(titlePromptText);
        titleText.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.3));
        instructorNameText = new TextField();
        instructorNameText.setPromptText(instructorNamePromptText);
        instructorNameText.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.3));
        instructorHomeText = new TextField();
        instructorHomeText.setPromptText(instructorHomePromptText);
        instructorHomeText.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.3));

        //LABELS
        courseInfoHeaderLabel = new Label(courseInfoHeaderLabelText);
        subjectLabel = new Label(subjectLabelText);
        numberLabel = new Label(numberLabelText);
        semesterLabel = new Label(semesterLabelText);
        yearLabel = new Label(yearLabelText);
        titleLabel = new Label(titleLabelText);
        instructorNameLabel = new Label(instructorNameLabelText);
        instructorHomeLabel = new Label(instructorHomeLabelText);
        exportDirLabel = new Label(exportDirText);
        exportDirSiteLabel = new Label(exportDirSiteLabelText);
        siteTemplateHeaderLabel = new Label(siteTemplateHeaderLabelText);
        siteTemplateSentLabel = new Label(siteTemplateLabelText);
        siteTempDirLabel = new Label(siteTemplateDirLabelText);
        sitePagesHeaderLabel = new Label(sitePagesHeaderLabelText);
        pageStyleHeaderLabel = new Label(pageStyleHeaderLabelText);
        bannerSchoolImageLabel = new Label(bannerSchoolImageLabelText);
        leftFooterImageLabel = new Label(leftFooterImageLabelText);
        rightFooterImageLabel = new Label(rightFooterImageLabelText);
        styleSheetLabel = new Label(styleSheetLabelText);
        noteLabel = new Label(noteLabelText);

        //TABLEVIEW
        sitePageTabel = new TableView();
        sitePageTabel.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<CourseDetailsItems> courseDetails = data.getCourseData().getCourseItem();
        sitePageTabel.setItems(courseDetails);

        //TABLECOLUMNS
        useCol = new TableColumn(useColText);
        navBarCol = new TableColumn(navBarText);
        scriptCol = new TableColumn(scriptText);
        fileCol = new TableColumn(fileNameText);

        useCol.setCellValueFactory(
                new PropertyValueFactory<CourseDetailsItems, Boolean>("use")
        );
        useCol.setCellFactory(column -> new CheckBoxTableCell());

        navBarCol.setCellValueFactory(
                new PropertyValueFactory<CourseDetailsItems, String>("navBar")
        );
        scriptCol.setCellValueFactory(
                new PropertyValueFactory<CourseDetailsItems, String>("script")
        );
        fileCol.setCellValueFactory(
                new PropertyValueFactory<CourseDetailsItems, String>("file")
        );

        useCol.setCellFactory(column -> new CheckBoxTableCell());
        useCol.setCellValueFactory(
                param -> param.getValue().isChecked()
        );

        sitePageTabel.setEditable(true);
        sitePageTabel.getColumns().addAll(useCol, navBarCol, fileCol, scriptCol);

        navBarCol.setMinWidth(150);
        fileCol.setMinWidth(290);
        scriptCol.setMinWidth(405);

        //COMBOBOX
        pageStyleCombo = new ComboBox();
        pageStyleCombo.setPromptText(pageStyleComboLabelText);
        pageStyleCombo.prefWidthProperty().bind(styleSheetBox.widthProperty().multiply(.1));
        subjectCombo = new ComboBox();
        subjectCombo.setPromptText(subjectPromptText);
        subjectCombo.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.18));
        yearCombo = new ComboBox();
        yearCombo.setPromptText(yearPromptText);
        yearCombo.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.18));
        numberCombo = new ComboBox();
        numberCombo.setPromptText(numberPromptText);
        numberCombo.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.18));
        semesterCombo = new ComboBox();
        semesterCombo.setPromptText(semesterPromptText);
        semesterCombo.prefWidthProperty().bind(courseInfoBackgroundPane.widthProperty().multiply(.18));

        //BUTTONS
        courseChangeButton = new Button(changeButtonText);
        courseChangeButton.prefWidthProperty().bind(exportDirBox.widthProperty().multiply(.1));
        selectTemplateDirButton = new Button(siteTemplateButtonText);
        selectTemplateDirButton.prefWidthProperty().bind(siteTemplateBox.widthProperty().multiply(.18));
        pageStyleButton1 = new Button(changeButtonText);
        pageStyleButton1.prefWidthProperty().bind(pageStyleBox.widthProperty().multiply(.1));
        pageStyleButton2 = new Button(changeButtonText);
        pageStyleButton2.prefWidthProperty().bind(pageStyleBox.widthProperty().multiply(.1));
        pageStyleButton3 = new Button(changeButtonText);
        pageStyleButton3.prefWidthProperty().bind(pageStyleBox.widthProperty().multiply(.1));

        //WRAP EVERYTHING
        //TOP  && MIDDLE SIDE
        courseInfoBox.getChildren().add(courseInfoHeaderLabel);
        subjectBox.getChildren().addAll(subjectLabel, subjectCombo, numberLabel, numberCombo);
        semesterBox.getChildren().addAll(semesterLabel, semesterCombo, yearLabel, yearCombo);
        titleBox.getChildren().addAll(titleLabel, titleText);
        instructorNameBox.getChildren().addAll(instructorNameLabel, instructorNameText);
        instructorHomeBox.getChildren().addAll(instructorHomeLabel, instructorHomeText);
        exportDirBox.getChildren().addAll(exportDirLabel, exportDirSiteLabel, courseChangeButton);
        courseInfoBackgroundPane.getChildren().addAll(subjectBox, semesterBox, titleBox, instructorNameBox, instructorHomeBox, exportDirBox);
        pageStyleHeaderPane.getChildren().add(pageStyleHeaderLabel);
        bannerSchoolImageBox.getChildren().addAll(bannerSchoolImageLabel, imageview, pageStyleButton1);
        leftFooterImageBox.getChildren().addAll(leftFooterImageLabel, imageview2, pageStyleButton2);
        rightFooterImageBox.getChildren().addAll(rightFooterImageLabel, imageview3, pageStyleButton3);
        styleSheetBox.getChildren().addAll(styleSheetLabel, pageStyleCombo);
        noteBox.getChildren().addAll(noteLabel);
        pageStyleBox.getChildren().addAll(bannerSchoolImageBox, leftFooterImageBox, rightFooterImageBox, styleSheetBox, noteBox);
        topBox.getChildren().addAll(courseInfoBox, courseInfoBackgroundPane);
        bottomBox.getChildren().addAll(pageStyleHeaderPane, pageStyleBox);
        leftSidePane.getChildren().addAll(topBox, siteTemplateBox, bottomBox);
        sitePageBox.getChildren().addAll(sitePagesHeaderLabel, sitePageTabel);
        siteTemplateHeaderBox.getChildren().add(siteTemplateHeaderLabel);
        siteTemplateDirBox.getChildren().addAll(siteTempDirLabel, selectTemplateDirButton);
        siteTemplateBox.getChildren().addAll(siteTemplateHeaderBox, siteTemplateSentLabel, siteTemplateDirBox, sitePageBox);
        // SplitPane sPane = new SplitPane(leftSidePane, );
        scroll = new ScrollPane(leftSidePane);
        // sPane.prefWidthProperty().bind(scroll.widthProperty().multiply(1));
        sitePageTabel.prefHeightProperty().bind(leftSidePane.heightProperty().multiply(.3));

        subjectCombo.getItems().addAll("CSE", "ESE");
        int year = Calendar.getInstance().get(Calendar.YEAR);
        yearCombo.getItems().addAll(Integer.toString(year - 1), Integer.toString(year), Integer.toString(year + 1));
        numberCombo.getItems().addAll("219", "308", "380");
        semesterCombo.getItems().addAll("Spring", "Summer", "Fall");

        subjectCombo.setOnAction(e -> {
            subject = (subjectCombo.getValue().toString());

        });

        semesterCombo.setOnAction(e -> {
            semester = (semesterCombo.getValue().toString());

        });

        numberCombo.setOnAction(e -> {
            number = (numberCombo.getValue().toString());

        });

        yearCombo.setOnAction(e -> {
            this.year = (yearCombo.getValue().toString());

        });

        courseChangeButton.setOnAction(e -> {
            String text = titleText.getText();
            String home = instructorHomeText.getText();
            String name = instructorNameText.getText();
            try {
                controller.handleAddCourseInfo();
                AppFileController fileControl = new AppFileController(app);
                fileControl.markFileAsNotSaved();
                app.getGUI().updateToolbarControls(false);
            } catch (IOException ex) {
                Logger.getLogger(CourseDetailsWorkspace.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        selectTemplateDirButton.setOnAction(e -> {
            data.getCourseData().handleSiteTemplate();
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);
        });

        pageStyleButton1.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("../TAManager/images"));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
            imagePath1 = FILE_PROTOCOL + selectedFile.getAbsolutePath();
            Image buttonImage = new Image(imagePath1);
            imageview.setImage(buttonImage);
            imageview.setTranslateX(-12);
            data.getCourseData().setImagePath(imagePath1);
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);
        });

        pageStyleButton2.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("../TAManager/images"));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
            imagePath2 = FILE_PROTOCOL + selectedFile.getAbsolutePath();
            Image buttonImage = new Image(imagePath2);
            imageview2.setImage(buttonImage);
            data.getCourseData().setImagePath2(imagePath2);
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);
        });

        pageStyleButton3.setOnAction(e -> {
            FileChooser fc = new FileChooser();
            fc.setInitialDirectory(new File("../TAManager/images"));
            File selectedFile = fc.showOpenDialog(app.getGUI().getWindow());
            imagePath3 = FILE_PROTOCOL + selectedFile.getAbsolutePath();
            Image buttonImage = new Image(imagePath3);
            imageview3.setImage(buttonImage);
            data.getCourseData().setImagePath3(imagePath3);
            AppFileController fileControl = new AppFileController(app);
            fileControl.markFileAsNotSaved();
            app.getGUI().updateToolbarControls(false);
        });

    }

    public CourseDetailsController getCourseDetailsController() {
        return controller;
    }

    public TableView geSitePageTable() {
        return sitePageTabel;
    }

    public HBox getCourseInfoHeaderPane() {
        return courseInfoBox;
    }

    public HBox getSiteTemplateHeaderPane() {
        return siteTemplateHeaderBox;
    }

    public VBox getCourseInfoBackgroundPane() {
        return courseInfoBackgroundPane;
    }

    public VBox getSiteTemplatePane() {
        return siteTemplateBox;
    }

    public VBox getSitePageHeaderPane() {
        return sitePageBox;
    }

    public VBox getPageStyleHeaderPane() {
        return pageStyleHeaderPane;
    }

    public VBox getPageStylePane() {
        return pageStyleBox;
    }

    public Label getPageStyleHeaderLabel() {
        return pageStyleHeaderLabel;
    }

    public Label getSiteTemplateHeaderLabel() {
        return siteTemplateHeaderLabel;
    }

    public Label getCourseInfoHeaderLabel() {
        return courseInfoHeaderLabel;
    }

    public Label getSubjectLabel() {
        return subjectLabel;
    }

    public Label getSemesterLabel() {
        return semesterLabel;
    }

    public Label getNumberLabel() {
        return numberLabel;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Label getYearLabel() {
        return yearLabel;
    }

    public Label getInstructorNameLabel() {
        return instructorNameLabel;
    }

    public Label getInstructorHomeLabel() {
        return instructorHomeLabel;
    }

    public Label getExportDirLabel() {
        return exportDirLabel;
    }

    public Label getExportDirSiteLabel() {
        return exportDirSiteLabel;
    }

    public Label getSiteTemplateSentLabel() {
        return siteTemplateSentLabel;
    }

    public Label getSiteTemplateDirLabel() {
        return siteTempDirLabel;
    }

    public Label getSitePagesLabel() {
        return sitePagesHeaderLabel;
    }

    public Label getBannerSchoolImageLabel() {
        return bannerSchoolImageLabel;
    }

    public Label getLeftFooterImageLabel() {
        return leftFooterImageLabel;
    }

    public Label getRightFooterImageLabel() {
        return rightFooterImageLabel;
    }

    public Label getStyleSheetLabel() {
        return styleSheetLabel;
    }

    public Label getNoteLabel() {
        return noteLabel;
    }

    public ComboBox getSubjectCombo() {
        return subjectCombo;
    }

    public ComboBox getNumberCombo() {
        return numberCombo;
    }

    public ComboBox getSemesterCombo() {
        return semesterCombo;
    }

    public ComboBox getYearCombo() {
        return yearCombo;
    }

    public ComboBox getPageStyleCombo() {
        return pageStyleCombo;
    }

    public Button getCourseChangeButton() {
        return courseChangeButton;
    }

    public Button getTemplateButton() {
        return selectTemplateDirButton;
    }

    public Button getPageStyleChangeButton() {
        return pageStyleButton1;
    }

    public Button getPageStyle2ChangeButton() {
        return pageStyleButton2;
    }

    public Button getPageStyle3ChangeButton() {
        return pageStyleButton3;
    }

    public TextField getTitleText() {
        return titleText;
    }

    public TextField getInstructoNameText() {
        return instructorNameText;
    }

    public TextField getInstructoHomeText() {
        return instructorHomeText;
    }

    public void resetWorkspace() {
        semesterCombo.setValue(null);
        subjectCombo.setValue(null);
        numberCombo.setValue(null);
        yearCombo.setValue(null);
        titleText.setText("");
        instructorNameText.setText("");
        instructorHomeText.setText("");
        exportDirSiteLabel.setText("");
        sitePageTabel.getItems().clear();
        sitePageTabel.refresh();
        siteTempDirLabel.setText("");
    }

    public void reloadWorkspace(String s) {

        subject = (subjectCombo.getValue().toString());
        semester = (semesterCombo.getValue().toString());
        number = (numberCombo.getValue().toString());
        this.year = (yearCombo.getValue().toString());
        siteTempDirLabel.setText(s);

    }

    public void setImageView(String view) {
        Image buttonImage = new Image(view);
        imageview.setTranslateX(-12);;
        imageview.setImage(buttonImage);
    }

    public void setImage3View(String view) {
        Image buttonImage = new Image(view);
        imageview2.setImage(buttonImage);
    }

    public void setImage2View(String view) {
        Image buttonImage = new Image(view);
        imageview3.setImage(buttonImage);
    }

}
