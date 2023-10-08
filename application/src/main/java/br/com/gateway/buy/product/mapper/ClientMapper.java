package br.com.gateway.buy.product.mapper;

import br.com.gateway.buy.product.entity.ClientEntity;
import br.com.muvz.tech.client.api.Client;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Builder
@Component
public class ClientMapper implements Serializable {

    private final ModelMapper modelMapper;

    public Client converterObjectToClient(Object object) {
        return modelMapper.map(object, Client.class);
    }

    public Client converterClientEntityToClient(ClientEntity clientEntity) {
        return modelMapper.map(clientEntity, Client.class);
    }

    public br.com.muvz.tech.client.api.Page converterToPageClient(Object pageClient) {
        return modelMapper.map(pageClient, br.com.muvz.tech.client.api.Page.class);
    }

    public Pageable convertPage(br.com.muvz.tech.client.api.Page page) {
        return modelMapper.map(page, Pageable.class);
    }
}