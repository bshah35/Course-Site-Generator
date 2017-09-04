/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.workspace;

import djf.ui.AppMessageDialogSingleton;
import djf.ui.AppYesNoCancelDialogSingleton;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.TAManagerProp;
import static tam.TAManagerProp.ADD_RECITATION_MESSAGE;
import static tam.TAManagerProp.ADD_RECITATION_TITLE;
import static tam.TAManagerProp.ADD_SCHEDULE_MESSAGE;
import static tam.TAManagerProp.ADD_SCHEDULE_TITLE;
import static tam.TAManagerProp.DELETE_MESSAGE;
import static tam.TAManagerProp.EDIT_SCHEDULE_MESSAGE;
import static tam.TAManagerProp.MISSING_CRITERIA_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_CRITERIA_NAME_TITLE;
import static tam.TAManagerProp.MISSING_DATE_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_DATE_NAME_TITLE;
import static tam.TAManagerProp.MISSING_LINK_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_LINK_NAME_TITLE;
import static tam.TAManagerProp.MISSING_TIME_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TIME_NAME_TITLE;
import static tam.TAManagerProp.MISSING_TITLE_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TITLE_NAME_TITLE;
import static tam.TAManagerProp.MISSING_TOPIC_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TOPIC_NAME_TITLE;
import static tam.TAManagerProp.MISSING_TYPE_NAME_MESSAGE;
import static tam.TAManagerProp.MISSING_TYPE_NAME_TITLE;
import static tam.TAManagerProp.RECITATION_DELETE_MESSAGE;
import static tam.TAManagerProp.RECITATION_DELETE_TITLE;
import static tam.TAManagerProp.SCHEDULE_DELETE_TITLE;
import tam.data.AddCalenderBoundries_Transaction;
import tam.data.AddSchedule_Transaction;
import tam.data.CSGData;
import tam.data.EditSchedule_Transaction;
import tam.data.RemoveSchedule_Transaction;
import tam.data.ScheduleData;
import tam.data.ScheduleItems;
import tam.data.ScheduleTypeItems;

/**
 *
 * @author Bilal
 */
public class ScheduleController {

    TAManagerApp app;
    static jTPS jtps = new jTPS();
    TableView schedule_table = new TableView();

    public ScheduleController(TAManagerApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }

