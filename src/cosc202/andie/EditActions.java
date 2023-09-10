package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Edit menu.
 * </p>
 * 
 * <p>
 * The Edit menu is very common across a wide range of applications.
 * There are a lot of operations that a user might expect to see here.
 * In the sample code there are Undo and Redo actions, but more may need to be
 * added.
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
public class EditActions implements AndieAction {
    /** The EditMenu for the JMenuBar */
    private JMenu editMenu;
    /** A list of actions for the Edit menu. */
    protected ArrayList<Action> actions;
    /** A array of the edit actions */
    private String[] items = { "Undo", "Redo" };

    /**
     * <p>
     * Create a set of Edit menu actions.
     * </p>
     */
    public EditActions() {
        actions = new ArrayList<Action>();
        actions.add(new UndoAction("Undo", null, "Undo", Integer.valueOf(KeyEvent.VK_Z)));
        actions.add(new RedoAction("Redo", null, "Redo", Integer.valueOf(KeyEvent.VK_Y)));
    }

    /**
     * <p>
     * Create a menu containing the list of Edit actions.
     * </p>
     * 
     * @return The edit menu UI element.
     */
    public JMenu createMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.setForeground(new Color(150, 150, 150));
        editMenu.setEnabled(false);
        this.editMenu = editMenu;
        for (Action action : actions) {
            editMenu.add(new JMenuItem(action));
        }

        return editMenu;
    }

    /**
     * <p>
     * Reconfigures the JMenuBar menu so that it is usable and not greyed out.
     * </p>
     */
    public void setUsable() {
        editMenu.setForeground(Color.black);
        editMenu.setEnabled(true);
    }

    /**
     * <p>
     * Reconfigures the JMenuBar menu so that it is unusable and greyed out.
     * </p>
     */
    public void setUnusable() {
        editMenu.setForeground(Color.gray);
        editMenu.setEnabled(false);
    }

    /**
     * A method to set the title of this menu.
     * 
     * @param text The new title of the menu
     */
    public void setTitleText(String text) {
        editMenu.setText(text);
    }

    /**
     * A method to set the title of a child in this menu.
     * 
     * @param item The index of the child to be changed
     * @param text The new title of the child
     */
    public void setItemText(int item, String text) {
        editMenu.getItem(item).setText(text);
    }

    /**
     * A method to set the prompt of a child in this menu.
     * 
     * @param item The index of the child to be changed
     * @param text The new prompt of the child
     */
    public void setItemPrompt(int item, String text) {
        editMenu.getItem(item).getAction().putValue("ShortDescription", text);
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
     * Action to undo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#undo()
     */
    public class UndoAction extends ImageAction {

        /**
         * <p>
         * Create a new undo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        UndoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the undo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the UndoAction is triggered.
         * It undoes the most recently applied operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            Settings.setWaitCursor();
            // Catches exception if user try to undo when stack is empty
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        target.getImage().undo();
                        double height = (double) target.getImage().getCurrentImage().getHeight() * target.getScale();
                        double width = (double) target.getImage().getCurrentImage().getWidth() * target.getScale();
                        target.setMaximumSize(new Dimension((int) width,
                                (int) height));
                        overlay.setMaximumSize(new Dimension((int) width,
                                (int) height));
                        overlay.repaint();
                        target.repaint();
                        target.getParent().revalidate();
                        Settings.packFrame();
                        Settings.setDefaultCursor();
                    } catch (Exception ex) {
                        String[] okAndCancel = { Settings.getMessage("OK") };
                        JOptionPane.showOptionDialog(null, Settings.getMessage("UndoErrorMessage"),
                                Settings.getMessage("UndoErrorTitle"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, okAndCancel,
                                okAndCancel[0]);
                                Settings.setDefaultCursor();
                    }
                }
            });
            // Start the thread
            thread.start();

        }
    }

    /**
     * <p>
     * Action to redo an {@link ImageOperation}.
     * </p>
     * 
     * @see EditableImage#redo()
     */
    public class RedoAction extends ImageAction {

        /**
         * <p>
         * Create a new redo action.
         * </p>
         * 
         * @param name     The name of the action (ignored if null).
         * @param icon     An icon to use to represent the action (ignored if null).
         * @param desc     A brief description of the action (ignored if null).
         * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
         */
        RedoAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Callback for when the redo action is triggered.
         * </p>
         * 
         * <p>
         * This method is called whenever the RedoAction is triggered.
         * It redoes the most recently undone operation.
         * </p>
         * 
         * @param e The event triggering this callback.
         */
        public void actionPerformed(ActionEvent e) {
            // Catches exception if user trys to redo when nothing has been undone
            Settings.setWaitCursor();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        target.getImage().redo();
                        double height = (double) target.getImage().getCurrentImage().getHeight() * target.getScale();
                        double width = (double) target.getImage().getCurrentImage().getWidth() * target.getScale();
                        target.setMaximumSize(new Dimension((int) width,
                                (int) height));
                        overlay.setMaximumSize(new Dimension((int) width,
                                (int) height));

                        target.repaint();
                        target.getParent().revalidate();
                        Settings.packFrame();
                        Settings.setDefaultCursor();

                    } catch (Exception ex) {
                        String[] okAndCancel = { Settings.getMessage("OK") };
                        JOptionPane.showOptionDialog(null, Settings.getMessage("RedoErrorMessage"),
                                Settings.getMessage("RedoErrorTitle"),
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, okAndCancel,
                                okAndCancel[0]);
                                Settings.setDefaultCursor();

                    }
                }
            });
            // Start the thread
            thread.start();

        }
    }

}
