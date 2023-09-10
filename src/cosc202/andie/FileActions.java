package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the File menu.
 * </p>
 * 
 * <p>
 * The File menu is very common across applications,
 * and there are several items that the user will expect to find here.
 * Opening and saving files is an obvious one, but also exiting the program.
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
public class FileActions implements AndieAction {
    // DataField
    /** A list of actions for the File menu. */
    protected ArrayList<Action> actions;
    /** The FileMenu for The Menu Bar */
    private JMenu fileMenu;
    /** The settings action */
    private SettingsAction settingsAction;
    /** An Array of the Actions in FileActions */
    private String[] items = { "Open", "Save", "SaveAs", "Export", "Exit" };
    /** The Appendage for the file name */
    private String appendage;
    /** The alllowed appendages for images */
    private String[] allowedFileTypes = { "JPG", "JPEG", "PNG" };

    /**
     * <p>
     * Create a set of File menu actions.
     * </p>
     */
    public FileActions() {
        actions = new ArrayList<Action>();
        actions.add(new FileOpenAction("Open", null, "Open a file", Integer.valueOf(KeyEvent.VK_O)));
        actions.add(new FileSaveAction("Save", null, "Save the file", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileSaveAsAction("Save As", null, "Save a copy", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new FileExportAction("Export", null, "Export the file", Integer.valueOf(KeyEvent.VK_S)));
        actions.add(new FileExitAction("Exit", null, "Exit the program", Integer.valueOf(0)));

    }

    /**
     * <p>
     * sets the value of the settingsAction data field
     * </p>
     * 
     * @param sa The instance of settingsAction
     */
    public void setSettings(SettingsAction sa) {
        settingsAction = sa;
    }

    /**
     * Gets the settings action instance
     * 
     * @return The settings action
     */
    public SettingsAction getSettingsAction() {
        return this.settingsAction;
    }

    /**
     * <p>
     * Create a menu contianing the list of File actions.
     * </p>
     * 
     * @return The File menu UI element.
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setForeground(Color.black);
        for (Action action : actions) {
            fileMenu.add(new JMenuItem(action));
        }

        for (int i = 1; i < 4; i++) {
            fileMenu.getItem(i).setForeground(new Color(150, 150, 150));
            fileMenu.getItem(i).setEnabled(false);
        }

        this.fileMenu = fileMenu;
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
     * Action to open an image from file.
     * </p>
     * 
     * @see EditableImage#open(String)
     */
    public class FileOpenAction extends ImageAction {

        /**
         * <p>
         * Create a new file-open action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileOpenAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);

        }

        /**
         * <p>
         * Callback for when the file-open action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileOpenAction is triggered.
         * It prompts the user to select a file and opens it as an image.
         * </p>
         * 
         * Added an array of Strings that represent paths to the photos file to save
         * navigating file systems during debugging
         * Should probably be removed before hand in
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // If opening a new image. Removes all Image Operations
            if (target.getImage().hasImage()) {
                String[] okAndCancel = { Settings.getMessage("OK"), Settings.getMessage("CANCEL") };
                int option = JOptionPane.showOptionDialog(null, Settings.getMessage("OpenSaveCheck"),
                        Settings.getMessage("OpenSaveCheckTitle"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, okAndCancel, null);
                if (option == JOptionPane.NO_OPTION) {
                    return;
                }
            }

            // This is the start of setting the file chooser to the photos folder
            String[] photoLocations = {
                    "A location",
                    "C:/Users/hamis/OneDrive - University of Otago/Documents/University/Third Year/COSC202/Andie/andie/Photos",
                    "/Users/charlietempleton/Desktop/Photos",
                    "/J:/ANDIE/andie/Photos",
                    "J:/COSC202/Andie/andie/Photos", "C:/Users/ben0n/Desktop/University/COSC202/andie/andie/Photos" };
            // dsd
            File file;
            int i = 0;
            do {
                file = new File(photoLocations[i++]);
            } while (!file.exists() && i < photoLocations.length);
            JFileChooser fileChooser = new JFileChooser();
            if (file.exists()) {
                fileChooser.setCurrentDirectory(file);
            }
            // This is the end of the setting of the file chooser to the photos folder

            int result = fileChooser.showOpenDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    if (target.getImage().hasImage()) {
                        target.restart_image();
                        target.repaint();
                    }

                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    target.getImage().open(imageFilepath);
                    // Once image open sets all menu items to be usable
                    settingsAction.imageExists();
                    for (int j = 1; j < 4; j++) {
                        fileMenu.getItem(j).setForeground(Color.black);
                        fileMenu.getItem(j).setEnabled(true);
                    }

                    // Editing the Buffered images size to match the new target image. Note this is
                    // black by default

                    target.setMaximumSize(new Dimension(target.getImage().getCurrentImage().getWidth(),
                            target.getImage().getCurrentImage().getHeight()));
                    overlay.setMaximumSize(new Dimension(target.getImage().getCurrentImage().getWidth(),
                            target.getImage().getCurrentImage().getHeight()));

                    target.getParent().revalidate();
                    Settings.packFrame();
                    appendage = fileChooser.getTypeDescription(fileChooser.getSelectedFile());
                    // System.out.println(appendage);
                    ToolBar.resetZoom();
                } catch (Exception ex) {
                    // Catches exception if user trys to open a file that is not an image
                    System.out.println(ex);
                    String[] okAndCancel = { Settings.getMessage("OK") };
                    JOptionPane.showOptionDialog(null, Settings.getMessage("OpenErrorMessage"),
                            Settings.getMessage("OpenErrorTitle"),
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, okAndCancel,
                            okAndCancel[0]);
                }
            } else if (result == JFileChooser.CANCEL_OPTION || result == JFileChooser.ABORT) {
                target.repaint();
                target.getParent().revalidate();
                Settings.packFrame();
                return;
            }
            Settings.getSettingsMenu().getItem(3).setEnabled(true);
            Settings.getTB().setMacroUsable();
            Settings.getTB().setTBUsable();
        }

    }

    /**
     * <p>
     * Action to save an image to its current file location.
     * </p>
     * 
     * @see EditableImage#save()
     */
    public class FileSaveAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAction is triggered.
         * It saves the image to its original filepath.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().save();
            } catch (Exception ex) {
                System.exit(1);
            }
        }

    }

