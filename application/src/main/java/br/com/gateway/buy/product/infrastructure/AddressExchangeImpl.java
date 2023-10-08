package br.com.gateway.buy.product.infrastructure;

import br.com.gateway.buy.product.entity.AddressEntity;
import br.com.gateway.buy.product.exchange.AddressExchange;
import br.com.gateway.buy.product.repository.AddressRepository;
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
public class AddressExchangeImpl implements AddressExchange {

    private final AddressRepository addressRepository;

    public Optional<AddressEntity> listAddressByNmAddressZipCode(Exchange exchange) {
        String nmAddressZipCode = (String) exchange.getIn().getBody();
        return addressRepository.findAddressBYNmAddressZipCode(nmAddressZipCode);
    }

    public Page<AddressEntity> listAddress(Exchange exchange) {
        Pageable pageRequest = (Pageable) exchange.getIn().getBody();
        return addressRepository.findAddressAll(pageRequest);
    }

    @Override
    public Optional<AddressEntity> createAddress(Exchange exchange) {
        AddressEntity addressEntity = (AddressEntity) exchange.getIn().getBody();
        return Optional.of(addressRepository.save(addressEntity));
    }
}