package co.parrolabs.dto;

import co.parrolabs.model.Customer;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressDto implements Serializable {

    private UUID id;

    private String number;

    private String city;

    private String zip_code;

    private String country;

    private CustomerDto customer;

}