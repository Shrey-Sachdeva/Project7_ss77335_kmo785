package assignment7;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.File;
import java.util.*;

public class shreyAttempt {
    private static Map<String, ArrayList<Integer>> phrases = new HashMap<>();

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
                Scanner inFile = new Scanner(fileObject.getFile());
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
                }
            } catch(Exception e) {
                System.out.println("Error in opening file" + fileObject.getFileNum());
            }
        }

        int[][] similarities = new int[files.length][files.length];
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

        for(int i = 0; i < files.length; i++) {
            for(int j = i+1; j < files.length; j++) {
                if(similarities[i][j] >= minNum) {
                    System.out.println(similarities[i][j] + ": " + i + ", " + j);
                }
            }
        }

        /*for(String s : keys) {
            ArrayList<Integer> values = phrases.get(s);
            System.out.print(s + ": ");
            for(Integer i : values) {
                System.out.print(i + ", ");
            }
            System.out.println("");
        }*/
        System.out.println("Done!");
    }

}