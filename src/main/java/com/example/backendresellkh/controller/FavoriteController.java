package com.example.backendresellkh.controller;

import com.example.backendresellkh.dto.request.FavoriteRequest;
import com.example.backendresellkh.dto.response.FavoriteResponse;
import com.example.backendresellkh.model.entity.Favorite;
import com.example.backendresellkh.service.interfaces.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<FavoriteResponse> createFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        FavoriteResponse favoriteResponse = favoriteService.createFavorite(favoriteRequest);
        return ResponseEntity.ok(favoriteResponse);
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getFavorites() {
        List<FavoriteResponse> favorites = favoriteService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }
}
