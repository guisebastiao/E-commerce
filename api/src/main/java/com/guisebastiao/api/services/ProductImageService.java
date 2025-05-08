package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.ProductImagesResponseDTO;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.ServerErrorException;
import com.guisebastiao.api.exceptions.UnauthorizedException;
import com.guisebastiao.api.models.Product;
import com.guisebastiao.api.models.ProductImage;
import com.guisebastiao.api.models.ProfilePicture;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.ProductImageRepository;
import com.guisebastiao.api.repositories.ProductRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Service
public class ProductImageService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private MinioClient minioClient;

    @Transactional
    public DefaultResponseDTO createProductImage(String productId, MultipartFile[] files) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("O producto não foi encontrado"));

        if(!product.getSaller().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este recurso");
        }


        Arrays.stream(files).forEach((file) -> {
            String objectId = UUID.randomUUID().toString();

            try {
                InputStream inputStream = file.getInputStream();

                this.minioClient.putObject(PutObjectArgs.builder()
                        .bucket("ecommerce")
                        .object(objectId)
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType("image/jpeg")
                        .build());
            } catch (Exception e) {
                throw new ServerErrorException("Algo deu errado, tente novamente mais tarde");
            }

            ProductImage productImage = new ProductImage();
            productImage.setObjectId(objectId);
            productImage.setProduct(product);
            this.productImageRepository.save(productImage);
        });

        DefaultResponseDTO response = new DefaultResponseDTO();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Imagens para o produto foram adicionadas com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO findProductImages(String productId) {
        Product product = this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("O produto não foi encontrado"));

        List<ProductImage> productImages = this.productImageRepository.findAllByProduct(product);

        ProductImagesResponseDTO[] imagesResponseDTO = productImages.stream()
                .map(productImage -> {
                    ProductImagesResponseDTO dto = new ProductImagesResponseDTO();
                    dto.setProductImageId(productImage.getId());

                    try {
                        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                                .method(Method.GET)
                                .bucket("ecommerce")
                                .object(productImage.getObjectId())
                                .expiry(604800)
                                .build());

                        dto.setUrl(url);
                    } catch (Exception e) {
                        throw new ServerErrorException("Algo deu errado, tente novamente mais tarde");
                    }

                    return dto;
                })
                .toArray(ProductImagesResponseDTO[]::new);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Imagens do produto foram encontradas com sucesso");
        response.setData(imagesResponseDTO);
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO deleteProductImage(String productImageId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProductImage productImage = this.productImageRepository.findById(UUIDConverter.toUUID(productImageId))
                .orElseThrow(() -> new EntityNotFoundException("A imagem do produto não foi encontrada"));

        if(!productImage.getProduct().getSaller().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este recurso");
        }

        this.productImageRepository.delete(productImage);

        DefaultResponseDTO response = new DefaultResponseDTO();

        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Imagem do produto foi deletada com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }
}
