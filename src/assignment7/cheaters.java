/* CHEATERS!
 * EE422C Project 7 submission by
 * Shrey Sachdeva
 * ss77335
 * 15455
 * Kylar Osborne
 * kmo785
 * 15455
 * Slip days used: <0>
 * Spring 2018
 */

package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class cheaters {
    // HashMap of all the N-word phrases in every file
    private static Map<String, ArrayList<Integer>> phrases = new HashMap<>();

    /**
     * Removes any non-letter, non-digit characters from a String
     * @param string is the string to purify
     * @return an uppercase purified String
     */
    private static String purify(String string) {
        String purifiedString = "";
        for(int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            // Only add letters and digits to the purified String
            if(Character.isLetterOrDigit(c)) {
                purifiedString += c;
            }
        }
        return purifiedString.toUpperCase();
    }

    /**
     * Determines which documents are plagiarised
     * @param args are the program arguments
     */
    public static void main(String args[]) {
        String filePath = args[0];
        int N = Integer.parseInt(args[1]);
        int minNum = Integer.parseInt(args[2]);
        // Get the files in the provided folder
        File f = new File(filePath);
        File[] files = f.listFiles();
        // Create fileObjects for each file
        ArrayList<FileObject> fileObjects = new ArrayList<>();
        for(int i = 1; i <= files.length; i++) {
            FileObject fileObject = new FileObject(files[i-1], i);
            fileObjects.add(fileObject);
        }
        // Add all possible N-word phrases to the phrases HashMap
        for(FileObject fileObject : fileObjects) {
            try {
                FileReader fileReader = new FileReader(fileObject.getFile());
                BufferedReader inFile = new BufferedReader(fileReader);
                // Add all Strings in a file to an ArrayList
                String string = inFile.readLine();
                ArrayList<String> strings = new ArrayList<>();
                while(string != null) {
                    String[] fileStrings = string.split("\\s+");
                    for(String s : fileStrings) {
                        strings.add(s);
                    }
                    string = inFile.readLine();
                }
                // Create all possible N-word phrases
                for(int i = 0; i < strings.size() - N; i++) {
                    String key = "";
                    for(int j = 0; j < N; j++) {
                        key += purify(strings.get(i + j));
                    }
                    // Add the N-word key to phrases
                    ArrayList<Integer> filesWithKey;
                    if(phrases.containsKey(key)) {
                        filesWithKey = phrases.get(key);
                    }
                    else {
                        filesWithKey = new ArrayList<>();
                    }
                    filesWithKey.add(fileObject.getFileNum());
                    phrases.put(key, filesWithKey);
                }
            } catch(Exception e) {
                System.out.println("Error in opening file" + fileObject.getFileNum());
            }
        }
        // Determine the number of similarities between every possible pair of files
        int[][] similarities = new int[files.length+1][files.length+1];
        Set<String> keys = phrases.keySet();
        // Update the value at the intersection of the two files in the 2-D array as similarities are detected
        for(String key : keys) {
            ArrayList<Integer> filesWithKey = phrases.get(key);
            if(filesWithKey.size() > 1) {
                for(int i = 0; i < filesWithKey.size(); i++) {
                    int file1 = filesWithKey.get(i);
                    for(int j = i+1; j < filesWithKey.size(); j++) {
                        int file2 = filesWithKey.get(j);
                        if(file1 < file2) {
                            similarities[file1][file2]++;
                        }
                        else if(file2 < file1) {
                            similarities[file2][file1]++;
                        }
                    }
                }
            }
        }
        // Create an ordered queue of FilePairs to sort the files based on number of similarities before outputting
        PriorityQueue<FilePair> FilePairs = new PriorityQueue<>();
        for(int i = 1; i < files.length+1; i++) {
            for(int j = i+1; j < files.length+1; j++) {
                if(similarities[i][j] >= minNum) {
                    String n1 = fileObjects.get(i-1).getFileName();
                    String n2 = fileObjects.get(j-1).getFileName();
                    FilePairs.add(new FilePair(n1,n2,similarities[i][j] ) );
                }
            }
        }
        // Output the plagiarised documents
        while(!FilePairs.isEmpty()){
            System.out.println(FilePairs.poll());
        }
    }

    /**
     * Class to represent a pair of plagiarised files
     */
    private static class FilePair implements Comparable<FilePair>{
        private String fileName1;
        private String fileName2;
        private int similarities;

        /**
         * FilePair Constructor
         * @param n1 is the first file
         * @param n2 is the second file
         * @param matches is the number of similarities between file1 and file2
         */
        public FilePair(String n1, String n2, int matches){
            fileName1 = n1;
            fileName2 = n2;
            similarities = matches;
        }

        /**
         * Creates a String representation of a FilePair
         * @return the String
         */
        public String toString(){
            return similarities + ": " + fileName1 + ", " + fileName2;
        }

        /**
         * Compare two FilePairs based on their number of similarities
         * @param other is the other FilePair
         * @return whether the FilePairs are < or > based on their number of similarities
         */
        public int compareTo(FilePair other) {
            if(this.similarities > other.similarities){
                return -1;
            }
            return 1;
        }
    }
}