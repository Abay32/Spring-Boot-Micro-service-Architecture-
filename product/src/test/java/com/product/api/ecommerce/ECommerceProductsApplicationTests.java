package com.product.api.ecommerce;

import com.product.api.ecommerce.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = Application.class)
@Testcontainers
@RequiredArgsConstructor
@AutoConfigureMockMvc
class ECommerceProductsApplicationTests {

    @Container
    static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:18.0");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    }

	@Test
	void shouldCreateProduct() throws Exception {
        ProductDto productDto = getProductDto();
        String productRequest = objectMapper.writeValueAsString(productDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productRequest)
        ).andExpect(status().isCreated()) ;
	}

    private ProductDto getProductDto() {
        return ProductDto.builder()
                .product_name("iphone 14 pro max")
                .description("iphone 14 pro max")
                .price(BigDecimal.valueOf(1278))
                .build();
    }

}
