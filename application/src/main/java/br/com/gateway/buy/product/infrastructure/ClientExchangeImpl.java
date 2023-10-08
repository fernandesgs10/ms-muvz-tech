package br.com.gateway.buy.product.infrastructure;

import br.com.gateway.buy.product.entity.ClientEntity;
import br.com.gateway.buy.product.exchange.ClientExchange;
import br.com.gateway.buy.product.repository.ClientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class ClientExchangeImpl implements ClientExchange {

    private final ClientRepository clientRepository;

    public Optional<ClientEntity> listClientByNmName(Exchange exchange) {
        String nmName = (String) exchange.getIn().getBody();
        return clientRepository.findClientBYNmName(nmName);
    }

    public Page<ClientEntity> listClient(Exchange exchange) {
        Pageable pageRequest = (Pageable) exchange.getIn().getBody();
        return clientRepository.findClientAll(pageRequest);
    }
}