package cosc202.andie;

/**
 * <p>
 * An interface definfing the methods required to iterate over the different
 * menus in the menu bar and set their titles and childrens titles.
 * This also defines the methods of disabling and enabling the menus.
 * </p>
 * 
 * @author Hamish Dudley
 * @version 1.0
 */
public interface AndieAction {
    /** A method to enable the menu */
    public void setUsable();

    /** A method to disable the menu */
    public void setUnusable();

    /**
     * A method to set the title of the menu.
     * 
     * @param text The new title
     */
    public void setTitleText(String text);

    /**
     * A method to set the title of a menus child.
     * 
     * @param item The ith child of the menu which we want to set the text of.
     * @param text The new title for this child.
     */
    public void setItemText(int item, String text);

    /**
     * A method to set the prompt of a menus child.
     * 
     * @param item The ith child of the menu which we want to set the prompt of.
     * @param text The new prompt for this child.
     */
    public void setItemPrompt(int item, String text);

    /**
     * A method to get the name of a child in the menu.
     * This name can be used to represent the message bundle key of the item.
     * 
     * @param item The ith child of the menu which we want to get the name of.
     * @return The name of the ith child.
     */
    public String getItem(int item);

    /**
     * A method to get the number of children in the menu.
     * 
     * @return The number of children in the menu.
     */
    public int getItemCount();

}
