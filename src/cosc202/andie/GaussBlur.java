package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a gauss blur filter to an image.
 * </p>
 * 
 * <p>
 * A Gauss blur blurs an image by constructing a kernel and applying the Gauss
 * formula to it.
 * </p>
 * 
 * @author Charlie Templeton
 * @version 1.0
 */
public class GaussBlur implements ImageOperation, java.io.Serializable {

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
     * Construct a Gauss filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the convolution kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed GaussFilter.
     */
    public GaussBlur(int radius) {
        this.radius = radius;
        bound = Settings.getBound();
    }

    /**
     * <p>
     * The constructor wihich initilises the width and height and calls
     * the other constructor to initilise the radius and bound
     * </p>
     * @param radius The redius of the kernel
     * @param width The Width of the image
     * @param height The Height of the image
     */
    GaussBlur(int radius, int width, int height) {
        this(radius);
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Set the radius of the Filter.
     * </p>
     * 
     * @param i The new radius
     */
    public void setRadius(int i) {
        this.radius = i;
    }

    /**
     * <p>
     * Construct a Gauss filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Gauss filter has radius 1.
     * </p>
     * 
     * @see GaussFilter(int)
     */
    GaussBlur() {
        this(1);
        bound = Settings.getBound();
    }

    /**
     * <p>
     * Creates an float array which contains the gauss values for the kernel
     * with size (radius * radius + 1) * (radius * radius + 1).
     * </p>
     * 
     * <p>
     * At each assignment it calls the get gauss method to get the
     * gauss value for the index in the array.
     * </p>
     * 
     * @return oneDimKer The Gaussian kernel to apply the filter to the image.
     */
    public float[] getGaussKernel() {
        float sum = 0;
        int size = (radius * 2 + 1);
        int arraySize = size * size;
        float[] oneDimKer = new float[arraySize];
        int xPoint = 0;
        int yPoint = 0;
        int eachLine = 0;
        int count = 0;

        // Creates the array with values.
        while (count < arraySize) {
            if (eachLine < size) {
                float x = getGauss(xPoint - radius, yPoint - radius);
                sum += x;
                oneDimKer[count] = x;
                xPoint++;
                count++;
                eachLine++;
            } else if (eachLine == size) {
                xPoint = 0;
                yPoint++;
                eachLine = 0;
            }
        }

        // Normalises the array.
        for (int i = 0; i < oneDimKer.length; i++) {
            oneDimKer[i] /= sum;
        }

        return oneDimKer;
    }

    /**
     * <p>
     * Uses parameters to calculate the gaussian value.
     * </p>
     * 
     * @param x the x index point.
     * @param y the y index point.
     * @return the gaussian value at x, y.
     */
    public float getGauss(int x, int y) {
        float sigma = this.radius / 3.0f;
        float sigmaSquared = sigma * sigma;
        return (float) ((1 / (2 * Math.PI * sigmaSquared))
                * Math.exp(-1 * ((Math.pow(x, 2) + Math.pow(y, 2)) / (2 * sigmaSquared))));
    }

    /**
     * <p>
     * Apply a Gauss filter to an image.
     * </p>
     * 
     * <p>
     * As with many filters, the Gauss filter is implemented via convolution.
     * The size of the convolution kernel is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param input The image to apply the Gauss filter to.
     * @return The resulting (blurred)) image.
     */
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);

        BufferedImage enlargedImage = ImageHelper.fillImageBorder(ImageHelper.enlargeImage(subImage, radius), radius);
        float[] array = getGaussKernel();

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
