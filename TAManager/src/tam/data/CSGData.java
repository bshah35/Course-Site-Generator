/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import djf.components.AppDataComponent;
import javafx.collections.ObservableList;
import tam.TAManagerApp;
import tam.file.TAFiles;
import tam.file.TimeSlot;
import tam.test_bed.TestSave;

/**
 *
 * @author Bilal
 */
public class CSGData implements AppDataComponent {

    TAManagerApp app;
    TAData taData;
    RecitationData recitData;
    ScheduleData scheduleData;
    ProjectsData projectsData;
    CourseData courseData;
    ObservableList<TeachingAssistant> ta;
    ObservableList<RecitationItems> recitationItem;
    ObservableList<TimeSlot> timeSlot;
    ObservableList<ScheduleItems> scheduleItem;
    ObservableList<ScheduleTypeItems> scheduleType;
    ObservableList<ProjectStudentItems> student;
    ObservableList<ProjectTeamItems> team;
    ObservableList<ProjectTeamExport> projectsTeamExport;
    ObservableList<ProjectsDataExport> projectsDataExport;
    ObservableList<CourseDetailsItems> courseItem;
    int startHour, endHour, startingMondayMonth, startingMondayDay, endingFridayMonth, endingFridayDay, month, day;

    public CSGData() {

    }

    public CSGData(TAManagerApp initApp) {
        app = initApp;
        taData = new TAData(app);
        recitData = new RecitationData(app);
        scheduleData = new ScheduleData(app);
        projectsData = new ProjectsData(app);
        courseData = new CourseData(app);
    }

    public CSGData(int startH, int endH, int startMondayDay, int startMondayMonth, int startFridayDay, int startFridayMonth, ObservableList<TimeSlot> officeHours,
            ObservableList<TeachingAssistant> ta, ObservableList<RecitationItems> recitation, ObservableList<ScheduleItems> schedule, ObservableList<ProjectStudentItems> student,
            ObservableList<ProjectTeamItems> team) {

        this.startHour = startH;
        this.endHour = endH;
        this.ta = ta;
        this.recitationItem = recitation;
        this.timeSlot = officeHours;
        this.startingMondayDay = startMondayDay;
        this.startingMondayMonth = startMondayMonth;
        this.endingFridayDay = startFridayDay;
        this.endingFridayMonth = startFridayMonth;
        this.scheduleItem = schedule;
        this.team = team;
        this.student = student;
        
    }

    @Override
    public void resetData() {
        taData.resetData();
        recitData.resetData();
        scheduleData.resetData();

    }

    public int getStartMondayMonth() {
        return startingMondayMonth;
    }

    public int getStartMondayDay() {
        return startingMondayDay;
    }

    public int getEndingFridayMonth() {
        return endingFridayMonth;
    }

    public int getEndingFridayDay() {
        return endingFridayDay;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public ObservableList getCourseItem() {
        return courseItem;
    }

    public ObservableList getProjectsTeamExport() {
        return projectsTeamExport;
    }

    public ObservableList getProjectsDataExport() {
        return projectsDataExport;
    }

    public ObservableList getProjectTeamItem() {
        return team;
    }

    public ObservableList getProjectStudentItem() {
        return student;
    }

    public ObservableList getScheduleItem() {
        return scheduleItem;
    }

    public ObservableList getScheduleTypeItem() {
        return scheduleType;
    }

    public ObservableList getTimeSlot() {
        return timeSlot;
    }

    public ObservableList getTeachingAssistants() {
        return ta;
    }

    public ObservableList getRecitationItems() {
        return recitationItem;
    }

    public TAData getTAData() {
        return taData;
    }

    public RecitationData getRecitationData() {
        return recitData;
    }

    public ScheduleData getScheduleData() {
        return scheduleData;
    }

    public ProjectsData getProjectsData() {
        return projectsData;
    }

    public CourseData getCourseData() {
        return courseData;
    }

    public void setProjectsTeamExport(ObservableList<ProjectTeamExport> projectsTeamExport) {
        this.projectsTeamExport = projectsTeamExport;
    }

    public void setProjectsDataExport(ObservableList<ProjectsDataExport> projectsDataExport) {
        this.projectsDataExport = projectsDataExport;
    }

    public String toString() {
        String s = "";
        s += "TA DATA \n ************************************************************************************************************************************\n";
        s += "startHour:" + Integer.toString(startHour) + "\n" + "endHour:" + Integer.toString(endHour) + "\n\n";

        for (int i = 0; i < ta.size(); i++) {
            s += "name:" + ta.get(i).getName() + "\n" + "email:" + ta.get(i).getEmail() + "\n\n";
        }

        for (int i = 0; i < timeSlot.size(); i++) {
            s += "day:" + timeSlot.get(i).getDay() + "\n" + "time:" + timeSlot.get(i).getTime() + "\n" + "name:" + timeSlot.get(i).getName() + "\n\n";
        }
        s += "RECITATION DATA \n ************************************************************************************************************************************\n";
        for (int i = 0; i < recitationItem.size(); i++) {
            s += "sections:" + recitationItem.get(i).getSections() + "\n" + "instructor:" + recitationItem.get(i).getInstructor() + "\n"
                    + "day_time:" + recitationItem.get(i).getDate() + "\n" + "location:" + recitationItem.get(i).getLocation() + "\nta1:" + recitationItem.get(i).getTa1()
                    + "\nta2:" + recitationItem.get(i).getTa2() + "\n\n";
        }

        s += "SCHEDULE DATA \n ************************************************************************************************************************************\n";

        s += "startingMondayMonth:" + Integer.toString(startingMondayMonth) + "\n" + "startingMondayDay:" + Integer.toString(startingMondayDay) + "\n"
                + "endingFridayMonth:" + Integer.toString(endingFridayMonth) + "\n" + "endingFridayDay:" + Integer.toString(endingFridayDay) + "\n\n";

        for (int i = 0; i < scheduleItem.size(); i++) {
            s += "type:" + scheduleItem.get(i).getType() + "\n" + "date:" + scheduleItem.get(i).getDate() + "\n" + "title:" + scheduleItem.get(i).getTitle() + "\n"
                    + "time:" + scheduleItem.get(i).getTime() + "\n\n"  + "topic:" + scheduleItem.get(i).getTopic() + "\n\n" +  "link:" + scheduleItem.get(i).getLink() + "\n\n"
                    + "criteria:" + scheduleItem.get(i).getCriteria() + "\n\n";
        }

        s += "PROJECT DATA \n ************************************************************************************************************************************\n";

        s += "Teams:\n";
        for (int i = 0; i < team.size(); i++) {
            s += "name:" + team.get(i).getTeamName() + "\n" + "color:" + team.get(i).getTeamName() + "\n" + "text color:" + team.get(i).getTeamTextColor() + "\n"
                    + "link:" + team.get(i).getTeamLink() + "\n\n";
        }

        s += "Students:\n";
        for (int i = 0; i < student.size(); i++) {
            s += "first name:" + student.get(i).getFirstName() + "\n" + "last name:" + student.get(i).getLastName() + "\n" + "team:" + student.get(i).getTeam() + "\n"
                    + "role:" + student.get(i).getRole() + "\n\n";
        }

        return s;
    }
}
