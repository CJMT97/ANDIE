package cosc202.andie;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * <p>
 * UI display element for previews of {@link BufferedImage}s.
 * </p>
 * 
 * <p>
 * This class extends {@link JPanel} to allow a preview of an image to be
 * updated.
 * Intended to be used in an options panel.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class PreviewImagePanel extends JPanel {
    /**
     * The image to display in the ImagePanel.
     */
    private BufferedImage image;

    /**
     * <p>
     * Create a new PreviewImagePanel.
     * </p>
     * 
     * <p>
     * The label indicating this is a preview and not the actual image is added in
     * this constructor.
     * </p>
     * 
     * @param image The image to be displayed, updatable using the setImage method.
     */
    public PreviewImagePanel(BufferedImage image) {
        setImage(image);
        JLabel label = new JLabel(Settings.getMessage("Preview"));
        this.add(label);
    }

    /**
     * The default constructor for the PreviewImagePanel.
     */
    public PreviewImagePanel() {
        this(null);
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * <p>
     * Update the currently displayed image.
     * </p>
     *
     * @param image The new image.
     */
    public void setImage(BufferedImage image) {
        this.image = image;
        this.repaint();
    }

    /**
     * <p>
     * Get the height of the image.
     * </p>
     * 
     * @return The height of the image currently displayed.
     */
    public int getImageHeight() {
        return this.getHeight() - 30;
    }

    /**
     * <p>
     * Get the width of the image.
     * </p>
     * 
     * @return The width of the image currently displayed.
     */
    public int getImageWidth() {
        return this.getWidth();
    }

    /**
     * <p>
     * Get the height of the image.
     * </p>
     *
     * <p>
     * Ensures a minimum height of 60 pixels so that the Label and Image have room
     * to display
     * </p>
     * 
     * @param dimension The dimension object with the new dimensions for the preview
     *                  image panel.
     */
    @Override
    public void setPreferredSize(Dimension dimension) {
        super.setPreferredSize(dimension);
        if (dimension.getHeight() < 60) {
            dimension.setSize(dimension.width, 60);
        }
        this.setSize((int) dimension.getWidth(), (int) dimension.getHeight());
    }

    /**
     * <p>
     * (Re)draw the component in the GUI.
     * </p>
     * 
     * @param g The Graphics component to draw the image on.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(image, null, 0, 30);
        g2.dispose();
    }
}
