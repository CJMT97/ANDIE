package cosc202.andie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * 
 * <p>
 * Action provided by the Resizing menu
 * </p>
 * 
 * <p>
 * Resizing class has create menu method which adds the resizeAction class
 * which takes an image and alters the size of the image to make it
 * larger or smaller based on user input
 * </p>
 * 
 * @author Charlie Templeton
 */

public class Resizing implements AndieAction {
    private JMenu jm;
    private String[] items = { "Resize", "Crop" };

    /**
     * createMenu method generates a JMenu and adds ResizeAction
     * which is then added to the JMenuBar
     * 
     * @return The Resize menu element
     */
    public JMenu createMenu() {
        JMenu jm = new JMenu("Resize");
        jm.setForeground(new Color(150, 150, 150));
        jm.setEnabled(false);
        this.jm = jm;
        jm.add(new ResizeAction("Resize Image", null, "Resizes image", Integer.valueOf(KeyEvent.VK_R)));
        jm.add(new CropAction("Crop Action", null, "Crops an image", null));
        return jm;
    }

    /**
     * <p>
     * reconfigures the JMenuBar menu so that it is usuable and not greyed out
     * </p>
     */
    public void setUsable() {
        jm.setForeground(Color.black);
        jm.setEnabled(true);
    }

    /**
     * <p>
     * Reconfigures the JMenuBar menu so that it is unusable and greyed out.
     * </p>
     */
    public void setUnusable() {
        jm.setForeground(Color.gray);
        jm.setEnabled(false);
    }

    /**
     * A method to set the title of this menu.
     * 
     * @param text The new title of the menu
     */
    public void setTitleText(String text) {
        jm.setText(text);
    }

    /**
     * A method to set the title of a child in this menu.
     * 
     * @param item The index of the child to be changed
     * @param text The new title of the child
     */
    public void setItemText(int item, String text) {
        jm.getItem(item).setText(text);
    }

