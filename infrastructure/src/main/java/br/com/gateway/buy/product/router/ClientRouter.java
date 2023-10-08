package br.com.gateway.buy.product.router;

import br.com.gateway.buy.product.enums.ClientRouterEnum;
import br.com.gateway.buy.product.exchange.ClientExchange;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientRouter {

    private final ClientExchange clientExchange;

    public RouteBuilder listClientRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(ClientRouterEnum.ROUTE_LIST_CLIENT.getName())
                        .bean(clientExchange, "listClient");
            }
        };
    }

    public RouteBuilder listClientByNmNameRouter() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(ClientRouterEnum.ROUTE_LIST_CLIENT_BY_NM_NAME.getName())
                        .bean(clientExchange, "listClientByNmName");
            }
        };
    }
}