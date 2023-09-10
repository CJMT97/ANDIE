package cosc202.andie;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * ImageOperation to apply Saturation filter to an image.
 * </p>
 * 
 * <p>
 * A saturation filter changes the saturation of the images.
 * </p>
 * 
 */
public class SaturationFilter implements ImageOperation, java.io.Serializable {

    /** The Level of saturation */
    private float saturation;
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
     * Construct a SaturationFilter to be applied to with the current bounds.
     */
    public SaturationFilter() {
        bound = Settings.getBound();
    }

    /**
     * Construct a SaturationFilter to be applied to with the current bounds with
     * scaling.
     */
    SaturationFilter(int width, int height) {
        bound = Settings.getBound();
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * A method to get the current saturation value of the filter.
     * 
     * @param saturation The Current Level of saturation
     */
    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    /**
     * The method to apply the saturation filter to the image.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);
        // BufferedImage enlargedImage =
        // ImageHelper.fillImageBorder(ImageHelper.enlargeImage(input, 1), 1);

        // Get the dimensions of the image we are working with
        int width = subImage.getWidth();
        int height = subImage.getHeight();

        // Create a new image to store the output
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Iterate over every pixel in the input image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the color of the pixel
                int color = subImage.getRGB(x, y);

                // Extract the red, green, and blue components
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                if (red >= 240 && green >= 240 && blue >= 240 && saturation > 0) {
                    // The color is white
                    output.setRGB(x, y, color);
                    continue;
                }
                if (red == green && red == blue && green == blue && saturation > 0) {
                    // The color is white
                    output.setRGB(x, y, color);
                    continue;
                }
                // Convert the RGB components to HSB (hue, saturation, brightness) color space
                float[] hsb = Color.RGBtoHSB(red, green, blue, null);

                // Increase the saturation of the pixel
                hsb[1] += saturation;

                // Adjust the brightness value in proportion to the saturation change
                hsb[2] += (saturation * 0.3f);

                // Ensure the saturation and brightness values are within the valid range of 0.0
                // to 1.0
                hsb[1] = Math.max(0.0f, Math.min(1.0f, hsb[1]));
                hsb[2] = Math.max(0.0f, Math.min(1.0f, hsb[2]));

                // Convert the HSB color back to RGB
                int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);

                // Set the new color of the pixel in the output image
                output.setRGB(x, y, rgb);
            }
        }

        return ImageHelper.pasteOnImage(input, output, scaledBound);
    }

    /**
     * Getter method for getting the saturation for Unit test
     * @return The saturation level
     */
    public float getSaturation() {
        return this.saturation;
    }

}
