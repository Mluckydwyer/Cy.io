package com.cyio.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.UUID;

@Entity

@Table(name = "thumbnails", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id"})
})



public class ThumbnailFile {

    @Id
    @Column(name = "id")
    private int id;
    
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "thumbnailID")
    private String thumbnailID;

    @Column(name = "thumbnailName")
    private String thumbnailName;

    @Column(name = "fileType")
    private String fileType;

    @Column (name = "data")
    @Lob
    private byte[] data;

    public ThumbnailFile(String thumbnailName, String fileType, byte[] data){
        this.fileType = fileType;
        this.thumbnailName = thumbnailName;
        this.data = data;

    }

    public ThumbnailFile(){
        UUID newID = UUID.randomUUID(); //generate a random UUID for the new User
        this.thumbnailID = newID.toString();
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
