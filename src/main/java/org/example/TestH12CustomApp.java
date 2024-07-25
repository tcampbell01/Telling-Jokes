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
// I used ChatGPT to use List<String> testLines = List.of for test data -->
// update: I removed list.of because zybooks didn't like it
//
/////////////////////////////// 80 COLUMNS WIDE ////////////////////////////////

//package org.example;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for H12CustomApp.
 */
public class TestH12CustomApp {

    // File paths for testing
    private static final String INPUT_FILE_PATH = "test_input.tsv";
    private static final String OUTPUT_FILE_PATH = "test_output.tsv";

    // Test data
    private static final List<String> testLines;

    static {
        testLines = new ArrayList<>();
        testLines.add("1: What do you get when you cross an angry sheep with " +
                "a moody cow? An animal that's in a baaaaad mooood.");
        testLines.add("2: What sounds like a sneeze and is made of leather? " +
                "A shoe.");
        testLines.add("3: Why did the scarecrow win an award? Because he was " +
                "outstanding in his field.");
        testLines.add("4: Why don't scientists trust atoms? Because they make " +
                "up everything.");
    }


    /**
     * Runs all the test cases for the H12CustomApp.
     *
     * @throws IOException if an I/O error occurs during testing
     */
    /**
     * Runs all the test cases for the H12CustomApp and returns true if all t
     * ests pass, false otherwise.
     *
     * @return true if all tests pass, false otherwise
     */
    public static boolean testH12CustomApp() {
        try {
            // Run tests
            testReadFileIgnoringLineNumbers(testLines);
            testClearFile();
            testSearchQuestionMarkInTsv(testLines);
            testWriteFileWithLineNumbers(testLines);
            testGetRandomJokes(testLines);
            testReadFileNonExistentFile();
            testWriteFileNonExistentPath();
            testWriteFileWithInvalidData();

            System.out.println("All tests passed.");
            return true; // All tests passed
        } catch (IOException e) {
            System.err.println("An error occurred during testing: " +
                    e.getMessage());
            return false; // Test failed due to an exception
        }
    }

    /**
     * Tests the readFileIgnoringLineNumbers method of H12CustomApp.
     *
     * @param testLines the test data to write and read
     * @throws IOException if an I/O error occurs during testing
     */
    private static void testReadFileIgnoringLineNumbers(List<String> testLines)
            throws IOException {
        H12CustomApp.writeFileWithLineNumbers(INPUT_FILE_PATH, testLines);
        List<String> lines = H12CustomApp.readFileIgnoringLineNumbers
                (new File(INPUT_FILE_PATH));
        assert lines.size() == 4 : "Test failed: Expected 4 lines, got "
                + lines.size();
        assert lines.get(0).equals("What do you get when you cross an angry " +
                "sheep with a moody cow? An animal that's in a baaaaad mooood.")
                : "Test failed: Unexpected line content";
    }

    /**
     * Tests the clearFile method of H12CustomApp.
     *
     * @throws IOException if an I/O error occurs during testing
     */
    private static void testClearFile() throws IOException {
        H12CustomApp.clearFile(OUTPUT_FILE_PATH);
        File file = new File(OUTPUT_FILE_PATH);
        assert file.exists() : "Test failed: File does not exist";
        assert file.length() == 0 : "Test failed: File is not empty after" +
                " clearing";
    }

    /**
     * Tests the searchQuestionMarkInTsv method of H12CustomApp.
     *
     * @param testLines the test data to write and read
     * @throws IOException if an I/O error occurs during testing
     */
    private static void testSearchQuestionMarkInTsv(List<String> testLines)
            throws IOException {
        H12CustomApp.writeFileWithLineNumbers(INPUT_FILE_PATH, testLines);
        List<String> lines = H12CustomApp.readFileIgnoringLineNumbers
                (new File(INPUT_FILE_PATH));
        List<String> filteredLines = H12CustomApp.searchQuestionMarkInTsv(lines);
        assert filteredLines.size() == 2 : "Test failed: Expected 2 lines with" +
                " question marks";
        assert filteredLines.get(0).contains("?") : "Test failed: First filtered" +
                " line does not contain a question mark";
    }

    /**
     * Tests the writeFileWithLineNumbers method of H12CustomApp.
     *
     * @param testLines the test data to write
     * @throws IOException if an I/O error occurs during testing
     */
    private static void testWriteFileWithLineNumbers(List<String> testLines)
            throws IOException {
        H12CustomApp.writeFileWithLineNumbers(OUTPUT_FILE_PATH, testLines);
        List<String> lines = H12CustomApp.readFileIgnoringLineNumbers
                (new File(OUTPUT_FILE_PATH));
        assert lines.size() == 4 : "Test failed: Expected 4 lines, got " +
                lines.size();
    }

    /**
     * Tests the getRandomJokes method of H12CustomApp.
     *
     * @param testLines the test data to filter and get random jokes from
     * @throws IOException if an I/O error occurs during testing
     */
    private static void testGetRandomJokes(List<String> testLines)
            throws IOException {
        List<String> filteredLines = H12CustomApp.searchQuestionMarkInTsv
                (testLines);
        List<String> randomJokes = H12CustomApp.getRandomJokes
                (filteredLines, 2);
        assert randomJokes.size() == 2 : "Test failed: Expected 2 random jokes";
        assert randomJokes.get(0).contains("?") : "Test failed: Random joke " +
                "does not contain a question mark";
    }

    /**
     * Tests reading from a non-existent file using the
     * readFileIgnoringLineNumbers method of H12CustomApp.
     */
    private static void testReadFileNonExistentFile() {
        try {
            List<String> lines = H12CustomApp.readFileIgnoringLineNumbers
                    (new File("nonexistent_file.tsv"));
            assert lines.isEmpty() : "Test failed: Expected empty list for " +
                    "nonexistent file";
        } catch (IOException e) {
            // Expected behavior: IOException for nonexistent file
            System.out.println("testReadFileNonExistentFile passed.");
        }
    }

    /**
     * Tests writing to a non-existent path using the writeFileWithLineNumbers
     * method of H12CustomApp.
     */
    private static void testWriteFileNonExistentPath() {
        try {
            H12CustomApp.writeFileWithLineNumbers("invalid/path/to/file.tsv",
                    List.of("Sample line"));
            assert false : "Test failed: Expected IOException for invalid path";
        } catch (IOException e) {
            System.out.println("testWriteFileNonExistentPath passed.");
        }
    }

    /**
     * Tests writing with invalid data using the writeFileWithLineNumbers
     * method of H12CustomApp.
     */
    private static void testWriteFileWithInvalidData() {
        try {
            // Use a valid file path
            String testFilePath = OUTPUT_FILE_PATH;
            H12CustomApp.writeFileWithLineNumbers(testFilePath, null);
            assert false : "Test failed: Expected NullPointerException for " +
                    "invalid data";
        } catch (IOException e) {
            // Expected behavior: IOException for invalid data
            System.out.println("testWriteFileWithInvalidData passed.");
        } catch (NullPointerException e) {
            // Expected behavior: NullPointerException for invalid data
            System.out.println("testWriteFileWithInvalidData passed.");
        }
    }
}
