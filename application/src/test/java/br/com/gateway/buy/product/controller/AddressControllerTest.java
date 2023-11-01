package br.com.gateway.buy.product.controller;

import br.com.gateway.buy.product.config.ConverterConfig;
import br.com.gateway.buy.product.config.MessageResourceConfig;
import br.com.gateway.buy.product.entity.AddressEntity;
import br.com.gateway.buy.product.mapper.AddressMapper;
import br.com.gateway.buy.product.service.AddressService;
import br.com.muvz.tech.client.api.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@DisplayName("Teste integration endpoints Address")
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = AddressController.class)
@Import(AddressController.class)
public class AddressControllerTest {

    public static final String ENDPOINT_CRUD_CONTROLLER = "/v1/muvz-tech/address";
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private AddressMapper addressMapper;
    @MockBean
    private AddressService addressService;

    @MockBean
    private MessageResourceConfig messageResourceConfig;


    @Test
    @DisplayName("Endpoint to Address list")
    public void listAddress_whenDo_expectedResult() throws Exception {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCoSeqAddress(1l);
        addressEntity.setNmAddress("Test");
        addressEntity.setNmAddressNumber("123");
        addressEntity.setNmAddressZipCode("0235");

        Address address = new Address();
        address.setCoSeqAddress(BigDecimal.valueOf(1));
        address.setNmAddress("Test");
        address.setNmAddressNumber("123");
        address.setNmAddressZipCode("0235");

        br.com.muvz.tech.address.api.Page pageReturn = new br.com.muvz.tech.address.api.Page();
        pageReturn.content(List.of(addressEntity));

        Page page = new PageImpl<>(List.of(addressEntity));

        when(addressService.listAddress(any())).thenReturn(page);
        when(addressMapper.converterObjectToAddress(any())).thenReturn(address);

        when(addressMapper.converterToPageAddress(any())).thenReturn(pageReturn);

        ResultActions result = mockMvc
                .perform(MockMvcRequestBuilders
                .get(ENDPOINT_CRUD_CONTROLLER )
                .characterEncoding("utf-8"))
                .andExpect(status().isOk());

        String content = result.andReturn().getResponse().getContentAsString();
        Assert.notNull(content);
    }
}
