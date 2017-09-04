/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import static djf.settings.AppPropertyType.EXPORT_ERROR_TITLE;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppGUI;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import static tam.TAManagerProp.MISSING_LINK_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_LINK_NAME_TITLE;
import tam.data.CSGData;
import tam.data.CourseDetailsItems;
import tam.data.CourseInfoItem;

/**
 *
 * @author Bilal
 */
public class CourseDetailsController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED

    TAManagerApp app;
    ObservableList<CourseDetailsItems> courseItem;
    TableView course_table = new TableView();
    CourseDetailsItems item;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public CourseDetailsController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        courseItem = FXCollections.observableArrayList();
    }

    public void handleAddCourseInfo() throws IOException {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData csgData = (CSGData) app.getDataComponent();
        TextField courseTitleText = workspace.getCourseDetailsWorkspace().titleText;
        String courseTitle = courseTitleText.getText();
        String subject = workspace.getCourseDetailsWorkspace().subject;
        String semester = workspace.getCourseDetailsWorkspace().semester;
        String number = workspace.getCourseDetailsWorkspace().number;
        String year = workspace.getCourseDetailsWorkspace().year;
        String instructorHome = workspace.getCourseDetailsWorkspace().instructorHomeText.getText();
        String instructorName = workspace.getCourseDetailsWorkspace().instructorNameText.getText();
        if (!validateLink(instructorHome)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
        } else {
            CourseInfoItem courseInfo = new CourseInfoItem();
            courseInfo.setCourseNumber(number);
            courseInfo.setCourseSemester(semester);
            courseInfo.setSubject(subject);
            courseInfo.setCourseYear(year);
            courseInfo.setCourseTitle(courseTitle);
            courseInfo.setCourseInstructorName(instructorName);
            courseInfo.setCourseInstrcutorHome(instructorHome);
            DirectoryChooser fc = new DirectoryChooser();
            fc.setInitialDirectory(new File(PATH_WORK));
            fc.setTitle(props.getProperty(EXPORT_ERROR_TITLE));
            File exportDir = fc.showDialog(app.getGUI().getWindow());
            workspace.getCourseDetailsWorkspace().exportDirSiteLabel.setText(exportDir.getAbsolutePath());
            AppGUI.setString2(exportDir.getAbsolutePath());

        }

    }

    private boolean validateLink(String link) throws IOException {
        try {
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

}
