package cosc202.andie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * <p>
 * ToolBarMacros class is reposible for getting the current macros that should be in the Toolbar 
 * </p>
 * @author Charlie Templeton
 */
public class ToolBarMacros {
    /**
     * Macro1 in the toolbar
     */
    protected static Stack<ImageOperation> macro1 = new Stack<ImageOperation>();
    /**
     * Macro2 in the toolbar
     */
    protected static Stack<ImageOperation> macro2 = new Stack<ImageOperation>();
    /**
     * Macro3 in the toolbar
     */
    protected static Stack<ImageOperation> macro3 = new Stack<ImageOperation>();
    /**
     * Macro4 in the toolbar
     */
    protected static Stack<ImageOperation> macro4 = new Stack<ImageOperation>();
    /**
     * Array of boolean representing wheather a macro is custom or default
     */
    protected static boolean[] macroCustom = new boolean[4];

    /**
     * <p>
     * setDefaultMacros method checks macros stored locally and if they exist initilises them 
     * otherwise set the macros to there default value
     * </p> 
     */
    public static void setDefaultMacros() {
        ArrayList<Stack<ImageOperation>> allMacros = new ArrayList<>();
        String[] fileNames = { "Macro1.ops", "Macro2.ops", "Macro3.ops", "Macro4.ops" };
        String appDataPath = System.getenv("LOCALAPPDATA");
        for (int i = 0; i < fileNames.length; i++) {

            try {
                File file = new File(appDataPath, fileNames[i]);
                FileInputStream inputStream = new FileInputStream(file);
                ObjectInputStream objIn = new ObjectInputStream(inputStream);

                Stack<ImageOperation> macro = (Stack<ImageOperation>) objIn.readObject();
                allMacros.add(macro);

                objIn.close();
                inputStream.close();
            } catch (IOException e) {
            } catch (ClassNotFoundException e) {
            }
        }

        if (allMacros.size() == 4) {
            macro1 = allMacros.get(0);
            macro2 = allMacros.get(1);
            macro3 = allMacros.get(2);
            macro4 = allMacros.get(3);
        }

        ImageOperation[] m1 = { new SepiaFilter(100, 100), new FlipHorizontal(), new MedianBlur(4, 100, 100) };
        ImageOperation[] m2 = { new ConvertToGrey(100, 100), new SobelFilters(true, 100, 100) };
        ImageOperation[] m3 = { new FlipVertical(), new RotateImage(1) };
        ImageOperation[] m4 = { new EmbossFilters(4, 100, 100), new GaussBlur(4, 100, 100),
                new BrightnessAndContrast(30, 50, 100, 100) };

        if (macro1.isEmpty()) {
            for (ImageOperation op : m1) {
                macro1.push(op);
            }
        }
        if (macro2.isEmpty()) {
            for (ImageOperation op : m2) {
                macro2.push(op);
            }
        }
        if (macro3.isEmpty()) {
            for (ImageOperation op : m3) {
                macro3.push(op);
            }
        }
        if (macro4.isEmpty()) {
            for (ImageOperation op : m4) {
                macro4.push(op);
            }
        }

        // Check if default
        
        macroCustom = getMacroCustom();
        saveCurrent();
    }

