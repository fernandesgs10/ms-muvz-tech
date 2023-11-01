package br.com.gateway.buy.product.infrastructure;

import br.com.gateway.buy.product.config.EndpointConfig;
import br.com.gateway.buy.product.exchange.TopHeadLineExchange;
import br.com.gateway.buy.product.generate.PaginationSort;
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

import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Slf4j
@Repository
@RequiredArgsConstructor
public class TopHeadLineExchangeImpl implements TopHeadLineExchange {

    private final RestTemplate restTemplate;

    private final EndpointConfig endpointConfig;

    @Override
    public PageImpl listTopHeadLine(Exchange exchange) {
        Object[] object = (Object[]) exchange.getIn().getBody();
        String sources = (String) object[0];
        String country = (String) object[1];
        String category = (String) object[2];
        Integer pageNo = (Integer) object[3];
        Integer pageSize = (Integer) object[4];
        List<String> sortBy = (List<String>) object[5];

        String formatUrl;
        String formatSort = String.valueOf(PaginationSort.formatSort(sortBy));
        String isFormatStr = formatSort.length() > 0 ? formatSort.substring(0, formatSort.length()-1).replaceAll("\\s", "") : "";

        if(!StringUtils.isEmpty(country) && !StringUtils.isEmpty(category)) {
            formatUrl = getStringCountryCategory(country, category, pageNo, pageSize, isFormatStr);

            ResponseEntity<Root> response = restTemplate.getForEntity(formatUrl, Root.class);
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(PaginationSort.getOrders(sortBy)));
            return new PageImpl(Objects.requireNonNull(response.getBody()).getArticles(), pageable, response.getBody().getArticles().size());
        }

        if(!StringUtils.isEmpty(sources)) {
            formatUrl = getStringSources(sources, pageNo, pageSize, isFormatStr);

            ResponseEntity<Root> response = restTemplate.getForEntity(formatUrl, Root.class);
            Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(PaginationSort.getOrders(sortBy)));
            return new PageImpl(Objects.requireNonNull(response.getBody()).getArticles(), pageable, response.getBody().getArticles().size());
        }

        return null;

    }

    @NotNull
    private String getStringSources(String sources, Integer pageNo, Integer pageSize, String isFormatStr) {
        String formatUrl;
        if(StringUtils.isEmpty(isFormatStr))
            formatUrl =
                    endpointConfig.getUrl() + "/" + endpointConfig.getContextTopHeadlines() + "?sources=" + sources +
                            "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();
        else
            formatUrl = endpointConfig.getUrl() + "/" + endpointConfig.getContextTopHeadlines() + "?sources=" + sources + "&" + isFormatStr +
                    "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();
        return formatUrl;
    }


    @NotNull
    private String getStringCountryCategory(String country, String category, Integer pageNo, Integer pageSize, String isFormatStr) {
        String formatUrl;
        if(StringUtils.isEmpty(isFormatStr))
            formatUrl =
                    endpointConfig.getUrl() + "/" + endpointConfig.getContextTopHeadlines() + "?country=" + country + "&category=" + category +
                            "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();
        else
            formatUrl = endpointConfig.getUrl() + "/" + endpointConfig.getContextTopHeadlines() + "?country=" + country + "&category=" + category + isFormatStr +
                    "&pageSize=" + pageSize + "&pageNo=" + pageNo + "&apiKey=" + endpointConfig.getKey();
        return formatUrl;
    }

}