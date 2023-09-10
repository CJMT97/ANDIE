package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a soft blur to an image.
 * </p>
 * 
 * <p>
 * A soft blur averages the pixels directly around the current pixel.
 * </p>
 * 
 * @author PPS - Parallel Processing Squad
 */
public class SoftBlur implements ImageOperation, java.io.Serializable {
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
     * Creates a new SoftBlur with the current bound.
     */
    SoftBlur() {
        bound = Settings.getBound();
    }

    /**
     * Creates a new SoftBlur with the current bound and the new width and height to
     * scale the image too.
     * 
     * @param width  The new width to scale the action to.
     * @param height The new height to scale the action to.
     */
    SoftBlur(int width, int height) {
        this();
        newWidth = width;
        newHeight = height;
    }

    /**
     * Applies a soft blur to the image using bounds and scaled to newWidth and
     * newHeight.
     * 
     * @param input The image to apply the soft blur to.
     * @return The image with a soft blur applied.
     */
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);

        BufferedImage enlargedImage = ImageHelper.fillImageBorder(ImageHelper.enlargeImage(subImage, 1), 1);

        // The values for the kernel as a 9-element array.
        float[] array = { 0, 1 / 8.0f, 0,
                1 / 8.0f, 1 / 2.0f, 1 / 8.0f,
                0, 1 / 8.0f, 0 };
        // Make a 3x3 filter from the array.
        Kernel kernel = new Kernel(3, 3, array);
        // Apply this as a convolution - same code as in MeanFilter.
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(enlargedImage.getColorModel(),
                enlargedImage.copyData(null),
                enlargedImage.isAlphaPremultiplied(), null);
        convOp.filter(enlargedImage, output);
        // And we're done.
        return ImageHelper.pasteOnImage(input, ImageHelper.cropImage(output, 1), scaledBound);
    }
}
