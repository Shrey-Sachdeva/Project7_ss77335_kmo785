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

import java.io.File;

public class FileObject {
    private File file;
    private int fileNum;

    /**
     * FileObject Constructor
     * @param file is the file
     * @param fileNum is the associated file number
     */
    public FileObject(File file, int fileNum) {
        this.file = file;
        this.fileNum = fileNum;
    }

    /**
     * Returns the file
     * @return the file
     */

    public File getFile() {
        return file;
    }

    /**
     * Return the file number
     * @return the file number
     */
    public int getFileNum() {
        return fileNum;
    }

    /**
     * Return the file's name as a String
     * @return the file's name
     */
    public String getFileName(){
        return file.getName();
    }
}
