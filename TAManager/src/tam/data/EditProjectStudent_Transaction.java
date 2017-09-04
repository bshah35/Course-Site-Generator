/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class EditProjectStudent_Transaction implements jTPS_Transaction {

    ProjectStudentItems student;
    String newFirstName, newLastName, newTeam, newRole, oldFirstName, oldLastName, oldTeam, oldRole;
    TableView studentTable;
    CSGData data;

    public EditProjectStudent_Transaction(ProjectStudentItems student, String newFirstName, String newLastName, String newTeam, String newRole, String oldFirstName, String oldLastName,
            String oldTeam, String oldRole, TableView studentTable) {

        this.student = student;
        this.newFirstName = newFirstName;
        this.newLastName = newLastName;
        this.newTeam = newTeam;
        this.newRole = newRole;
        this.oldFirstName = oldFirstName;
        this.oldLastName = oldLastName;
        this.oldTeam = oldTeam;
        this.oldRole = oldRole;
        this.studentTable = studentTable;
      
    }

    @Override
    public void doTransaction() {
        student.setFirstName(newFirstName);
        student.setLastName(newLastName);
        student.setRole(newRole);
        student.setTeam(newTeam);
        studentTable.refresh();

    }

    @Override
    public void undoTransaction() {
        student.setFirstName(oldFirstName);
        student.setLastName(oldLastName);
        student.setRole(oldRole);
        student.setTeam(oldTeam);
        studentTable.refresh();
    }

}
