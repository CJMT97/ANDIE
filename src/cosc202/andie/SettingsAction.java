package cosc202.andie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * <p>
 * Actions provided by the Settings menu.
 * </p>
 * 
 * <p>
 * The Settings menu provides the static settings class which manages
 * application wide settings and the locale setttings.
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class SettingsAction {

    /** A list of actions for the Settings menu. */
    private static JMenu settingsMenu;
    /** A list ofactions to be displayed under the settngs menu */
    protected ArrayList<Action> actions;
    /**
     * The message bundle that is used to display text in the appropriate language
     */
    public static ResourceBundle bundle = null;
    /**
     * All of the actions added to the menu bar to be updated when language is
     * changed
     */
    private static AndieAction[] allActions;
    /**
     * If we are recording a new macro
     */
    private static boolean applying;

    /**
     * <p>
     * Create the Settings menu and initialises the static variables used Settings
     * for updating locale and resizing an image
     * </p>
     * 
     * @param actionsList A list of AndieActions that are the menus in the menu bar.
     *                    Menus and all their children will be updated dynamically
     *                    when the language is changed at run time.
     * 
     * @param frame       The main JFrame that the application is running in. This
     *                    is used in the rezise and center the window.
     */
    public SettingsAction(ArrayList<AndieAction> actionsList, JFrame frame) {
        actions = new ArrayList<Action>();
        allActions = new AndieAction[actionsList.size()];
        for (int i = 0; i < allActions.length; i++) {
            allActions[i] = actionsList.get(i);
        }
        actions.add(new Settings("Settings", null, "Open the settings", Integer.valueOf(KeyEvent.VK_O),
                actions, allActions, frame));
        actions.add(new ConfigureToolBar("Configure ToolBar", null, "Configure the ToolBar",
                Integer.valueOf(KeyEvent.VK_C)));
        actions.add(
                new CreateMacroAction("Create a Macro", null, "Creates a new Macro", Integer.valueOf(KeyEvent.VK_A)));
        actions.add(new ApplyMacroAction("Apply a Macro", null, "Apply's a new Macro", Integer.valueOf(KeyEvent.VK_M)));
        actions.add(new MailAction("Report a bug", null, "Send Feedback", Integer.valueOf(KeyEvent.VK_F)));
    }

    /**
     * <p>
     * Creates the UI for configuring the ToolBar in Andie
     * </p>
     */
    public class ConfigureToolBar extends ImageAction {

        ConfigureToolBar(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Settings.createTBFrame();
        }

    }

    /**
     * <p>
     * Applys a new macro to the target image
     * </p>
     */
    public class ApplyMacroAction extends ImageAction {

        /**
         * Constructor for the ApplyMacroAction class
         * 
         * @param name     The name of the action
         * @param icon     The icon for the action
         * @param desc     The description of the action
         * @param mnemonic The mnemonic for the action
         */
        ApplyMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * <p>
         * Applys a macro to the target image
         * </p>
         */
        public void actionPerformed(ActionEvent e) {
            try {
                target.getImage().appyMacro();
                target.repaint();
                target.revalidate();
            } catch (Exception ex) {
            }
        }
    }

    /**
     * <p>
     * Make a new Macro by recording actions performed on the target image
     * </p>
     */
    public class CreateMacroAction extends ImageAction {

        /**
         * Constructor for the CreateMacroAction class
         * 
         * @param name     The name of the action
         * @param icon     The icon for the action
         * @param desc     The description of the action
         * @param mnemonic The mnemonic for the action
         */
        CreateMacroAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Creates a macro allowing the user to begin recording a new macro.
         */
        public void actionPerformed(ActionEvent e) {
            createMacroUI(target);
        }
    }

    /**
     * <p>
     * Creates the GUI for recording and creating a new Macro
     * </p>
     * 
     * @param target The Image Panel
     */
    public void createMacroUI(ImagePanel target) {
        JFrame macroFrame = new JFrame(Settings.getMessage("CreateMacro"));
        macroFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        JButton[] buttons = { new JButton(Settings.getMessage("Record")),
                new JButton(Settings.getMessage("FinishRecording")), new JButton(Settings.getMessage("CANCEL")) };

        for (JButton button : buttons) {
            button.setFocusPainted(false);
            button.setOpaque(true);
            button.setBackground(Color.gray);
            panel.add(button);
        }

        buttons[0].addActionListener(ex -> {
            if (!applying) {
                try {
                    applying = true;
                    target.getImage().recordMacro();
                    // This fixes needing to click to get the change to apply.
                    target.repaint();
                    buttons[0].setBackground(Color.red);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

        buttons[1].addActionListener(ex -> {
            if (applying) {
                try {
                    applying = false;
                    target.getImage().saveMacro();
                    buttons[0].setBackground(Color.gray);
                    macroFrame.dispose();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });

        buttons[2].addActionListener(ex -> {
            applying = false;
            buttons[0].setBackground(Color.gray);
            macroFrame.dispose();
        });

        macroFrame.getContentPane().add(panel);
        macroFrame.pack();
        macroFrame.setResizable(false);
        macroFrame.setLocationRelativeTo(null);
        macroFrame.setVisible(true);
    }

    /**
     * A method to enable the menu bars when there is an image present.
     */
    public void imageExists() {
        for (int i = 1; i < allActions.length; i++) {
            allActions[i].setUsable();
        }
    }

    /**
     * A method to disable the menu bars when these is no image present.
     */
    public void imageDoesNotExist() {
        for (int i = 1; i < allActions.length; i++) {
            allActions[i].setUnusable();
        }
    }

    /**
     * A method to disable this menu
     */
    public void setUnusable() {
        settingsMenu.setForeground(Color.gray);
        settingsMenu.setEnabled(false);
    }

    /**
     * <p>
     * Create a menu contianing the list of Settings actions.
     * </p>
     * 
     * @return The Settings menu UI element.
     */
    public JMenu createMenu() {
        JMenu settingsMenu = new JMenu("Settings");
        settingsMenu.setForeground(Color.black);
        for (Action action : actions) {
            settingsMenu.add(new JMenuItem(action));
        }
        SettingsAction.settingsMenu = settingsMenu;
        return settingsMenu;
    }

    /**
     * <p>
     * Create an email action to send feedback to the developers.
     * </p>
     * 
     */
    public class MailAction extends ImageAction {
        /**
         * The okay button to send the bug report.
         */
        JButton okay = new JButton(Settings.getMessage("OK"));
        /**
         * The cancel button to cancel reporting the bug
         */
        JButton cancel = new JButton(Settings.getMessage("CANCEL"));
        /**
         * The subject of the bug report.
         */
        JTextField subject;
        /**
         * The details of the bug report.
         */
        JTextArea report;
        // Create a key listener to limit the number of characters in the report
        KeyListener inputListener = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (report.getText().length() > 1000) {
                    report.setText(report.getText().substring(0, 1000));
                }
                if (subject.getText().length() > 50) {
                    report.setText(report.getText().substring(0, 50));
                }
                if (report.getText().length() == 0 || subject.getText().length() == 0)
                    okay.setEnabled(false);
                else
                    okay.setEnabled(true);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        };

        /**
         * Constructor for the MailAction class
         * 
         * @param name     The name of the action
         * @param icon     The icon for the action
         * @param desc     The description of the action
         * @param mnemonic The mnemonic for the action
         */
        MailAction(String name, ImageIcon icon, String desc, Integer mnemonic) {
            super(name, icon, desc, mnemonic);
        }

        /**
         * Shows a dialog box for inputting feedback to the developers.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            JLabel title;
            JPanel jp = new JPanel(null);
            JPanel optionPanel = new JPanel();
            JPanel subjectPanel = new JPanel();
            JPanel reportPanel = new JPanel();

            optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
            subjectPanel.setLayout(new BoxLayout(subjectPanel, BoxLayout.X_AXIS));
            reportPanel.setLayout(new BorderLayout());
            BorderLayout layout = new BorderLayout();
            layout.setVgap(10);
            jp.setLayout(layout);
            jp.setPreferredSize(new Dimension(400, 200));
            // optionPanel.setPreferredSize(new Dimension(400, 200));

            title = new JLabel("<html><u><center>" + Settings.getMessage("ReportProblem") + "</center></u></html>",
                    SwingConstants.CENTER);
            title.setFont(new Font("Serif", Font.BOLD, 40));
            // title.setBounds(80, 0, 300, 50);

            subjectPanel.add(createLabel(Settings.getMessage("Subject") + ":"));
            subjectPanel.setMaximumSize(new Dimension(400, 30));
            subjectPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            subject = new JTextField();
            subject.addKeyListener(inputListener);
            subjectPanel.add(subject);

            reportPanel.setMaximumSize(new Dimension(400, 100));
            reportPanel.add(createLabel(Settings.getMessage("ReportProblemPrompt") + ":"), BorderLayout.NORTH);
            reportPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            report = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(report);
            // report.setRows(10);
            report.setLineWrap(true);
            report.setWrapStyleWord(true);
            report.addKeyListener(inputListener);
            reportPanel.add(scrollPane, BorderLayout.CENTER);

            optionPanel.add(subjectPanel);
            optionPanel.add(reportPanel);
            JLabel message = createLabel(
                    "<html><div text-align: center>" + Settings.getMessage("ReportProblemMessage") + "</div></html>");
            message.setPreferredSize(new Dimension(400, 30));
            message.setMinimumSize(new Dimension(400, 30));
            message.setMaximumSize(new Dimension(400, 30));
            message.setAlignmentX(Component.LEFT_ALIGNMENT);
            // message.setOpaque(true);
            // message.setBackground(Color.BLUE);
            message.setHorizontalAlignment(SwingConstants.CENTER);
            optionPanel.add(message);
            jp.add(title, BorderLayout.PAGE_START);
            jp.add(optionPanel, BorderLayout.LINE_START);

            okay.setEnabled(false);
            okay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane pane = getOptionPane((JComponent) e.getSource());
                    pane.setValue(okay);
                }
            });
            cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane pane = getOptionPane((JComponent) e.getSource());
                    pane.setValue(cancel);
                }
            });
            okay.setText(Settings.getMessage("OK"));
            cancel.setText(Settings.getMessage("CANCEL"));
            Object[] okAndCancel = { okay, cancel };
            int option = JOptionPane.showOptionDialog(null, jp, Settings.getMessage("ReportBug"),
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, okAndCancel, okAndCancel[0]);

            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                // Ok was pressed
                EMail.sendMail("hamishdudley3011+PPS-teamP@gmail.com", subject.getText(), report.getText());
            }
        }

        /**
         * <p>
         * Recursively find the JOptionPane that is the parent of the given component
         * </p>
         * 
         * @param parent The component to find the parent JOptionPane of
         * @return The JOptionPane that is the parent of the given component
         */
        private JOptionPane getOptionPane(JComponent parent) {
            JOptionPane pane = null;
            if (!(parent instanceof JOptionPane)) {
                pane = getOptionPane((JComponent) parent.getParent());
            } else {
                pane = (JOptionPane) parent;
            }
            return pane;
        }

        /**
         * <p>
         * Creates a JLabel with the given text
         * </p>
         * 
         * @param text The text to be displayed on the label
         * @return The JLabel with the given text
         */
        private JLabel createLabel(String text) {
            JLabel message = new JLabel(text);
            // message.setBounds(60, 90, 350, 25);
            message.setFont(new Font("Serif", Font.PLAIN, 15));
            message.setAlignmentX(Component.LEFT_ALIGNMENT);
            return message;
        }
    }
}