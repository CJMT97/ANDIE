package cosc202.andie;

import java.util.*;
import java.io.*;
import java.awt.Dimension;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.HashMap;

/**
 * <p>
 * An image with a set of operations applied to it.
 * </p>
 * 
 * <p>
 * The EditableImage represents an image with a series of operations applied to
 * it.
 * It is fairly core to the ANDIE program, being the central data structure.
 * The operations are applied to a copy of the original image so that they can
 * be undone.
 * THis is what is meant by "A Non-Destructive Image Editor" - you can always
 * undo back to the original image.
 * </p>
 * 
 * <p>
 * Internally the EditableImage has two {@link BufferedImage}s - the original
 * image
 * and the result of applying the current set of operations to it.
 * The operations themselves are stored on a {@link Stack}, with a second
 * {@link Stack}
 * being used to allow undone operations to be redone.
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
class EditableImage {

    /** The original image. This should never be altered by ANDIE. */
    private BufferedImage original;
    /**
     * The current image, the result of applying {@link ops} to {@link original}.
     */
    private BufferedImage current;
    /** The sequence of operations currently applied to the image. */
    private Stack<ImageOperation> ops;
    /** A memory of 'undone' operations to support 'redo'. */
    private Stack<ImageOperation> redoOps;
    /** The file where the original image is stored/ */
    private String imageFilename;
    /** The file where the operation sequence is stored. */
    private String opsFilename;
    /** The stack of operations for a macro. */
    private Stack<ImageOperation> macro;
    /** The boolean flag to determine if a macro is currently being recorded. */
    private boolean isRecording;
    /** The number of the macros done. */
    public int macroIndex = 0;
    /** The number of the macros undone. */
    public int undoMacroIndex = 0;
    /** The number of the macros redone. */
    public int redoMacroIndex = 0;
    /** Boolean to track if the current operation is a macro. */
    private boolean isMacro;
    /** Hashmap containing the macros that can be undone. */
    private HashMap<Integer, Stack<ImageOperation>> undoMacros = new HashMap<>();
    /** Hashmap containing the macros that can be redone. */
    private HashMap<Integer, Stack<ImageOperation>> redoMacros = new HashMap<>();
    /** List of operations that are able to be undone. */
    private ArrayList<Boolean> undoOpArrayList = new ArrayList<Boolean>();
    /** List of operations that can be redone. */
    private ArrayList<Boolean> redoOpArrayList = new ArrayList<Boolean>();

    /**
     * <p>
     * Create a new EditableImage.
     * </p>
     * 
     * <p>
     * A new EditableImage has no image (it is a null reference), and an empty stack
     * of operations.
     * </p>
     */
    public EditableImage() {
        original = null;
        current = null;
        ops = new Stack<ImageOperation>();
        redoOps = new Stack<ImageOperation>();
        imageFilename = null;
        opsFilename = null;

        macro = new Stack<ImageOperation>();
    }

    /**
     * <p>
     * Check if there is an image loaded.
     * </p>
     * 
     * @return True if there is an image, false otherwise.
     */
    public boolean hasImage() {
        return current != null;
    }

    /**
     * <p>
     * Method to make the isRecording boolean datafield true.
     * </p>
     */
    private void makeRecordingTrue() {
        isRecording = true;
    }

    /**
     * <p>
     * Method to make the isRecording boolean datafield false.
     * </p>
     */
    private void makeRecordingFalse() {
        isRecording = false;
    }

    /**
     * <p>
     * Method to alter a boolean field to start recording a Macro.
     * </p>
     */
    public void recordMacro() {
        if (!isRecording) {
            makeRecordingTrue();
        }

    }

    /**
     * <p>
     * Method to save a macro into a .ops file.
     * </p>
     */
    public void saveMacro() {
        makeRecordingFalse();
        String imageFilepath = "";
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                imageFilepath = fileChooser.getSelectedFile().getAbsolutePath();
                if (imageFilepath.contains(".")) {
                    String path = imageFilepath;
                    while (path.contains(".")) {
                        path = path.substring(path.indexOf(".") + 1, path.length());
                    }

                    boolean found = false;
                    if (path.toUpperCase().equals(".OPS")) {
                        found = true;
                    }

                    if (!found) {
                        imageFilepath = imageFilepath + ".ops";
                    }
                } else {
                    imageFilepath = imageFilepath + ".ops";
                }
            } catch (Exception ex) {
                System.exit(1);
            }

            try {
                FileOutputStream fileOut = new FileOutputStream(imageFilepath);
                ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
                objOut.writeObject(this.macro);
                objOut.close();
                fileOut.close();
            } catch (IOException e) {
            }

        }
    }

    /**
     * <p>
     * Public method to call the private apply method.
     * </p>
     */
    public void appyMacro() {
        applyMacro(current);
    }

    /**
     * <p>
     * Method to apply a macro to a BufferedImage.
     * </p>
     */
    private void applyMacro(BufferedImage image) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("OPS Files", "ops");
        fileChooser.setFileFilter(filter);

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            try {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                //
                Stack<ImageOperation> operationStack = (Stack<ImageOperation>) objIn.readObject();
                ImageOperation[] macroOps = operationStack.toArray(new ImageOperation[0]);

                undoMacroIndex++;
                undoMacros.put(undoMacroIndex, operationStack);

                for (ImageOperation op : macroOps) {
                    isMacro = true;

                    ImageAction.target.getImage().apply(op);
                    int width = (int) (ImageAction.target.getImage().getCurrentImage().getWidth()
                            * ImageAction.target.getScale());
                    int height = (int) (ImageAction.target.getImage().getCurrentImage().getHeight()
                            * ImageAction.target.getScale());
                    ImageAction.target.setMaximumSize(new Dimension(width, height));
                    ImageAction.overlay.setMaximumSize(new Dimension(width, height));
                    ImageAction.target.repaint();
                    ImageAction.target.getParent().revalidate();
                    Settings.packFrame();
                    macroIndex++;
                }
                macroIndex = 0;
                isMacro = false;

                fileIn.close();
                objIn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>
     * Apply a macro in the form of a Stack<ImageOperation> to this image.
     * </p>
     * 
     * @param macro The stack of operations to apply.
     */
    public void applyTBMacro(Stack<ImageOperation> macro) {
        undoMacroIndex++;
        undoMacros.put(undoMacroIndex, macro);

        for (ImageOperation op : macro) {
            isMacro = true;
            ImageAction.target.getImage().apply(op);
            int width = (int) (ImageAction.target.getImage().getCurrentImage().getWidth()
                    * ImageAction.target.getScale());
            int height = (int) (ImageAction.target.getImage().getCurrentImage().getHeight()
                    * ImageAction.target.getScale());
            ImageAction.target.setMaximumSize(new Dimension(width, height));
            ImageAction.overlay.setMaximumSize(new Dimension(width, height));
            ImageAction.target.repaint();
            ImageAction.target.getParent().revalidate();
            Settings.packFrame();
            macroIndex++;
        }
        macroIndex = 0;
        isMacro = false;
    }

    /**
     * <p>
     * Apply an {@link ImageOperation} to this image.
     * </p>
     * 
     * @param imageOperation The operation to apply.
     */
    public void apply(ImageOperation imageOperation) {

        if (isMacro && macroIndex == 0) {
            undoOpArrayList.add(true);
        } else if (!isMacro) {
            undoOpArrayList.add(false);
        }

        current = imageOperation.apply(current);
        ops.add(imageOperation);

        if (isRecording == true) {
            macro.add(imageOperation);
        }
    }

    /**
     * <p>
     * Undo the last {@link ImageOperation} or macro applied to the image.
     * </p>
     */
    public void undo() {
        if (undoOpArrayList.get(undoOpArrayList.size() - 1)) {
            Stack<ImageOperation> currMacro = undoMacros.get(undoMacroIndex);
            undoMacros.remove(undoMacroIndex);
            undoMacroIndex--;
            int mSize = currMacro.size();

            for (int i = 0; i < mSize; i++) {
                redoOps.push(ops.pop());
            }

            redoMacroIndex++;
            redoMacros.put(redoMacroIndex, currMacro);
            redoOpArrayList.add(true);
            undoOpArrayList.remove(undoOpArrayList.size() - 1);

            refresh();
        } else {
            redoOps.push(ops.pop());
            redoOpArrayList.add(false);
            undoOpArrayList.remove(undoOpArrayList.size() - 1);
            refresh();
        }
    }

    /**
     * <p>
     * Reapply the most recently {@link undo}ne {@link ImageOperation} or macro to
     * the image.
     * </p>
     */
    public void redo() {
        if (redoOpArrayList.get(redoOpArrayList.size() - 1)) {
            Stack<ImageOperation> currMacro = redoMacros.get(redoMacroIndex);
            redoMacros.remove(redoMacroIndex);
            redoMacroIndex--;
            int mSize = currMacro.size();

            for (int i = 0; i < mSize; i++) {
                ops.push(redoOps.pop());
            }

            undoMacroIndex++;
            undoMacros.put(undoMacroIndex, currMacro);
            undoOpArrayList.add(true);
            redoOpArrayList.remove(redoOpArrayList.size() - 1);

            refresh();
        } else {
            ops.push(redoOps.pop());
            undoOpArrayList.add(false);
            redoOpArrayList.remove(redoOpArrayList.size() - 1);
            refresh();
        }
    }

    /**
     * <p>
     * Make a 'deep' copy of a BufferedImage.
     * </p>
     * 
     * <p>
     * Object instances in Java are accessed via references, which means that
     * assignment does
     * not copy an object, it merely makes another reference to the original.
     * In order to make an independent copy, the {@code clone()} method is generally
     * used.
     * {@link BufferedImage} does not implement {@link Cloneable} interface, and so
     * the
     * {@code clone()} method is not accessible.
     * </p>
     * 
     * <p>
     * This method makes a cloned copy of a BufferedImage.
     * This requires knoweldge of some details about the internals of the
     * BufferedImage,
     * but essentially comes down to making a new BufferedImage made up of copies of
     * the internal parts of the input.
     * </p>
     * 
     * <p>
     * This code is taken from StackOverflow:
     * <a href=
     * "https://stackoverflow.com/a/3514297">https://stackoverflow.com/a/3514297</a>
     * in response to
     * <a href=
     * "https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage">https://stackoverflow.com/questions/3514158/how-do-you-clone-a-bufferedimage</a>.
     * Code by Klark used under the CC BY-SA 2.5 license.
     * </p>
     * 
     * <p>
     * This method (only) is released under
     * <a href="https://creativecommons.org/licenses/by-sa/2.5/">CC BY-SA 2.5</a>
     * </p>
     * 
     * @param bi The BufferedImage to copy.
     * @return A deep copy of the input.
     */
    private static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * <p>
     * Open an image from a file.
     * </p>
     * 
     * <p>
     * Opens an image from the specified file.
     * Also tries to open a set of operations from the file with <code>.ops</code>
     * added.
     * So if you open <code>some/path/to/image.png</code>, this method will also try
     * to read the operations from <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param filePath The file to open the image from.
     * @throws Exception If something goes wrong.
     */
    public void open(String filePath) throws Exception {
        imageFilename = filePath;
        opsFilename = imageFilename + ".ops";
        File imageFile = new File(imageFilename);
        original = ImageIO.read(imageFile);
        current = deepCopy(original);

        try {
            FileInputStream fileIn = new FileInputStream(this.opsFilename);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            // Silence the Java compiler warning about type casting.
            // Understanding the cause of the warning is way beyond
            // the scope of COSC202, but if you're interested, it has
            // to do with "type erasure" in Java: the compiler cannot
            // produce code that fails at this point in all cases in
            // which there is actually a type mismatch for one of the
            // elements within the Stack, i.e., a non-ImageOperation.
            @SuppressWarnings("unchecked")
            Stack<ImageOperation> opsFromFile = (Stack<ImageOperation>) objIn.readObject();
            ops = opsFromFile;
            redoOps.clear();
            objIn.close();
            fileIn.close();
        } catch (Exception ex) {
            // Could be no file or something else. Carry on for now.
        }
        this.refresh();
    }

    /**
     * <p>
     * Save an image to file.
     * </p>
     * 
     * <p>
     * Saves an image to the file it was opened from, or the most recent file saved
     * as.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @throws Exception If something goes wrong.
     */
    public void save() throws Exception {
        if (this.opsFilename == null) {
            this.opsFilename = this.imageFilename + ".ops";
        }
        // Write image file based on file extension.
        String extension = imageFilename.substring(1 + imageFilename.lastIndexOf(".")).toLowerCase();
        ImageIO.write(original, extension, new File(imageFilename));
        // Write operations file.
        FileOutputStream fileOut = new FileOutputStream(this.opsFilename);
        ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
        objOut.writeObject(this.ops);
        objOut.close();
        fileOut.close();
    }

    /**
     * <p>
     * Save an image to a speficied file.
     * </p>
     * 
     * <p>
     * Saves an image to the file provided as a parameter.
     * Also saves a set of operations from the file with <code>.ops</code> added.
     * So if you save to <code>some/path/to/image.png</code>, this method will also
     * save
     * the current operations to <code>some/path/to/image.png.ops</code>.
     * </p>
     * 
     * @param imageFilename The file location to save the image to.
     * @throws Exception If something goes wrong.
     */
    public void saveAs(String imageFilename) throws Exception {
        this.imageFilename = imageFilename;
        this.opsFilename = imageFilename + ".ops";
        save();
    }

    /**
     * <p>
     * Get the current image after the operations have been applied.
     * </p>
     * 
     * @return The result of applying all of the current operations to the
     *         {@link original} image.
     */
    public BufferedImage getCurrentImage() {
        return current;
    }

    /**
     * <p>
     * Reapply the current list of operations to the original.
     * </p>
     * 
     * <p>
     * While the latest version of the image is stored in {@link current}, this
     * method makes a fresh copy of the original and applies the operations to it in
     * sequence.
     * This is useful when undoing changes to the image, or in any other case where
     * {@link current}
     * cannot be easily incrementally updated.
     * </p>
     */
    private void refresh() {
        current = deepCopy(original);
        for (ImageOperation op : ops) {
            current = op.apply(current);
        }
    }
}
