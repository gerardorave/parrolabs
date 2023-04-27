package co.parrolabs.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShippingAddressRequest {

    private UUID id;
    @NotNull(message = "the shipping address must have a number")
    private String number;
    @NotNull(message = "the shipping address must have a city")
    private String city;
    @NotNull(message = "the shipping address must have a zip Code")
    private String zip_code;
    @NotNull(message = "the shipping address must have a country")
    private String country;
    @NotNull(message = "the shipping address must have a customer id")
    private UUID customerId;

}