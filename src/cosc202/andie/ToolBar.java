package cosc202.andie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * <p>
 * ToolBar Class creates a ToolBar for the user to use allowing quick access to
 * operations
 * </p>
 * 
 * @author Charlie Templeton
 */
public class ToolBar extends JPanel {
    /**
     * The frame containing the toolbar
     */
    private JFrame frame;
    /**
     * The buttons on the toolbar drawing section
     */
    private JButton draw, line, rectangle, roundRect, circle, color;
    /**
     * The buttons on the toolbar on the image options and macro section
     */
    private JButton hFlip, vFlip, rr, rl, plus, minus, select, eraser, undo, redo, macro1, macro2, macro3, macro4;
    /**
     * The text field for the zoom percentage
     */
    private static JTextField zoomPercentage;
    /**
     * The labels on the toolbar for macros and zoom
     */
    private JLabel zoom, macroLabel;
    /**
     * The combo box for selecting a pen stroke size
     */
    private JComboBox<Integer> strokeSizeComboBox;
    /**
     * If the toolbar is horizontal or vertical
     * True for horizontal.
     */
    private boolean horizontal = true;

    /**
     * <p>
     * The constructor for ToolBar which creates all the items on the toolbar and
     * adds them
     * </p>
     * 
     * @param frame The Frame the Andie is in
     */
    public ToolBar(JFrame frame) {
        this.frame = frame;

        setBackground(new Color(215, 215, 215));
        setLayout(null);

        // *** ToolBar Labels *** //
        // Zoom Label
        zoom = new JLabel(Settings.getMessage("ZoomIn"));
        zoom.setFont(new Font("Arial", Font.BOLD, 20));
        zoom.setHorizontalAlignment(SwingConstants.CENTER);

        // Macro Label
        macroLabel = new JLabel(Settings.getMessage("Macros"));
        macroLabel.setFont(new Font("Arial", Font.BOLD, 20));
        macroLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // *** Drawing Section *** //
        // Freedraw Pencil
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/draw.png"));
        Image image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
        ImageIcon scaledIcon = new ImageIcon(image);
        JButton draw = new JButton(scaledIcon);
        draw.setOpaque(true);
        draw.setBackground(new Color(106, 188, 205));
        draw.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        draw.setBounds(10, 30, 20, 20);
        draw.setContentAreaFilled(false);
        this.draw = draw;

        // Line
        imageIcon = new ImageIcon(getClass().getResource("/line.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
        scaledIcon = new ImageIcon(image);
        JButton line = new JButton(scaledIcon);
        line.setOpaque(true);
        line.setBackground(new Color(106, 188, 205));
        line.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        line.setBounds(40, 30, 20, 20);
        line.setContentAreaFilled(false);
        this.line = line;

        // Rectangle
        imageIcon = new ImageIcon(getClass().getResource("/Square.png"));
        image = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        scaledIcon = new ImageIcon(image);
        JButton rectangle = new JButton(scaledIcon);
        rectangle.setOpaque(true);
        rectangle.setBackground(new Color(106, 188, 205));
        rectangle.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        rectangle.setBounds(10, 55, 20, 20);
        rectangle.setContentAreaFilled(false);
        this.rectangle = rectangle;

        // Round Rectangle
        imageIcon = new ImageIcon(getClass().getResource("/RoundSquare.png"));
        image = imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        scaledIcon = new ImageIcon(image);
        JButton roundRect = new JButton(scaledIcon);
        roundRect.setOpaque(true);
        roundRect.setBackground(new Color(106, 188, 205));
        roundRect.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        roundRect.setBounds(40, 55, 20, 20);
        roundRect.setContentAreaFilled(false);
        this.roundRect = roundRect;

        // Circle
        imageIcon = new ImageIcon(getClass().getResource("/Circle.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
        scaledIcon = new ImageIcon(image);
        JButton circle = new JButton(scaledIcon);
        circle.setOpaque(true);
        circle.setBackground(new Color(106, 188, 205));
        circle.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        circle.setBounds(70, 30, 20, 20);
        circle.setContentAreaFilled(false);
        this.circle = circle;

        // Color Selecter
        JButton color = new JButton();
        color.setOpaque(true);
        color.setBackground(Color.black);
        color.setBorder(BorderFactory.createLineBorder(Color.white, 1));
        color.setBounds(100, 30, 20, 20);
        this.color = color;

        // Stroke Size Selecter
        // Create a combo box with stroke size options
        Integer[] strokeSizes = { 1, 2, 4, 8, 16 };
        strokeSizeComboBox = new JComboBox<>(strokeSizes);
        strokeSizeComboBox.setSelectedIndex(0); // Set default stroke size

        // Select Region
        select = new JButton();
        select.setText(Settings.getMessage("Select"));
        select.setFont(new Font("Arial", Font.PLAIN, 15));
        select.setOpaque(true);
        select.setBackground(Color.white);
        select.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        select.setBounds(10, 30, 20, 20);
        select.setFocusPainted(false);

        // Eraser
        imageIcon = new ImageIcon(getClass().getResource("/Eraser.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_AREA_AVERAGING);
        scaledIcon = new ImageIcon(image);
        eraser = new JButton(scaledIcon);
        eraser.setOpaque(true);
        eraser.setBackground(new Color(106, 188, 205));
        eraser.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        eraser.setBounds(10, 30, 20, 20);
        eraser.setContentAreaFilled(false);

        // *** Image Options Section *** //
        // Undo
        imageIcon = new ImageIcon(getClass().getResource("/Undo.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        undo = new JButton(scaledIcon);
        undo.setOpaque(true);
        undo.setBackground(new Color(215, 215, 215));
        undo.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        undo.setBounds(40, 30, 20, 20);

        // Redo
        imageIcon = new ImageIcon(getClass().getResource("/Redo.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        redo = new JButton(scaledIcon);
        redo.setOpaque(true);
        redo.setBackground(new Color(215, 215, 215));
        redo.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        redo.setBounds(40, 30, 20, 20);

        // Horizontal Flip
        imageIcon = new ImageIcon(getClass().getResource("/HFlip.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        hFlip = new JButton(scaledIcon);
        hFlip.setOpaque(true);
        hFlip.setBackground(new Color(215, 215, 215));
        hFlip.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        hFlip.setBounds(40, 30, 20, 20);

        // Vertical Flip
        imageIcon = new ImageIcon(getClass().getResource("/VFlip.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        vFlip = new JButton(scaledIcon);
        vFlip.setOpaque(true);
        vFlip.setBackground(new Color(215, 215, 215));
        vFlip.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        vFlip.setBounds(40, 30, 20, 20);

        // Rotate Right
        imageIcon = new ImageIcon(getClass().getResource("/RotateRight.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        rr = new JButton(scaledIcon);
        rr.setOpaque(true);
        rr.setBackground(new Color(215, 215, 215));
        rr.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        rr.setBounds(40, 30, 20, 20);

        // Rotate Left
        imageIcon = new ImageIcon(getClass().getResource("/RotateLeft.png"));
        image = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        rl = new JButton(scaledIcon);
        rl.setOpaque(true);
        rl.setBackground(new Color(215, 215, 215));
        rl.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        rl.setBounds(40, 30, 20, 20);

        // *** Zoom In Section ** //
        // Minus Button
        imageIcon = new ImageIcon(getClass().getResource("/Minus.png"));
        image = imageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        minus = new JButton(scaledIcon);
        minus.setOpaque(true);
        minus.setBackground(new Color(215, 215, 215));
        minus.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        minus.setForeground(Color.black);

        // Plus Button
        imageIcon = new ImageIcon(getClass().getResource("/Plus.png"));
        image = imageIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        scaledIcon = new ImageIcon(image);
        plus = new JButton(scaledIcon);
        plus.setOpaque(true);
        plus.setBackground(new Color(215, 215, 215));
        plus.setBorder(BorderFactory.createLineBorder(new Color(215, 215, 215), 1));
        plus.setForeground(Color.black);

        // Zoom Percentage
        zoomPercentage = new JTextField("100%");
        zoomPercentage.setEditable(false);
        zoomPercentage.setHorizontalAlignment(JTextField.CENTER);

        // *** Macro Section ***/
        // Macro1
        macro1 = new JButton("1");
        macro1.setOpaque(true);
        macro1.setBackground(Color.white);
        macro1.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        macro1.setForeground(Color.black);

        // Macro2
        macro2 = new JButton("2");
        macro2.setOpaque(true);
        macro2.setBackground(Color.white);
        macro2.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        macro2.setForeground(Color.black);

        // Macro3
        macro3 = new JButton("3");
        macro3.setOpaque(true);
        macro3.setBackground(Color.white);
        macro3.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        macro3.setForeground(Color.black);

        // Macro4
        macro4 = new JButton("4");
        macro4.setOpaque(true);
        macro4.setBackground(Color.white);
        macro4.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        macro4.setForeground(Color.black);

        // Add all button listners
        ButtonListener bl = new ButtonListener();
        draw.addActionListener(bl);
        line.addActionListener(bl);
        rectangle.addActionListener(bl);
        roundRect.addActionListener(bl);
        circle.addActionListener(bl);
        color.addActionListener(bl);
        strokeSizeComboBox.addActionListener(bl);
        undo.addActionListener(bl);
        redo.addActionListener(bl);
        hFlip.addActionListener(bl);
        vFlip.addActionListener(bl);
        rr.addActionListener(bl);
        rl.addActionListener(bl);
        minus.addActionListener(bl);
        plus.addActionListener(bl);
        select.addActionListener(bl);
        eraser.addActionListener(bl);
        macro1.addActionListener(bl);
        macro2.addActionListener(bl);
        macro3.addActionListener(bl);
        macro4.addActionListener(bl);

        // Setting all focusables to false. Means keybindings will work.
        draw.setFocusable(false);
        line.setFocusable(false);
        rectangle.setFocusable(false);
        roundRect.setFocusable(false);
        circle.setFocusable(false);
        color.setFocusable(false);
        strokeSizeComboBox.setFocusable(false);
        undo.setFocusable(false);
        redo.setFocusable(false);
        hFlip.setFocusable(false);
        vFlip.setFocusable(false);
        rr.setFocusable(false);
        rl.setFocusable(false);
        minus.setFocusable(false);
        plus.setFocusable(false);
        select.setFocusable(false);
        eraser.setFocusable(false);
        macro1.setFocusable(false);
        macro2.setFocusable(false);
        macro3.setFocusable(false);
        macro4.setFocusable(false);

        // Drawing Add Buttons
        add(draw);
        add(line);
        add(rectangle);
        add(roundRect);
        add(circle);
        add(color);
        add(strokeSizeComboBox);
        add(select);
        add(eraser);

        // Image Options Add Buttons
        add(undo);
        add(redo);
        add(hFlip);
        add(vFlip);
        add(rr);
        add(rl);

        // Zoom Options add Buttons and Label
        add(zoom);
        add(minus);
        add(zoomPercentage);
        add(plus);

        // Macros Section add Labels and Buttons
        add(macroLabel);
        add(macro1);
        add(macro2);
        add(macro3);
        add(macro4);

        Settings.setPenColor(Color.BLACK);
        Settings.setStrokeSize(1);

        setMacroUnusable();
        setTBUnusable();
    }

    /**
     * <p>
     * Method makes ToolBar macros unusable
     * </p>
     */
    public void setMacroUnusable() {
        macro1.setEnabled(false);
        macro2.setEnabled(false);
        macro3.setEnabled(false);
        macro4.setEnabled(false);
    }

    /**
     * <p>
     * Method makes ToolBar macros usable
     * </p>
     */
    public void setMacroUsable() {
        macro1.setEnabled(true);
        macro2.setEnabled(true);
        macro3.setEnabled(true);
        macro4.setEnabled(true);
    }

    /**
     * <p>
     * Method makes ToolBar usable
     * </p>
     */
    public void setTBUsable() {
        draw.setEnabled(true);
        line.setEnabled(true);
        rectangle.setEnabled(true);
        roundRect.setEnabled(true);
        circle.setEnabled(true);
        color.setEnabled(true);
        strokeSizeComboBox.setEnabled(true);
        select.setEnabled(true);
        eraser.setEnabled(true);
        undo.setEnabled(true);
        redo.setEnabled(true);
        hFlip.setEnabled(true);
        vFlip.setEnabled(true);
        rr.setEnabled(true);
        rl.setEnabled(true);
        minus.setEnabled(true);
        plus.setEnabled(true);
    }

    /**
     * <p>
     * Method makes ToolBar unusable
     * </p>
     */
    public void setTBUnusable() {
        draw.setEnabled(false);
        line.setEnabled(false);
        rectangle.setEnabled(false);
        roundRect.setEnabled(false);
        circle.setEnabled(false);
        color.setEnabled(false);
        strokeSizeComboBox.setEnabled(false);
        select.setEnabled(false);
        eraser.setEnabled(false);
        undo.setEnabled(false);
        redo.setEnabled(false);
        hFlip.setEnabled(false);
        vFlip.setEnabled(false);
        rr.setEnabled(false);
        rl.setEnabled(false);
        minus.setEnabled(false);
        plus.setEnabled(false);
    }

    /**
     * <p>
     * Method makes ToolBar Horizontal
     * </p>
     */
    public void setHorizontal() {
        horizontal = true;

        // Set Bounds for ToolBar
        color.setBounds(205, 45, 30, 30);
        circle.setBounds(105, 45, 30, 30);
        line.setBounds(65, 5, 30, 30);
        draw.setBounds(25, 5, 30, 30);
        strokeSizeComboBox.setBounds(145, 45, 50, 30);
        roundRect.setBounds(65, 45, 30, 30);
        rectangle.setBounds(25, 45, 30, 30);
        select.setBounds(105, 5, 90, 30);
        eraser.setBounds(205, 5, 30, 30);

        undo.setBounds(265, 5, 30, 30);
        redo.setBounds(305, 5, 30, 30);
        hFlip.setBounds(345, 5, 30, 30);
        vFlip.setBounds(345, 45, 30, 30);
        rl.setBounds(265, 45, 30, 30);
        rr.setBounds(305, 45, 30, 30);

        zoom.setBounds(390, 5, 150, 30);
        minus.setBounds(405, 50, 20, 20);
        zoomPercentage.setBounds(435, 45, 60, 30);
        plus.setBounds(505, 50, 20, 20);

        macroLabel.setBounds(540, 5, 150, 30);
        macro1.setBounds(550, 42, 25, 25);
        macro2.setBounds(585, 42, 25, 25);
        macro3.setBounds(620, 42, 25, 25);
        macro4.setBounds(655, 42, 25, 25);

        repaint();
        revalidate();
    }

    /**
     * <p>
     * Method makes ToolBar Vertical
     * </p>
     */
    public void setVertical() {
        horizontal = false;

        color.setBounds(5, 145, 30, 30);
        circle.setBounds(10, 105, 30, 30);
        line.setBounds(55, 65, 30, 30);
        draw.setBounds(55, 25, 30, 30);
        strokeSizeComboBox.setBounds(45, 145, 50, 30);
        roundRect.setBounds(10, 65, 30, 30);
        rectangle.setBounds(10, 25, 30, 30);
        select.setBounds(5, 185, 90, 30);
        eraser.setBounds(55, 105, 30, 30);

        undo.setBounds(10, 245, 30, 30);
        redo.setBounds(55, 245, 30, 30);
        hFlip.setBounds(10, 285, 30, 30);
        vFlip.setBounds(55, 285, 30, 30);
        rl.setBounds(10, 325, 30, 30);
        rr.setBounds(55, 325, 30, 30);

        zoom.setBounds(0, 375, 100, 30);
        minus.setBounds(2, 410, 20, 20);
        zoomPercentage.setBounds(25, 405, 50, 30);
        plus.setBounds(78, 410, 20, 20);

        macroLabel.setBounds(0, 455, 100, 30);
        macro1.setBounds(10, 495, 30, 30);
        macro2.setBounds(55, 495, 30, 30);
        macro3.setBounds(10, 535, 30, 30);
        macro4.setBounds(55, 535, 30, 30);

        repaint();
        revalidate();
    }

    /**
     * <p>
     * Method Unselects all buttons on the ToolBar
     * </p>
     */
    public void unselect() {
        Settings.makeDrawingFalse();
        draw.setContentAreaFilled(false);
        line.setContentAreaFilled(false);
        rectangle.setContentAreaFilled(false);
        roundRect.setContentAreaFilled(false);
        circle.setContentAreaFilled(false);
        select.setBackground(Color.white);
        eraser.setContentAreaFilled(false);
    }

    /**
     * <p>
     * Button Listner Class creates listners for all the buttons on the ToolBar
     * </p>
     */
    public class ButtonListener implements ActionListener {

        /**
         * <p>
         * Method is Triggered when a button is pressed
         * </p>
         * 
         * @param e The Action event that was triggered when the button is pressed
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            unselect();
            ImageAction.getOverlay().repaint();
            if (e.getSource() != select) {
                Settings.setSelecting(false);
            }
            if (e.getSource() != eraser) {
                Settings.setErasing(false);
            }
            // Line
            if (e.getSource() == line && ShapeHandler.getShapeType() != 1) {
                Settings.makeDrawingTrue(1);
                line.setContentAreaFilled(true);
            } else if (e.getSource() == line && ShapeHandler.getShapeType() == 1) {
                Settings.makeDrawingFalse();
                line.setContentAreaFilled(false);
            }

            // Freedraw
            if (e.getSource() == draw && ShapeHandler.getShapeType() != 2) {
                Settings.makeDrawingTrue(2);
                draw.setContentAreaFilled(true);
            } else if (e.getSource() == draw && ShapeHandler.getShapeType() == 2) {
                Settings.makeDrawingFalse();
                draw.setContentAreaFilled(false);
            }

            // Rectangle
            if (e.getSource() == rectangle && ShapeHandler.getShapeType() != 3) {
                Settings.makeDrawingTrue(3);
                rectangle.setContentAreaFilled(true);
            } else if (e.getSource() == rectangle && ShapeHandler.getShapeType() == 3) {
                Settings.makeDrawingFalse();
                rectangle.setContentAreaFilled(false);
            }

            // Circle
            if (e.getSource() == circle && ShapeHandler.getShapeType() != 4) {
                Settings.makeDrawingTrue(4);
                circle.setContentAreaFilled(true);
            } else if (e.getSource() == circle && ShapeHandler.getShapeType() == 4) {
                Settings.makeDrawingFalse();
                circle.setContentAreaFilled(false);
            }

            // Round Rectangle
            if (e.getSource() == roundRect && ShapeHandler.getShapeType() != 5) {
                Settings.makeDrawingTrue(5);
                roundRect.setContentAreaFilled(true);
            } else if (e.getSource() == roundRect && ShapeHandler.getShapeType() == 5) {
                Settings.makeDrawingFalse();
                roundRect.setContentAreaFilled(false);
            }

            // Color Selector
            if (e.getSource() == color) {

                Color initialColor = Color.BLACK;
                Color selectedColor = JColorChooser.showDialog(null, "Select a Color", initialColor);
                if (selectedColor != null) {
                    color.setBackground(selectedColor);
                }
                Settings.setPenColor(selectedColor);
            }

            // Stroke Size Selector
            if (e.getSource() == strokeSizeComboBox) {
                Settings.setStrokeSize((int) strokeSizeComboBox.getSelectedItem());
            }

            // Select Button
            if (e.getSource() == select && !Settings.isSelecting()) {
                select.setBackground(new Color(106, 188, 205));
                Settings.makeDrawingTrue(3, false);
                ShapeHandler.setShapeType(3);
                Settings.setSelecting(true);
            } else if (e.getSource() == select && Settings.isSelecting()) {
                select.setBackground(Color.white);
                Settings.setSelecting(false);
            }

            // Eraser Button
            if (e.getSource() == eraser && !Settings.isErasing()) {
                eraser.setContentAreaFilled(true);
                Settings.setErasing(true);
                Settings.makeDrawingTrue(2);
            } else if (e.getSource() == eraser && Settings.isErasing()) {
                Settings.setErasing(false);
                Settings.makeDrawingFalse();
                eraser.setContentAreaFilled(false);
            }

            // Undo
            if (e.getSource() == undo) {
                EditActions outerObj = new EditActions();
                EditActions.UndoAction innerObj = outerObj.new UndoAction("Undo", null,
                        "Undo an Image Opertaion", Integer.valueOf(KeyEvent.VK_U));
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                    ImageAction.target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }

            // Redo
            if (e.getSource() == redo) {
                EditActions outerObj = new EditActions();
                EditActions.RedoAction innerObj = outerObj.new RedoAction("Redo", null,
                        "Redo an Image Opertaion", Integer.valueOf(KeyEvent.VK_U));
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                    ImageAction.target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }

            // Horizontal Flip
            if (e.getSource() == hFlip) {
                ScaleImageActions outerObj = new ScaleImageActions();
                ScaleImageActions.FlipX innerObj = outerObj.new FlipX("Horizontal flip", null,
                        "Flips image horizontally", Integer.valueOf(KeyEvent.VK_H));
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                }
            }

            // Vertical Flip
            if (e.getSource() == vFlip) {
                ScaleImageActions outerObj = new ScaleImageActions();
                ScaleImageActions.FlipY innerObj = outerObj.new FlipY("Vertical flip", null, "Flips image vertically",
                        Integer.valueOf(KeyEvent.VK_V));
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                }
            }

            // Rotate Right
            if (e.getSource() == rr) {
                ScaleImageActions outerObj = new ScaleImageActions();
                ScaleImageActions.RotateImage_Right innerObj = outerObj.new RotateImage_Right("Rotate 90* Right", null,
                        "Rotates Image 90* to the Right", null);
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                }
            }

            // Rotate Left
            if (e.getSource() == rl) {
                ScaleImageActions outerObj = new ScaleImageActions();
                ScaleImageActions.RotateImage_Left innerObj = outerObj.new RotateImage_Left("Rotate 90* Left", null,
                        "Rotates Image 90* to the Left", null);
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                }
            }

            // Minus Button
            if (e.getSource() == minus && getZoomPercent() > 50 && ImageAction.target.getImage().hasImage()) {
                int number = getZoomPercent() - 1;
                zoomPercentage.setText(number + "%");
                ViewActions outerObj = new ViewActions();
                ViewActions.SetZoom innerObj = outerObj.new SetZoom("Set Zoom ToolBar", null, "Decreases zoom by 1",
                        null);
                innerObj.zoomSize(number);
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                }
            }

            // Plus Button
            if (e.getSource() == plus && getZoomPercent() < 200 && ImageAction.target.getImage().hasImage()) {
                int number = getZoomPercent() + 1;
                zoomPercentage.setText(number + "%");
                ViewActions outerObj = new ViewActions();
                ViewActions.SetZoom innerObj = outerObj.new SetZoom("Set Zoom ToolBar", null, "Increases zoom by 1",
                        null);
                innerObj.zoomSize(number);
                try {
                    innerObj.actionPerformed(e);
                } catch (NullPointerException ex) {
                }
            }

            // Macro1
            if (e.getSource() == macro1) {
                Settings.setWaitCursor();
                setMacroUnusable();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ImageAction.target.getImage().applyTBMacro(ToolBarMacros.macro1);
                        ImageAction.target.repaint();
                        ImageAction.overlay.repaint();
                        ImageAction.target.getParent().revalidate();
                        setMacroUsable();
                        Settings.setDefaultCursor();
                    }
                });
                // Start the thread
                thread.start();

            }

            // Macro2
            if (e.getSource() == macro2) {
                Settings.setWaitCursor();
                setMacroUnusable();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ImageAction.target.getImage().applyTBMacro(ToolBarMacros.macro2);
                        ImageAction.target.repaint();
                        ImageAction.overlay.repaint();
                        ImageAction.target.getParent().revalidate();
                        setMacroUsable();
                        Settings.setDefaultCursor();
                    }
                });
                // Start the thread
                thread.start();
            }

            // Macro3
            if (e.getSource() == macro3) {
                Settings.setWaitCursor();
                setMacroUnusable();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ImageAction.target.getImage().applyTBMacro(ToolBarMacros.macro3);
                        ImageAction.target.repaint();
                        ImageAction.overlay.repaint();
                        ImageAction.target.getParent().revalidate();
                        setMacroUsable();
                        Settings.setDefaultCursor();
                    }
                });
                // Start the thread
                thread.start();
            }

            // Macro4
            if (e.getSource() == macro4) {
                Settings.setWaitCursor();
                setMacroUnusable();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ImageAction.target.getImage().applyTBMacro(ToolBarMacros.macro4);
                        ImageAction.target.repaint();
                        ImageAction.overlay.repaint();
                        ImageAction.target.getParent().revalidate();
                        setMacroUsable();
                        Settings.setDefaultCursor();
                    }
                });
                // Start the thread
                thread.start();
            }

        }
    }

    /**
     * Getter method returns the Zoom Percent in the ZoomPercentage TextField
     * 
     * @return The Number that represents the percentage Zoomed
     */
    public static int getZoomPercent() {
        String number = zoomPercentage.getText();
        int x = Integer.parseInt(number.substring(0, number.length() - 1));
        return x;
    }

    /**
     * Setter method resets the Zoom to 100 when opening a new Image
     */
    public static void resetZoom() {
        ViewActions outerObj = new ViewActions();
        ViewActions.SetZoom innerObj = outerObj.new SetZoom("Set Zoom ToolBar", null, "Increases zoom by 1", null);
        innerObj.resetZoom();
        zoomPercentage.setText("100%");
    }

    /**
     * Setter method changes the text in ZoomPercentage to be the same ass the
     * current level of Zoom
     */
    public static void setZoomBox() {
        zoomPercentage.setText(String.format("%,.0f", ImageAction.target.getZoom()) + "%");
    }

    /**
     * <p>
     * The Paint component method adds the dashed lines to seperate the sections of
     * the tool bar
     * </p>
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Cast Graphics to Graphics2D to use the setStroke method
        Graphics2D g2d = (Graphics2D) g;

        // Set the color of the line
        g2d.setColor(Color.BLACK);

        // Set the stroke to a dashed line with a pattern of 10 pixels on and 5 pixels
        float[] dashPattern = { 10f, 5f };
        g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10f, dashPattern, 0f));

        if (horizontal) {
            // Draw a lines
            g2d.draw(new Line2D.Float(10, 5, 10, 75));
            g2d.draw(new Line2D.Float(250, 5, 250, 75));
            g2d.draw(new Line2D.Float(390, 5, 390, 75));
            g2d.draw(new Line2D.Float(540, 5, 540, 75));
            g2d.draw(new Line2D.Float(690, 5, 690, 75));
        } else if (!horizontal) {
            // Draw a lines
            g2d.draw(new Line2D.Float(7, 10, 97, 10));
            g2d.draw(new Line2D.Float(7, 230, 97, 230));
            g2d.draw(new Line2D.Float(7, 370, 97, 370));
            g2d.draw(new Line2D.Float(7, 450, 97, 450));
            g2d.draw(new Line2D.Float(7, 575, 97, 575));
        }

        // Set the stroke back to normal
        g2d.setStroke(new BasicStroke(1f));

    }

}
