package test.cosc202.andie;

import java.awt.event.KeyEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.Resizing;


public class ResizeTest {
    @Test
    void isUsableTest() {
        Resizing r = new Resizing();
        Resizing.ResizeAction ra = r.new ResizeAction("Resize Image", null, "Resizes image",
                Integer.valueOf(KeyEvent.VK_R));

        Assertions.assertFalse(ra.isUsable("%"));
        Assertions.assertFalse(ra.isUsable(""));
        Assertions.assertFalse(ra.isUsable("0"));
        Assertions.assertFalse(ra.isUsable("e"));
        Assertions.assertFalse(ra.isUsable("1e0"));
        Assertions.assertFalse(ra.isUsable("1%%"));
        Assertions.assertFalse(ra.isUsable("%100%"));
        Assertions.assertFalse(ra.isUsable("0.1"));
        Assertions.assertFalse(ra.isUsable("0.1%"));
        Assertions.assertFalse(ra.isUsable("01"));
        Assertions.assertFalse(ra.isUsable("01%"));
        Assertions.assertTrue(ra.isUsable("1001"));
        Assertions.assertTrue(ra.isUsable("1001%"));
    }

    @Test 
    void getPercentChangeTest(){
        Resizing r = new Resizing();
        Resizing.ResizeAction ra = r.new ResizeAction("Resize Image", null, "Resizes image",
                Integer.valueOf(KeyEvent.VK_R));
        
        //method only called if selected item is usable
        ra.setResizeJCB("1");
        Assertions.assertEquals(1,ra.getPercentChange()); 
        ra.setResizeJCB("1%");
        Assertions.assertEquals(1,ra.getPercentChange()); 
        ra.setResizeJCB("100");
        Assertions.assertEquals(100,ra.getPercentChange()); 
        ra.setResizeJCB("100%");
        Assertions.assertEquals(100,ra.getPercentChange()); 
        ra.setResizeJCB("126");
        Assertions.assertEquals(126,ra.getPercentChange()); 
        ra.setResizeJCB("126%");
        Assertions.assertEquals(126,ra.getPercentChange()); 
        ra.setResizeJCB("1000%");
        Assertions.assertEquals(1000,ra.getPercentChange()); 
        ra.setResizeJCB("1000%");
        Assertions.assertEquals(1000,ra.getPercentChange()); 
        ra.setResizeJCB("1001");
        Assertions.assertEquals(1001,ra.getPercentChange()); 
        ra.setResizeJCB("1001%");
        Assertions.assertEquals(1001,ra.getPercentChange()); 
    }
}
