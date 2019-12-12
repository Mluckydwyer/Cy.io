package com.cyio.backend.service;

import com.cyio.backend.model.ThumbnailFile;
import com.cyio.backend.repository.ThumbnailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
public class ThumbnailFileStorageService {
    @Autowired
    private ThumbnailRepository thumbRepository;

    public  ThumbnailFile storeFile(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            ThumbnailFile tbFile;

            if (validateFileName(fileName)){
            tbFile = new ThumbnailFile(fileName, file.getContentType(), file.getBytes());
            return thumbRepository.save(tbFile);}

        }catch (IOException e) {
            throw new IllegalArgumentException("could not store file");
        }
        return null;
    }

    public ThumbnailFile getFile(String thumbnailID){
        return thumbRepository.findById(thumbnailID)
                .orElseThrow(() -> new IllegalArgumentException("file not found"));
    }

    public boolean validateFileName(String fileName){
        //vaildate file name
        if (fileName.contains((".."))){
            throw new IllegalArgumentException("File name invalid");
        }
        else
            return true;

    }
}
