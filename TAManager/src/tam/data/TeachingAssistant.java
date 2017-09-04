package tam.data;

import djf.ui.AppGUI;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;
import tam.TAManagerApp;
import tam.workspace.CSGWorkspace;

/**
 * This class represents a Teaching Assistant for the table of TAs.
 *
 * Updated by: Bilal Shah
 */
public class TeachingAssistant<E extends Comparable<E>> implements Comparable<E> {

    // THE TABLE WILL STORE TA NAMES AND EMAILS
    private final StringProperty name, email;
    private final BooleanProperty undergrad;
    static int count = 0;
    TAManagerApp app;
   
    /**
     * Constructor initializes the TA name
     */
    public TeachingAssistant(String initName, String email, Boolean isUnderGrad) {
        name = new SimpleStringProperty(initName);
        this.email = new SimpleStringProperty(email);
        this.undergrad = new SimpleBooleanProperty(isUnderGrad);
    }

    // ACCESSORS AND MUTATORS FOR THE PROPERTIES
    public String getName() {
        return name.get();
    }

    public String getEmail() {
        return email.get();
    }

    public Boolean getUndergrad() {
        return undergrad.get();
    }

    public void setName(String initName) {
        name.set(initName);
    }

    public void setEmail(String initEmail) {
        email.set(initEmail);
    }

    public void setCheck(Boolean initCheck) {
        undergrad.set(initCheck);
    }

    public ObservableBooleanValue isChecked(TAManagerApp initApp) {
        app = initApp;
        CSGWorkspace workspace = (CSGWorkspace)app.getWorkspaceComponent();
        workspace.getRecitationWorkspace().reloadWorkspace();
        return undergrad;
    }

    public void setChecked(Boolean checked) {
        this.undergrad.set(checked);
    }

    @Override
    public int compareTo(E otherTA) {
        return getName().compareTo(((TeachingAssistant) otherTA).getName());
    }

    @Override
    public String toString() {
        String email = this.email.getValue();
        return name.getValue() + email + undergrad.getValue();
    }
}
