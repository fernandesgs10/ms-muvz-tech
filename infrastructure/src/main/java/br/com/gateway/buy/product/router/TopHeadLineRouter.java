package br.com.gateway.buy.product.router;

import br.com.gateway.buy.product.enums.TopHeadLineRouterEnum;
import br.com.gateway.buy.product.exchange.TopHeadLineExchange;
import lombok.AllArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TopHeadLineRouter {

    private final TopHeadLineExchange topHeadLineExchange;

    public RouteBuilder listTopHeadLine() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                from(TopHeadLineRouterEnum.ROUTE_LIST_TOP_HEAD_LINE.getName())
                        .bean(topHeadLineExchange, "listTopHeadLine");
            }
        };
    }




}