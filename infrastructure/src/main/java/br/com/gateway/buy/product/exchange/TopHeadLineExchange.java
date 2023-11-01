package br.com.gateway.buy.product.exchange;

import br.com.gateway.buy.product.innoveahub.Root;
import org.apache.camel.Exchange;
import org.springframework.data.domain.Page;

public interface TopHeadLineExchange {

	Page<Root> listTopHeadLine(Exchange exchange);






}
