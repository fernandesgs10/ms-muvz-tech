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

    public static void main(String args[]) {
        List<String> list = new ArrayList<>();
        list.add("author");
        list.add("title, asc");

        String str = String.valueOf(formatSort(list));

        String formatStr = str.length() > 0 ? str.substring(0, str.length()-1) : "";

        String result = formatStr.replaceAll("\\s", "");

        System.out.println(result);

    }

    public static StringBuilder formatSort(List<String> properties) {
        StringBuilder str = new StringBuilder();
        if(properties != null) {
            final int[] count = {0};
            properties.forEach(sortBys -> {
                count[0]++;
                str.append("sortBy=" + sortBys);
                if (count.length > 0)
                    str.append("&");
            });
        }

        return str;
    }

    @NotNull
    public static List<Sort.Order> getOrders(List<String> properties) {
        Sort sort = Sort.by(new Sort.Order[0]);

        List<Sort.Order> sortOrders = new ArrayList<>();
        boolean isPagination = false;

        if (properties != null) {
            for (String property : properties) {
                String[] split = property.split(",");
                if (properties.size() > 1) {
                    isPagination = true;
                    if (split.length > 1 && split[1].trim().equalsIgnoreCase("desc")) {
                        sortOrders.add(Sort.by(split[0]).descending().getOrderFor(split[0]));
                    } else {
                        sortOrders.add(Sort.by(split[0]).ascending().getOrderFor(split[0]));
                    }
                }
            }

            if(!isPagination) {
                for (String property : properties) {
                    String[] split = property.split(",");
                        if (split.length > 1 && split[1].trim().equalsIgnoreCase("desc")) {
                            sortOrders.add(Sort.by(split[0]).descending().getOrderFor(split[0]));
                        } else {
                            sortOrders.add(Sort.by(split[0]).ascending().getOrderFor(split[0]));
                        }
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
