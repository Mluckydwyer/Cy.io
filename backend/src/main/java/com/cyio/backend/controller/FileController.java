package com.cyio.backend.controller;

import com.cyio.backend.model.ThumbnailFile;
import com.cyio.backend.payload.UploadFileResponse;
import com.cyio.backend.service.ThumbnailFileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class FileController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ThumbnailFileStorageService fileStorageService;

    @PostMapping("/uploadthumbnail")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file){
        ThumbnailFile thumbnail = fileStorageService.storeFile(file);

        String downloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/")
                .pathSegment(thumbnail.getThumbnailID())
                .toUriString();

        return new UploadFileResponse(thumbnail.getThumbnailName(), downloadUri, file.getContentType(), file.getSize());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId){
        //load file from db
        ThumbnailFile thumbnailFile = fileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(thumbnailFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + thumbnailFile.getThumbnailName() + "\"")
                .body(new ByteArrayResource(thumbnailFile.getData()));

    }
}
