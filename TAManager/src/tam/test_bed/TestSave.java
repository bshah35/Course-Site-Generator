/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import djf.components.AppDataComponent;
import static djf.settings.AppPropertyType.ABOUT_CSG_MESSAGE;
import djf.ui.AppMessageDialogSingleton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tam.data.CSGData;
import tam.data.RecitationItems;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.ScheduleTypeItems;
import tam.data.ProjectTeamItems;
import tam.data.ProjectStudentItems;
import tam.data.ProjectTeamExport;
import tam.data.ProjectsDataExport;
import tam.data.ScheduleItems;
import tam.file.TAFiles;
import tam.file.TimeSlot;
import java.awt.Color;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.scene.control.DatePicker;
import properties_manager.PropertiesManager;

/**
 *
 * @author Bilal
 */
public class TestSave {

    static ObservableList<TeachingAssistant> ta;
    static ObservableList<RecitationItems> recitation;
    static ObservableList<ScheduleItems> scheduleTable;
    static ObservableList<ScheduleTypeItems> scheduleType;
    static ObservableList<ProjectTeamItems> teams;
    static ObservableList<ProjectStudentItems> students;
    static ObservableList<TimeSlot> officeHours;
    static ObservableList<ProjectTeamExport> teamExport;
    static ObservableList<ProjectsDataExport> studentExport;
    static ObservableList<CSGData> csgData;
    static int startHour, endHour, startingMondayMonth, startingMondayDay, endingFridayMonth, endingFridayDay;
    static CSGData data = new CSGData();
    static TAFiles fileManager;

