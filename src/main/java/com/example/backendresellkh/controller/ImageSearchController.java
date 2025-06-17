package com.example.backendresellkh.controller;

import com.example.backendresellkh.external.client.ImageVectorClient;
import com.example.backendresellkh.service.interfaces.ImageVectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/searching")
@RequiredArgsConstructor
public class ImageSearchController {
    private final ImageVectorService imageVectorService;
    private final ImageVectorClient imageVectorClient;

    @PostMapping(
            value = "/api/search/by-upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public List<String> searchByUploadedImage(@RequestPart("image") MultipartFile image) {
        List<Double> queryVector = imageVectorClient.getImageVectorFromFile(image);
        return imageVectorService.findSimilarImages(queryVector, 10);
    }



}
