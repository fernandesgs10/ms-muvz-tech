package br.com.gateway.buy.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EverthingRouterEnum {

    ROUTE_LIST_EVERTHING("direct:listEverthing");

    private final String name;



}
