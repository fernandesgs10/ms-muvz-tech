package br.com.gateway.buy.product.controller;

import br.com.gateway.buy.product.common.MuvzException;
import br.com.gateway.buy.product.common.NotFoundException;
import br.com.gateway.buy.product.config.MessageResourceConfig;
import br.com.gateway.buy.product.entity.AddressEntity;
import br.com.gateway.buy.product.generate.PageGeneric;
import br.com.gateway.buy.product.service.AddressService;
import br.com.gateway.buy.product.generate.PaginationSort;
import br.com.gateway.buy.product.mapper.AddressMapper;
import br.com.muvz.tech.address.api.AddressApi;
import br.com.muvz.tech.client.api.Address;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("${openapi.ms-muvz-tech.base-path:/v1/muvz-tech}/address")
@AllArgsConstructor
@SuppressWarnings("unchecked")
public class AddressController extends PageGeneric implements AddressApi {

    private final AddressMapper addressMapper;
    private final AddressService addressService;
    private final MessageResourceConfig messageResourceConfig;

    @Override
    @PostMapping
    public ResponseEntity createAddress(@ApiParam(value = "Optional description in new address", required = true)
                                        @Valid @RequestBody br.com.muvz.tech.address.api.Address address) {
        log.info("class=AddressController method=createAddress step=start address={}", address);
        AddressEntity addressEntity = addressMapper.converterAddressToAddressEntity(address);

        AddressEntity addressEntityReturn = addressService.createAddress(addressEntity).orElseThrow(() ->
                new MuvzException(messageResourceConfig.getMessage("general.error")));

        Address addressDtoEntity = addressMapper.converterAddressEntityToAddress(addressEntityReturn);

        log.info("class=AddressController method=createAddress step=end response{}", addressDtoEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDtoEntity);
    }

    @Override
    @GetMapping("/nmAddressZipCode/{nmAddressZipCode}")
    public ResponseEntity listAddressByNmAddressZipCode(
            @ApiParam(value = "nmAddressZipCode address",required=true) @PathVariable("nmAddressZipCode") String nmAddressZipCode) {
        log.info("class=AddressController method=listAddressByNmAddressZipCode step=start nmName={}", nmAddressZipCode);

        AddressEntity addressEntity = addressService.listAddressByNmAddressZipCode(nmAddressZipCode).orElseThrow(() ->
                new NotFoundException(messageResourceConfig.getMessage("client.nmname.notfound", "nmAddressZipCode", nmAddressZipCode)));

        Address address = addressMapper.converterAddressEntityToAddress(addressEntity);
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(address);
        log.info("class=AddressController method=listAddressByNmAddressZipCode step=end response{}", response);
        return response;
    }

    @Override
    @GetMapping
    public ResponseEntity<br.com.muvz.tech.address.api.Page> listAddress(
            @Valid @RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer pageNo,
            @Valid @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
            @Valid @RequestParam(value = "sortBy", required = false) List<String> sortBy) {
        log.info("class=AddressController method=listAddress step=start");

        List<Sort.Order> sort = PaginationSort.getOrders(sortBy);
        Map<String, String> orderMap = Maps.newHashMap();
        orderMap.put("nmAddressZipCode", "nmAddressZipCode");

        PageRequest paging = PageRequest.of(pageNo, pageSize, Sort.by(sort));
        Pageable pg = PaginationSort.ajustarSort(paging, orderMap);

        Page pageClient = addressService.listAddress(pg);

        Page<Address> pageClientDto = convertPage(pageClient,
                addressMapper::converterObjectToAddress);

        br.com.muvz.tech.address.api.Page pageConvert =
                addressMapper.converterToPageAddress(pageClientDto);

        pageConvert.content(Collections.singletonList(pageClientDto.getContent()));
        ResponseEntity response = ResponseEntity.status(HttpStatus.OK).body(pageConvert);
        log.info("class=AddressController method=listAddress step=end response{}", response);

        return response;
    }
}