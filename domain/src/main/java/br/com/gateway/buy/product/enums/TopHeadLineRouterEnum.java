package br.com.gateway.buy.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TopHeadLineRouterEnum {

    ROUTE_LIST_TOP_HEAD_LINE("direct:listTopHeadLine");

    private final String name;



}
