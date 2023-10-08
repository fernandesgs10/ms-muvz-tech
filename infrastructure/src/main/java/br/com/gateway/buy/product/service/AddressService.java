package br.com.gateway.buy.product.service;

import br.com.gateway.buy.product.common.MuvzException;
import br.com.gateway.buy.product.router.AddressRouter;
import br.com.gateway.buy.product.entity.AddressEntity;
import br.com.gateway.buy.product.enums.AddressRouterEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class AddressService {

    private final AddressRouter addressRouter;

    public Page<AddressEntity> listAddress(Pageable pageable) {
        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(addressRouter.listAddressRouter());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(AddressRouterEnum.ROUTE_LIST_ADDRESS.getName(), pageable, Page.class);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if (ex.getCause() != null) {
                Throwable cause = ex.getCause();
                if (cause instanceof HttpClientErrorException.BadRequest) {
                    throw (HttpClientErrorException.BadRequest) cause;
                }
            }
            throw new MuvzException(ex.getMessage());
        }
    }

    public Optional<AddressEntity> listAddressByNmAddressZipCode(String nmAddressZipCode) {
        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(addressRouter.listAddressByNmAddressZipCodeRouter());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(AddressRouterEnum.ROUTE_LIST_ADDRESS_ZIPCODE_ROUTER.getName(), nmAddressZipCode, Optional.class);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if (ex.getCause() != null) {
                Throwable cause = ex.getCause();
                if (cause instanceof HttpClientErrorException.BadRequest) {
                    throw (HttpClientErrorException.BadRequest) cause;
                }
            }
            throw new MuvzException(ex.getMessage());
        }
    }

    public Optional<AddressEntity> createAddress(AddressEntity address) {
        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(addressRouter.createAddressRouter());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(AddressRouterEnum.ROUTE_CREATE_ADDRESS.getName(), address, Optional.class);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            if (ex.getCause() != null) {
                Throwable cause = ex.getCause();
                if (cause instanceof HttpClientErrorException.BadRequest) {
                    throw (HttpClientErrorException.BadRequest) cause;
                }
            }
            throw new MuvzException(ex.getMessage());
        }
    }
}
