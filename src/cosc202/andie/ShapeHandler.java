package cosc202.andie;

import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * <p>
 * A class to manage shapes which can be drawn.
 * </p>
 * 
 * <p>
 * This class contains the methods required to draw different shapes onto a
 * panel with a preview. It also supports zoom.
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class ShapeHandler {
    /**
     * The current shape type.
     * 
     * @see setShapeType for which number corresponds to which shape.
     */
    private static int shapeType = 0;
    /**
     * The current shape.
     */
    private static Shape currentShape;
    /**
     * The current shape scaled for the overlay.
     */
    private static Shape currentOverlayShape;
    /**
     * The center point of the shape.
     */
    private static Point centerPoint;

    /**
     * A method to change the current shape type to one of the following options
     * Line is type 1
     * Free line is type 2
     * Rectangle is type 3
     * Elipse is type 4
     * Rounded rectangle is type 5
     * 
     * @param type The new image type
     */
    protected static void setShapeType(int type) {
        // System.out.println("Shape reset");
        shapeType = type;
        // Need to create the correct shape with correct constructor
        switch (shapeType) {
            case 1:
                currentShape = new Line2D.Double(0, 0, 0, 0);
                currentOverlayShape = new Line2D.Double(0, 0, 0, 0);
                break;
            case 2:
                currentShape = new Path2D.Double();
                currentOverlayShape = new Path2D.Double();
                break;
            case 3:
                currentShape = new Rectangle2D.Double();
                currentOverlayShape = new Rectangle2D.Double();
                break;
            case 4:
                currentShape = new Ellipse2D.Double();
                currentOverlayShape = new Ellipse2D.Double();
                break;
            case 5:
                currentShape = new RoundRectangle2D.Double();
                currentOverlayShape = new RoundRectangle2D.Double();
                break;
            default:
                break;
        }
    }

    /**
     * Get the current shape type.
     * 
     * @return The current shape type
     */
    public static int getShapeType() {
        return shapeType;
    }

    /**
     * Gets the current shape. This is scaled for any zooming in effect.
     * 
     * @return The current shape
     */
    protected static Shape getCurrentShape() {
        // System.out.println("Get current shape called" +
        // currentShape.getBounds2D().toString());
        Shape shape = currentShape;
        setShapeType(shapeType);
        return shape;
    }

    /**
     * Gets the current shape. This is scaled for any zooming in effect.
     * 
     * @param resetShape If the shape should be reset ready to draw another new
     *                   shape.
     * @return The current shape
     */
    protected static Shape getCurrentShape(boolean resetShape) {
        // System.out.println("Get current shape called" +
        // currentShape.getBounds2D().toString());
        Shape shape = currentShape;
        if (resetShape)
            setShapeType(shapeType);
        return shape;
    }

    /**
     * Get the current shape for the overlay.
     * This is different from the main shape as the main shape is scaled for the
     * current zoom.
     * 
     * @return The shape uneffected by zoom
     */
    protected static Shape getCurrentOverlayShape() {
        return currentOverlayShape;
    }

    /**
     * Sets the start point of the shape using appropriate methods for the currently
     * selected shape.
     * 
     * @param point The starting point.
     */
    protected static void setStartPoint(Point point) {
        double scale = ImageAction.target.getScale();
        double x = point.getX();
        double y = point.getY();
        switch (shapeType) {
            case 1:
                Line2D.Double line = (Line2D.Double) currentShape;
                Line2D.Double line_overlay = (Line2D.Double) currentOverlayShape;
                line.x1 = x / scale;
                line.y1 = y / scale;
                currentShape = line;
                line_overlay.x1 = x;
                line_overlay.y1 = y;
                currentOverlayShape = line_overlay;
                break;
            case 2:
                Path2D.Double path = (Path2D.Double) currentShape;
                Path2D.Double pathOverlay = (Path2D.Double) currentOverlayShape;
                path.moveTo(x / scale, y / scale);
                pathOverlay.moveTo(x, y);
                break;
            case 3:
                Rectangle2D.Double rect = (Rectangle2D.Double) currentShape;
                Rectangle2D.Double rectOverlay = (Rectangle2D.Double) currentOverlayShape;
                rect.setRect(x / scale, y / scale, 0, 0);
                rectOverlay.setRect(x, y, 0, 0);
                centerPoint = new Point((int) x, (int) y);
                break;
            case 4:
                Ellipse2D.Double ellipse = (Ellipse2D.Double) currentShape;
                ellipse.setFrame(point.getX(), point.getY(), 0, 0);
                centerPoint = new Point((int) point.getX(), (int) point.getY());
                break;
            case 5:
                RoundRectangle2D.Double rr = (RoundRectangle2D.Double) currentShape;
                rr.setFrame(point.getX(), point.getY(), 0, 0);
                centerPoint = new Point((int) point.getX(), (int) point.getY());
                break;
            default:
                break;
        }
    }

    /**
     * A method to set the end point of the current shape using appropriate methods
     * for the currently selected shape.
     * 
     * @param point The new end point.
     */
    protected static void setEndPoint(Point point) {
        double scale = ImageAction.target.getScale();
        double x = point.getX();
        double y = point.getY();
        switch (shapeType) {
            case 1:
                Line2D.Double line = (Line2D.Double) currentShape;
                Line2D.Double line_overlay = (Line2D.Double) currentOverlayShape;
                line.x2 = x / scale;
                line.y2 = y / scale;
                line_overlay.x2 = x;
                line_overlay.y2 = y;
                break;
            case 2:
                Path2D.Double path = (Path2D.Double) currentShape;
                Path2D.Double pathOveraly = (Path2D.Double) currentOverlayShape;
                path.lineTo(x / scale, y / scale);
                pathOveraly.lineTo(x, y);
                break;
            case 3:
                Rectangle2D.Double rectangleShape = (Rectangle2D.Double) currentShape;
                Rectangle2D.Double rectangleShapeOverlay = (Rectangle2D.Double) currentOverlayShape;
                Rectangle2D.Double rectangle = calculateRectangle(centerPoint, point);
                rectangleShape.setRect(rectangle.getX() / scale, rectangle.getY() / scale, rectangle.getWidth() / scale,
                        rectangle.getHeight() / scale);
                rectangleShapeOverlay.setRect(rectangle);
                break;
            case 4:
                Ellipse2D.Double ellipse = (Ellipse2D.Double) currentShape;
                Ellipse2D.Double ellipseOverlay = (Ellipse2D.Double) currentOverlayShape;
                rectangle = calculateRectangle(centerPoint, point);
                ellipse.setFrame(rectangle.getX() / scale, rectangle.getY() / scale, rectangle.getWidth() / scale,
                        rectangle.getHeight() / scale);
                ellipseOverlay.setFrame(rectangle.getX(), rectangle.getY(), rectangle.getWidth(),
                        rectangle.getHeight());
                break;
            case 5:
                RoundRectangle2D.Double rr = (RoundRectangle2D.Double) currentShape;
                RoundRectangle2D.Double rrOverlay = (RoundRectangle2D.Double) currentOverlayShape;
                rectangle = calculateRectangle(centerPoint, point);
                rr.setRoundRect(rectangle.getX() / scale, rectangle.getY() / scale, rectangle.getWidth() / scale,
                        rectangle.getHeight() / scale,
                        20.0,
                        20.0);
                rrOverlay.setRoundRect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight(),
                        20.0,
                        20.0);
                break;
            default:
                break;
        }
        ImageAction.getOverlay().repaint();
    }

    /**
     * A method to construct an appropriate rectange despite which corner you start
     * and finish in.
     * 
     * @param center   The first corner of the rectangle
     * @param newPoint The second corner of the rectangle
     * @return The rectangle class representation of the rectangle formed from these
     *         points
     */
    protected static Rectangle2D.Double calculateRectangle(Point center, Point newPoint) {
        double x = newPoint.getX();
        double y = newPoint.getY();
        double cx = centerPoint.getX();
        double cy = centerPoint.getY();
        double diffX = cx - x;
        double diffY = cy - y;
        double width = Math.abs(diffX);
        double height = Math.abs(diffY);
        if (diffY <= 0 & diffX <= 0) {
            // Bottom right
            // System.out.println("Botton right");
            return new Rectangle2D.Double(cx, cy, width, height);
        } else if (diffY > 0 && diffX <= 0) {
            // Top Right
            // System.out.println("Top right");
            return new Rectangle2D.Double(cx, cy - height, width, height);
        } else if (diffY <= 0 && diffX > 0) {
            // Bottom Left
            // System.out.println("Bottom Left");
            return new Rectangle2D.Double(cx - width, cy, width, height);
        } else {
            // Top Left
            // System.out.println("Top left");
            return new Rectangle2D.Double(cx - width, cy - height, width, height);
        }
    }
}
