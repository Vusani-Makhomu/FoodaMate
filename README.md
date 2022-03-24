Author: Vusani Makhomu. 
Email: Makhomuvusani23@gmail.com

How to run the program locally:

1. Open the command line in the java directory of the project (Figure 1.1 in diagrams.pdf)
2. Enter the command: javac org/foodamate/careers/com/Main.java (Figure 1.2 in diagrams.pdf)
   [Note: The purpose of the command entered at 2. is to compile the main.java program]
3. Verify that the program compiled successfully by checking if .class files were generated. (APIConnection.class, Graph.class, Main.class and ParseAPIContents.class)
   [Note: Take a look at Figure 1.3 in diagrams.pdf]
4. Enter the command: java org/foodamate/careers/com/Main (Figure 1.4 in diagrams.pdf)
   The program produced by your program might differ from the program in diagrams.pdf depending on the data from the API.
   [Note: The purpose of the command entered at 3. is to run the program that was compiled at 2.]
5. This will execute the program using the default date range.
   [Note: Read "how it work" to know more about the default date range.]
6. The program (main.java) has a help feature which can be useful for knowing more about how to use the program
7. To use the help feature, simply enter the command (assuming that you have done step 2.): java org/foodamate/careers/com/Main help
   [Figure 1.5 in diagrams.pdf]
8. To execute the program with specific date range, enter the command (assuming that you have done step 2.): 
   java org/foodamate/careers/com/Main [start_date] [end_date]
   [Note: [start_date] represent the start date that you want, the same is true of [end_date]]]
   Here is an example with real date. start date is 10-01-2022 and end date is 15-01-2022. The command would be:
   java org/foodamate/careers/com/Main 10-01-2022 15-01-2022
   [Figure 1.6 in diagrams.pdf]
9. If there is no data for the specified data range and therefore cannot plot the graph, 
    the program will let you know.
   [Figure 1.7 in diagrams.pdf]


How it works:

The data from the API is retrieved by APIConnection. This object (class) stores the api url, and has the functionality to retrieve
the api contents and to return it to the calling code. 

Once the data has been received, it is passed to ParseAPIContents. This object (class) cleans the data that was retrieved.
Since it was retrieved as bytes, and not as JSON data, it contains curly brackets. ParseAPIContents removes these brackets.
Then it breaks each key-value pair and store it in an array as 1 element. Double quotes are then removed from the key, and 
the colon (:) between these 2 is replaced by an equal sign. Parsing is complete.
ParseAPIContents has functionality that allows the calling code to retrieve the parsed data. 

After the parsed data has been retrieved, it is passed to Graph, along with a date range (either the one specified by the user
or the default one which is the start date and the end date of the API data). This object (class) is responsible for 
plotting the graph.
This class first extracts the user base values and the date values separately. This is done by separating each element of
the array that was passed in as APIData. This process is done by separating the key and value of array element.
Once the user base data has been extracted, the percentage increase of the user base is then determined.
After this is done, the graph is plotted. Each user base data, and the percentage increase is used to construct the graph line.
To find the number of asterisks to print for a user base, its percentage increase is divided by 100. With this approach,
the number of asterisks on screen will never exceed 100.

The calling code then retrieves the plotted graph and prints it on the console. This is done by printing out each line of 
the graph.

![Foodamate_Repo_Cover](https://user-images.githubusercontent.com/79424556/160018558-b6946285-0082-4e34-8f4f-b85a8442f2b1.jpg)

