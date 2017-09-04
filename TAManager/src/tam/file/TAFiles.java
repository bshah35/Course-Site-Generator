package tam.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import tam.TAManagerApp;
import tam.data.CSGData;
import tam.data.CourseDetailsItems;
import tam.data.CourseInfoItem;
import tam.data.ProjectStudentItems;
import tam.data.ProjectTeamExport;
import tam.data.ProjectTeamItems;
import tam.data.ProjectsDataExport;
import tam.data.RecitationData;
import tam.data.RecitationItems;
import tam.data.ScheduleItems;
import tam.data.ScheduleTypeItems;
import tam.data.TAData;
import tam.data.TeachingAssistant;

/**
 * This class serves as the file component for the TA manager app. It provides
 * all saving and loading services for the application.
 *
 * Updated by: Bilal Shah
 */
public class TAFiles implements AppFileComponent {

    // THIS IS THE APP ITSELF
    TAManagerApp app;
    static String prettyPrinted;
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_EMAIL = "email";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_GRAD_TAS = "grad_tas";
    static final String JSON_IS_UNDERGRAD = "isUnderGrad";
    static final String JSON_RECITATION_SECTION = "section";
    static final String JSON_DAY_TIME = "day_time";
    static final String JSON_TA1 = "ta_1";
    static final String JSON_TA2 = "ta_2";
    static final String JSON_INSTRUCTOR = "instructor";
    static final String JSON_LOCATION = "location";
    static final String JSON_RECITATION = "recitations";
    static final String JSON_SCHEDULE_START_MONDAY_MONTH = "startingMondayMonth";
    static final String JSON_SCHEDULE_START_MONDAY_DAY = "startingMondayDay";
    static final String JSON_SCHEDULE_END_FRIDAY_MONTH = "endingFridayMonth";
    static final String JSON_SCHEDULE_END_FRIDAY_DAY = "endingFridayDay";
    static final String JSON_SCHEDULE_HOLIDAY_ARRAY = "holidays";
    static final String JSON_SCHEDULE_RECITATIONS_ARRAY = "recitations";
    static final String JSON_SCHEDULE_LECTURES_ARRAY = "lectures";
    static final String JSON_SCHEDULE_REFERENCES_ARRAY = "references";
    static final String JSON_SCHEDULE_HWS_ARRAY = "hws";
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    static final String JSON_SCHEDULE_TYPE = "type";
    static final String JSON_SCHEDULE_DATE = "date";
    static final String JSON_SCHEDULE_TITLE = "title";
    static final String JSON_SCHEDULE_TOPIC = "topic";
    static final String JSON_SCHEDULE_MONTH = "month";
    static final String JSON_SCHEDULE_DAY = "day";
    static final String JSON_SCHEDULE_LINK = "link";
    static final String JSON_SCHEDULE_CRITERIA = "criteria";
    static final String JSON_SCHEDULE_TIME = "time";
    static final String JSON_PROJECT_LINK = "link";
    static final String JSON_PROJECT_TEAM_NAME = "name";
    static final String JSON_PROJECT_TEAM_COLOR = "color";
    static final String JSON_PROJECT_TEAM_TEXT_COLOR = "text color";
    static final String JSON_PROJECT_STUDENT_FIRST_NAME = "firstName";
    static final String JSON_PROJECT_STUDENT_LAST_NAME = "lastName";
    static final String JSON_PROJECT_STUDENT_TEAM = "team";
    static final String JSON_PROJECT_STUDENT_ROLE = "role";
    static final String JSON_PROJECT_STUDENTS = "Students";
    static final String JSON_PROJECT_TEAMS = "Teams";
    static final String JSON_PROJECT_EXPORT_STUDENTS = "students";
    static final String JSON_PROJECT_EXPORT_TEAMS = "teams";
    static final String JSON_PROJECT_TEAM_EXPORT_TEXT_COLOR = "text_color";
    static final String JSON_PROJECT_TEAM_RED = "red";
    static final String JSON_PROJECT_TEAM_GREEN = "green";
    static final String JSON_PROJECT_TEAM_BLUE = "blue";
    static final String JSON_PROJECT_TEAM_NAME_EXPORT = "name";
    static final String JSON_PROJECT_WORK = "work";
    static final String JSON_PROJECT_SEMESTER = "semester";
    static final String JSON_PROJECT = "projects";
    static final String JSON_PROJECTDATA_STUDENTS = "students";
    static final String JSON_COURSE_BANNER_ARRAY = "banner";
    static final String JSON_COURSE_SUBJECT = "subject";
    static final String JSON_COURSE_SEMESTER = "semester";
    static final String JSON_COURSE_NUMBER = "course_number";
    static final String JSON_COURSE_YEAR = "course_year";
    static final String JSON_COURSE_TITLE = "course_title";
    static final String JSON_COURSE_INSTRUCTOR_HOME = "instructor_home";
    static final String JSON_COURSE_INSTRUCTOR_ARRAY = "instructor_link";
    static final String JSON_COURSE_INSTRUCTOR_NAME = "instructor_name";
    static final String JSON_COURSE_EXPORT_DIR = "export_dir";
    static final String JSON_COURSE_SITE_PAGE_TABLE = "site_pages";
    static final String JSON_COURSE_SITE_PAGE_DIR = "site_page_dir";
    static final String JSON_COURSE_USE = "use";
    static final String JSON_COURSE_NAVBAR = "navebar";
    static final String JSON_COURSE_FILE = "file";
    static final String JSON_COURSE_SCRIPT = "script";
    static final String JSON_COURSE_IMAGE_PATH1 = "image1";
    static final String JSON_COURSE_IMAGE_PATH2 = "image2";
    static final String JSON_COURSE_IMAGE_PATH3 = "image3";

    public TAFiles() {

    }

    public TAFiles(TAManagerApp initApp) {
        app = initApp;
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        // CLEAR THE OLD DATA OUT
        CSGData dataManager = (CSGData) data;

        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.getTAData().initHours(startHour, endHour);

        // NOW RELOAD THE WORKSPACE WITH THE LOADED DATA
        app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            Boolean isUnderGrad = jsonTA.getBoolean(JSON_IS_UNDERGRAD);
            // Boolean isunderGrad = Boolean.parseBoolean(isUnderGrad);
            dataManager.getTAData().addTA(name, email, isUnderGrad);

        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.getTAData().addOfficeHoursReservation(day, time, name);
        }

