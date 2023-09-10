package cosc202.andie;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.*;
import javax.imageio.*;

/**
 * Editing in order to have a commit - Ben, Charlie!, Hamish!, PPSrgrg
 * Editing in order to have a commit - Ben, Charlie!, Hamish!, ANDie
 * Test for home laptop clone
 * <p>
 * Main class for A Non-Destructive Image Editor (ANDIE).
 * </p>
 * 
 * <p>
 * This class is the entry point for the program.
 * It creates a Graphical User Interface (GUI) that provides access to various
 * image editing and processing operations.
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
public class Andie {
    /**
     * The file actions in the File menu.
     */
    protected static FileActions fileActions;
    /**
     * The edit actions in the edit menu.
     */
    protected static EditActions editActions;
    /**
     * The search bar.
     */
    protected static SearchBar searchBar;
    /**
     * The SettingsAction
     * 
     */
    protected static SettingsAction settingsAction;
    /**
     * <p>
     * Launches the main GUI for the ANDIE program.
     * </p>
     * 
     * <p>
     * This method sets up an interface consisting of an active image (an
     * {@code ImagePanel})
     * and various menus which can be used to trigger operations to load, save,
     * edit, etc.
     * These operations are implemented {@link ImageOperation}s and triggerd via
     * {@code ImageAction}s grouped by their general purpose into menus.
     * </p>
     * 
     * @see ImagePanel
     * @see ImageAction
     * @see ImageOperation
     * @see FileActions
     * @see EditActions
     * @see ViewActions
     * @see FilterActions
     * @see ColourActions
     * @see ScaleImageActions
     * @throws Exception if there is an error loading the GUI
     */
    private static void createAndShowGUI() throws Exception {
        // Sets the UI to be cross platform
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        ArrayList<AndieAction> actions = new ArrayList<>();
        ToolBarMacros.setDefaultMacros();

        // Set up the main GUI frame.
        JFrame frame = new JFrame("ANDIE");

        Image image = ImageIO.read(Andie.class.getClassLoader().getResource("icon.png"));
        frame.setIconImage(image);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // The main content area is an ImagePanel.
        JPanel main = new JPanel();

        LayoutManager layout = new OverlayLayout(main);
        main.setLayout(layout);

        // main.setOpaque(true);
        // Set an overlay layout so we have the main taking both
        // image panel and the overlay panel.
        // main.setLayout(new OverlayLayout(main));
        main.setBackground(new Color(12, 26, 64));
        ImagePanel imagePanel = new ImagePanel();
        ImageAction.setTarget(imagePanel);
        ImageAction.setMain(main);
        imagePanel.setOpaque(true);
        imagePanel.setBackground(Color.white);

        OverlayPanel overlay = new OverlayPanel();
        overlay.setBackground(new Color(0, 0, 0, 0));
        overlay.setVisible(true);
        overlay.setOpaque(false);
        // overlay.setBackground(Color.blue);
        overlay.setMaximumSize(new Dimension(100, 100));

        // Visible Overlay with background. Image overtop.
        ImageAction.setOverlay(overlay);
        main.add(overlay);
        main.add(imagePanel);

        // main.setMaximumSize(new Dimension(300, 300));

        JScrollPane scrollPane = new JScrollPane(main);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Add in menus for various types of action the user may perform.
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);

        // File menus are pretty standard, so things that usually go in File menus go
        // here.
        fileActions = new FileActions();
        menuBar.add(fileActions.createMenu());
        actions.add(fileActions);

        // Likewise Edit menus are very common, so should be clear what might go here.
        EditActions editActions = new EditActions();
        menuBar.add(editActions.createMenu());
        actions.add(editActions);

        // View actions control how the image is displayed, but do not alter its actual
        // content.
        ViewActions viewActions = new ViewActions();
        menuBar.add(viewActions.createMenu());
        actions.add(viewActions);

        // Filters apply a per-pixel operation to the image, generally based on a local
        // window.
        FilterActions filterActions = new FilterActions();
        menuBar.add(filterActions.createMenu());
        actions.add(filterActions);

        // Actions that affect the representation of colour in the image.
        ColourActions colourActions = new ColourActions();
        menuBar.add(colourActions.createMenu());
        actions.add(colourActions);

        // Resize actions resize the image.
        Resizing resizeAction = new Resizing();
        menuBar.add(resizeAction.createMenu());
        actions.add(resizeAction);

        // Actions that alter the image in some way.
        ScaleImageActions imageActions = new ScaleImageActions();
        menuBar.add(imageActions.createMenu());
        actions.add(imageActions);

        // actions that allow Andie re-configuration
        // fileActions, editActions, viewActions, filterActions,colourActions,
        // resizeAction, imageActions,
        SettingsAction settingsAction = new SettingsAction(actions, frame);
        Andie.settingsAction=settingsAction;
        fileActions.setSettings(settingsAction);
        JMenu menu = settingsAction.createMenu();
        menuBar.add(menu);
        Settings.setSettingsMenu(menu);

        JButton search = new JButton("Search");
        search.setBackground(new Color(215,215,215));
        search.setFocusable(false);
        search.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (Andie.searchBar != null) {
                    Andie.searchBar.dispose();
                }
                SearchBar searchbar = new SearchBar();
                Andie.searchBar = searchbar;
                searchbar.setVisible(true);
            }
        });

        menuBar.add(search);
        frame.setJMenuBar(menuBar);
        Settings.setDefaultToolBar();
        Settings.updateUI();
        Settings.updateLookAndFeel(Theme.getTheme());
        // frame.pack();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.setVisible(true);
        frame.setFocusable(true);

        // adding the key listeners.
        frame.addKeyListener(new Keyboard_Listener());
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                String[] okAndCancel = { Settings.getMessage("Close"), Settings.getMessage("CANCEL") };
                int option = JOptionPane.showOptionDialog(null, Settings.getMessage("CloseQuestion"),
                        Settings.getMessage("CloseQuestionTitle"),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, okAndCancel, null);
                if (option == JOptionPane.NO_OPTION) {
                    return;
                } else if (option == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    /**
     * <p>
     * Main entry point to the ANDIE program.
     * </p>
     * 
     * <p>
     * Creates a LoadScreen that lasts 5 seconds before closing.
     * </p>
     * 
     * <p>
     * Creates and launches the main GUI in a separate thread.
     * As a result, this is essentially a wrapper around {@code createAndShowGUI()}.
     * </p>
     * 
     * @param args Command line arguments, not currently used.
     * @throws Exception If something goes awry.
     * @see #createAndShowGUI()
     */
    public static void main(String[] args) throws Exception {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        Settings.updateLocale();
        JDialog loadScreen = new JDialog();
        JPanel lp = new LoadPanel();
        loadScreen.setUndecorated(true);
        lp.setPreferredSize(new Dimension(400, 400));
        loadScreen.getContentPane().add(lp); // add the content panel to the dialog box.
        loadScreen.pack();
        loadScreen.setLocation(dim.width / 2 - loadScreen.getSize().width / 2,
                dim.height / 2 - loadScreen.getSize().height / 2);
        loadScreen.setVisible(true);
        loadScreen.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            Thread.sleep(5000); // sleep for 5 seconds.
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadScreen.dispose();
        loadScreen.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    System.exit(1);
                }
            }
        });
    }
}
