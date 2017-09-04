/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Bilal
 */
public class RemoveProjectStudent_Transaction implements jTPS_Transaction {

    ObservableList<ProjectStudentItems> projectItems;
    ProjectStudentItems projectStudent;

    public RemoveProjectStudent_Transaction(ObservableList<ProjectStudentItems> projectItems, ProjectStudentItems projectStudent) {
        this.projectItems = projectItems;
        this.projectStudent = projectStudent;

    }

    @Override
    public void doTransaction() {
        projectItems.remove(projectStudent);

    }

    @Override
    public void undoTransaction() {
        projectItems.add(projectStudent);

    }

}