        //LOAD THE RECITATION
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATION);
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            String section = jsonRecitation.getString(JSON_RECITATION_SECTION);
            String instructor = jsonRecitation.getString(JSON_INSTRUCTOR);
            String day_time = jsonRecitation.getString(JSON_DAY_TIME);
            String location = jsonRecitation.getString(JSON_LOCATION);
            String ta1 = jsonRecitation.getString(JSON_TA1);
            String ta2 = jsonRecitation.getString(JSON_TA2);
            if (i == jsonRecitationArray.size() - 1) {

                dataManager.getRecitationData().addRecitation(section, instructor, day_time, location, ta1, ta2, true);
            } else {
                dataManager.getRecitationData().addRecitation(section, instructor, day_time, location, ta1, ta2, false);
            }
        }

        //LOAD THE SCHEDULE
        JsonArray jsonScheduleArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            String type = jsonSchedule.getString(JSON_SCHEDULE_TYPE);
            String date = jsonSchedule.getString(JSON_SCHEDULE_DATE);
            String title = jsonSchedule.getString(JSON_SCHEDULE_TITLE);
            String topic = jsonSchedule.getString(JSON_SCHEDULE_TOPIC);
            String link = jsonSchedule.getString(JSON_SCHEDULE_LINK);
            String criteria = jsonSchedule.getString(JSON_SCHEDULE_CRITERIA);
            String time = jsonSchedule.getString(JSON_SCHEDULE_TIME);
            dataManager.getScheduleData().addSchedule(type, date, title, time, topic, link, criteria);

        }
        //LOAD THE SCHEDULE CALENDER BOUNDRIES
        String startMondayDay = json.getString(JSON_SCHEDULE_START_MONDAY_DAY);
        String startMondayMonth = json.getString(JSON_SCHEDULE_START_MONDAY_MONTH);
        String endFridayDay = json.getString(JSON_SCHEDULE_END_FRIDAY_DAY);
        String endFridayMonth = json.getString(JSON_SCHEDULE_END_FRIDAY_MONTH);
        dataManager.getScheduleData().addCalenderBoundries(startMondayDay, startMondayMonth, endFridayDay, endFridayMonth, "2017");

        //LOAD THE PROJECT TEAM
        JsonArray jsonTeamArray = json.getJsonArray(JSON_PROJECT_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String name = jsonTeam.getString(JSON_PROJECT_TEAM_NAME);
            String color = jsonTeam.getString(JSON_PROJECT_TEAM_COLOR);
            String text_color = jsonTeam.getString(JSON_PROJECT_TEAM_TEXT_COLOR);
            String link = jsonTeam.getString(JSON_PROJECT_LINK);
            dataManager.getProjectsData().addTeam(name, color, text_color, link, true);

        }

        //LOAD THE PROJECT STUDENT
        JsonArray jsonStudentArray = json.getJsonArray(JSON_PROJECT_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
            System.out.println(jsonStudentArray.size());
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            String fName = jsonStudent.getString(JSON_PROJECT_STUDENT_FIRST_NAME);
            String lName = jsonStudent.getString(JSON_PROJECT_STUDENT_LAST_NAME);
            String team_name = jsonStudent.getString(JSON_PROJECT_STUDENT_TEAM);
            String role = jsonStudent.getString(JSON_PROJECT_STUDENT_ROLE);

            if (i == jsonStudentArray.size() - 1) {
                dataManager.getProjectsData().addStudents(fName, lName, team_name, role, true, true);
            } else {
                dataManager.getProjectsData().addStudents(fName, lName, team_name, role, false, true);
            }

        }

        //LOAD COURSE INFO
        JsonArray jsonCourseInfoArray = json.getJsonArray(JSON_COURSE_BANNER_ARRAY);
        for (int i = 0; i < jsonCourseInfoArray.size(); i++) {
            JsonObject jsonInfo = jsonCourseInfoArray.getJsonObject(i);
            String subject = jsonInfo.getString(JSON_COURSE_SUBJECT);
            String semester = jsonInfo.getString(JSON_COURSE_SEMESTER);
            String year = jsonInfo.getString(JSON_COURSE_YEAR);
            String number = jsonInfo.getString(JSON_COURSE_NUMBER);
            String title = jsonInfo.getString(JSON_COURSE_TITLE);
            dataManager.getCourseData().loadCourseBannerInfo(subject, semester, number, year, title);

        }

        //LOAD COURSE INFO
        JsonArray jsonCourseInstructorArray = json.getJsonArray(JSON_COURSE_INSTRUCTOR_ARRAY);
        for (int i = 0; i < jsonCourseInstructorArray.size(); i++) {
            JsonObject jsonInfo = jsonCourseInstructorArray.getJsonObject(i);
            String instructorName = jsonInfo.getString(JSON_COURSE_INSTRUCTOR_NAME);
            String instructorHome = jsonInfo.getString(JSON_COURSE_INSTRUCTOR_HOME);
            String exportDir = jsonInfo.getString(JSON_COURSE_EXPORT_DIR);
            dataManager.getCourseData().loadCourseInstructor(instructorName, instructorHome, exportDir);

        }
        //LOAD COURSE SITE DIRECTORY
        String siteDir = json.getString(JSON_COURSE_SITE_PAGE_DIR);
        dataManager.getCourseData().setSiteDirLabel(siteDir);
        
         //LOAD COURSE SITE DIRECTORY
        String image1 = json.getString(JSON_COURSE_IMAGE_PATH1);
        dataManager.getCourseData().setImagePath(image1);

         //LOAD COURSE SITE DIRECTORY
        String image2 = json.getString(JSON_COURSE_IMAGE_PATH2);
        dataManager.getCourseData().setImagePath2(image2);

         //LOAD COURSE SITE DIRECTORY
        String image3 = json.getString(JSON_COURSE_IMAGE_PATH3);
        dataManager.getCourseData().setImagePath3(image3);

        //LOAD COURSE DETAILS
        JsonArray jsonCourseDetailArray = json.getJsonArray(JSON_COURSE_SITE_PAGE_TABLE);
        for (int i = 0; i < jsonCourseDetailArray.size(); i++) {
            JsonObject jsonInfo = jsonCourseDetailArray.getJsonObject(i);
            String isUse = jsonInfo.getString(JSON_COURSE_USE);
            String navBAr = jsonInfo.getString(JSON_COURSE_NAVBAR);
            String file = jsonInfo.getString(JSON_COURSE_FILE);
            String script = jsonInfo.getString(JSON_COURSE_SCRIPT);
            Boolean use = Boolean.parseBoolean(isUse);
            dataManager.getCourseData().addCourseSite(use, navBAr, file, script);

        }

        //LOAD COURSE PAGE STYLE
    }

    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
        InputStream is = new FileInputStream(jsonFilePath);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject json = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return json;
    }

    @Override
    public void saveData(AppDataComponent data, String filePath, int i) throws IOException {
        if (i != 0) {
            saveDataExport(data, filePath, i);
        } else {
            // GET THE DATA
            CSGData dataManager = (CSGData) data;

            JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
            ObservableList<TeachingAssistant> tas = dataManager.getTAData().getTeachingAssistants();
            for (TeachingAssistant ta : tas) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .add(JSON_IS_UNDERGRAD, ta.getUndergrad())
                        .build();
                taArrayBuilder.add(taJson);

            }
            JsonArray undergradTAsArray = taArrayBuilder.build();

            JsonArrayBuilder gradTaArrayBuilder = Json.createArrayBuilder();
            ObservableList<TeachingAssistant> gradTas = dataManager.getTAData().getTeachingAssistants();
            for (TeachingAssistant ta : gradTas) {
                if (!ta.getUndergrad()) {
                    JsonObject taJson = Json.createObjectBuilder()
                            .add(JSON_NAME, ta.getName())
                            .add(JSON_EMAIL, ta.getEmail())
                            .build();
                    gradTaArrayBuilder.add(taJson);
                }
            }
            JsonArray gradTAsArray = gradTaArrayBuilder.build();

            // NOW BUILD THE TIME SLOT JSON OBJECTS TO SAVE
            JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
            ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager.getTAData());
            for (TimeSlot ts : officeHours) {
                JsonObject tsJson = Json.createObjectBuilder()
                        .add(JSON_DAY, ts.getDay())
                        .add(JSON_TIME, ts.getTime())
                        .add(JSON_NAME, ts.getName()).build();
                timeSlotArrayBuilder.add(tsJson);
            }
            JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

            //NOW BUILD RECITATIONS
            JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
            ObservableList<RecitationItems> recitations = dataManager.getRecitationData().getRecitationTable();
            for (RecitationItems recit : recitations) {
                JsonObject recitJson = Json.createObjectBuilder()
                        .add(JSON_RECITATION_SECTION, recit.getSections())
                        .add(JSON_INSTRUCTOR, recit.getInstructor())
                        .add(JSON_DAY_TIME, recit.getDate())
                        .add(JSON_LOCATION, recit.getLocation())
                        .add(JSON_TA1, recit.getTa1())
                        .add(JSON_TA2, recit.getTa2()).build();

                recitationArrayBuilder.add(recitJson);

            }
            JsonArray recitationArray = recitationArrayBuilder.build();

            //NOW BUILD SCHEDULE
            JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
            ObservableList<ScheduleItems> schedule = dataManager.getScheduleData().getScheduleTable();
            for (ScheduleItems scheduleTable : schedule) {
                JsonObject scheduleJson = Json.createObjectBuilder()
                        .add(JSON_SCHEDULE_TYPE, scheduleTable.getType())
                        .add(JSON_SCHEDULE_DATE, scheduleTable.getDate())
                        .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle())
                        .add(JSON_SCHEDULE_TIME, scheduleTable.getTime())
                        .add(JSON_SCHEDULE_TOPIC, scheduleTable.getTopic())
                        .add(JSON_SCHEDULE_LINK, scheduleTable.getLink())
                        .add(JSON_SCHEDULE_CRITERIA, scheduleTable.getCriteria())
                        .build();
                scheduleArrayBuilder.add(scheduleJson);

            }
            JsonArray schedulenArray = scheduleArrayBuilder.build();

            //NOW BUILD PROJECTS   
            JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
            ObservableList<ProjectTeamItems> team = dataManager.getProjectsData().getProjectsTeamTable();
            for (ProjectTeamItems teamTable : team) {
                JsonObject teamJson = Json.createObjectBuilder()
                        .add(JSON_PROJECT_TEAM_NAME, teamTable.getTeamName())
                        .add(JSON_PROJECT_TEAM_COLOR, teamTable.getTeamColor())
                        .add(JSON_PROJECT_TEAM_TEXT_COLOR, teamTable.getTeamTextColor())
                        .add(JSON_PROJECT_LINK, teamTable.getTeamLink())
                        .build();

                teamArrayBuilder.add(teamJson);

            }

            JsonArray teamArray = teamArrayBuilder.build();

            JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
            ObservableList<ProjectStudentItems> student = dataManager.getProjectsData().getProjectsStudentTable();
            for (ProjectStudentItems studentTable : student) {
                JsonObject studentJson = Json.createObjectBuilder()
                        .add(JSON_PROJECT_STUDENT_FIRST_NAME, studentTable.getFirstName())
                        .add(JSON_PROJECT_STUDENT_LAST_NAME, studentTable.getLastName())
                        .add(JSON_PROJECT_STUDENT_TEAM, studentTable.getTeam())
                        .add(JSON_PROJECT_STUDENT_ROLE, studentTable.getRole())
                        .build();

                studentArrayBuilder.add(studentJson);

            }

            JsonArray studentArray = studentArrayBuilder.build();

            //COURSE INFO
            JsonArrayBuilder courseInfoArrayBuilder = Json.createArrayBuilder();
            ObservableList<CourseInfoItem> courseInfo = dataManager.getCourseData().getCourseInfoItemList();
            for (CourseInfoItem info : courseInfo) {
                JsonObject infoJson = Json.createObjectBuilder()
                        .add(JSON_COURSE_SUBJECT, info.getSubject())
                        .add(JSON_COURSE_SEMESTER, info.getCourseSemester())
                        .add(JSON_COURSE_NUMBER, info.getCourseNumber())
                        .add(JSON_COURSE_YEAR, info.getCourseYear())
                        .add(JSON_COURSE_TITLE, info.getCourseTitle())
                        .build();
                courseInfoArrayBuilder.add(infoJson);
            }
            JsonArray courseInfoArray = courseInfoArrayBuilder.build();

            //COURSE INFO
            JsonArrayBuilder courseInfoInstructorArrayBuilder = Json.createArrayBuilder();
            for (CourseInfoItem info : courseInfo) {
                JsonObject infoJson = Json.createObjectBuilder()
                        .add(JSON_COURSE_INSTRUCTOR_NAME, "" + info.getCourseInstructorName())
                        .add(JSON_COURSE_INSTRUCTOR_HOME, "" + info.getCourseInstrcutorHome())
                        .add(JSON_COURSE_EXPORT_DIR, info.getCourseExportDir())
                        .build();
                courseInfoInstructorArrayBuilder.add(infoJson);
            }
            JsonArray courseInfoInstructorArray = courseInfoInstructorArrayBuilder.build();

            //SITE PAGE TABLE
            JsonArrayBuilder sitePageArrayBuilder = Json.createArrayBuilder();
            ObservableList<CourseDetailsItems> courseSite = dataManager.getCourseData().getCourseItem();
            for (CourseDetailsItems info : courseSite) {
                JsonObject infoJson = Json.createObjectBuilder()
                        .add(JSON_COURSE_USE, "" + info.isUse())
                        .add(JSON_COURSE_NAVBAR, "" + info.getNavBar())
                        .add(JSON_COURSE_FILE, info.getFile())
                        .add(JSON_COURSE_SCRIPT, info.getScript())
                        .build();
                sitePageArrayBuilder.add(infoJson);
            }
            JsonArray sitePageArrayArray = sitePageArrayBuilder.build();

            // THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add("COURSE DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_COURSE_INSTRUCTOR_ARRAY, courseInfoInstructorArray)
                    .add(JSON_COURSE_BANNER_ARRAY, courseInfoArray)
                    .add(JSON_COURSE_SITE_PAGE_DIR, "" + dataManager.getCourseData().getSiteDir())
                    .add(JSON_COURSE_SITE_PAGE_TABLE, sitePageArrayArray)
                    .add(JSON_COURSE_IMAGE_PATH1, "" + dataManager.getCourseData().getImagePath())
                    .add(JSON_COURSE_IMAGE_PATH2, "" + dataManager.getCourseData().getImagePath2())
                    .add(JSON_COURSE_IMAGE_PATH3, "" + dataManager.getCourseData().getImagePath3())
                    .add("\nTA DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_START_HOUR, "" + dataManager.getTAData().getStartHour())
                    .add(JSON_END_HOUR, "" + dataManager.getTAData().getEndHour())
                    .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                    .add(JSON_GRAD_TAS, gradTAsArray)
                    .add(JSON_OFFICE_HOURS, timeSlotsArray)
                    .add("RECITATION DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_RECITATION, recitationArray)
                    .add("SCHEDULE DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_SCHEDULE_START_MONDAY_DAY, "" + dataManager.getScheduleData().getStartDay())
                    .add(JSON_SCHEDULE_START_MONDAY_MONTH, "" + dataManager.getScheduleData().getStartMonth())
                    .add(JSON_SCHEDULE_END_FRIDAY_MONTH, "" + dataManager.getScheduleData().getEndMonth())
                    .add(JSON_SCHEDULE_END_FRIDAY_DAY, "" + dataManager.getScheduleData().getEndDay())
                    .add(JSON_SCHEDULE_ITEMS, schedulenArray)
                    .add("PROJECT DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_PROJECT_TEAMS, teamArray)
                    .add(JSON_PROJECT_STUDENTS, studentArray)
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }
    }

    public void saveDataTest(CSGData data, ObservableList<CSGData> csgData, String filePath) throws IOException {

        for (int i = 0; i < csgData.size(); i++) {
            CSGData csg = (CSGData) csgData.get(i);
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add("TA DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_START_HOUR, "" + csg.getStartHour())
                    .add(JSON_END_HOUR, "" + csg.getEndHour())
                    .add(JSON_UNDERGRAD_TAS, getTeachingAssistantsArray((CSGData) csgData.get(i)))
                    .add(JSON_GRAD_TAS, getGradTeachingAssistantsArray((CSGData) csgData.get(i)))
                    .add(JSON_OFFICE_HOURS, getTimeSlotArray((CSGData) csgData.get(i)))
                    .add("RECITATION DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_RECITATION, getRecitationArray((CSGData) csgData.get(i)))
                    .add("SCHEDULE DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_SCHEDULE_START_MONDAY_MONTH, "" + csg.getStartMondayMonth())
                    .add(JSON_SCHEDULE_START_MONDAY_DAY, "" + csg.getStartMondayDay())
                    .add(JSON_SCHEDULE_END_FRIDAY_MONTH, "" + csg.getEndingFridayMonth())
                    .add(JSON_SCHEDULE_END_FRIDAY_DAY, "" + csg.getEndingFridayDay())
                    .add(JSON_SCHEDULE_ITEMS, getScheduleTableArray((CSGData) csgData.get(i)))
                    .add("PROJECT DATA", "***********************************************************************************"
                            + "*************************************************************************************/")
                    .add(JSON_PROJECT_TEAMS, getProjectsTeamArray((CSGData) csgData.get(i)))
                    .add(JSON_PROJECT_STUDENTS, getProjectsStudentArray((CSGData) csgData.get(i)))
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }
    }

    private JsonArray getWorkArray(CSGData csgData) {
        JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
        JsonObject projectJson = Json.createObjectBuilder()
                .add(JSON_PROJECT_SEMESTER, "Spring 2016")
                .add(JSON_PROJECT, getProjectsDataArray(csgData))
                .build();

        projectsArrayBuilder.add(projectJson);

        JsonArray projectsArray = projectsArrayBuilder.build();
        return projectsArray;
    }

    private JsonArray getProjectsDataArray(CSGData csgData) {
        JsonArrayBuilder projectsArrayBuilder = Json.createArrayBuilder();
        int j = 0;
        ObservableList<ProjectsDataExport> projects = csgData.getProjectsData().getProjectsDataExport();
        for (ProjectsDataExport projectsTable : projects) {
            JsonObject projectJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME_EXPORT, projectsTable.teamName())
                    .add(JSON_PROJECTDATA_STUDENTS, getProjectsExportStudentArray(csgData, j))
                    .add(JSON_PROJECT_LINK, projectsTable.getLink())
                    .build();
            j++;
            projectsArrayBuilder.add(projectJson);
        }

        JsonArray projectsArray = projectsArrayBuilder.build();
        return projectsArray;
    }

    private JsonArray getProjectsExportStudentArray(CSGData csgData, int j) {

        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectsDataExport> student = csgData.getProjectsData().getProjectsDataExport();
        //for (; j < csgData.getProjectsDataExport().size();) {
        for (int i = 0; i < student.get(j).getStudents().size(); i++) {
            JsonObject studentJson = Json.createObjectBuilder()
                    .add("", student.get(j).getStudents().get(i))
                    .build();
            System.out.println(student.get(j).getStudents().get(i));

            studentArrayBuilder.add(studentJson);

        }
        // break;
        //}

        JsonArray studentArray = studentArrayBuilder.build();
        return studentArray;

    }

    private JsonArray getTeachingAssistantsArray(CSGData csgData) {
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = csgData.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            JsonObject taJson = Json.createObjectBuilder()
                    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_IS_UNDERGRAD, ta.getUndergrad()).build();
            taArrayBuilder.add(taJson);

        }
        JsonArray undergradTAsArray = taArrayBuilder.build();
        return undergradTAsArray;
    }

    private JsonArray getGradTeachingAssistantsArray(CSGData csgData) {
        JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
        ObservableList<TeachingAssistant> tas = csgData.getTeachingAssistants();
        for (TeachingAssistant ta : tas) {
            if (!ta.getUndergrad()) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .build();
                taArrayBuilder.add(taJson);
            }
        }
        JsonArray undergradTAsArray = taArrayBuilder.build();
        return undergradTAsArray;
    }

    private JsonArray getTimeSlotArray(CSGData csgData) {

        JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
        ObservableList<TimeSlot> officeHours = csgData.getTimeSlot();
        for (TimeSlot ts : officeHours) {
            JsonObject tsJson = Json.createObjectBuilder()
                    .add(JSON_DAY, ts.getDay())
                    .add(JSON_TIME, ts.getTime())
                    .add(JSON_NAME, ts.getName()).build();
            timeSlotArrayBuilder.add(tsJson);
        }
        JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        return timeSlotsArray;
    }

    private JsonArray getRecitationArray(CSGData csgData) {
        JsonArrayBuilder recitArrayBuilder = Json.createArrayBuilder();
        ObservableList<RecitationItems> recitation = csgData.getRecitationItems();
        for (RecitationItems recit : recitation) {
            JsonObject recitJson = Json.createObjectBuilder()
                    .add(JSON_RECITATION_SECTION, recit.getSections())
                    .add(JSON_INSTRUCTOR, recit.getInstructor())
                    .add(JSON_DAY_TIME, recit.getDate())
                    .add(JSON_LOCATION, recit.getLocation())
                    .add(JSON_TA1, recit.getTa1())
                    .add(JSON_TA2, recit.getTa2()).build();

            recitArrayBuilder.add(recitJson);

        }

        JsonArray recitationArray = recitArrayBuilder.build();
        return recitationArray;

    }

    private JsonArray getScheduleTableArray(CSGData csgData) {
        JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
        ObservableList<ScheduleItems> schedule = csgData.getScheduleItem();
        for (ScheduleItems scheduleTable : schedule) {
            JsonObject scheduleJson = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_TYPE, scheduleTable.getType())
                    .add(JSON_SCHEDULE_DATE, scheduleTable.getDate())
                    .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle())
                    .add(JSON_SCHEDULE_TIME, scheduleTable.getTime())
                    .add(JSON_SCHEDULE_TOPIC, scheduleTable.getTopic())
                    .add(JSON_SCHEDULE_LINK, scheduleTable.getLink())
                    .add(JSON_SCHEDULE_CRITERIA, scheduleTable.getCriteria())
                    .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                    .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                    .build();

            scheduleArrayBuilder.add(scheduleJson);

        }
        JsonArray schedulenArray = scheduleArrayBuilder.build();
        return schedulenArray;

    }

    private JsonArray getProjectsTeamArray(CSGData csgData) {
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectTeamItems> team = csgData.getProjectTeamItem();
        for (ProjectTeamItems teamTable : team) {
            JsonObject teamJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_TEAM_NAME, teamTable.getTeamName())
                    .add(JSON_PROJECT_TEAM_COLOR, teamTable.getTeamColor())
                    .add(JSON_PROJECT_TEAM_TEXT_COLOR, teamTable.getTeamTextColor())
                    .add(JSON_PROJECT_LINK, teamTable.getTeamLink())
                    .build();

            teamArrayBuilder.add(teamJson);

        }

        JsonArray teamArray = teamArrayBuilder.build();
        return teamArray;

    }

    private JsonArray getProjectsStudentArray(CSGData csgData) {
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
        ObservableList<ProjectStudentItems> student = csgData.getProjectStudentItem();
        for (ProjectStudentItems studentTable : student) {
            JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_PROJECT_STUDENT_FIRST_NAME, studentTable.getFirstName())
                    .add(JSON_PROJECT_STUDENT_LAST_NAME, studentTable.getLastName())
                    .add(JSON_PROJECT_STUDENT_TEAM, studentTable.getTeam())
                    .add(JSON_PROJECT_STUDENT_ROLE, studentTable.getRole())
                    .build();

            studentArrayBuilder.add(studentJson);

        }

        JsonArray studentArray = studentArrayBuilder.build();
        return studentArray;

    }

    //EXPORT SAVE
