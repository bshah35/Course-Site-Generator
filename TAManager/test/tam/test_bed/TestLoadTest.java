/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Bilal
 */
public class TestLoadTest {
    
    public TestLoadTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }

     /**
     * Test of loadDesignOne method, of class TestLoad.
     */
    @Test
    public void testLoad() {
        System.out.println("loadDesignOne");
        String expResult = TestLoad.loadDesign("../TAManager/work/SiteSaveTest.json");
        String result = TestLoad.loadDesign("../TAManager/work/SiteSaveTestExpected.json");
        assertEquals(expResult, result);
    }
    
}
