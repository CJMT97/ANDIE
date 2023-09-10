package cosc202.andie;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;;

/**
 * <p>
 * A class to listen to mouse presses and movements in the Andie project.
 * </p>
 * 
 * <p>
 * This allows us to listen to mouse movements and draw shapes as well as
 * allowing cropping of images.
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class DrawMouseListener implements MouseListener, MouseMotionListener {
    // OverlayPanel overlay = ImageAction.getOverlay();

    /**
     * Triggered when the mouse is clicked.
     */
    @Override
    public void mouseClicked(java.awt.event.MouseEvent e) {
        // System.out.println("Mouse Clicked");
    }

    /**
     * Triggered when the mouse is pressed to start the shape drawing process.
     */
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        // System.out.println("Mouse Pressed");
        // Picks a random color and stroke size for fun. Then applys the draw.
        // Random rand = new Random();
        // Settings.setPenColor(new Color(rand.nextInt(256), rand.nextInt(256),
        // rand.nextInt(256), 255));
        // Settings.setStrokeSize(rand.nextInt(10) + 1);

        // Settings.makeDrawingTrue(1);
        // ShapeHandler.setShapeType(1);
        if(Settings.isCropping()){
            Settings.setAxisCursor();
        }
        

        ShapeHandler.setStartPoint(e.getPoint());
        ShapeHandler.setEndPoint(e.getPoint());
        // We have started drawing
        ImageAction.getOverlay().setMouseDown(true);
    }

    /**
     * Triggered when the mouse is released. Applying the action to the image.
     */
    @Override
    public void mouseReleased(java.awt.event.MouseEvent e) {
        // System.out.println("Mouse Released");

        ShapeHandler.setEndPoint(e.getPoint());
        // We have finished drawing this shape
        ImageAction.getOverlay().setMouseDown(false);
        if (Settings.getDrawing()) {
            // ImageAction.getOverlay().setLineEnd(e.getPoint());
            DrawAction drawAction = new DrawAction(null, null, null, null);
            // drawAction.accept(e);
            // Call the action to draw the line on buffered image.
            // ImageAction.getTarget().getImage().apply(drawAction.new DrawLine());
            drawAction.actionPerformed(null);

        } else if (Settings.isCropping()) {
            
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Settings.setCropping(false);
                        ImageAction.target.getImage()
                                .apply(new Crop(ShapeHandler.getCurrentShape(),
                                        ImageAction.target.getImage().getCurrentImage().getWidth(),
                                        ImageAction.target.getImage().getCurrentImage().getHeight()));
                        ImageAction.target.setMaximumSize(
                                new Dimension(ImageAction.target.getImage().getCurrentImage().getWidth(),
                                        ImageAction.target.getImage().getCurrentImage().getHeight()));
                        ImageAction.overlay.setMaximumSize(
                                new Dimension(ImageAction.target.getImage().getCurrentImage().getWidth(),
                                        ImageAction.target.getImage().getCurrentImage().getHeight()));
                        ImageAction.target.repaint();
                        ImageAction.target.getParent().revalidate();
                        Settings.getTB().setTBUsable();
                        Settings.getTB().setMacroUsable();
                        Settings.setDefaultCursor();
                    } catch (Exception ex) {
                        Settings.getTB().setTBUsable();
                        Settings.getTB().setMacroUsable();
                        Settings.setDefaultCursor();
                    }
                }
            });

            // Start the thread
            thread.start();
        }
        ImageAction.getOverlay().repaint();
    }

    @Override
    public void mouseEntered(java.awt.event.MouseEvent e) {
        // System.out.println("Mouse Entered");
    }

    @Override
    public void mouseExited(java.awt.event.MouseEvent e) {
        // System.out.println("Mouse exited");
    }

    /**
     * Triggered when the mouse is dragged setting the end point of the shape in the
     * Image Handler.
     */
    @Override
    public void mouseDragged(java.awt.event.MouseEvent e) {
        // System.out.println(e.getX() + " -> " + e.getY());
        // ImageAction.getOverlay().setLineEnd(e.getPoint());
        double xCoord = e.getPoint().getX();
        double yCoord = e.getPoint().getY();
        Point p = new Point((int) xCoord, (int) yCoord);
        ShapeHandler.setEndPoint(p);
    }

    @Override
    public void mouseMoved(java.awt.event.MouseEvent e) {
    }

}
