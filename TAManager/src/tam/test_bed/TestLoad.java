/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import tam.data.CSGData;
import tam.file.TAFiles;

/**
 *
 * @author Bilal
 */
public class TestLoad {
    static ObservableList<CSGData> csgData;
    static TAFiles fileManager;
    
    public static String loadDesign(String path) {
        String courseSiteData = "";
        fileManager = new TAFiles();
        try {
            csgData = fileManager.loadDataTest(path);
        } catch (IOException ex) {
            Logger.getLogger(TestLoad.class.getName()).log(Level.SEVERE, null, ex);
        }   
        for(CSGData data: csgData){
            courseSiteData += data.toString();
        }
        return courseSiteData;
    }
    
     
    public static void main(String[] args){
        System.out.println(loadDesign("../TAManager/work/SiteSaveTest.json"));
    }
}
