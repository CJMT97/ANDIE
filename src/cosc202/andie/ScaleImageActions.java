package cosc202.andie;

import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

/**
 * <p>
 * A class of actions relating to the scaling of images.
 * </p>
 * 
 * 
 * @author Ben Nicholson
 */
public class ScaleImageActions implements AndieAction {
    /** A list of actions for the Scale Image Actions menu. */
    protected ArrayList<Action> actions;
    /** The Transformation menu for The MenuBar */
    private JMenu fileMenu;
    /** An Array of all the actions in ScaleImageActions */
    private String[] items = { "HorizontalFlip", "VerticalFlip", "Rotates90Right", "Rotates90Left", "Rotates180" };

    /**
     * <p>
     * Constructor adds all the ScaleImage actions to the actions
     * Arraylist.
     * <p>
     */
    ScaleImageActions() {
        actions = new ArrayList<Action>();
        actions.add(new FlipX("Horizontal flip", null, "Flips image horizontally", Integer.valueOf(KeyEvent.VK_H)));
        actions.add(new FlipY("Vertical flip", null, "Flips image vertically", Integer.valueOf(KeyEvent.VK_V)));
        actions.add(new RotateImage_Right("Rotate 90* Right", null, "Rotates Image 90* to the Right", null));
        actions.add(new RotateImage_Left("Rotate 90* Left", null, "Rotates Image 90* to the Left", null));
        actions.add(new RotateImage_180("Rotate 180*", null, "Rotates Image 180*", null));
    }

    /**
     * <p>
     * Create a set of Scale Image menu actions.
     * </p>
     * 
     * @return The created menu
     */
    public JMenu createMenu() {
        JMenu fileMenu = new JMenu("Pivot Image");
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
     * Class FlipX, Flips an image on it's horizontal (X) axis.
     * </p>
     * 
     * @author Charlie Templeton
     */
    public class FlipX extends ImageAction {
        FlipX(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            target.getImage().apply(new FlipHorizontal());
            target.repaint();
            target.getParent().revalidate();
        }
    }

    /**
     * <p>
     * Class FlipY, Flips an image on it's horizontal (Y) axis.
     * </p>
     * 
     * @author Charlie Templeton
     */
    public class FlipY extends ImageAction {
        FlipY(String name, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        public void actionPerformed(ActionEvent e) {
            // Create and apply the filter
            target.getImage().apply(new FlipVertical());
            target.repaint();
            target.getParent().revalidate();
        }

    }

    /**
     * Class RotateImage_Left
     * Rotates an image 90 degrees to the left
     * 
     * @author Ben Nicholson
     */
    public class RotateImage_Left extends ImageAction {

        RotateImage_Left(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            RotateImage rotateAction = new RotateImage(2);
            target.getImage().apply(rotateAction);
            int width = (int)(target.getImage().getCurrentImage().getWidth()*target.getScale());
            int height = (int)(target.getImage().getCurrentImage().getHeight()*target.getScale());
            target.setMaximumSize(new Dimension(width,
                    height));
            overlay.setMaximumSize(new Dimension(width,
            height));
            overlay.repaint();
            target.repaint();
            target.getParent().revalidate();
            Settings.packFrame();
        }
    }

    /**
     * Class RotateImage_Right
     * Rotates an image 90 degrees to the right.
     * 
     * @author Ben Nicholson
     */
    public class RotateImage_Right extends ImageAction {

        RotateImage_Right(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            RotateImage m = new RotateImage(1);
            target.getImage().apply(m);

            int width = (int)(target.getImage().getCurrentImage().getWidth()*target.getScale());
            int height = (int)(target.getImage().getCurrentImage().getHeight()*target.getScale());
            target.setMaximumSize(new Dimension(width,
                    height));
            overlay.setMaximumSize(new Dimension(width,
            height));
            target.repaint();
            target.getParent().revalidate();
            Settings.packFrame();
        }
    }

    /**
     * Class RotateImage180
     * 
     * <p>
     * Rotates an Image 180
     * </p>
     * 
     * @author Ben Nicholson
     */
    public class RotateImage_180 extends ImageAction {

        RotateImage_180(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            target.getImage().apply(new RotateImage());
            target.repaint();
            target.getParent().revalidate();
            Settings.packFrame();
        }

    }
}
