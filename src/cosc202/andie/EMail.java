package cosc202.andie;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * A class to help send emails
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public class EMail {

    /**
     * A method to send an email to the given address with the given subject and
     * body using the default email client. If the default email client is not
     * supported, the option is given to copy to the clipboard.
     * 
     * @param to      The email address to send the email to
     * @param subject The subject of the email
     * @param body    The body of the email
     */
    public static void sendMail(String to, String subject, String body) {
        Desktop desktop = Desktop.getDesktop();
        if (desktop.isSupported(Desktop.Action.MAIL)) {
            try {
                StringBuilder sb = new StringBuilder();

                sb.append("mailto:");
                sb.append(encodeString(to));
                sb.append("?subject=");
                sb.append(encodeString(subject));
                sb.append("&body=");
                sb.append(encodeString(body));
                URI mailto = new URI(sb.toString());
                desktop.mail(mailto);
                // System.out.println(sb.toString());
            } catch (URISyntaxException e) {
                showError(subject, body);
            } catch (IOException e) {
                showError(subject, body);
            }
        } else {
            JLabel label = new JLabel("We failed to send your report. Would you like to copy the email to clipboard?");
            String[] okAndCancel = { Settings.getMessage("OK"), Settings.getMessage("CANCEL") };
            int option = JOptionPane.showOptionDialog(null, label, "Send email failed!",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE, null, okAndCancel, okAndCancel[0]);
            if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
                return;
            } else if (option == JOptionPane.OK_OPTION) {
                // Ok was pressed
                showError(subject, body);
            }
        }
    }

    /**
     * A method to say the email could not be sent and give an option to copy the
     * details to the clipboard
     * 
     * @param subject The subject of the email
     * @param body    The body of the email
     */
    private static void showError(String subject, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("Send email to: hamishdudley3011+PPS-teamP@gmail.com");
        sb.append("\n");
        sb.append("Subject: ");
        sb.append(subject);
        sb.append("\n");
        sb.append("Body: ");
        sb.append(body);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new java.awt.datatransfer.StringSelection(sb.toString()), null);
        JLabel copiedLabel = new JLabel(
                "Email details copied to clipboard. Please paste into your email client.");
        JOptionPane.showMessageDialog(new JFrame(), copiedLabel,
                "Send email failed!",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * A method to encode a string to be used in a URL
     * Spaces are encoded as %20 instead of + since this is how emails read them
     * 
     * @param s The string to encode
     * @return The encoded string
     * @throws UnsupportedEncodingException If the encoding is not supported, should
     *                                      never be thrown as this is hard coded
     */
    private static String encodeString(String s) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        while (s.length() > 0) {
            if (s.contains(" ")) {
                sb.append(URLEncoder.encode(s.substring(0, s.indexOf(" ")), StandardCharsets.UTF_8.toString()));
                sb.append("%20");
                s = s.substring(s.indexOf(" ") + 1);
            } else {
                sb.append(URLEncoder.encode(s, StandardCharsets.UTF_8.toString()));
                s = "";
            }
        }
        return sb.toString();
    }

}
