package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to convert an image from colour to greyscale.
 * </p>
 * 
 * <p>
 * The images produced by this operation are still technically colour images,
 * in that they have red, green, and blue values, but each pixel has equal
 * values for red, green, and blue giving a shade of grey.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public class ConvertToGrey implements ImageOperation, java.io.Serializable {

    /**
     * The bound to apply the scale to
     */
    Rectangle2D bound;
    /**
     * Used for scaling the preview image
     */
    int newWidth = -1;
    /**
     * Used for scaling the preview image
     */
    int newHeight = -1;

    /**
     * <p>
     * Create a new CovertToGrey operation.
     * </p>
     */
    ConvertToGrey() {
        bound = Settings.getBound();
    }

    /**
     * <p>
     * Create a new CovertToGrey operation.
     * </p>
     */
    ConvertToGrey(int width, int height) {
        this();
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Apply greyscale conversion to an image.
     * </p>
     * 
     * <p>
     * The conversion from red, green, and blue values to greyscale uses a
     * weighted average that reflects the human visual system's sensitivity
     * to different wavelengths -- we are most sensitive to green light and
     * least to blue.
     * </p>
     * 
     * @param input The image to be converted to greyscale
     * @return The resulting greyscale image.
     */
    public BufferedImage apply(BufferedImage input) {
        BufferedImage image = input;
        // Make the image that we apply to the image inside the bounds
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        image = ImageHelper.getSubImage(image, scaledBound);

        for (int y = 0; y < image.getHeight(); ++y) {
            for (int x = 0; x < image.getWidth(); ++x) {
                int argb = image.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int grey = (int) Math.round(0.3 * r + 0.6 * g + 0.1 * b);

                argb = (a << 24) | (grey << 16) | (grey << 8) | grey;
                image.setRGB(x, y, argb);
            }
        }
        // Paste the new image back onto the original image or just returns the original
        // image if bound is null
        return ImageHelper.pasteOnImage(input, image, scaledBound);
    }

}
