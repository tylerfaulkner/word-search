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
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;

/**
 * A Command Line Interface to search for words in a character grid file.
 */
public class WordSearchCLI {
    private static final int DOUBLE_DIGITS = 10;
    private static final double SECONDS_TO_MINUTE = 60;
    private static final double NANO_TO_MICRO = 1000;
    private static final double NANO_TO_MILLI = 1000000;
    private static final double NANO_TO_SECONDS = 1000000000;

    public static void main(String[] args) {
        Path gridPath = Paths.get(args[0]);
        String wordPath = args[1];
        String strategyChosen = args[2];
        AutoCompleter strategy;
        long runtime = 0;
        try {
            if (strategyChosen.equals("SortedArrayList")) {
                strategy = new SortedArrayStrategy();
                strategy.initialize(wordPath);
                GameBoard gameBoard = new GameBoard(strategy);
                gameBoard.load(gridPath);
                List<String> words = gameBoard.findWords();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < words.size(); ++i) {
                    sb.append(words.get(i));
                    if (i != words.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println(sb.toString());
                System.out.println("Total word count: " + words.size());
                runtime = gameBoard.findWordsRunTime();
            } else if (strategyChosen.equals("ArrayListIndexed")) {
                strategy = new IndexArrayStrategy();
                strategy.initialize(wordPath);
                GameBoard gameBoard = new GameBoard(strategy);
                gameBoard.load(gridPath);
                List<String> words = gameBoard.findWords();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < words.size(); ++i) {
                    sb.append(words.get(i));
                    if (i != words.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println(sb.toString());
                System.out.println("Total word count: " + words.size());
                runtime = gameBoard.findWordsRunTime();
            } else if (strategyChosen.equals("LinkedListIndexed")) {
                strategy = new IndexArrayStrategy();
                strategy.initialize(wordPath);
                GameBoard gameBoard = new GameBoard(strategy);
                gameBoard.load(gridPath);
                List<String> words = gameBoard.findWords();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < words.size(); ++i) {
                    sb.append(words.get(i));
                    if (i != words.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println(sb.toString());
                System.out.println("Total word count: " + words.size());
                runtime = gameBoard.findWordsRunTime();
            } else if (strategyChosen.equals("ArrayListIterated")) {
                strategy = new ForEachArrayStrategy();
                strategy.initialize(wordPath);
                GameBoard gameBoard = new GameBoard(strategy);
                gameBoard.load(gridPath);
                List<String> words = gameBoard.findWords();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < words.size(); ++i) {
                    sb.append(words.get(i));
                    if (i != words.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println(sb.toString());
                System.out.println("Total word count: " + words.size());
                runtime = gameBoard.findWordsRunTime();
            } else if (strategyChosen.equals("LinkedListIterated")) {
                strategy = new ForEachLinkedStrategy();
                strategy.initialize(wordPath);
                GameBoard gameBoard = new GameBoard(strategy);
                gameBoard.load(gridPath);
                List<String> words = gameBoard.findWords();
                StringBuilder sb = new StringBuilder();
                sb.append("[");
                for (int i = 0; i < words.size(); ++i) {
                    sb.append(words.get(i));
                    if (i != words.size() - 1) {
                        sb.append(", ");
                    }
                }
                sb.append("]");
                System.out.println(sb.toString());
                System.out.println("Total word count: " + words.size());
                runtime = gameBoard.findWordsRunTime();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The provided file could not be found in the directory.");
        }
        if (runtime / NANO_TO_MICRO < 1) {
            System.out.println("Runtime: " + runtime + " nanoseconds");
        } else if (runtime / NANO_TO_MILLI < 1) {
            System.out.println("Runtime: " + runtime / NANO_TO_MICRO + " microseconds");
        } else if (runtime / NANO_TO_SECONDS < 1) {
            System.out.println("Runtime: " + runtime / NANO_TO_MILLI + " milliseconds");
        } else {
            double seconds = runtime / NANO_TO_SECONDS;
            int minutes = 0;
            while (seconds / SECONDS_TO_MINUTE >= 1) {
                seconds = seconds - SECONDS_TO_MINUTE;
                ++minutes;
            }
            DecimalFormat df = new DecimalFormat("##.##");
            if (minutes >= DOUBLE_DIGITS && seconds >= DOUBLE_DIGITS) {
                System.out.println("Runtime: " + minutes + ":" + df.format(seconds));
            } else if (seconds >= DOUBLE_DIGITS && minutes < DOUBLE_DIGITS) {
                System.out.println("Runtime: " + "0" + minutes + ":" + df.format(seconds));
            } else if (minutes >= DOUBLE_DIGITS && seconds < DOUBLE_DIGITS) {
                System.out.println("Runtime: " + minutes + ":" + "0" + df.format(seconds));
            } else {
                System.out.println("Runtime: " + "0" + minutes + ":" + "0" + df.format(seconds));
            }
        }
    }
}