//**************************************************************************************************************************************************************************************************************************************    
    public void saveDataExport(AppDataComponent data, String filePath, int i) throws IOException {
        // GET THE DATA
        CSGData dataManager = (CSGData) data;

        if (i == 6) {
            JsonArrayBuilder courseInfoArrayBuilder = Json.createArrayBuilder();
            ObservableList<CourseInfoItem> courseInfo = dataManager.getCourseData().getCourseInfoItemList();
            for (CourseInfoItem info : courseInfo) {
                JsonObject infoJson = Json.createObjectBuilder()
                        .add(JSON_COURSE_SUBJECT, info.getSubject())
                        .add(JSON_COURSE_SEMESTER, info.getCourseSemester())
                        .add(JSON_COURSE_NUMBER, info.getCourseNumber())
                        .add(JSON_COURSE_YEAR, info.getCourseYear())
                        .add(JSON_COURSE_TITLE, info.getCourseTitle())
                        .build();
                courseInfoArrayBuilder.add(infoJson);

            }
            JsonArray courseInfoArray = courseInfoArrayBuilder.build();

            //COURSE INFO
            JsonArrayBuilder courseInfoInstructorArrayBuilder = Json.createArrayBuilder();
            for (CourseInfoItem info : courseInfo) {
                JsonObject infoJson = Json.createObjectBuilder()
                        .add(JSON_COURSE_INSTRUCTOR_NAME, "" + info.getCourseInstructorName())
                        .add(JSON_COURSE_INSTRUCTOR_HOME, "" + info.getCourseInstrcutorHome())
                        .build();
                courseInfoInstructorArrayBuilder.add(infoJson);
            }
            JsonArray courseInfoInstructorArray = courseInfoInstructorArrayBuilder.build();

            // THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(JSON_COURSE_INSTRUCTOR_ARRAY, courseInfoInstructorArray)
                    .add(JSON_COURSE_BANNER_ARRAY, courseInfoArray)
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();

        }

        if (i == 1) {
            JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
            ObservableList<TeachingAssistant> tas = dataManager.getTAData().getTeachingAssistants();
            for (TeachingAssistant ta : tas) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .add(JSON_IS_UNDERGRAD, ta.getUndergrad())
                        .build();
                taArrayBuilder.add(taJson);

            }
            JsonArray undergradTAsArray = taArrayBuilder.build();

            JsonArrayBuilder gradTaArrayBuilder = Json.createArrayBuilder();
            ObservableList<TeachingAssistant> gradTas = dataManager.getTAData().getTeachingAssistants();
            for (TeachingAssistant ta : gradTas) {
                if (!ta.getUndergrad()) {
                    JsonObject taJson = Json.createObjectBuilder()
                            .add(JSON_NAME, ta.getName())
                            .add(JSON_EMAIL, ta.getEmail())
                            .build();
                    gradTaArrayBuilder.add(taJson);
                }
            }
            JsonArray gradTAsArray = gradTaArrayBuilder.build();

            // NOW BUILD THE TIME SLOT JSON OBJECTS TO SAVE
            JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
            ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager.getTAData());
            for (TimeSlot ts : officeHours) {
                JsonObject tsJson = Json.createObjectBuilder()
                        .add(JSON_DAY, ts.getDay())
                        .add(JSON_TIME, ts.getTime())
                        .add(JSON_NAME, ts.getName()).build();
                timeSlotArrayBuilder.add(tsJson);
            }
            JsonArray timeSlotsArray = timeSlotArrayBuilder.build();

            // THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(JSON_START_HOUR, "" + dataManager.getTAData().getStartHour())
                    .add(JSON_END_HOUR, "" + dataManager.getTAData().getEndHour())
                    .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                    .add(JSON_GRAD_TAS, gradTAsArray)
                    .add(JSON_OFFICE_HOURS, timeSlotsArray)
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }
        if (i == 2) {
            //NOW BUILD RECITATIONS
            JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
            ObservableList<RecitationItems> recitations = dataManager.getRecitationData().getRecitationTable();
            for (RecitationItems recit : recitations) {
                JsonObject recitJson = Json.createObjectBuilder()
                        .add(JSON_RECITATION_SECTION, "<strong>" + recit.getSections() + "</strong>" + " (" + recit.getInstructor() + ")")
                        .add(JSON_DAY_TIME, recit.getDate())
                        .add(JSON_LOCATION, recit.getLocation())
                        .add(JSON_TA1, recit.getTa1())
                        .add(JSON_TA2, recit.getTa2()).build();

                recitationArrayBuilder.add(recitJson);

            }
            JsonArray recitationArray = recitationArrayBuilder.build();

            // THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(JSON_RECITATION, recitationArray).build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }

        if (i == 3) {
            //HOLIDAY ARRAY
            JsonArrayBuilder scheduleArrayBuilder = Json.createArrayBuilder();
            ObservableList<ScheduleItems> schedule = dataManager.getScheduleData().getScheduleTable();
            for (ScheduleItems scheduleTable : schedule) {
                if (scheduleTable.getType().equals("Holiday")) {
                    if (scheduleTable.getTitle().equalsIgnoreCase("SNOW DAY")) {
                        JsonObject scheduleJson = Json.createObjectBuilder()
                                .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                                .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                                .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle() + "<br /><img src='./images/SnowDay.gif' />")
                                .add(JSON_SCHEDULE_LINK, scheduleTable.getLink())
                                .build();
                        scheduleArrayBuilder.add(scheduleJson);
                    } else {
                        JsonObject scheduleJson = Json.createObjectBuilder()
                                .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                                .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                                .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle() + "<br /><br /><br /><br />")
                                .add(JSON_SCHEDULE_LINK, scheduleTable.getLink())
                                .build();
                        scheduleArrayBuilder.add(scheduleJson);
                    }
                }
            }

            JsonArray scheduleArray = scheduleArrayBuilder.build();

            //LECTURES ARRAY
            JsonArrayBuilder scheduleLectureArrayBuilder = Json.createArrayBuilder();
            for (ScheduleItems scheduleTable : schedule) {
                if (scheduleTable.getType().equals("Lecture")) {
                    String temp = scheduleTable.getTopic();
                    String s = scheduleTable.getTopic();
                    s = s.replaceAll("\\s", "<br />");
                    scheduleTable.setTopic(s);
                    JsonObject scheduleJson = Json.createObjectBuilder()
                            .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                            .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                            .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle())
                            .add(JSON_SCHEDULE_TOPIC, scheduleTable.getTopic())
                            .add(JSON_SCHEDULE_LINK, scheduleTable.getLink())
                            .build();
                    scheduleLectureArrayBuilder.add(scheduleJson);
                    scheduleTable.setTopic(temp);
                }
            }

            JsonArray lectureArray = scheduleLectureArrayBuilder.build();

            //REFERENCES ARRAY
            JsonArrayBuilder scheduleRefArrayBuilder = Json.createArrayBuilder();
            for (ScheduleItems scheduleTable : schedule) {
                if (scheduleTable.getType().equals("References")) {
                    JsonObject scheduleJson = Json.createObjectBuilder()
                            .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                            .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                            .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle())
                            .add(JSON_SCHEDULE_TOPIC, scheduleTable.getTopic())
                            .add(JSON_SCHEDULE_LINK, scheduleTable.getLink())
                            .build();
                    scheduleRefArrayBuilder.add(scheduleJson);

                }
            }
            JsonArray refArray = scheduleRefArrayBuilder.build();

            //RECITATIONS ARRAY
            JsonArrayBuilder scheduleRecitArrayBuilder = Json.createArrayBuilder();
            for (ScheduleItems scheduleTable : schedule) {
                if (scheduleTable.getType().equals("Recitation")) {
                    String s = scheduleTable.getTopic();
                    if (scheduleTable.getTopic().contains("&")) {
                        s = s.replaceAll("(?<=[{&}])" + " ", "<br />");
                    }
                    JsonObject scheduleJson = Json.createObjectBuilder()
                            .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                            .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                            .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle())
                            .add(JSON_SCHEDULE_TOPIC, s + "<br />")
                            .build();
                    scheduleRecitArrayBuilder.add(scheduleJson);

                }
            }
            JsonArray recitArray = scheduleRecitArrayBuilder.build();

            //HWS ARRAY
            JsonArrayBuilder scheduleHwArrayBuilder = Json.createArrayBuilder();
            for (ScheduleItems scheduleTable : schedule) {
                String s = scheduleTable.getTopic();
                if (scheduleTable.getType().equals("HW")) {
                    if (scheduleTable.getTopic().contains(".zip")) {
                        String b = s.substring(0, 13);
                        String c = b + "<br />" + s.substring(14, s.length());
                    }
                    JsonObject scheduleJson = Json.createObjectBuilder()
                            .add(JSON_SCHEDULE_MONTH, scheduleTable.getMonth())
                            .add(JSON_SCHEDULE_DAY, scheduleTable.getDay())
                            .add(JSON_SCHEDULE_TITLE, scheduleTable.getTitle())
                            .add(JSON_SCHEDULE_TOPIC, scheduleTable.getTopic())
                            .build();
                    scheduleHwArrayBuilder.add(scheduleJson);

                }
            }
            JsonArray hwsArray = scheduleHwArrayBuilder.build();

            // THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(JSON_SCHEDULE_START_MONDAY_MONTH, "" + dataManager.getScheduleData().getStartMonth())
                    .add(JSON_SCHEDULE_START_MONDAY_DAY, "" + dataManager.getScheduleData().getStartDay())
                    .add(JSON_SCHEDULE_END_FRIDAY_MONTH, "" + dataManager.getScheduleData().getEndMonth())
                    .add(JSON_SCHEDULE_END_FRIDAY_DAY, "" + dataManager.getScheduleData().getEndDay())
                    .add(JSON_SCHEDULE_HOLIDAY_ARRAY, scheduleArray)
                    .add(JSON_SCHEDULE_LECTURES_ARRAY, lectureArray)
                    .add(JSON_SCHEDULE_REFERENCES_ARRAY, refArray)
                    .add(JSON_SCHEDULE_RECITATIONS_ARRAY, recitArray)
                    .add(JSON_SCHEDULE_HWS_ARRAY, hwsArray)
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }

        //TEAMS AND STUDENTS
        if (i == 4) {
            JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
            ObservableList<ProjectTeamExport> team = dataManager.getProjectsData().getProjectsTeamExport();
            for (ProjectTeamExport teamTable : team) {
                JsonObject teamJson = Json.createObjectBuilder()
                        .add(JSON_PROJECT_TEAM_NAME_EXPORT, teamTable.getName())
                        .add(JSON_PROJECT_TEAM_RED, teamTable.getRed())
                        .add(JSON_PROJECT_TEAM_GREEN, teamTable.getGreen())
                        .add(JSON_PROJECT_TEAM_BLUE, teamTable.getBlue())
                        .add(JSON_PROJECT_TEAM_EXPORT_TEXT_COLOR, teamTable.getTextColor())
                        .build();

                teamArrayBuilder.add(teamJson);

            }

            JsonArray teamArray = teamArrayBuilder.build();

            JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
            ObservableList<ProjectStudentItems> student = dataManager.getProjectsData().getProjectsStudentTable();
            for (ProjectStudentItems studentTable : student) {
                JsonObject studentJson = Json.createObjectBuilder()
                        .add(JSON_PROJECT_STUDENT_LAST_NAME, studentTable.getLastName())
                        .add(JSON_PROJECT_STUDENT_FIRST_NAME, studentTable.getFirstName())
                        .add(JSON_PROJECT_STUDENT_TEAM, studentTable.getTeam())
                        .add(JSON_PROJECT_STUDENT_ROLE, studentTable.getRole())
                        .build();

                studentArrayBuilder.add(studentJson);

            }

            JsonArray studentArray = studentArrayBuilder.build();

            // THEN PUT IT ALL TOGETHER IN A JsonObject
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(JSON_PROJECT_EXPORT_TEAMS, teamArray)
                    .add(JSON_PROJECT_EXPORT_STUDENTS, studentArray)
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }
        //PROJECTS DATA
        if (i == 5) {
            JsonObject dataManagerJSO = Json.createObjectBuilder()
                    .add(JSON_PROJECT_WORK, getWorkArray(dataManager))
                    .build();

            // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
            Map<String, Object> properties = new HashMap<>(1);
            properties.put(JsonGenerator.PRETTY_PRINTING, true);
            JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
            StringWriter sw = new StringWriter();
            JsonWriter jsonWriter = writerFactory.createWriter(sw);
            jsonWriter.writeObject(dataManagerJSO);
            jsonWriter.close();

            // INIT THE WRITER
            OutputStream os = new FileOutputStream(filePath);
            JsonWriter jsonFileWriter = Json.createWriter(os);
            jsonFileWriter.writeObject(dataManagerJSO);
            prettyPrinted = sw.toString();
            PrintWriter pw = new PrintWriter(filePath);
            pw.write(prettyPrinted);
            pw.close();
        }

    }

    //LOAD TEST
