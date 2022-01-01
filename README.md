# AdventOfCode2021
My Advent of Code solutions 2021

To set the project up, use Intellij.

Open the Project Structure window and make sure the project SDK and language level are set to Jasa 11 (8 might work here as well, I don't think I've used anything special from 11).
Run maven clean install.
You can run/debug the project from intellij by setting up an Application run configration with AppContextCreator specified as the class that contains the main method.

To run the solutions for any particular day, simply go to the core module and change the annotation in the ApplicationContextCreator constructor (In core/src/main/java/com/guillermo/leif/Application.java) to refer to the desired challenge (e.g. @Challenge12 refers to the solution from day 12).
The code for the challenges is available in the daily-challenges module.

The file-input reader is somewhat difficult to navigate, as I didn't use day-names for the different readers like I probably should have (i.e. FileReaderDay12 vs. CaveMapReader) just know that this module was used to read the inputs from the input files stored in the daily challenges resources folder, and generally doesn't do a whole lot of important logic realated to solving the challenges.
