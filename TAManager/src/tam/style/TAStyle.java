package tam.style;

import djf.AppTemplate;
import djf.components.AppStyleComponent;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.AppGUI;
import java.util.HashMap;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import tam.data.CourseDetailsItems;
import tam.data.ProjectStudentItems;
import tam.data.ProjectTeamItems;
import tam.data.RecitationItems;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.workspace.CSGWorkspace;
import tam.data.ScheduleItems;
import tam.workspace.TAWorkspace;

/**
 * This class manages all CSS style for this application.
 *
 * Updated by: Bilal Shah
 *
 * @version 1.0
 */
public class TAStyle extends AppStyleComponent {

    // FIRST WE SHOULD DECLARE ALL OF THE STYLE TYPES WE PLAN TO USE
    //ICONS
    public static String ICONS_STYLE = "tag_button";
    //TABS
    public static String TAB_HEADER_PANE = "tab-pane";
    public static String COURSE_DATA_TAB = "course_data_tab";
    public static String TA_DATA_TAB = "ta_data_tab";
    public static String RECITATIONS_DATA_TAB = "recitation_data_tab";
    public static String SCHEDULE_DATA_TAB = "schedule_data_tab";
    public static String PROJECT_DATA_TAB = "project_data_tab";

    public static String CLASS_TOP_PANE = "top_pane";
    public static String CLASS_BOTTOM_PANE = "bottom_pane";

    // WE'LL USE THIS FOR ORGANIZING LEFT AND RIGHT CONTROLS
    public static String CLASS_PLAIN_PANE = "plain_pane";

    // THESE ARE THE HEADERS FOR EACH SIDE
    public static String CLASS_HEADER_PANE = "header_pane";
    public static String CLASS_HEADER_LABEL = "header_label";

    //COURSE
    //*******************************************************************************************************************
    //HEADERS
    public static String CLASS_COURSE_INFO_HEADER_PANE = "course_info_header_pane";
    public static String CLASS_SITE_TEMPLATE_HEADER_PANE = "course_site_template_header_pane";
    public static String CLASS_COURSE_INFO_BACKGROUND_PANE = "course_info_background_pane";
    public static String CLASS_SITE_TEMPLATE_PANE = "course_site_template_pane";
    public static String CLASS_SITE_PAGE_PANE = "course_site_page_pane";
    public static String CLASS_PAGE_STYLE_HEADER_PANE = "course_page_style_header_pane";
    public static String CLASS_PAGE_STYLE_PANE = "course_page_style_pane";

    //LABELS
    public static String CLASS_COURSE_INFO_HEADER_LABEL = "course_info_header_label";
    public static String CLASS_COURSE_SITE_TEMPLATE_HEADER_LABEL = "course_site_template_header_label";
    public static String CLASS_COURSE_SUBJECT_LABEL = "course_subject_label";
    public static String CLASS_COURSE_NUMBER_LABEL = "course_number_label";
    public static String CLASS_COURSE_SEMESTER_LABEL = "course_semester_label";
    public static String CLASS_COURSE_YEAR_LABEL = "course_year_label";
    public static String CLASS_COURSE_TITLE_LABEL = "course_title_label";
    public static String CLASS_COURSE_INSTRUCTOR_NAME_LABEL = "course_instructor_name_label";
    public static String CLASS_COURSE_INSTRUCTOR_HOME_LABEL = "course_instructor_home_label";
    public static String CLASS_COURSE_EXPORT_DIR_LABEL = "course_export_dir_label";
    public static String CLASS_COURSE_EXPORT_DIR_SITE_LABEL = "course_export_dir_site_label";
    public static String CLASS_COURSE_SITE_SENT_LABEL = "course_site_sent_label";
    public static String CLASS_COURSE_SITE_DIR_LABEL = "course_site_template_dir_label";
    public static String CLASS_COURSE_SITE_PAGES_HEADER_LABEL = "course_site_pages_header_label";
    public static String CLASS_COURSE_PAGE_STYLE_HEADER_LABEL = "course_pages_style_header_label";
    public static String CLASS_COURSE_BANNER_SCHOOL_IMAGE_LABEL = "course_banner_image_label";
    public static String CLASS_COURSE_LEFT_FOOTER_IMAGE_LABEL = "course_leftfooter_label";
    public static String CLASS_COURSE_RIGHT_FOOTER_IMAGE_LABEL = "course_rightfooter_label";
    public static String CLASS_COURSE_STYLESHEET_LABEL = "course_stylesheet_label";
    public static String CLASS_COURSE_NOTE_LABEL = "course_note_label";