    public void handleAddSchedule() throws IOException {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TextField titleTextField = workspace.getScheduleWorkspace().titleText;
        TextField topicTextField = workspace.getScheduleWorkspace().topicText;
        TextField linkTextField = workspace.getScheduleWorkspace().linkText;
        TextField criteriaTextField = workspace.getScheduleWorkspace().criteriaText;
        TextField timeTextField = workspace.getScheduleWorkspace().timeText;
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String time = timeTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();
        String type = workspace.getScheduleWorkspace().getType();
        String date = workspace.getScheduleWorkspace().getDate();

        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData csgData = (CSGData) app.getDataComponent();
        ScheduleData data = csgData.getScheduleData();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (type.equals("")) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_TYPE_NAME_TITLE), props.getProperty(MISSING_TYPE_NAME_MESSAGE));
        } else if (date.equals("")) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_DATE_NAME_TITLE), props.getProperty(MISSING_DATE_NAME_MESSAGE));
        }
        if (type.equalsIgnoreCase("Holiday")) {
            if (link.isEmpty() || !validateLink(link)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                jTPS_Transaction add;
                String Month = date.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = date.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");
                ScheduleItems schedule = new ScheduleItems(type, date, title, time, topic, link, criteria, Month, Day);

                //ScheduleTypeItems typeItem = new ScheduleTypeItems(Month,newTitle,newLink,Day, newCriteria, newTime);
                add = new AddSchedule_Transaction(data.getScheduleTable(), schedule);
                jtps.addTransaction(add);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(ADD_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        } else if (type.equalsIgnoreCase("Lecture")) {
            if (link.isEmpty() || !validateLink(link)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (topic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                jTPS_Transaction add;
                String Month = date.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = date.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");
                ScheduleItems schedule = new ScheduleItems(type, date, title, time, topic, link, criteria, Month, Day);

                //ScheduleTypeItems typeItem = new ScheduleTypeItems(Month,newTitle,newLink,Day, newCriteria, newTime);
                add = new AddSchedule_Transaction(data.getScheduleTable(), schedule);
                jtps.addTransaction(add);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(ADD_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }

        } else if (type.equalsIgnoreCase("Recitation")) {
            if (topic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                jTPS_Transaction add;
                String Month = date.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = date.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");
                ScheduleItems schedule = new ScheduleItems(type, date, title, time, topic, link, criteria, Month, Day);

                //ScheduleTypeItems typeItem = new ScheduleTypeItems(Month,newTitle,newLink,Day, newCriteria, newTime);
                add = new AddSchedule_Transaction(data.getScheduleTable(), schedule);
                jtps.addTransaction(add);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(ADD_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        } else if (type.equalsIgnoreCase("References")) {
            if (topic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (link.isEmpty() || !validateLink(link)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                jTPS_Transaction add;
                String Month = date.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = date.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");
                ScheduleItems schedule = new ScheduleItems(type, date, title, time, topic, link, criteria, Month, Day);

                //ScheduleTypeItems typeItem = new ScheduleTypeItems(Month,newTitle,newLink,Day, newCriteria, newTime);
                add = new AddSchedule_Transaction(data.getScheduleTable(), schedule);
                jtps.addTransaction(add);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(ADD_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        } else if (type.equalsIgnoreCase("HW")) {
            if (topic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (link.isEmpty() || !validateLink(link)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (time.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TIME_NAME_TITLE), props.getProperty(MISSING_TIME_NAME_MESSAGE));
            } else if (criteria.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_CRITERIA_NAME_TITLE), props.getProperty(MISSING_CRITERIA_NAME_MESSAGE));

            } else if (title.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                jTPS_Transaction add;
                String Month = date.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = date.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");
                ScheduleItems schedule = new ScheduleItems(type, date, title, time, topic, link, criteria, Month, Day);

                add = new AddSchedule_Transaction(data.getScheduleTable(), schedule);
                jtps.addTransaction(add);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(ADD_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        }

    }

    public void handleEditSchedule() throws IOException {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView scheduleTable = workspace.getScheduleWorkspace().getScheduleTable();
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        ScheduleItems scheduleItems = (ScheduleItems) selectedItem;
        TextField titleTextField = workspace.getScheduleWorkspace().titleText;
        TextField topicTextField = workspace.getScheduleWorkspace().topicText;
        TextField linkTextField = workspace.getScheduleWorkspace().linkText;
        TextField criteriaTextField = workspace.getScheduleWorkspace().criteriaText;
        TextField timeTextField = workspace.getScheduleWorkspace().timeText;
        String newTitle = titleTextField.getText();
        String newTopic = topicTextField.getText();
        String newTime = timeTextField.getText();
        String newLink = linkTextField.getText();
        String newCriteria = criteriaTextField.getText();
        String newType = workspace.getScheduleWorkspace().getType();
        String newDate = workspace.getScheduleWorkspace().getDate();

        String oldTitle = scheduleItems.getTitle();
        String oldTopic = scheduleItems.getTopic();
        String oldTime = scheduleItems.getTime();
        String oldLink = scheduleItems.getLink();
        String oldCriteria = scheduleItems.getCriteria();
        String oldType = scheduleItems.getType();
        String oldDate = scheduleItems.getDate();

        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        if (newType.equals("")) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_TYPE_NAME_TITLE), props.getProperty(MISSING_TYPE_NAME_MESSAGE));
        } else if (newDate.equals("")) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.setMinWidth(400);
            dialog.show(props.getProperty(MISSING_DATE_NAME_TITLE), props.getProperty(MISSING_DATE_NAME_MESSAGE));
        }
        if (newType.equalsIgnoreCase("Holiday")) {
            if (newLink.isEmpty() || !validateLink(newLink)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (newTitle.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                String Month = newDate.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = newDate.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");

                String oldMonth = oldDate.substring(0, 2);
                oldMonth = oldMonth.replaceAll("/", "");
                oldMonth = oldMonth.replaceFirst("^0*", "");
                String oldDay = oldDate.substring(2, 5);
                oldDay = oldDay.replaceAll("/", "");
                oldDay = oldDay.replaceFirst("^0*", "");

                jTPS_Transaction edit = new EditSchedule_Transaction(scheduleItems, oldType, oldDate, oldTitle, oldTime, oldTopic, oldLink, oldCriteria,
                        newType, newDate, newTitle, newTime, newTopic, newLink, newCriteria, scheduleTable, Month, Day, oldMonth, oldDay);
                jtps.addTransaction(edit);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(EDIT_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        } else if (newType.equalsIgnoreCase("Lecture")) {
            if (newLink.isEmpty() || !validateLink(newLink)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (newTopic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (newTitle.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {
                String Month = newDate.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = newDate.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");

                String oldMonth = oldDate.substring(0, 2);
                oldMonth = oldMonth.replaceAll("/", "");
                oldMonth = oldMonth.replaceFirst("^0*", "");
                String oldDay = oldDate.substring(2, 5);
                oldDay = oldDay.replaceAll("/", "");
                oldDay = oldDay.replaceFirst("^0*", "");

                jTPS_Transaction edit = new EditSchedule_Transaction(scheduleItems, oldType, oldDate, oldTitle, oldTime, oldTopic, oldLink, oldCriteria,
                        newType, newDate, newTitle, newTime, newTopic, newLink, newCriteria, scheduleTable, Month, Day, oldMonth, oldDay);
                jtps.addTransaction(edit);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(EDIT_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }

        } else if (newType.equalsIgnoreCase("Recitation")) {
            if (newTopic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (newTitle.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {

                String Month = newDate.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = newDate.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");

                String oldMonth = oldDate.substring(0, 2);
                oldMonth = oldMonth.replaceAll("/", "");
                oldMonth = oldMonth.replaceFirst("^0*", "");
                String oldDay = oldDate.substring(2, 5);
                oldDay = oldDay.replaceAll("/", "");
                oldDay = oldDay.replaceFirst("^0*", "");

                jTPS_Transaction edit = new EditSchedule_Transaction(scheduleItems, oldType, oldDate, oldTitle, oldTime, oldTopic, oldLink, oldCriteria,
                        newType, newDate, newTitle, newTime, newTopic, newLink, newCriteria, scheduleTable, Month, Day, oldMonth, oldDay);
                jtps.addTransaction(edit);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(EDIT_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        } else if (newType.equalsIgnoreCase("References")) {
            if (newTopic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (newLink.isEmpty() || !validateLink(newLink)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (newTitle.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {

                String Month = newDate.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = newDate.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");

                String oldMonth = oldDate.substring(0, 2);
                oldMonth = oldMonth.replaceAll("/", "");
                oldMonth = oldMonth.replaceFirst("^0*", "");
                String oldDay = oldDate.substring(2, 5);
                oldDay = oldDay.replaceAll("/", "");
                oldDay = oldDay.replaceFirst("^0*", "");

                jTPS_Transaction edit = new EditSchedule_Transaction(scheduleItems, oldType, oldDate, oldTitle, oldTime, oldTopic, oldLink, oldCriteria,
                        newType, newDate, newTitle, newTime, newTopic, newLink, newCriteria, scheduleTable, Month, Day, oldMonth, oldDay);
                jtps.addTransaction(edit);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(EDIT_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");
            }
        } else if (newType.equalsIgnoreCase("HW")) {
            if (newTopic.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TOPIC_NAME_TITLE), props.getProperty(MISSING_TOPIC_NAME_MESSAGE));

            } else if (newLink.isEmpty() || !validateLink(newLink)) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_LINK_NAME_TITLE), props.getProperty(MISSING_LINK_NAME_MESSAGE));
            } else if (newTime.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TIME_NAME_TITLE), props.getProperty(MISSING_TIME_NAME_MESSAGE));
            } else if (newCriteria.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_CRITERIA_NAME_TITLE), props.getProperty(MISSING_CRITERIA_NAME_MESSAGE));

            } else if (newTitle.isEmpty()) {
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(MISSING_TITLE_NAME_TITLE), props.getProperty(MISSING_TITLE_NAME_MESSAGE));
            } else {

                String Month = newDate.substring(0, 2);
                Month = Month.replaceAll("/", "");
                Month = Month.replaceFirst("^0*", "");
                String Day = newDate.substring(2, 5);
                Day = Day.replaceAll("/", "");
                Day = Day.replaceFirst("^0*", "");

                String oldMonth = oldDate.substring(0, 2);
                oldMonth = oldMonth.replaceAll("/", "");
                oldMonth = oldMonth.replaceFirst("^0*", "");
                String oldDay = oldDate.substring(2, 5);
                oldDay = oldDay.replaceAll("/", "");
                oldDay = oldDay.replaceFirst("^0*", "");

                jTPS_Transaction edit = new EditSchedule_Transaction(scheduleItems, oldType, oldDate, oldTitle, oldTime, oldTopic, oldLink, oldCriteria,
                        newType, newDate, newTitle, newTime, newTopic, newLink, newCriteria, scheduleTable, Month, Day, oldMonth, oldDay);
                jtps.addTransaction(edit);
                AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
                dialog.setMinWidth(400);
                dialog.show(props.getProperty(ADD_SCHEDULE_TITLE), props.getProperty(EDIT_SCHEDULE_MESSAGE));
                String comboBoxText = props.getProperty(TAManagerProp.SCHEDULE_TYPE_COMBO_TEXT.toString());
                workspace.getScheduleWorkspace().datePicker.setValue(null);
                workspace.getScheduleWorkspace().comboBox.setValue(comboBoxText);
                titleTextField.setText("");
                timeTextField.setText("");
                topicTextField.setText("");
                criteriaTextField.setText("");
                linkTextField.setText("");

            }
        }

    }

    public void handleAddCalenderBoundries() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CSGData csgData = (CSGData) app.getDataComponent();
        ScheduleData data = csgData.getScheduleData();
        DatePicker endDatePicker = null;
        DatePicker startDatePicker = null;
        String date = "", date2 = "", oldDate = "", year = "", oldYear = "", startMonth = "", startDay = "", oldStartMonth = "", oldStartDay = "", oldDate2 = "", 
                endMonth = "", endDay = "", oldEndMonth = "", oldEndDay = "";

        startDatePicker = workspace.getScheduleWorkspace().getStartDatePicker();
        date = workspace.getScheduleWorkspace().getStartDate();
        oldDate = workspace.getScheduleWorkspace().oldStartDate;
        workspace.getScheduleWorkspace().oldStartDate = workspace.getScheduleWorkspace().getStartDate();
        year = date.substring(6,10);
        startMonth = date.substring(0, 2);
        startMonth = startMonth.replaceAll("/", "");
        startMonth = startMonth.replaceFirst("^0*", "");
        startDay = date.substring(2, 5);
        startDay = startDay.replaceAll("/", "");
        startDay = startDay.replaceFirst("^0*", "");

        oldStartMonth = oldDate.substring(0, 2);
        oldStartMonth = oldStartMonth.replaceAll("/", "");
        oldStartMonth = oldStartMonth.replaceFirst("^0*", "");
        oldStartDay = oldDate.substring(2, 5);
        oldStartDay = oldStartDay.replaceAll("/", "");
        oldStartDay = oldStartDay.replaceFirst("^0*", "");
        oldYear = oldDate.substring(6,10);
        data.setStartDay(Integer.parseInt(startDay));
        data.setStartMonth(Integer.parseInt(startMonth));

        endDatePicker = workspace.getScheduleWorkspace().endDatePicker;
        date2 = workspace.getScheduleWorkspace().getEndDate();
        oldDate2 = workspace.getScheduleWorkspace().oldEndDate;
        workspace.getScheduleWorkspace().oldEndDate = workspace.getScheduleWorkspace().getEndDate();
        endMonth = date2.substring(0, 2);
        endMonth = endMonth.replaceAll("/", "");
        endMonth = endMonth.replaceFirst("^0*", "");
        endDay = date2.substring(2, 5);
        endDay = endDay.replaceAll("/", "");
        endDay = endDay.replaceFirst("^0*", "");
        oldEndMonth = oldDate2.substring(0, 2);
        oldEndMonth = oldEndMonth.replaceAll("/", "");
        oldEndMonth = oldEndMonth.replaceFirst("^0*", "");
        oldEndDay = oldDate2.substring(2, 5);
        oldEndDay = oldEndDay.replaceAll("/", "");
        oldEndDay = oldEndDay.replaceFirst("^0*", "");

        data.setEndDay(Integer.parseInt(endDay));
        data.setEndMonth(Integer.parseInt(endMonth));

        jTPS_Transaction add = new AddCalenderBoundries_Transaction(startDay, startMonth, oldStartDay, oldStartMonth, year, oldYear, data, startDatePicker,
                endDay, endMonth, oldEndDay, oldEndMonth, endDatePicker, workspace.getScheduleWorkspace());
        jtps.addTransaction(add);
    }

    private boolean validateDelete() {
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        AppYesNoCancelDialogSingleton yesNoDialog = AppYesNoCancelDialogSingleton.getSingleton();
        yesNoDialog.setMinWidth(450);
        yesNoDialog.show(props.getProperty(SCHEDULE_DELETE_TITLE), props.getProperty(DELETE_MESSAGE));
        String selection = yesNoDialog.getSelection();

        if (selection.equals(AppYesNoCancelDialogSingleton.YES)) {
            return true;
        } else {
            return false;
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

    

    public void handleDeletePress() {
        CSGData csgData = (CSGData) app.getDataComponent();
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        TableView scheduleTable = workspace.getScheduleWorkspace().getScheduleTable();
        if (!scheduleTable.getItems().isEmpty()) {
            Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();

            if (selectedItem != null && validateDelete()) {
                ScheduleItems schedule = (ScheduleItems) selectedItem;
                ScheduleData data = csgData.getScheduleData();

                jTPS_Transaction remove = new RemoveSchedule_Transaction(data.getScheduleTable(), schedule);
                jtps.addTransaction(remove);
                scheduleTable.getItems().remove(selectedItem);
            }
        }
    }

    public void handleUndo() {
        jtps.undoTransaction();
    }

    public void handleRedo() {
        jtps.doTransaction();
    }
}
