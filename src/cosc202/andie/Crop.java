package cosc202.andie;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to crop an image.
 * </p>
 * 
 * <p>
 * An action to crop an image
 * </p>
 * 
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class Crop implements ImageOperation, java.io.Serializable {

    /**
     * The rectangle which bounds the cropped image.
     */
    private Rectangle rectangle;
    /**
     * Used for scaling the preview image
     */
    private int newWidth = -1;
    /**
     * Used for scaling the preview image
     */
    private int newHeight = -1;

    /**
     * <p>
     * Construct a crop to be applied to the inputted shape.
     * </p>
     * 
     * <p>
     * The shape is a rectangle which comes from Image Handler and should bound the
     * area which will become the neew cropped image.
     * </p>
     * 
     * @param r The rectangle received from where the user has dragged
     *          Uses ShapeHandler to get this
     */
    Crop(Shape shape) {
        Rectangle2D.Double r = (Rectangle2D.Double) shape;
        this.rectangle = new Rectangle((int) r.getX(), (int) r.getY(), (int) r.getWidth(), (int) r.getHeight());
    }

    Crop(Shape shape, int width, int height) {
        this(shape);
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Crops the image.
     * </p>
     * 
     * @param input The image to apply the Mean filter to.
     * @return The resulting cropped image.
     */
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, rectangle, newWidth, newHeight);
        BufferedImage image = ImageHelper.getSubImage(input, scaledBound);
        BufferedImage blank = new BufferedImage(
                image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < blank.getWidth(); x++) {
            for (int y = 0; y < blank.getHeight(); y++) {
                blank.setRGB(x, y, image.getRGB(x, y));
            }
        }
        return blank;
    }

}
