# AdventOfCode2021
My Advent of Code solutions 2021
See https://adventofcode.com/ for the challenges solved in this project. I made it through day 15, and got set up for day 16.

To set the project up, use Intellij.

Open the Project Structure window and make sure the project SDK and language level are set to Java 11 (8 might work here as well, I don't think I've used anything special from 11).
Run maven clean install.
You can run/debug the project from intellij by setting up an Application run configration with AppContextCreator specified as the class that contains the main method.

To run the solutions for any particular day, simply go to the core module and change the annotation in the ApplicationContextCreator constructor (In core/src/main/java/com/guillermo/leif/Application.java) to refer to the desired challenge (e.g. @Challenge12 refers to the solution from day 12).
The code for the challenges is available in the daily-challenges module.

The file-input reader is somewhat difficult to navigate, as I didn't use day-names for the different readers like I probably should have (i.e. FileReaderDay12 vs. CaveMapReader) just know that this module was used to read the inputs from the input files stored in the daily challenges resources folder, and generally doesn't do a whole lot of important logic realated to solving the challenges.

## Reflection on what I've done 05/21/2022
I think I may have overcomplicated the setup, but it is fairly simple to switch between the days, which is nice.
Since working on the advent of code for 2021, I have leveled up my OOP understanding significantly. I noticed I didn't really break out quite as many classes as I probably could have. One thing I've noticed doing real-world work is that following strong OOP principles isn't actually necessarily the best approach. What I've found is that it's important to get the task done in a way that works first. Follow some basic principles, but don't go overboard with it. The point is to get it done. Next, write unit tests (or if you're a code ninja, write unit tests first), and then if you still have time you can think/talk-to-people about how the system is likely to evolve and make things more maintainable for the potential futures.
