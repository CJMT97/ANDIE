package cosc202.andie;

import javax.swing.*;

/**
 * <p>
 * Abstract class representing actions the user might take in the interface.
 * </p>
 * 
 * <p>
 * This class uses Java's AbstractAction approach for Actions that can be
 * applied to images.
 * The key difference from a generic AbstractAction is that an ImageAction
 * contains a reference
 * to an image (via an ImagePanel interface element).
 * </p>
 * 
 * <p>
 * A distinction should be made between an ImageAction and an
 * {@link ImageOperation}.
 * An ImageOperation is applied to an image in order to change it in some way.
 * An ImageAction provides support for the user doing something to an image in
 * the user interface.
 * ImageActions may apply an ImageOperation, but do not have to do so.
 * </p>
 * 
 * <p>
 * <a href="https://creativecommons.org/licenses/by-nc-sa/4.0/">CC BY-NC-SA
 * 4.0</a>
 * </p>
 * 
 * @author Steven Mills
 * @version 1.0
 */
public abstract class ImageAction extends AbstractAction {

    /**
     * The user interface element containing the image upon which actions should be
     * performed.
     * This is common to all ImageActions.
     */
    protected static ImagePanel target;
    /**
     * The overlay panel where previews of drawings are displayed to the user as
     * they draw.
     */
    protected static OverlayPanel overlay;
    /**
     * The main panel containing the image and the overlay.
     */
    protected static JPanel main;

    /**
     * <p>
     * Constructor for ImageActions.
     * </p>
     * 
     * <p>
     * To construct an ImageAction you provide the information needed to integrate
     * it with the interface.
     * Note that the target is not specified per-action, but via the static member
     * {@link target}
     * via {@link setTarget}.
     * </p>
     * 
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    ImageAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
        super(name, icon);
        if (desc != null) {
            putValue(SHORT_DESCRIPTION, desc);
        }
        if (mnemonic != null) {
            putValue(MNEMONIC_KEY, mnemonic);
        }
    }

    /**
     * <p>
     * Set the target for ImageActions.
     * </p>
     * 
     * @param newTarget The ImagePanel to apply ImageActions to.
     */
    public static void setTarget(ImagePanel newTarget) {
        target = newTarget;
    }

    /**
     * A method to set the current overlay panel.
     * 
     * @param newTarget The new overlay panel.
     */
    public static void setOverlay(OverlayPanel newTarget) {
        overlay = newTarget;
    }

    /**
     * A method to set the current main panel.
     * 
     * @param newTarget The new main panel.
     */
    public static void setMain(JPanel newTarget) {
        main = newTarget;
    }

    /**
     * <p>
     * Get the target for ImageActions.
     * </p>
     * 
     * @return The ImagePanel to which ImageActions are currently being applied.
     */
    public static ImagePanel getTarget() {
        return target;
    }

    /**
     * A method to get the current overlay panel.
     * 
     * @return The current overlay panel.
     */
    public static OverlayPanel getOverlay() {
        return overlay;
    }

    /**
     * A method to get the current main panel.
     * 
     * @return The current main panel.
     */
    public static JPanel getMain() {
        return main;
    }

}
