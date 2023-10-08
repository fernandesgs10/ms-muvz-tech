package br.com.gateway.buy.product.generate;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class PaginationSort {

    @NotNull
    public static List<Sort.Order> getOrders(List<String> properties) {
        Sort sort = Sort.by(new Sort.Order[0]);

        List<Sort.Order> sortOrders = new ArrayList<>();

        if(properties != null) {
            for (String property : properties) {
                var orderBy = property.split(",");
                if (orderBy[1].trim().equals("desc")) {
                    sortOrders.add(Sort.by(orderBy[0]).descending().getOrderFor(orderBy[0]));
                } else {
                    sortOrders.add(Sort.by(orderBy[0]).ascending().getOrderFor(orderBy[0]));
                }
            }
        }
        
        return sortOrders;
    }

    public static Pageable ajustarSort(Pageable pageable, Map<String, String> orderMap) {

        List<Sort.Order> orders = pageable.getSort().stream().map(order -> criarOrder(order, orderMap)).filter(Objects::nonNull)
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
    }

    private static Sort.Order criarOrder(Sort.Order order, Map<String, String> orderMap) {

        String propriedade = order.getProperty();

        if (orderMap.containsKey(propriedade)) {

            if (StringUtils.isBlank(orderMap.get(propriedade))) {
                return null;
            }

            propriedade = orderMap.get(propriedade);
        }

        return new Sort.Order(order.getDirection(), propriedade);
    }
}
