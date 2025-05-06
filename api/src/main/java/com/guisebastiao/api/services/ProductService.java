package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.ProductDTO;
import com.guisebastiao.api.dtos.ProductResponseDTO;
import com.guisebastiao.api.dtos.UserDTO;
import com.guisebastiao.api.enums.Category;
import com.guisebastiao.api.enums.ProductActive;
import com.guisebastiao.api.enums.Role;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.ForbiddenException;
import com.guisebastiao.api.exceptions.UnauthorizedException;
import com.guisebastiao.api.models.Product;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.ProductRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public DefaultResponseDTO create(ProductDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(!user.getRole().equals(Role.SELLER)) {
            throw new ForbiddenException("Sua conta não está autorizada para vender produtos");
        }

        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(Category.fromString(dto.getCategory()));
        product.setDiscount(dto.getDiscount());
        product.setIsActive(ProductActive.fromString(dto.getIsActive()));
        product.setSaller(user);

        this.productRepository.save(product);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Produto criado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO findById(String id) {
        Product product = productRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("O produto não está disponivel"));

        ProductResponseDTO productResponseDTO = new ProductResponseDTO();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Produto encontrado com sucesso");
        response.setData(productResponseDTO.toDto(product));
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO update(String id, ProductDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = this.productRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Esse produto não existe"));

        if(!user.getId().equals(product.getSaller().getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este produto");
        }

        if(!Role.SELLER.equals(user.getRole())) {
            throw new ForbiddenException("Você não tem permissão para editar esse produto");
        }

        Product newProduct = new Product();
        newProduct.setId(UUIDConverter.toUUID(id));
        newProduct.setProductName(dto.getProductName());
        newProduct.setDescription(dto.getDescription());
        newProduct.setPrice(dto.getPrice());
        newProduct.setStock(dto.getStock());
        newProduct.setCategory(Category.fromString(dto.getCategory()));
        newProduct.setDiscount(dto.getDiscount());
        newProduct.setIsActive(ProductActive.fromString(dto.getIsActive()));
        newProduct.setSaller(user);

        this.productRepository.save(newProduct);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Produto atualizado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO delete(String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = this.productRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Esse produto não existe"));

        if(!user.getId().equals(product.getSaller().getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este produto");
        }

        if(!Role.SELLER.equals(user.getRole())) {
            throw new ForbiddenException("Você não tem permissão para deletar esse produto");
        }

        this.productRepository.delete(product);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Produto deletado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }
}
