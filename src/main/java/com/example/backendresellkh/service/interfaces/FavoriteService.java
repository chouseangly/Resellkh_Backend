package com.example.backendresellkh.service.interfaces;

import com.example.backendresellkh.dto.request.FavoriteRequest;
import com.example.backendresellkh.dto.response.FavoriteResponse;
import com.example.backendresellkh.model.entity.Favorite;

import java.util.List;

public interface FavoriteService {
    FavoriteResponse createFavorite(FavoriteRequest favoriteRequest);
    List<FavoriteResponse> getAllFavorites();
}
