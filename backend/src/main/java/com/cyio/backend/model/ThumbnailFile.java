package com.cyio.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

public class ThumbnailFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String thumbnailID;

    private String thumbnailName;

    private String fileType;

    @Lob
    private byte[] data;

    public ThumbnailFile(String thumbnailName, String fileType, byte[] data){
        this.fileType = fileType;
        this.thumbnailName = thumbnailName;
        this.data = data;

    }


    public String getThumbnailID() {
        return thumbnailID;
    }

    public void setThumbnailID(String thumbnailID) {
        this.thumbnailID = thumbnailID;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}