    //BUTTONS
    public static String CLASS_COURSE_SUBJECT_COMBO = "course_subject_combo";
    public static String CLASS_COURSE_NUMBER_COMBO = "course_number_combo";
    public static String CLASS_COURSE_SEMESTER_COMBO = "course_semester_combo";
    public static String CLASS_COURSE_YEAR_COMBO = "course_year_combo";
    public static String CLASS_COURSE_CHANGE_BUTTON_TEXT = "course_change_button_text";
    public static String CLASS_COURSE_SITE_TEMPLATE_BUTTON = "course_site_template_button";
    //*******************************************************************************************************************

    //TA
    //*******************************************************************************************************************
    // ON THE LEFT WE HAVE THE TA ENTRY
    public static String CLASS_TA_TABLE = "ta_table";
    public static String CLASS_TA_TABLE_COLUMN_HEADER = "ta_table_column_header";
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_TEXT_FIELD = "add_ta_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";
    public static String CLASS_UPDATE_TA_BUTTON = "update_ta_button";
    public static String CLASS_CLEAR_TA_BUTTON = "clear_ta_button";

    // ON THE RIGHT WE HAVE THE OFFICE HOURS GRID
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_UPDATE_OFFICE_HOURS_BUTTON = "update_office_hours_button";
    public static String CLASS_START_TIME = "start_time";
    public static String CLASS_END_TIME = "end_time";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_MOUSE_ON_TA_CELL_PANE = "office_hours_grid_mouse_on_ta_cell_pane";
    //*******************************************************************************************************************

    //RECITATIONS
    //*******************************************************************************************************************
    //HEADERS
    public static String CLASS_RECITATION_TABLE = "recitation_table";
    public static String CLASS_RECITATION_HEADER_PANE = "recitation_header_pane";
    public static String CLASS_RECITATION_ADD_EDIT_HEADER_PANE = "recitation_add_edit_header_pane";

    //LABELS
    public static String CLASS_RECITATION_HEADER_LABEL = "recitation_header_label";
    public static String CLASS_RECITATION_ADD_EDIT_HEADER_LABEL = "recitation_add_edit_header_label";
    public static String CLASS_RECITATION_SECTION_LABEL = "recitation_section_label";
    public static String CLASS_RECITATION_INSTRUCTOR_LABEL = "recitation_instructor_label";
    public static String CLASS_RECITATION_LOCATION_LABEL = "recitation_location_label";
    public static String CLASS_RECITATION_DATE_LABEL = "recitation_date_label";
    public static String CLASS_RECITATION_TA_LABEL = "recitation_ta_label";

    //BUTTON
    public static String RECITATION_ADD_TA_PANE = "recitation_ta_pane";
    public static String RECITATION_TA_NAME_COMBO_TEXT = "recitation_combo_button_text";
    public static String RECITATION__ADD_BUTTON_TEXT = "recitation_add_button_text";
    public static String RECITATION__CLEAR_BUTTON_TEXT = "recitation_clear_button_text";
    //*******************************************************************************************************************

    //SCHEDULE
    //*******************************************************************************************************************
    //HEADERS
    public static String CLASS_SCHEDULE_HEADER_PANE = "schedule_header_pane";
    public static String CLASS_SCHEDULE_CALENDAR_BOUNDARIES_HEADER_PANE = "schedule_calendar_bounderies_header_pane";
    public static String CLASS_SCHEDULE_ITEMS_HEADER_PANE = "schedule_items_header_pane";
    public static String CLASS_SCHEDULE_ADD_EDIT_HEADER_PANE = "schedule_add_edit_header_pane";

    //LABELS
    public static String CLASS_SCHEDULE_HEADER_LABEL = "schedule_header_label";
    public static String CLASS_SCHEDULE_CALENDAR_BOUNDARIES_HEADER_LABEL = "schedule_calendar_bounderies_header_label";
    public static String CLASS_SCHEDULE_START_CALENDAR_BOUNDARIES_LABEL = "schedule_start_calendar_bounderies_label";
    public static String CLASS_SCHEDULE_END_CALENDAR_BOUNDARIES_LABEL = "schedule_end_calendar_bounderies_label";
    public static String CLASS_SCHEDULE_ITEMS_HEADER_LABEL = "schedule_items_header_label";
    public static String CLASS_SCHEDULE_ADD_EDIT_HEADER_LABEL = "schedule_add_edit_header_label";
    public static String CLASS_SCHEDULE_TYPE_LABEL = "schedule_type_label";
    public static String CLASS_SCHEDULE_TOPIC_LABEL = "schedule_topic_label";
    public static String CLASS_SCHEDULE_TITLE_LABEL = "schedule_title_label";
    public static String CLASS_SCHEDULE_DATE_LABEL = "schedule_date_label";
    public static String CLASS_SCHEDULE_LINK_LABEL = "schedule_link_label";
    public static String CLASS_SCHEDULE_CRITERIA_LABEL = "schedule_criteria_label";

