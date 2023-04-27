package co.parrolabs.util;

import co.parrolabs.dto.CustomerDto;
import co.parrolabs.dto.OrderCustomerDto;
import co.parrolabs.dto.ProductDto;
import co.parrolabs.dto.mongodb.CustomerMongoDbDto;
import co.parrolabs.dto.mongodb.OrderCustomerMongoDbDto;
import co.parrolabs.dto.mongodb.ProductMongoDbDto;
import co.parrolabs.dto.request.OrderCustomerRequest;
import co.parrolabs.model.OrderCustomer;

import java.util.UUID;

public final class Transformation {

    //mapper.map(productDto, ProductMongoDbDto .class);
    public static ProductMongoDbDto productDtoToMongoDbDto(ProductDto productDto){
        return ProductMongoDbDto.builder()
                .id(productDto.getId().toString())
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .weight(productDto.getWeight())
                .build();
    }

    public static CustomerMongoDbDto customerDtoToMongoDbDto(CustomerDto customerDto){
        String shippingAddressId = "";
        if(customerDto.getPrimaryShippingAddress() != null){
            shippingAddressId = customerDto.getPrimaryShippingAddress().toString();
        }
        return CustomerMongoDbDto.builder()
                .id(customerDto.getId().toString())
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .phone(customerDto.getPhone())
                .primaryShippingAddress(shippingAddressId)
                .build();
    }

    public static OrderCustomerMongoDbDto orderCustomerToMongoDbDto(OrderCustomerDto orderCustomerDto){
        String shippingAddressId = "";
        String orderDate = "";
        if(orderCustomerDto.getCustomerShippingAddressId() != null){
            shippingAddressId = orderCustomerDto.getCustomerShippingAddressId().toString();
        }
        if(orderCustomerDto.getArrivalDate() != null){
            orderDate = ZoneDateTimeUtils.formatDateString(orderCustomerDto.getArrivalDate());
        }
        return OrderCustomerMongoDbDto.builder()
                .id(orderCustomerDto.getId().toString())
                .customerId(orderCustomerDto.getCustomer().getId().toString())
                .arrivalDate(ZoneDateTimeUtils.formatDateString(orderCustomerDto.getArrivalDate()))
                .customerShippingAddressId(shippingAddressId)
                .paymentTypeId(orderCustomerDto.getPaymentType().getId().toString())
                .orderDate(orderDate)
                .build();
    }

}
