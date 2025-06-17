package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.RatingRequest;
import com.example.backendresellkh.dto.response.RatingResponse;
import com.example.backendresellkh.service.interfaces.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @PostMapping
    public ResponseEntity<RatingResponse> createRating(@RequestBody RatingRequest ratingRequest) {
        RatingResponse ratingResponse = ratingService.createRating(ratingRequest);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/byuserid")
    public ResponseEntity<RatingResponse> getRatingByUserId(@RequestParam Long userId) {
        RatingResponse ratingResponse = ratingService.getRatingByUserId(userId);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping("/byuserratedid")
    public ResponseEntity<RatingResponse> getRatingByUserRatedId(@RequestParam Long userId) {
        RatingResponse ratingResponse = ratingService.getRatingByUserRatedId(userId);
        return ResponseEntity.ok(ratingResponse);
    }

    @GetMapping
    public ResponseEntity<List<RatingResponse>> getAllRating() {
        List<RatingResponse> ratingResponses = ratingService.getAllRating();
        return ResponseEntity.ok(ratingResponses);
    }
}
