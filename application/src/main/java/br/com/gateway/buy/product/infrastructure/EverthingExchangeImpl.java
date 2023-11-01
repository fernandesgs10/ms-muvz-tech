package br.com.gateway.buy.product.infrastructure;

import br.com.gateway.buy.product.config.EndpointConfig;
import br.com.gateway.buy.product.exchange.EverthingExchange;
import br.com.gateway.buy.product.generate.PaginationSort;
import br.com.gateway.buy.product.innoveahub.Article;
import br.com.gateway.buy.product.innoveahub.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Slf4j
@Repository
@RequiredArgsConstructor
public class EverthingExchangeImpl implements EverthingExchange {

    private final RestTemplate restTemplate;

    private final EndpointConfig endpointConfig;

    @Override
    public PageImpl listEverthing(Exchange exchange) {
        Object[] object = (Object[]) exchange.getIn().getBody();
        String q = (String) object[0];
        String from = (String) object[1];
        String to = (String) object[2];
        String domain = (String) object[3];
        Integer pageNo = (Integer) object[4];
        Integer pageSize = (Integer) object[5];
        List<String> sortBy = (List<String>) object[6];

        String formatUrl;
        String formatSort = String.valueOf(PaginationSort.formatSort(sortBy));
        String isFormatStr = formatSort.length() > 0 ? formatSort.substring(0, formatSort.length()-1).replaceAll("\\s", "") : "";

        if(!StringUtils.isEmpty(domain)) {
            formatUrl = getStringDomain(domain, pageNo, pageSize, isFormatStr);

            ResponseEntity<Root> response = restTemplate.getForEntity(formatUrl, Root.class);
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(PaginationSort.getOrders(sortBy)));

            sort(response, pageable);

            return new PageImpl(Objects.requireNonNull(response.getBody()).getArticles(), pageable, response.getBody().getArticles().size());
        }

        if(!StringUtils.isEmpty(q) && !StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)) {
            formatUrl = getStringQAndFromAndTo(q, from, to, pageNo, pageSize, isFormatStr);

            ResponseEntity<Root> response = restTemplate.getForEntity(formatUrl, Root.class);
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(PaginationSort.getOrders(sortBy)));

            sort(response, pageable);

            return new PageImpl(Objects.requireNonNull(response.getBody()).getArticles(), pageable, response.getBody().getArticles().size());
        }

        if(!StringUtils.isEmpty(q) && !StringUtils.isEmpty(from)) {
            formatUrl = getStringQAndFrom(q, from, pageNo, pageSize, isFormatStr);

            ResponseEntity<Root> response = restTemplate.getForEntity(formatUrl, Root.class);
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(PaginationSort.getOrders(sortBy)));

            sort(response, pageable);

            return new PageImpl(Objects.requireNonNull(response.getBody()).getArticles(), pageable, response.getBody().getArticles().size());
        }



        return null;
    }

    private void sort(ResponseEntity<Root> response, Pageable pageable) {
        pageable.getSort().get().forEach(sort -> {
            if (String.valueOf(sort).equals("publishedAt: ASC")) {
                Objects.requireNonNull(response.getBody()).getArticles().sort(Comparator.comparing(Article::getPublishedAt));
            }
            if (String.valueOf(sort).equals("publishedAt: DESC")) {
                Objects.requireNonNull(response.getBody()).getArticles().sort(Comparator.comparing(Article::getPublishedAt).reversed());
            }
        });
    }

    @NotNull
    private String getStringDomain(String domains, Integer pageNo, Integer pageSize, String isFormatStr) {
        String formatUrl;
        if(StringUtils.isEmpty(isFormatStr))
        formatUrl =
                endpointConfig.getUrl() + "/" + endpointConfig.getContextEverthing() + "?domains=" + domains +
                "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();

        else
            formatUrl = endpointConfig.getUrl() + "/" + endpointConfig.getContextEverthing() + "?domains=" + domains + "&" + isFormatStr +
                    "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();
        return formatUrl;
    }

    @NotNull
    private String getStringQAndFrom(String q, String from, Integer pageNo, Integer pageSize, String isFormatStr) {
        String formatUrl;
        if(StringUtils.isEmpty(isFormatStr))
        formatUrl =
                endpointConfig.getUrl() + "/" + endpointConfig.getContextEverthing() + "?q=" + q + "&from=" + from +
                        "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();

        else
        formatUrl = endpointConfig.getUrl() + "/" + endpointConfig.getContextEverthing() + "?q=" + q + "&from=" + from + "&" + isFormatStr +
                "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();

        return formatUrl;
    }

    @NotNull
    private String getStringQAndFromAndTo(String q, String from, String to, Integer pageNo, Integer pageSize, String isFormatStr) {
        String formatUrl;
        if(StringUtils.isEmpty(isFormatStr))
            formatUrl =
                    endpointConfig.getUrl() + "/" + endpointConfig.getContextEverthing() + "?q=" + q
                            + "&from=" + from + "&to="
                            + to +
                            "&pageSize="
                            + pageSize + "&pageNo="
                            + pageNo + "&apiKey=" + endpointConfig.getKey();

        else
            formatUrl = endpointConfig.getUrl() + "/" + endpointConfig.getContextEverthing() + "?q=" + q
                    + "&from=" + from
                    +  "&to="
                    + to +"&" + isFormatStr +
                    "&pageSize=" + pageSize
                    + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();

        return formatUrl;
    }
}