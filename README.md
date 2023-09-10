## <h1><span style="color:royalblue">ANDIE</span></h1>

Welcome to the README for ANDIE, a non-destructive image editor. Detailed below are the project structure, added feature, attributions of work and users guide for the PPS team members for the COSC202 ANDIE project.

## <h1><span style="color:royalblue">Table of Contents</span></h1>

- `Folder Structure`
- `Users Guide`
- `Features Added`
- `Group Members Contributions`
- `Testing`
- `Exception Handling`

## <h1><span style="color:royalblue">Folder Structure</span></h1>

The workspace for ANDIE contains the following folders:

- `Photos`: the folder contains images for project testing
- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

- `.gitingore` the list of folders ingored by git


Meanwhile, the compiled output files will be generated in the `bin` folder by default.


## <h1><span style="color:royalblue">Users Guide</span></h1>


ANDIE provide the user with the following 
options for loading and editing images. When the editor is first opened, only the functionality that is <u>`underlined`</u> is avalible to the user until a photo is opened, at which point all other functionality becomes available.


### <u>`File`</u> 
* <u>`Open:`</u> allows the user to traverse thought their files to find and open an image.
* `Save:` saves the current state of modification of an image.
* `Save As:` allows the user to save name and save an image in a place specified by the user.
* `Export:` Allows the user to export the current image without retainng the stack of operations perfomed. 
* <u>`Exit:`</u> allows the user to exit the program.
### Edit
* `Undo:` undo the last image action performed on the image if an action has already been performed.
* `Redo:` redo the last image action perfomed if a previous action has already been undone.
### View
* `Zoom In:` 
zooms in on the image.
* `Zoom Out:`
zooms out on the image.
* `Zoom Full:`
returns the image to the orignal zoom.
* `Zoom Slider:`
presents the user with a slider which has options to zoom from 50 to 150 percent, at the bottom of the window are `Ok` or `Cancel` option to confirm the zoom changes or exit the window.
### Filter
* `Mean Filter:` applies a Mean Filter to the image of a size determined by a radius input by the user bounded between 1 and 10.
* `Soft Blur:` applies a Soft Blur to the image.
* `Guass Blur:` applies a Gaussian Blur to the image of a size determined by a radius input by the user bounded between 1 and 10. 
* `Sharpen Filter:` applies a Sharpen Filter to the image.
* `Median Blur:` applies a Median Blur to the image of a size determined by a raduis input by the user bounded between 1 and 10.
* `Horozontal Sobel Filter:` applies a horizontal Sobel Filter to the image.
* `Vertical Sobel Filter:` applies a vertical Sobel Filter to the image.
* `Emboss Filter:` applies a Emboss Filter the image with the user being able to select one of eight Emboss options in a preview.
* `Sepia Filter:` applies a Sepia Filter to the image.
* `Saturation Filter:` applies a Saturation Filter to the image with the user being able to select between 10 and -10 saturation on a silder in the preview.

### Colour
* `Greyscale:` applies a Greyscale Filter to make the image black and white.
* `Brightness and Contrast:`
presents the user with a preview window that shows a smaller version of the image plus two sliders that go from -100 to 100, one for brightness and the other for contrast, 
the user can then use the `Ok` or `Cancel` button to confirm the changes or abort.
### Resize
* `Resize:` 
resize presents the user with a window that lets the user select a percentage to resize by, with options from 25% through to 200% in 25% increments with `Ok` and `Cancel` buttons to confirm the changes to the image or abort any new changes.
* `Crop Action:` This begins the process of cropping the image. Setting the mouse pointer to a cross hair, the user can click and drag to select the area of the image they wish to crop. Once the user has selected the area they wish to crop the crop is automatically applied.
### Scale Image
* `Horizontal Flip:` flips the image horizontally.
* `Vertical Flip:` flips the image vertically.
* `Rotate 90 Right:` rotates the image to the right 90 degrees.
* `Rotate 90 Left:` rotates the image to the left 90 degrees.
* `Rotate 180*:` rotates the image 180 degrees.
### <u>`Settings`</u>
* <u>`Settings:`</u> the settings panel opens a window that allows the user to <u>`Select language`</u> with options avliable being English, Maori or Italian and 
<u>`Select a country`</u> with the avalible countries being New Zealand and United States for English, New Zealand for Maori and Italy and Switzerland for Italian. The user may confirm their choice or exit using the <u>`Ok`</u> or <u>`Cancel`</u> options.

