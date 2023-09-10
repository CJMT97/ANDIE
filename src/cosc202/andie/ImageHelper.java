package cosc202.andie;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * A class to help manipulate images. This class is intended for use with the
 * filters
 * 
 * @author Hamish Dudley
 */
public class ImageHelper {

    /**
     * A method to enlarge an image by the radius.
     * A black square will be added to each side of the image of width and length
     * given by radius.
     * 
     * @param image  The image to add a black border to
     * @param radius The size of the black border added
     * @return The new image with a black border
     */
    public static BufferedImage enlargeImage(BufferedImage image, int radius) {
        BufferedImage output = new BufferedImage(image.getWidth() + radius * 2, image.getHeight() + radius * 2,
                image.getType());
        Graphics g = output.getGraphics();
        g.drawImage(image, radius, radius, null);
        g.dispose();
        return output;
    }

    /**
     * A method to fill the image boder of size radius with the nearest pixel colour
     * 
     * @param image  The image with a border size radius to be filled with nearest
     *               pixels
     * @param radius The size of the border being filled
     * @return An image with border filled with the nearest pixel colour
     */
    public static BufferedImage fillImageBorder(BufferedImage image, int radius) {
        int width = image.getWidth();
        int height = image.getHeight();
        int corner = image.getRGB(radius, radius);
        // int count = 0;
        // Top left corner
        for (int x = 0; x < radius; x++) {
            for (int y = 0; y < radius; y++) {
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Top right corner
        corner = image.getRGB(width - radius - 1, radius);
        for (int x = width - radius; x < width; x++) {
            for (int y = 0; y < radius; y++) {
                // System.out.println(x + ", " + y);
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Bottom left corner
        corner = image.getRGB(radius, height - radius - 1);
        for (int x = 0; x < radius; x++) {
            for (int y = height - radius; y < height; y++) {
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Bottom right corner
        corner = image.getRGB(width - radius - 1, height - radius - 1);
        for (int x = width - radius; x < width; x++) {
            for (int y = height - radius; y < height; y++) {
                // System.out.println(x + ", " + y);
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Top
        for (int x = radius; x < width - radius; x++) {
            for (int y = 0; y < radius; y++) {
                // System.out.println(x + ", " + y);
                corner = image.getRGB(x, radius);
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Bottom
        for (int x = radius; x < width - radius; x++) {
            for (int y = height - radius; y < height; y++) {
                // System.out.println(x + ", " + y);
                corner = image.getRGB(x, height - radius - 1);
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Left
        for (int x = 0; x < radius; x++) {
            for (int y = radius; y < height - radius; y++) {
                // System.out.println(radius + ", " + y);
                corner = image.getRGB(radius, y);
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Right
        for (int x = width - radius; x < width; x++) {
            for (int y = radius; y < height - radius; y++) {
                // System.out.println((width - radius - 1) + ", " + y);
                corner = image.getRGB(width - radius - 1, y);
                image.setRGB(x, y, corner);
                // count++;
            }
        }
        // Count is to check doing right number of operations ie not chaning boundaries
        // more than once
        // System.out.println(count);
        return image;
    }

    /**
     * A method to crop an image by removing radius from each side. This leaves the
     * center of the image
     * 
     * @param image  The image to be cropped
     * @param radius The size to crop off the input image
     * @return The center of the input image with radius removed from all sides
     */
    public static BufferedImage cropImage(BufferedImage image, int radius) {
        BufferedImage output = new BufferedImage(image.getWidth() - radius * 2, image.getHeight() - radius * 2,
                image.getType());
        return pasteOnImage(output,
                image.getSubimage(radius, radius, image.getWidth() - 2 * radius, image.getHeight() - 2 * radius),
                new Rectangle2D.Double(0, 0, image.getWidth() - 2 * radius, image.getHeight() - 2 * radius));
    }

    /**
     * A method to paste an image onto another image at a given location. Used when
     * applying filters to selected areas
     * 
     * @param input The image to paste onto
     * @param image The image to paste
     * @param bound The location to paste the image
     * @return The input image with the image pasted on at specified location in a
     *         new buffered image.
     */
    public static BufferedImage pasteOnImage(BufferedImage input, BufferedImage image, Rectangle2D bound) {
        BufferedImage blank = new BufferedImage(
                input.getColorModel(),
                input.copyData(null),
                input.isAlphaPremultiplied(), null);
        // Settings.setSelecting(false);
        if (bound != null) {
            for (int x = (int) bound.getX(); x < (int) (bound.getX() + bound.getWidth()); x++) {
                for (int y = (int) bound.getY(); y < (int) (bound.getY() + bound.getHeight()); y++) {
                    blank.setRGB(x, y, image.getRGB((int) (x - bound.getX()), (int) (y - bound.getY())));
                }
            }
            return blank;
        }
        return image;
    }

    /**
     * A method to get a subimage of an image. Used when applying filters to
     * selected areas
     * 
     * @param image The image to get a subimage of
     * @param bound The location of the subimage
     * @return The subimage of the input image
     */
    public static BufferedImage getSubImage(BufferedImage image, Rectangle2D bound) {
        // System.out.println(image.getWidth() + ", " + image.getHeight());
        // System.out.println(bound.getX() + ", " + bound.getY() + ", " +
        // bound.getWidth() + ", " + bound.getHeight());
        if (bound != null) {
            image = image.getSubimage((int) bound.getX(), (int) bound.getY(), (int) bound.getWidth(),
                    (int) bound.getHeight());
        }
        return image;
    }

    /**
     * A method to scale the bounds of a selected area to fit the new width and
     * height.
     * 
     * @param input     The image to scale the bounds from
     * @param bound     The bounds to scale
     * @param newWidth  The new width to scale to
     * @param newHeight The new height to scale to
     * @return The bounds scaled to the new width and height
     */
    public static Rectangle2D scaleBound(BufferedImage input, Rectangle2D bound, int newWidth, int newHeight) {
        Rectangle2D scaledBound = bound;
        if ((bound != null && newWidth != -1 && newHeight != -1) || (input.getWidth() != newWidth
                && input.getHeight() != newHeight)) {
            // Rescale the bounds
            double scaleWidth = (double) newWidth / input.getWidth();
            double scaleHeight = (double) newHeight / input.getHeight();
            scaledBound = new Rectangle2D.Double(
                    bound.getX() / scaleWidth,
                    bound.getY() / scaleHeight,
                    bound.getWidth() / scaleWidth,
                    bound.getHeight() / scaleHeight);
            // System.out
            // .println("Bounds: " + bound.getX() + ", " + bound.getY() + ", " +
            // bound.getWidth()
            // + ", " + bound.getHeight());
            // System.out
            // .println("Scaled Bounds: " + scaledBound.getX() + ", " + scaledBound.getY() +
            // ", "
            // + scaledBound.getWidth()
            // + ", " + scaledBound.getHeight());
        }
        return scaledBound;
    }
}
