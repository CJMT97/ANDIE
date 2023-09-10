package cosc202.andie;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * <p>
 * ImageOperation to resize an image.
 * </p>
 * 
 * 
 * @author Charlie Templeton
 */
public class Resize extends Resizing implements ImageOperation, java.io.Serializable {
    /**
     * The percentage change required to resize the image. If resizing is done by
     * percent.
     */
    private double percentChange;
    /**
     * The max width of the resized image if resizing using width and height
     */
    private int maxWidth;
    /**
     * 
     * The max height of the resized image if resizing using width and height
     */
    private int maxHeight;
    /**
     * A boolean to indicate if we are resizing using percentage change or pixels.
     */
    private boolean byPercentageChange;
    /**
     * The new frame where options are displayed.
     */
    private JFrame frame;

    /**
     * A Constructor that takes a percentage as input and divides it by
     * 100.0 and stores the result in the percentageChange data field
     * 
     * @param percentChange the percent change to the size of image
     * @param error         The label to display error messages in
     * @param frame         The frame where resize options are displayed
     */
    public Resize(int percentChange, JLabel error, JFrame frame) {
        this.frame = frame;
        this.percentChange = percentChange / 100.0;
        byPercentageChange = true;
    }

    /**
     * A constructor that takes a maximum width and a maximum height.
     * If a maxWidth and maxHeight are set the image will be resized so that it fits
     * within the maximums, scaling down the largest change requried.
     * 
     * @param maxWidth  The maximum width.
     * @param maxHeight The maximum height.
     */
    public Resize(int maxWidth, int maxHeight) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        // By default it is increasing in size. This is changed in hte apply method if
        // it is decreasing
        percentChange = 2;
        byPercentageChange = false;
    }

    /**
     * Apply method overides method in image operation iterface. Method applys
     * a change in size to the image stored in the BufferedImage.
     * 
     * @param input The Buffered image to be resized
     */
    @Override
    public BufferedImage apply(BufferedImage input) {
        /*
         * if byPercentageChage is true gets width and height of input
         * and returns a call to the resizePercentChage method with the specified width
         * and height
         * otherwise calls resizePercentChange with maxWidth and mazHEight data feilds
         * passed instead
         */
        if (byPercentageChange) {
            int newWidth = getX(input.getWidth());
            int newHeight = getY(input.getHeight());
            frame.dispose();
            return resizePercentageChange(input, newWidth, newHeight);
        } else {
            if (input.getWidth() < maxHeight)
                percentChange = 0.5;
            return resizePercentageChange(input, maxWidth, maxHeight);
        }
    }

    /**
     * Method uses input and the width and height to resize the input image if
     * the resize is enlarging percent change is > 1 and is getting smaller
     * percentChange < 1
     * 
     * @param input     The input image.
     * @param newWidth  The new widht of the image.
     * @param newHeight The new height of the image
     * @return The resized image to return.
     */
    private BufferedImage resizePercentageChange(BufferedImage input, int newWidth, int newHeight) {
        try {
            if (percentChange < 1.0) {
                Image image = input.getScaledInstance(newWidth, newHeight, Image.SCALE_AREA_AVERAGING);
                BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                newImage.getGraphics().drawImage(image, 0, 0, null);
                return newImage;

            } else {
                Image image = input.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                newImage.getGraphics().drawImage(image, 0, 0, null);
                return newImage;
            }
        } catch (OutOfMemoryError | NegativeArraySizeException ex) {
            String[] okAndCancel = { Settings.getMessage("OK") };
            JOptionPane.showOptionDialog(null, Settings.getMessage("ResizeErrorTooLarge"),
                    Settings.getMessage("ResizeErrorTooLargeTitle"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, okAndCancel, okAndCancel[0]);
            return input;
        }
    }

    /**
     * gets the new width after applying the percentage change
     * 
     * @param width The current
     * @return newWidth
     */
    public int getX(int width) {
        int newWidth = (int) (width * percentChange);
        return newWidth;
    }

    /**
     * gets the new height after applying the percentage change
     * 
     * @param height The current Height.
     * @return newHeight
     */
    public int getY(int height) {
        int newHeight = (int) (height * percentChange);
        return newHeight;
    }

}
