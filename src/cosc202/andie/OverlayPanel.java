package cosc202.andie;

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 * <p>
 * UI Displaying a preview of drawings before they are applied to the image.
 * Drawing and Cropping operations.
 * 
 * </p>
 * @author Ben Nicholson , Hamish Dudley 
 */
public class OverlayPanel extends JPanel {

    /** The scale of the OverlayPanel */
    private double scale;
    /** Wheather or not the mouse is pressed */
    private boolean mouseDown;

    /**
     * A constructor to create a new OverlayPanel with mouse listeners.
     */
    public OverlayPanel() {
        scale = 1.0;
        MouseListener drawMouseListener = new DrawMouseListener();
        MouseMotionListener drawMotionListener = new DrawMouseListener();
        addMouseListener(drawMouseListener);
        addMouseMotionListener(drawMotionListener);
        // System.out.println("Created Overlay");
    }

    /**
     * <p>
     * Get the currently displayed image
     * </p>
     *
     * @return the image currently displayed.
     */

    /**
     * <p>
     * Sets the image to be fully opaque. This should only be done once.
     * </p>
     *
     * @param image The new image.
     */

    /**
     * <p>
     * Get the height of the image.
     * </p>
     * 
     * @return The height of the image currently displayed.
     */
    public int getImageHeight() {
        return this.getHeight();
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
     * Sets whether or not the mouse is currently pressed. This is used to decide if
     * we should be displaying the shape in the preview panel.
     * 
     * @param down The new value of mouse down
     */
    public void setMouseDown(boolean down) {
        this.mouseDown = down;
    }

    /**
     * Gets the current value of mouse down.
     * 
     * @return The value of mouse down
     */
    public boolean getMouseDown() {
        return mouseDown;
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
        // g2.clearRect(0, 0, WIDTH, HEIGHT);
        g2.scale(scale, scale);
        // g2.drawImage(image, null, 0, 0);
        // setBackground(new Color(0, 0, 0, 50));
        if (Settings.isCropping() || Settings.isSelecting()) {
            g2.setColor(new Color(150, 150, 150, 100));
            g2.fill(ShapeHandler.getCurrentOverlayShape());
            g2.draw(ShapeHandler.getCurrentOverlayShape());
        } else if (mouseDown && Settings.getDrawing()) {
            if (Settings.isErasing()) {
                g2.setColor(Color.white);
            } else {
                g2.setColor(Settings.getPenColor());
            }
            g2.setStroke(new BasicStroke((int) (Settings.getStrokeSize() * ImageAction.target.getScale())));

            g2.draw(ShapeHandler.getCurrentOverlayShape());
            // System.out.println(Settings.getStrokeSize());

            // g2.draw(new Line2D.Double(0, 0, 100, 200));
            // System.out.println("Overlay panel drawing");
            // g2.drawLine((int) startLine.getX(), (int) startLine.getY(), (int)
            // endLine.getX(), (int) endLine.getY());

            // Draw a rect
            // if (startLine.getX() < endLine.getX()) {
            // g2.drawRect((int) startLine.getX(), (int) startLine.getY(),
            // (int) Math.abs((startLine.getX() - endLine.getX())),
            // (int) (Math.abs(startLine.getY() - endLine.getY())));
            // } else {
            // g2.drawRect((int) (endLine.getX() - startLine.getX()), (int) (endLine.getY()
            // - startLine.getY()),
            // (int) Math.abs((startLine.getX() - endLine.getX())),
            // (int) (Math.abs(startLine.getY() - endLine.getY())));
            // }
            // System.out.println("Hit");
        } else {
            g2.clearRect(0, 0, WIDTH, HEIGHT);
            // System.out.println("Clear called");
        }
        // System.out.println(Settings.getDrawing());
        // g2.drawLine(20, 20, 100, 70);
        g2.dispose();

    }
}
