package cosc202.andie;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * <p>
 * Actions provided by the Filter menu.
 * </p>
 * 
 * <p>
 * The Filter menu contains actions that update each pixel in an image based on
 * some small local neighbourhood.
 * This includes a mean filter (a simple blur) in the sample code, but more
 * operations will need to be added.
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
public class FilterActions implements AndieAction {
    /**
     * A list of actions for the Filter menu.
     */
    protected ArrayList<Action> actions;
    /** The FilterMenu for the MenuBar */
    private JMenu fileMenu;
    /** All the Actions in Filter actions */
    private String[] items = { "MeanFilter", "SoftBlur", "GaussBlur", "SharpenFilter", "MedianBlur",
            "HorizontalSobelFilter", "VerticalSobelFilter", "EmbossFilters", "SepiaFilter", "SaturationFilter" };

    /**
     * <p>
     * Create a set of Filter menu actions.
     * </p>
     */
    public FilterActions() {
        actions = new ArrayList<Action>();
        actions.add(new MeanFilterAction("Mean filter", null, "Apply a mean filter", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new SoftBlurAction("Soft blur", null, "Apply a soft blur", Integer.valueOf(KeyEvent.VK_B)));
        actions.add(new GaussBlurAction("Gauss blur", null, "Apply a Gauss blur", Integer.valueOf(KeyEvent.VK_G)));
        actions.add(new SharpenFilterAction("Sharpen filter", null, "Apply a Sharpen filter",
                Integer.valueOf(KeyEvent.VK_S)));
        // New by Alex
        actions.add(new MedianBlurAction("Median blur", null, "Apply a Median blur", Integer.valueOf(KeyEvent.VK_N)));
        // N for now
        actions.add(new SobelFilter1Action("Horizontal Sobel Filter", null, "Apply a Horizontal Sobel filter",
                Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new SobelFilter2Action("Vertical Sobel Filter", null, "Apply a Vertical Sobel filter",
                Integer.valueOf(KeyEvent.VK_V)));
        actions.add(new EmbossFiltersAction("Emboss Filters", null, "Apply a Emboss filter",
                Integer.valueOf(KeyEvent.VK_E)));
        actions.add(new SepiaFilterAction("Sepia Filter", null, "Apply a Sepia filter",
                Integer.valueOf(KeyEvent.VK_Y)));
        actions.add(new SaturationFilterAction("Saturation Filter", null, "Apply a Saturation filter",
                Integer.valueOf(KeyEvent.VK_T)));

    }

    /**
     * <p>
     * Create a menu contianing the list of Filter actions.
     * </p>
     * 
     * @return The filter menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Filter");
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
     * reconfigures the JMenuBar menu so that it is usuable and not greyed out
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
     * @param text The new title of the menu
     */
    public void setTitleText(String text) {
        fileMenu.setText(text);
    }

    /**
     * A method to set the title of a child in this menu.
     * 
     * @param item The index of the child to be changed
     * @param text The new title of the child
     */
    public void setItemText(int item, String text) {
        fileMenu.getItem(item).setText(text);
    }

    /**
     * A method to set the prompt of a child in this menu.
     * 
     * @param item The index of the child to be changed
     * @param text The new prompt of the child
     */
    public void setItemPrompt(int item, String text) {
        fileMenu.getItem(item).getAction().putValue("ShortDescription", text);
    }

    /**
     * A method to get the name of a child in this menu.
     * 
     * @param item The index of the child whos name we want.
     * @return A name representing this child
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
     * getInput opens a JPanel to get the users input with a preview
     * showcasing what each option will do.
     * </p>
     *
     * @param filterType Integer value corresponds to a ID for a filterType.
     * @return Returns the radius of the inputted value for application to the main
     *         image.
     */
    public int getInput(int filterType) {
        int radius = -1;

        BufferedImage currentImage = ImageAction.getTarget().getImage().getCurrentImage();
        JPanel panel = new JPanel();
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        JSlider inputSlider = new JSlider(JSlider.VERTICAL, 1, 10, 1);
        JSlider tempInputSlider = new JSlider(JSlider.VERTICAL, -10, 10, 0);
        PreviewImagePanel previewImagePanel = new PreviewImagePanel(currentImage);
        // check whether the orientation of image is vertical or horizontal.
        if (currentImage.getWidth() < currentImage.getHeight()) {
            previewImagePanel.setPreferredSize(new Dimension(300, 500));
        } else {
            previewImagePanel.setPreferredSize(new Dimension(500, 300));
        }

        Resize resize = new Resize(previewImagePanel.getImageWidth(), previewImagePanel.getImageHeight());
        final BufferedImage previewImage = resize.apply(currentImage);
        ChangeListener sliderChangeListener;
        previewImagePanel.setImage(previewImage);

        if (filterType == 0) {
            sliderChangeListener = new ChangeListener() {
                MeanFilter mf = new MeanFilter(0, currentImage.getWidth(), currentImage.getHeight());

                public void stateChanged(ChangeEvent changeEvent) {
                    JSlider slider = (JSlider) changeEvent.getSource();
                    if (!slider.getValueIsAdjusting()) { // ie the slider has stopped moving
                        mf.setRadius(inputSlider.getValue());
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        previewImagePanel.setImage(mf.apply(previewImage));
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            };
        } else if (filterType == 1) {
            sliderChangeListener = new ChangeListener() {
                GaussBlur gb = new GaussBlur(0, currentImage.getWidth(), currentImage.getHeight());

                public void stateChanged(ChangeEvent changeEvent) {
                    JSlider slider = (JSlider) changeEvent.getSource();
                    if (!slider.getValueIsAdjusting()) { // ie the slider has stopped moving
                        gb.setRadius(inputSlider.getValue());
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        previewImagePanel.setImage(gb.apply(previewImage));
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            };
        } else if (filterType == 2) {
            inputSlider.setVisible(false);
            sliderChangeListener = new ChangeListener() {
                SaturationFilter sf = new SaturationFilter(currentImage.getWidth(), currentImage.getHeight());

                public void stateChanged(ChangeEvent changeEvent) {
                    JSlider slider = (JSlider) changeEvent.getSource();
                    if (!slider.getValueIsAdjusting()) { // ie the slider has stopped moving
                        sf.setSaturation(tempInputSlider.getValue() / 10.f);
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        previewImagePanel.setImage(sf.apply(previewImage));
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            };
        } else {
            sliderChangeListener = new ChangeListener() {
                MedianBlur mb = new MedianBlur(0, currentImage.getWidth(), currentImage.getHeight());

                public void stateChanged(ChangeEvent changeEvent) {
                    JSlider slider = (JSlider) changeEvent.getSource();
                    if (!slider.getValueIsAdjusting()) {
                        mb.setRadius(inputSlider.getValue());
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        previewImagePanel.setImage(mb.apply(previewImage));
                        previewImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            };
        }
        Dimension sliderSize = new Dimension(50, 400);
        inputSlider.setPreferredSize(sliderSize);
        inputSlider.setMajorTickSpacing(1);
        inputSlider.setPaintTicks(true);
        inputSlider.setPaintLabels(true);
        inputSlider.addChangeListener(sliderChangeListener);
        sliderPanel.add(inputSlider);
        panel.add(previewImagePanel);
        panel.add(sliderPanel);

        Dimension tempSliderSize = new Dimension(50, 400);
        tempInputSlider.setPreferredSize(tempSliderSize);
        tempInputSlider.setMajorTickSpacing(1);
        tempInputSlider.setPaintTicks(true);
        tempInputSlider.setPaintLabels(true);
        tempInputSlider.addChangeListener(sliderChangeListener);
        sliderPanel.add(tempInputSlider);
        panel.add(previewImagePanel);
        panel.add(sliderPanel);

        if (filterType != 2) {
            tempInputSlider.setVisible(false);
        }

        String[] title_prompts = new String[] { "MeanFilter", "GaussBlur", "SaturationFilter", "MedianBlur" };
        String[] okAndCancel = { Settings.getMessage("OK"), Settings.getMessage("CANCEL") };
        int option = JOptionPane.showOptionDialog(null, panel, Settings.getMessage(title_prompts[filterType]),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, okAndCancel, null);
        // Check the return value from the dialog box.
        if (option == JOptionPane.CANCEL_OPTION) {
            return radius;
        } else if (option == JOptionPane.OK_OPTION && filterType != 2) {
            radius = inputSlider.getValue();
        } else if (option == JOptionPane.OK_OPTION && filterType == 2) {
            radius = tempInputSlider.getValue();
        }

        return radius;
    }

    /**
     * <p>
     * Action to blur an image with a mean filter.
     * </p>
     * 
     * @see MeanFilter
     */
    public class MeanFilterAction extends ImageAction {

        /**
         * <p>
         * Create a new mean-filter action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        MeanFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the Mean filter action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the MeanFilterAction is triggered.
         * It prompts the user for a filter radius, then applys an appropriately sized
         * {@link MeanFilter}.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            int radius = getInput(0);
            if (radius == -1) {
                return;
            }
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the Mean filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new MeanFilter(radius, target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    target.repaint();
                    overlay.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }
    }

    /**
     * <p>
     * Action to blur an image with a Soft filter.
     * </p>
     * 
     * @see SoftBlur
     */
    public class SoftBlurAction extends ImageAction {

        /**
         * Create a new SoftBlur action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SoftBlurAction(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * An action to apply a soft blur to an image.
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the SoftBlur filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new SoftBlur(target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    target.repaint();
                    overlay.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }
    }

    /**
     * <p>
     * Action to apply a Gauss blur to an image.
     * </p>
     * 
     * @see GaussBlur
     */
    public class GaussBlurAction extends ImageAction {

        /**
         * Create a new Gauss blur action.
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        GaussBlurAction(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action to apply a Gauss blur to an image.
         * 
         * @param e The event triggering this action.
         */
        public void actionPerformed(ActionEvent e) {
            // Determine the radius - ask the user.
            int radius = getInput(1);
            if (radius == -1) {
                return;
            }
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the MedianBlur filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new GaussBlur(radius, target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    target.repaint();
                    overlay.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }
    }

    /**
     * Filter Action to sharpen an image using SharpenFilter
     * 
     * @see SharpenFilter
     */
    public class SharpenFilterAction extends ImageAction {

        /**
         * Create a new SharpenFilterAction
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        SharpenFilterAction(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action to apply the Sharpen filter to the image.
         * 
         * @param e The event triggering this action.
         */
        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the Sharpen filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new SharpenFilter(target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    target.repaint();
                    overlay.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }
    }

    /**
     * <p>
     * Action to blur an image with a bedian blur filter.
     * </p>
     * 
     * @see MedianBlur
     */
    public class MedianBlurAction extends ImageAction {

        /**
         * Constructor for the MedianBlurAction.
         * 
         * @param name     The name of the action.
         * @param icon     The icon to display for the action.
         * @param desc     The description of the action.
         * @param mnemonic The mnemonic to use for the action.
         */
        MedianBlurAction(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action to apply the MedianBlur filter to the image.
         */
        public void actionPerformed(ActionEvent e) {

            // Determine the radius - ask the user.
            int radius = getInput(3);
            if (radius == -1) {
                return;
            }
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the MedianBlur filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new MedianBlur(radius, target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    overlay.repaint();
                    target.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }

    }

    /**
     * The Sobel filter action class to apply a Sobe filter to an image.
     */
    public class SobelFilter1Action extends ImageAction {

        /**
         * Constructor for SobelFilter1Action
         * 
         * @param name     The name of the action
         * @param icon     The icon for the action
         * @param desc     The description of the action
         * @param mnemonic The mnemonic for the action
         */
        public SobelFilter1Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action to apply a Sobel filter to an image.
         * 
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the MedianBlur filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new SobelFilters(true, target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    overlay.repaint();
                    target.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }

    }

    /**
     * The Sepia filter action class to apply a Sepia filter to an image.
     */
    public class SepiaFilterAction extends ImageAction {

        /**
         * The constructor for the sepia filter action.
         * 
         * @param name     The name of the action.
         * @param icon     The icon for the action.
         * @param desc     The description of the action.
         * @param mnemonic The mnemonic for the action.
         */
        SepiaFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action performed method to apply the sepia filter to an image.
         * 
         * @param e The action event.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new SepiaFilter(target.getWidth(),
                    target.getHeight()));
            // Set the cursor back to the default cursor
            overlay.repaint();
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * The saturation filter action class to change the saturation of an image.
     */
    public class SaturationFilterAction extends ImageAction {

        /**
         * The saturation value.
         */
        private float saturation;

        /**
         * The constructor for the SaturationFilterAction class.
         * 
         * @param name     The name of the action.
         * @param icon     The icon to display.
         * @param desc     The description of the action.
         * @param mnemonic The mnemonic key.
         */
        SaturationFilterAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action to perform when the user selects saturation filter.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            saturation = getInput(2) / 10.0f;
            if (saturation == -1) {
                return;
            }
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the MedianBlur filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    SaturationFilter sf = new SaturationFilter(target.getWidth(), target.getHeight());
                    sf.setSaturation(saturation);
                    target.getImage().apply(sf);
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    overlay.repaint();
                    target.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }

    }

    /**
     * The Sobel filter action class which applies Sobel filters to the iamge.
     */
    public class SobelFilter2Action extends ImageAction {

        /**
         * Constructor for the SobelFilter2Action class.
         * 
         * @param name     the name of the action
         * @param icon     the icon to display
         * @param desc     the description of the action
         * @param mnemonic the mnemonic associated with this action
         */
        public SobelFilter2Action(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Apply the Sobel filter to the image.
         * 
         * @param e the action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            // Set the cursor to "wait" cursor
            target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            // Create a new thread to apply the MedianBlur filter
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    target.getImage().apply(new SobelFilters(false, target.getWidth(), target.getHeight()));
                    // Set the cursor back to the default cursor
                    target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    overlay.repaint();
                    target.repaint();
                    target.getParent().revalidate();
                }
            });

            // Start the thread
            thread.start();
        }

    }

    /**
     * The Emboss filter actions class to apply Emboss filters to an image.
     */
    public class EmbossFiltersAction extends ImageAction {

        /**
         * Constructor for the EmbossFiltersAction class.
         * 
         * @param name     The name of the action.
         * @param icon     The ImageIcon to display.
         * @param desc     The description of the action.
         * @param mnemonic The mnemonic key.
         */
        public EmbossFiltersAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * The action to perform when emboss filter is triggered.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // Create and apply the filter
            // Set showActions cursor to "wait" cursor
            int filterType = showOptions();

            if (filterType != 0) {
                target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                // Create a new thread to apply the MedianBlur filter
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        target.getImage().apply(new EmbossFilters(filterType, target.getWidth(), target.getHeight()));
                        // Set the cursor back to the default cursor
                        target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        target.repaint();
                        target.getParent().revalidate();
                    }
                });

                // Start the thread
                thread.start();
            }
        }
    }

    /**
     * Shows the options for the Emboss filter
     * 
     * @return the selected option or 0 if no option is selected.
     */
    public int showOptions() {
        BufferedImage currImage = ImageAction.getTarget().getImage().getCurrentImage();

        // Creates the panel with your custom content
        PreviewImagePanel pImagePanel = new PreviewImagePanel(currImage);
        JPanel customPanel = new JPanel(new BorderLayout());
        customPanel.add(pImagePanel, BorderLayout.LINE_START);

        if (currImage.getWidth() < currImage.getHeight()) {
            pImagePanel.setPreferredSize(new Dimension(300, 500));
        } else {
            pImagePanel.setPreferredSize(new Dimension(500, 300));
        }

        // Creates the radio buttons and adds listeners to them
        JRadioButton[] radioButtons = { new JRadioButton("1"), new JRadioButton("2"),
                new JRadioButton("3"), new JRadioButton("4"),
                new JRadioButton("5"), new JRadioButton("6"),
                new JRadioButton("7"), new JRadioButton("8") };
        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1));

        Resize resize = new Resize(pImagePanel.getImageWidth(), pImagePanel.getImageHeight());
        BufferedImage previewImage = resize.apply(currImage);
        pImagePanel.setImage(previewImage);

        for (int i = 0; i < radioButtons.length; i++) {
            int index = i + 1;
            radioButtons[i].addActionListener(e -> {
                // Update the PreviewImagePanel based on the selected radio button
                EmbossFilters eb = new EmbossFilters(index, currImage.getWidth(), currImage.getHeight());
                pImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                pImagePanel.setImage(eb.apply(previewImage));
                pImagePanel.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            });
            buttonGroup.add(radioButtons[i]);
            buttonPanel.add(radioButtons[i]);
        }
        customPanel.add(buttonPanel, BorderLayout.CENTER);

        // Shows the option dialog
        int choice = JOptionPane.showConfirmDialog(null, customPanel, "Embossing Options",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (choice == JOptionPane.OK_OPTION) {
            // Returns the index of the selected option
            for (int i = 0; i < radioButtons.length; i++) {
                if (radioButtons[i].isSelected()) {
                    return i + 1;
                }
            }
        }

        // Returns 0 if no option is selected
        return 0;
    }
}
