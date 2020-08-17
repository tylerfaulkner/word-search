/*
 * Course: CS 2852 - 71
 * Spring 2020
 * Lab 6
 * Name: Tyler Faulkner
 * Created: 04/24/2020
 */
package faulknert;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simulates a game board of a grid of letters.
 */
public class GameBoard {
    private static final int MAX_WORD_LENGTH = 15;
    private boolean findWordsRan = false;
    private long runtime;
    private AutoCompleter strategy;
    private int maxCol;
    private char[][] grid;

    /**
     * Sets the desired strategy for the game board.
     *
     * @param strategy an autocompleter child
     * @throws IllegalArgumentException thrown if the provided strategy has not been initialized
     */
    public GameBoard(AutoCompleter strategy) throws IllegalArgumentException {
        try {
            strategy.getLastOperationTime();
            this.strategy = strategy;
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException("Strategy needs to be initialized first.");
        }
    }

    /**
     * Loads the desired file that contains a grid of characters
     *
     * @param path the path to the file to be loaded
     * @throws FileNotFoundException if the provided path does not return a file
     */
    public void load(Path path) throws FileNotFoundException {
        Scanner count = new Scanner(path.toFile());
        Scanner in = new Scanner(path.toFile());
        int rows = 0;
        while (count.hasNextLine()) {
            maxCol = count.nextLine().length();
            rows++;
        }
        grid = new char[rows][maxCol];
        int row = 0;
        while (in.hasNextLine()) {
            String currentRow = in.nextLine();
            for (int i = 0; i < maxCol; ++i) {
                grid[row][i] = currentRow.charAt(i);
            }
            ++row;
        }
    }

    /**
     * Searches through the entire grid to find every possible word combination.
     *
     * @return a list of the the words found
     */
    public List<String> findWords() {
        long start = System.nanoTime();
        List<String> words = new ArrayList<>();
        for (int row = 0; row < grid.length; ++row) {
            for (int col = 0; col < maxCol; ++col) {
                words.addAll(recursiveSearch(row, col, new boolean[grid.length][maxCol], ""));
            }
        }
        findWordsRan = true;
        runtime = System.nanoTime() - start;
        return words;
    }

    /**
     * Returns the runtime of the findWords() method only after the method has ran.
     *
     * @return runtime as a long
     * @throws IllegalStateException if findWords() has not been run yet
     */
    public long findWordsRunTime() throws IllegalStateException {
        if (findWordsRan) {
            return runtime;
        } else {
            throw new IllegalStateException();
        }
    }

    private List<String> recursiveSearch(int row, int col,
                                         boolean[][] visitedFlags, String partialWord) {
        List<String> list = new ArrayList<>();
        partialWord = partialWord + grid[row][col];
        boolean[][] temp = new boolean[grid.length][maxCol];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < maxCol; j++) {
                temp[i][j] = visitedFlags[i][j];
            }
        }
        temp[row][col] = true;
        if (partialWord.length() >= 3 && strategy.contains(partialWord)) {
            list.add(partialWord);
        }
        if (partialWord.length() <= MAX_WORD_LENGTH &&
                strategy.allThatBeginsWith(partialWord).size() > 0) {
            if (row + 1 < grid.length && !temp[row + 1][col]) {
                list.addAll(recursiveSearch(row + 1, col, temp, partialWord));
            }
            if (row - 1 >= 0 && !temp[row - 1][col]) {
                list.addAll(recursiveSearch(row - 1, col, temp, partialWord));
            }
            if (col + 1 < maxCol && !temp[row][col + 1]) {
                list.addAll(recursiveSearch(row, col + 1, temp, partialWord));
            }
            if (col - 1 >= 0 && !temp[row][col - 1]) {
                list.addAll(recursiveSearch(row, col - 1, temp, partialWord));
            }
            if (row + 1 < grid.length && col + 1 < maxCol && !temp[row + 1][col + 1]) {
                list.addAll(recursiveSearch(row + 1, col + 1, temp, partialWord));
            }
            if (row + 1 < grid.length && col - 1 >= 0 && !temp[row + 1][col - 1]) {
                list.addAll(recursiveSearch(row + 1, col - 1, temp, partialWord));
            }
            if (row - 1 >= 0 && col + 1 < maxCol && !temp[row - 1][col + 1]) {
                list.addAll(recursiveSearch(row - 1, col + 1, temp, partialWord));
            }
            if (row - 1 >= 0 && col - 1 >= 0 && !temp[row - 1][col - 1]) {
                list.addAll(recursiveSearch(row - 1, col - 1, temp, partialWord));
            }
        }
        return list;
    }
}
