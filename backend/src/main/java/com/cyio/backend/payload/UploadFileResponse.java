package com.cyio.backend.payload;

public class UploadFileResponse {
    String thumbnailName;
    String downloadUri;
    String fileType;
    long fileSize;

    public UploadFileResponse(String thumbnailName, String downloadUri, String fileType, long fileSize) {
        this.thumbnailName = thumbnailName;
        this.downloadUri = downloadUri;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getDownloadUri() {
        return downloadUri;
    }

    public void setDownloadUri(String downloadUri) {
        this.downloadUri = downloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
