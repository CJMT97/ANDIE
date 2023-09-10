package test.cosc202.andie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import org.junit.jupiter.api.Test;
import cosc202.andie.PreviewImagePanel;

public class PreviewPanel {
    @Test
    void resizingTests() {
        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        PreviewImagePanel panel = new PreviewImagePanel(image);
        panel.setPreferredSize(new Dimension(10, 10));
        assertEquals(panel.getWidth(), 10);
        assertFalse(panel.getHeight() == 10);
        assertEquals(panel.getHeight(), 60);

    }

    @Test
    void getImageHeightTests() {
        BufferedImage image = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB);
        PreviewImagePanel panel = new PreviewImagePanel(image);
        panel.setPreferredSize(new Dimension(10, 10));
        assertEquals(panel.getImageHeight(), 30);

        panel.setPreferredSize(new Dimension(100, 100));
        assertEquals(panel.getImageHeight(), 70);
        //70
    }

}
