package br.com.gateway.buy.product.controller;

import br.com.gateway.buy.product.common.NotFoundException;
import br.com.gateway.buy.product.config.MessageResourceConfig;
import br.com.gateway.buy.product.entity.ClientEntity;
import br.com.gateway.buy.product.generate.PageGeneric;
import br.com.gateway.buy.product.generate.PaginationSort;
import br.com.gateway.buy.product.mapper.ClientMapper;
import br.com.gateway.buy.product.service.ClientService;
import br.com.muvz.tech.client.api.Client;
import br.com.muvz.tech.client.api.ClientApi;
import com.google.common.collect.Maps;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${openapi.ms-muvz-tech.base-path:/v1/muvz-tech}/client")
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class ClientController extends PageGeneric implements ClientApi {

    private final ClientMapper clientMapper;
    private final ClientService clientService;
    private final MessageResourceConfig messageResourceConfig;

    @Override
    @GetMapping
    public ResponseEntity<br.com.muvz.tech.client.api.Page> listClient(
            @Valid @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @Valid @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @Valid @RequestParam(value = "sortBy", required = false) List<String> sortBy) {
        log.info("class=ClientController method=listClient step=start");

        List<Sort.Order> sort = PaginationSort.getOrders(sortBy);
        Map<String, String> orderMap = Maps.newHashMap();
        orderMap.put("nmName", "nmName");

        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Pageable pg = PaginationSort.ajustarSort(paging, orderMap);

        Page pageClient = clientService.listClient(pg);

        Page<Client> pageClientDto = convertPage(pageClient,
                clientMapper::converterObjectToClient);

        br.com.muvz.tech.client.api.Page pageConvert =
                    clientMapper.converterToPageClient(pageClientDto);

        pageConvert.content(Collections.singletonList(pageClientDto.getContent()));
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(pageConvert);
        log.info("class=ClientController method=listClient step=end response{}", response);
        return response;
    }

    @Override
    @GetMapping("/nmName/{nmName}")
    public ResponseEntity listClientByName(
            @ApiParam(value = "name client", required = true) @PathVariable("nmName") String nmName) {
        log.info("class=ClientController method=listClientByName step=start nmName={}", nmName);

        ClientEntity clientEntity = clientService.listClientByName(nmName).orElseThrow(() ->
                new NotFoundException(messageResourceConfig.getMessage("general.notfound", "nmName", nmName)));

        Client clientDTO = clientMapper.converterClientEntityToClient(clientEntity);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(clientDTO);
        log.info("class=ClientController method=listClientByName step=end response{}", response);
        return response;
    }
}