    /**
     * <p>
     * Action to save an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileSaveAsAction extends ImageAction {

        /**
         * <p>
         * Create a new file-save-as action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileSaveAsAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getAbsolutePath();

                    // This is the file type of the image which was opened
                    String suffix = appendage.substring(0, appendage.indexOf(" ")).toUpperCase();
                    // System.out.println(imageFilepath);
                    if (imageFilepath.contains(".")) {
                        // System.out.println(". found");
                        String path = imageFilepath;
                        while (path.contains(".")) {
                            path = path.substring(path.indexOf(".") + 1, path.length());
                        }

                        // System.out.println(path);

                        boolean found = false;
                        for (String extension : allowedFileTypes) {
                            if (path.toUpperCase().equals(extension)) {
                                found = true;
                            }
                        }
                        // If the user didn't enter a file type then append the original file type
                        if (!found || (suffix.equals("PNG") && !path.toUpperCase().equals("PNG"))) {
                            imageFilepath = imageFilepath + "." + suffix;
                        }
                    } else {
                        imageFilepath = imageFilepath + "." + suffix;
                    }
                    // System.out.println(imageFilepath);
                    target.getImage().saveAs(imageFilepath);
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        }

    }

    /**
     * <p>
     * Action to export an image to a new file location.
     * </p>
     * 
     * @see EditableImage#saveAs(String)
     */
    public class FileExportAction extends ImageAction {

        /**
         * <p>
         * Create a new file export action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExportAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-save-as action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileSaveAsAction is triggered.
         * It prompts the user to select a file and saves the image to it.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(target);

            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    String imageFilepath = fileChooser.getSelectedFile().getCanonicalPath();
                    // System.out.println("\""
                    // + imageFilepath.substring(imageFilepath.length() - 4,
                    // imageFilepath.length()).toUpperCase()
                    // + "\"");
                    if (!imageFilepath.substring(imageFilepath.length() - 4, imageFilepath.length()).toUpperCase()
                            .equals(".PNG")) {
                        imageFilepath = imageFilepath + ".PNG";
                    }
                    // System.out.println(imageFilepath);
                    File file = new File(imageFilepath);
                    ImageIO.write(target.getImage().getCurrentImage(), "PNG", file);
                } catch (Exception ex) {
                    System.exit(1);
                }
            }
        }

    }

    /**
     * <p>
     * Action to quit the ANDIE application.
     * </p>
     */
    public class FileExitAction extends AbstractAction {

        /**
         * <p>
         * Create a new file-exit action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        FileExitAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon);
            putValue(SHORT_DESCRIPTION, desc);
            putValue(MNEMONIC_KEY, mnemonic);
        }

        /**
         * <p>
         * Callback for when the file-exit action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the FileExitAction is triggered.
         * It quits the program.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }

    }
}
