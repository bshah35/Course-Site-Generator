/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;

/**
 *
 * @author Bilal
 */
public class CourseDetailsItems {
    // THE TABLE WILL STORE TA NAMES AND EMAILS

    private final StringProperty navBar, file, script;
    private final BooleanProperty use;
   
    
   
    /**
     * Constructor initializes the TA name
     */
    public CourseDetailsItems(Boolean isUse, String initNavBar, String initFile, String initScript) {
        this.navBar = new SimpleStringProperty(initNavBar);
        this.file = new SimpleStringProperty(initFile);
        this.script = new SimpleStringProperty(initScript);
        this.use = new SimpleBooleanProperty(isUse);

    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES
    

    public String getNavBar() {
        return navBar.get();
    }

    public String getFile() {
        return file.get();
    }

    public String getScript() {
        return script.get();
    }

    public Boolean isUse() {
        return use.get();
    }

    public void setNavBar(String initNavBar) {
        navBar.set(initNavBar);
    }

    public void setFile(String initFile) {
        file.set(initFile);
    }

    public void setScriptt(String initScript) {
        script.set(initScript);
    }

    public void setUse(Boolean isUse) {
        use.set(isUse);
    }

    public ObservableBooleanValue isChecked() {
        return use;
    }

    public void setChecked(Boolean checked) {
        this.use.set(checked);
    }

    @Override
    public String toString() {
        return navBar.getValue() + file.getValue() + script.getValue() + use.getValue();
    }
}
