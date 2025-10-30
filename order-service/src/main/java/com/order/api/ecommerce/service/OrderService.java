package com.order.api.ecommerce.service;
import com.order.api.ecommerce.dto.InventoryResponse;
import com.order.api.ecommerce.dto.OrderRequest;
import com.order.api.ecommerce.dto.OrderResponse;
import com.order.api.ecommerce.exceptions.NotFoundException;
import com.order.api.ecommerce.mapper.OrderMapper;
import com.order.api.ecommerce.model.Order;
import com.order.api.ecommerce.model.OrderLineItems;
import com.order.api.ecommerce.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    private WebClient.Builder webClientBuilder;

    public OrderResponse placeOrder(OrderRequest orderRequest){

        Order order = new Order();

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(orderMapper::toOrderLineItemsEntity)
                .toList();
        order.setOrderLineItemsList(orderLineItems);

        List<String> listOfSkuCode = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //Call Inventory service, and place order if product is in Stock
        InventoryResponse[] inventoryResponsesArray = webClientBuilder.build()
                .get()
                    .uri("http://inventory/api/v1/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",listOfSkuCode).build())
                        .retrieve()
                            .bodyToMono(InventoryResponse[].class)
                                .block();

        assert inventoryResponsesArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponsesArray)
                .allMatch(InventoryResponse::isInStock);

        if (allProductsInStock){
            orderRepository.save(order);
        } else {
            throw new NotFoundException("Product is not in stock, please");
        }

        return OrderResponse.builder()
                .message("The order has placed")
                .build();
    }
}
