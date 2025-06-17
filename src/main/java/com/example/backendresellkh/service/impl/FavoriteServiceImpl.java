package com.example.backendresellkh.service.impl;

import com.example.backendresellkh.dto.request.FavoriteRequest;
import com.example.backendresellkh.dto.response.FavoriteResponse;
import com.example.backendresellkh.model.entity.Favorite;
import com.example.backendresellkh.repository.FavoriteRepository;
import com.example.backendresellkh.service.interfaces.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteServiceImpl implements FavoriteService {
    private final FavoriteRepository favoriteRepository;
    private final AuthServiceImpl authServiceImpl;

    @Override
    public FavoriteResponse createFavorite(FavoriteRequest favoriteRequest) {
        Favorite favorite = Favorite.builder()
                .userId(favoriteRequest.getUserId())
                .productId(favoriteRequest.getProductId())
                .createdAt(LocalDateTime.now())
                .build();
        favoriteRepository.createFavorite(favorite);
        return mapToFavoriteResponse(favorite);
    }

    @Override
    public List<FavoriteResponse> getAllFavorites() {
        return favoriteRepository.getAllFavorites()
                .stream()
                .map(this::mapToFavoriteResponse)
                .toList();
    }

    private FavoriteResponse mapToFavoriteResponse(Favorite favorite) {
        return FavoriteResponse.builder()
                .favoriteId(favorite.getFavoriteId())
                .userId(favorite.getUserId())
                .photoId(favorite.getProductId())
                .build();
    }
}
