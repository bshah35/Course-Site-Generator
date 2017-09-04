/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import java.util.ArrayList;

/**
 *
 * @author Bilal
 */
public class ProjectsDataExport {

    ArrayList<String> students;
    String teamName, link;

    public ProjectsDataExport(String initTeamName, ArrayList<String> initStudents, String initLink) {
        teamName = initTeamName;
        students = initStudents;
        link = initLink;
    }

    public ArrayList<String> getStudents() {
        return students;
    }

    public String teamName() {
        return teamName;
    }

    public String getLink() {
        return link;
    }
}
