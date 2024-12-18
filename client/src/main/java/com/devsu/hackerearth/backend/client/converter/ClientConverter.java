package com.devsu.hackerearth.backend.client.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;


@Service
public class ClientConverter implements Converter<Client, ClientDto>{

    @Override
    public ClientDto convert(Client client) {
        return ClientDto.builder()
        .address(client.getAddress())
        .age(client.getAge())
        .dni(client.getDni())
        .gender(client.getGender())
        .id(client.getId())
        .isActive(client.isActive())
        .name(client.getName())
        .build();
    }
    
}
