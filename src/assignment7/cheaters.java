package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class cheaters {
    private static Map<String, ArrayList<Integer>> phrases = new HashMap<>();

    private static String purify(String string) {
        String purifiedString = "";
        for(int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if(Character.isLetterOrDigit(c)) {
                purifiedString += c;
            }
        }
        return purifiedString.toUpperCase();
    }

    public static void main(String args[]) {
        String filePath = args[0];
        int N = Integer.parseInt(args[1]);
        int minNum = Integer.parseInt(args[2]);

        File f = new File(filePath);
        File[] files = f.listFiles();

        ArrayList<FileObject> fileObjects = new ArrayList<>();
        for(int i = 1; i <= files.length; i++) {
            FileObject fileObject = new FileObject(files[i-1], i);
            fileObjects.add(fileObject);
        }

        for(FileObject fileObject : fileObjects) {
            try {
                FileReader fileReader = new FileReader(fileObject.getFile());
                BufferedReader inFile = new BufferedReader(fileReader);
                //System.out.println("startin file");
                String string = inFile.readLine();
                ArrayList<String> strings = new ArrayList<>();
                while(string != null) {
                    String[] fileStrings = string.split("\\s+");
                    for(String s : fileStrings) {
                        strings.add(s);
                    }
                    string = inFile.readLine();
                    //System.out.println("read");
                }

                for(int i = 0; i < strings.size() - N; i++) {
                    String key = "";
                    for(int j = 0; j < N; j++) {
                        key += purify(strings.get(i+j));
                    }
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
                //System.out.println(fileObject.getFileNum());
                /*Scanner inFile = new Scanner(fileObject.getFile());
                int numWords = 0;
                while(inFile.hasNext()) {
                    inFile.next();
                    numWords++;
                }
                inFile = new Scanner(fileObject.getFile());
                for(int i = 0; i <= (numWords-N); i++) {
                    Scanner inFileCopy = new Scanner(fileObject.getFile());
                    for(int j = 0; j < i; j++) {
                        inFileCopy.next();
                    }
                    String key = "";
                    for(int j = 0; j < N; j++) {
                        String s = inFileCopy.next();
                        String s2 = "";
                        for(int k = 0; k < s.length(); k++) {
                            if(Character.isLetterOrDigit(s.charAt(k))) {
                                s2 += s.charAt(k);
                            }
                        }
                        key += s2.toUpperCase();
                        //key += inFileCopy.next();
                    }
                    //key.replaceAll("[^a-zA-Z ]", "");
                    //key.toUpperCase();
                    ArrayList<Integer> filesWithKey;
                    if(phrases.containsKey(key)) {
                        filesWithKey = phrases.get(key);
                    }
                    else {
                        filesWithKey = new ArrayList<>();
                    }
                    filesWithKey.add(fileObject.getFileNum());
                    phrases.put(key, filesWithKey);
                    inFile.next();
                }*/
            } catch(Exception e) {
                System.out.println("Error in opening file" + fileObject.getFileNum());
            }
        }

        int[][] similarities = new int[files.length+1][files.length+1];
        Set<String> keys = phrases.keySet();

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

        PriorityQueue<FilePair> filePairs = new PriorityQueue<>();
        for(int i = 1; i < files.length+1; i++) {
            for(int j = i+1; j < files.length+1; j++) {
                if(similarities[i][j] >= minNum) {
                    //System.out.println(similarities[i][j] + ": " + fileObjects.get(i-1).getFileName() + ", " + fileObjects.get(j-1).getFileName());
                    String n1 = fileObjects.get(i-1).getFileName();
                    String n2 = fileObjects.get(j-1).getFileName();
                    filePairs.add(new FilePair(n1,n2,similarities[i][j] ) );
                }
            }
        }
        while(!filePairs.isEmpty()){
            System.out.println(filePairs.poll());
        }


        /*for(String s : keys) {
            ArrayList<Integer> values = phrases.get(s);
            System.out.print(s + ": ");
            for(Integer i : values) {
                System.out.print(i + ", ");
            }
            System.out.println("");
        }*/
        //System.out.println("Done!");
    }

    private static class FilePair implements Comparable<FilePair>{
        private String fileName1;
        private String fileName2;
        private int similarities;

        public FilePair(String n1, String n2, int matches){
            fileName1 = n1;
            fileName2 = n2;
            similarities = matches;
        }

        public String toString(){
            return similarities + ": " + fileName1 + ", " + fileName2;
        }

        public int compareTo(FilePair other) {
            if(this.similarities > other.similarities){
                return -1;
            }
            return 1;
        }
    }
}