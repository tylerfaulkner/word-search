/*
 * Course: CS 2852 - 71
 * Spring 2020
 * Lab 6
 * Name: Tyler Faulkner
 * Created: 04/24/2020
 */
package faulknert;

import java.util.List;

/**
 * Interface that layouts the required methods for a strategy in the autocompleter program.
 */
public interface AutoCompleter {

    /**
     * Used to initialize the strategy to run correctly.
     * @param filename name of file to search
     */
    void initialize(String filename);

    /**
     * Iterates through the list and produces a new list of all elements with the matching prefix
     * @param prefix string to contain
     * @return List of strings that contain the prefix
     */
    List<String> allThatBeginsWith(String prefix);

    /**
     * Return lastOperationTime
     * @return long of lastOperationTime
     */
    long getLastOperationTime();

    /**
     * Checks if the target word is in the list
     * @param target word to be searched for
     * @return true if a match is found
     */
    boolean contains(String target);
}
