package br.com.gateway.buy.product.router;

import br.com.gateway.buy.product.enums.EverthingRouterEnum;
import br.com.gateway.buy.product.exchange.EverthingExchange;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EverthingRouter {

    private final EverthingExchange everthingExchange;

    public RouteBuilder listEverthingRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(EverthingRouterEnum.ROUTE_LIST_EVERTHING.getName())
                        .bean(everthingExchange, "listEverthing");
            }
        };
    }


}