    /**
     * <p>
     * SetMacroCustom method updates the macros stored in the local file to be the current macros in ANDIE that
     * the user has made 
     * </p>
     */
    public static void setMacroCustom(){
        String appDataPath = System.getenv("LOCALAPPDATA");
        String fileText = macroCustom[0] + "\n" + macroCustom[1] + "\n" + macroCustom[2] + "\n" + macroCustom[3];
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appDataPath + "MacroSettings.txt"))) {
            writer.write(fileText);
        } catch (IOException e) {
        }
    }

    /**
     * <p>
     * getMacroCustom method gets boolean values that are stored in a local file and uses them to decide if the
     * macros are custom or not 
     * </p>
     * @return An Array of booleans representing the state of each macro (custom or default)
     */
    public static boolean[] getMacroCustom(){
        boolean[] mc = new boolean[4];
        String appDataPath = System.getenv("LOCALAPPDATA");
        try (BufferedReader reader = new BufferedReader(new FileReader(appDataPath + "MacroSettings.txt"))) {
            String line;

            int index = 0;
            while ((line = reader.readLine()) != null) {
                mc[index] = Boolean.parseBoolean(line);
                index++;
            }
        } catch (IOException e) {
        }
        return mc;
    }

    /**
     * <p>
     * saveCurrent method takes the values of each of the macro datafields and writes them to the local files 
     * </p>
     */
    public static void saveCurrent() {
        String appDataPath = System.getenv("LOCALAPPDATA"); // Get the local application data directory
        ArrayList<Stack<ImageOperation>> allMacros = new ArrayList<>();
        allMacros.add(macro1);
        allMacros.add(macro2);
        allMacros.add(macro3);
        allMacros.add(macro4);
        String[] fileNames = { "Macro1.ops", "Macro2.ops", "Macro3.ops", "Macro4.ops" };

        for (int i = 0; i < allMacros.size(); i++) {
            try {
                File file = new File(appDataPath, fileNames[i]);
                FileOutputStream outputStream = new FileOutputStream(file);
                ObjectOutputStream objOut = new ObjectOutputStream(outputStream);

                objOut.writeObject(allMacros.get(i));

                objOut.close();
                outputStream.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * Sets macro1 to default value and saves 
     */
    public static void setDefaultMacro1() {
        ImageOperation[] m1 = { new SepiaFilter(ImageAction.getTarget().getWidth(), ImageAction.getTarget().getHeight()),
            new FlipHorizontal(),
            new MedianBlur(4, ImageAction.getTarget().getWidth(), ImageAction.getTarget().getHeight())};
        if (!macro1.isEmpty()) {
            macro1.clear();
        }
        for (ImageOperation op : m1) {
            macro1.push(op);
        }
        macroCustom[0]=false;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * Sets macro2 to default value and saves 
     */
    public static void setDefaultMacro2() {
        ImageOperation[] m2 = { new ConvertToGrey(ImageAction.getTarget().getWidth(), ImageAction.getTarget().getHeight()),
            new SobelFilters(true, ImageAction.getTarget().getWidth(), ImageAction.getTarget().getHeight()) };
        if (!macro2.isEmpty()) {
            macro2.clear();
        }
        for (ImageOperation op : m2) {
            macro2.push(op);
        }
        macroCustom[1]=false;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * Sets macro3 to default value and saves 
     */
    public static void setDefaultMacro3() {
        ImageOperation[] m3 = { new FlipVertical(), new RotateImage(1) };
        if (!macro3.isEmpty()) {
            macro3.clear();
        }
        for (ImageOperation op : m3) {
            macro3.push(op);
        }
        macroCustom[2]=false;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * Sets macro4 to default value and saves 
     */
    public static void setDefaultMacro4() {
        ImageOperation[] m4 = { new EmbossFilters(4, ImageAction.getTarget().getWidth(), ImageAction.getTarget().getHeight()),
            new GaussBlur(4, ImageAction.getTarget().getWidth(), ImageAction.getTarget().getHeight()),
            new BrightnessAndContrast(30, 50, ImageAction.getTarget().getWidth(),
                    ImageAction.getTarget().getHeight()) };
        if (!macro4.isEmpty()) {
            macro4.clear();
        }
        for (ImageOperation op : m4) {
            macro4.push(op);
            saveCurrent();
        }
        macroCustom[3]=false;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * <p>
     * getMacro method gets the macro that the user has made in order to assign it to a ToolBar macro
     * </p>
     * @return The Macro the user has made 
     */
    public static Stack<ImageOperation> getMacro() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("OPS Files", "ops");
        fileChooser.setFileFilter(filter);
        Stack<ImageOperation> operationStack = new Stack<ImageOperation>();

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getPath();
            try {
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream objIn = new ObjectInputStream(fileIn);
                //
                operationStack = (Stack<ImageOperation>) objIn.readObject();

                fileIn.close();
                objIn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return operationStack;
        }
        return null;
    }

    /**
     * Sets Macro 1 to the users custom macro and saves 
     */
    public static void setMacro1() {
        Stack<ImageOperation> newMacro = getMacro();
        macro1 = newMacro;
        macroCustom[0]=true;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * Sets Macro 2 to the users custom macro and saves 
     */
    public static void setMacro2() {
        Stack<ImageOperation> newMacro = getMacro();
        macro2 = newMacro;
        macroCustom[1]=true;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * Sets Macro 3 to the users custom macro and saves
     */
    public static void setMacro3() {
        Stack<ImageOperation> newMacro = getMacro();
        macro3 = newMacro;
        macroCustom[2]=true;
        setMacroCustom();
        saveCurrent();
    }

    /**
     * Sets Macro 4 to the users custom macro and saves
     */
    public static void setMacro4() {
        Stack<ImageOperation> newMacro = getMacro();
        macro4 = newMacro;
        macroCustom[3]=true;
        setMacroCustom();
        saveCurrent();
    }
}
