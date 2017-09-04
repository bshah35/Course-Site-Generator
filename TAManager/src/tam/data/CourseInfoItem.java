/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

/**
 *
 * @author Bilal
 */
public class CourseInfoItem {
     private String subject, courseNumber, courseSemester, courseYear, courseTitle, courseInstructorName, courseInstrcutorHome,
            courseExportDir;
     
    public String getSubject() {
        return subject;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public String getCourseSemester() {
        return courseSemester;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getCourseInstructorName() {
        return courseInstructorName;
    }

    public String getCourseInstrcutorHome() {
        return courseInstrcutorHome;
    }

    public String getCourseExportDir() {
        return courseExportDir;
    }
    
     public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public void setCourseSemester(String courseSemester) {
        this.courseSemester = courseSemester;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseInstructorName(String courseInstructorName) {
        this.courseInstructorName = courseInstructorName;
    }

    public void setCourseInstrcutorHome(String courseInstrcutorHome) {
        this.courseInstrcutorHome = courseInstrcutorHome;
    }

    public void setCourseExportDir(String courseExportDir) {
        this.courseExportDir = courseExportDir;
    }
}
