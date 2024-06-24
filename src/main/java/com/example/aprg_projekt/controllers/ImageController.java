package com.example.aprg_projekt.controllers;

import com.example.aprg_projekt.services.ProfileService;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

//TODO: AJ
@Controller
class ImageController {

    private final ProfileService profileService;

    public ImageController(ProfileService profileService) {
        this.profileService = profileService;
    }

    private static final Map<String, MediaType> extensionToMediaTypeMap = Map.of(
            ".jpg", MediaType.IMAGE_JPEG,
            ".jpeg", MediaType.IMAGE_JPEG,
            ".png", MediaType.IMAGE_PNG,
            ".gif", MediaType.IMAGE_GIF
    );

    @GetMapping("/images/{filename:.+\\.(?:jpg|jpeg|png|gif)}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            var file = profileService.loadImage(filename);
            System.out.println(file.toString());
            String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
            MediaType mediaType = extensionToMediaTypeMap.getOrDefault(extension, MediaType.APPLICATION_OCTET_STREAM);
            return ResponseEntity.ok().contentType(mediaType).body(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}