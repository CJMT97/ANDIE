package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.Arrays;

/**
 * <p>
 * ImageOperation to apply a median filter to an image.
 * </p>
 * 
 * <p>
 * A Median filter blurs an image by taking the median RGB values of pixels
 * in a surrounding neighbourhood.
 * </p>
 * 
 * @author Alex Poore
 * @version 1.0
 */
public class MedianBlur implements ImageOperation, java.io.Serializable {

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
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the kernel used.
     * A size of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianBlur.
     */
    MedianBlur(int radius) {
        this.radius = radius;
        bound = Settings.getBound();
    }

    /**
     * <p>
     * Construct a Median filter with the given size.
     * </p>
     * 
     * <p>
     * The size of the filter is the 'radius' of the kernel used.
     * A radius of 1 is a 3x3 filter, 2 is 5x5, and so on.
     * Larger filters give a stronger blurring effect.
     * </p>
     * 
     * @param radius The radius of the newly constructed MedianBlur.
     * @param width The width of the image
     * @param height The Height of the Image
     */
    MedianBlur(int radius, int width, int height) {
        this(radius);
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Construct a Median filter with the default size.
     * </p
     * >
     * <p>
     * By default, a Median filter has radius 1.
     * </p>
     * 
     * @see MedianBlur(int)
     */
    MedianBlur() {
        this(1);
    }

    /**
     * <p>
     * Set the radius of the filter.
     * </p>
     * 
     * @param i The new radius
     */
    public void setRadius(int i) {
        this.radius = i;
    }

    /**
     * <p>
     * Apply a Median filter to an image.
     * </p>
     * 
     * <p>
     * The Median filter is implemented via taking the median RGB values of the
     * pixels in a kernal.
     * The kernel size is specified by the {@link radius}.
     * Larger radii lead to stronger blurring.
     * </p>
     * 
     * @param image The image to apply the Median filter to.
     * @return The resulting (blurred) image.
     */
    public BufferedImage apply(BufferedImage image) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(image, bound, newWidth, newHeight);
        BufferedImage imageToWorkOff = ImageHelper.getSubImage(image, scaledBound);

        int kernalSize = (radius * 2 + 1);

        BufferedImage output = new BufferedImage(imageToWorkOff.getWidth(), imageToWorkOff.getHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);

        int x = 0;
        int y = 0;
        for (int c = x; c < imageToWorkOff.getWidth(); c++) {
            for (int d = y; d < imageToWorkOff.getHeight(); d++) {

                int[] aArray = new int[kernalSize * kernalSize];
                int aIndex = 0;

                int[] rArray = new int[kernalSize * kernalSize];
                int rIndex = 0;

                int[] gArray = new int[kernalSize * kernalSize];
                int gIndex = 0;

                int[] bArray = new int[kernalSize * kernalSize];
                int bIndex = 0;

                // Loops here to imitate a kernal.
                for (int w = c - radius; w <= c + radius; w++) {
                    for (int h = d - radius; h <= d + radius; h++) {

                        if (w < 0 && h < 0) {
                            int argb = imageToWorkOff.getRGB(0, 0);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (h < 0 && w >= imageToWorkOff.getWidth()) {
                            int argb = imageToWorkOff.getRGB(imageToWorkOff.getWidth() - 1, 0);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (h >= imageToWorkOff.getHeight() && w < 0) {
                            int argb = imageToWorkOff.getRGB(0, imageToWorkOff.getHeight() - 1);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (h >= imageToWorkOff.getHeight() && w >= imageToWorkOff.getWidth()) {
                            int argb = imageToWorkOff.getRGB(imageToWorkOff.getWidth() - 1,
                                    imageToWorkOff.getHeight() - 1);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (h < 0 && w >= 0 && w < imageToWorkOff.getWidth()) {
                            int argb = imageToWorkOff.getRGB(w, 0);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (w >= imageToWorkOff.getWidth() && h >= 0 && h < imageToWorkOff.getHeight()) {
                            int argb = imageToWorkOff.getRGB(imageToWorkOff.getWidth() - 1, h);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (h >= imageToWorkOff.getHeight() && w >= 0 && w < imageToWorkOff.getWidth()) {
                            int argb = imageToWorkOff.getRGB(w, imageToWorkOff.getHeight() - 1);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (w < 0 && h >= 0 && h < imageToWorkOff.getHeight()) {
                            int argb = imageToWorkOff.getRGB(0, h);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }

                        if (h >= 0 && h < imageToWorkOff.getHeight() && w >= 0 && w < imageToWorkOff.getWidth()) {
                            int argb = imageToWorkOff.getRGB(w, h);
                            int r = (argb & 0x00FF0000) >> 16;
                            int g = (argb & 0x0000FF00) >> 8;
                            int b = (argb & 0x000000FF);
                            int a = (argb & 0xFF000000) >> 24;
                            aArray[aIndex] = a;
                            if (aIndex < aArray.length) {
                                aIndex++;
                            }
                            rArray[rIndex] = r;
                            if (rIndex < rArray.length) {
                                rIndex++;
                            }
                            gArray[gIndex] = g;
                            if (gIndex < gArray.length) {
                                gIndex++;
                            }
                            bArray[bIndex] = b;
                            if (bIndex < bArray.length) {
                                bIndex++;
                            }
                        }
                    }
                }

                // Calling median function to get median values for each channel.
                int medA = getMedian(aArray);
                int medR = getMedian(rArray);
                int medG = getMedian(gArray);
                int medB = getMedian(bArray);
                // Setting the pixel in question to the median value.
                // System.out.println("c after second loops = " + c);
                int rgb = (medA << 24) | (medR << 16) | (medG << 8) | medB;
                output.setRGB(c, d, rgb); // Here is where values are set.
            }
        }
        return ImageHelper.pasteOnImage(image, output, scaledBound);
    }

    /**
     * <p>
     * Calculates the median value of an array of integers.
     * </p>
     * 
     * @param input The imput image.
     * @return The median int value in the input array.
     */
    public int getMedian(int[] input) {
        // This will feed in an int array of unsorted single channel values.
        int[] output = input;
        Arrays.sort(output);
        int indexOfMedian = 0;
        indexOfMedian = ((input.length - 1) / 2);
        int returnValue = output[indexOfMedian];
        return returnValue;
    }
}
