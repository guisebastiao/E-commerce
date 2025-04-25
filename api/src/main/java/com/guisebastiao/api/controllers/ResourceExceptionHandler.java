package com.guisebastiao.api.controllers;

import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.FieldErrorDTO;
import com.guisebastiao.api.exceptions.BadRequestException;
import com.guisebastiao.api.exceptions.DuplicateEntityException;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.RequiredAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<DefaultResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getFieldErrors();


        List<FieldErrorDTO> fieldErrorDTOs = fieldErrors.stream().map(err -> {
            FieldErrorDTO fieldError = new FieldErrorDTO();
            fieldError.setField(err.getField());
            fieldError.setError(err.getDefaultMessage());
            return fieldError;
        }).toList();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        response.setMessage("Erro de validação");
        response.setFieldErrors(fieldErrorDTOs);
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<DefaultResponseDTO> handleDuplicateEntityException(DuplicateEntityException e) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.CONFLICT.value());
        response.setMessage(e.getMessage());
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<DefaultResponseDTO> handleEntityNotFoundException(EntityNotFoundException e) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage(e.getMessage());
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<DefaultResponseDTO> handleBadRequestException(BadRequestException e) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage(e.getMessage());
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(RequiredAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<DefaultResponseDTO> handleRequiredAuthenticationException(RequiredAuthenticationException e) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setMessage(e.getMessage());
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<DefaultResponseDTO> handleNotFound(NoHandlerFoundException e) {
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.NOT_FOUND.value());
        response.setMessage("Rota não encontrada");
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<DefaultResponseDTO> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Ocorreu um erro inesperado");
        response.setSuccess(Boolean.FALSE);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
