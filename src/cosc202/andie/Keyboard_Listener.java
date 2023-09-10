package cosc202.andie;

import java.awt.event.*;

/**
 * <p>
 * Provides a KeyListener for common operations.
 * </p>
 * 
 * @author Benjamin Nicholson
 * @version 1.0
 */
public class Keyboard_Listener extends KeyAdapter {
    /**
     * The image action to apply
     */
    ImageAction imageAction;
    /** The zoom level */
    private boolean zoom;

    /**
     * The method fired when a key is pressed to listen for shortcuts
     */
    @Override
    public void keyReleased(KeyEvent e) {
        FileActions fileActions = Andie.fileActions;
        ViewActions viewActions = new ViewActions();
        EditActions editActions = new EditActions();
        ScaleImageActions rotateActions = new ScaleImageActions();
        FilterActions filterActions = new FilterActions();
        Resizing resizeAction = new Resizing();
        SearchBar searchbar = new SearchBar();
        Settings settings = new Settings();
        SettingsAction settingActions = Andie.settingsAction;
        int action = e.getKeyCode();
        // If only CTRL is down.
        boolean imagePresent = !(ImageAction.getTarget().getImage().getCurrentImage() == null);
        // These keyevents are allow at all times.
        if (e.isControlDown() && e.isShiftDown() == false) {
            if (action == KeyEvent.VK_O) {
                imageAction = fileActions.new FileOpenAction(null, null, null, null);
            }
            if (action == KeyEvent.VK_Q) {
                Andie.searchBar = searchbar;
                searchbar.setVisible(true);
                return;
            }
        }
        // allowed only if an image is open.
        if (e.isControlDown() && e.isShiftDown() == false && imagePresent) {
            switch (action) {
                // Save
                case KeyEvent.VK_S:
                    imageAction = fileActions.new FileSaveAction("Save Image", null, "Save a file", null);
                    break;
                // Export
                case KeyEvent.VK_X:
                    imageAction = fileActions.new FileExportAction("Export Image", null, null, null);
                    break;
                // Zoom in
                case KeyEvent.VK_MINUS:
                    imageAction = viewActions.new ZoomOutAction(null, null, null, null);
                    zoom = true;
                    break;
                // Zoom in
                case KeyEvent.VK_EQUALS:
                    imageAction = viewActions.new ZoomInAction(null, null, null, null);
                    zoom = true;
                    break;
                // Undo
                case KeyEvent.VK_LEFT:
                    imageAction = editActions.new UndoAction(null, null, null, null);
                    break;
                // Redo
                case KeyEvent.VK_RIGHT:
                    imageAction = editActions.new RedoAction(null, null, null, null);
                    break;
                // Rotate Right 90
                case KeyEvent.VK_R:
                    imageAction = rotateActions.new RotateImage_Right(null, null, null, null);
                    break;
                case KeyEvent.VK_C:
                    imageAction = resizeAction.new CropAction(null, null, null, null);
                    break;

                case KeyEvent.VK_T:
                    // imageAction = settingActions.new ConfigureToolBar(null, null, null, nul
                    break;
                case KeyEvent.VK_L:
                    settings.actionPerformed(null);
                    return;
                case KeyEvent.VK_F:
                    imageAction = rotateActions.new FlipX(null, null, null, null);
                    break;
                case KeyEvent.VK_0:
                    imageAction = filterActions.new SoftBlurAction(null, null, null, null);
                    break;
                case KeyEvent.VK_1:
                    imageAction = filterActions.new EmbossFiltersAction(null, null, null, null);
                    break;
                case KeyEvent.VK_2:
                    imageAction = filterActions.new GaussBlurAction(null, null, null, null);
                    break;
                case KeyEvent.VK_3:
                    imageAction = filterActions.new MeanFilterAction(null, null, null, null);
                    break;
                case KeyEvent.VK_4:
                    imageAction = filterActions.new MedianBlurAction(null, null, null, null);
                    break;
                case KeyEvent.VK_5:
                    imageAction = filterActions.new SaturationFilterAction(null, null, null, null);
                    break;
                case KeyEvent.VK_6:
                    imageAction = filterActions.new SepiaFilterAction(null, null, null, null);
                    break;
                case KeyEvent.VK_7:
                    imageAction = filterActions.new SharpenFilterAction(null, null, null, null);
                    break;
                case KeyEvent.VK_8:
                    imageAction = filterActions.new SobelFilter1Action(null, null, null, null);
                    break;
                case KeyEvent.VK_9:
                    imageAction = filterActions.new SobelFilter2Action(null, null, null, null);
                    break;
            }
        }
        // If CTRL + Shift is down.
        if (e.isControlDown() && e.isShiftDown()) {
            switch (action) {
                // Rotate Left 90
                case KeyEvent.VK_R:
                    if (imagePresent) {
                        imageAction = rotateActions.new RotateImage_Left(null, null, null, null);
                    }
                    break;
                // Save As
                case KeyEvent.VK_S:
                    if (imagePresent) {
                        imageAction = fileActions.new FileSaveAsAction(null, null, null, null);
                    }
                    break;
                case KeyEvent.VK_F:
                    if (imagePresent) {
                        imageAction = rotateActions.new FlipY(null, null, null, null);
                    }

            }
        }
        if (e.isAltDown() && e.isControlDown()) {
            if (imagePresent) {
                imageAction = resizeAction.new ResizeAction(null, null, null, null);
            }
        }
        // Applying the specific ImageAction to the image.
        if (imageAction != null) {
            imageAction.actionPerformed(null);
            imageAction = null;
            if (zoom) {
                ToolBar.setZoomBox();
                zoom = false;
            }
        }
    }

}
