package cosc202.andie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.*;

/**
 * <p>
 * Backend and GUI for the SearchBar
 * </p>
 * 
 * @author Ben Nicholson
 * @version 1.0
 * 
 */
public class SearchBar extends JFrame {
        /** The textfiled for searching in */
        private JTextField searchField;
        /** The Map of buttons and actions */
        private HashMap<String, Set<JButton>> searchButtonMap;
        /** The Panels for the search box and displaying the results */
        private JPanel searchBox, displayResults;
        /** The set of every action that requires an image */
        private Set<String> requiresImage;

        /**
         * <p>
         * Constructor Populates Search UI and Initilises Search
         * </p>
         */
        public SearchBar() {
                setBackground(new Color(225, 225, 225));
                setResizable(false);
                Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
                searchBox = new JPanel();
                displayResults = new JPanel();
                searchField = new JTextField(15);
                searchField.setSize(50, 100);
                searchBox.setBackground(new Color(215, 215, 215));
                displayResults.setBackground(new Color(215, 215, 215));
                searchField.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyReleased(KeyEvent e) {
                                onAction();
                        }
                });
                // Populating the prohibited options for when an image isn't open.
                requiresImage = Set.of(Settings.getMessage("MeanFilter"),
                                Settings.getMessage("MedianBlur"),
                                Settings.getMessage("SoftBlur"),
                                Settings.getMessage("GaussBlur"),
                                Settings.getMessage("SharpenFilter"),
                                Settings.getMessage("EmbossFilters"),
                                Settings.getMessage("SepiaFilter"),
                                Settings.getMessage("HorizontalSobelFilter"),
                                Settings.getMessage("SaturationFilter"),
                                Settings.getMessage("VerticalSobelFilter"),
                                Settings.getMessage("ZoomSliderTitle"),
                                Settings.getMessage("ZoomIn"),
                                Settings.getMessage("ZoomOut"),
                                Settings.getMessage("ZoomFull"),
                                Settings.getMessage("Save"),
                                Settings.getMessage("SaveAs"),
                                Settings.getMessage("Export"),
                                Settings.getMessage("HorizontalFlip"),
                                Settings.getMessage("VerticalFlip"),
                                Settings.getMessage("FilterActions"),
                                Settings.getMessage("Rotates90Left"),
                                Settings.getMessage("Rotates90Right"),
                                Settings.getMessage("Rotates180"),
                                Settings.getMessage("Undo"),
                                Settings.getMessage("Redo"),
                                Settings.getMessage("FileActions"),
                                Settings.getMessage("EditActions"),
                                Settings.getMessage("ScaleImageActions"),
                                Settings.getMessage("ViewActions"),
                                Settings.getMessage("ColourActions"),
                                Settings.getMessage("BrightnessAndContrast"),
                                Settings.getMessage("Greyscale"),
                                Settings.getMessage("Resize"),
                                Settings.getMessage("Crop"));

                populateButtonMap();
                displayResults.setLayout(new BoxLayout(displayResults, BoxLayout.Y_AXIS));
                // displayResults.setBackground(Color.GREEN);
                searchBox.add(searchField);
                this.add(searchBox, BorderLayout.NORTH);
                this.add(displayResults, BorderLayout.CENTER);
                this.setSize(300, 300);
                this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        }

        /**
         * <p>
         * searchFilter essentially matches which ID's begin with the input. If they
         * match add
         * them to a arraylist then return said list.
         * 
         * @param input the inputted text into the searchField
         * @return returns an ArrayList of the raw matching ID values to the input.
         */
        public ArrayList<String> searchFilter(String input) {
                ArrayList<String> results = new ArrayList<>();
                Set<String> idList = this.searchButtonMap.keySet();
                Iterator<String> itr = idList.iterator();
                String fa = Settings.getMessage("FilterActions");
                String va = Settings.getMessage("ViewActions");
                String fileActionsString = Settings.getMessage("FileActions");
                String ea = Settings.getMessage("EditActions");
                String colourActions = Settings.getMessage("ColourActions");
                while (itr.hasNext()) {
                        String idListNext = itr.next();
                        if (!(idListNext.length() < input.length()) && !(input.length() == 0)) {
                                String checker = idListNext.toLowerCase().replaceAll(" ", "");
                                String word = input.toLowerCase().replaceAll(" ", "");
                                if (checker.matches("(.*)" + word + "(.*)")) {
                                        if (ImageAction.target.getImage().getCurrentImage() != null
                                                        && requiresImage.contains(idListNext) == true) {
                                                if (idListNext.equals(fa)) {
                                                        if (fa.toLowerCase().matches("(.*)" + word)) {
                                                                results.add(idListNext);
                                                        }
                                                } else if (idListNext.equals(va)) {
                                                        if (va.toLowerCase().matches("(.*)" + word)) {
                                                                results.add(idListNext);
                                                        }
                                                } else if (idListNext.equals(fileActionsString)) {
                                                        if (fileActionsString.toLowerCase()
                                                                        .matches("(.*)" + word + "(.*)")) {
                                                                results.add(idListNext);
                                                        }
                                                } else if (idListNext.equals(ea)) {
                                                        if (ea.toLowerCase().matches("(.*)" + word)) {
                                                                results.add(idListNext);
                                                        }
                                                } else if (idListNext.equals(colourActions)) {
                                                        if (colourActions.toLowerCase()
                                                                        .matches("(.*)" + word + "(.*)")) {
                                                                results.add(idListNext);
                                                        }
                                                } else {
                                                        results.add(idListNext);
                                                }
                                        } else if (!(requiresImage.contains(idListNext))) {
                                                // System.out.println(requiresImage.contains(idListNext) + " " +
                                                // idListNext);
                                                results.add(idListNext);
                                        }
                                }
                        }
                }
                return results;

        }

        /**
         * <p>
         * Gets a set of all the JButtons associated with a given arraylist of String
         * ID's
         * </p>
         * 
         * @param in The inputted ArrayList of ID's for the hashmap to find.
         * @return returns the set of JButtons.
         */
        public Set<JButton> getJButtons(ArrayList<String> in) {
                HashSet<JButton> output = new HashSet<JButton>();

                for (String i : in) {
                        Set<JButton> setatID = this.searchButtonMap.get(i);
                        Iterator<JButton> itr = setatID.iterator();
                        while (itr.hasNext()) {
                                output.add(itr.next());
                        }
                }
                return output;
        }

        /**
         * <p>
         * Method changes the displayed results relative to the input to the JTextField.
         * </p>
         */
        public void onAction() {
                JPanel resultPane = new JPanel();
                displayResults.removeAll();
                resultPane.setMaximumSize(new Dimension(100, 300));
                resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.Y_AXIS));
                resultPane.setBackground(new Color(215, 215, 215));

                ArrayList<JButton> results = new ArrayList<JButton>(getJButtons(searchFilter(searchField.getText())));
                Collections.sort(results, new sortByName());

                if (results.size() == 0) {
                        // resultPane.setAlignmentX(LEFT_ALIGNMENT);
                        JLabel errorLabel = new JLabel();
                        FileActions f = Andie.fileActions;
                        if (Settings.getSelectedLang() == 0) {
                                errorLabel = new JLabel(
                                                "<html><center>Sorry! We're out of results.. <br><br>If you haven't opened an image some results are hidden!</center></html>");
                        } else if (Settings.getSelectedLang() == 1) {
                                errorLabel = new JLabel(
                                                "<html><center>Aroha mai! Kua pau nga hua.<br><br>Mena kaore koe i whakatuwhera i tetahi whakaahua ka huna etahi hua!</center></html>");
                        } else {
                                errorLabel = new JLabel(
                                                "<html><center>Scusa! Abbiamo finito i risultati.<br><br>Se non hai aperto un'immagine alcuni risultati sono nascosti!</center></html>");
                        }

                        errorLabel.setPreferredSize(new Dimension(200, 100));
                        errorLabel.setMaximumSize(new Dimension(300, 100));
                        errorLabel.setAlignmentX(SwingConstants.CENTER);
                        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
                        // errorPrompt.setMaximumSize(new Dimension(200,100));
                        resultPane.add(errorLabel, BorderLayout.CENTER);
                        JButton button = createButton(Settings.getMessage("Open"),
                                        f.new FileOpenAction(null, null, null, null));
                        button.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                        dispose();
                                }
                        });
                        resultPane.add(button);
                } else {
                        for (JButton i : results) {
                                resultPane.add(i, BorderLayout.CENTER);
                        }
                }
                JScrollPane s = new JScrollPane(resultPane);
                s.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
                s.setBounds(50, 30, 300, 100);
                s.setBackground(Color.black);
                displayResults.add(s, BorderLayout.CENTER);
                displayResults.setPreferredSize(new Dimension(300, 300));
                this.revalidate();
        }

        /**
         * <p>
         * Populates the Hashmap of the String ID, JButton pairs.
         * </p>
         */
        public void populateButtonMap() {
                FilterActions f = new FilterActions();
                FileActions fileActions = Andie.fileActions;
                EditActions editActions = new EditActions();
                ViewActions viewActions = new ViewActions();
                ColourActions colourActions = new ColourActions();

                ScaleImageActions rotateActions = new ScaleImageActions();
                Resizing resize = new Resizing();
                Settings settings = new Settings();

                searchButtonMap = new HashMap<String, Set<JButton>>();
                // Key word Filters
                searchButtonMap.put(Settings.getMessage("FilterActions"), Set.of(
                                createButton(Settings.getMessage("MeanFilter"),
                                                f.new MeanFilterAction(null, null, null, null)),
                                createButton(Settings.getMessage("SoftBlur"),
                                                f.new SoftBlurAction(null, null, null, null)),
                                createButton(Settings.getMessage("MedianBlur"),
                                                f.new MedianBlurAction(null, null, null, null)),
                                createButton(Settings.getMessage("GaussBlur"),
                                                f.new GaussBlurAction(null, null, null, null)),
                                createButton(Settings.getMessage("SharpenFilter"),
                                                f.new SharpenFilterAction(null, null, null, null)),
                                createButton(Settings.getMessage("HorizontalSobelFilter"),
                                                f.new SobelFilter1Action(null, null, null, null)),
                                createButton(Settings.getMessage("VerticalSobelFilter"),
                                                f.new SobelFilter2Action(null, null, null, null)),
                                createButton(Settings.getMessage("EmbossFilters"),
                                                f.new EmbossFiltersAction(null, null, null, null)),
                                createButton(Settings.getMessage("SepiaFilter"),
                                                f.new SepiaFilterAction(null, null, null, null)),
                                createButton(Settings.getMessage("SaturationFilter"),
                                                f.new SaturationFilterAction(null, null, null, null))));
                // KeyWord Colour Actions
                searchButtonMap.put(Settings.getMessage("ColourActions"), Set.of(
                                createButton(
                                                Settings.getMessage("ColourActions") + " -> "
                                                                + Settings.getMessage("BrightnessAndContrastTitle"),
                                                colourActions.new BrightnessAndContrastAction(null, null, null, null)),
                                createButton(Settings.getMessage("ColourActions") + " -> "
                                                + Settings.getMessage("Greyscale"),
                                                colourActions.new ConvertToGreyAction(null, null, null, null))));

                // Brightness and Contrast
                searchButtonMap.put(Settings.getMessage("BrightnessAndContrast"), Set.of(
                                createButton(Settings.getMessage("BrightnessAndContrast"),
                                                colourActions.new BrightnessAndContrastAction(null, null, null,
                                                                null))));
                // Convert to Grey
                searchButtonMap.put(Settings.getMessage("Greyscale"), Set.of(
                                createButton(Settings.getMessage("Greyscale"),
                                                colourActions.new ConvertToGreyAction(null, null, null, null))));
                // Median Filter
                searchButtonMap.put(Settings.getMessage("MedianBlur"), Set.of(
                                createButton(Settings.getMessage("MedianBlur"),
                                                f.new MedianBlurAction(null, null, null, null))));

                // Mean Filter
                searchButtonMap.put(Settings.getMessage("MeanFilter"), Set.of(
                                createButton(Settings.getMessage("MeanFilter"),
                                                f.new MeanFilterAction(null, null, null, null))));

                // Soft Blur
                searchButtonMap.put(Settings.getMessage("SoftBlur"), Set.of(
                                createButton(Settings.getMessage("SoftBlur"),
                                                f.new SoftBlurAction(null, null, null, null))));

                // GaussBlur
                searchButtonMap.put(Settings.getMessage("GaussBlur"), Set.of(
                                createButton(Settings.getMessage("GaussBlur"),
                                                f.new GaussBlurAction(null, null, null, null))));

                // Sharpen Filter
                searchButtonMap.put(Settings.getMessage("SharpenFilter"), Set.of(
                                createButton(Settings.getMessage("SharpenFilter"),
                                                f.new SharpenFilterAction(null, null, null, null))));

                // Horizontal Sobel Filter
                searchButtonMap.put(Settings.getMessage("HorizontalSobelFilter"), Set.of(
                                createButton(Settings.getMessage("HorizontalSobelFilter"),
                                                f.new SobelFilter1Action(null, null, null, null))));

                // Vertical Sobel Filter

                searchButtonMap.put(Settings.getMessage("VerticalSobelFilter"), Set.of(
                                createButton(Settings.getMessage("VerticalSobelFilter"),
                                                f.new SobelFilter2Action(null, null, null, null))));

                // Emboss Filters
                searchButtonMap.put(Settings.getMessage("EmbossFilters"), Set.of(
                                createButton(Settings.getMessage("EmbossFilters"),
                                                f.new EmbossFiltersAction(null, null, null, null))));

                // Sepia Filter
                searchButtonMap.put(Settings.getMessage("SepiaFilter"), Set.of(
                                createButton(Settings.getMessage("SepiaFilter"),
                                                f.new SepiaFilterAction(null, null, null, null))));
                // Saturation Filter
                searchButtonMap.put(Settings.getMessage("SaturationFilter"), Set.of(
                                createButton(Settings.getMessage("SaturationFilter"),
                                                f.new SaturationFilterAction(null, null, null, null))));

                // KeyWord View Actions
                searchButtonMap.put(Settings.getMessage("ViewActions"), Set.of(
                                createButton(Settings.getMessage("ZoomSliderTitle"),
                                                viewActions.new SetZoom(null, null, null, null)),
                                createButton(Settings.getMessage("ZoomIn"),
                                                viewActions.new ZoomInAction(null, null, null, null)),
                                createButton(Settings.getMessage("ZoomOut"),
                                                viewActions.new ZoomOutAction(null, null, null, null)),
                                createButton(Settings.getMessage("ZoomFull"),
                                                viewActions.new ZoomFullAction(null, null, null, null))));

                searchButtonMap.put(Settings.getMessage("ZoomSliderTitle"), Set.of(
                                createButton(Settings.getMessage("ZoomSliderTitle"),
                                                viewActions.new ZoomSlider(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("ZoomIn"), Set.of(
                                createButton(Settings.getMessage("ZoomIn"),
                                                viewActions.new ZoomInAction(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("ZoomOut"), Set.of(
                                createButton(Settings.getMessage("ZoomOut"),
                                                viewActions.new ZoomOutAction(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("ZoomFull"), Set.of(
                                createButton(Settings.getMessage("ZoomFull"),
                                                viewActions.new ZoomFullAction(null, null, null, null))));

                // EditActions
                searchButtonMap.put(Settings.getMessage("EditActions"), Set.of(
                                createButton(Settings.getMessage("Redo"),
                                                editActions.new RedoAction(null, null, null, null)),
                                createButton(Settings.getMessage("Undo"),
                                                editActions.new UndoAction(null, null, null, null))));
                // Redo
                searchButtonMap.put(Settings.getMessage("Redo"), Set.of(
                                createButton(Settings.getMessage("Redo"),
                                                editActions.new RedoAction(null, null, null, null))));
                // Undo
                searchButtonMap.put(Settings.getMessage("Undo"), Set.of(
                                createButton(Settings.getMessage("Undo"),
                                                editActions.new UndoAction(null, null, null, null))));

                // FileActions
                searchButtonMap.put(Settings.getMessage("FileActions"), Set.of(
                                createButton(Settings.getMessage("FileActions") + " -> " + Settings.getMessage("Open"),
                                                fileActions.new FileOpenAction(null, null, null, null)),
                                createButton(Settings.getMessage("FileActions") + " -> " + Settings.getMessage("Save"),
                                                fileActions.new FileSaveAction(getName(), null, getName(), null)),
                                createButton(Settings.getMessage("FileActions") + " -> "
                                                + Settings.getMessage("SaveAs"),
                                                fileActions.new FileSaveAsAction(getName(), null, getName(), null)),
                                createButton(Settings.getMessage("FileActions") + " -> "
                                                + Settings.getMessage("Export"),
                                                fileActions.new FileExportAction(getName(), null, getName(), null)),
                                createButton(Settings.getMessage("FileActions") + " -> " + Settings.getMessage("Exit"),
                                                fileActions.new FileExitAction(getName(), null, getName(), null))));

                searchButtonMap.put(Settings.getMessage("Open"), Set.of(
                                createButton(Settings.getMessage("Open"),
                                                fileActions.new FileOpenAction(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Save"), Set.of(
                                createButton(Settings.getMessage("Save"),
                                                fileActions.new FileSaveAction(getName(), null, getName(), null))));
                searchButtonMap.put(Settings.getMessage("SaveAs"), Set.of(
                                createButton(Settings.getMessage("SaveAs"),
                                                fileActions.new FileSaveAsAction(getName(), null, getName(), null))));
                searchButtonMap.put(Settings.getMessage("Exit"), Set.of(
                                createButton(Settings.getMessage("Exit"),
                                                fileActions.new FileExitAction(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Export"), Set.of(
                                createButton(Settings.getMessage("Export"),
                                                fileActions.new FileExportAction(null, null, null, null))));

                // Rotate Actions
                searchButtonMap.put(Settings.getMessage("ScaleImageActions"), Set.of(
                                createButton(Settings.getMessage("Rotates180"),
                                                rotateActions.new RotateImage_180(null, null, null, null)),
                                createButton(Settings.getMessage("Rotates90Left"),
                                                rotateActions.new RotateImage_Left(null, null, null, null)),
                                createButton(Settings.getMessage("Rotates90Right"),
                                                rotateActions.new RotateImage_Right(null, null, null, null)),
                                createButton(Settings.getMessage("HorizontalFlip"),
                                                rotateActions.new FlipX(null, null, null, null)),
                                createButton(Settings.getMessage("VerticalFlip"),
                                                rotateActions.new FlipY(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Rotates180"), Set.of(
                                createButton(Settings.getMessage("Rotates180"),
                                                rotateActions.new RotateImage_180(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Rotates90Left"), Set.of(
                                createButton(Settings.getMessage("Rotates90Left"),
                                                rotateActions.new RotateImage_Left(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Rotates90Right"), Set.of(
                                createButton(Settings.getMessage("Rotates90Right"),
                                                rotateActions.new RotateImage_Right(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("HorizontalFlip"), Set.of(
                                createButton(Settings.getMessage("HorizontalFlip"),
                                                rotateActions.new FlipX(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("VerticalFlip"), Set.of(
                                createButton(Settings.getMessage("VerticalFlip"),
                                                rotateActions.new FlipY(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Crop"), Set.of(
                                createButton(Settings.getMessage("Crop"),
                                                resize.new CropAction(null, null, null, null))));
                searchButtonMap.put(Settings.getMessage("Resize"), Set.of(
                                createButton(Settings.getMessage("Resize"),
                                                resize.new ResizeAction(getName(), null, getName(), null))));
                // Adding Settings
                searchButtonMap.put(Settings.getMessage("ToolBar"), Set.of(
                                createButton(Settings.getMessage("ToolBar"), Andie.settingsAction.new ConfigureToolBar(
                                                getName(), null, getName(), null))));

                JButton languageSettingsB = new JButton(Settings.getMessage("LanguageAction"));
                languageSettingsB.setFocusable(false);
                languageSettingsB.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                settings.actionPerformed(null);
                        }
                });

                languageSettingsB.setBackground(new Color(215, 215, 215));
                languageSettingsB.setMaximumSize(new Dimension(300, 30));
                searchButtonMap.put(Settings.getMessage("LanguageAction"), Set.of(
                                languageSettingsB));

        }

        /**
         * <p>
         * createButton simply creates a button in a single call.
         * </p>
         * 
         * @param name   the name of the given action.
         * @param action the given action when the button is clicked.
         * @return returns the created button.
         */
        public JButton createButton(String name, AbstractAction action) {
                JButton b = new JButton(name);
                b.setFocusable(false);
                b.addActionListener(action);
                b.setBackground(new Color(215, 215, 215));
                b.setMaximumSize(new Dimension(300, 30));
                return b;
        }

        /**
         * <p>
         * Simple Comparator class compares two JButtons Textboxes for sorting purposes.
         * </p>
         * 
         * @author Ben Nicholson
         */
        private class sortByName implements Comparator<JButton> {
                @Override
                public int compare(JButton o1, JButton o2) {
                        // TODO Auto-generated method stub
                        return o1.getText().compareTo(o2.getText());
                }

        }
}