    //BUTTON
    public static String CLASS_SCHEDULE_ADD_PANE = "schedule_add_pane";
    public static String CLASS_SCHEDULE_ADD_BUTTON_TEXT = "schedule_add_button";
    public static String CLASS_SCHEDULE_UPDATE_BUTTON_TEXT = "schedule_update_button";
    public static String CLASS_SCHEDULE_CLEAR_BUTTON_TEXT = "schedule_clear_button";
    public static String CLASS_SCHEDULE_COMBO_BUTTON_TEXT = "schedule_combo_button";
    //*******************************************************************************************************************

    //PROJECTS
    //*******************************************************************************************************************
    //HEADERS
    public static String CLASS_PROJECTS_HEADER_PANE = "projects_header_pane";
    public static String CLASS_PROJECTS_TEAMS_HEADER_PANE = "projects_teams_header_pane";
    public static String CLASS_PROJECTS_TEAMS_ADD_EDIT_HEADER_PANE = "projects_teams_add_edit_header_pane";
    public static String CLASS_PROJECTS_STUDENTS_HEADER_PANE = "projects_students_header_pane";
    public static String CLASS_PROJECTS_STUDENTS_ADD_EDIT_HEADER_PANE = "projects_students_add_edit_header_pane";

    //LABELS
    public static String CLASS_PROJECTS_HEADER_LABEL = "projects_header_label";
    public static String CLASS_PROJECTS_TEAMS_HEADER_LABEL = "projects_teams_header_label";
    public static String CLASS_PROJECTS_TEAMS_ADD_EDIT_HEADER_LABEL = "projects_teams_add_edit_header_label";
    public static String CLASS_PROJECTS_TEAMS_NAME_LABEL = "projects_teams_name_label";
    public static String CLASS_PROJECTS_TEAMS_COLOR_LABEL = "projects_teams_color_label";
    public static String CLASS_PROJECTS_TEAMS_TEXT_COLOR_LABEL = "projects_teams_text_color_label";
    public static String CLASS_PROJECTS_TEAMS_LINK_LABEL = "projects_teams_link_label";
    public static String CLASS_PROJECTS_TEAMS_COLOR_PROMPT_LABEL = "projects_teams_color_prompt_label";
    public static String CLASS_PROJECTS_STUDENTS_HEADER_LABEL = "projects_students_header_label";
    public static String CLASS_PROJECTS_STUDENTS_ADD_EDIT_HEADER_LABEL = "projects_students_add_edit_header_label";
    public static String CLASS_PROJECTS_STUDENTS_FIRST_NAME_LABEL = "projects_first_name_label";
    public static String CLASS_PROJECTS_STUDENTS_LAST_NAME_LABEL = "projects_last_name_label";
    public static String CLASS_PROJECTS_STUDENTS_TEAM_LABEL = "projects_students_team_label";
    public static String CLASS_PROJECTS_STUDENTS_ROLE_LABEL = "projects_students_role_label";

    //BUTTON
    public static String CLASS_PROJECT_ADD_PANE = "project_add_pane";
    public static String CLASS_PROJECTS_ADD_BUTTON_TEXT = "project_add_button";
    public static String CLASS_PROJECTS_CLEAR_BUTTON_TEXT = "project_clear_button";
    //*******************************************************************************************************************
    // THIS PROVIDES ACCESS TO OTHER COMPONENTS
    private AppTemplate app;

    /**
     * This constructor initializes all style for the application.
     *
     * @param initApp The application to be stylized.
     */
    public TAStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;

        // LET'S USE THE DEFAULT STYLESHEET SETUP
        super.initStylesheet(app);

