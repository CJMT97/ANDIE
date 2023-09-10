package cosc202.andie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * <p>
 * Class LoadPanel creates the imagery for the loading screen seen before ANDIE
 * opens
 * </p>
 * 
 * @author Charlie Templeton
 */
public class LoadPanel extends JPanel {

    /**
     * <p>
     * Constructor sets panel color and adds an image and a label to create the load
     * panel
     * </p>
     */
    public LoadPanel() {
        // Sets layout
        setLayout(null);

        // Adds a title message saying opening ANDIE
        JLabel title = new JLabel("ANDIE");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setBounds(0, 0, 400, 100);
        title.setForeground(Color.white);
        

        // Adds image to panel in a JLabel
        ImageIcon imageIcon = new ImageIcon(getClass().getResource("/loadscreen.png"));
        Image image = imageIcon.getImage().getScaledInstance(375, 295, Image.SCALE_SMOOTH); // scale the image to a
                                                                                            // preferred size
        ImageIcon scaledIcon = new ImageIcon(image); // create a new ImageIcon object with the scaled image
        JLabel imageLabel = new JLabel(scaledIcon); // create a JLabel with the scaled ImageIcon
        imageLabel.setBounds(7, 40, 400, 400);
        
        setPreferredSize(new Dimension(400, 300));

        // Sets Panel Color
        setBackground(Color.black);

        add(title);
        add(imageLabel); // add the JLabel to the panel

        

    }
}
