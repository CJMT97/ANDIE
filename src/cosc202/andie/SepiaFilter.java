package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * <p>
 * Sepia Filter gives image a warmth Filter
 * </p>
 * 
 * @author Charlie Templeton
 */
public class SepiaFilter implements ImageOperation, java.io.Serializable {
    /**
     * The bound of the image
     */
    private Rectangle2D bound;
    /**
     * The new width to scale the image to.
     */
    private int newWidth = -1;
    /**
     * The new height to scale the image to.
     */
    private int newHeight = -1;

    /**
     * Constructor for Sepia filter initilises the bound
     */
    SepiaFilter() {
        bound = Settings.getBound();
    }

    /**
     * Constructor for Sepia filter initilises the width and height
     * and then the bound in the other constructor
     */
    SepiaFilter(int width, int height) {
        this();
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * <p>
     * Apply method alters the image and applys a sepiua filter to it
     * </p>
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);

        BufferedImage enlargedImage = ImageHelper.fillImageBorder(ImageHelper.enlargeImage(subImage, 1), 1);

        // Get the dimensions of the input image
        int width = subImage.getWidth();
        int height = subImage.getHeight();

        // Create a new image to store the output
        BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Define the sepia color
        int sepiaDepth = 20;
        // int sepiaRed = 112;
        // int sepiaGreen = 66;
        // int sepiaBlue = 20;

        // Iterate over every pixel in the input image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Get the color of the pixel
                int color = input.getRGB(x, y);

                // Extract the red, green, and blue components
                int red = (color >> 16) & 0xff;
                int green = (color >> 8) & 0xff;
                int blue = color & 0xff;

                // Calculate the new values of the red, green, and blue components
                int newRed = (int) Math.min((red * 0.393 + green * 0.769 + blue * 0.189) + sepiaDepth, 255);
                int newGreen = (int) Math.min((red * 0.349 + green * 0.686 + blue * 0.168) + sepiaDepth, 255);
                int newBlue = (int) Math.min((red * 0.272 + green * 0.534 + blue * 0.131) + sepiaDepth, 255);

                // Apply the sepia color to the pixel
                int sepiaColor = (newRed << 16) | (newGreen << 8) | newBlue;
                output.setRGB(x, y, sepiaColor);
            }
        }

        return ImageHelper.pasteOnImage(input, output, scaledBound);
    }

}
