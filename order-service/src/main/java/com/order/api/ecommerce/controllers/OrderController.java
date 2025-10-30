package com.order.api.ecommerce.controllers;

import com.order.api.ecommerce.dto.OrderRequest;
import com.order.api.ecommerce.dto.OrderResponse;
import com.order.api.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest){
        OrderResponse orderResponse = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED) ;
    }
}
