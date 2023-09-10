package cosc202.andie;

import java.awt.image.BufferedImage;

/**
 * <p>
 * RotateImage_90, either rotates the image to the left or the right.
 * does this via a matrix manipulation.
 * </p>
 * 
 * @author Ben Nicholson
 */
public class RotateImage implements ImageOperation, java.io.Serializable {

    /**
     * The currently selected option
     */
    private int option_rotate;

    /**
     * The constructor that initilises the option_rotate datafield
     * @param option_rotate The Option for rotating 
     */
    RotateImage(int option_rotate) {
        this.option_rotate = option_rotate;
    }

    /**
     * The constructor that initilises the option_rotate datafield to be 0
     */
    RotateImage() {
        this.option_rotate = 0;
    }

    /**
     * apply sorts whether this is a right or left rotation.
     * If the constructor is passed a "True", it is right rotation else, left
     * rotation.
     * 
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        BufferedImage output;
        if (this.option_rotate == 1) {
            output = rotateRight(input);
        } else if (this.option_rotate == 2) {
            output = rotateLeft(input);
        } else {
            output = rotate180(input);
        }
        return output;
    }

    /**
     * rotateLeft takes a Image and alters it so the NxM becomes MxN to the left (so
     * fill from left hand side.)
     * 
     * @param input Takes the bufferedImage and does a right rotation on it.
     * @return returns the rotated image.
     * 
     */
    public BufferedImage rotateRight(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
        int a = 0;
        for (int i = 0; i < input.getWidth(); i++) {
            for (int j = input.getHeight() - 1; j != -1; j--) {
                output.setRGB(j, i, input.getRGB(i, a));
                a++;
            }
            a = 0;
        }
        return output;
    }

    /**
     * 
     * rotateLeft takes a Image and alters it so the NxM becomes MxN to the left.
     * (so fill from left hand side.)
     * 
     * @param input Takes the bufferedImage and does a left 90* rotation on it.
     * @return returns the rotated image.
     * 
     */
    public BufferedImage rotateLeft(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
        int h = 0;
        for (int i = input.getWidth() - 1; i != -1; i--) {
            for (int j = input.getHeight() - 1; j != -1; j--) {
                output.setRGB(j, i, input.getRGB(h, j));
            }
            h++;
        }
        return output;
    }

    /**
     * Rotate180, rotates an image 180
     * 
     * @param input the bufferedImage
     * @return returns a new image rotated 180*
     */
    public BufferedImage rotate180(BufferedImage input) {
        BufferedImage output = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
        output = rotateLeft(input);
        output = rotateLeft(output);
        return output;
    }

}
