package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.*;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.UnauthorizedException;
import com.guisebastiao.api.models.Address;
import com.guisebastiao.api.models.Assessment;
import com.guisebastiao.api.models.Product;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.AssessmentRepository;
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
public class AssessmentService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private MinioClient minioClient;

    public DefaultResponseDTO create(String productId, AssessmentDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Product product = productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("O produto da avaliação não foi encontrado"));

        Assessment assessment = new Assessment();

        assessment.setUser(user);
        assessment.setProduct(product);
        assessment.setComment(dto.getComment());
        assessment.setNote(dto.getNote());

        this.assessmentRepository.save(assessment);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Produto avaliado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO findAll(String productId, int offset, int limit) {
        Product product = this.productRepository.findById(UUIDConverter.toUUID(productId))
                .orElseThrow(() -> new EntityNotFoundException("O produto não foi encontrado"));

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Assessment> resultPage = this.assessmentRepository.findAllByProduct(product, pageable);

        PagingDTO paging = new PagingDTO();
        paging.setCurrentPage(offset);
        paging.setItemsPerPage(limit);
        paging.setTotalPages(resultPage.getTotalPages());
        paging.setTotalItems(resultPage.getTotalElements());

        List<AssessmentResponseDTO> responseList = resultPage
                .getContent()
                .stream()
                .map(e -> new AssessmentResponseDTO().toDto(e, minioClient))
                .toList();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Avaliações encontradas com sucesso");
        response.setData(responseList);
        response.setSuccess(Boolean.TRUE);
        response.setPaging(paging);
        return response;
    }

    public DefaultResponseDTO update(String id, AssessmentDTO dto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Assessment assessment = this.assessmentRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("A avaliação não foi encontrada"));

        if(!assessment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar esta avaliação");
        }

        Assessment assessmentUpdate = new Assessment();

        assessmentUpdate.setId(UUIDConverter.toUUID(id));
        assessmentUpdate.setUser(user);
        assessmentUpdate.setProduct(assessment.getProduct());
        assessmentUpdate.setComment(dto.getComment());
        assessmentUpdate.setNote(dto.getNote());

        this.assessmentRepository.save(assessmentUpdate);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Avaliação atualizada com sucesso");
        response.setSuccess(Boolean.TRUE);
        return response;
    }


    public DefaultResponseDTO delete(String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Assessment assessment = this.assessmentRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("A avaliação não foi encontrada"));

        if(!assessment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Você não tem permissão para acessar esta avaliação");
        }

        this.assessmentRepository.delete(assessment);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("A avalição foi deletada com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }
}
