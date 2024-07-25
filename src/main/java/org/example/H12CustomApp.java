///////////////////////// TOP OF FILE COMMENT BLOCK ////////////////////////////
//
// Title:           Telling Jokes
// Course:          CS 200, Summer 2024
//
// Author:          Teresa Campbell
// Email:           tjcampbe@wisc.edu
// Lecturer's Name: Professor Jim Williams
//
///////////////////////////////// CITATIONS ////////////////////////////////////
//
//I used ChatGPT for the regex code.  I also used it to figure out how to use
//
//
///////////////////////////////// REFLECTION ///////////////////////////////////
//
// 1. This program takes a huge dataset of jokes, returns a subset of this
//    dataset, and then returns a smaller subset of these based on user input
//
// 2. Why did you choose the method header for the read file method (e.g.,
//      return type, parameters, throws clause)?
//    - The method reads the contents of a file and returns a list of strings,
//       where each string represents a line form the file without line numbers.
//      it also accepts a file object as a parameter,
//      which represents the file to be read.  Throws clause: because file
//      operations can lead to input/output exceptions.  The throws clause
//      allows
//      the program to handle these exceptions without breaking the application
//
// 3. Why did you choose the method header for the write file method
//    (e.g., return type, parameters, throws clause)?
//    - return type void: the method's purpose is to write data to a file, so it
//      does not need to return any value
//    - parameters: The method accepts a string representing the file path where
//      the data should be written.  It also accepts a list of strings where
//      each string represents a line of text to be written to the file.
//    - IOException: reading from and writing to a file can result in input/
//      output exceptions.
// 4. What are the biggest challenges you encountered: I struggled with trying
//    to figure out how to remove the line numbers and how to add line numbers
//    back in
// 3. What did you learn from this assignment: I enjoyed this assignment because
//    it really encompassed everything that we have learned in the course:
//     arrays, for loops, reading and writing from a file, exceptions, random,
//     scanner, and testing
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

//package org.example;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * This application processes a TSV file, filtering lines that contain
 * question marks,
 * and then writes the filtered lines to a new file. The user can then
 * specify how many
 * random jokes to retrieve from the filtered lines.
 *
 * @author Teresa Campbell
 */
public class H12CustomApp {

    /**
     * Reads all lines from the specified file into a list of strings,
     * ignoring initial numeric line numbers.
     *
     * @param inputFile The file to read from.
     * @return A list of lines from the file, with initial numeric line
     * numbers removed.
     * @throws IOException If an error occurs while reading the file.
     */
    public static List<String> readFileIgnoringLineNumbers(File inputFile)
            throws IOException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Remove initial numeric row number and space if present
                String cleanedLine = line.replaceAll("^\\d+\\s*",
                        "");
                lines.add(cleanedLine);
            }
        }
        return lines;

    }

    /**
     * Clears the contents of the specified file.
     *
     * @param filePath The path of the file to clear.
     * @throws IOException If an error occurs while clearing the file.
     */
    public static void clearFile(String filePath) {
        try (PrintWriter printWriter = new PrintWriter(filePath)) {
            printWriter.flush(); // Use the PrintWriter to avoid the warning
        } catch (IOException e) {
            System.err.println("An error occurred while clearing the file: "
                    + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Filters lines containing a question mark from the given list,
     * adding new line numbers.
     *
     * @param lines The list of lines to filter.
     * @return A list of lines that contain a question mark,
     * with new line numbers.
     */
    public static List<String> searchQuestionMarkInTsv(List<String> lines) {
        List<String> filteredLines = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("?")) {
                filteredLines.add(line);
            }
        }

        return filteredLines;
    }


    /**
     * Writes the specified lines to a file at the given path,
     * adding new line numbers.
     *
     * @param filePath The path of the file to write to.
     * @param lines    The list of lines to write.
     * @throws IOException If an error occurs while writing the file.
     */
    public static void writeFileWithLineNumbers(String filePath,
                                                List<String> lines)
            throws IOException {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            int lineNumber = 1;
            for (String line : lines) {
                // Write each line with a new line number
                writer.printf("%d: %s%n", lineNumber, line);
                lineNumber++;
            }
        }
    }


    /**
     * Returns a random sample of jokes from the given list, adding new
     * line numbers.
     *
     * @param lines The list of jokes.
     * @param numberOfJokes The number of random jokes to return.
     * @return A list of random jokes, with new line numbers.
     */
    public static List<String> getRandomJokes(List<String> lines,
                                              int numberOfJokes) {
        List<String> randomJokes = new ArrayList<>();
        Random random = new Random();
        int totalJokes = lines.size();

        // Ensure the number of jokes requested is not greater than the
        // available jokes
        numberOfJokes = Math.min(numberOfJokes, totalJokes);

        List<String> selectedJokes = new ArrayList<>();

        for (int i = 0; i < numberOfJokes; i++) {
            int randomIndex = random.nextInt(totalJokes);
            String selectedJoke = lines.get(randomIndex);
            // Remove line numbers from the selected joke
            String jokeWithoutNumber = selectedJoke.replaceAll
                    ("^\\d+:\\s*", "");
            selectedJokes.add(jokeWithoutNumber);
            lines.remove(randomIndex);
            totalJokes--;
        }

        // Add new row numbers to the selected jokes
        for (int i = 0; i < selectedJokes.size(); i++) {
            randomJokes.add((i + 1) + ": " + selectedJokes.get(i));
        }

        return randomJokes;
    }

    /**
     * Main method to execute the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Define file paths
        String inputFilePath =
                "/Users/teresacampbell/Desktop/H12CustomApp/src/main/" +
                        "resources/dad_jokes.tsv";
        String outputFilePath =
                "/Users/teresacampbell/Desktop/H12CustomApp/src/main/java" +
                        "/org/example/Jokes.tsv";

        // Run tests
        boolean passed = TestH12CustomApp.testH12CustomApp();
        System.out.println("Tests passed: " + passed);

        // Main application logic
        try {
            // Read and process jokes
            File inputFile = new File(inputFilePath);
            List<String> lines = H12CustomApp.readFileIgnoringLineNumbers
                    (inputFile);
            List<String> filteredLines = H12CustomApp.searchQuestionMarkInTsv
                    (lines);
            H12CustomApp.clearFile(outputFilePath);  // Clear the file before
            // writing
            H12CustomApp.writeFileWithLineNumbers(outputFilePath, filteredLines);

            // User input for number of jokes
            Scanner userInputScanner = new Scanner(System.in);
            System.out.println("How many jokes would you like to hear?");
            int numberOfJokes = 0;
            if (userInputScanner.hasNextInt()) {
                numberOfJokes = userInputScanner.nextInt();
            } else {
                System.out.println("Invalid input. Using default value of 5.");
                numberOfJokes = 5; // Default value if input is not an integer
            }

            // Get random sample of jokes
            List<String> randomJokes = H12CustomApp.getRandomJokes(filteredLines,
                    numberOfJokes);
            System.out.println("Random sample of jokes:");
            for (String joke : randomJokes) {
                System.out.println(joke);
            }

            userInputScanner.close(); // Close the scanner

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}