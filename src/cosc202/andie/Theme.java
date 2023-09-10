package cosc202.andie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * <p>
 * Class Theme is responsible for keeping track of what theme the user has selected and saved 
 * </p>
 * @author Charlie Templeton
 */
public class Theme {

    /**
     * <p>
     * getTheme method retreives the value in the local file whic represents theme
     * if no value exist it sets it to 4 which is the default 
     * </p>
     * @return The theme of the program represented by an Int
     */
    public static int getTheme(){
        int x = 4;
        String appDataPath = System.getenv("LOCALAPPDATA");
        try (BufferedReader reader = new BufferedReader(new FileReader(appDataPath + "Theme.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                x = Integer.parseInt(line);
            }
        } catch (IOException e) {
        }
        return x;
    }

    /**
     * <p>
     * setTheme gets recieves the current theme that is selected and writes it to a local file
     * </p>
     * @param theme The theme that is currently selected
     */
    public static void setTheme(int theme){
        String appDataPath = System.getenv("LOCALAPPDATA");
        String fileText = "" + theme;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appDataPath + "Theme.txt"))) {
            writer.write(fileText);
        } catch (IOException e) {
        }
}
}
