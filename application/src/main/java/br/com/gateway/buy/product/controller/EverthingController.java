package br.com.gateway.buy.product.controller;

import br.com.gateway.buy.product.generate.PageGeneric;
import br.com.gateway.buy.product.innoveahub.Root;
import br.com.gateway.buy.product.mapper.EverthingMapper;
import br.com.gateway.buy.product.service.EverthingService;
import br.com.muvz.tech.everthing.api.EverythingApi;
import br.com.muvz.tech.everthing.api.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("${openapi.ms-muvz-tech.base-path:/v1/muvz-tech}/everthing")
@AllArgsConstructor
public class EverthingController extends PageGeneric implements EverythingApi {

    private final EverthingMapper everthingMapper;
    private final EverthingService everthingService;

    @Override
    @GetMapping
    public ResponseEntity<br.com.muvz.tech.everthing.api.Page> listEverthing(
            @Valid @RequestParam(value = "q", required = false) String q,
            @Valid @RequestParam(value = "from", required = false) String from,
            @Valid @RequestParam(value = "to", required = false) String to,
            @Valid @RequestParam(value = "domains", required = false) String domains,
            @Valid @RequestParam(value = "pageNo", required = false, defaultValue="0") Integer pageNo,
            @Valid @RequestParam(value = "pageSize", required = false, defaultValue="20") Integer pageSize,
            @Valid @RequestParam(value = "sortBy", required = false) List<String> sortBy) {

        log.info("class=EverthingController method=listEverthing step=start q={}, from={}, to={}, domains={}, pageNo={}, pageSize={}, sortBy={}",
                q, from, to, domains, pageNo, pageSize, sortBy);

        org.springframework.data.domain.Page<Root> page = everthingService.listEverthing(q, from, to, domains, pageNo, pageSize, sortBy);
        Page pageConvert = everthingMapper.converterToPageClient(page);

        log.info("class=EverthingController method=listEverthing step=end response{}", pageConvert);
        return ResponseEntity.status(HttpStatus.OK).body(pageConvert);
    }
}


