package tam.workspace;

import djf.AppTemplate;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import djf.controller.AppFileController;
import djf.ui.AppGUI;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import tam.TAManagerApp;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import properties_manager.PropertiesManager;
import tam.TAManagerProp;
import static tam.style.TAStyle.CLASS_ADD_TA_BUTTON;
import tam.workspace.TAWorkspace;

/**
 * This class serves as the workspace component for the CSG Manager application.
 * It provides all the user interface controls in the workspace area.
 *
 * Updated by: Bilal Shah
 */
public class CSGWorkspace extends AppWorkspaceComponent {

    TAManagerApp app;
    AppTemplate tempApp;
    AppGUI gui;
    //BOXES
    VBox courseBox = new VBox(15);
    VBox taBox = new VBox();
    VBox recitationBox = new VBox(15);
    VBox scheduleBox = new VBox(15);
    VBox projectBox = new VBox();
    //TAB PANE
    TabPane tabPane = new TabPane();
    //TABS
    Tab CourseDataTab = new Tab();
    Tab TaDataTab = new Tab();
    Tab ReciationDataTab = new Tab();
    Tab ScheduleDataTab = new Tab();
    Tab ProjectDataTab = new Tab();

    //WORKSPACES
    CourseDetailsWorkspace courseDetailsWorkspace;
    TAWorkspace taWorkspace;
    RecitationWorkspace recitationWorkspace;
    ScheduleWorkspace scheduleWorkspace;
    ProjectWorkspace projectWorkspace;

    public CSGWorkspace(AppTemplate initApp) {
        tempApp = initApp;
    }

    public CSGWorkspace(TAManagerApp initApp) {
        app = initApp;
        gui = app.getGUI();
        //INTILIZE WORKSPACES
        courseDetailsWorkspace = new CourseDetailsWorkspace(app);
        recitationWorkspace = new RecitationWorkspace(app);
        taWorkspace = new TAWorkspace(app);
        scheduleWorkspace = new ScheduleWorkspace(app);
        projectWorkspace = new ProjectWorkspace(app);
       
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        //PANES
        SplitPane sPane = new SplitPane(taWorkspace.getLeftPane(), new ScrollPane(taWorkspace.getRightPane()));

        //COURSE
        courseBox.getChildren().addAll(courseDetailsWorkspace.scroll);

        //TA
        taBox.getChildren().add(sPane);

        //RECITATIONS
        recitationBox.getChildren().add(recitationWorkspace.scroll);

        //SCHEDULE
        scheduleBox.getChildren().addAll(scheduleWorkspace.topPane, scheduleWorkspace.bottomPane);

        //PROJECTS
        projectBox.getChildren().addAll(projectWorkspace.scroll);

        //LABEL TAB TEXT
        String CourseDataTabText = props.getProperty(TAManagerProp.COURSE_DATA_TAB.toString());
        String TaDataTabText = props.getProperty(TAManagerProp.TA_DATA_TAB.toString());
        String reciationsDataTabText = props.getProperty(TAManagerProp.RECITATIONS_DATA_TAB.toString());
        String scheduleDataTabText = props.getProperty(TAManagerProp.SCHEDULE_DATA_TAB.toString());
        String projectDataTabText = props.getProperty(TAManagerProp.PROJECT_DATA_TAB.toString());

        //COURSE
        CourseDataTab.setText(CourseDataTabText);
        CourseDataTab.setContent(courseBox);

        //TA
        TaDataTab.setText(TaDataTabText);
        TaDataTab.setContent(taBox);

        //RECITATIONS
        ReciationDataTab.setText(reciationsDataTabText);
        ReciationDataTab.setContent(recitationBox);

        //SCHEDULE
        ScheduleDataTab.setText(scheduleDataTabText);
        ScheduleDataTab.setContent(scheduleBox);

        //PROJECT
        ProjectDataTab.setText(projectDataTabText);
        ProjectDataTab.setContent(projectBox);

        tabPane.getTabs().addAll(CourseDataTab, TaDataTab, ReciationDataTab, ScheduleDataTab, ProjectDataTab);
        //tabPane.setTabMinWidth(364);
        tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        workspace = new BorderPane();
        ((BorderPane) workspace).setCenter(tabPane);
        double width = app.getGUI().getWidthOfScreen();
        System.out.println(width);
        double tabWidth = (width / 5);
        tabPane.setTabMinWidth(tabWidth - 20.0);

        taWorkspace.getTATable().prefHeightProperty().bind(workspace.heightProperty().multiply(1));
        recitationWorkspace.recitationTable.prefHeightProperty().bind(workspace.heightProperty().multiply(.6));
        recitationWorkspace.bottomPane.prefHeightProperty().bind(workspace.heightProperty().multiply(.4));
        recitationWorkspace.bottomPane.prefWidthProperty().bind(workspace.widthProperty().multiply(1));
        projectWorkspace.projectTabBox.prefHeightProperty().bind(workspace.heightProperty().multiply(1.5));
        projectBox.prefHeightProperty().bind(workspace.heightProperty().multiply(1.5));
        courseDetailsWorkspace.sitePageTabel.prefHeightProperty().bind(workspace.heightProperty().multiply(.4));
        //courseDetailsWorkspace.siteTemplateBox.prefHeightProperty().bind(workspace.heightProperty().multiply(.3));
        courseDetailsWorkspace.bottomBox.prefWidthProperty().bind(workspace.widthProperty().multiply(1));
        
    }

