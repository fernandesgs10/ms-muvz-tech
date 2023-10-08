package br.com.gateway.buy.product.exchange;

import br.com.gateway.buy.product.entity.ClientEntity;
import org.apache.camel.Exchange;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ClientExchange {

	Page<ClientEntity> listClient(Exchange exchange);

	Optional<ClientEntity> listClientByNmName(Exchange exchange);



}
