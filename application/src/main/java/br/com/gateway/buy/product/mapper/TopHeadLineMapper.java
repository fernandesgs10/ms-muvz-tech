package br.com.gateway.buy.product.mapper;

import br.com.muvz.tech.everthing.api.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Builder
@Component
@AllArgsConstructor
public class TopHeadLineMapper implements Serializable {

    private final ModelMapper modelMapper;

    public Root converterObjectToClient(Object object) {
        return modelMapper.map(object, Root.class);
    }

    public br.com.muvz.tech.topheadline.api.Page converterToPageClient(Object pageClient) {
        return modelMapper.map(pageClient, br.com.muvz.tech.topheadline.api.Page.class);
    }

    public Pageable convertPage(br.com.muvz.tech.topheadline.api.Page page) {
        return modelMapper.map(page, Pageable.class);
    }
}