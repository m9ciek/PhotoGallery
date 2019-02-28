package com.maciek.gallery.service;

import com.maciek.gallery.dao.ImageRepository;
import com.maciek.gallery.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Component
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public void uploadImage(MultipartFile file, Image image) throws Exception {
        Path fileNameAndPath = Paths.get(image.getPath(), file.getOriginalFilename());
        Files.write(fileNameAndPath, file.getBytes());
        Image tempImage = imageRepository.find(image.getFileName());
        if (tempImage == null) {
            imageRepository.save(image);
        } else {
            throw new RuntimeException("Image already exists"); //Need to create own exception and handle it
        }
    }

    @Override
    public List<Image> findImages() {
        return imageRepository.findAll();
    }
}
