package test.cosc202.andie;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import cosc202.andie.ImagePanel;

public class ImagePanelTest {
    @Test
    void initialDummyTest(){}

    @Test
    void getZoomInitialValue(){
        ImagePanel panel = new ImagePanel();
        Assertions.assertEquals(100.0, panel.getZoom());
        
    }

    @Test
    void getZoomAfterSet(){
        ImagePanel panel = new ImagePanel();
        panel.setZoom(10);
        Assertions.assertTrue(panel.getZoom() == 50);
        Assertions.assertTrue(panel.getZoom() <= 200);
        Assertions.assertEquals(50, panel.getZoom());

        panel.setZoom(150);
        Assertions.assertEquals(150, panel.getZoom());
    }
}
