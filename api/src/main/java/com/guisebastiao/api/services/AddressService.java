package com.guisebastiao.api.services;

import com.guisebastiao.api.dtos.AddressDTO;
import com.guisebastiao.api.dtos.AddressResponseDTO;
import com.guisebastiao.api.dtos.DefaultResponseDTO;
import com.guisebastiao.api.dtos.PagingDTO;
import com.guisebastiao.api.enums.State;
import com.guisebastiao.api.exceptions.EntityNotFoundException;
import com.guisebastiao.api.exceptions.UnauthorizedException;
import com.guisebastiao.api.models.Address;
import com.guisebastiao.api.models.User;
import com.guisebastiao.api.repositories.AddressRepository;
import com.guisebastiao.api.utils.UUIDConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public DefaultResponseDTO create(AddressDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Address address = new Address();
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setState(State.valueOf(dto.getState()));
        address.setZip(dto.getZip());
        address.setUser(user);

        this.addressRepository.save(address);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Endereço criado com sucesso");
        response.setSuccess(Boolean.TRUE);

        return response;
    }

    public DefaultResponseDTO findAllAddressByUser(int offset, int limit) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Pageable pageable = PageRequest.of(offset, limit);

        Page<Address> resultPage = this.addressRepository.findAllByUser(user, pageable);

        PagingDTO paging = new PagingDTO();
        paging.setCurrentPage(offset);
        paging.setItemsPerPage(limit);
        paging.setTotalPages(resultPage.getTotalPages());
        paging.setTotalItems(resultPage.getTotalElements());

        List<AddressResponseDTO> responseList = resultPage
                .getContent()
                .stream()
                .map(e -> {
                    AddressResponseDTO response = new AddressResponseDTO();
                    response.setId(e.getId());
                    response.setStreet(e.getStreet());
                    response.setCity(e.getCity());
                    response.setState(e.getState());
                    response.setZip(e.getZip());
                    return response;
                })
                .toList();

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Endereços encontrados com sucesso");
        response.setData(responseList);
        response.setPaging(paging);
        response.setSuccess(Boolean.TRUE);
        return response;
    }

    public DefaultResponseDTO update(String id, AddressDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Address address = this.addressRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Esse endereço não existe"));

        if(!user.getId().toString().equals(address.getUser().getId().toString())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este endereço");
        }

        Address newAddress = new Address();
        newAddress.setId(UUIDConverter.toUUID(id));
        newAddress.setStreet(dto.getStreet());
        newAddress.setCity(dto.getCity());
        newAddress.setState(State.valueOf(dto.getState()));
        newAddress.setZip(dto.getZip());
        newAddress.setUser(user);

        this.addressRepository.save(newAddress);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Endereço editado com sucesso");
        response.setSuccess(Boolean.TRUE);
        return response;
    }

    public DefaultResponseDTO delete(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Address address = this.addressRepository.findById(UUIDConverter.toUUID(id))
                .orElseThrow(() -> new EntityNotFoundException("Esse endereço não existe"));

        if(!user.getId().toString().equals(address.getUser().getId().toString())) {
            throw new UnauthorizedException("Você não tem permissão para acessar este endereço");
        }

        this.addressRepository.delete(address);

        DefaultResponseDTO response = new DefaultResponseDTO();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Endereço excluido com sucesso");
        response.setSuccess(Boolean.TRUE);
        return response;
    }
}
