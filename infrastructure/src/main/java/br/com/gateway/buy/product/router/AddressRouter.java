package br.com.gateway.buy.product.router;

import br.com.gateway.buy.product.enums.AddressRouterEnum;
import br.com.gateway.buy.product.exchange.AddressExchange;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddressRouter {

    private final AddressExchange addressExchange;

    public RouteBuilder listAddressRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(AddressRouterEnum.ROUTE_LIST_ADDRESS.getName())
                        .bean(addressExchange, "listAddress");
            }
        };
    }

    public RouteBuilder listAddressByNmAddressZipCodeRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(AddressRouterEnum.ROUTE_LIST_ADDRESS_ZIPCODE_ROUTER.getName())
                        .bean(addressExchange, "listAddressByNmAddressZipCode");
            }
        };
    }

    public RouteBuilder createAddressRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(AddressRouterEnum.ROUTE_CREATE_ADDRESS.getName())
                        .bean(addressExchange, "createAddress");
            }
        };
    }
}