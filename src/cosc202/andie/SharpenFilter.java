package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a sharpen filter to an image.
 * </p>
 * 
 * <p>
 * A sharpen filter makes the edges of items in an image more defined.
 * </p>
 * 
 * @author PPS - Parallel Processing Squad
 */
public class SharpenFilter implements ImageOperation, java.io.Serializable {
    /**
     * The bound of where this filter should be applied.
     */
    private Rectangle2D bound;
    /**
     * The new width to scale the action to.
     */
    private int newWidth = -1;
    /**
     * The new height to scale the action to.
     */
    private int newHeight = -1;

    /**
     * Creates a new SharpenFilter with the current bound.
     */
    SharpenFilter() {
        bound = Settings.getBound();
    }

    /**
     * Creates a new SharpenFilter with the current bound and the new width and
     * newHeight to scale the image to.
     * 
     * @param width  The new width to scale the action to.
     * @param height The new height to scale the action to.
     */
    SharpenFilter(int width, int height) {
        this();
        newWidth = width;
        newHeight = height;
    }

    /**
     * <p>
     * Apply a new kernal with inputted array to each pixel.
     * Will "sharpen" the pixel relative to others.
     * </p>
     * 
     * @author Ben Nicholson
     * @version 1.0
     */
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);
        BufferedImage enlargedImage = ImageHelper.fillImageBorder(ImageHelper.enlargeImage(subImage, 1), 1);
        // The values for the kernel as a 9-element array.
        float[] array = { 0, -1 / 2f, 0,
                -1 / 2f, 3, -1 / 2f,
                0, -1 / 2f, 0 };
        // Make a 3x3 filter from the array.
        Kernel kernel = new Kernel(3, 3, array);
        // Apply this as a convolution - same code as in MeanFilter.
        ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        BufferedImage output = new BufferedImage(enlargedImage.getColorModel(),
                enlargedImage.copyData(null),
                enlargedImage.isAlphaPremultiplied(), null);
        convOp.filter(enlargedImage, output);

        // And we're done.
        return ImageHelper.pasteOnImage(input, ImageHelper.cropImage(output, 1), scaledBound);
    }
}