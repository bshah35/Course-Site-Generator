/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import static djf.settings.AppPropertyType.EXPORT_ERROR_TITLE;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import static djf.settings.AppStartupConstants.PATH_WORK;
import djf.ui.AppGUI;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.workspace.CSGWorkspace;

/**
 *
 * @author Bilal
 */
public class CourseData {
    // WE'LL NEED ACCESS TO THE APP TO NOTIFY THE GUI WHEN DATA CHANGES

    TAManagerApp app;

    ObservableList<CourseInfoItem> courseInfoItem;
    CourseInfoItem courseInfo = new CourseInfoItem();
    ObservableList<CourseDetailsItems> courseItem;
    TableView course_table = new TableView();
    CourseDetailsItems item;
    String siteDir, imagePath, imagePath2, imagePath3;
    AppGUI gui;

    public CourseData(TAManagerApp initApp) {
        app = initApp;
        courseItem = FXCollections.observableArrayList();

        courseInfoItem = FXCollections.observableArrayList();
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS

    }

    public void handleSiteTemplate() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData csgData = (CSGData) app.getDataComponent();
        DirectoryChooser fc = new DirectoryChooser();
        fc.setInitialDirectory(new File(PATH_WORK));
        fc.setTitle(props.getProperty(EXPORT_ERROR_TITLE));
        File templateDir = fc.showDialog(app.getGUI().getWindow());
        workspace.getCourseDetailsWorkspace().getSiteTemplateDirLabel().setText(templateDir.getAbsolutePath());
        System.out.println(templateDir.getAbsolutePath());
        Label s = workspace.getCourseDetailsWorkspace().getSiteTemplateDirLabel();
        siteDir = s.getText();
        AppGUI.setString(siteDir);
        //app.getGUI().setString(siteDir);
        courseItem.clear();
        workspace.getCourseDetailsWorkspace().geSitePageTable().refresh();
        List<String> filesSize = getHTMLfiles(s.getText());
        List<String> JsSize = getJSfiles(s.getText());

        for (int i = 0; i < filesSize.size(); i++) {
            if (filesSize.get(i).equals("index.html")) {
                String navBarTitle = "Home";
                String htmlName = filesSize.get(i);
                String jsName = JsSize.get(i);
                item = new CourseDetailsItems(true, navBarTitle, htmlName, jsName);
                courseItem.add(item);
            }
            if (filesSize.get(i).equals("syllabus.html")) {
                String navBarTitle = "Syllabus";
                String htmlName = filesSize.get(i);
                String jsName = JsSize.get(i);
                item = new CourseDetailsItems(true, navBarTitle, htmlName, jsName);
                courseItem.add(item);
            }
            if (filesSize.get(i).equals("schedule.html")) {
                String navBarTitle = "Schedule";
                String htmlName = filesSize.get(i);
                String jsName = JsSize.get(i);
                item = new CourseDetailsItems(true, navBarTitle, htmlName, jsName);
                courseItem.add(item);
            }
            if (filesSize.get(i).equals("hws.html")) {
                String navBarTitle = "HWs";
                String htmlName = filesSize.get(i);
                String jsName = JsSize.get(i);
                item = new CourseDetailsItems(true, navBarTitle, htmlName, jsName);
                courseItem.add(item);
            }
            if (filesSize.get(i).equals("projects.html")) {
                String navBarTitle = "Projects";
                String htmlName = filesSize.get(i);
                String jsName = JsSize.get(i);
                item = new CourseDetailsItems(true, navBarTitle, htmlName, jsName);
                courseItem.add(item);
            }

        }

    }

    public void addCourseSite(Boolean use, String navBar, String file, String script) {
        CourseDetailsItems item = new CourseDetailsItems(use, navBar, file, script);
        courseItem.add(item);
    }

    public void loadCourseBannerInfo(String subject, String semester, String number, String year, String title) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetailsWorkspace().getSubjectCombo().setValue(subject);
        workspace.getCourseDetailsWorkspace().getSemesterCombo().setValue(semester);
        workspace.getCourseDetailsWorkspace().getNumberCombo().setValue(number);
        workspace.getCourseDetailsWorkspace().getYearCombo().setValue(year);
        workspace.getCourseDetailsWorkspace().getTitleText().setText(title);
        courseInfo.setCourseNumber(number);
        courseInfo.setCourseSemester(semester);
        courseInfo.setCourseTitle(title);
        courseInfo.setSubject(subject);
        courseInfo.setCourseYear(year);

        courseInfoItem.add(courseInfo);
        workspace.getCourseDetailsWorkspace().reloadWorkspace("");
    }

    public void setSiteDirLabel(String s) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetailsWorkspace().reloadWorkspace(s);
        siteDir = s;
        AppGUI.setString(siteDir);
    }

    public void loadCourseInstructor(String instructorName, String instructorHome, String exportDir) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getCourseDetailsWorkspace().getInstructoNameText().setText(instructorName);
        workspace.getCourseDetailsWorkspace().getInstructoHomeText().setText(instructorHome);
        workspace.getCourseDetailsWorkspace().getExportDirSiteLabel().setText(exportDir);
        AppGUI.setString2(exportDir);
        courseInfo.setCourseInstructorName(instructorName);
        courseInfo.setCourseInstrcutorHome(instructorHome);
        courseInfo.setCourseExportDir(exportDir);
    }

    public String getSiteDir() {
        return siteDir;
    }

    public void addCourseInfo(CourseInfoItem info) {
        courseInfoItem.clear();
        courseInfoItem.add(info);
    }

    public ObservableList getCourseInfoItemList() {
        return courseInfoItem;
    }

    public void setCourseInfo(CourseInfoItem courseInfoItem) {
        this.courseInfo = courseInfoItem;
    }

    public CourseInfoItem getCourseInfoItem() {
        return courseInfo;
    }

    public ObservableList getCourseItem() {
        return courseItem;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        this.imagePath = imagePath;
        workspace.getCourseDetailsWorkspace().setImageView(imagePath);
        
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public void setImagePath2(String imagePath) {
      CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
       this.imagePath2 = imagePath;
        workspace.getCourseDetailsWorkspace().setImage2View(imagePath);
    }

    public String getImagePath3() {
        return imagePath3;
    }

    public void setImagePath3(String imagePath) {
       CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        this.imagePath3 = imagePath;
        workspace.getCourseDetailsWorkspace().setImage3View(imagePath);
    }

    public List<String> getHTMLfiles(String directory) {
        directory = directory.concat("\\public_html");
        List<String> htmlFiles = new ArrayList<>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().toLowerCase().endsWith((".html"))) {
                htmlFiles.add(file.getName());
            }
        }
        return htmlFiles;
    }

    public List<String> getJSfiles(String directory) {
        directory = directory.concat("\\public_html\\js");
        List<String> jsFiles = new ArrayList<>();
        File dir = new File(directory);
        for (File file : dir.listFiles()) {
            if (file.getName().toLowerCase().endsWith((".js"))) {
                jsFiles.add(file.getName());
            }
        }
        return jsFiles;
    }

}
