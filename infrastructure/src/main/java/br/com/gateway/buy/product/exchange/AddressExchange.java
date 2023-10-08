package br.com.gateway.buy.product.exchange;

import br.com.gateway.buy.product.entity.AddressEntity;
import org.apache.camel.Exchange;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface AddressExchange {

	Page<AddressEntity> listAddress(Exchange exchange);

	Optional<AddressEntity> createAddress(Exchange exchange);

	Optional<AddressEntity> listAddressByNmAddressZipCode(Exchange exchange);

}
