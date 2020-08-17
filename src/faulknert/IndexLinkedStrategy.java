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
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * The search strategy utilizing a linked list and its get method.
 */
public class IndexLinkedStrategy implements AutoCompleter {
    private LinkedList<String> linkedList;

    private boolean initialized = false;

    private long lastOperationTime;

    @Override
    public void initialize(String filename) {
        long start = System.nanoTime();
        try(Scanner in = new Scanner(new File(filename))){
            String ext = filename.substring(filename.lastIndexOf('.'));
            linkedList = new LinkedList<>();
            if(ext.equals(".txt")) {
                while (in.hasNextLine()) {
                    linkedList.add(in.nextLine());
                }
            } else {
                in.useDelimiter(",|\\n");
                while (in.hasNextLine()){
                    in.next();
                    linkedList.add(in.nextLine().substring(1));
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
            LinkedList<String> matches = new LinkedList<>();
            for (int i = 0; i < linkedList.size(); ++i) {
                try {
                    if (linkedList.get(i).substring(0, prefix.length()).
                            toUpperCase().equals(prefix)) {
                        matches.add(linkedList.get(i));
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
        for(int i = 0; i < linkedList.size(); ++i){
            if(linkedList.get(i).toUpperCase().equals(target)){
                found = true;
            }
        }
        return found;
    }
}
