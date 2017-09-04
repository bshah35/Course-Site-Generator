/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.awt.Color;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import static tam.TAManagerProp.DELETE_MESSAGE;
import static tam.TAManagerProp.MISSING_LINK_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_LINK_NAME_TITLE;
import static tam.TAManagerProp.PROJECTS_DELETE_TITLE;
import static tam.TAManagerProp.PROJECTS_NAME_MISSING_MESSAGE;
import static tam.TAManagerProp.PROJECTS_NAME_TITLE;
import static tam.TAManagerProp.PROJECTS_ROLE_MESSAGE;
import static tam.TAManagerProp.PROJECTS_ROLE_TITLE;
import tam.data.AddProjectsStudents_Transaction;
import tam.data.AddProjectsTeam_Transaction;
import tam.data.CSGData;
import tam.data.EditProjectStudent_Transaction;
import tam.data.EditProjectTeam_Transaction;
import tam.data.ProjectStudentItems;
import tam.data.ProjectTeamExport;
import tam.data.ProjectTeamItems;
import tam.data.ProjectsData;
import tam.data.RemoveProjectStudent_Transaction;
import tam.data.RemoveProjectTeam_Transaction;
import tam.test_bed.ColorName;

/**
 *
 * @author Bilal
 */
public class ProjectsController {

    TAManagerApp app;
    static jTPS jtps = new jTPS();
    TableView projects_team = new TableView();
    TableView projects_students = new TableView();

