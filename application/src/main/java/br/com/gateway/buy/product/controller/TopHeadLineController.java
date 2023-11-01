package br.com.gateway.buy.product.controller;

import br.com.gateway.buy.product.generate.PageGeneric;
import br.com.gateway.buy.product.innoveahub.Root;
import br.com.gateway.buy.product.mapper.TopHeadLineMapper;
import br.com.gateway.buy.product.service.TopHeadLineService;
import br.com.muvz.tech.topheadline.api.TopHeadlinesApi;
import io.swagger.annotations.ApiParam;
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
@RequestMapping("${openapi.ms-muvz-tech.base-path:/v1/muvz-tech}/top-headlines")
@AllArgsConstructor
public class TopHeadLineController extends PageGeneric implements TopHeadlinesApi {

    private final TopHeadLineMapper topHeadLineMapper;
    private final TopHeadLineService topHeadLineService;

    @Override
    @GetMapping
    public ResponseEntity<br.com.muvz.tech.topheadline.api.Page> listTopHeadLine(
            @ApiParam(value = "sources") @Valid @RequestParam(value = "sources", required = false) String sources,
            @ApiParam(value = "country") @Valid @RequestParam(value = "country", required = false) String country,
            @ApiParam(value = "category") @Valid @RequestParam(value = "category", required = false) String category,
            @Valid @RequestParam(value = "pageNo", required = false, defaultValue="0") Integer pageNo,
            @Valid @RequestParam(value = "pageSize", required = false, defaultValue="20") Integer pageSize,
            @Valid @RequestParam(value = "sortBy", required = false) List<String> sortBy) {

        log.info("class=TopHeadLineController method=listTopHeadLineBySources step=start sources={}, " +
                        "country={}, category={}, pageNo={}, pageSize={}, sortBy={}",
                sources, country, category, pageNo, pageSize, sortBy);

        org.springframework.data.domain.Page<Root> page = topHeadLineService.listTopHeadLine(sources, country, category, pageNo, pageSize, sortBy);

        br.com.muvz.tech.topheadline.api.Page pageConvert = topHeadLineMapper.converterToPageClient(page);

        log.info("class=TopHeadLineController method=listTopHeadLineBySources step=end response{}", pageConvert);
        return ResponseEntity.status(HttpStatus.OK).body(pageConvert);
    }
}


