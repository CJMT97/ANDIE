package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * ImageOperation to apply a change of brightness and contrast.
 * </p>
 * 
 * <p>
 * The Brightness and Contrast change is defined by the equation
 * (1.0+contrastChange/100.0)*(currentValue -127.5) +
 * 127.5*(1.0+brightnessChange/100.0)
 * where brightnessChange and contrastChange are numbers betweeen -100 and 100.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @see java.awt.image.ConvolveOp
 * @author Steven Mills
 * @author Hamish Dudley
 * @version 1.0
 */
public class BrightnessAndContrast implements ImageOperation, java.io.Serializable {

    /**
     * The percentage change of contrast to apply.
     */
    private int contrastChange;
    /**
     * The percentage change of brightness to apply.
     */
    private int brightnessChange;
    /**
     * The bound to apply to
     */
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
     * Construct a Brightness and Contrast change with given Brightness change and
     * Contrast change.
     * </p>
     * 
     * <p>
     * brightnessChange and contrastChange are integers between -100 and 100 with
     * the extremities having stronger effects on the respective operation.
     * </p>
     * 
     * @param brightnessChange The percentage change of contrast to apply.
     * @param contrastChange   The percentage change of contrast to apply.
     */
    BrightnessAndContrast(int brightnessChange, int contrastChange) {
        setBrightnessAndContrast(brightnessChange, contrastChange);
        bound = Settings.getBound();
    }

    /**
     * <p>
     * Construct a Brightness and Contrast change with given Brightness change and
     * Contrast change. Uses the width and height given to rescale the bounds to
     * correlate to a preview panel.
     * </p>
     * 
     * <p>
     * brightnessChange and contrastChange are integers between -100 and 100 with
     * the extremities having stronger effects on the respective operation.
     * </p>
     * 
     * @param brightnessChange The percentage change of contrast to apply.
     * @param contrastChange   The percentage change of contrast to apply.
     * @param width            The width to scale bounds to.
     * @param height           The height to scale bounds to.
     */
    BrightnessAndContrast(int brightnessChange, int contrastChange, int width, int height) {
        setBrightnessAndContrast(brightnessChange, contrastChange);
        bound = Settings.getBound();
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Set the brightness and contrast percentages.
     * </p>
     * 
     * @param brightnessChange The new brightness change as a percentage.
     * @param contrastChange   The new contrast change as a percentage.
     */
    public void setBrightnessAndContrast(int brightnessChange, int contrastChange) {
        this.brightnessChange = brightnessChange;
        this.contrastChange = contrastChange;
    }

    /**
     * <p>
     * Apply a Brightness and Contrast change to an image.
     * </p>
     * 
     * <p>
     * The change in brightness and contrast is defined by the equation outlined in
     * the class declaration.
     * </p>
     * 
     * @see BrightnessAndContrast
     * 
     * @param input The image to apply the brightness and contrast filter to.
     * @return The resulting changed image.
     */
    public BufferedImage apply(BufferedImage input) {
        // System.out.println("SubImage: " + input.getWidth() + ", " +
        // input.getHeight());
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);

        BufferedImage output = new BufferedImage(subImage.getWidth(), subImage.getHeight(), subImage.getType());
        // BufferedImage output = new BufferedImage(
        // subImage.getColorModel(),
        // subImage.copyData(null),
        // subImage.isAlphaPremultiplied(), null);
        for (int y = 0; y < subImage.getHeight(); ++y) {
            for (int x = 0; x < subImage.getWidth(); ++x) {
                int argb = subImage.getRGB(x, y);
                int a = (argb & 0xFF000000) >> 24;
                int r = (argb & 0x00FF0000) >> 16;
                int g = (argb & 0x0000FF00) >> 8;
                int b = (argb & 0x000000FF);

                int newR = getValue(r);
                int newG = getValue(g);
                int newB = getValue(b);

                argb = (a << 24) | (newR << 16) | (newG << 8) | newB;
                output.setRGB(x, y, argb);
            }
        }
        return ImageHelper.pasteOnImage(input, output, scaledBound);
    }

    private int getValue(int currentValue) {
        double c = (1.0 + contrastChange / 100.0) * (currentValue - 127.5);
        double b = 127.5 * (1.0 + brightnessChange / 100.0);
        int out = (int) (c + b);
        if (out > 255) {
            out = 255;
            // System.out.println("Failed: " + currentValue + " --> " + out);
        }
        if (out < 0) {
            out = 0;
        }
        return out;
    }

}
