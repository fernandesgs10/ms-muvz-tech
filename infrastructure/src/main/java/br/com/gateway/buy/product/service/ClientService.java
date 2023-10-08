package br.com.gateway.buy.product.service;

import br.com.gateway.buy.product.common.MuvzException;
import br.com.gateway.buy.product.entity.ClientEntity;
import br.com.gateway.buy.product.router.ClientRouter;
import br.com.gateway.buy.product.enums.ClientRouterEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class ClientService {

    private final ClientRouter clientRouter;

    public Page<ClientEntity> listClient(Pageable pageable) {
        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(clientRouter.listClientRouter());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(ClientRouterEnum.ROUTE_LIST_CLIENT.getName(), pageable, Page.class);
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

    public Optional<ClientEntity> listClientByName(String nmName) {
        try (CamelContext ctx = new DefaultCamelContext()) {
            ctx.addRoutes(clientRouter.listClientByNmNameRouter());
            ctx.start();
            try (ProducerTemplate producerTemplate = ctx.createProducerTemplate()) {
                return producerTemplate.
                        requestBody(ClientRouterEnum.ROUTE_LIST_CLIENT_BY_NM_NAME.getName(), nmName, Optional.class);
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