    @Override
    public void resetWorkspace() {
        taWorkspace.resetWorkspace();
        recitationWorkspace.resetWorkspace();
        scheduleWorkspace.resetWorkspace();
        projectWorkspace.resetWorkspace();
        courseDetailsWorkspace.resetWorkspace();
    }

    public CourseDetailsWorkspace getCourseDetailsWorkspace() {
        return courseDetailsWorkspace;
    }

    public TAWorkspace getTAWorkspace() {
        return taWorkspace;
    }

    public RecitationWorkspace getRecitationWorkspace() {
        return recitationWorkspace;
    }

    public ScheduleWorkspace getScheduleWorkspace() {
        return scheduleWorkspace;
    }

    public ProjectWorkspace getProjectWorkspace() {
        return projectWorkspace;
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        taWorkspace.reloadWorkspace(dataComponent);
        recitationWorkspace.reloadWorkspace();
        
    }

    public Tab getCourse_Tab() {
        return CourseDataTab;
    }

    public Tab getTA_Tab() {
        return TaDataTab;
    }

    public Tab getRecitation_Tab() {
        return ReciationDataTab;
    }

    public Tab getSchedule_Tab() {
        return ScheduleDataTab;
    }

    public Tab getProject_Tab() {
        return ProjectDataTab;
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    @Override
    public void UndoButton() {

        Tab selTab = tabPane.getTabs().get(
                tabPane.getSelectionModel().getSelectedIndex());
        String s = selTab.getText();
        if (s.equals("Recitations Data")) {
            recitationWorkspace.controller.handleUndo();
        } else if (s.equals("TA Data")) {
            taWorkspace.controller.handleUndo();
        } else if (s.equals("Project Data")) {
            projectWorkspace.controller.handleUndo();
        } else if (s.equals("Schedule Data")) {
            scheduleWorkspace.controller.handleUndo();
        } 

    }

    @Override
    public void redoButton() {
        Tab selTab = tabPane.getTabs().get(
                tabPane.getSelectionModel().getSelectedIndex());
        String s = selTab.getText();
        if (s.equals("Recitations Data")) {
            recitationWorkspace.controller.handleredo();
        } else if (s.equals("TA Data")) {
            taWorkspace.controller.handleredo();
        } else if (s.equals("Project Data")) {
            projectWorkspace.controller.handleRedo();

        } else if (s.equals("Schedule Data")) {
            scheduleWorkspace.controller.handleRedo();

        }
    }
}
