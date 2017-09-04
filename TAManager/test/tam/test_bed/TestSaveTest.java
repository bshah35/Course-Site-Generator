/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bilal
 */
public class TestSaveTest {
    
    public TestSaveTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of saveDesign method, of class TestSave.
     */
    @Test
    public void testSaveDesign() {
         System.out.println("saveDesign");
          TestSave.saveDesign();
        try {
            assertEquals("The files differ!",
                    FileUtils.readFileToString(new File("../TAManager/work/SiteSaveTestExpected.json"), "utf-8"),
                    FileUtils.readFileToString(new File("../TAManager/work/SiteSaveTest.json"), "utf-8"));
        } catch (IOException ex) {
            Logger.getLogger(TestSaveTest.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    /**
     * Test of main method, of class TestSave.
     */
    @Test
    public void testMain() throws ParseException {
        System.out.println("main");
        String[] args = null;
        TestSave.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    
}
