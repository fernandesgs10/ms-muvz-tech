package br.com.gateway.buy.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientRouterEnum {

    ROUTE_LIST_CLIENT("direct:listClient"),

    ROUTE_LIST_CLIENT_BY_NM_NAME("direct:listClientByNmName");

    private final String name;



}
