package tam;

import java.util.Locale;
import tam.data.TAData;
import tam.file.TAFiles;
import tam.workspace.TAWorkspace;
import djf.AppTemplate;
import djf.SplashScreen;
import tam.style.TAStyle;
import static javafx.application.Application.launch;
import tam.data.CSGData;
import tam.workspace.CSGWorkspace;

/**
 * This class serves as the application class for our TA Manager App program.
 * Note that much of its behavior is inherited from AppTemplate, as defined in
 * the Desktop Java Framework. This app starts by loading all the UI-specific
 * settings like icon files and tooltips and other things, then the full User
 * Interface is loaded using those settings. Note that this is a JavaFX
 * application.
 *
 * @author Richard McKenna
 * @version 1.0
 */
public class TAManagerApp extends AppTemplate {

    /**
     * This hook method must initialize all four components in the proper order
     * ensuring proper dependencies are respected, meaning all proper objects
     * are already constructed when they are needed for use, since some may need
     * others for initialization.
     */
    @Override
    public void buildAppComponentsHook() {
        // CONSTRUCT ALL FOUR COMPONENTS. NOTE THAT FOR THIS APP
        // THE WORKSPACE NEEDS THE DATA COMPONENT TO EXIST ALREADY
        // WHEN IT IS CONSTRUCTED, SO BE CAREFUL OF THE ORDER
        dataComponent = new CSGData(this);
        workspaceComponent = new CSGWorkspace(this);
        fileComponent = new TAFiles(this);
        styleComponent = new TAStyle(this);
    }

    /**
     * This is where program execution begins. Since this is a JavaFX app it
     * will simply call launch, which gets JavaFX rolling, resulting in sending
     * the properly initialized Stage (i.e. window) to the start method
     * inherited from AppTemplate, defined in the Desktop Java Framework.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
      /* SplashScreen splash = new SplashScreen();
        splash.setVisible(true);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(35);
                splash.loadingNum.setText(Integer.toString(i) + "%");
                splash.loadingBar.setValue(i);
                if(i==100){
                    splash.setVisible(false);
                }
            }

        } catch (Exception e) {

        }
*/
        launch(args);
    }
}