* `Configure Toolbar:` opens a window that allows the user to select from three windows labled `Configuration`, `Customise` and `Theme`. The `Configuration` window previews the editor with the toolbar and allows the user to select the location of the toolbar, the options being `Top`, `Left`, `Right` and `Bottom`. The `Customise` window allows the user to customise the four macro slots. With options to `Reset` or create a `New` macro for each of the four slots. The user can also use the `Theme` option to select a theme for the editor from four options, being `Navy`, `Gray`, `Black` and `White`, the user can then confirm their choice using the `Save` option or abandon changes using `Cancel`.
* `Create a Macro:` opens a small menu titled `Create a Macro` that allows the user to `Record` or `Finish Recording` a macro as well as  `Cancel` out of the panel.
* `Apply a Macro:` allows the user to travese through their files to find a macro, the macro will be applied when it is selected.
* `Report a Bug:` opens a window that allows the user to report an issue with the program, The report window has subject field `Enter a Subject:` and a general text box fo the user to write their issue into titled `Please provide as much detain as you can:`. Clicking `Ok` will send the report to the developers using the default email client or if this can't be done offers to copy the email to the users clipboard. `Cancel` will exit the window.
### Toolbar
* `Drawing:` Tools that allow the user to draw on the image from a list of drawing options being free pen, line, square, square with rounded corners, circle and eraser, this section also contains a select button which allows users to select an area of the image and apply any filter to specifically that are. There are also line size selectors and a colour selection button. By default this is located to the left of the toolbar.
* `Rotates and Flips:` Shortcuts to rotate an image 90 degrees to the left or right and flip it either horizontally or vertically are included towards the centre of the toolbar by default.
* `Undo and Redo:` Undo and Redo buttons are included for convenience towards the centre of the toolbar by default.
* `Zoom:` A user friendly zoom option is inclued in the toolbar and is displayed around the centre of the toolbar by default.
* `Macros:` The Macros section of the toolbar is located to the far right side of the toolbar in the default toolbar configration and allows the user to select from four pre-programed macros that can be applied to the image or apply their own customised macros which are set from the `Configure Toolbar` settings panel.

### Search Bar
* `Search Bar:` Included at the end of the menu items is a search option that allows the user to search for any features of the program they wish to use. The search has appropriate items disabled when no image is open and will display a message to the user if no results are found. The search bar is designed to stay on top of the window so that it is easily accessible to the user at all times.

### Keyboard Shortcuts
Bellow is a list of keyboard shortcuts included in the ANDIE program:

`ctrl+ options:`
- `s` = Save as
- `x` = Export
- `-` = Zoom out
- `+` = Zoom out
- `LeftArrow` = Undo
- `RightArrow` = Redo
- `r` = RotateRight
- `c` = Resize
- `t` = Settings
- `f` = Flipx
- `0` = SoftBlur
- `1` = EmbossFilter
- `2` = GaussBlur
- `3` = MeanFilter
- `4` = MedianBlur
- `5` = SaturationFilter
- `6` = SepiaFilter
- `7` = SharpenFilter
- `8` = VerticalSobelFilter
- `9` = HorizontalSobelFilter 

`ctrl+shift+ options:`
- `r` = RotateLeft
- `s` = SaveAs
- `f` = FlipY

`ctrl+alt+ options:`

- `r` = Resize

## <h1><span style="color:royalblue">Features Added</span></h1>

The features added and issues addressed for `Part 1` of the project are listed bellow. 


* `Brightness Adjustment `
* `Contrast Adjustment `
* `Execption Handling `
* `File Export, Differen `
* `Gaussian Filter `
* `Image Flip `
* `Image Resize `
* `Image Rotations, 90 Left, 90 Right 180 `
* `Median Filter `
* `Multilingual Support`
* `Other Error Avoidance / Prevention`
* `Sharpen Filter `
* `LoadScreen`
* `Centering and resizing the window.`
* `JUnit tests`
* `Preview colour/filter actions.`

The features added and issues addressed for `Part 2` are listed bellow:
* `Extend Sharpen Filter` to apply to the boundary
* `Extend Mean Filter` to apply to the boundary
* `Extend Median Filter` to apply to the boundary
* `Extend Gaussian Filter` to apply to the boundary
* `Edge detection Filter`
* `Horozontal Sobel Filter:`
* `Vertical Sobel Filter:`
* `Emboss Filter`
* `Sepia Filter`
* `Saturation Filter`
* `Negative values for Filters`
* `A default Toolbar`
* `Create a Macro`
* `Apply a Macro`
* `Mouse based image selection`
* `Drawing Options`
* `Image Cropping`
* `Keyboard Shortcuts`

Additinal extened features:
* `Image Overlay` to display a preview of the drawing, selecting and cropping while the user is dragging their mouse.
* `Search`
* `Customisable Toolbar` including changing the default macros
* `Theme Selection`
* `Loading Screen` to greet the user with a friendly image while the program loads.
* `Centering the current image within the frame.`
* `Report a Bug`



## <h1><span style="color:royalblue">Group Members Contributions</span></h1>
Credits and Acknowledgments:


