package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.*;

/**
 * <p>
 * ImageOperation to apply a Mean (simple blur) filter.
 * </p>
 * 
 * <p>
 * A Mean filter blurs an image by replacing each pixel by the average of the
 * pixels in a surrounding neighbourhood, and can be implemented by a
 * convoloution.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @version 1.0
 */
public class MeanFilter implements ImageOperation, java.io.Serializable {

    /**
     * The size of filter to apply. A radius of 1 is a 3x3 filter, a radius of 2 a
     * 5x5 filter, and so forth.
     */
    private int radius;
    /** The bound to scale by */
    private Rectangle2D bound;
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
     * Construct a Mean filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MeanFilter
     */
    MeanFilter(int radius) {
        this.radius = radius;
        bound = Settings.getBound();
    }

    MeanFilter(int radius, int width, int height) {
        this(radius);
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Set the radius of the Filter
     * </p>
     * 
     * @param i The new radius
     */
    public void setRadius(int i) {
        this.radius = i;
    }

    /**
     * <p>
     * Construct a Mean filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Mean filter has radius 1.
     * </p>
     * 
     * @see MeanFilter(int)
     */
    MeanFilter() {
        this(1);
    }

    /**
     * <p>
     * Apply a Mean filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Mean filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Mean filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);
        BufferedImage enlargedImage = ImageHelper.fillImageBorder(ImageHelper.enlargeImage(subImage, radius), radius);
        int size = (2 * radius + 1) * (2 * radius + 1);
        float[] array = new float[size];
        Arrays.fill(array, 1.0f / size);

        Kernel kernel = new Kernel(2 * radius + 1, 2 * radius + 1, array);
        // RenderingHints hints = new RenderingHints(null, kernel);
        // ConvolveOp convOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, hints);
        ConvolveOp convOp = new ConvolveOp(kernel);
        BufferedImage output = new BufferedImage(enlargedImage.getColorModel(),
                enlargedImage.copyData(null),
                enlargedImage.isAlphaPremultiplied(), null);
        convOp.filter(enlargedImage, output);
        return ImageHelper.pasteOnImage(input, ImageHelper.cropImage(output, radius), scaledBound);
    }

}
