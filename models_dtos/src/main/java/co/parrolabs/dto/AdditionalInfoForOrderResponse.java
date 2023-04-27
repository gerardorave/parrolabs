package co.parrolabs.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;



@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfoForOrderResponse implements Serializable {
    private List<ProductDto> products;
    private ShippingAddressDto shippingAddress;
    private CustomerDto customer;
    private PaymentTypeDto paymentType;
}

