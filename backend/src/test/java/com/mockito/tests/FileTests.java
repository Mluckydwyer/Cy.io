package com.mockito.tests;

import com.cyio.backend.exception.BadRequestException;
import com.cyio.backend.model.ThumbnailFile;
import com.cyio.backend.repository.GameRepository;
import com.cyio.backend.repository.ThumbnailRepository;
import com.cyio.backend.service.ThumbnailFileStorageService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

public class FileTests {
   @InjectMocks
    ThumbnailFileStorageService service;

   @Mock
   ThumbnailRepository fileRepo;

   @Before
    public void init() {
       MockitoAnnotations.initMocks(this);
   }

   @Test(expected = IllegalArgumentException.class)
    public void fileVlidationTest(){
        String fileName = "/res/thumb1..jpg";

        service.validateFileName(fileName);
   }

   @Test
    public void fileVlidationTest2(){
       String fileName = "res/thumb1.jpg";
       assertTrue(service.validateFileName(fileName));
   }

   @Test
    public void retrieveFileTest(){
       ThumbnailFile file = new ThumbnailFile();
       file.setThumbnailName("Thumbnail-1");
       UUID id = UUID.randomUUID();
       file.setThumbnailID(id.toString());
       Optional<ThumbnailFile> fList = Optional.of(file);
       when(fileRepo.findById(id.toString())).thenReturn(fList);
       //doReturn(fList).when(fileRepo.findById(id.toString()));
       assertTrue( service.getFile(id.toString()) != null);
   }

}
