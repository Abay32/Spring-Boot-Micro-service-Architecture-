package com.inventory.api.ecommerce.controller;

import com.inventory.api.ecommerce.dto.InventoryResponse;
import com.inventory.api.ecommerce.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    //http:localhost:8082/api/inventory?skuCode=iphone-13&skuCode=iphone13-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam(name = "skuCode") List<String> skuCode){
        return inventoryService.isInStock(skuCode);
    }

}