    public static void saveDesign() {

        fileManager = new TAFiles();
        ta = FXCollections.observableArrayList();
        recitation = FXCollections.observableArrayList();
        officeHours = FXCollections.observableArrayList();
        scheduleTable = FXCollections.observableArrayList();
        scheduleType = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        teamExport = FXCollections.observableArrayList();
        studentExport = FXCollections.observableArrayList();
        startHour = 8;
        endHour = 23;
        startingMondayMonth = 1;
        startingMondayDay = 23;
        endingFridayMonth = 5;
        endingFridayDay = 19;
        csgData = FXCollections.observableArrayList();

        officeHours.addAll(
                new TimeSlot("FRIDAY", "10_30pm", "Aarin Chase"),
                new TimeSlot("TUESDAY", "8_30am", "Hasib Rezoan"),
                new TimeSlot("MONDAY", "11_30am", "Saeid Kader"),
                new TimeSlot("THURSDAY", "9_00pm", "Hsiang-Ju Lai"),
                new TimeSlot("WEDNESDAY", "7_00pm", "Michael Mathew"),
                new TimeSlot("FRIDAY", "3_00pm", "Jacob Evans"));

        ta.addAll(
                new TeachingAssistant("John Doe", "john.doe@stonybrook.edu", true),
                new TeachingAssistant("Bilal Shah", "bilal.shah@stonybrook.edu", true),
                new TeachingAssistant("Saeid Kader", "asm.kader@stonybrook.edu", true),
                new TeachingAssistant("Hasib Rezoan", "hasib.rezoan@stonybrook.edu", true),
                new TeachingAssistant("Ryan Tam", "ryan.tam.rezoan@stonybrook.edu", true),
                new TeachingAssistant("Dachi Vardidze", "dachi.vardidze@stonybrook.edu", false));

        recitation.addAll(
                new RecitationItems("R01", "Richard Mckenna", "Mondays, 3:30pm-4:23pm", "Old Computer Science 2114", "Wonyong Jeong", "Jane Doe"),
                new RecitationItems("R02", "Richard Mckenna", "Wednesdays, 3:30pm-4:23pm", "Old Computer Science 2114", "Jerrel Sogoni", "Abu Saeid"),
                new RecitationItems("R03", "Richard Mckenna", "Mondays, 5:30pm-6:23pm", "Old Computer Science 2114", "James Hoffman", "Bilal Shah"),
                new RecitationItems("R04", "Ritwik Banerjee", "Thursdays, 5:30pm-6:23pm", "Old Computer Science 2114", "Milto Ndrenika", "Hasib Rezoan"),
                new RecitationItems("R05", "Ritwik Banerjee", "Tuesdays, 5:30pm-6:23pm", "Old Computer Science 2113", "Michael Mathew", "Ryan Tam"),
                new RecitationItems("R06", "Ritwik Banerjee", "Tuesdays, 4:00pm-4:53pm", "Old Computer Science 2114", "Ali Afzal", "Dachi Vardidze"));

        scheduleTable.addAll(
                new ScheduleItems("Holiday", "02/09/2017", "SNOW DAY", "11:59pm", "Something", "http://funnybizblog.com/funny-stuff/calvin-hobbes-snowman-cartoons", "", "1", "23"),
                new ScheduleItems("Lecture", "02/14/2017", "Lecture 3", "12:00am", "Event Programming", "http://funnybizblog.com/funny-stuff/calvin-hobbes-snowman-cartoons", "", "1", "24"),
                new ScheduleItems("Holiday", "03/12/2017", "Spring Break", "11:00am", "Event Programming", "http://funnybizblog.com/funny-stuff/calvin-hobbes-snowman-cartoons", "", "1", "23"),
                new ScheduleItems("HW", "03/27/2017", "HW3", "10:00am", "UML", "https://www.youtube.com/watch?v=wKGX8EbYarU", "", "3", "23"),
                new ScheduleItems("HW", "04/14/2017", "HW4", "12:01am", "Hard HW", "https://docs.google.com/spreadsheets/d/1hDY9OR4Nu5p2XLd3mkTB93y78Bnk3LInO0Ydkrz8gZg", "sddssdsdsd", "3", "23"),
                new ScheduleItems("Lecture", "04/12/2017", "Lecture 4", "2:30pm", "Hard Lecture", "https://docs.google.com/spreadsheets/d/1hDY9OR4Nu5p2XLd3mkTB93y78Bnk3LInO0Ydkrz8gZg", "sddssdsdsd", "3", "23"));
                
  
                 scheduleType.addAll(
                        new ScheduleTypeItems("2", "SNOW DAY", "http://funnybizblog.com/funny-stuff/calvin-hobbes-snowman-cartoons", "9", "", ""),
                        new ScheduleTypeItems("2", "Lecture 3", "http://www.dol.gov/laborday/history.htm", "14", "", ""),
                        new ScheduleTypeItems("3", "Holiday", "https://www.youtube.com/watch?v=wKGX8EbYarU", "12", "https://docs.google.com/spreadsheets/d/191YCoIl5dfRgE_R7uHcYsHl0g5sp6--0xDVmav8svcE", ""),
                        new ScheduleTypeItems("3", "HW3", "", "27", "https://docs.google.com/spreadsheets/d/1nznBvuw7oNWf0aOb8ywmFlqYXYL5Kw7eALihp6Zhlzc", ""),
                        new ScheduleTypeItems("4", "HW4", "", "14", "https://docs.google.com/spreadsheets/d/1hDY9OR4Nu5p2XLd3mkTB93y78Bnk3LInO0Ydkrz8gZg", ""),
                        new ScheduleTypeItems("4", "Lecture 4", "", "25", "", ""));

        teams.addAll(
                new ProjectTeamItems("Atomic Comics", "55211#", "ffffff#", "http://atomicomic.com"),
                new ProjectTeamItems("C4 Comics", "235399#", "ffffff#", "https://c4-comics.appspot.com"),
                new ProjectTeamItems("Comx Central", "525322#", "ffffff#", "https://comxcentral.appspot.com"));

        students.addAll(
                new ProjectStudentItems("Beau", "Brummell", "Atomic Comics", "Lead Designer"),
                new ProjectStudentItems("Jane", "Doe", "C4 Comics", "Lead Programmer"),
                new ProjectStudentItems("Noonian", "Soong", "Atomic Comics", "Data Designer"));

        teamExport.addAll(
                new ProjectTeamExport("Aqua", "0", "255", "255", "black"),
                new ProjectTeamExport("Aquamarine", "127", "255", "212", "black"),
                new ProjectTeamExport("Battery Charge Blue", "103", "200", "255", "black"),
                new ProjectTeamExport("Black", "0", "0", "0", "white"),
                new ProjectTeamExport("Blurple", "65", "0", "255", "white"));
        ArrayList<String> student = new ArrayList<>();
        student.add("Beau Brumell");
        student.add("Noonian Soong");

        ArrayList<String> student2 = new ArrayList<>();
        student2.add("Jane Doe");

        studentExport.addAll(
                new ProjectsDataExport("Atomic Comics", student, "http://atomicomic.com"),
                new ProjectsDataExport("C4 Comics", student2, "https://c4-comics.appspot.com"));

        data.setProjectsTeamExport(teamExport);
        data.setProjectsDataExport(studentExport);
        csgData.addAll(new CSGData(startHour, endHour, startingMondayDay, startingMondayMonth, endingFridayDay, endingFridayMonth, officeHours, ta, recitation, scheduleTable, students, teams));

    }

    public int getStart() {
        return startHour;
    }

    public int getEnd() {
        return endHour;
    }

    public ObservableList<TeachingAssistant> getTA() {
        return ta;
    }

    public ObservableList<RecitationItems> getRecitation() {
        return recitation;
    }

    public ObservableList<TimeSlot> getOfficeHours() {
        return officeHours;
    }

    public ObservableList getScheduleItem() {
        return scheduleTable;
    }

    public ObservableList getScheduleTypeItem() {
        return scheduleType;
    }

    public ObservableList getProjectsTeamTable() {
        return teams;
    }

    public ObservableList getProjectsStudentTable() {
        return students;
    }

    public static void main(String[] args) throws ParseException {
      /*  saveDesign();
        try {
            fileManager.saveDataTest(data, csgData, "../TAManager/work/SiteSaveTest.json");
        } catch (IOException ex) {
            Logger.getLogger(TestSave.class.getName()).log(Level.SEVERE, null, ex);
        }
*/          String s = "804d80";
            System.out.println(s.substring(0,6));
    }

}
