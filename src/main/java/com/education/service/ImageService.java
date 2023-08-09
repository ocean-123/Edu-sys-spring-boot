package com.education.service;

import com.education.entity.Images;
import com.education.repository.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public void saveImage(MultipartFile file) throws IOException {
        Images image = new Images();
        image.setImageName(file.getOriginalFilename());
        image.setImageData(file.getBytes());
        imageRepo.save(image);
    }


//    public List<Image> getAllImages() {
//        return imageRepo.findAll();
//    }
}