### Hamish Dudley
* `Brightness Adjustment`
* `Contrast Adjustment`
* `File Export`
* `Implement Multilingual Support`
* `Application Settings`
    - `Static settings for use application wide`
    - `Locale dynamic update`
    - `Frame repacking`
* `Andie action interface and implementation`
* `Fix save so that pngs can saved`
* `Fix saving pngs so that if you manually add .jpg extension it still saves as PNG otherwise would fail to save with alpha chanels`
### Part 2:
* `Drawing Operations`
    - `Free Draw`
    - `Line Drawing`
    - `Rectangle Drawing`
    - `Rectangle with rounded corners Draw`
    - `Elipse Draw`
    - `Eraser`
* `Report a Problem featrue`
* `Mouse based image selection`
* `Image cropping`
* `Applying filters to selected area`
* `Apply filters in a relative scale` This allows for area selection to be applied to images of different sizes and still appear in the correct position on the image when used in macros. 
* `Javadoc`
* `Image Helper` This class contains methods that are used to extend all of the filters to work on the boundarys with image manipulation and selection.
* `GUI for Search`
* `Image Overlay` with Ben to display a preview of the drawing, selecting and cropping while the user is dragging their mouse.

### Charlie Templeton
* `Gaussian Filter`
* `Image Flip, Horizontal, Vertical `
* `Image Resize`
* `Zoom Slider`
* `Load Screen`
* `JUnit tests`
* `Other Error Avoidance / Prevention`
    - `Grey out the menus`
    - `Error handling for resize UI`
    - `Error handling for change of local text sizes in componets`
    - `Error handled for zoom full`
    - `Error handled for zoom slider`
    - `Error handled for button text when changing locale`
* `Execption Handling`
    - `Exception handling for resize UI` 
    - `Undo exception handling`
    - `Redo exception handling`
    - `File opening exception handling`
### Part 2:
* `Extend Gaussian Filter`
* `Horizontal Sobel Filter`
* `Vertical Sobel Filter`
* `All Eight of the Embos Filters`
* `Saturation Filter handled for Negative values`
* `Sepia Filter`
* `Saturation Filter`
* `Default Toolbar`
* `Toolbar Settings`
    - `Tool Bar Orientation`
    - `Custom Toolbar Macros, remembers between runs`
    - `ANDIE Theme, remembers between runs`
* `Macros`
    - `Undo and Redo for the whole Macro instead of one operation at a time`
    - `Create and Apply Macro Menus and Actions`
* `Updated bundle words`
* `Exception Handling`
    - `ToolBar Items`
    - `Filters`
    - `UI`

### Ben Nicholson
* `File Export `
* `Image Rotations, 90 Left, 90 Right & 180`
* `Sharpen Filter`
* `Preview filter / get user input.`
* `Added loading cursor for user indication`
* `Team organisation (utilized notion for organising tasks)`
* `Prompts`
  - `Added Opening prompt to save before opening a new image.`
  - `Added Exit Prompt to save.`
* `Other Error Avoidance / Prevention`
    - `Error Prevention on filter-previews (redesigned, used JSliders so no false inputs).`
    - `Error handling on imageOps. After an existing image has ops done on it (Created new Editable Image).`
### Part 2:
* `Extend Mean Filter`
* `Extend Sharpen Filter`
* `Drawing Operations`
    -`Inital peer programming with Hamish. We figured out broadly how to "draw" and to handle drawing errors like skipping, zooming etc.`
* `Image Overlay with Hamish to display a preview of the drawing, selecting and cropping while the user is dragging their mouse.`
* `Search (Hamish altered the GUI making it more polished.)`
    -`Searching Filter`
    -`Populating/Storing Searching Options`
    -`Updating Search Languages`
    -`Sorting the outputs`
    -`Basic GUI before Hamish's aforementioned improvement.`
* `Keyboard Shortcuts`
    -`Every shortcut option.`
* `Continous integration Pipeline`
    -`Added a pipeline to run tests and recreate a javadoc file with each commit to main.`
* `Exception Handling`
    -`SearchBar (If no image open certain options won't display that will break program)`
    -`KeyBindings (If no image open certain options won't display that will break program)`
    -`Overlay ensures that the overlay always scales with the zoom meaning our preview won't parse errors.`
* `Bug Fixes`
    - `Overlay scaling errors with zoom`
    - `Free-draw Skipping /w Hamish`
    - `Zooming draw errors (if zoomed actions would apply off-center) /w Hamish`


### Alex Poore
* `Median Filter `
* `Multilingual Support`
* `README sturcture`
### Part 2:
* `Extend Median Filter`
* `Macros`
    - `Basic first implementation`
* `JUnit Tests`
* `Non member specific README updates`

## <h1><span style="color:royalblue">Testing</span></h1>

Junit test were conducted for the following classes as presented below.