        // INIT THE STYLE FOR THE FILE TOOLBAR
        app.getGUI().initFileToolbarStyle();
        //app.getGUI().getSaveButton().getStyleClass().add(ICONS_STYLE);
        // AND NOW OUR WORKSPACE STYLE
        initTAWorkspaceStyle();
    }

    /**
     * This function specifies all the style classes for all user interface
     * controls in the workspace.
     */
    private void initTAWorkspaceStyle() {
        // LEFT SIDE - THE HEADER
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        String image = FILE_PROTOCOL + PATH_IMAGES + "abstract_blue.jpg";
        String image2 = FILE_PROTOCOL + PATH_IMAGES + "blueheader.jpg";
        String image3 = FILE_PROTOCOL + PATH_IMAGES + "mirrorblueheader.jpg";
        //TABS
        workspaceComponent.getTabPane().getStyleClass().add(TAB_HEADER_PANE);
        workspaceComponent.getCourse_Tab().getStyleClass().add(COURSE_DATA_TAB);
        workspaceComponent.getTA_Tab().getStyleClass().add(TA_DATA_TAB);
        workspaceComponent.getRecitation_Tab().getStyleClass().add(RECITATIONS_DATA_TAB);
        workspaceComponent.getSchedule_Tab().getStyleClass().add(SCHEDULE_DATA_TAB);
        workspaceComponent.getProject_Tab().getStyleClass().add(PROJECT_DATA_TAB);

        //COURSE      
        //****************************************************************************************************************************************************
        TableView<CourseDetailsItems> sitePageTable = workspaceComponent.getCourseDetailsWorkspace().geSitePageTable();
        sitePageTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : sitePageTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        workspaceComponent.getCourseDetailsWorkspace().getCourseInfoHeaderPane().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getCourseDetailsWorkspace().getSiteTemplateHeaderPane().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getCourseDetailsWorkspace().getPageStyleHeaderPane().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getCourseDetailsWorkspace().getCourseInfoBackgroundPane().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;-fx-padding: 15 0 18 0");

        workspaceComponent.getCourseDetailsWorkspace().getSiteTemplatePane().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getCourseDetailsWorkspace().getSitePageHeaderPane().setStyle("-fx-background-image: url('" + "" + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        workspaceComponent.getCourseDetailsWorkspace().getPageStylePane().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;-fx-padding: 50 0 0 0");

        //HEADERS
        //workspaceComponent.getCourseDetailsWorkspace().getCourseInfoHeaderPane().getStyleClass().add(CLASS_COURSE_INFO_HEADER_PANE);
        //  workspaceComponent.getCourseDetailsWorkspace().getSiteTemplateHeaderPane().getStyleClass().add(CLASS_SITE_TEMPLATE_HEADER_PANE);
        //  workspaceComponent.getCourseDetailsWorkspace().getCourseInfoBackgroundPane().getStyleClass().add(CLASS_COURSE_INFO_BACKGROUND_PANE);
        //  workspaceComponent.getCourseDetailsWorkspace().getSiteTemplatePane().getStyleClass().add(CLASS_SITE_TEMPLATE_PANE);
        //workspaceComponent.getCourseDetailsWorkspace().getSitePageHeaderPane().getStyleClass().add(CLASS_SITE_PAGE_PANE);
        //  workspaceComponent.getCourseDetailsWorkspace().getPageStyleHeaderPane().getStyleClass().add(CLASS_PAGE_STYLE_HEADER_PANE);
        // workspaceComponent.getCourseDetailsWorkspace().getPageStylePane().getStyleClass().add(CLASS_PAGE_STYLE_PANE);
        //LABELS
        workspaceComponent.getCourseDetailsWorkspace().getCourseInfoHeaderLabel().getStyleClass().add(CLASS_COURSE_INFO_HEADER_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getSiteTemplateHeaderLabel().getStyleClass().add(CLASS_COURSE_SITE_TEMPLATE_HEADER_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getSubjectLabel().getStyleClass().add(CLASS_COURSE_SUBJECT_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getNumberLabel().getStyleClass().add(CLASS_COURSE_NUMBER_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getSemesterLabel().getStyleClass().add(CLASS_COURSE_SEMESTER_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getYearLabel().getStyleClass().add(CLASS_COURSE_YEAR_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getTitleLabel().getStyleClass().add(CLASS_COURSE_TITLE_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getInstructorNameLabel().getStyleClass().add(CLASS_COURSE_INSTRUCTOR_NAME_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getInstructorHomeLabel().getStyleClass().add(CLASS_COURSE_INSTRUCTOR_HOME_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getExportDirLabel().getStyleClass().add(CLASS_COURSE_EXPORT_DIR_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getExportDirSiteLabel().getStyleClass().add(CLASS_COURSE_EXPORT_DIR_SITE_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getSiteTemplateSentLabel().getStyleClass().add(CLASS_COURSE_SITE_SENT_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getSiteTemplateDirLabel().getStyleClass().add(CLASS_COURSE_SITE_DIR_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getSitePagesLabel().getStyleClass().add(CLASS_COURSE_SITE_PAGES_HEADER_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getPageStyleHeaderLabel().getStyleClass().add(CLASS_COURSE_PAGE_STYLE_HEADER_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getBannerSchoolImageLabel().getStyleClass().add(CLASS_COURSE_BANNER_SCHOOL_IMAGE_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getLeftFooterImageLabel().getStyleClass().add(CLASS_COURSE_LEFT_FOOTER_IMAGE_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getRightFooterImageLabel().getStyleClass().add(CLASS_COURSE_RIGHT_FOOTER_IMAGE_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getStyleSheetLabel().getStyleClass().add(CLASS_COURSE_STYLESHEET_LABEL);
        workspaceComponent.getCourseDetailsWorkspace().getNoteLabel().getStyleClass().add(CLASS_COURSE_NOTE_LABEL);

        //BUTTONS
        workspaceComponent.getCourseDetailsWorkspace().getSubjectCombo().getStyleClass().add(CLASS_COURSE_SUBJECT_COMBO);
        workspaceComponent.getCourseDetailsWorkspace().getNumberCombo().getStyleClass().add(CLASS_COURSE_NUMBER_COMBO);
        workspaceComponent.getCourseDetailsWorkspace().getSemesterCombo().getStyleClass().add(CLASS_COURSE_SEMESTER_COMBO);
        workspaceComponent.getCourseDetailsWorkspace().getYearCombo().getStyleClass().add(CLASS_COURSE_YEAR_COMBO);
        workspaceComponent.getCourseDetailsWorkspace().getCourseChangeButton().getStyleClass().add(CLASS_COURSE_CHANGE_BUTTON_TEXT);
        workspaceComponent.getCourseDetailsWorkspace().getTemplateButton().getStyleClass().add(CLASS_COURSE_SITE_TEMPLATE_BUTTON);
        workspaceComponent.getCourseDetailsWorkspace().getPageStyleChangeButton().getStyleClass().add(CLASS_COURSE_CHANGE_BUTTON_TEXT);
        workspaceComponent.getCourseDetailsWorkspace().getPageStyle2ChangeButton().getStyleClass().add(CLASS_COURSE_CHANGE_BUTTON_TEXT);
        workspaceComponent.getCourseDetailsWorkspace().getPageStyle3ChangeButton().getStyleClass().add(CLASS_COURSE_CHANGE_BUTTON_TEXT);
        workspaceComponent.getCourseDetailsWorkspace().getPageStyleCombo().getStyleClass().add(CLASS_COURSE_SEMESTER_COMBO);
        //****************************************************************************************************************************************************

        //TA      
        //****************************************************************************************************************************************************
       workspaceComponent.getTAWorkspace().getTAsHeaderBox().setStyle("-fx-background-image: url('" + image3 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
       
      workspaceComponent.getTAWorkspace().getOfficeHoursSubheaderBox().setStyle("-fx-background-image: url('" + image3 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        //HEADERS
        //workspaceComponent.getTAWorkspace().getTAsHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTAWorkspace().getTAsHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getTAWorkspace().getDeleteButton().getStyleClass().add(ICONS_STYLE);
        // LEFT SIDE - THE TABLE
        TableView<TeachingAssistant> taTable = workspaceComponent.getTAWorkspace().getTATable();
        taTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        // LEFT SIDE - THE TA DATA ENTRY
        workspaceComponent.getTAWorkspace().getAddBox().getStyleClass().add(CLASS_ADD_TA_PANE);
        workspaceComponent.getTAWorkspace().getNameTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getTAWorkspace().getEmailTextField().getStyleClass().add(CLASS_ADD_TA_TEXT_FIELD);
        workspaceComponent.getTAWorkspace().getAddButton().getStyleClass().add(CLASS_ADD_TA_BUTTON);
        workspaceComponent.getTAWorkspace().getClearButton().getStyleClass().add(CLASS_CLEAR_TA_BUTTON);

        // RIGHT SIDE - THE HEADER
       // workspaceComponent.getTAWorkspace().getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getTAWorkspace().getOfficeHoursSubheaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getTAWorkspace().getUpdateOfficeHoursButton().getStyleClass().add(CLASS_UPDATE_OFFICE_HOURS_BUTTON);
        workspaceComponent.getTAWorkspace().getStartButton().getStyleClass().add(CLASS_START_TIME);
        workspaceComponent.getTAWorkspace().getEndButton().getStyleClass().add(CLASS_END_TIME);
        //****************************************************************************************************************************************************

        //RECITATION
        //****************************************************************************************************************************************************
        // TOP SIDE - THE RECITATION TABLE
        TableView<RecitationItems> recitationTable = workspaceComponent.getRecitationWorkspace().getRecitationTable();
        recitationTable.getStyleClass().add(CLASS_RECITATION_TABLE);
        for (TableColumn tableColumn : recitationTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        workspaceComponent.getRecitationWorkspace().getRecitationHeaderBox().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getRecitationWorkspace().getAdd_Edit_HeaderBox().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getRecitationWorkspace().getBottomPane().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        //HEADERS
        //workspaceComponent.getRecitationWorkspace().getBottomPane().getStyleClass().add(CLASS_RECITATION_HEADER_PANE);
        //workspaceComponent.getRecitationWorkspace().getAdd_Edit_HeaderBox().getStyleClass().add(CLASS_RECITATION_ADD_EDIT_HEADER_PANE);
        workspaceComponent.getRecitationWorkspace().getAdd_Edit_HeaderLabel().getStyleClass().add(CLASS_RECITATION_ADD_EDIT_HEADER_LABEL);
        //workspaceComponent.getRecitationWorkspace().getRecitationHeaderBox().getStyleClass().add(CLASS_HEADER_PANE);
        workspaceComponent.getRecitationWorkspace().getRecitationHeaderLabel().getStyleClass().add(CLASS_HEADER_LABEL);
        workspaceComponent.getRecitationWorkspace().getTopPane().getStyleClass().add(CLASS_TOP_PANE);
        workspaceComponent.getRecitationWorkspace().getBottomPane().getStyleClass().add(CLASS_BOTTOM_PANE);
        //LABELS
        workspaceComponent.getRecitationWorkspace().getRecitationSectionLabel().getStyleClass().add(CLASS_RECITATION_SECTION_LABEL);
        workspaceComponent.getRecitationWorkspace().getRecitationInstructorLabel().getStyleClass().add(CLASS_RECITATION_INSTRUCTOR_LABEL);
        workspaceComponent.getRecitationWorkspace().getRecitationDateLabel().getStyleClass().add(CLASS_RECITATION_DATE_LABEL);
        workspaceComponent.getRecitationWorkspace().getRecitationLocationLabel().getStyleClass().add(CLASS_RECITATION_LOCATION_LABEL);
        workspaceComponent.getRecitationWorkspace().getRecitationTALabel().getStyleClass().add(CLASS_RECITATION_TA_LABEL);
        workspaceComponent.getRecitationWorkspace().getRecitationTA2Label().getStyleClass().add(CLASS_RECITATION_TA_LABEL);

        //BUTTON
        workspaceComponent.getRecitationWorkspace().getAddBox().getStyleClass().add(RECITATION_ADD_TA_PANE);
        workspaceComponent.getRecitationWorkspace().getTAName().getStyleClass().add(RECITATION_TA_NAME_COMBO_TEXT);
        workspaceComponent.getRecitationWorkspace().getTA2Name().getStyleClass().add(RECITATION_TA_NAME_COMBO_TEXT);
        workspaceComponent.getRecitationWorkspace().getAddButton().getStyleClass().add(RECITATION__ADD_BUTTON_TEXT);
        workspaceComponent.getRecitationWorkspace().getClearButton().getStyleClass().add(RECITATION__CLEAR_BUTTON_TEXT);
        workspaceComponent.getRecitationWorkspace().getDeleteButton().getStyleClass().add(ICONS_STYLE);
        //****************************************************************************************************************************************************

        //SCHEDULE
        //****************************************************************************************************************************************************
        TableView<ScheduleItems> scheduleTable = workspaceComponent.getScheduleWorkspace().getScheduleTable();
        scheduleTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : scheduleTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
        workspaceComponent.getScheduleWorkspace().getScheduleHeaderBox().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch; -fx-padding: 5 0 15 0");

        workspaceComponent.getScheduleWorkspace().getScheduleItemsHeaderBox().setStyle("-fx-background-image: url('" + image2 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;-fx-padding: 5 0 0 10");

        workspaceComponent.getScheduleWorkspace().getAdd_editBoxHeader().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getScheduleWorkspace().getCalendarBoundriesHeaderBox().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch; -fx-padding: 0 0 20 0");
        //HEADERS
        // workspaceComponent.getScheduleWorkspace().getScheduleHeaderBox().getStyleClass().add(CLASS_SCHEDULE_HEADER_PANE);
        //workspaceComponent.getScheduleWorkspace().getCalendarBoundriesHeaderBox().getStyleClass().add(CLASS_SCHEDULE_CALENDAR_BOUNDARIES_HEADER_PANE);
        //workspaceComponent.getScheduleWorkspace().getScheduleItemsHeaderBox().getStyleClass().add(CLASS_SCHEDULE_ITEMS_HEADER_PANE);
        // workspaceComponent.getScheduleWorkspace().getAdd_editBoxHeader().getStyleClass().add(CLASS_SCHEDULE_ADD_EDIT_HEADER_PANE);
        workspaceComponent.getScheduleWorkspace().getTopPane().getStyleClass().add(CLASS_TOP_PANE);
        workspaceComponent.getScheduleWorkspace().getBottomPane().getStyleClass().add(CLASS_BOTTOM_PANE);
        //LABELS
        workspaceComponent.getScheduleWorkspace().getScheduleHeaderLabel().getStyleClass().add(CLASS_SCHEDULE_HEADER_LABEL);
        workspaceComponent.getScheduleWorkspace().getCalendarBoundriesHeaderLabel().getStyleClass().add(CLASS_SCHEDULE_CALENDAR_BOUNDARIES_HEADER_LABEL);
        workspaceComponent.getScheduleWorkspace().getCalendarStartLabel().getStyleClass().add(CLASS_SCHEDULE_START_CALENDAR_BOUNDARIES_LABEL);
        workspaceComponent.getScheduleWorkspace().getCalendarEndLabel().getStyleClass().add(CLASS_SCHEDULE_END_CALENDAR_BOUNDARIES_LABEL);
        workspaceComponent.getScheduleWorkspace().getScheduleItemsHeaderLabel().getStyleClass().add(CLASS_SCHEDULE_ITEMS_HEADER_LABEL);
        workspaceComponent.getScheduleWorkspace().getAdd_editHeaderLabel().getStyleClass().add(CLASS_SCHEDULE_ADD_EDIT_HEADER_LABEL);
        workspaceComponent.getScheduleWorkspace().getTypeLabel().getStyleClass().add(CLASS_SCHEDULE_TYPE_LABEL);
        workspaceComponent.getScheduleWorkspace().getDateLabel().getStyleClass().add(CLASS_SCHEDULE_DATE_LABEL);
        workspaceComponent.getScheduleWorkspace().getTimeLabel().getStyleClass().add(CLASS_SCHEDULE_DATE_LABEL);
        workspaceComponent.getScheduleWorkspace().getTitleLabel().getStyleClass().add(CLASS_SCHEDULE_TITLE_LABEL);
        workspaceComponent.getScheduleWorkspace().getTopicLabel().getStyleClass().add(CLASS_SCHEDULE_TOPIC_LABEL);
        workspaceComponent.getScheduleWorkspace().getLinkLabel().getStyleClass().add(CLASS_SCHEDULE_LINK_LABEL);
        workspaceComponent.getScheduleWorkspace().getCriteriaLabel().getStyleClass().add(CLASS_SCHEDULE_CRITERIA_LABEL);

        //BUTTON
        workspaceComponent.getScheduleWorkspace().getAddBox().getStyleClass().add(CLASS_SCHEDULE_ADD_PANE);
        workspaceComponent.getScheduleWorkspace().getAddButton().getStyleClass().add(CLASS_SCHEDULE_ADD_BUTTON_TEXT);
        workspaceComponent.getScheduleWorkspace().getClearButton().getStyleClass().add(CLASS_SCHEDULE_CLEAR_BUTTON_TEXT);
        workspaceComponent.getScheduleWorkspace().getTopicCombo().getStyleClass().add(CLASS_SCHEDULE_COMBO_BUTTON_TEXT);
        workspaceComponent.getScheduleWorkspace().getDeleteButton().getStyleClass().add(ICONS_STYLE);

        //****************************************************************************************************************************************************
        //PROJECTS
        //****************************************************************************************************************************************************
        TableView<ProjectTeamItems> projectTable = workspaceComponent.getProjectWorkspace().getProjectTeamTable();
        projectTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : projectTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }

        TableView<ProjectStudentItems> studentsTable = workspaceComponent.getProjectWorkspace().getStudentsTeamTable();
        studentsTable.getStyleClass().add(CLASS_TA_TABLE);
        for (TableColumn tableColumn : studentsTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TA_TABLE_COLUMN_HEADER);
        }
         workspaceComponent.getProjectWorkspace().getprojectHeaderPane().setStyle("-fx-background-image: url('" + image3 + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getProjectWorkspace().getAdd_editBoxHeaderTeams().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");

        workspaceComponent.getProjectWorkspace().getAdd_editBoxHeaderStudents().setStyle("-fx-background-image: url('" + image + "'); "
                + "-fx-background-position: center center; "
                + "-fx-background-repeat: stretch;");
        //HEADERS
       // workspaceComponent.getProjectWorkspace().getprojectHeaderPane().getStyleClass().add(CLASS_PROJECTS_HEADER_PANE);
        workspaceComponent.getProjectWorkspace().getTeamsHeaderPane().getStyleClass().add(CLASS_PROJECTS_TEAMS_HEADER_PANE);
        // workspaceComponent.getProjectWorkspace().getAdd_editBoxHeaderTeams().getStyleClass().add(CLASS_PROJECTS_TEAMS_ADD_EDIT_HEADER_PANE);
        workspaceComponent.getProjectWorkspace().getStudentHeaderPane().getStyleClass().add(CLASS_PROJECTS_STUDENTS_HEADER_PANE);
        workspaceComponent.getProjectWorkspace().getAdd_editBoxHeaderStudents().getStyleClass().add(CLASS_PROJECTS_STUDENTS_ADD_EDIT_HEADER_PANE);

        //LABELS
        workspaceComponent.getProjectWorkspace().getprojectHeaderLabel().getStyleClass().add(CLASS_PROJECTS_HEADER_LABEL);
        workspaceComponent.getProjectWorkspace().getTeamsHeaderLabel().getStyleClass().add(CLASS_PROJECTS_TEAMS_HEADER_LABEL);
        workspaceComponent.getProjectWorkspace().getAdd_editTeamHeaderLabel().getStyleClass().add(CLASS_PROJECTS_TEAMS_ADD_EDIT_HEADER_LABEL);
        workspaceComponent.getProjectWorkspace().getTeamNameLabel().getStyleClass().add(CLASS_PROJECTS_TEAMS_NAME_LABEL);
        workspaceComponent.getProjectWorkspace().getTeamColorLabel().getStyleClass().add(CLASS_PROJECTS_TEAMS_COLOR_LABEL);
        workspaceComponent.getProjectWorkspace().getTeamTextColorLabel().getStyleClass().add(CLASS_PROJECTS_TEAMS_TEXT_COLOR_LABEL);
        workspaceComponent.getProjectWorkspace().getTeamLinkLabel().getStyleClass().add(CLASS_PROJECTS_TEAMS_LINK_LABEL);
        workspaceComponent.getProjectWorkspace().getColorPicker().getStyleClass().add(CLASS_PROJECTS_TEAMS_COLOR_PROMPT_LABEL);
        workspaceComponent.getProjectWorkspace().getTextColorPicker().getStyleClass().add(CLASS_PROJECTS_TEAMS_COLOR_PROMPT_LABEL);
        workspaceComponent.getProjectWorkspace().getStudentHeaderLabel().getStyleClass().add(CLASS_PROJECTS_STUDENTS_HEADER_LABEL);
        workspaceComponent.getProjectWorkspace().getAdd_editStudentHeaderLabel().getStyleClass().add(CLASS_PROJECTS_STUDENTS_ADD_EDIT_HEADER_LABEL);
        workspaceComponent.getProjectWorkspace().getStudentFirstNameLabelLabel().getStyleClass().add(CLASS_PROJECTS_STUDENTS_FIRST_NAME_LABEL);
        workspaceComponent.getProjectWorkspace().getStudentLastNameLabel().getStyleClass().add(CLASS_PROJECTS_STUDENTS_LAST_NAME_LABEL);
        workspaceComponent.getProjectWorkspace().getStudentTeamlLabel().getStyleClass().add(CLASS_PROJECTS_STUDENTS_TEAM_LABEL);
        workspaceComponent.getProjectWorkspace().getStudentRolelLabel().getStyleClass().add(CLASS_PROJECTS_STUDENTS_ROLE_LABEL);
        workspaceComponent.getProjectWorkspace().getComboBox().getStyleClass().add(CLASS_PROJECTS_TEAMS_COLOR_PROMPT_LABEL);

        //BUTTONS
        workspaceComponent.getProjectWorkspace().getTeamAddPane().getStyleClass().add(CLASS_PROJECT_ADD_PANE);
        workspaceComponent.getProjectWorkspace().getAddButton().getStyleClass().add(CLASS_PROJECTS_ADD_BUTTON_TEXT);
        workspaceComponent.getProjectWorkspace().getClearButton().getStyleClass().add(CLASS_PROJECTS_CLEAR_BUTTON_TEXT);
        workspaceComponent.getProjectWorkspace().getStudentAddPane().getStyleClass().add(CLASS_PROJECT_ADD_PANE);
        workspaceComponent.getProjectWorkspace().getStudentAddButton().getStyleClass().add(CLASS_PROJECTS_ADD_BUTTON_TEXT);
        workspaceComponent.getProjectWorkspace().getStudentClearButton().getStyleClass().add(CLASS_PROJECTS_CLEAR_BUTTON_TEXT);
        workspaceComponent.getProjectWorkspace().getDeleteButton().getStyleClass().add(ICONS_STYLE);
        workspaceComponent.getProjectWorkspace().getStudentDeleteButton().getStyleClass().add(ICONS_STYLE);
        //****************************************************************************************************************************************************
    }

    /**
     * This method initializes the style for all UI components in the office
     * hours grid. Note that this should be called every time a new TA Office
     * Hours Grid is created or loaded.
     */
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        workspaceComponent.getTAWorkspace().getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(workspaceComponent.getTAWorkspace().getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }

    /**
     * This helper method initializes the style of all the nodes in the nodes
     * map to a common style, styleClass.
     */
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node) nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }

    public void Highlight(Pane p) {
        p.getStyleClass().clear();
        p.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_MOUSE_ON_TA_CELL_PANE);
    }

    public void UnHighlight(Pane p) {

        p.getStyleClass().add(CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
    }

}
