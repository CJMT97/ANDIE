package cosc202.andie;

import java.awt.geom.Rectangle2D;
import java.awt.image.*;

/**
 * <p>
 * Class for generating emboss filters
 * </p>
 * 
 * @author Charlie Templeton
 */
public class EmbossFilters implements ImageOperation, java.io.Serializable {
    /** Which Emboos filter to apply*/
    private int filterType;
    /** The bound to apply the scale too */
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
     * @param filterType The Type of Emboss Filter
     */
    public EmbossFilters(int filterType) {
        this.filterType = filterType;
        bound = Settings.getBound();
    }

    EmbossFilters(int filterType, int width, int height) {
        this(filterType);
        this.newWidth = width;
        this.newHeight = height;
    }

    /**
     * Gets the kernel for the appropriate emboss filter
     * 
     * @return the kernel
     */
    public float[] getKernel() {
        if (filterType == 1) {
            float[] array = { 0, 0, 0,
                    1f, 0, -1f,
                    0, 0, 0 };
            return array;
        } else if (filterType == 2) {
            float[] array = { 1f, 0, 0,
                    0, 0, 0,
                    0, 0, -1f };
            return array;
        } else if (filterType == 3) {
            float[] array = { 0, 1f, 0,
                    0, 0, 0,
                    0, -1f, 0 };
            return array;
        } else if (filterType == 4) {
            float[] array = { 0, 0, 1f,
                    0, 0, 0,
                    -1f, 0, 0 };
            return array;
        } else if (filterType == 5) {
            float[] array = { 0, 0, 0,
                    -1f, 0, 1f,
                    0, 0, 0 };
            return array;
        } else if (filterType == 6) {
            float[] array = { -1f, 0, 0,
                    0, 0, 0,
                    0, 0, 1f };
            return array;
        } else if (filterType == 7) {
            float[] array = { 0, -1f, 0,
                    0, 0, 0,
                    0, 1f, 0 };
            return array;
        } else {
            float[] array = { 0, 0, -1f,
                    0, 0, 0,
                    1f, 0, 0 };
            return array;
        }
    }

    /** 
     * <p>
     * Apply Method applys the Emboss filter that is selected according to filterType
     * </p>
     */
    public BufferedImage apply(BufferedImage input) {
        Rectangle2D scaledBound = ImageHelper.scaleBound(input, bound, newWidth, newHeight);
        BufferedImage subImage = ImageHelper.getSubImage(input, scaledBound);
        // Enlarge the input image by one pixel on each side
        BufferedImage enlargedImage = ImageHelper.fillImageBorder(ImageHelper.enlargeImage(subImage, 1), 1);

        // Get the convolution kernel
        float[] kernelArray = getKernel();

        // Get the dimensions of the enlarged image and the kernel
        int imageWidth = enlargedImage.getWidth();
        int imageHeight = enlargedImage.getHeight();
        int kernelWidth = 3;

        // Create a new image to store the convolved image
        BufferedImage output = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);

        int offset = 127;

        // Iterate over every pixel in the image
        for (int y = 1; y < imageHeight - 1; y++) {
            for (int x = 1; x < imageWidth - 1; x++) {
                // Apply the convolution kernel to the current pixel
                float sumRed = 0;
                float sumGreen = 0;
                float sumBlue = 0;
                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        // Get the color of the pixel
                        int color = enlargedImage.getRGB(x + kx, y + ky);
                        int red = (color >> 16) & 0xff;
                        int green = (color >> 8) & 0xff;
                        int blue = color & 0xff;

                        // Multiply the color with the corresponding kernel value and accumulate the
                        // result
                        float kernelValue = kernelArray[(ky + 1) * kernelWidth + (kx + 1)];
                        sumRed += kernelValue * red;
                        sumGreen += kernelValue * green;
                        sumBlue += kernelValue * blue;
                    }
                }

                sumRed = sumRed / 2 + offset;
                sumGreen = sumGreen / 2 + offset;
                sumBlue = sumBlue / 2 + offset;

                // Set the color of the output pixel to the convolved value
                int convolvedRed = (int) Math.min(Math.max(sumRed, 0), 255);
                int convolvedGreen = (int) Math.min(Math.max(sumGreen, 0), 255);
                int convolvedBlue = (int) Math.min(Math.max(sumBlue, 0), 255);
                int convolvedColor = (convolvedRed << 16) | (convolvedGreen << 8) | convolvedBlue;
                output.setRGB(x, y, convolvedColor);
            }
        }

        // Crop the output image to remove the border
        return ImageHelper.pasteOnImage(input, ImageHelper.cropImage(output, 1), scaledBound);
    }
}