//**************************************************************************************************************************************************************************************************************************************
    public ObservableList<CSGData> loadDataTest(String filePath) throws IOException {
        ObservableList<CSGData> csgData = FXCollections.observableArrayList();
        // LOAD THE JSON FILE WITH ALL THE DATA
        JsonObject json = loadJSONFile(filePath);

        // LOAD THE START AND END HOURS
        String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        int startH = Integer.parseInt(startHour);
        int endH = Integer.parseInt(endHour);
        String startMonDay = json.getString(JSON_SCHEDULE_START_MONDAY_DAY);
        String startMonMonth = json.getString(JSON_SCHEDULE_START_MONDAY_MONTH);
        String endFriDay = json.getString(JSON_SCHEDULE_END_FRIDAY_DAY);
        String endFriMonth = json.getString(JSON_SCHEDULE_END_FRIDAY_MONTH);
        int startMondayDay = Integer.parseInt(startMonDay);
        int startMondayMonth = Integer.parseInt(startMonMonth);
        int startFridayDay = Integer.parseInt(endFriDay);
        int startFridayMonth = Integer.parseInt(endFriMonth);

        // NOW LOAD ALL THE UNDERGRAD TAs
        ObservableList<TeachingAssistant> ta = getLoadTeachingAssistant(json.getJsonArray(JSON_UNDERGRAD_TAS));
        ObservableList<TimeSlot> officeHours = getLoadOfficeHours(json.getJsonArray(JSON_OFFICE_HOURS));
        ObservableList<RecitationItems> recitation = getRecitationItems(json.getJsonArray(JSON_RECITATION));
        ObservableList<ScheduleItems> schedule = getScheduleItems(json.getJsonArray(JSON_SCHEDULE_ITEMS));
        //ObservableList<ScheduleTypeItems> scheduleHoliday = getScheduleHolidayItems(json.getJsonArray(JSON_SCHEDULE_HOLIDAY_ARRAY));
        ObservableList<ProjectTeamItems> team = getProjectTeamItems(json.getJsonArray(JSON_PROJECT_TEAMS));
        ObservableList<ProjectStudentItems> student = getProjectStudentItems(json.getJsonArray(JSON_PROJECT_STUDENTS));

        csgData.add(new CSGData(startH, endH, startMondayDay, startMondayMonth, startFridayDay, startFridayMonth, officeHours, ta, recitation, schedule, student, team));
        return csgData;

    }

    private ObservableList<TeachingAssistant> getLoadTeachingAssistant(JsonArray TAArray) {
        ObservableList<TeachingAssistant> taList = FXCollections.observableArrayList();
        for (int i = 0; i < TAArray.size(); i++) {

            JsonObject jsonTA = TAArray.getJsonObject(i);
            taList.add(new TeachingAssistant(
                    jsonTA.getString(JSON_NAME),
                    jsonTA.getString(JSON_EMAIL),
                    jsonTA.getBoolean(JSON_IS_UNDERGRAD)
            ));

        }
        return taList;
    }

    private ObservableList<TimeSlot> getLoadOfficeHours(JsonArray jsonOfficeArray) {
        ObservableList<TimeSlot> officeList = FXCollections.observableArrayList();

        for (int i = 0; i < jsonOfficeArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeArray.getJsonObject(i);
            officeList.add(new TimeSlot(
                    jsonOfficeHours.getString(JSON_DAY),
                    jsonOfficeHours.getString(JSON_TIME),
                    jsonOfficeHours.getString(JSON_NAME)
            ));

        }

        return officeList;
    }

    private ObservableList<RecitationItems> getRecitationItems(JsonArray jsonRecitationArray) {
        ObservableList<RecitationItems> recitList = FXCollections.observableArrayList();

        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            recitList.add(new RecitationItems(
                    jsonRecitation.getString(JSON_RECITATION_SECTION),
                    jsonRecitation.getString(JSON_INSTRUCTOR),
                    jsonRecitation.getString(JSON_DAY_TIME),
                    jsonRecitation.getString(JSON_LOCATION),
                    jsonRecitation.getString(JSON_TA1),
                    jsonRecitation.getString(JSON_TA2)
            ));

        }

        return recitList;
    }

    private ObservableList<ScheduleItems> getScheduleItems(JsonArray jsonScheduleArray) {
        ObservableList<ScheduleItems> scheduleList = FXCollections.observableArrayList();

        for (int i = 0; i < jsonScheduleArray.size(); i++) {
            JsonObject jsonSchedule = jsonScheduleArray.getJsonObject(i);
            scheduleList.add(new ScheduleItems(
                    jsonSchedule.getString(JSON_SCHEDULE_TYPE),
                    jsonSchedule.getString(JSON_SCHEDULE_DATE),
                    jsonSchedule.getString(JSON_SCHEDULE_TITLE),
                    jsonSchedule.getString(JSON_SCHEDULE_TIME),
                    jsonSchedule.getString(JSON_SCHEDULE_TOPIC),
                    jsonSchedule.getString(JSON_SCHEDULE_LINK),
                    jsonSchedule.getString(JSON_SCHEDULE_CRITERIA),
                    jsonSchedule.getString(JSON_SCHEDULE_MONTH),
                    jsonSchedule.getString(JSON_SCHEDULE_DAY)
            ));

        }

        return scheduleList;
    }

    private ObservableList<ProjectTeamItems> getProjectTeamItems(JsonArray jsonProjectArray) {
        ObservableList<ProjectTeamItems> projectList = FXCollections.observableArrayList();

        for (int i = 0; i < jsonProjectArray.size(); i++) {
            JsonObject jsonProject = jsonProjectArray.getJsonObject(i);
            projectList.add(new ProjectTeamItems(
                    jsonProject.getString(JSON_PROJECT_TEAM_NAME),
                    jsonProject.getString(JSON_PROJECT_TEAM_COLOR),
                    jsonProject.getString(JSON_PROJECT_TEAM_TEXT_COLOR),
                    jsonProject.getString(JSON_PROJECT_LINK)
            ));

        }

        return projectList;
    }

    private ObservableList<ProjectStudentItems> getProjectStudentItems(JsonArray jsonProjectArray) {
        ObservableList<ProjectStudentItems> projectList = FXCollections.observableArrayList();

        for (int i = 0; i < jsonProjectArray.size(); i++) {
            JsonObject jsonProject = jsonProjectArray.getJsonObject(i);
            projectList.add(new ProjectStudentItems(
                    jsonProject.getString(JSON_PROJECT_STUDENT_FIRST_NAME),
                    jsonProject.getString(JSON_PROJECT_STUDENT_LAST_NAME),
                    jsonProject.getString(JSON_PROJECT_STUDENT_TEAM),
                    jsonProject.getString(JSON_PROJECT_STUDENT_ROLE)
            ));

        }

        return projectList;
    }

    // IMPORTING/EXPORTING DATA IS USED WHEN WE READ/WRITE DATA IN AN
    // ADDITIONAL FORMAT USEFUL FOR ANOTHER PURPOSE, LIKE ANOTHER APPLICATION
    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
