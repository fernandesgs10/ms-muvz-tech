package br.com.gateway.buy.product.service;

import br.com.gateway.buy.product.common.MuvzException;
import br.com.gateway.buy.product.enums.TopHeadLineRouterEnum;
import br.com.gateway.buy.product.innoveahub.Root;
import br.com.gateway.buy.product.router.TopHeadLineRouter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class TopHeadLineService {

    private final TopHeadLineRouter topHeadLineRouter;

    public Page<Root> listTopHeadLine(String sources,
                                      String country,
                                      String category,
                                 Integer pageNo,
                                 Integer pageSize,
                                 List<String> sortBy) {

        Object[] object = {sources, country, category, pageNo, pageSize, sortBy};

        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(topHeadLineRouter.listTopHeadLine());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(TopHeadLineRouterEnum.ROUTE_LIST_TOP_HEAD_LINE.getName(), object, Page.class);
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
