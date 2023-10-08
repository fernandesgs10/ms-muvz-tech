package br.com.gateway.buy.product.mapper;

import br.com.gateway.buy.product.entity.AddressEntity;
import br.com.muvz.tech.client.api.Address;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Builder
@Component
public class AddressMapper implements Serializable {

    private final ModelMapper modelMapper;

    public Address converterObjectToAddress(Object object) {
        return modelMapper.map(object, Address.class);
    }

    public Address converterAddressEntityToAddress(AddressEntity addressEntity) {
        return modelMapper.map(addressEntity, Address.class);
    }

    public AddressEntity converterAddressToAddressEntity(br.com.muvz.tech.address.api.Address address) {
        return modelMapper.map(address, AddressEntity.class);
    }

    public br.com.muvz.tech.address.api.Page converterToPageAddress(Object pageAddress) {
        return modelMapper.map(pageAddress, br.com.muvz.tech.address.api.Page.class);
    }

    public Pageable convertPage(br.com.muvz.tech.address.api.Page page) {
        return modelMapper.map(page, Pageable.class);
    }
}