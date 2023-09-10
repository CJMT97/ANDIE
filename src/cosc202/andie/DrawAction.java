package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.image.*;

import javax.swing.ImageIcon;

/**
 * <p>
 * Actions provided for drawing menu.
 * </p>
 * 
 * <p>
 * This contains the functionality to draw onto the buffered image.
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class DrawAction extends ImageAction {

    /**
     * <p>
     * Create a new Draw Line Action.
     * </p>
     * 
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    DrawAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon, desc, mnemonic);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        target.getImage().apply(new DrawShape());
    }

    /**
     * <p>
     * ImageOperation to draw a shape on an image.
     * </p>
     * 
     * <p>
     * Draws the current shape in the Image Handler onto the buffered image.
     * </p>
     * 
     * @author Hamish Dudley
     * @version 1.0
     */
    public class DrawShape implements ImageOperation, java.io.Serializable {
        /**
         * The Stroke Size of the Drawing
         */
        private int strokeSize;
        /**
         * The pen color to draw with
         */
        private Color penColor;
        /**
         * the current shape that is being drawn
         */
        private Shape currentShape;

        /**
         * <p>
         * Constructs a new shape drawing
         * </p
         * >
         * <p>
         * By default, a line is drawn from (20,20) to (100,150)
         * </p>
         */
        DrawShape() {
            if (Settings.isErasing()) {
                this.penColor = Color.WHITE;
            } else {
                this.penColor = Settings.getPenColor();
            }
            this.strokeSize = Settings.getStrokeSize();
            this.currentShape = ShapeHandler.getCurrentShape();
        }

        /**
         * <p>
         * Draw a shape on an image.
         * </p>
         * 
         * @param input The image to draw the shape onto.
         * @return The resulting image with shape drawn on.
         */
        public BufferedImage apply(BufferedImage input) {
            Graphics2D g2 = input.createGraphics();
            g2.setColor(penColor);
            // Set the thinkness of the line
            g2.setStroke(new BasicStroke(strokeSize));
            // g2.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int)
            // end.getY());
            g2.draw(currentShape);
            // System.out.println(strokeSize);
            // g2.draw(new Line2D.Double(0, 0, 100, 200));
            // g2.drawLine(0, 0, 100, 150);
            // System.out.println("Draw action drawing" +
            // currentShape.getBounds2D().toString());

            g2.dispose();
            return input;
        }

    }

}
