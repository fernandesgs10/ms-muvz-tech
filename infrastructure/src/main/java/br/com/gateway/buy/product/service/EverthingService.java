package br.com.gateway.buy.product.service;

import br.com.gateway.buy.product.common.MuvzException;
import br.com.gateway.buy.product.enums.EverthingRouterEnum;
import br.com.gateway.buy.product.innoveahub.Root;
import br.com.gateway.buy.product.router.EverthingRouter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@SuppressWarnings("unchecked")
@Slf4j
@Component
@AllArgsConstructor
public class EverthingService {

    private final EverthingRouter everthingRouter;

    public Page<Root> listEverthing(String q,
                                 String from,
                                 String to,
                                 String domain,
                                 Integer pageNo,
                                 Integer pageSize,
                                 List<String> sortBy) {

        Object[] object = {q, from, to, domain, pageNo, pageSize, sortBy};

        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(everthingRouter.listEverthingRouter());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(EverthingRouterEnum.ROUTE_LIST_EVERTHING.getName(), object, Page.class);
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
