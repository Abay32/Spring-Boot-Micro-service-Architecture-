package com.order.api.ecommerce.mapper;

import com.order.api.ecommerce.dto.OrderLineItemsDto;
import com.order.api.ecommerce.model.OrderLineItems;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderLineItemsDto toOrderLineItemsDto(OrderLineItems orderLineItems);
    OrderLineItems toOrderLineItemsEntity(OrderLineItemsDto orderLineItemsDto);
}