### GaussBlur JUnit Test:
The tests conducted in this file assess the functionality of two methods in `GaussBlur.java` specifically the `getGaussKernel` and `getGauss` methods. The tests were all passed in each case. 
For the `getGauss` method we tested that the values returned matched the values expected at points (x,y) and it performs as expected. For `getGaussKernel` we tested that the kernel that
was returned matched the expected kernel at different radius sizes and it passed all tests.

### ImagePanel JUnit Test:
The tests conducted in the file assess the functionality of the `setZoom` method in `ImagePanel.java`. The first test checked the initial value was 100 which passed and the second tests that the `getZoom` matches the 
zoom after calling the `setZoom` method which also passed.

### Resize JUnit Test:
The tests conducted in the file assess the functionality of the two methods in `Resizing.java`, the `isUseable` and `getPercentageChange` methods. The first test is for the `isUseable` method and we just made sure 
that for a bunch of different string cases the method returns what expected and all tests passed. The second test we made sure that for various different inouts that `getPercentageChange` method can convert it to the 
percentage to change as a int, and all tests passed.

Brute force testing was conducted by group members throught the development process.

Further test were conducted by allowing group members friends to try and break the program through their inputs to the program whilst it was running.

### Part 2:

### SobelFilters JUnit Test:
The test conducted assessed the functionality of the `getKernal` method in `SobelFilters.java`, the test checked that the expected kernal was applied depending on whether the filter was applied vertically or horizontally. the test passed.

### EmbossFilters JUnit Test:
The test was conducted to assess the functionality of the `getKernal` method in `EmbossFilters.java`, the test checked that correct kernalwas applied depending on the filter type specified. The test passed.

### SaturationFilter JUnit Test:
The test was conducted to assess the functonallity of the `setSaturation` method in `SaturationFilter.java`, the test checked if the saturation value had been set within the desired bounds. The test passed.



## <h1><span style="color:royalblue">Exceptions Handled</span></h1>
Below are a list of fixed issues encountered in the development process:

- `Grey out the menus`:
   <br> Added implementation for when no image has been opened the menus grey out and become unusable until an image has been opened.</br>

- `Error handling for resize UI`:
   <br> Added implementation so that if the user enters a unusable peice of text then it will present them with a error message and if they try to do it again it will indicate that the error count has increased. Fixed an issue where an operation in the ImageResize class tried to divide by zero.</br>


- `Error handling for change of local` 
    <br>Fixed an issue where program wouldn't open if language prefences were unsupported. Program now defaults to english if language bundle cannot be found. </br>

- `Error handled for zoom full`
    <br>Fixed an issue where Zoom Full wouldnt call the repaint() method which lead to errors.</br>

- `Error handled for zoom slider`
    <br>Made it so the zoom slider saves how zoomed in you are so next time you call the slider it is positioned 
    at the level of zoom you are currently at.</br>

- `Error handled for button text when changing local` 
    <br>Added error handling for when a word in one language is 
    longer than another, the program makes sure the text fits in the labels and buttons.</br>

- `Exception handling for resize UI` 
    <br>fixed an issue where reopening the resize option doesn't reset the selected item in the Jcombo box to 100%.</br>

- `Redo exception and Undo exception handling`
    <br>Added a exception prompt for when a user attempts to undo or redo images that have not been altered or undone.</br>

- `File opening exception handling`
    <br>Added an exception prompt for if the file that is attempting to be opened is not a image</br>
    <br>Added a method to "restart" the editableImage. So on opening we clear the operations and open a new image.<br>

- `Preview Menu Image Scaling`
    <br> Added functionality so the scaling is reflective of the actual image(e.g if a rotate has been applied)<br>

- `Opening new image, after an existing image has ops done on it.`
    <br>Fixed an issue where the stack of operations was not cleared when a new image was opened thus automatically applying all perviously done changes to the new image.<br>

- `Added Loading cursor for user indication`
    <br> Added loading cursors for all filters, gives indication to user that it's working, crop now abandons cropping if it dragged off the image and apply <br>

- `Free Draw Skipping`
    <br> Fixed an issue where when free drawing, if moved too fast, the mouselistener would skip certain pixels.<br>

- `Cropping off screen`
    <br> Fixed an issue where cropping of the side of the image woud cause an out of bounds error. <br>

- `Overlay not scaling correct`
    <br> Fixed an issue where the overlay wouldn't scale when using feature such as zoom and rotate. <br>

- `Drawing not mapping for overlay to imagepanel when zoomed.`
    <br> Fixed an issue where the drawing options would apply in the wrong place if the user had previously zoomed.<br>

- `Search options when image isn't open`
    <br> Added a feature that tells the user to oepn an image and presents them with an open option if they try to search for features without first opening an image.<br>

- `Search languages not updating`
    <br> Fixed an issue where the search option display failed to update properly when the program language was changed. <br>

   

    