    /**
     * A method to set the prompt of a child in this menu.
     * 
     * @param item The index of the child to be changed
     * @param text The new prompt of the child
     */
    public void setItemPrompt(int item, String text) {
        jm.getItem(item).getAction().putValue("ShortDescription", text);
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
     * Action to resize image
     * </p>
     * 
     * @see Resize
     */
    public class ResizeAction extends ImageAction {
        // Datafield hold reference to the JComboBox

        /**
         * The combo box of different resize options
         */
        private JComboBox<String> resize;
        /**
         * The buttons to confirm or cancel the currently previewed resize.
         */
        private JButton ok, cancel;
        /**
         * The frame of options
         */
        private JFrame temp;
        /**
         * The label where error messages are displayed
         */
        private JLabel error;
        /**
         * The error counter
         */
        private int count = 1;

        /**
         * <p>
         * Constructor creates new ResizeAction
         * and calls the getJComboBox method
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        public ResizeAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
            getJComboBox();
        }

        /**
         * <p>
         * Creates a JComboBox to be used in the resize UI
         * </p>
         */
        public void getJComboBox() {

            // Creates a JComboBox object and sets it in the data feild
            String[] percentages = { "25%", "50%", "75%", "100%", "125%", "150%", "175%", "200%" };
            JComboBox<String> resize = new JComboBox<String>(percentages);
            resize.setEditable(true);
            resize.setSelectedIndex(3);
            resize.setMinimumSize(new Dimension(40, 10));
            resize.setPreferredSize(new Dimension(60, 20));
            resize.setMaximumSize(resize.getPreferredSize());
            this.resize = resize;
        }

        /**
         * <p>
         * Checks to see if selected item in JComboBox is usable to covert to a number
         * </p>
         * 
         * @param s The selected item in the JComboBox
         * @return True or false whether its usable or not
         */
        public boolean isUsable(String s) {
            if (s.equals("0") || s.equals("0%") || s.equals("%") || s.equals("") || s.charAt(0) == '0') {
                return false;
            }
            String numbers = "1234567890%";
            for (int i = 0; i < s.length(); i++) {
                if (!numbers.contains(s.charAt(i) + "")) {
                    return false;
                }
                if (s.charAt(i) == '%' && !(i == s.length() - 1)) {
                    return false;
                }
            }
            return true;
        }

        /**
         * <p>
         * Converts the string obtained from the selected item in the JComboBox into an
         * int which it then returns to be use as the percentage increase for resizing
         * </p>
         * 
         * @return int that represents the percentage change for resizing
         */
        public int getPercentChange() {
            String currItem = resize.getSelectedItem().toString();
            String lastLetter = String.valueOf(currItem.charAt(currItem.length() - 1));
            if (lastLetter.equals("%")) {
                String number = currItem.substring(0, currItem.length() - 1);
                int percentChange = Integer.parseInt(number);
                return percentChange;
            } else {
                currItem = currItem + "%";
                String number = currItem.substring(0, currItem.length() - 1);
                int percentChange = Integer.parseInt(number);
                return percentChange;
            }
        }

        /**
         * <p>
         * method for setting the selected item of the JComboBox
         * used only for JUnit test
         * </p>
         * 
         * @param value The tester value to set the selected item to
         */
        public void setResizeJCB(String value) {
            resize.setSelectedItem(value);
        }

        /**
         * <p>
         * Applys the resize to the image by using the resize class
         * </p>
         * 
         * @param percentChange The percentage change in the size of the image
         */
        public void doResize(int percentChange) {
            Resize resize = new Resize(percentChange, error, temp);

            target.getImage().apply(resize);

            target.setMaximumSize(new Dimension(target.getImage().getCurrentImage().getWidth(),
                    target.getImage().getCurrentImage().getHeight()));
            overlay.setMaximumSize(new Dimension(target.getImage().getCurrentImage().getWidth(),
                    target.getImage().getCurrentImage().getHeight()));
            target.repaint();
            target.getParent().revalidate();
        }

        /**
         * <p>
         * Callback for when the ResizeAction action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the ResizeAction is triggered.
         * It creates a JFrame and prompts the user for a percentage to resize by.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel title, message;
            JPanel jp = new JPanel();
            JComboBox<String> jcb = resize;
            JButton ok, cancel;
            JLabel error;

            resize.setSelectedIndex(3);

            jp.setLayout(null);
            jp.setPreferredSize(new Dimension(400, 250));

            title = new JLabel();
            title.setFont(new Font("Serif", Font.BOLD, 40));
            title.setText(
                    "<html><div text-align: center><u>" + Settings.getMessage("ResizeTitle") + "</u></div></html>");
            title.setBounds(0, 0, 400, 95);
            title.setHorizontalAlignment(SwingConstants.CENTER);

            message = new JLabel(Settings.getMessage("ResizeTitle"));
            message.setBounds(60, 100, 350, 25);
            message.setFont(new Font("Serif", Font.PLAIN, 15));

            jcb.setBounds(60, 130, 280, 50);

            Insets insets = new Insets(10, 0, 10, 0);

            ok = new JButton(Settings.getMessage("OK"));
            ok.setBounds(90, 210, 100, 30);
            ok.setMargin(insets);

            cancel = new JButton(Settings.getMessage("CANCEL"));
            cancel.setBounds(210, 210, 100, 30);
            cancel.setMargin(insets);

            error = new JLabel();
            error.setBounds(0, 170, 400, 30);

            this.error = error;

            ButtonListener bl = new ButtonListener();
            ok.addActionListener(bl);
            cancel.addActionListener(bl);

            this.ok = ok;
            this.cancel = cancel;

            jp.add(title);
            jp.add(message);
            jp.add(jcb);
            jp.add(ok);
            jp.add(cancel);
            jp.add(error);

            JFrame temp = new JFrame(Settings.getMessage("ResizeTitle"));
            temp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            temp.add(jp);
            temp.pack();
            temp.setResizable(false);
            temp.setLocationRelativeTo(null);
            temp.setVisible(true);
            this.temp = temp;
        }

        /**
         * <p>
         * Listner class for the buttons in the JFrame created when resizeAction fires
         * </p>
         * 
         * @author Charlie Templeton
         */
        private class ButtonListener implements ActionListener {
            @Override

            /**
             * <p>
             * listens to the buttons in the Jframe created when resizeAction fires
             * and when clicked will execute the desired resize
             * </p>
             * 
             * @param e The event triggering this callback.
             */
            public void actionPerformed(ActionEvent e) {
                int width = 1;
                int height = 1;
                if (isUsable(resize.getSelectedItem().toString())) {
                    width = (int) (target.getImage().getCurrentImage().getWidth() * (getPercentChange() / 100.0));
                    height = (int) (target.getImage().getCurrentImage().getHeight() * (getPercentChange() / 100.0));
                }
                if (e.getSource() == cancel) {
                    error.setText("");
                    temp.dispose();
                    count = 1;
                }
                if (!isUsable(resize.getSelectedItem().toString())) {
                    error.setForeground(Color.red);
                    error.setText(
                            "<html><div text-align: center> " + Settings.getMessage("ResizeErrorMessageReadNumbers")
                                    + " ("
                                    + count + ")</div></html>");
                    error.setHorizontalAlignment(SwingConstants.CENTER);
                    count++;
                } else if (e.getSource() == ok && getPercentChange() > 1000) {
                    error.setForeground(Color.red);
                    error.setText("<html><div text-align: center> "
                            + Settings.getMessage("ResizeErrorMessageScaleLimit") + " (" + count
                            + ") </div></html>");
                    error.setHorizontalAlignment(SwingConstants.CENTER);
                    count++;
                } else if (e.getSource() == ok && (width <= 0 || height <= 0)) {
                    error.setForeground(Color.red);
                    error.setText(
                            "<html><div text-align: center> " + Settings.getMessage("ResizeErrorMessageScale0") + " ("
                                    + count + ") </div></html>");
                    error.setHorizontalAlignment(SwingConstants.CENTER);
                    count++;
                } else if (e.getSource() == ok) {
                    error.setText("");
                    doResize(getPercentChange());
                    count = 1;
                    Settings.packFrame();
                }

            }

        }
    }

    /**
     * <p>
     * Action to begin cropping an image.
     * </p>
     * 
     * @see ConvertToGrey
     */
    public class CropAction extends ImageAction {

        /**
         * <p>
         * Create a new crop action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        CropAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the crop action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the CropAction is triggered.
         * It starts the process of cropping
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Settings.getTB().setTBUnusable();
            Settings.getTB().setMacroUnusable();
            Settings.setAxisCursor();
            Settings.makeDrawingFalseCrop();
            ShapeHandler.setShapeType(3);
            Settings.setCropping(true);

            // This now waits for the user to drag over the area they wish to crop then
            // applies the crop from mouse listener
        }

    }
}