    public ProjectsController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    public void handleAddTeam() throws IOException {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData csgData = (CSGData) app.getDataComponent();
        ProjectsData data = csgData.getProjectsData();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        TextField nameTextField = workspace.getProjectWorkspace().teamNameText;
        TextField linkTextField = workspace.getProjectWorkspace().teamLinkText;

        String name = nameTextField.getText();
        String link = linkTextField.getText();
        String color = workspace.getProjectWorkspace().color;

        String textColor = workspace.getProjectWorkspace().textColor;

        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_NAME_TITLE), props.getProperty(PROJECTS_NAME_MISSING_MESSAGE));
        } else if (link.isEmpty() || !validateLink(link)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
        } else if (color == null) {

        } else if (textColor == null) {

        } else {
            color = color.replaceAll("0x", "#");
            if (color.length() > 6) {
                color = color.substring(0, 7);
            }

            textColor = textColor.replaceAll("0x", "#");
            if (textColor.length() > 6) {
                textColor = textColor.substring(0, 7);

            }
            csgData.getProjectsData().addTeam(name, textColor, textColor, link, false);
            jTPS_Transaction add;
            ProjectTeamItems projectsTeam = new ProjectTeamItems(name, color, textColor, link);
            add = new AddProjectsTeam_Transaction(data.getProjectsTeamTable(), projectsTeam);
            jtps.addTransaction(add);
        }

    }

    public void handleEditProjectTeam() throws IOException {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData csgData = (CSGData) app.getDataComponent();
        TableView teamTable = workspace.getProjectWorkspace().getProjectTeamTable();
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        ProjectTeamItems team = (ProjectTeamItems) selectedItem;

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        TextField nameTextField = workspace.getProjectWorkspace().teamNameText;
        TextField linkTextField = workspace.getProjectWorkspace().teamLinkText;

        String newName = nameTextField.getText();
        String newLink = linkTextField.getText();
        String newColor = workspace.getProjectWorkspace().color;
        newColor = newColor.replaceAll("0x", "#");
        if (newColor.length() > 6) {
            newColor = newColor.substring(0, 7);
        }
        String newTextColor = workspace.getProjectWorkspace().textColor;
        newTextColor = newTextColor.replaceAll("0x", "#");
        if (newTextColor.length() > 6) {
            newTextColor = newTextColor.substring(0, 7);
        }
        String oldName = team.getTeamName();
        String oldLink = team.getTeamLink();
        String oldColor = team.getTeamColor();
        String oldTextColor = team.getTeamTextColor();

        if (newName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_NAME_TITLE), props.getProperty(PROJECTS_NAME_MISSING_MESSAGE));
        } else if (newColor.isEmpty()) {

        } else if (newTextColor.isEmpty()) {

        } else if (newLink.isEmpty() || !validateLink(newLink)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
        } else {
            String colorHex = newColor.replaceAll("#", "");
            String color2Hex = newTextColor.replaceAll("#", "");
            int x = Integer.parseInt(colorHex, 16);
            int c = Integer.parseInt(color2Hex, 16);
            Color color = new Color(x);
            int r, g, b;
            r = color.getRed();
            g = color.getGreen();
            b = color.getBlue();
            String textColor = getColorNameFromHex(c);
            ProjectTeamExport export = null;
            String oldRed = "";
            String oldGreen = "";
            String oldBlue = "";
            String oldExportTextColor = "";
            for (int i = 0; i < csgData.getProjectsData().getProjectsTeamExport().size(); i++) {
                export = (ProjectTeamExport) csgData.getProjectsData().getProjectsTeamExport().get(i);
                if (export.getName().equals(team.getTeamName())) {
                    oldRed = export.getRed();
                    oldGreen = export.getGreen();
                    oldBlue = export.getBlue();
                    oldExportTextColor = export.getTextColor();
                    break;
                }
            }

            String newRed = Integer.toString(r);
            String newGreen = Integer.toString(g);
            String newBlue = Integer.toString(b);
            jTPS_Transaction edit;
            edit = new EditProjectTeam_Transaction(team, export, newRed, newGreen, newBlue, textColor, oldRed, oldGreen, oldBlue, oldExportTextColor, csgData, newName, newColor,
                    newTextColor, newLink, oldName, oldColor, oldTextColor, oldLink, teamTable);
            jtps.addTransaction(edit);
        }

    }

    public void handleDeletePress() {
        CSGData csgData = (CSGData) app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView teamTable = workspace.getProjectWorkspace().getProjectTeamTable();
        if (!teamTable.getItems().isEmpty()) {
            Object selectedItem = teamTable.getSelectionModel().getSelectedItem();

            if (selectedItem != null && validateDelete()) {
                ProjectTeamItems team = (ProjectTeamItems) selectedItem;
                ProjectsData data = csgData.getProjectsData();

                ProjectTeamExport export = null;
                for (int i = 0; i < csgData.getProjectsData().getProjectsTeamExport().size(); i++) {
                    export = (ProjectTeamExport) csgData.getProjectsData().getProjectsTeamExport().get(i);
                    if (export.getName().equals(team.getTeamName())) {
                        break;
                    }

                }
                jTPS_Transaction remove = new RemoveProjectTeam_Transaction(data.getProjectsTeamTable(), team, data.getProjectsTeamExport(), export,csgData,workspace);
                jtps.addTransaction(remove);
                teamTable.getItems().remove(selectedItem);
                workspace.projectWorkspace.reloadWorkspace();
            }
        }
    }

    public void handleAddStudents() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData csgData = (CSGData) app.getDataComponent();
        ProjectsData data = csgData.getProjectsData();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        TextField firstNameField = workspace.getProjectWorkspace().firstNameText;
        TextField lastNameTextField = workspace.getProjectWorkspace().lastNameText;
        TextField roleTextField = workspace.getProjectWorkspace().roleText;

        String fName = firstNameField.getText();
        String lName = lastNameTextField.getText();
        String role = roleTextField.getText();
        String combo = workspace.getProjectWorkspace().teamSelect;

        if (fName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_NAME_TITLE), props.getProperty(PROJECTS_NAME_MISSING_MESSAGE));
        } else if (lName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_NAME_TITLE), props.getProperty(PROJECTS_NAME_MISSING_MESSAGE));
        } else if (role.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_ROLE_TITLE), props.getProperty(PROJECTS_ROLE_MESSAGE));
        } else if (combo == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show("Team Select", "Team not selected");
        } else {
            jTPS_Transaction add;
            ProjectStudentItems projectsStudent = new ProjectStudentItems(fName, lName, combo, role);
            add = new AddProjectsStudents_Transaction(data.getProjectsStudentTable(), projectsStudent);
            jtps.addTransaction(add);
            csgData.getProjectsData().addStudents(fName, lName, combo, role, true,false);
        }

    }

    public void handleEditProjectStudent() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();

        TableView studentTable = workspace.getProjectWorkspace().getStudentsTeamTable();
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        ProjectStudentItems student = (ProjectStudentItems) selectedItem;
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        TextField firstNameField = workspace.getProjectWorkspace().firstNameText;
        TextField lastNameTextField = workspace.getProjectWorkspace().lastNameText;
        TextField roleTextField = workspace.getProjectWorkspace().roleText;

        String newFName = firstNameField.getText();
        String newLName = lastNameTextField.getText();
        String newRole = roleTextField.getText();
        String newCombo = workspace.getProjectWorkspace().teamSelect;
        String oldFName = student.getFirstName();
        String oldLName = student.getLastName();
        String oldRole = student.getRole();
        String oldCombo = student.getTeam();

        if (newFName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_NAME_TITLE), props.getProperty(PROJECTS_NAME_MISSING_MESSAGE));
        } else if (newLName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_NAME_TITLE), props.getProperty(PROJECTS_NAME_MISSING_MESSAGE));
        } else if (newRole.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(PROJECTS_ROLE_TITLE), props.getProperty(PROJECTS_ROLE_MESSAGE));
        } else if (newCombo == null) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show("Team Select", "Team not selected");
        } else {
            jTPS_Transaction edit;
            edit = new EditProjectStudent_Transaction(student, newFName, newLName, newCombo, newRole, oldFName, oldLName, oldCombo, oldRole, studentTable);
            jtps.addTransaction(edit);
        }
    }

    public void handleDeleteStudentPress() {
        CSGData csgData = (CSGData) app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView studentTable = workspace.getProjectWorkspace().getStudentsTeamTable();
        if (!studentTable.getItems().isEmpty()) {
            Object selectedItem = studentTable.getSelectionModel().getSelectedItem();

            if (selectedItem != null && validateDelete()) {
                ProjectStudentItems student = (ProjectStudentItems) selectedItem;
                ProjectsData data = csgData.getProjectsData();

                jTPS_Transaction remove = new RemoveProjectStudent_Transaction(data.getProjectsStudentTable(), student);
                jtps.addTransaction(remove);
                studentTable.getItems().remove(selectedItem);

            }
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

    private boolean validateDelete() {
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.setMinWidth(450);
        yesNoDialog.show(props.getProperty(PROJECTS_DELETE_TITLE), props.getProperty(DELETE_MESSAGE));
        String selection = yesNoDialog.getSelection();

        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convert hexColor to rgb, then call getColorNameFromRgb(r, g, b)
     *
     * @param hexColor
     * @return
     */
    public String getColorNameFromHex(int hexColor) {
        int red = (hexColor & 0xFF0000) >> 16;
        int grn = (hexColor & 0xFF00) >> 8;
        int blue = (hexColor & 0xFF);
        return getColorNameFromRgb(red, grn, blue).toLowerCase();
    }

    public String getColorNameFromRgb(int r, int g, int b) {
        ArrayList<ColorName> colorList = initColorList();
        ColorName closestMatch = null;
        int minMSE = Integer.MAX_VALUE;
        int mse;
        for (ColorName c : colorList) {
            mse = c.computeMSE(r, g, b);
            if (mse < minMSE) {
                minMSE = mse;
                closestMatch = c;
            }
        }

        if (closestMatch != null) {
            return closestMatch.getName();
        } else {
            return "No matched color name.";
        }
    }

    private ArrayList<ColorName> initColorList() {
        ArrayList<ColorName> colorList = new ArrayList<>();
        colorList.add(new ColorName("AliceBlue", 0xF0, 0xF8, 0xFF));
        colorList.add(new ColorName("AntiqueWhite", 0xFA, 0xEB, 0xD7));
        colorList.add(new ColorName("Aqua", 0x00, 0xFF, 0xFF));
        colorList.add(new ColorName("Aquamarine", 0x7F, 0xFF, 0xD4));
        colorList.add(new ColorName("Azure", 0xF0, 0xFF, 0xFF));
        colorList.add(new ColorName("Beige", 0xF5, 0xF5, 0xDC));
        colorList.add(new ColorName("Bisque", 0xFF, 0xE4, 0xC4));
        colorList.add(new ColorName("Black", 0x00, 0x00, 0x00));
        colorList.add(new ColorName("BlanchedAlmond", 0xFF, 0xEB, 0xCD));
        colorList.add(new ColorName("Blue", 0x00, 0x00, 0xFF));
        colorList.add(new ColorName("BlueViolet", 0x8A, 0x2B, 0xE2));
        colorList.add(new ColorName("Brown", 0xA5, 0x2A, 0x2A));
        colorList.add(new ColorName("BurlyWood", 0xDE, 0xB8, 0x87));
        colorList.add(new ColorName("CadetBlue", 0x5F, 0x9E, 0xA0));
        colorList.add(new ColorName("Chartreuse", 0x7F, 0xFF, 0x00));
        colorList.add(new ColorName("Chocolate", 0xD2, 0x69, 0x1E));
        colorList.add(new ColorName("Coral", 0xFF, 0x7F, 0x50));
        colorList.add(new ColorName("CornflowerBlue", 0x64, 0x95, 0xED));
        colorList.add(new ColorName("Cornsilk", 0xFF, 0xF8, 0xDC));
        colorList.add(new ColorName("Crimson", 0xDC, 0x14, 0x3C));
        colorList.add(new ColorName("Cyan", 0x00, 0xFF, 0xFF));
        colorList.add(new ColorName("DarkBlue", 0x00, 0x00, 0x8B));
        colorList.add(new ColorName("DarkCyan", 0x00, 0x8B, 0x8B));
        colorList.add(new ColorName("DarkGoldenRod", 0xB8, 0x86, 0x0B));
        colorList.add(new ColorName("DarkGray", 0xA9, 0xA9, 0xA9));
        colorList.add(new ColorName("DarkGreen", 0x00, 0x64, 0x00));
        colorList.add(new ColorName("DarkKhaki", 0xBD, 0xB7, 0x6B));
        colorList.add(new ColorName("DarkMagenta", 0x8B, 0x00, 0x8B));
        colorList.add(new ColorName("DarkOliveGreen", 0x55, 0x6B, 0x2F));
        colorList.add(new ColorName("DarkOrange", 0xFF, 0x8C, 0x00));
        colorList.add(new ColorName("DarkOrchid", 0x99, 0x32, 0xCC));
        colorList.add(new ColorName("DarkRed", 0x8B, 0x00, 0x00));
        colorList.add(new ColorName("DarkSalmon", 0xE9, 0x96, 0x7A));
        colorList.add(new ColorName("DarkSeaGreen", 0x8F, 0xBC, 0x8F));
        colorList.add(new ColorName("DarkSlateBlue", 0x48, 0x3D, 0x8B));
        colorList.add(new ColorName("DarkSlateGray", 0x2F, 0x4F, 0x4F));
        colorList.add(new ColorName("DarkTurquoise", 0x00, 0xCE, 0xD1));
        colorList.add(new ColorName("DarkViolet", 0x94, 0x00, 0xD3));
        colorList.add(new ColorName("DeepPink", 0xFF, 0x14, 0x93));
        colorList.add(new ColorName("DeepSkyBlue", 0x00, 0xBF, 0xFF));
        colorList.add(new ColorName("DimGray", 0x69, 0x69, 0x69));
        colorList.add(new ColorName("DodgerBlue", 0x1E, 0x90, 0xFF));
        colorList.add(new ColorName("FireBrick", 0xB2, 0x22, 0x22));
        colorList.add(new ColorName("FloralWhite", 0xFF, 0xFA, 0xF0));
        colorList.add(new ColorName("ForestGreen", 0x22, 0x8B, 0x22));
        colorList.add(new ColorName("Fuchsia", 0xFF, 0x00, 0xFF));
        colorList.add(new ColorName("Gainsboro", 0xDC, 0xDC, 0xDC));
        colorList.add(new ColorName("GhostWhite", 0xF8, 0xF8, 0xFF));
        colorList.add(new ColorName("Gold", 0xFF, 0xD7, 0x00));
        colorList.add(new ColorName("GoldenRod", 0xDA, 0xA5, 0x20));
        colorList.add(new ColorName("Gray", 0x80, 0x80, 0x80));
        colorList.add(new ColorName("Green", 0x00, 0x80, 0x00));
        colorList.add(new ColorName("GreenYellow", 0xAD, 0xFF, 0x2F));
        colorList.add(new ColorName("HoneyDew", 0xF0, 0xFF, 0xF0));
        colorList.add(new ColorName("HotPink", 0xFF, 0x69, 0xB4));
        colorList.add(new ColorName("IndianRed", 0xCD, 0x5C, 0x5C));
        colorList.add(new ColorName("Indigo", 0x4B, 0x00, 0x82));
        colorList.add(new ColorName("Ivory", 0xFF, 0xFF, 0xF0));
        colorList.add(new ColorName("Khaki", 0xF0, 0xE6, 0x8C));
        colorList.add(new ColorName("Lavender", 0xE6, 0xE6, 0xFA));
        colorList.add(new ColorName("LavenderBlush", 0xFF, 0xF0, 0xF5));
        colorList.add(new ColorName("LawnGreen", 0x7C, 0xFC, 0x00));
        colorList.add(new ColorName("LemonChiffon", 0xFF, 0xFA, 0xCD));
        colorList.add(new ColorName("LightBlue", 0xAD, 0xD8, 0xE6));
        colorList.add(new ColorName("LightCoral", 0xF0, 0x80, 0x80));
        colorList.add(new ColorName("LightCyan", 0xE0, 0xFF, 0xFF));
        colorList.add(new ColorName("LightGoldenRodYellow", 0xFA, 0xFA, 0xD2));
        colorList.add(new ColorName("LightGray", 0xD3, 0xD3, 0xD3));
        colorList.add(new ColorName("LightGreen", 0x90, 0xEE, 0x90));
        colorList.add(new ColorName("LightPink", 0xFF, 0xB6, 0xC1));
        colorList.add(new ColorName("LightSalmon", 0xFF, 0xA0, 0x7A));
        colorList.add(new ColorName("LightSeaGreen", 0x20, 0xB2, 0xAA));
        colorList.add(new ColorName("LightSkyBlue", 0x87, 0xCE, 0xFA));
        colorList.add(new ColorName("LightSlateGray", 0x77, 0x88, 0x99));
        colorList.add(new ColorName("LightSteelBlue", 0xB0, 0xC4, 0xDE));
        colorList.add(new ColorName("LightYellow", 0xFF, 0xFF, 0xE0));
        colorList.add(new ColorName("Lime", 0x00, 0xFF, 0x00));
        colorList.add(new ColorName("LimeGreen", 0x32, 0xCD, 0x32));
        colorList.add(new ColorName("Linen", 0xFA, 0xF0, 0xE6));
        colorList.add(new ColorName("Magenta", 0xFF, 0x00, 0xFF));
        colorList.add(new ColorName("Maroon", 0x80, 0x00, 0x00));
        colorList.add(new ColorName("MediumAquaMarine", 0x66, 0xCD, 0xAA));
        colorList.add(new ColorName("MediumBlue", 0x00, 0x00, 0xCD));
        colorList.add(new ColorName("MediumOrchid", 0xBA, 0x55, 0xD3));
        colorList.add(new ColorName("MediumPurple", 0x93, 0x70, 0xDB));
        colorList.add(new ColorName("MediumSeaGreen", 0x3C, 0xB3, 0x71));
        colorList.add(new ColorName("MediumSlateBlue", 0x7B, 0x68, 0xEE));
        colorList.add(new ColorName("MediumSpringGreen", 0x00, 0xFA, 0x9A));
        colorList.add(new ColorName("MediumTurquoise", 0x48, 0xD1, 0xCC));
        colorList.add(new ColorName("MediumVioletRed", 0xC7, 0x15, 0x85));
        colorList.add(new ColorName("MidnightBlue", 0x19, 0x19, 0x70));
        colorList.add(new ColorName("MintCream", 0xF5, 0xFF, 0xFA));
        colorList.add(new ColorName("MistyRose", 0xFF, 0xE4, 0xE1));
        colorList.add(new ColorName("Moccasin", 0xFF, 0xE4, 0xB5));
        colorList.add(new ColorName("NavajoWhite", 0xFF, 0xDE, 0xAD));
        colorList.add(new ColorName("Navy", 0x00, 0x00, 0x80));
        colorList.add(new ColorName("OldLace", 0xFD, 0xF5, 0xE6));
        colorList.add(new ColorName("Olive", 0x80, 0x80, 0x00));
        colorList.add(new ColorName("OliveDrab", 0x6B, 0x8E, 0x23));
        colorList.add(new ColorName("Orange", 0xFF, 0xA5, 0x00));
        colorList.add(new ColorName("OrangeRed", 0xFF, 0x45, 0x00));
        colorList.add(new ColorName("Orchid", 0xDA, 0x70, 0xD6));
        colorList.add(new ColorName("PaleGoldenRod", 0xEE, 0xE8, 0xAA));
        colorList.add(new ColorName("PaleGreen", 0x98, 0xFB, 0x98));
        colorList.add(new ColorName("PaleTurquoise", 0xAF, 0xEE, 0xEE));
        colorList.add(new ColorName("PaleVioletRed", 0xDB, 0x70, 0x93));
        colorList.add(new ColorName("PapayaWhip", 0xFF, 0xEF, 0xD5));
        colorList.add(new ColorName("PeachPuff", 0xFF, 0xDA, 0xB9));
        colorList.add(new ColorName("Peru", 0xCD, 0x85, 0x3F));
        colorList.add(new ColorName("Pink", 0xFF, 0xC0, 0xCB));
        colorList.add(new ColorName("Plum", 0xDD, 0xA0, 0xDD));
        colorList.add(new ColorName("PowderBlue", 0xB0, 0xE0, 0xE6));
        colorList.add(new ColorName("Purple", 0x80, 0x00, 0x80));
        colorList.add(new ColorName("Red", 0xFF, 0x00, 0x00));
        colorList.add(new ColorName("RosyBrown", 0xBC, 0x8F, 0x8F));
        colorList.add(new ColorName("RoyalBlue", 0x41, 0x69, 0xE1));
        colorList.add(new ColorName("SaddleBrown", 0x8B, 0x45, 0x13));
        colorList.add(new ColorName("Salmon", 0xFA, 0x80, 0x72));
        colorList.add(new ColorName("SandyBrown", 0xF4, 0xA4, 0x60));
        colorList.add(new ColorName("SeaGreen", 0x2E, 0x8B, 0x57));
        colorList.add(new ColorName("SeaShell", 0xFF, 0xF5, 0xEE));
        colorList.add(new ColorName("Sienna", 0xA0, 0x52, 0x2D));
        colorList.add(new ColorName("Silver", 0xC0, 0xC0, 0xC0));
        colorList.add(new ColorName("SkyBlue", 0x87, 0xCE, 0xEB));
        colorList.add(new ColorName("SlateBlue", 0x6A, 0x5A, 0xCD));
        colorList.add(new ColorName("SlateGray", 0x70, 0x80, 0x90));
        colorList.add(new ColorName("Snow", 0xFF, 0xFA, 0xFA));
        colorList.add(new ColorName("SpringGreen", 0x00, 0xFF, 0x7F));
        colorList.add(new ColorName("SteelBlue", 0x46, 0x82, 0xB4));
        colorList.add(new ColorName("Tan", 0xD2, 0xB4, 0x8C));
        colorList.add(new ColorName("Teal", 0x00, 0x80, 0x80));
        colorList.add(new ColorName("Thistle", 0xD8, 0xBF, 0xD8));
        colorList.add(new ColorName("Tomato", 0xFF, 0x63, 0x47));
        colorList.add(new ColorName("Turquoise", 0x40, 0xE0, 0xD0));
        colorList.add(new ColorName("Violet", 0xEE, 0x82, 0xEE));
        colorList.add(new ColorName("Wheat", 0xF5, 0xDE, 0xB3));
        colorList.add(new ColorName("White", 0xFF, 0xFF, 0xFF));
        colorList.add(new ColorName("WhiteSmoke", 0xF5, 0xF5, 0xF5));
        colorList.add(new ColorName("Yellow", 0xFF, 0xFF, 0x00));
        colorList.add(new ColorName("YellowGreen", 0x9A, 0xCD, 0x32));
        return colorList;
    }

    public void handleUndo() {
        jtps.undoTransaction();
    }

    public void handleRedo() {
        jtps.doTransaction();
    }

}
