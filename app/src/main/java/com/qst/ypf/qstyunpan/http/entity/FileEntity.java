package com.qst.ypf.qstyunpan.http.entity;

public class FileEntity {

    /**
     * currentPath : E:\apache-tomcat-8.0.32\webapps\yun\WEB-INF\file\1234\/
     * fileName : image
     * filePath :
     * fileSize : -
     * fileType : folder-open
     * lastTime : 2017-11-02 11:57:00
     */

    private String currentPath;
    private String fileName;
    private String filePath;
    private String fileSize;
    private String fileType;
    private String lastTime;

    public String getCurrentPath() {
        return currentPath;
    }

    public void setCurrentPath(String currentPath) {
        this.currentPath = currentPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

}
