package br.com.gateway.buy.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AddressRouterEnum {

    ROUTE_LIST_ADDRESS("direct:listAddress"),

    ROUTE_CREATE_ADDRESS("direct:createAddress"),

    ROUTE_LIST_ADDRESS_ZIPCODE_ROUTER("direct:listAddressByNmAddressZipCode");

    private final String name;

}
