package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * Image operation the flip an image Vertially.
 * </p>
 * 
 * @author Charlie Templeton
 * @version 1.0
 */

public class FlipVertical implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Swaps each pixel vertically to flip the image vertically.
     * </p>
     * 
     * @return img THe buffered image fliped vertically.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        BufferedImage img = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < input.getHeight(); i++) {
            for (int j = 0; j < input.getWidth(); j++) {
                img.setRGB(j, input.getHeight() - 1 - i, input.getRGB(j, i));
            }
        }
        return img;
    }

}
