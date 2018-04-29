package assignment7;

import java.io.File;

public class FileObject {
    private File file;
    private int fileNum;

    public FileObject(File file, int fileNum) {
        this.file = file;
        this.fileNum = fileNum;
    }

    public File getFile() {
        return file;
    }

    public int getFileNum() {
        return fileNum;
    }

    public String getFileName(){
        return file.getName();
    }
}
