package cosc202.andie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.geom.Rectangle2D;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.swing.*;

/**
 * <p>
 * A static class to handle application wide settings such as locale settings
 * and handling the applicationn window.
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class Settings extends AbstractAction {
    /** A list of actions for the Settings menu. */
    private static JMenu settingsMenu;
    /** A list ofactions to be displayed under the settngs menu */
    protected static ArrayList<Action> actions;
    /**
     * The message bundle that is used to display text in the appropriate language
     */
    public static ResourceBundle bundle = null;// ResourceBundle.getBundle("MessageBundle");
    /**
     * All of the actions added to the menu bar to be updated when language is
     * changed
     */
    private static AndieAction[] allActions;
    /**
     * The main Frame of the application so that it can be resized and text updated
     * on language change.
     */
    private static JFrame frame, conFrame;
    /** The users current preferences. */
    private static Preferences prefs = Preferences.userNodeForPackage(Andie.class);
    /** The languages available */
    private static String[] languages = { "English", "Maori", "Italian" };
    /** The codes of available languages */
    private static String[] languageCodes = { "en", "mi", "it" };
    /**
     * The countries available for each language. ie The first array represents the
     * countries available for the first language...
     */
    private static String[][] availableCountries = { { "New Zealand", "United States" }, { "New Zealand" },
            { "Italy", "Switzerland" } };
    /** All countries supported over all languages */
    private static String[] allCountries = { "New Zealand", "United States", "Italy", "Switzerland" };
    /** The codes of all supported countries over all languages */
    private static String[] countryCodes = { "NZ", "US", "IT", "SW" };
    /**
     * The currently selected country. Initialised to -1 to check if the current
     * preference is a country that isnt supported. This could happen from old or
     * other versions of ANDIE
     */
    private static int selectedCountry = -1; // -1 to check if prefs is set to a language that isn't supported
    /**
     * The currently selected country. Initialised to -1 to check if the current
     * preference is a country that isnt supported. This could happen from old or
     * other versions of ANDIE
     */
    private static int selectedLanguage = -1; // this is done in updateLocale
    /** The button group of languages */
    private static ButtonGroup bgLanguages;
    /** The button group of countries */
    private static ButtonGroup bgCountries;
    /** The buttons in the languages button group */
    private static ArrayList<JRadioButton> languageButtons;
    /** The buttons in the countries button group */
    private static ArrayList<JRadioButton> countryButtons;
    /** A flag to keep track if ANDIE is drawing */
    private static boolean isDrawing;
    /** A flag to keep track if ANDIE is selecting */
    private static boolean isSelecting;
    /** A flag to keep track if ANDIE is erasing */
    private static boolean isErasing;
    /** A variable to keep track of the current pen colour */
    private static Color penColor;
    /** The theme colour */
    private static Color colorTheme = new Color(12, 26, 64);
    /** The thickness of the pen and theme number */
    private static int strokeSize, theme;
    /** The variable representing the orientation of the tool bar */
    private static int tbOrientation = 1;
    /**
     * A flag to keep track of if ANDIE is cropping and if the toolbar is vertical
     */
    private static boolean isCropping, tbVertical;
    /** The tool bar */
    private static ToolBar tb;
    /** The panels that make up the elements of ANDIE */
    private static JPanel tbBorder, panel1, panel2, panel3, panel4, tab1Panel, tab3Image;
    /** The buttons on the toolbar */
    private static JRadioButton[] buttons;
    /** The labels on the toolbar */
    private static JLabel center, center2;
    /** The tabbed pane */
    private static JTabbedPane tabbedPane;
    /** The labels for macro settings */
    private static JLabel[] currMacLabel = { new JLabel("Default"), new JLabel("Default"), new JLabel("Default"),
            new JLabel("Default") };
    /** The theme icon */
    private static ImageIcon themeIcon;
    /**
     * An item listener to change the countries to only supported countries when a
     * different language is selected
     */
    private static ItemListener languageListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            // System.out.println(e.getItem());
            // System.out.println(e.getSource());
            for (int i = 0; i < languageButtons.size(); i++) { // Find which button is now selected
                if (e.getStateChange() == ItemEvent.SELECTED && e.getItem() == languageButtons.get(i)) {
                    boolean newSelectedRequired = false;
                    // System.out.println(languageButtons.get(i).getText());
                    // Sets all of the buttons to invisble so that we can show only the countries
                    // relavent to this language
                    for (JRadioButton button : countryButtons) {
                        button.setVisible(false);
                        if (contains(availableCountries[i], button.getText())) {
                            // if the coutry is in the list of countries for this language
                            button.setVisible(true);
                        } else {
                            button.setVisible(false);
                            if (button.isSelected()) {
                                newSelectedRequired = true;
                            }
                        }
                    }
                    if (newSelectedRequired) {
                        int j = 0;
                        do {
                            if (countryButtons.get(j).isVisible()) {
                                countryButtons.get(j).setSelected(true);
                                newSelectedRequired = false;
                            }
                            j++;
                        } while (newSelectedRequired && j < countryButtons.size());
                    }
                    // // Shows the countries relavent to this language
                    // for (int c = allCountries.length - 1; c >= 0; c--) {
                    // for (String text : availableCountries[i]) {
                    // if (text.equals(allCountries[c])) {
                    // countryButtons.get(c).setVisible(true);
                    // // bgCountries.setSelected(countryButtons.get(c).getModel(), true);
                    // }
                    // }
                    // }
                    selectedLanguage = i;
                }
            }
        }
    };
    /**
     * An item listener to change the selected country when a different country is
     * pressed.
     */
    private static ItemListener countryListener = new ItemListener() {
        @Override
        public void itemStateChanged(ItemEvent e) {
            for (int i = 0; i < countryButtons.size(); i++) {
                if (e.getSource() == countryButtons.get(i) && e.getStateChange() == ItemEvent.SELECTED) {
                    selectedCountry = i;
                    // System.out.println(allCountries[i]);
                }
            }
        }

    };

    /**
     * A default constructor for Settings. Used in keyboard shortcuts.
     */
    Settings() {
    }

    /**
     * <p>
     * Constructor for Settings that displays in the menu.
     * </p>
     * 
     * <p>
     * To construct Settings you provide the information needed to integrate
     * it with the interface.
     * </p>
     * 
     * @param name     The name of the action (ignored if null).
     * @param icon     An icon to use to represent the action (ignored if null).
     * @param desc     A brief description of the action (ignored if null).
     * @param mnemonic A mnemonic key to use as a shortcut (ignored if null).
     */
    Settings(String name, ImageIcon icon, String desc, Integer mnemonic, ArrayList<Action> actionsIn,
            AndieAction[] allActionsIn, JFrame frameIn) {
        super(name, icon);
        if (desc != null) {
            putValue(SHORT_DESCRIPTION, desc);
        }
        if (mnemonic != null) {
            putValue(MNEMONIC_KEY, mnemonic);
        }
        actions = actionsIn;
        allActions = allActionsIn;
        frame = frameIn;
        // Reset the languages
        // prefs.put("language", "en");
        // prefs.put("language", "NZ");
        // System.out.println(prefs.get("language", "en") + "_" + prefs.get("country",
        // "NZ"));
        boolean found = false;
        for (int i = 0; i < languageCodes.length; i++) {
            for (String country : availableCountries[i]) {
                for (int x = 0; x < allCountries.length; x++) {
                    if (allCountries[x].equals(country)) {
                        // Finds the country and language codes supported by ANDIE
                        if (prefs.get("country", "NZ").equals(countryCodes[x]) && prefs.get("language", "en")
                                .equals(languageCodes[i])) {
                            found = true;
                            // System.out.println("found");
                        }
                        // System.out.println(languageCodes[i] + "_" + countryCodes[x]);
                    }
                }
                // System.out.println(languageCodes[i] + "_" + country);
            }

        }
        if (!found) {
            prefs.put("language", "en");
            prefs.put("country", "NZ");
        }
        Locale.setDefault(new Locale(prefs.get("language", "en"), prefs.get("country", "NZ")));
        bundle = ResourceBundle.getBundle("MessageBundle");
    }

    /**
     * Sets the Settings menu that the settings options are in
     * 
     * @param menu The menu that the settings are in
     */
    public static void setSettingsMenu(JMenu menu) {
        settingsMenu = menu;
    }

    /**
     * The method called when the settings button in the menu is pressed
     * This method constructs the panel which displays settings and allows the
     * settings to be changed when save is pressed
     * 
     * @param e The action event that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        bgLanguages = new ButtonGroup();
        bgCountries = new ButtonGroup();
        languageButtons = new ArrayList<>();
        countryButtons = new ArrayList<>();
        JLabel title;
        JPanel jp = new JPanel(null);
        JPanel optionPanel = new JPanel();
        JPanel countrySelectionPanel = new JPanel();
        JPanel languageSelectionPanel = new JPanel();

        optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
        countrySelectionPanel.setLayout(new BoxLayout(countrySelectionPanel, BoxLayout.X_AXIS));
        languageSelectionPanel.setLayout(new BoxLayout(languageSelectionPanel, BoxLayout.X_AXIS));
        jp.setLayout(new BorderLayout());
        jp.setPreferredSize(new Dimension(400, 200));
        // optionPanel.setPreferredSize(new Dimension(400, 200));

        // Determine the radius - ask the user.
        title = new JLabel("<html><u>" + Settings.getMessage("SettingsAction") + "</u></html>",
                SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 40));
        // title.setBounds(80, 0, 300, 50);

        languageSelectionPanel.add(createLabel(Settings.getMessage("SettingsSelectLanguage")));
        languageSelectionPanel.setMaximumSize(new Dimension(400, 50));
        createRadioButtons(languageSelectionPanel, bgLanguages, languageButtons, languageListener, languages);

        countrySelectionPanel.setMaximumSize(new Dimension(400, 50));
        countrySelectionPanel.add(createLabel(Settings.getMessage("SettingsSelectCountry")));
        createRadioButtons(countrySelectionPanel, bgCountries, countryButtons, countryListener, allCountries);

        bgLanguages.setSelected(languageButtons.get(selectedLanguage).getModel(), true);
        bgCountries.setSelected(countryButtons.get(selectedCountry).getModel(), true);

        countrySelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        languageSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // title.setOpaque(true);
        // title.setBackground(Color.BLUE);
        // languageSelectionPanel.setBackground(Color.CYAN);
        // countrySelectionPanel.setBackground(Color.GREEN);
        // optionPanel.setBackground(Color.RED);

        optionPanel.add(languageSelectionPanel);
        optionPanel.add(countrySelectionPanel);
        JLabel message = createLabel(
                "<html><div text-align: center>" + Settings.getMessage("SettingsSaveWarning") + "</div></html>");
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

        String[] okAndCancel = { Settings.getMessage("OK"), Settings.getMessage("CANCEL") };
        int option = JOptionPane.showOptionDialog(null, jp, Settings.getMessage("SettingsAction"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, okAndCancel, okAndCancel[0]);

        if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
            return;
        } else if (option == JOptionPane.OK_OPTION) {
            // System.out.println("Settings action called");
            prefs.put("language", languageCodes[selectedLanguage]);
            prefs.put("country", countryCodes[selectedCountry]);
            // if (prefs.get("Country", "NZ").equals("NZ")) {
            // prefs.put("country", "US");
            // } else
            // prefs.put("country", "NZ");
            // System.out.println(prefs.get("language", "failed Language"));
            // System.out.println(prefs.get("country", "failed Country"));
            updateLocale();
            bundle = ResourceBundle.getBundle("MessageBundle");
            updateUI();
        }
    }

    /**
     * A method to update the UI. This changes all of the text in the menus to
     * reflect the current language preference. It also updates the application
     * title and then repacks the frame to ensure that all menu items can still be
     * seen.
     */
    public static void updateUI() {
        // System.out.println(allActions);
        for (AndieAction action : allActions) {
            // System.out.println(action.getItem(0));
            for (int i = 0; i < action.getItemCount(); i++) {
                action.setItemText(i, bundle.getString(action.getItem(i)));
                action.setItemPrompt(i, bundle.getString(action.getItem(i) + "Prompt"));
            }
            action.setTitleText(bundle.getString(action.getClass().getSimpleName()));
        }
        settingsMenu.setText(bundle.getString("SettingsAction"));
        settingsMenu.getItem(0).setText(bundle.getString("LanguageAction"));
        settingsMenu.getItem(0).getAction().putValue("ShortDescription", bundle.getString("SettingsActionPrompt"));
        frame.setTitle(Settings.getMessage("ANDIE"));
        frame.remove(tbBorder);

        configureToolBar(tbOrientation);
        String[] menuItems = { "ToolBar", "CreateMacro", "ApplyMacro", "ReportBug" };
        for (int i = 0; i < menuItems.length; i++) {
            settingsMenu.getItem(i + 1).setText(bundle.getString(menuItems[i]));
            settingsMenu.getItem(i + 1).getAction().putValue("ShortDescription",
                    bundle.getString(menuItems[i] + "Prompt"));
        }
        if (!ImageAction.target.getImage().hasImage()) {
            settingsMenu.getItem(3).setEnabled(false);
        }
        if (Andie.searchBar != null) {
            Andie.searchBar.populateButtonMap();
            Andie.searchBar.revalidate();
            Andie.searchBar.repaint();
            Andie.searchBar.onAction();
        }

        packFrame();
    }

    /**
     * A method that sets the default toolbar for ANDIE
     */
    public static void setDefaultToolBar() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(12, 26, 64));
        tbBorder = panel;

        ToolBar toolBar = new ToolBar(frame);
        toolBar.setPreferredSize(new Dimension(660, 80));
        tb = toolBar;
        panel.add(tb);
        frame.add(panel, BorderLayout.NORTH);

        // Create a FrameListener instance
        FrameListener fl = new FrameListener();
        tbBorder.addComponentListener(fl);
        frame.addWindowStateListener(fl);
        fl.componentResized(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));

    }

    /**
     * A method that sets the toolbar to the specified orientation
     * 1) is for top
     * 2) is for right
     * 3) is for left
     * 4) is for bottom
     * 
     * @param pos The orientation of the toolbar
     */
    public static void configureToolBar(int pos) {

        ToolBar toolBar = new ToolBar(frame);
        toolBar.setPreferredSize(new Dimension(660, 80));
        tb = toolBar;

        panel1 = new JPanel();
        panel1.setBackground(colorTheme);
        panel1.setPreferredSize(new Dimension(110, 5));

        panel2 = new JPanel();
        panel2.setBackground(colorTheme);
        panel2.setPreferredSize(new Dimension(5, frame.getHeight()));

        panel3 = new JPanel();
        panel3.setBackground(colorTheme);
        panel3.setPreferredSize(new Dimension(5, frame.getHeight()));

        panel4 = new JPanel();
        panel4.setBackground(colorTheme);
        panel4.setPreferredSize(new Dimension(110, 15));

        if (pos == 1) {
            tbVertical = false;
            frame.remove(tbBorder);
            JPanel panel = new JPanel();
            panel.setBackground(colorTheme);
            tbBorder = panel;
            tbBorder.remove(tb);
            tb.setHorizontal();
            tb.setPreferredSize(new Dimension(700, 80));
            tbBorder.add(tb);
            frame.add(tbBorder, BorderLayout.NORTH);
        }
        if (pos == 4) {
            tbVertical = false;
            frame.remove(tbBorder);
            JPanel panel = new JPanel();
            panel.setBackground(colorTheme);
            tbBorder = panel;
            tbBorder.remove(tb);
            tb.setHorizontal();
            tb.setPreferredSize(new Dimension(700, 80));
            tbBorder.add(tb);
            frame.add(tbBorder, BorderLayout.SOUTH);
        }
        if (pos == 2) {
            tbVertical = true;
            frame.remove(tbBorder);
            JPanel panel = new JPanel();
            panel.setBackground(colorTheme);
            tbBorder = panel;
            tbBorder.setLayout(new BorderLayout());
            tb.setLayout(null);
            tb.setVertical();
            tb.setPreferredSize(new Dimension(100, 580));
            tbBorder.remove(tb);

            tbBorder.add(tb, BorderLayout.CENTER);
            tbBorder.add(panel1, BorderLayout.NORTH);
            tbBorder.add(panel2, BorderLayout.WEST);
            tbBorder.add(panel3, BorderLayout.EAST);
            tbBorder.add(panel4, BorderLayout.SOUTH);

            frame.add(tbBorder, BorderLayout.WEST);
        }
        if (pos == 3) {
            tbVertical = true;
            frame.remove(tbBorder);
            JPanel panel = new JPanel();
            panel.setBackground(colorTheme);
            tbBorder = panel;
            tbBorder.setLayout(new BorderLayout());
            tb.setLayout(null);
            tb.setVertical();
            tb.setPreferredSize(new Dimension(100, 580));
            tbBorder.remove(tb);

            tbBorder.add(tb, BorderLayout.CENTER);
            tbBorder.add(panel1, BorderLayout.NORTH);
            tbBorder.add(panel2, BorderLayout.WEST);
            tbBorder.add(panel3, BorderLayout.EAST);
            tbBorder.add(panel4, BorderLayout.SOUTH);

            frame.add(tbBorder, BorderLayout.EAST);
        }

        FrameListener fl = new FrameListener();
        tbBorder.addComponentListener(fl);
        frame.addWindowStateListener(fl);
        fl.componentResized(new ComponentEvent(frame, ComponentEvent.COMPONENT_RESIZED));

        if (ImageAction.getTarget().getImage().hasImage()) {
            tb.setTBUsable();
        } else {
            tb.setTBUnusable();
        }
        packFrame();
        frame.repaint();
        frame.revalidate();
    }

    /**
     * A method that creates the toolbar configuration frame
     */
    public static void createTBFrame() {
        JRadioButton[] b = { new JRadioButton(getMessage("Top")),
                new JRadioButton(getMessage("Left")),
                new JRadioButton(getMessage("Right")),
                new JRadioButton(getMessage("Bottom")) };
        buttons = b;
        JButton[] sc = { new JButton(getMessage("Save")), new JButton(getMessage("CANCEL")) };
        ButtonGroup buttonGroup = new ButtonGroup();

        tabbedPane = new JTabbedPane();
        tab1Panel = new JPanel();
        tab1Panel.setLayout(new BorderLayout());

        conFrame = new JFrame(getMessage("ToolBar"));
        conFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        conFrame.setLayout(new BorderLayout());

        JPanel northPanel = new JPanel();
        northPanel.setBackground(Color.gray);
        JPanel southPanel = new JPanel();
        southPanel.setBackground(Color.gray);
        buttons[tbOrientation - 1].setSelected(true);

        for (JRadioButton button : buttons) {
            button.setFocusable(false);
            button.setOpaque(true);
            button.setForeground(Color.white);
            button.setBackground(Color.gray);
        }

        for (int i = 0; i < buttons.length; i++) {
            int index = i + 1;
            northPanel.add(buttons[i], BorderLayout.NORTH);
            buttons[i].addActionListener(ex -> {
                buttons[index - 1].setSelected(true);
                Settings.setToolBar(index);
                updateTBImage();
            });
            buttonGroup.add(buttons[i]);
        }

        for (JButton button : sc) {
            button.setFocusable(false);
            button.setFocusPainted(false);
            southPanel.add(button, BorderLayout.SOUTH);
        }

        sc[0].addActionListener(ex -> {
            Settings.configureToolBar(tbOrientation);
            conFrame.dispose();
        });

        sc[1].addActionListener(ex -> {
            conFrame.dispose();
        });

        tab1Panel.add(northPanel, BorderLayout.NORTH);
        tab1Panel.add(southPanel, BorderLayout.SOUTH);

        tabbedPane.addTab(getMessage("Configuration"), tab1Panel);
        customiseTB();
        setTheme();

        conFrame.getContentPane().add(tabbedPane);
        updateTBImage();
        conFrame.pack();
        conFrame.setResizable(false);
        conFrame.setLocationRelativeTo(null);
        conFrame.setVisible(true);
    }

    /**
     * Getter method which gets the settingsMenu
     * 
     * @return The language settings method
     */
    public static JMenu getSettingsMenu() {
        return settingsMenu;
    }

    /**
     * Getter method which gets the ToolBar
     * 
     * @return the Toolbar in Andie
     */
    public static ToolBar getTB() {
        return tb;
    }

    /**
     * A method that applys the customisations to the toolbar
     */
    public static void customiseTB() {
        JPanel tab2Panel = new JPanel();
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel center = new JPanel();
        tab2Panel.setLayout(new BorderLayout());
        north.setLayout(null);

        center.setLayout(null);

        JLabel[] labels = { new JLabel(getMessage("Macro1")), new JLabel(getMessage("Macro2")),
                new JLabel(getMessage("Macro3")),
                new JLabel(getMessage("Macro4")) };
        JButton[] buttons = { new JButton(getMessage("Reset")), new JButton(getMessage("New")),
                new JButton(getMessage("Reset")), new JButton(getMessage("New")),
                new JButton(getMessage("Reset")), new JButton(getMessage("New")), new JButton(getMessage("Reset")),
                new JButton(getMessage("New")),
                new JButton(getMessage("Save")), new JButton(getMessage("CANCEL")) };
        JLabel title = new JLabel("<html><u><center>" + getMessage("CustomiseTitle") + "</center></u></html>");

        boolean[] mc = ToolBarMacros.getMacroCustom();
        for (int i = 0; i < mc.length; i++) {
            if (mc[i]) {
                currMacLabel[i].setText(getMessage("Custom"));
            } else {
                currMacLabel[i].setText(getMessage("Default"));
            }

        }

        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.white);
        title.setBounds(0, 0, 620, 100);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        north.setPreferredSize(new Dimension(550, 100));
        north.add(title);

        int xaxis = 0;
        int yaxis = 5;
        int count = 1;

        for (JLabel label : labels) {
            label.setFont(new Font("Arial", Font.PLAIN, 40));
            label.setForeground(Color.white);
            label.setBounds(xaxis, yaxis, 310, 50);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            center.add(label);
            xaxis += 310;
            if (count == 2) {
                xaxis = 0;
                yaxis = 175;
                count = 1;
            } else {
                count++;
            }
        }

        // Buttons
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                buttons[0].setBounds(54, 135, 95, 30);
                buttons[1].setBounds(159, 135, 95, 30);

                center.add(buttons[0]);
                center.add(buttons[1]);
                buttons[2].setBounds(54, 310, 95, 30);
                buttons[3].setBounds(159, 310, 95, 30);
                center.add(buttons[2]);
                center.add(buttons[3]);
            } else {
                buttons[4].setBounds(365, 135, 95, 30);
                buttons[5].setBounds(469, 135, 95, 30);
                center.add(buttons[4]);
                center.add(buttons[5]);
                buttons[6].setBounds(365, 310, 95, 30);
                buttons[7].setBounds(469, 310, 95, 30);
                center.add(buttons[6]);
                center.add(buttons[7]);
            }
        }

        south.add(buttons[8]);
        south.add(buttons[9]);

        buttons[0].addActionListener(ex -> {
            ToolBarMacros.setDefaultMacro1();
            currMacLabel[0].setText(getMessage("Default"));
        });
        buttons[2].addActionListener(ex -> {
            ToolBarMacros.setDefaultMacro2();
            currMacLabel[1].setText(getMessage("Default"));
        });
        buttons[4].addActionListener(ex -> {
            ToolBarMacros.setDefaultMacro3();
            currMacLabel[2].setText(getMessage("Default"));
        });
        buttons[6].addActionListener(ex -> {
            ToolBarMacros.setDefaultMacro4();
            currMacLabel[3].setText(getMessage("Default"));
        });

        buttons[1].addActionListener(ex -> {
            ToolBarMacros.setMacro1();
            currMacLabel[0].setText(getMessage("Custom"));
        });
        buttons[3].addActionListener(ex -> {
            ToolBarMacros.setMacro2();
            currMacLabel[0].setText(getMessage("Custom"));
        });
        buttons[5].addActionListener(ex -> {
            ToolBarMacros.setMacro3();
            currMacLabel[0].setText(getMessage("Custom"));
        });
        buttons[7].addActionListener(ex -> {
            ToolBarMacros.setMacro4();
            currMacLabel[0].setText(getMessage("Custom"));
        });

        buttons[8].addActionListener(ex -> {
            conFrame.dispose();
        });
        buttons[9].addActionListener(ex -> {
            conFrame.dispose();
        });

        currMacLabel[0].setBounds(102, 75, 100, 30);
        currMacLabel[0].setFont(new Font("Arial", Font.PLAIN, 20));
        currMacLabel[0].setOpaque(true);
        currMacLabel[0].setForeground(Color.white);
        currMacLabel[0].setBackground(new Color(115, 115, 115));
        currMacLabel[0].setHorizontalAlignment(SwingConstants.CENTER);
        center.add(currMacLabel[0]);

        currMacLabel[1].setBounds(410, 75, 100, 30);
        currMacLabel[1].setFont(new Font("Arial", Font.PLAIN, 20));
        currMacLabel[1].setOpaque(true);
        currMacLabel[1].setForeground(Color.white);
        currMacLabel[1].setBackground(new Color(115, 115, 115));
        currMacLabel[1].setHorizontalAlignment(SwingConstants.CENTER);
        center.add(currMacLabel[1]);

        currMacLabel[2].setBounds(102, 250, 100, 30);
        currMacLabel[2].setFont(new Font("Arial", Font.PLAIN, 20));
        currMacLabel[2].setOpaque(true);
        currMacLabel[2].setForeground(Color.white);
        currMacLabel[2].setBackground(new Color(115, 115, 115));
        currMacLabel[2].setHorizontalAlignment(SwingConstants.CENTER);
        center.add(currMacLabel[2]);

        currMacLabel[3].setBounds(410, 250, 100, 30);
        currMacLabel[3].setFont(new Font("Arial", Font.PLAIN, 20));
        currMacLabel[3].setOpaque(true);
        currMacLabel[3].setForeground(Color.white);
        currMacLabel[3].setBackground(new Color(115, 115, 115));
        currMacLabel[3].setHorizontalAlignment(SwingConstants.CENTER);
        center.add(currMacLabel[3]);

        north.setBackground(Color.gray);
        center.setBackground(new Color(27, 28, 28));
        south.setBackground(Color.gray);
        tab2Panel.add(north, BorderLayout.NORTH);
        tab2Panel.add(center, BorderLayout.CENTER);
        tab2Panel.add(south, BorderLayout.SOUTH);

        tabbedPane.add(getMessage("Customise"), tab2Panel);
    }

    /**
     * Sets the cursor to a wait cursor
     */
    public static void setWaitCursor() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        ImageAction.target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
    }

    /**
     * Sets the cursor to a wait cursor
     */
    public static void setDefaultCursor() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        ImageAction.target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

    }

    /**
     * Sets the cursor to a Axis cursor
     */
    public static void setAxisCursor() {
        frame.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        ImageAction.target.getParent().setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

    }

    /**
     * Sets the theme of the application
     */
    public static void setTheme() {
        JPanel tab3Panel = new JPanel();
        tab3Panel.setLayout(new BorderLayout());

        JButton[] themes = { new JButton(getMessage("Navy")), new JButton(getMessage("Gray")),
                new JButton(getMessage("Black")), new JButton(getMessage("White")) };
        JPanel east = new JPanel();
        east.setLayout(null);
        east.setBackground(Color.gray);
        east.setPreferredSize(new Dimension(100, 300));
        int y = 75;
        for (JButton button : themes) {
            button.setBounds(10, y, 80, 30);
            east.add(button);
            y += 75;
        }

        themes[0].addActionListener(ex -> {
            theme = 1;
            Theme.setTheme(1);
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/Navy.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            themeIcon = new ImageIcon(image);
            tab3Image.remove(center2);
            center2 = new JLabel(themeIcon);
            tab3Image.add(center2);
            tab3Image.repaint();
            tab3Image.revalidate();
        });
        themes[1].addActionListener(ex -> {
            theme = 2;
            Theme.setTheme(2);
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/Gray.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            themeIcon = new ImageIcon(image);
            tab3Image.remove(center2);
            center2 = new JLabel(themeIcon);
            tab3Image.add(center2);
            tab3Image.repaint();
            tab3Image.revalidate();
        });
        themes[2].addActionListener(ex -> {
            theme = 3;
            Theme.setTheme(3);
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/Black.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            themeIcon = new ImageIcon(image);
            tab3Image.remove(center2);
            center2 = new JLabel(themeIcon);
            tab3Image.add(center2);
            tab3Image.repaint();
            tab3Image.revalidate();
        });
        themes[3].addActionListener(ex -> {
            theme = 4;
            Theme.setTheme(4);
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/White.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            themeIcon = new ImageIcon(image);
            tab3Image.remove(center2);
            center2 = new JLabel(themeIcon);
            tab3Image.add(center2);
            tab3Image.repaint();
            tab3Image.revalidate();
        });

        if (center2 == null) {
            tab3Image = new JPanel();
            URL image;
            if (Theme.getTheme() == 1) {
                image = Settings.class.getResource("/Navy.png");
            } else if (Theme.getTheme() == 2) {
                image = Settings.class.getResource("/Gray.png");
            } else if (Theme.getTheme() == 3) {
                image = Settings.class.getResource("/Black.png");
            } else {
                image = Settings.class.getResource("/White.png");
            }
            ImageIcon imageIcon = new ImageIcon(image);
            Image currImage = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            themeIcon = new ImageIcon(currImage);
            center2 = new JLabel(themeIcon);
            tab3Image.add(center2);
        } else {
            tab3Image.add(center2);
        }

        east.setLayout(null);

        JButton[] saveAndCancel = { new JButton(getMessage("Save")), new JButton(getMessage("CANCEL")) };
        JPanel south = new JPanel();
        south.setPreferredSize(new Dimension(550, 100));
        south.setBackground(Color.gray);
        south.add(saveAndCancel[0]);
        south.add(saveAndCancel[1]);

        saveAndCancel[0].addActionListener(ex -> {
            updateLookAndFeel(Theme.getTheme());
            conFrame.dispose();
        });
        saveAndCancel[1].addActionListener(ex -> {
            conFrame.dispose();
        });

        tab3Panel.add(tab3Image, BorderLayout.CENTER);
        tab3Panel.add(east, BorderLayout.EAST);
        tab3Panel.add(south, BorderLayout.SOUTH);

        tabbedPane.add(getMessage("Theme"), tab3Panel);
    }

    /**
     * Sets the image which is displayed on the tabbed pane to show the user a
     * preview of what the toolbar will look like.
     */
    public static void updateTBImage() {
        if (center != null) {
            tab1Panel.remove(center);
        }

        if (tbOrientation == 1) {
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/NorthTB.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            ImageIcon scaledIcon = new ImageIcon(image);
            center = new JLabel(scaledIcon);
            center.setBorder(BorderFactory.createLineBorder(new Color(27, 28, 28), 20));
            tab1Panel.add(center, BorderLayout.CENTER);
            tab1Panel.repaint();
            tab1Panel.revalidate();
            conFrame.repaint();
            conFrame.revalidate();
        }
        if (tbOrientation == 2) {
            center.removeAll();
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/WestTB.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            ImageIcon scaledIcon = new ImageIcon(image);
            center = new JLabel(scaledIcon);
            center.setBorder(BorderFactory.createLineBorder(new Color(27, 28, 28), 20));
            tab1Panel.add(center, BorderLayout.CENTER);
            tab1Panel.repaint();
            tab1Panel.revalidate();
            conFrame.repaint();
            conFrame.revalidate();
        }
        if (tbOrientation == 3) {
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/EastTB.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            ImageIcon scaledIcon = new ImageIcon(image);
            center = new JLabel(scaledIcon);
            center.setBorder(BorderFactory.createLineBorder(new Color(27, 28, 28), 20));
            tab1Panel.add(center, BorderLayout.CENTER);
            tab1Panel.repaint();
            tab1Panel.revalidate();
            conFrame.repaint();
            conFrame.revalidate();
        }
        if (tbOrientation == 4) {
            ImageIcon imageIcon = new ImageIcon(Settings.class.getResource("/SouthTB.png"));
            Image image = imageIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING);
            ImageIcon scaledIcon = new ImageIcon(image);
            center = new JLabel(scaledIcon);
            center.setBorder(BorderFactory.createLineBorder(new Color(27, 28, 28), 20));
            tab1Panel.add(center, BorderLayout.CENTER);
            tab1Panel.repaint();
            tab1Panel.revalidate();
            conFrame.repaint();
            conFrame.revalidate();
        }
    }

    /**
     * The FrameListener class is used to listen for changes in the frame size and
     * state.
     */
    private static class FrameListener extends ComponentAdapter implements WindowStateListener {
        private int frameWidth;
        private int frameHeight;

        /**
         * Invoked when the frames size changes to adjust the size of the toolbar.
         */
        public void componentResized(ComponentEvent e) {
            int height = frame.getHeight();
            if (frame.getWidth() <= 710 && !tbVertical) {
                tbBorder.setLayout(new FlowLayout(FlowLayout.LEFT));
            }
            if (frame.getWidth() > 710 && !tbVertical) {
                tbBorder.setLayout(new FlowLayout(FlowLayout.CENTER));
            }
            if (tbVertical && frame.getHeight() >= 660) {
                panel1.setPreferredSize(new Dimension(110, (height - 650) / 2));
                panel4.setPreferredSize(new Dimension(110, (height - 650) / 2));
            } else if (tbVertical && frame.getHeight() < 660) {
                panel1.setPreferredSize(new Dimension(110, 5));
                panel4.setPreferredSize(new Dimension(110, 5));
            }

        }

        /**
         * Invoked when the frame is maximized to adjust the size of the toolbar.
         */
        @Override
        public void windowStateChanged(WindowEvent e) {
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH && !tbVertical) {
                frameWidth = frame.getWidth();
                tbBorder.setLayout(new FlowLayout(FlowLayout.CENTER));
            } else {
                if (frameWidth <= 710 && !tbVertical) {
                    tbBorder.setLayout(new FlowLayout(FlowLayout.LEFT));
                }
                if (frameWidth > 710 && !tbVertical) {
                    tbBorder.setLayout(new FlowLayout(FlowLayout.CENTER));
                }
            }
            if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH && tbVertical) {
                frameHeight = frame.getHeight();
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                panel1.setPreferredSize(new Dimension(110, (dim.height - 650) / 2));
                panel4.setPreferredSize(new Dimension(110, (dim.height - 650) / 2));
            } else {
                if (frameWidth >= 660 && tbVertical) {
                    panel1.setPreferredSize(new Dimension(110, (frameHeight - 650) / 2));
                    panel4.setPreferredSize(new Dimension(110, (frameHeight - 650) / 2));
                }
                if (frameWidth < 660 && tbVertical) {
                    panel1.setPreferredSize(new Dimension(110, 5));
                    panel4.setPreferredSize(new Dimension(110, 5));
                }
            }
        }
    }

    /**
     * A method to resize the screen. This also limits the application width to 50
     * pixels less than the maximum width as the application was ending up behind
     * the task bar when repacking with very large images.
     */
    public static void packFrame() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.pack();
        if (frame.getWidth() > dim.getWidth() - 50) {
            frame.setSize((int) dim.getWidth() - 50, frame.getHeight());
        }
        if (frame.getHeight() > dim.getHeight() - 50) {
            frame.setSize(frame.getWidth(), (int) dim.getHeight() - 50);
        }
        if (frame.getHeight() < (int) dim.getHeight() / 2) {
            frame.setSize(frame.getWidth(), (int) dim.getHeight() / 2);
        }
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }

    /**
     * A method to create the radio buttons used in the language settings.
     * 
     * @param panel    The panel to add the radio buttons to
     * @param bg       The button group to add the buttons to
     * @param list     The list of buttons to add
     * @param listener The listener to add to the buttons
     * @param text     The text to add to the buttons
     * @return
     */
    private JPanel createRadioButtons(JPanel panel, ButtonGroup bg, ArrayList<JRadioButton> list,
            ItemListener listener, String[] text) {
        for (String string : text) {
            JRadioButton button = new JRadioButton(string);
            button.setAlignmentX(Component.RIGHT_ALIGNMENT);
            button.addItemListener(listener);
            panel.add(button);
            bg.add(button);
            list.add(button);
        }

        return panel;
    }

    /**
     * A method to create a generic label with the given text.
     * 
     * @param text The text to add to the label
     * @return The label with the given text
     */
    private JLabel createLabel(String text) {
        JLabel message = new JLabel(text);
        // message.setBounds(60, 90, 350, 25);
        message.setFont(new Font("Serif", Font.PLAIN, 15));
        message.setAlignmentX(Component.RIGHT_ALIGNMENT);
        return message;
    }

    /**
     * A method to get text from the message bundle in the current language.
     * 
     * @param key The key of the text you want to get from the message bundle
     * @return The text from the message bundle in the appropriate language
     */
    public static String getMessage(String key) {
        if (bundle == null) {
            return "";
        }
        return bundle.getString(key);
    }

    /**
     * A method to update the language to the current default language.
     * Should be called on startup to load the current language settings.
     * Also checks that a language and country is not set that is not supported by
     * this application. This could occur when using outdated or different versions
     * of the software.
     */
    public static void updateLocale() {
        // prefs.put("language", "it");
        // prefs.put("country", "IT");
        String language = prefs.get("language", "en");
        String country = prefs.get("country", "NZ");
        // System.out.println(language + "_" + country);
        for (int i = 0; i < countryCodes.length; i++) {
            if (country.equals(countryCodes[i])) {
                selectedCountry = i;
                // System.out.println("Country found");
            }
        }
        for (int i = 0; i < languageCodes.length; i++) {
            if (language.equals(languageCodes[i])) {
                selectedLanguage = i;
                // System.out.println("language found");
            }
        }
        // Resets the language if you somehow end up on bundle not supported
        if (selectedCountry == -1 || selectedLanguage == -1) {
            selectedCountry = 0;
            selectedLanguage = 0;
            language = languageCodes[0];
            country = countryCodes[0];
            // System.out.println("Reset languages");
        }
        // prefs.put("country", "NZ");
        Locale.setDefault(new Locale(language, country));
    }

    /**
     * A method to check if the given string is in the given array.
     * 
     * @param allCountries The array to check
     * @param text         The string to check for
     * @return True if the string is in the array, false otherwise
     */
    private static boolean contains(String[] allCountries, String text) {
        for (String string : allCountries) {
            if (string.equals(text))
                return true;
        }
        return false;
    }

    /**
     * A method to set the drawing flag to true. This also sets the shape type so
     * that the shape is always initialised with the correct type.
     * 
     * @param shapeType The type of shape to be drawn
     */
    public static void makeDrawingTrue(int shapeType) {
        isDrawing = true;
        ShapeHandler.setShapeType(shapeType);
        setSelecting(false);

    }

    /**
     * A method to set the drawing flag to true. This allows cropping as you can
     * show the preview but not draw the shape.
     * 
     * @param shapeType The Type of shape
     * @param drawing   Wheather or not the user is Drawing
     */
    public static void makeDrawingTrue(int shapeType, boolean drawing) {
        setCropping(false);
        isDrawing = drawing;
        ShapeHandler.setShapeType(shapeType);

    }

    /**
     * A method to set the drawing flag to false. This also sets the shape type to 0
     * which is uninitialized.
     */
    public static void makeDrawingFalse() {
        isDrawing = false;
        ShapeHandler.setShapeType(0);

    }

    /**
     * A method to set the cropping flag to true. This allows for croppping.
     */
    public static void makeDrawingFalseCrop() {
        isDrawing = false;
        tb.unselect();
    }

    /**
     * A method to get the current state of the drawing flag.
     * 
     * @return The current state of the drawing flag.
     */
    public static boolean getDrawing() {
        return isDrawing;
    }

    /**
     * A method to set the pen colour to the given colour.
     * 
     * @param color The colour to set the pen to.
     */
    public static void setPenColor(Color color) {
        penColor = color;
    }

    /**
     * A method to get the current pen colour.
     * 
     * @return The current pen colour.
     */
    public static Color getPenColor() {
        return penColor;
    }

    /**
     * A method to set the stroke size of the pen.
     * 
     * @param size The size of the stroke.
     */
    public static void setStrokeSize(int size) {
        strokeSize = size;
    }

    /**
     * A method to get the current stroke size of the pen.
     * 
     * @return The current stroke size of the pen.
     */
    public static int getStrokeSize() {
        return strokeSize;
    }

    /**
     * A method to start cropping an image. This sets the cursor to a crosshair to
     * give the user visual feedback.
     * 
     * @param cropping A boolean to set the cropping flag to.
     */
    public static void setCropping(boolean cropping) {
        isCropping = cropping;
    }

    /**
     * A method to get if we are currently in cropping mode.
     * 
     * @return The current state of the cropping flag.
     */
    public static boolean isCropping() {
        return isCropping;
    }

    /**
     * A method to set if we are currently selecting on the image.
     * 
     * @param selecting The state to set the selecting flag to.
     */
    public static void setSelecting(boolean selecting) {
        if (selecting) {
            tb.setMacroUnusable();
            settingsMenu.getItem(3).setEnabled(false);
        } else if (!selecting && ImageAction.target.getImage().hasImage()) {
            tb.setMacroUsable();
            settingsMenu.getItem(3).setEnabled(true);
        }
        isSelecting = selecting;
    }

    /**
     * A method to get if we are currently selecting on the image.
     * 
     * @return The current state of the selecting flag.
     */
    public static boolean isSelecting() {
        return isSelecting;
    }

    /**
     * A memthod to set the orientation of the toolbar.
     * 
     * @param x The orientation to set the toolbar to.
     */
    public static void setToolBar(int x) {
        tbOrientation = x;
    }

    /**
     * A method to get the current bounds. Whether it is the whole image or a
     * selection selected using the selection tool.
     * 
     * @return The current bounds.
     */
    public static Rectangle2D getBound() {
        Rectangle2D bound;
        if (ImageAction.target == null) {
            // For initialising macros on toolbar to be applied to the whole image
            bound = new Rectangle2D.Double(0, 0, 100, 100);
        } else {
            bound = new Rectangle2D.Double(0, 0, ImageAction.target.getWidth(), ImageAction.target.getHeight());
        }
        if (Settings.isSelecting()) {
            Rectangle2D rect = (Rectangle2D) ShapeHandler.getCurrentShape(false);
            bound = new Rectangle2D.Double(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
            if (bound.getWidth() <= 0 || bound.getHeight() <= 0
                    || bound.getX() + bound.getWidth() > ImageAction.target.getWidth()
                    || bound.getY() + bound.getHeight() > ImageAction.target.getHeight()) {
                bound = null;
            }
        }
        return bound;
    }

    /**
     * A method to add a component to the frame.
     * 
     * @param component The component to add to the frame.
     */
    public static void addToFrame(JComponent component) {
        frame.add(component);
    }

    /**
     * A method to set if ANDIE is erasing.
     * 
     * @param erasing The state to set the erasing flag to.
     */
    public static void setErasing(boolean erasing) {
        isErasing = erasing;
    }

    /**
     * A method to get if ANDIE is erasing.
     * 
     * @return The current state of the erasing flag.
     */
    public static boolean isErasing() {
        return isErasing;
    }

    /**
     * <p>
     * Returns the given selcted language
     * </p>
     * 
     * @return the int representing the selected language.
     */
    public static int getSelectedLang() {
        return selectedLanguage;
    }

    /**
     * A method to set the look and feel of the program.
     * 1) Navy
     * 2) Grey
     * 3) Black
     * 4) White
     * 
     * @param theme The theme to set the look and feel to.
     */
    public static void updateLookAndFeel(int theme) {
        if (theme == 1) {
            ImageAction.main.setBackground(new Color(12, 26, 64));
            colorTheme = new Color(12, 26, 64);
            configureToolBar(tbOrientation);
        }
        if (theme == 2) {
            ImageAction.main.setBackground(new Color(115, 115, 115));
            colorTheme = new Color(115, 115, 115);
            configureToolBar(tbOrientation);
        }
        if (theme == 3) {
            ImageAction.main.setBackground(new Color(10, 10, 10));
            colorTheme = new Color(10, 10, 10);
            configureToolBar(tbOrientation);
        }
        if (theme == 4) {
            ImageAction.main.setBackground(new Color(255, 255, 255));
            colorTheme = new Color(215, 215, 215);
            configureToolBar(tbOrientation);
        }
    }
}