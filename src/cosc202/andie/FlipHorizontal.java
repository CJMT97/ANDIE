package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * Image operation the flip an image Horizontally.
 * </p>
 * 
 * @author Charlie Templeton
 * @version 1.0
 */

public class FlipHorizontal implements ImageOperation, java.io.Serializable {

    /**
     * <p>
     * Swaps each pixel horizontally to flip the image horizontally.
     * </p>
     * 
     * @return img The buffered image fliped horizontally.
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        BufferedImage img = new BufferedImage(input.getWidth(), input.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = 0; j < input.getHeight(); j++) {
                img.setRGB(input.getWidth() - 1 - i, j, input.getRGB(i, j));
            }
        }
        return img;
    }

}
