package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Colour menu.
 * </p>
 * 
 * <p>
 * The Colour menu contains actions that affect the colour of each pixel
 * directly
 * without reference to the rest of the image.
 * This includes conversion to greyscale in the sample code, but more operations
 * will need to be added.
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
public class ColourActions implements AndieAction {

    /** A list of actions for the Colour menu. */
    protected ArrayList<Action> actions;
    /**
     * The JMenu for the MenuBar
     */
    private JMenu fileMenu;
    /**
     * The Two Color actions stored in an array 
     */
    private String[] items = { "Greyscale", "BrightnessAndContrast" };

    /**
     * <p>
     * Create a set of Colour menu actions.
     * </p>
     */
    public ColourActions() {
        actions = new ArrayList<Action>();
        actions.add(new ConvertToGreyAction("Greyscale", null, "Convert to greyscale", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new BrightnessAndContrastAction("Brightness and Contrast", null,
                "Change the brightness and contrast of the image", Integer.valueOf(KeyEvent.VK_C)));
    }

    /**
     * <p>
     * Create a menu contianing the list of Colour actions.
     * </p>
     * 
     * @return The colour menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Colour");
        fileMenu.setForeground(new Color(150, 150, 150));
        fileMenu.setEnabled(false);
        this.fileMenu = fileMenu;
        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        return fileMenu;
    }

    /**
     * <p>
     * Reconfigures the JMenuBar menu so that it is usable and not greyed out.
     * </p>
     */
    public void setUsable() {
        fileMenu.setForeground(Color.black);
        fileMenu.setEnabled(true);
    }

    /**
     * <p>
     * Reconfigures the JMenuBar menu so that it is unusable and greyed out.
     * </p>
     */
    public void setUnusable() {
        fileMenu.setForeground(Color.gray);
        fileMenu.setEnabled(false);
    }

    /**
     * A method to set the title of this menu.
     * 
     * @param text The new title of the menu.
     */
    public void setTitleText(String text) {
        fileMenu.setText(text);
    }

    /**
     * A method to set the title of a child in this menu.
     * 
     * @param item The index of the child to be changed.
     * @param text The new title of the child.
     */
    public void setItemText(int item, String text) {
        fileMenu.getItem(item).setText(text);
    }

    /**
     * A method to set the prompt of a child in this menu.
     * 
     * @param item The index of the child to be changed.
     * @param text The new prompt of the child.
     */
    public void setItemPrompt(int item, String text) {
        fileMenu.getItem(item).getAction().putValue("ShortDescription", text);
    }

    /**
     * A method to get the name of a child in this menu.
     * 
     * @param item The index of the child whos name we want.
     * @return A name representing this child.
     */
    public String getItem(int item) {
        return items[item];
    }

    /**
     * A method to get the number of children in this menu
     * 
     * @return The number of children currently in this menu
     */
    public int getItemCount() {
        return items.length;
    }

    /**
     * <p>
     * Action to convert an image to greyscale.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class ConvertToGreyAction extends ImageAction {

        /**
         * <p>
         * Create a new convert-to-grey action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        ConvertToGreyAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the convert-to-grey action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ConvertToGreyAction is triggered.
         * It changes the image to greyscale.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new ConvertToGrey(target.getWidth(),
                    target.getHeight()));
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * <p>
     * Action to change the brightness and contrast after displaying an options menu
     * with preview.
     * </p>
     * 
     * @see BrightnessAndContrast
     */
    public class BrightnessAndContrastAction extends ImageAction {

        /**
         * <p>
         * Create a new brightness and contrast action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        BrightnessAndContrastAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Briightness and Contrast action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever BrightnessAndContrastAction is triggered.
         * It displays an options menu with a preview then changes the brightness and
         * contrast of the image.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Determine the brightness and contrast changes - ask the user.
            int brightnessChange = 0;
            int contrastChange = 0;
            // This image is used as a static point for the preview so that changes are
            // applied to the current image not the last preview.
            BufferedImage currentImage = target.getImage().getCurrentImage();

            // Pop-up options menu giving options for brightness and contrast change as well
            // as a preview of what the current options will do to the image.
            // This is done with a panel named panel displaying left to right containing the
            // PreviewImagePanel and the panel sliderPanel.
            // sliderPanel is a panel displaying top to bottom containing sliders and labels
            // for adjusting brightness and contrast.
            JPanel panel = new JPanel();
            JPanel sliderPanel = new JPanel();
            sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

            JSlider brightnessSlider = new JSlider(-100, 100, 0);
            JSlider contrastSlider = new JSlider(-100, 100, 0);
            JLabel brightnessJLabel = new JLabel(Settings.getMessage("Brightness"));
            JLabel contrastJLabel = new JLabel(Settings.getMessage("Contrast"));
            PreviewImagePanel previewImagePanel = new PreviewImagePanel(currentImage);
            if (currentImage.getWidth() < currentImage.getHeight()) {
                previewImagePanel.setPreferredSize(new Dimension(300, 500));
            } else {
                previewImagePanel.setPreferredSize(new Dimension(500, 300));
            }

            // The change listener which updates the preview when the slider stops being
            // changed.
            Resize resize = new Resize(previewImagePanel.getImageWidth(), previewImagePanel.getImageHeight());
            final BufferedImage previewImage = resize.apply(currentImage);
            previewImagePanel.setImage(previewImage);
            ChangeListener sliderChangeListener = new ChangeListener() {
                BrightnessAndContrast bc = new BrightnessAndContrast(0, 0, currentImage.getWidth(),
                        currentImage.getHeight());

                public void stateChanged(ChangeEvent changeEvent) {
                    JSlider slider = (JSlider) changeEvent.getSource();
                    if (!slider.getValueIsAdjusting()) { // ie the slider has stopped moving.
                        bc.setBrightnessAndContrast(brightnessSlider.getValue(), contrastSlider.getValue());
                        previewImagePanel.setImage(bc.apply(previewImage));
                    }
                }
            };
            // Setting the sizes and showing percentages on sliders.

            Dimension sliderSize = new Dimension(400, 50);
            brightnessSlider.setPreferredSize(sliderSize);
            brightnessSlider.setMajorTickSpacing(20);
            brightnessSlider.setPaintTicks(true);
            brightnessSlider.setPaintLabels(true);
            brightnessSlider.addChangeListener(sliderChangeListener);

            contrastSlider.setPreferredSize(sliderSize);
            contrastSlider.setMajorTickSpacing(20);
            contrastSlider.setPaintTicks(true);
            contrastSlider.setPaintLabels(true);
            contrastSlider.addChangeListener(sliderChangeListener);

            sliderPanel.add(brightnessJLabel);
            sliderPanel.add(brightnessSlider);
            sliderPanel.add(contrastJLabel);
            sliderPanel.add(contrastSlider);

            panel.add(previewImagePanel);
            panel.add(sliderPanel);

            // Show the options panel that has the options contained in panel.
            String[] okAndCancel = { Settings.getMessage("OK"), Settings.getMessage("CANCEL") };
            int option = JOptionPane.showOptionDialog(null, panel, Settings.getMessage("BrightnessAndContrastTitle"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, okAndCancel, okAndCancel[0]);

            // Check the return value from the dialog box.
            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                brightnessChange = brightnessSlider.getValue();
                contrastChange = contrastSlider.getValue();
            }

            // Apply the change.
            target.getImage().apply(new BrightnessAndContrast(brightnessChange, contrastChange, currentImage.getWidth(),
                    currentImage.getHeight()));
            target.repaint();
            target.getParent().revalidate();
        }

    }

}