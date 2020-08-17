/*
 * Course: CS 2852 - 71
 * Spring 2020
 * Lab 6
 * Name: Tyler Faulkner
 * Created: 04/24/2020
 */
package faulknert;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The search strategy that utilizes a arraylist and a for each loop.
 */
public class ForEachArrayStrategy implements AutoCompleter {
    private ArrayList<String> arrayList;

    private boolean initialized = false;

    private long lastOperationTime;

    @Override
    public void initialize(String filename) {
        long start = System.nanoTime();
        try(Scanner in = new Scanner(new File(filename))){
            String ext = filename.substring(filename.lastIndexOf('.'));
            arrayList = new ArrayList<>();
            if(ext.equals(".txt")) {
                while (in.hasNextLine()) {
                    arrayList.add(in.nextLine());
                }
            } else {
                in.useDelimiter(",|\\n");
                while (in.hasNextLine()){
                    in.next();
                    arrayList.add(in.nextLine().substring(1));
                }
            }
            initialized = true;
            lastOperationTime = System.nanoTime() - start;
        } catch (FileNotFoundException e){
            lastOperationTime = System.nanoTime() - start;
            System.out.println("File not found.");
        }
    }

    @Override
    public List<String> allThatBeginsWith(String prefix) throws IllegalStateException {
        long start = System.nanoTime();
        if(initialized) {
            ArrayList<String> matches = new ArrayList<>();
            for (String word: arrayList) {
                try {
                    if (word.substring(0, prefix.length()).toUpperCase().equals(prefix)) {
                        matches.add(word);
                    }
                } catch (IndexOutOfBoundsException e){
                    //used to skip word if search term is longer than word
                }
            }
            lastOperationTime = System.nanoTime() - start;
            return matches;
        } else{
            lastOperationTime = System.nanoTime() - start;
            throw new IllegalStateException();
        }
    }

    @Override
    public long getLastOperationTime() {
        if(initialized) {
            return lastOperationTime;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public boolean contains(String target) {
        boolean found = false;
        for(String string : arrayList){
            if(target.equals(string.toUpperCase())){
                found = true;
            }
        }
        return found;
    }
}
