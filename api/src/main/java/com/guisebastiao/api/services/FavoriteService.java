package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.AssessmentResponseDTO;
import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.FavoriteResponseDTO;
import com.guisebastiao.api.dtos.PagingDTO;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.UnauthorizedException;
import com.guisebastiao.api.models.Favorite;
import com.guisebastiao.api.models.Product;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.FavoriteRepository;
import com.guisebastiao.api.repositories.ProductRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MinioClient minioClient;

    public DefaultResponseDTO create(String productId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("O produto não foi encontrado"));

        Favorite favorite = new Favorite();

        favorite.setUser(user);
        favorite.setProduct(product);

        this.favoriteRepository.save(favorite);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Produto adicionado aos favoritos");
        response.setSuccess(Boolean.TRUE);
        return response;
    }

    public DefaultResponseDTO findAll(int offset, int limit) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Favorite> resultPage = this.favoriteRepository.findAllByUser(user, pageable);

        PagingDTO paging = new PagingDTO();
        paging.setCurrentPage(offset);
        paging.setItemsPerPage(limit);
        paging.setTotalPages(resultPage.getTotalPages());
        paging.setTotalItems(resultPage.getTotalElements());

        List<FavoriteResponseDTO> responseList = resultPage
                .getContent()
                .stream()
                .map(e -> new FavoriteResponseDTO().toDto(e, minioClient))
                .toList();

        DefaultResponseDTO response = new DefaultResponseDTO();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Avaliações encontradas com sucesso");
        response.setData(responseList);
        response.setSuccess(Boolean.TRUE);
        response.setPaging(paging);
        return response;
    }

    public DefaultResponseDTO delete(String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Favorite favorite = this.favoriteRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Esse produto como favorito não existe"));

        if(!favorite.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este recurso");
        }

        this.favoriteRepository.delete(favorite);

        DefaultResponseDTO response = new DefaultResponseDTO();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Produto favorito removido com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }
}
