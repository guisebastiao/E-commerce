package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.*;
import com.guisebastiao.api.exceptions.BadRequestException;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.models.Cart;
import com.guisebastiao.api.models.CartItem;
import com.guisebastiao.api.models.Product;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.CartItemRepository;
import com.guisebastiao.api.repositories.CartRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MinioClient minioClient;

    @Transactional
    public DefaultResponseDTO create(AddCartItemDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(UUIDConverter.toUUID(dto.getProductId()))
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if(product.getStock() < dto.getQuantity()) {
            throw new BadRequestException("A quantidade selecionada está fora de estoque");
        }

        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    cart.getItems().add(newItem);
                    return newItem;
                });

        cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());

        cartItemRepository.save(cartItem);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Item adicionado no carrinho com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    @Transactional
    public DefaultResponseDTO getAllCartItems(int offset, int limit) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Pageable pageable = PageRequest.of(offset / limit, limit);

        Page<CartItem> cartItemsPage = cartItemRepository.findByCart(cart, pageable);

        List<CartResponseDTO> products = cartItemsPage.getContent().stream().map(item -> {

            UserResponseDTO userDTO = new UserResponseDTO();

            ProductResponseDTO product = new ProductResponseDTO();
            product.setId(item.getProduct().getId());
            product.setProductName(item.getProduct().getProductName());
            product.setDescription(item.getProduct().getDescription());
            product.setPrice(item.getProduct().getPrice());
            product.setStock(item.getProduct().getStock());
            product.setCategory(item.getProduct().getCategory());
            product.setDiscount(item.getProduct().getDiscount());
            product.setIsActive(item.getProduct().getIsActive());
            product.setSaller(userDTO.toDto(item.getProduct().getSaller(), minioClient));

            CartResponseDTO response = new CartResponseDTO();
            response.setCartItemid(item.getId());
            response.setProduct(product);
            response.setQuantity(item.getQuantity());

            return response;
        }).toList();



        PagingDTO pagingDTO = new PagingDTO();
        pagingDTO.setCurrentPage(offset);
        pagingDTO.setItemsPerPage(Math.toIntExact(cartItemsPage.getTotalElements()));
        pagingDTO.setTotalPages(cartItemsPage.getTotalPages());

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage(pagingDTO.getTotalPages() + " produtos encontrados");
        response.setData(products);
        response.setPaging(pagingDTO);
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    @Transactional
    public DefaultResponseDTO deleteCartItem(String id) {
        CartItem cartItem = this.cartItemRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Esse produto não foi encontrado"));

        cartItemRepository.delete(cartItem);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Produto removido do carrinho com sucesso");
        response.setSuccess(Boolean.TRUE);
        return response;
    }
}
